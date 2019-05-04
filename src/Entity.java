import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;


public abstract class Entity {
	protected int posX;
	protected int posY;
	protected int height;
	protected int width;
	protected Rectangle hitBox;
	protected Color hitBoxColor;
	protected int dx;
	protected int dy;
	
	public Entity(int posX, int posY, int height, int width, Color color) {
		// TODO Auto-generated constructor stub
		this.posX = posX;
		this.posY = posY;
		this.height = height;
		this.width = width;
		this.hitBox = new Rectangle(posX, posY, height, width);
		this.hitBoxColor = color;
	}
	
	public abstract void checkForCollision();
	
	public abstract void updatePosition();
	
	public void die() {
		//TODO: This class
	}
	
	public void spawn(Graphics2D g2) {
		this.hitBox = new Rectangle(posX, posY, height, width);
		g2.setColor(hitBoxColor);
		g2.fill(hitBox);
		g2.draw(hitBox);
		
	}
		

}
