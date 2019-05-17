import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import javax.swing.JComponent;

public class LevelLoader {

	private static final int ENEMY_RUNNER = 0;
	private static final int ENEMY_SHOOTER = 1;
	private static final int ENEMY_WALKER = 2;
	private static final int ENEMY_BOSS = 4;
	private static final int HERO = 0;
	private static final Color TEXT_COLOR = Color.MAGENTA;
	private static final Font SCORE_FONT = new Font("Score", Font.BOLD, 24);
	private static final int SCORE_POSITION = 25;
	private static final int FILL_COLOR = 0;
	private static final int OUTLINE_COLOR = 1;

	private ArrayList<Entity> entities;
	private ArrayList<Entity> projectiles;
	private ArrayList<Obstacle> obstacles;
	private ArrayList<Entity> fruit;
	private Color bgColor;
	private GameKeyboardListener keyListener;
	private Scanner scanner;
	private HashMap<String, Integer> keyStates = new HashMap<String, Integer>();
	private int score = 0;

	//
	// initial level loading
	//

	/**
	 * Starts the process of loading and constructing a level.
	 * 
	 * @param path the path of the level file to be loaded
	 */

	public LevelLoader() {

		this.keyStates.put("U", 0);
		this.keyStates.put("D", 0);

	}

	public void loadFile(String path) {

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

	private void constructLevel() throws Exception {

		this.obstacles = new ArrayList<Obstacle>();
		this.entities = new ArrayList<Entity>();
		this.projectiles = new ArrayList<Entity>();
		this.fruit = new ArrayList<Entity>();
		this.keyStates.put("U", 0);
		Color[] obstacleColors = getObstacleColors();
		createHero();
		createMonsters();
		createObstacles(obstacleColors);
		this.scanner.close();

	}

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

	private Color convertTextToColor() {

		int r = this.scanner.nextInt();
		int g = this.scanner.nextInt();
		int b = this.scanner.nextInt();
		return new Color(r, g, b);

	}

	private void createHero() throws Exception {

		this.scanner.nextLine();
		if (!this.scanner.next().equals("H")) {
			throw new Exception("Hero not found, it must be the second line");
		} else {
			int x = this.scanner.nextInt();
			int y = this.scanner.nextInt();
			this.entities.add(new Hero(x, y));
			this.keyListener.addHero((Hero) this.entities.get(HERO));
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
			if (subtype == ENEMY_RUNNER) {
				this.entities.add(new Runner(x, y, (Hero) this.entities.get(HERO)));
			}
			if (subtype == ENEMY_SHOOTER) {
				this.entities.add(new Shooter(x, y, (Hero) this.entities.get(HERO), this.projectiles));
			}
			if (subtype == ENEMY_WALKER) {
				this.entities.add(new Walker(x, y, (Hero) this.entities.get(HERO)));
			}
			if (subtype == ENEMY_BOSS) {
				this.entities.add(new ColeBoss(x, y, this.projectiles, this.fruit));
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
			this.obstacles.add(new Obstacle(x, y, width, height, subtype, obstacleColors[FILL_COLOR],
					obstacleColors[OUTLINE_COLOR]));
			if (!this.scanner.hasNextLine()) {
				break;
			}
			this.scanner.nextLine();
		}

	}

	///
	/// getters and setters
	///

	public boolean handleGetIsHeroDead() {

		Hero hero = (Hero) this.entities.get(HERO);
		return hero.isDead();

	}

	/**
	 * Returns the KeyListener used for all keyboard input.
	 * 
	 * @return
	 */
	public GameKeyboardListener getKeyListener() {
		return keyListener;
	}

	/**
	 * Sets the KeyListener used for all keyboard input.
	 * 
	 * @param keyListener
	 */
	public void setKeyListener(GameKeyboardListener keyListener) {
		this.keyListener = keyListener;
	}

	/**
	 * Gets the background color of the currently loaded level.
	 * 
	 * @return
	 */
	public Color getBgColor() {
		return bgColor;
	}

	///
	/// update methods
	///

	/**
	 * Updates the positions of all entities, checks if they should shoot a
	 * projectile, and removes any dead entities.
	 */
	public void updateEntityActions() {

		if (this.entities.size() == 1 && this.fruit.size() == 0) {
			this.keyStates.put("U", 1);
		}
		updateActionsHelper(this.entities.subList(1, this.entities.size()));
		updateActionsHelper(this.projectiles);
		updateActionsHelper(this.fruit);
		Hero hero = (Hero) this.entities.get(HERO);
		hero.updatePosition();
		hero.shootProjectile();

	}

	private void updateActionsHelper(List<Entity> list) {

		Entity e;
		for (int i = list.size() - 1; i > -1; i--) {
			e = list.get(i);
			e.updatePosition();
			if (e.isDead()) {
				list.remove(e);
			}
		}

	}

	/**
	 * Checks for collision between entities and obstacles/each other.
	 */
	public void handleCollisions() {

		for (Obstacle o : this.obstacles) {
			this.entities.get(HERO).checkObstacleCollision(o);
		}
		for (Entity p : this.projectiles) {
			collisionHelper(p);
		}
		for (Entity e : this.entities.subList(1, this.entities.size())) {
			Hero hero = (Hero) this.entities.get(HERO);
			collisionHelper(e);
			Enemy m = (Enemy) e;
			if (m.isTrapped() && hero.getFullHitBox().intersects(e.getFullHitBox())) {
				m.markForDeath();
				this.fruit.add(new Fruit((int) e.posX, (int) e.posY));
			}
			hero.checkHeroProjectileCollision(m);
		}
		for (int i = 0; i < fruit.size(); i++) {
			collisionHelper(this.fruit.get(i));
		}

	}

	private void collisionHelper(Entity e) {

		Hero hero = (Hero) this.entities.get(HERO);
		for (Obstacle o : this.obstacles) {
			e.checkObstacleCollision(o);
		}
		if (hero.getFullHitBox().intersects(e.getFullHitBox())) {
			if (e.isEdible || ((Enemy) e).isTrapped()) {
				e.markForDeath();
				score = score + e.pointValue;
			} else {
				hero.markForDeath();
			}
		}

	}

	///
	/// drawing methods
	///

	/**
	 * Causes all entities to be displayed.
	 * 
	 * @param g2       the Graphics2D object used to draw everything
	 * @param observer the observer needed to draw sprite images
	 */
	public void drawEntities(Graphics2D g2, JComponent observer) {

		for (Entity e : this.entities) {
			e.drawOn(g2, observer);
		}
		for (Entity p : this.projectiles) {
			p.drawOn(g2, observer);
		}
		for (Entity f : this.fruit) {
			f.drawOn(g2, observer);
		}

	}

	/**
	 * Causes all obstacles to be displayed.
	 * 
	 * @param g2 the Graphics2D object used to draw everything
	 */
	public void drawObstacles(Graphics2D g2) {

		for (Obstacle o : this.obstacles) {
			o.drawOn(g2);
		}

	}

	public void drawScore(Graphics2D g2) {

		g2.setColor(TEXT_COLOR);
		g2.setFont(SCORE_FONT);
		g2.drawString("SCORE: " + score, SCORE_POSITION, SCORE_POSITION);

	}

	public int getUKeyState() {
		return this.keyStates.get("U");
	}

}
