import java.awt.geom.Rectangle2D;

//TODO: hitbox scale might be unneeded
//TODO: draw behind the hero/monsters
public abstract class Projectile extends Entity {

	private static final double X_VELOCITY = 2;
	private static final double X_VELOCITY_MAX = 10;
	private static final double X_DRAG = 0.5;
	private static final double Y_VELOCITY = 0;
	private static final double Y_VELOCITY_MAX = 0;
	private static final double GRAVITY = 0;

	private double hitBoxScale;
	private double hitBoxXOffset;
	private double hitBoxYOffset;
	private int initialDirection;

	public Projectile(int startPosX, int startPosY, int width, int height, String spritePath, double hitBoxScale,
			int initialDirection) {

		super(startPosX, startPosY, width, height, spritePath);
		addMovementValues(X_VELOCITY, X_VELOCITY_MAX, X_DRAG, Y_VELOCITY, Y_VELOCITY_MAX, GRAVITY);
		this.hitBoxScale = hitBoxScale;
		this.hitBoxXOffset = width * (1 - hitBoxScale);
		this.hitBoxYOffset = height * (1 - hitBoxScale);
		this.initialDirection = initialDirection;

	}

	@Override
	protected void updateHitBox() {

		super.updateHitBox();
		double adjustedX = posX + this.hitBoxXOffset;
		double adjustedY = posY + this.hitBoxYOffset;
		double adjustedWidth = width * hitBoxScale;
		double adjustedHeight = height * hitBoxScale;
		this.obstacleHitBox = new Rectangle2D.Double(adjustedX, adjustedY, adjustedWidth, adjustedHeight);

	}

	@Override
	public void updatePosition() {

		super.updatePosition();
		boolean offScreenX = Math.abs(this.posX - 500 - this.width / 2) > 500 + this.width;
		boolean offScreenY = Math.abs(this.posY - 500 - this.height / 2) > 500 + this.height;
		if (offScreenX || offScreenY) {
			this.markForDeath();
		}
		if (initialDirection > 0) {
			handleKeyInteraction("left", 1);
		} else {
			handleKeyInteraction("right", 1);
		}

	}

	@Override
	public void checkObstacleCollision(Obstacle o) {

		this.updateHitBox();
		if (o.intersects(this.obstacleHitBox)) {
			this.markForDeath();
		}

	}

}
