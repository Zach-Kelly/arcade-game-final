import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import javax.swing.JComponent;

public abstract class Enemy extends Entity {

	private boolean isTrapped;
	private int timeTrapped;
	private double bubbleDiameter;
	private double bubbleXOffset;
	private double bubbleYOffset;
	private Ellipse2D.Double bubble;
	protected Hero hero;
	
	public Enemy(int posX, int posY, int width, int height, String spritePath, Hero hero) {
		
		super(posX, posY, width, height, spritePath);
		this.hero = hero;
		double bubbleDiameter = Math.pow(width * width + height * height, 0.5);
		this.bubbleXOffset = (bubbleDiameter - width) / 2;
		this.bubbleYOffset = (bubbleDiameter - height) / 2;		
		bubble = new Ellipse2D.Double(bubbleDiameter, bubbleDiameter, posX, posY);
		
	}
	
	public abstract void movementControl();
	
	@Override
	public void drawOn(Graphics2D g2, JComponent observer) {
		
		super.drawOn(g2, observer);
		if (this.isTrapped) {
			this.bubble.setFrame(this.posX, this.posY, this.bubbleDiameter, this.bubbleDiameter);
		}
		
	}
	
}
