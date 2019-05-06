import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.Timer;

//TODO: add hero and monsters back in
//TODO: add updateLevel for entities
@SuppressWarnings("serial")
public class LevelComponent extends JComponent {
	
	public LevelLoader levelLoader;
	private JFrame frame;
	
	public LevelComponent(JFrame frame) {
		
		this.levelLoader = new LevelLoader();
		this.frame = frame;
		KeyboardListener keyListener = new KeyboardListener();
		this.addKeyListener(keyListener);
		this.levelLoader.addKeyListener(keyListener);

	}
	
	public void handleLoadLevel(String path) {
		this.levelLoader.loadFile(path);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		this.levelLoader.drawObstacles(g2);
		this.levelLoader.drawEntities(g2);
		
	}
	
	public void updateScreen() {
		
		this.levelLoader.updateEntityPositions();
		this.levelLoader.handleCollisions();
		this.repaint();
		
	}
	
	public Color handleGetBackgroundColor() {
		return this.levelLoader.getBgColor();
	}
	
}
