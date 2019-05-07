import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

public abstract class Entity {

	private static final int HITBOX_HEIGHT = 1;

	private double posX;
	private double posY;
	private double dx = 0;
	private double dy = 0;
	private int height;
	private int width;
	private boolean onGround = false;

	private double xVelocity = 0;
	private double xVelocityMax = 0;
	private double xDrag = 0;
	private double yVelocity = 0;
	private double yVelocityMax = 0;
	private double gravity = 0;

	private HashMap<String, Integer> keyStates = new HashMap<String, Integer>();
	private Rectangle2D obstacleHitBox;
	private Image sprite;

	public Entity(int posX, int posY, int width, int height, String spritePath) {

		ImageIcon icon = new ImageIcon(spritePath);
		sprite = icon.getImage().getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING);
		this.posX = posX;
		this.posY = posY;
		this.height = height;
		this.width = width;
		this.obstacleHitBox = new Rectangle2D.Double(posX, posY, width, HITBOX_HEIGHT);
		this.keyStates.put("up", 0);
		this.keyStates.put("left", 0);
		this.keyStates.put("right", 0);

	}

	public void addMovementValues(double xV, double xVMax, double xDrag, double yV, double yVMax, double g) {

		this.xVelocity = xV;
		this.xVelocityMax = xVMax;
		this.xDrag = xDrag;
		this.yVelocity = yV;
		this.yVelocityMax = yVMax;
		this.gravity = g;

	}

	public void checkObstacleCollision(Obstacle o) {

		this.updateHitBox();
		if (o.intersects(this.obstacleHitBox)) {
			if (o.getType() == 0) {
				if (this.dy >= 0) {
					this.posY = o.getMinY() - this.height;
					this.dy = 0;
					this.onGround = true;
				}
			}
			if (o.getType() == 1) {
				if (this.obstacleHitBox.getCenterX() - o.getCenterX() < 0) { // left of wall
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

	private void updateVerticalPosition() {

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

	private void updateHorizontalPosition() {

		this.dx += this.xVelocity * (this.keyStates.get("right") - this.keyStates.get("left"));
		if (this.dx > 0) {
			if (this.dx > this.xVelocityMax) {
				this.dx = this.xVelocityMax;
			}
			this.posX += this.dx;
			this.dx = Math.max(0, this.dx - this.xDrag);
		} else if (this.dx < 0) {
			if (this.dx < -this.xVelocityMax) {
				this.dx = -this.xVelocityMax;
			}
			this.posX += this.dx;
			this.dx = Math.min(0, this.dx + this.xDrag);
		}

	}

	public void updateHitBox() {
		int hitBoxY = (int) (this.posY + this.height - HITBOX_HEIGHT);
		this.obstacleHitBox.setRect(this.posX, hitBoxY, this.obstacleHitBox.getWidth(), HITBOX_HEIGHT);
	}

	public void die() {
		// TODO: This class
	}

	public void drawOn(Graphics2D g2, JComponent observer) {
		g2.drawImage(sprite, (int) this.posX, (int) this.posY, observer);
	}

	public void handleKeyInteraction(String key, int state) {
		this.keyStates.put(key, state);
	}

}
