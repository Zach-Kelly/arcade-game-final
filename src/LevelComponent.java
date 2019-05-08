import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class LevelComponent extends JComponent {

	public LevelLoader levelLoader;
	private ArrayList<String> levelList = new ArrayList<String>();
	private int currentLevelIndex = 0;
	private JFrame frame;

	public LevelComponent(JFrame frame) {

		this.frame = frame;
		this.levelLoader = new LevelLoader();
		KeyboardListener keyListener = new KeyboardListener();
		keyListener.addLevelComponent(this);
		this.addKeyListener(keyListener);
		this.levelLoader.addKeyListener(keyListener);
		this.levelList.add("src/Levels/Level_1.txt");
		this.levelList.add("src/Levels/Level_2.txt");
		this.levelList.add("src/Levels/Level_3.txt");

	}

	public KeyListener handleGetKeyListener() {
		return this.levelLoader.getKeyListener();
	}

	public void handleLoadLevel(int indexOffset) {

		this.currentLevelIndex += indexOffset;
		this.currentLevelIndex = (this.currentLevelIndex + this.levelList.size()) % this.levelList.size();
		this.levelLoader.loadFile(this.levelList.get(this.currentLevelIndex));
		this.frame.getContentPane().setBackground(this.levelLoader.getBgColor());

	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		this.levelLoader.drawObstacles(g2);
		this.levelLoader.drawEntities(g2, this);

	}

	public void updateScreen() {

		this.levelLoader.updateEntityPositions();
		//this.levelLoader.updateProjectilePositions();
		this.levelLoader.handleCollisions();
		this.repaint();

	}

}
