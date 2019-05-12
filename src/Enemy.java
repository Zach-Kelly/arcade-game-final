import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import javax.swing.JComponent;

public abstract class Enemy extends Entity {

	private static final BasicStroke BUBBLE_STROKE = new BasicStroke(5);
	private static final Color BUBBLE_COLOR = Color.PINK;
	private boolean isTrapped = false;
	private int timeTrapped;
	private double bubbleDiameter;
	private double bubbleXOffset;
	private double bubbleYOffset;
	private Ellipse2D.Double bubble;
	protected Hero hero;

	public Enemy(int posX, int posY, int width, int height, String spritePath, Hero hero) {

		super(posX, posY, width, height, spritePath);
		this.hero = hero;
		double dWidth = (double) this.width;
		double dHeight = (double) this.height;
		this.bubbleDiameter = Math.pow(dWidth * dWidth + dHeight * dHeight, 0.5);
		this.bubbleXOffset = (bubbleDiameter - width) / 2;
		this.bubbleYOffset = (bubbleDiameter - height) / 2;
		bubble = new Ellipse2D.Double(bubbleDiameter, bubbleDiameter, posX, posY);

	}

	public abstract void movementControl();

	@Override
	public void drawOn(Graphics2D g2, JComponent observer) {

		super.drawOn(g2, observer);
		if (this.isTrapped) {
			this.bubble.setFrame(this.posX - this.bubbleXOffset, this.posY - this.bubbleYOffset, this.bubbleDiameter,
					this.bubbleDiameter);
			g2.setStroke(BUBBLE_STROKE);
			g2.setColor(BUBBLE_COLOR);
			g2.draw(this.bubble);
			g2.setStroke(STANDARD_STROKE);
		}

	}

}
