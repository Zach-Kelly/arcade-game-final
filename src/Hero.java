import java.awt.Color;
//TODO: getters and setters
public class Hero extends Entity {
	
	private static final Color HERO_COLOR = Color.RED;
	private static final int GRAVITY = 1;
	private static final int TERMINAL_VELOCITY = 19;
	private KeyboardListener listener;

	public Hero(int startPosX, int startPosY) {
		// TODO Auto-generated constructor stub
		super(startPosX, startPosY, 20, 10, HERO_COLOR);
		this.dx = 0;
		this.dy = 0;
	}
	
	@Override
	public void checkObstacleCollision(Obstacle o) {
		//TODO: fix double jumping
		this.hitBox.setLocation(this.posX, this.posY);
		if(o.intersects(this.hitBox)) {
			if(o.getType() == 0) {
				this.posY = (int) (o.getMinY() - this.hitBox.height);
				this.dy = 0;
			}
			if(o.getType() == 1) {
				if(this.hitBox.getCenterX() - o.getCenterX() < 0) { //left of wall
					this.posX = (int) (o.getMinX() - this.hitBox.width);
				} else {
					this.posX = (int) (o.getMaxX());
				}
			}
		} else {
			this.onGround = false;
		}
		
	}

	@Override
	public void updatePosition() {
		
		this.posX += dx;
		this.dy += GRAVITY;
		if (dy > TERMINAL_VELOCITY) {
			dy = TERMINAL_VELOCITY;
		}
		this.posY = this.posY + dy;
		//this.hitBox.setLocation(this.posX, this.posY);
		
	}
	
	public void handleLeft(boolean keyPressed) {
		if (keyPressed) {
			this.dx=-5;
			System.out.println("To the left");
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
		//System.out.println("Oh I jumped");
	}
	
	public KeyboardListener getListener() {
		return this.listener;
	}
}
