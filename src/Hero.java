import java.awt.Color;

public class Hero extends Entity {
	public static final Color HERO_COLOR = Color.RED;
	protected KeyboardListener listener;

	public Hero(int startPosX, int startPosY) {
		// TODO Auto-generated constructor stub
		super(startPosX, startPosY, 20, 10, HERO_COLOR);
		this.dx = 0;
		this.dy=0;
		this.listener = new KeyboardListener(this);
	}

	@Override
	public void checkForCollision() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updatePosition() {
		// TODO Auto-generated method stub
		this.posX += dx;
		this.posY += dy;
		//System.out.print(posX);
	}
	
	public void handleLeft(boolean keyPressed) {
		if (keyPressed) {
			this.dx=-5;
			//System.out.println("To the left");
		}
		else {
			this.dx = 0;
			//System.out.println("Stop");
		}
	}
	
	public void handleRight(boolean keyPressed) {
		if (keyPressed) {
			this.dx = 5;
			//System.out.println("To the right");
		}
		
		else {
			this.dx = 0;
			//System.out.println("Stop");
		}
	}
	
	public void handleJump() {
		System.out.println("Oh I jumped");
	}
	
	public KeyboardListener getListener() {
		return this.listener;
	}
}
