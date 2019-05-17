import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

public abstract class Entity {

	private static final int PLATFORM = 0;
	private static final int WALL = 1;
	private static final int HITBOX_HEIGHT = 1;
	private static final int SCREEN_SIDE_LENGTH = 1000;

	private Image sprite;
	private double xVelocity = 0;
	private double xVelocityMax = 0;
	private double xDrag = 0;
	private double yVelocity = 0;
	private double yVelocityMax = 0;
	private double gravity = 0;
	private double posX;
	private double posY;
	private double dx = 0;
	private double dy = 0;
	private int height;
	private int width;
	private boolean onGround = false;
	private boolean isDead = false;
	private boolean lastDirectionRight = true;
	private HashMap<String, Integer> keyStates = new HashMap<String, Integer>();
	private Rectangle2D.Double obstacleHitBox;
	private Rectangle2D.Double fullHitBox;
	private long timeTrapped;
	private boolean isEdible;
	private int pointValue;

	/**
	 * Constructs a new Entity
	 * 
	 * @param posX       the starting x coordinate
	 * @param posY       the starting y coordinate
	 * @param width      the hitbox width
	 * @param height     the hitbox height
	 * @param spritePath the sprite file path
	 */
	public Entity(int posX, int posY, int width, int height, String spritePath) {

		ImageIcon icon = new ImageIcon(spritePath);
		sprite = icon.getImage().getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING);
		this.setPosX(posX);
		this.setPosY(posY);
		this.setHeight(height);
		this.setWidth(width);
		this.setObstacleHitBox(new Rectangle2D.Double(posX, posY, width, HITBOX_HEIGHT));
		this.fullHitBox = new Rectangle2D.Double(posX, posY, width, height);
		this.keyStates.put("up", 0);
		this.keyStates.put("left", 0);
		this.keyStates.put("right", 0);
		this.keyStates.put("shoot", 0);

	}

	/**
	 * Sets the values that control how movement works
	 * 
	 * @param xV    x velocity
	 * @param xVMax maximum x velocity
	 * @param xDrag horizontal drag
	 * @param yV    y velocity
	 * @param yVMax maximum y velocity
	 * @param g     gravity
	 */
	public void addMovementValues(double xV, double xVMax, double xDrag, double yV, double yVMax, double g) {

		this.xVelocity = xV;
		this.xVelocityMax = xVMax;
		this.xDrag = xDrag;
		this.yVelocity = yV;
		this.yVelocityMax = yVMax;
		this.gravity = g;

	}

	/**
	 * Checks if the Entity is currently colliding with the given Obstacle
	 * 
	 * @param o the Obstacle to be checked for collision
	 */
	public void checkObstacleCollision(Obstacle o) {

		this.updateHitBox();
		if (o.intersects(this.getObstacleHitBox())) {
			if (o.getSubtype() == PLATFORM) {
				if (this.getDy() >= 0) {
					this.setPosY(o.getMinY() - this.getHeight());
					this.setDy(0);
					this.setOnGround(true);
				}
			}
			if (o.getSubtype() == WALL) {
				if (this.getObstacleHitBox().getCenterX() - o.getCenterX() < 0) {
					this.setPosX(o.getMinX() - this.getWidth());
				} else {
					this.setPosX(o.getMaxX());
				}
			}
			this.updateHitBox();
		}

	}

	/**
	 * Updates the Entity position
	 */
	public void updatePosition() {

		updateHorizontalPosition();
		updateVerticalPosition();
		checkLevelBoundaries();

	}

	/**
	 * Updates the Entity verical position
	 */
	private void updateVerticalPosition() {

		if (this.isOnGround()) {
			this.setDy(this.getDy() + this.keyStates.get("up") * this.yVelocity);
			this.setOnGround(false);
		}
		if (this.getDy() > this.yVelocityMax) {
			this.setDy(this.yVelocityMax);
		}
		this.setPosY(this.getPosY() + this.getDy());
		this.setDy(this.getDy() + this.gravity);

	}

	/**
	 * Updates the Entity horizontal position
	 */
	private void updateHorizontalPosition() {

		this.setDx(this.getDx() + this.xVelocity * (this.keyStates.get("right") - this.keyStates.get("left")));
		if (this.getDx() > 0) {
			if (this.getDx() > this.xVelocityMax) {
				this.setDx(this.xVelocityMax);
			}
			this.setLastDirectionRight(true);
			this.setPosX(this.getPosX() + this.getDx());
			this.setDx(Math.max(0, this.getDx() - this.xDrag));
		} else if (this.getDx() < 0) {
			if (this.getDx() < -this.xVelocityMax) {
				this.setDx(-this.xVelocityMax);
			}
			this.setLastDirectionRight(false);
			this.setPosX(this.getPosX() + this.getDx());
			this.setDx(Math.min(0, this.getDx() + this.xDrag));
		}

	}

	/**
	 * Updates the hitbox position to be the Entity positioin
	 */
	protected void updateHitBox() {

		int hitBoxY = (int) (this.getPosY() + this.getHeight() - HITBOX_HEIGHT);
		this.getObstacleHitBox().setRect(this.getPosX(), hitBoxY, this.getObstacleHitBox().getWidth(), HITBOX_HEIGHT);
		this.fullHitBox.setFrame(this.getPosX(), this.getPosY(), this.getWidth(), this.getHeight());

	}

	public Rectangle2D.Double getFullHitBox() {
		return fullHitBox;
	}

	public void markForDeath() {
		this.setDead(true);
	}

	public boolean isDead() {
		return isDead;
	}

	public void drawOn(Graphics2D g2, JComponent observer) {
		g2.drawImage(sprite, (int) this.getPosX(), (int) this.getPosY(), observer);
	}

	public void handleKeyInteraction(String key, int state) {
		this.keyStates.put(key, state);
	}

	public int getKeyState(String key) {
		return this.keyStates.get(key);
	}

	/**
	 * Makes the Entity loop around the stage
	 */
	public void checkLevelBoundaries() {

		if (this.getPosX() > SCREEN_SIDE_LENGTH) {
			this.setPosX(1 - this.getWidth());
		}
		if (this.getPosX() < 1 - this.getWidth()) {
			this.setPosX(SCREEN_SIDE_LENGTH - 1);
		}

		if (this.getPosY() < 1 - this.getHeight()) {
			this.setPosY(SCREEN_SIDE_LENGTH - 1);
		}
		if (this.getPosY() > SCREEN_SIDE_LENGTH) {
			this.setPosY(1 - this.getHeight());
		}

	}

	public double getPosX() {
		return posX;
	}

	public void setPosX(double posX) {
		this.posX = posX;
	}

	public double getPosY() {
		return posY;
	}

	public void setPosY(double posY) {
		this.posY = posY;
	}

	public double getDy() {
		return dy;
	}

	public void setDy(double dy) {
		this.dy = dy;
	}

	public double getDx() {
		return dx;
	}

	public void setDx(double dx) {
		this.dx = dx;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}

	public boolean isOnGround() {
		return onGround;
	}

	public void setOnGround(boolean onGround) {
		this.onGround = onGround;
	}

	public boolean isLastDirectionRight() {
		return lastDirectionRight;
	}

	public void setLastDirectionRight(boolean lastDirectionRight) {
		this.lastDirectionRight = lastDirectionRight;
	}

	public Rectangle2D.Double getObstacleHitBox() {
		return obstacleHitBox;
	}

	public void setObstacleHitBox(Rectangle2D.Double obstacleHitBox) {
		this.obstacleHitBox = obstacleHitBox;
	}

	public boolean isEdible() {
		return isEdible;
	}

	public void setEdible(boolean isEdible) {
		this.isEdible = isEdible;
	}

	public int getPointValue() {
		return pointValue;
	}

	public void setPointValue(int pointValue) {
		this.pointValue = pointValue;
	}

	public long getTimeTrapped() {
		return timeTrapped;
	}

	public void setTimeTrapped(long timeTrapped) {
		this.timeTrapped = timeTrapped;
	}

}
