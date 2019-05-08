import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JComponent;

public class LevelLoader {
	//TODO: convert subtypes to strings for readability
	private Scanner scanner;
	private Color bgColor;
	private ArrayList<Obstacle> obstacles;
	private ArrayList<Entity> entities;
	//private ArrayList<Projectile> enemyProjectiles;
	private KeyboardListener keyListener;

	public void loadFile(String path) {

		System.out.println(path);
		try {
			this.scanner = new Scanner(new File(path));
		} catch (FileNotFoundException e) {
			System.out.println("Invalid level file");
			return;
		}
		try {
			constructLevel();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public KeyboardListener getKeyListener() {
		return keyListener;
	}

	public void addKeyListener(KeyboardListener keyListener) {
		this.keyListener = keyListener;
	}

	public void updateEntityPositions() {

		for (Entity e : this.entities) {
			e.updatePosition();
		}

	}

	private void constructLevel() throws Exception {

		this.obstacles = new ArrayList<Obstacle>();
		this.entities = new ArrayList<Entity>();
		//this.enemyProjectiles = new ArrayList<Projectile>();

		Color[] obstacleColors = getObstacleColors();
		createHero();
		createMonsters();
		createObstacles(obstacleColors);
		this.scanner.close();

	}

	public void handleCollisions() {

		for (Obstacle o : this.obstacles) {
			for (Entity entity : this.entities) {
				entity.checkObstacleCollision(o);
			}
		}
		//TODO: make a list of things that kill the hero on touch - or not
//		for (Projectile p : this.enemyProjectiles) {
//			if (this.entities.get(0).getFullHitBox().intersects(p.getFullHitBox())) {
//				this.entities.get(0).die();
//			}
//		}
		for (int i = 1; i < this.entities.size(); i++) {
			if (this.entities.get(0).getFullHitBox().intersects(this.entities.get(i).getFullHitBox())) {
				this.entities.get(0).die();
			}
		}

	}
	//TODO
//	public void updateProjectilePositions() {
//		
//		for (Projectile p : this.enemyProjectiles) {
//			p.updatePosition();
//		}
//		
//	}

	private Color[] getObstacleColors() throws Exception {

		Color obstacleFillColor;
		Color obstacleOutlineColor;
		if (!this.scanner.next().equals("L")) {
			throw new Exception("Level colors not found, it must be the first line");
		} else {
			this.bgColor = convertTextToColor();
			obstacleFillColor = convertTextToColor();
			obstacleOutlineColor = convertTextToColor();
		}
		Color[] obstacleColors = { obstacleFillColor, obstacleOutlineColor };
		return obstacleColors;

	}

	private void createHero() throws Exception {

		this.scanner.nextLine();
		if (!this.scanner.next().equals("H")) {
			throw new Exception("Hero not found, it must be the second line");
		} else {
			int x = this.scanner.nextInt();
			int y = this.scanner.nextInt();
			this.entities.add(new Hero(x, y, this.entities));
			this.keyListener.addHero((Hero) this.entities.get(0));
		}

	}

	private void createMonsters() throws Exception {

		this.scanner.nextLine();
		boolean hasMonsters = false;
		while (this.scanner.next().equals("M")) {
			hasMonsters = true;
			int x = this.scanner.nextInt();
			int y = this.scanner.nextInt();
			int subtype = this.scanner.nextInt();
			if (subtype == 0) {
				this.entities.add(new Runner(x, y, (Hero) this.entities.get(0), this.entities));
			}
			if (subtype == 1) {
				//TODO: shooter
				this.entities.add(null);
			}
			this.scanner.nextLine();
		}
		if (!hasMonsters) {
			throw new Exception("Monsters not found, they must come after the hero line");
		}

	}

	private void createObstacles(Color[] obstacleColors) {

		this.scanner.nextLine();
		while (this.scanner.next().equals("O")) {
			int x = this.scanner.nextInt();
			int y = this.scanner.nextInt();
			int subtype = this.scanner.nextInt();
			int width = this.scanner.nextInt();
			int height = this.scanner.nextInt();
			this.obstacles.add(new Obstacle(x, y, width, height, subtype, obstacleColors[0], obstacleColors[1]));
			if (!this.scanner.hasNextLine()) {
				break;
			}
			this.scanner.nextLine();
		}

	}

	private Color convertTextToColor() {

		int r = this.scanner.nextInt();
		int g = this.scanner.nextInt();
		int b = this.scanner.nextInt();
		return new Color(r, g, b);

	}

	public Color getBgColor() {
		return bgColor;
	}

	public void drawEntities(Graphics2D g2, JComponent observer) {

		for (Entity e : this.entities) {
			e.drawOn(g2, observer);
		}

	}

	public void drawObstacles(Graphics2D g2) {

		for (Obstacle o : this.obstacles) {
			o.drawOn(g2);
		}

	}

}
