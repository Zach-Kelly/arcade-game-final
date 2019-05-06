import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardListener implements KeyListener {
	private Hero hero;
	
	public void addHero(Hero hero) {
		this.hero = hero;
		System.out.println("sdfasd");
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
