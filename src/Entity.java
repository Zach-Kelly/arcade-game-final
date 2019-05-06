import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;


public abstract class Entity {
	
	protected int posX;
	protected int posY;
	protected int height;
	protected int width;
	protected Rectangle hitBox;
	protected Color hitBoxColor;
	protected int dx;
	protected int dy;
	protected boolean onGround;
	
	public Entity(int posX, int posY, int height, int width, Color color) {
		
		this.posX = posX;
		this.posY = posY;
		this.height = height;
		this.width = width;
		this.hitBox = new Rectangle(posX, posY, height, width);
		this.hitBoxColor = color;
		this.onGround = false;
		
	}
	
	public abstract void checkObstacleCollision(Obstacle o);
	
	public abstract void updatePosition();
	
	public void die() {
		//TODO: This class
	}
	
	public void drawOn(Graphics2D g2) {
		
		g2.setColor(hitBoxColor);
		g2.fill(hitBox);
		g2.draw(hitBox);
		
	}
		

}
