
public abstract class Projectile extends Entity {

	private static final double X_VELOCITY = 2.5;
	private static final double X_VELOCITY_MAX = 10;
	private static final double X_DRAG = 0.5;
	private static final double Y_VELOCITY = 0;
	private static final double Y_VELOCITY_MAX = 0;
	private static final double GRAVITY = 0;
	private static final int STAGE_WIDTH = 1000;

	private int initialDirection;

	public Projectile(int startPosX, int startPosY, int width, int height, String spritePath, int initialDirection) {

		super(startPosX, startPosY, width, height, spritePath);
		addMovementValues(X_VELOCITY, X_VELOCITY_MAX, X_DRAG, Y_VELOCITY, Y_VELOCITY_MAX, GRAVITY);
		this.initialDirection = initialDirection;
		this.isEdible = false;
		
	}

	@Override
	public void updatePosition() {

		super.updatePosition();
		boolean offScreenX = Math.abs(this.posX - STAGE_WIDTH / 2 - this.width / 2) > STAGE_WIDTH / 2 + this.width;
		boolean offScreenY = Math.abs(this.posY - STAGE_WIDTH / 2 - this.height / 2) > STAGE_WIDTH / 2 + this.height;
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
