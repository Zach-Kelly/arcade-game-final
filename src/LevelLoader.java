import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class LevelLoader {

	private Scanner scanner;
	private Color bgColor;
	private ArrayList<Obstacle> obstacles;
	
	public void loadFile(String path) {
		
		try {
			this.scanner = new Scanner(new File(path));
		}
		catch(FileNotFoundException e) {
			System.out.println("Invalid level file");
			return;
		}
		try {
			constructLevel();
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	private void constructLevel() throws Exception {
		
		Color obstacleFillColor;
		Color obstacleOutlineColor;
		this.obstacles = new ArrayList<Obstacle>();
		
		if(!this.scanner.next().equals("L")) {
			throw new Exception("Level colors not found, it must be the first line");
		}
		else {
			this.bgColor = convertTextToColor();
			obstacleFillColor = convertTextToColor();
			obstacleOutlineColor = convertTextToColor();
		}
		
		this.scanner.nextLine();
		if(!this.scanner.next().equals("H")) {
			throw new Exception("Hero not found, it must be the second line");
		}
		else {
			//TODO: construct hero
		}
		
		this.scanner.nextLine();
		boolean hasMonsters = false;
		while(this.scanner.next().equals("M")) {
			hasMonsters = true;
			//TODO: construct monster
			this.scanner.nextLine();
		}
		if(!hasMonsters) {
			throw new Exception("Monsters not found, they must come after the hero line");
		}
		
		this.scanner.nextLine();
		while(this.scanner.next().equals("O")) {
			int x = this.scanner.nextInt();
			int y = this.scanner.nextInt();
			String subtype = this.scanner.next();
			int width = this.scanner.nextInt();
			int height = this.scanner.nextInt();
			this.obstacles.add(new Obstacle(x, y, width, height, subtype, obstacleFillColor,
											obstacleOutlineColor));		
			if(!this.scanner.hasNextLine()) {
				break;
			}
			this.scanner.nextLine();
		}
		this.scanner.close();
		
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
	
	public void drawLevel(Graphics2D g2) {
		
		//TODO: add hero and monsters
		for(Obstacle o : this.obstacles) {
			o.drawOn(g2);
		}
		
	}
	
}
