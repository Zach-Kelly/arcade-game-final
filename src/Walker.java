
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

	/**
	 * Constructs a new Walker
	 * 
	 * @param posX the starting x coordinate
	 * @param posY the starting y coordinate
	 * @param hero the hero
	 */
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
					this.walkDirection = -1;
				} else {
					this.setPosX(o.getMaxX());
					this.walkDirection = 1;
				}
			}
			this.updateHitBox();
		}

	}

}
