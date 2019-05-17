
public abstract class Projectile extends Entity {

	private static final double X_VELOCITY = 2.5;
	private static final double X_VELOCITY_MAX = 10;
	private static final double X_DRAG = 0.5;
	private static final double Y_VELOCITY = 0;
	private static final double Y_VELOCITY_MAX = 0;
	private static final double GRAVITY = 0;
	private static final int STAGE_WIDTH = 1000;

	private int initialDirection;

	/**
	 * Constructs a new Projectile
	 * 
	 * @param startPosX
	 * @param startPosY
	 * @param width
	 * @param height
	 * @param spritePath
	 * @param initialDirection
	 */
	public Projectile(int startPosX, int startPosY, int width, int height, String spritePath, int initialDirection) {

		super(startPosX, startPosY, width, height, spritePath);
		addMovementValues(X_VELOCITY, X_VELOCITY_MAX, X_DRAG, Y_VELOCITY, Y_VELOCITY_MAX, GRAVITY);
		this.initialDirection = initialDirection;
		this.setEdible(false);
		
	}

	@Override
	public void updatePosition() {

		super.updatePosition();
		boolean offScreenX = Math.abs(this.getPosX() - STAGE_WIDTH / 2 - this.getWidth() / 2) > STAGE_WIDTH / 2 + this.getWidth();
		boolean offScreenY = Math.abs(this.getPosY() - STAGE_WIDTH / 2 - this.getHeight() / 2) > STAGE_WIDTH / 2 + this.getHeight();
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
		if (o.intersects(this.getObstacleHitBox())) {
			this.markForDeath();
		}

	}

}
