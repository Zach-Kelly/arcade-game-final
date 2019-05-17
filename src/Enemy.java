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

	/**
	 * Constructs a new Enemy
	 * 
	 * @param posX       the starting x coordinate
	 * @param posY       the starting y coordinate
	 * @param width      the hitbox width
	 * @param height     the hitbox height
	 * @param spritePath the sprite file path
	 * @param hero       the hero
	 */
	public Enemy(int posX, int posY, int width, int height, String spritePath, Hero hero) {

		super(posX, posY, width, height, spritePath);
		this.hero = hero;
		double dWidth = (double) this.getWidth();
		double dHeight = (double) this.getHeight();
		this.bubbleDiameter = Math.pow(dWidth * dWidth + dHeight * dHeight, 0.5);
		this.bubbleXOffset = (bubbleDiameter - width) / 2;
		this.bubbleYOffset = (bubbleDiameter - height) / 2;
		bubble = new Ellipse2D.Double(bubbleDiameter, bubbleDiameter, posX, posY);
		this.setEdible(false);

	}

	/**
	 * The enemy AI, controls how each enemy moves
	 */
	public abstract void movementControl();

	@Override
	public void drawOn(Graphics2D g2, JComponent observer) {

		super.drawOn(g2, observer);
		if (this.isTrapped) {
			this.bubble.setFrame(this.getPosX() - this.bubbleXOffset, this.getPosY() - this.bubbleYOffset,
					this.bubbleDiameter, this.bubbleDiameter);
			g2.setStroke(BUBBLE_STROKE);
			g2.setColor(BUBBLE_COLOR);
			g2.draw(this.bubble);
			g2.setStroke(STANDARD_STROKE);
		}

	}

	/**
	 * @return if the enemy is currently trapped or not
	 */
	public boolean isTrapped() {
		return isTrapped;
	}

	/**
	 * @param isTrapped the current trapped status of the enemy
	 */
	public void setTrapped(boolean isTrapped) {
		this.isTrapped = isTrapped;
	}

	@Override
	protected void updateHitBox() {

		if (isTrapped) {
			this.getObstacleHitBox().setRect(this.getPosX() - bubbleXOffset, this.getPosY() - bubbleYOffset,
					this.bubbleDiameter, this.bubbleDiameter);
			this.getFullHitBox().setFrame(this.getPosX() - bubbleXOffset, this.getPosY() - bubbleYOffset,
					this.bubbleDiameter, this.bubbleDiameter);
		} else {
			int hitBoxY = (int) (this.getPosY() + this.getHeight() - HITBOX_HEIGHT);
			this.getObstacleHitBox().setRect(this.getPosX(), hitBoxY, this.getObstacleHitBox().getWidth(),
					HITBOX_HEIGHT);
			this.getFullHitBox().setFrame(this.getPosX(), this.getPosY(), this.getWidth(), this.getHeight());
		}

	}

	/**
	 * The movement pattern the enemy should follow if currently trapped
	 */
	private void bubbleMovement() {

		checkBubbleTime();
		handleKeyInteraction("up", 1);
		this.setDy(BUBBLE_VELOCITY);
		this.setPosY(this.getPosY() + this.getDy());
		if (this.getPosY() < 0) {
			this.setPosY(0);
			this.setDy(0);
		}

	}

	/**
	 * Checks if enought time has passed for the enemy to break free of the bubble
	 */
	private void checkBubbleTime() {

		long now = System.currentTimeMillis();
		if (now - this.getTimeTrapped() >= MAX_TIME_TRAPPED) {
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

	/**
	 * @return the hero
	 */
	public Hero getHero() {
		return hero;
	}

}
