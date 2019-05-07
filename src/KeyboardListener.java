import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardListener implements KeyListener {
	private Hero hero;
	private LevelComponent levelComponent;

	public void addHero(Hero hero) {
		this.hero = hero;
	}

	public void addLevelComponent(LevelComponent levelComponent) {
		this.levelComponent = levelComponent;
	}

	@Override
	public void keyPressed(KeyEvent arg0) {

		if (arg0.getKeyCode() == KeyEvent.VK_U) {
			this.levelComponent.handleLoadLevel(1);
		} else if (arg0.getKeyCode() == KeyEvent.VK_D) {
			this.levelComponent.handleLoadLevel(-1);
		} else {
			if (arg0.getKeyCode() == KeyEvent.VK_LEFT) {
				this.hero.handleKeyInteraction("left", 1);
			}
			if (arg0.getKeyCode() == KeyEvent.VK_RIGHT) {
				this.hero.handleKeyInteraction("right", 1);
			}
			if (arg0.getKeyCode() == KeyEvent.VK_UP) {
				this.hero.handleKeyInteraction("up", 1);
			}
		}

	}

	@Override
	public void keyReleased(KeyEvent arg0) {

		if (arg0.getKeyCode() == KeyEvent.VK_LEFT) {
			this.hero.handleKeyInteraction("left", 0);
		}
		if (arg0.getKeyCode() == KeyEvent.VK_RIGHT) {
			this.hero.handleKeyInteraction("right", 0);
		}
		if (arg0.getKeyCode() == KeyEvent.VK_UP) {
			this.hero.handleKeyInteraction("up", 0);
		}

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
