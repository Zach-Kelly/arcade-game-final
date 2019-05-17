
public class Walker extends Enemy {

	private static final int WALKER_WIDTH = 67;
	private static final int WALKER_HEIGHT = 113;
	private static final String SPRITE_PATH = "src/Sprites/noob.png";
	private static final double X_VELOCITY = 0.25;
	private static final double X_VELOCITY_MAX = 3;
	private static final double X_DRAG = 0.05;
	private static final double Y_VELOCITY = 0;
	private static final double Y_VELOCITY_MAX = 7;
	private static final double GRAVITY = 0.1;
	private static final int INITIAL_DIRECTION = 1;
	private static final int PLATFORM = 0;
	private static final int WALL = 1;

	private int walkDirection;

	public Walker(int posX, int posY, Hero hero) {

		super(posX, posY, WALKER_WIDTH, WALKER_HEIGHT, SPRITE_PATH, hero);
		addMovementValues(X_VELOCITY, X_VELOCITY_MAX, X_DRAG, Y_VELOCITY, Y_VELOCITY_MAX, GRAVITY);
		this.walkDirection = INITIAL_DIRECTION;

	}

	@Override
	public void movementControl() {

		if (walkDirection > 0) {
			handleKeyInteraction("left", 0);
			handleKeyInteraction("right", 1);
		}
		if (walkDirection < 0) {
			handleKeyInteraction("left", 1);
			handleKeyInteraction("right", 0);
		}

	}

	@Override
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
					this.walkDirection = -1;
				} else {
					this.posX = o.getMaxX();
					this.walkDirection = 1;
				}
			}
			this.updateHitBox();
		}

	}

}
