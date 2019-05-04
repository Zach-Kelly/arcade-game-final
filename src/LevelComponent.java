import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class LevelComponent extends JComponent {
	
	public LevelLoader levelLoader;
	private JFrame frame;
	private int numTicks;
	
	public LevelComponent(JFrame frame) {
		this.levelLoader = new LevelLoader();
		this.frame = frame;
		this.numTicks = 0;
	}
	
	public void handleLoadLevel(String path) {
		this.levelLoader.loadFile(path);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		this.frame.getContentPane().setBackground(this.levelLoader.getBgColor());
		this.levelLoader.drawLevel(g2);
		
		
		
	}
	public void drawScreen() {
		handleCollisions();
		updateState();
		this.repaint();
		numTicks+=1;
		//System.out.println("Tick " + this.numTicks);
	}
	
	public void updateState() {
		this.levelLoader.updateLevel();
	}
	
	public Hero getHero() {
		return (Hero) this.levelLoader.getHero();
	}
	
	public void handleCollisions() {
		ArrayList<Entity> entities=this.levelLoader.getEntities();
		ArrayList<Obstacle> obstacles=this.levelLoader.getObstacles();
		for (Obstacle obstacle : obstacles) {
			for (Entity entity : entities) {
				if (obstacle.intersects(entity.hitBox)) {
					obstacle.handleCollision(entity);
				}
			}
		}
	}
	
}
