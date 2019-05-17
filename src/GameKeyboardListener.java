import java.awt.Polygon;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameKeyboardListener implements KeyListener {

	private static final int SELECTOR_Y_OFFSET = -100;

	private Hero hero;
	private LevelComponent levelComponent;
	private boolean selectorMovable = true;
	private Polygon selector;
	private int selectorPos = -1;

	public GameKeyboardListener(Polygon selector) {

		this.selector = selector;
		int cornerX1 = 50;
		int cornerX2 = 150;
		int cornerY1 = 500;
		int cornerY2 = 525;
		int cornerY3 = 550;
		this.selector.addPoint(cornerX1, cornerY1);
		this.selector.addPoint(cornerX1, cornerY3);
		this.selector.addPoint(cornerX2, cornerY2);

	}

	public void addHero(Hero hero) {
		this.hero = hero;
	}

	public void addLevelComponent(LevelComponent levelComponent) {
		this.levelComponent = levelComponent;
	}

	@Override
	public void keyPressed(KeyEvent arg0) {

		if (!this.levelComponent.isTitleScreen()) {
			if (arg0.getKeyCode() == KeyEvent.VK_U) {
				this.levelComponent.handleLoadLevel(1);
			} else if (arg0.getKeyCode() == KeyEvent.VK_D) {
				this.levelComponent.handleLoadLevel(-1);
			} else {
				heroMovement(arg0);
			}
		} else {
			titleScreenMovement(arg0);
		}

	}

	private void heroMovement(KeyEvent arg0) {

		if (arg0.getKeyCode() == KeyEvent.VK_LEFT) {
			this.hero.handleKeyInteraction("left", 1);
		}
		if (arg0.getKeyCode() == KeyEvent.VK_RIGHT) {
			this.hero.handleKeyInteraction("right", 1);
		}
		if (arg0.getKeyCode() == KeyEvent.VK_UP) {
			this.hero.handleKeyInteraction("up", 1);
		}
		if (arg0.getKeyCode() == KeyEvent.VK_SPACE) {
			this.hero.handleKeyInteraction("shoot", 1);
		}
		if (arg0.getKeyCode() == KeyEvent.VK_P) {
			this.levelComponent.toggleIsPaused();
		}

	}

	private void titleScreenMovement(KeyEvent arg0) {

		if (arg0.getKeyCode() == KeyEvent.VK_SPACE) {
			if (this.selectorPos == -1) {
				this.levelComponent.setTitleScreen(false);
				this.levelComponent.setTransitioning(true);
				this.levelComponent.setTransitionTime();
				Sound.jokermaSound();
			} else {
				this.levelComponent.toggleIsInstructions();
				if (this.levelComponent.isInstructions()) {
					this.selectorMovable = false;
				} else {
					this.selectorMovable = true;
				}
			}
		}
		if (arg0.getKeyCode() == KeyEvent.VK_UP || arg0.getKeyCode() == KeyEvent.VK_DOWN) {
			if (this.selectorMovable) {
				this.moveSelector();
			}
		}

	}

	private void moveSelector() {

		this.selector.translate(0, this.selectorPos * SELECTOR_Y_OFFSET);
		this.selectorPos *= -1;
		this.selectorMovable = false;

	}

	@Override
	public void keyReleased(KeyEvent arg0) {

		if (!this.levelComponent.isTitleScreen()) {
			if (arg0.getKeyCode() == KeyEvent.VK_LEFT) {
				this.hero.handleKeyInteraction("left", 0);
			}
			if (arg0.getKeyCode() == KeyEvent.VK_RIGHT) {
				this.hero.handleKeyInteraction("right", 0);
			}
			if (arg0.getKeyCode() == KeyEvent.VK_UP) {
				this.hero.handleKeyInteraction("up", 0);
			}
			if (arg0.getKeyCode() == KeyEvent.VK_SPACE) {
				this.hero.handleKeyInteraction("shoot", 0);
			}
		} else {
			if (arg0.getKeyCode() == KeyEvent.VK_UP || arg0.getKeyCode() == KeyEvent.VK_DOWN) {
				this.selectorMovable = true;
			}
		}

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
