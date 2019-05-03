import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardListener implements KeyListener {
	private Hero hero;
	
	public KeyboardListener(Hero theGuy) {
		// TODO Auto-generated constructor stub
		this.hero = theGuy;
		
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getKeyCode()==KeyEvent.VK_LEFT) {
			this.hero.handleLeft(true);
			System.out.println("Left Pressed");
		}
		if (arg0.getKeyCode()==KeyEvent.VK_RIGHT) {
			this.hero.handleRight(true);
			System.out.println("Right Pressed");
		}
		System.out.println("Key Pressed");

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
