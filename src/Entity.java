import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;


public abstract class Entity {
	
	protected double posX;
	protected double posY;
	protected int height;
	protected int width;
	protected Rectangle2D hitBox;
	protected Color hitBoxColor;
	protected double dx;
	protected double dy;
	protected boolean onGround = false;
	
	public Entity(int posX, int posY, int height, int width, Color color) {
		
		this.posX = posX;
		this.posY = posY;
		this.height = height;
		this.width = width;
		this.hitBox = new Rectangle2D.Double(posX, posY, height, width);
		this.hitBoxColor = color;
		
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
