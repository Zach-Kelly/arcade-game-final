import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

//TODO: screen wrap around
public abstract class Entity {

	private static final int PLATFORM = 0;
	private static final int WALL = 1;
	private static final int HITBOX_HEIGHT = 1;
	protected static final BasicStroke STANDARD_STROKE = new BasicStroke();

	protected double posX;
	protected double posY;
	private double dx = 0;
	protected double dy = 0;
	protected int height;
	protected int width;
	protected boolean onGround = false;
	private boolean isDead = false;
	protected boolean lastDirectionRight = true;

	private double xVelocity = 0;
	private double xVelocityMax = 0;
	private double xDrag = 0;
	private double yVelocity = 0;
	private double yVelocityMax = 0;
	private double gravity = 0;

	protected HashMap<String, Integer> keyStates = new HashMap<String, Integer>();
	protected Rectangle2D.Double obstacleHitBox;
	private Rectangle2D.Double fullHitBox;
	protected ArrayList<Entity> entities;
	private Image sprite;

	public Entity(int posX, int posY, int width, int height, String spritePath, ArrayList<Entity> entities) {

		ImageIcon icon = new ImageIcon(spritePath);
		sprite = icon.getImage().getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING);
		this.posX = posX;
		this.posY = posY;
		this.height = height;
		this.width = width;
		this.obstacleHitBox = new Rectangle2D.Double(posX, posY, width, HITBOX_HEIGHT);
		this.fullHitBox = new Rectangle2D.Double(posX, posY, width, height);
		this.keyStates.put("up", 0);
		this.keyStates.put("left", 0);
		this.keyStates.put("right", 0);
		this.keyStates.put("shoot", 0);
		this.entities = entities;

	}

	public void addMovementValues(double xV, double xVMax, double xDrag, double yV, double yVMax, double g) {

		this.xVelocity = xV;
		this.xVelocityMax = xVMax;
		this.xDrag = xDrag;
		this.yVelocity = yV;
		this.yVelocityMax = yVMax;
		this.gravity = g;

	}

	// TODO: make this an interface
	public abstract void shootProjectile();

	public void checkObstacleCollision(Obstacle o) {

		this.updateHitBox();
		if (o.intersects(this.obstacleHitBox)) {
			if (o.getSubtype() == PLATFORM) {
				if (this.dy >= 0) {
					this.posY = o.getMinY() - this.height;
					this.dy = 0;
					this.onGround = true;
				}
			}
			if (o.getSubtype() == WALL) {
				if (this.obstacleHitBox.getCenterX() - o.getCenterX() < 0) {
					this.posX = o.getMinX() - this.width;
				} else {
					this.posX = o.getMaxX();
				}
			}
			this.updateHitBox();
		}

	}

	public void updatePosition() {

		updateHorizontalPosition();
		updateVerticalPosition();

	}

	protected void updateVerticalPosition() {

		if (this.onGround) {
			this.dy += this.keyStates.get("up") * this.yVelocity;
			this.onGround = false;
		}
		if (this.dy > this.yVelocityMax) {
			this.dy = this.yVelocityMax;
		}
		this.posY = this.posY + this.dy;
		this.dy += this.gravity;

	}

	protected void updateHorizontalPosition() {

		this.dx += this.xVelocity * (this.keyStates.get("right") - this.keyStates.get("left"));
		if (this.dx > 0) {
			if (this.dx > this.xVelocityMax) {
				this.dx = this.xVelocityMax;
			}
			this.lastDirectionRight = true;
			this.posX += this.dx;
			this.dx = Math.max(0, this.dx - this.xDrag);
		} else if (this.dx < 0) {
			if (this.dx < -this.xVelocityMax) {
				this.dx = -this.xVelocityMax;
			}
			this.lastDirectionRight = false;
			this.posX += this.dx;
			this.dx = Math.min(0, this.dx + this.xDrag);
		}

	}

	protected void updateHitBox() {

		int hitBoxY = (int) (this.posY + this.height - HITBOX_HEIGHT);
		this.obstacleHitBox.setRect(this.posX, hitBoxY, this.obstacleHitBox.getWidth(), HITBOX_HEIGHT);

	}

	public Rectangle2D.Double getFullHitBox() {
		return fullHitBox;
	}

	public void markForDeath() {
		this.isDead = true;
	}

	public boolean isDead() {
		return isDead;
	}

	public void drawOn(Graphics2D g2, JComponent observer) {
		g2.drawImage(sprite, (int) this.posX, (int) this.posY, observer);
	}

	public void handleKeyInteraction(String key, int state) {
		this.keyStates.put(key, state);
	}

}
