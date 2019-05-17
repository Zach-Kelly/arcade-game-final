import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class LevelComponent extends JComponent {

	private static final int NUMBER_OF_LEVELS = 19;

	private ArrayList<String> levelList = new ArrayList<String>();
	private int currentLevelIndex = 0;
	private JFrame frame;
	public LevelLoader levelLoader;
	public boolean isPaused;
	public boolean isGameOver;
	private boolean isTransitioning = false;
	private long transitionTime;
	public boolean isTitleScreen = true;
	private Image pauseScreen;
	private Image titleScreen;
	private Image levelTransition;
	private boolean isHeroDead = false;
	private Polygon selector = new Polygon();
	public int selectorPos = -1;
	public boolean selectorMovable = true;
	private Font levelFont;
	private Image deathScreen;
	private int lives = 5;
	private static final Color TEXT_COLOR = Color.MAGENTA;
	private Image gameOver;
	public boolean isInstructions = false;
	private Image instructions;

	public LevelComponent(JFrame frame) {

		this.frame = frame;
		this.levelLoader = new LevelLoader();
		ImageIcon pause = new ImageIcon("src/MiscResources/Bismarck.png");
		this.pauseScreen = pause.getImage();
		ImageIcon instructions = new ImageIcon("src/MiscResources/Instructions.png");
		this.instructions = instructions.getImage();
		ImageIcon gameOver = new ImageIcon("src/MiscResources/GameOver.png");
		this.gameOver = gameOver.getImage();
		ImageIcon title = new ImageIcon("src/MiscResources/TitleScreen.png");
		ImageIcon transition = new ImageIcon("src/MiscResources/LevelTransition.png");
		this.levelTransition = transition.getImage();
		this.titleScreen = title.getImage();
		ImageIcon deathScreen = new ImageIcon("src/MiscResources/deathScreen.png");
		this.deathScreen = deathScreen.getImage();
		this.selector.addPoint(50, 500);
		this.selector.addPoint(50, 550);
		this.selector.addPoint(150, 525);
		String levelPath;
		for (int i = 1; i <= NUMBER_OF_LEVELS; i++) {
			levelPath = "src/Levels/Level_" + Integer.toString(i) + ".txt";
			this.levelList.add(levelPath);
		}
		GameKeyboardListener gameKeyboardListener = new GameKeyboardListener();
		gameKeyboardListener.addLevelComponent(this);
		this.addKeyListener(gameKeyboardListener);
		this.levelLoader.setKeyListener(gameKeyboardListener);
		this.transitionTime = 0;
		this.levelFont = new Font("Level", Font.BOLD, 60);
		new Sound();

	}

	public void moveSelector() {

		this.selector.translate(0, this.selectorPos * -100);
		this.selectorPos *= -1;
		this.selectorMovable = false;

	}

	public KeyListener handleGetKeyListener() {
		return this.levelLoader.getKeyListener();
	}

	public void handleLoadLevel(int indexOffset) {
		if (!this.isTitleScreen) {
			this.isTransitioning = true;
			if (this.isHeroDead) {
				this.lives--;
				Sound.stinkySound();
			} else {
				Sound.jokermaSound();
			}
			this.transitionTime = System.currentTimeMillis();
		}
		this.isPaused = false;
		this.isGameOver = false;
		this.currentLevelIndex += indexOffset;
		this.currentLevelIndex = (this.currentLevelIndex + this.levelList.size()) % this.levelList.size();
		this.levelLoader.loadFile(this.levelList.get(this.currentLevelIndex));
		this.frame.getContentPane().setBackground(this.levelLoader.getBgColor());

	}

	public void updateScreen() {
		if (levelLoader.getKeyState() == 1) {
			handleLoadLevel(1);
		}

		if (!this.isPaused && !this.isTitleScreen && !this.isTransitioning) {
			this.levelLoader.updateEntityActions();
			this.levelLoader.handleCollisions();
		}
		if (this.levelLoader.handleGetIsHeroDead() && this.lives > 0) {
			this.isHeroDead = true;
			handleLoadLevel(0);
		}
		checkTransition();
		checkHeroDeathTransition();
		this.repaint();

	}

	private void checkHeroDeathTransition() {

		if (this.isHeroDead) {
			long now = System.currentTimeMillis();
			if (now - this.transitionTime >= 2500) {
				this.isHeroDead = false;
			}
		}

	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		if (this.lives < 1) {
			g2.drawImage(this.gameOver, 0, 0, this);
		} else if(this.isInstructions) {
			g2.drawImage(this.instructions, 0, 0, this);
		} else {
			if (!this.isTitleScreen && !this.isTransitioning) {
				g2.setColor(TEXT_COLOR);
				Font livesFont = new Font("Score", Font.BOLD, 24);
				g2.setFont(livesFont);
				g2.drawString("Lives: " + Integer.toString(this.lives), 25, 75);
				this.levelLoader.drawObstacles(g2);
				this.levelLoader.drawEntities(g2, this);
				this.levelLoader.drawScore(g2);
				if (this.isPaused && !this.isGameOver) {
					g2.drawImage(this.pauseScreen, 0, 0, this);
				}
			} else if (this.isTitleScreen) {
				g2.drawImage(this.titleScreen, 0, 0, this);
				g2.setColor(Color.BLUE);
				g2.fill(this.selector);
				g2.draw(this.selector);
			} else if (this.isTransitioning && !this.isTitleScreen) {
				if (this.isHeroDead) {
					g2.drawImage(this.deathScreen, 0, 0, this);
				} else {
					g2.drawImage(this.levelTransition, 0, 0, this);
				}
				g2.setColor(Color.WHITE);
				g2.setFont(levelFont);
				g2.drawString("Level: " + (this.currentLevelIndex + 1), 400, 300);
			}
		}

	}

	private void checkTransition() {
		if (this.isTransitioning) {

			long now = System.currentTimeMillis();
			if (now - this.transitionTime >= 2500) {
				this.isTransitioning = false;
			}
		}
	}

	public void setTransitioning(boolean transitioning) {
		this.isTransitioning = transitioning;
	}

	public void setTransitionTime() {
		this.transitionTime = System.currentTimeMillis();
	}
}
