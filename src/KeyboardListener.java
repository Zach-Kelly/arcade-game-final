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
		
		//TODO: fix this
		if (arg0.getKeyCode()==KeyEvent.VK_LEFT) {
			this.hero.handleLeft(true);
			//System.out.println("Left Pressed");
		} else if (arg0.getKeyCode()==KeyEvent.VK_RIGHT) {
			this.hero.handleRight(true);
			//System.out.println("Right Pressed");
		} else if (arg0.getKeyCode() == KeyEvent.VK_UP) {
			if (Math.abs((this.hero.dy)) < 6) {//this.hero.onGround) {
				this.hero.dy += -30;
			}
		}
		if (arg0.getKeyCode() == KeyEvent.VK_U) {
			System.out.println("level loaded");
			this.levelComponent.handleLoadLevel(1);
		}
		if (arg0.getKeyCode() == KeyEvent.VK_D) {
			System.out.println("level loaded");
			this.levelComponent.handleLoadLevel(-1);
		}
		//System.out.println("Key Pressed");
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getKeyCode()==KeyEvent.VK_LEFT) {
			this.hero.handleLeft(false);
		}
		if (arg0.getKeyCode()==KeyEvent.VK_RIGHT) {
			this.hero.handleRight(false);
		}

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
