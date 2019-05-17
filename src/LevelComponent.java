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

public class LevelComponent extends JComponent {

	private static final long serialVersionUID = 1L;
	private static final int NUMBER_OF_LEVELS = 19;
	private static final int TRANSITION_TIME = 2500;
	private static final int LEVEL_DISPLAY_X = 400;
	private static final int LEVEL_DISPLAY_Y = 300;
	private static final int LIVES_DISPLAY_X = 25;
	private static final int LIVES_DISPLAY_Y = 75;
	private static final Font LIVES_FONT = new Font("Score", Font.BOLD, 24);
	private static final Font LEVEL_FONT = new Font("Level", Font.BOLD, 60);
	private static final Color TEXT_COLOR = Color.MAGENTA;

	private Image pauseScreen;
	private Image titleScreen;
	private Image levelTransition;
	private Image instructions;
	private Image gameOver;
	private Image deathScreen;
	private boolean isPaused;
	private boolean isTransitioning = false;
	private boolean isTitleScreen = true;
	private boolean isHeroDead = false;
	private boolean isInstructions = false;
	private ArrayList<String> levelList = new ArrayList<String>();
	private JFrame frame;
	private LevelLoader levelLoader = new LevelLoader();
	private long transitionTime = 0;
	private int lives = 5;
	private int currentLevelIndex = 0;
	private Polygon selector = new Polygon();

	/**
	 * Constructs a new LevelComponent
	 * 
	 * @param frame the frame the game is contained within
	 */
	public LevelComponent(JFrame frame) {

		this.frame = frame;
		String levelPath;
		for (int i = 1; i <= NUMBER_OF_LEVELS; i++) {
			levelPath = "src/Levels/Level_" + Integer.toString(i) + ".txt";
			this.levelList.add(levelPath);
		}
		GameKeyboardListener gameKeyboardListener = new GameKeyboardListener(this.selector);
		gameKeyboardListener.addLevelComponent(this);
		this.addKeyListener(gameKeyboardListener);
		this.levelLoader.setKeyListener(gameKeyboardListener);
		addImages();

	}

	public void setTitleScreen(boolean isTitleScreen) {
		this.isTitleScreen = isTitleScreen;
	}

	public boolean isTitleScreen() {
		return isTitleScreen;
	}

	public void toggleIsPaused() {
		this.isPaused = !this.isPaused;
	}

	public void toggleIsInstructions() {
		this.isInstructions = !this.isInstructions;
	}

	public boolean isInstructions() {
		return isInstructions;
	}

	/**
	 * Loads all needed images
	 */
	private void addImages() {

		ImageIcon pause = new ImageIcon("src/MiscResources/Bismarck.png");
		this.pauseScreen = pause.getImage();
		ImageIcon instructions = new ImageIcon("src/MiscResources/Instructions.png");
		this.instructions = instructions.getImage();
		ImageIcon gameOver = new ImageIcon("src/MiscResources/GameOver.png");
		this.gameOver = gameOver.getImage();
		ImageIcon transition = new ImageIcon("src/MiscResources/LevelTransition.png");
		this.levelTransition = transition.getImage();
		ImageIcon title = new ImageIcon("src/MiscResources/TitleScreen.png");
		this.titleScreen = title.getImage();
		ImageIcon deathScreen = new ImageIcon("src/MiscResources/deathScreen.png");
		this.deathScreen = deathScreen.getImage();

	}

	public KeyListener handleGetKeyListener() {
		return this.levelLoader.getKeyListener();
	}

	/**
	 * Loads a new level
	 * 
	 * @param indexOffset the offset of the desired level from the current level
	 */
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
		this.currentLevelIndex += indexOffset;
		this.currentLevelIndex = (this.currentLevelIndex + this.levelList.size()) % this.levelList.size();
		this.levelLoader.loadFile(this.levelList.get(this.currentLevelIndex));
		this.frame.getContentPane().setBackground(this.levelLoader.getBgColor());

	}

	/**
	 * Updates all movement, collision, painting, etc.
	 */
	public void updateScreen() {

		if (levelLoader.getUKeyState() == 1) {
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
		this.repaint();

	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		if (this.lives < 1) {
			g2.drawImage(this.gameOver, 0, 0, this);
		} else if (this.isInstructions) {
			g2.drawImage(this.instructions, 0, 0, this);
		} else {
			if (this.isTitleScreen) {
				g2.drawImage(this.titleScreen, 0, 0, this);
				g2.setColor(Color.BLUE);
				g2.fill(this.selector);
				g2.draw(this.selector);
			} else {
				if (!this.isTransitioning) {
					g2.setColor(TEXT_COLOR);
					g2.setFont(LIVES_FONT);
					g2.drawString("Lives: " + Integer.toString(this.lives), LIVES_DISPLAY_X, LIVES_DISPLAY_Y);
					this.levelLoader.drawObstacles(g2);
					this.levelLoader.drawEntities(g2, this);
					this.levelLoader.drawScore(g2);
					if (this.isPaused) {
						g2.drawImage(this.pauseScreen, 0, 0, this);
					}
				} else {
					if (this.isHeroDead) {
						g2.drawImage(this.deathScreen, 0, 0, this);
					} else {
						g2.drawImage(this.levelTransition, 0, 0, this);
					}
					g2.setColor(Color.WHITE);
					g2.setFont(LEVEL_FONT);
					g2.drawString("Level: " + (this.currentLevelIndex + 1), LEVEL_DISPLAY_X, LEVEL_DISPLAY_Y);
				}
			}
		}

	}

	/**
	 * Checks for a transition between levels
	 */
	private void checkTransition() {

		if (this.isTransitioning || this.isHeroDead) {
			if (System.currentTimeMillis() - this.transitionTime >= TRANSITION_TIME) {
				this.isTransitioning = false;
				this.isHeroDead = false;
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
