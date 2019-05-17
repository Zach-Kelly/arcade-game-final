import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import javax.swing.JComponent;

public abstract class Enemy extends Entity {

	private static final BasicStroke BUBBLE_STROKE = new BasicStroke(5);
	private static final Color BUBBLE_COLOR = Color.PINK;
	private static final int MAX_TIME_TRAPPED = 10000;
	private static final BasicStroke STANDARD_STROKE = new BasicStroke();
	private static final int BUBBLE_VELOCITY = -1;
	private static final int HITBOX_HEIGHT = 1;

	private double bubbleDiameter;
	private double bubbleXOffset;
	private double bubbleYOffset;
	private Ellipse2D.Double bubble;
	private Hero hero;
	private boolean isTrapped = false;

	public Enemy(int posX, int posY, int width, int height, String spritePath, Hero hero) {

		super(posX, posY, width, height, spritePath);
		this.hero = hero;
		double dWidth = (double) this.width;
		double dHeight = (double) this.height;
		this.bubbleDiameter = Math.pow(dWidth * dWidth + dHeight * dHeight, 0.5);
		this.bubbleXOffset = (bubbleDiameter - width) / 2;
		this.bubbleYOffset = (bubbleDiameter - height) / 2;
		bubble = new Ellipse2D.Double(bubbleDiameter, bubbleDiameter, posX, posY);
		this.isEdible = false;

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

	public boolean isTrapped() {
		return isTrapped;
	}

	public void setTrapped(boolean isTrapped) {
		this.isTrapped = isTrapped;
	}

	@Override
	protected void updateHitBox() {

		if (isTrapped) {
			this.obstacleHitBox.setRect(this.posX - bubbleXOffset, this.posY - bubbleYOffset, this.bubbleDiameter,
					this.bubbleDiameter);
			this.fullHitBox.setFrame(this.posX - bubbleXOffset, this.posY - bubbleYOffset, this.bubbleDiameter,
					this.bubbleDiameter);
		} else {
			int hitBoxY = (int) (this.posY + this.height - HITBOX_HEIGHT);
			this.obstacleHitBox.setRect(this.posX, hitBoxY, this.obstacleHitBox.getWidth(), HITBOX_HEIGHT);
			this.fullHitBox.setFrame(this.posX, this.posY, this.width, this.height);
		}

	}

	private void bubbleMovement() {

		checkBubbleTime();
		handleKeyInteraction("up", 1);
		this.dy = BUBBLE_VELOCITY;
		this.posY = this.posY + this.dy;
		if (this.posY < 0) {
			this.posY = 0;
			this.dy = 0;
		}

	}

	private void checkBubbleTime() {

		long now = System.currentTimeMillis();
		if (now - this.timeTrapped >= MAX_TIME_TRAPPED) {
			this.isTrapped = false;
		}

	}

	@Override
	public void updatePosition() {

		if (isTrapped) {
			this.bubbleMovement();
		} else {
			super.updatePosition();
			movementControl();
		}

	}

	public Hero getHero() {
		return hero;
	}

}
