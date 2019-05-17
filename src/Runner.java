import java.awt.geom.Point2D;

public class Runner extends Enemy {

	private static final int RUNNER_WIDTH = 67;
	private static final int RUNNER_HEIGHT = 113;
	private static final String SPRITE_PATH = "src/Sprites/Thanos.png";
	private static final double X_VELOCITY = 0.2;
	private static final double X_VELOCITY_MAX = 6;
	private static final double X_DRAG = 0.1;
	private static final double Y_VELOCITY = -6;
	private static final double Y_VELOCITY_MAX = 7;
	private static final double GRAVITY = 0.1;
	private static final int HERO_X_OFFSET = 20;
	private static final int HERO_Y_OFFSET = 0;

	public Runner(int posX, int posY, Hero hero) {

		super(posX, posY, RUNNER_WIDTH, RUNNER_HEIGHT, SPRITE_PATH, hero);
		addMovementValues(X_VELOCITY, X_VELOCITY_MAX, X_DRAG, Y_VELOCITY, Y_VELOCITY_MAX, GRAVITY);

	}

	@Override
	public void movementControl() {

		Point2D.Double heroPosition = this.getHero().getPosition();
		double xOffset = heroPosition.x - this.posX;
		double yOffset = heroPosition.y - this.posY;
		horizontalMovement(xOffset, yOffset);
		verticalMovement(yOffset);

	}

	private void horizontalMovement(double xOffset, double yOffset) {

		if (Math.abs(xOffset) < HERO_X_OFFSET) {
			handleKeyInteraction("left", 0);
			handleKeyInteraction("right", 0);
		} else if (xOffset > 0) {
			handleKeyInteraction("right", 1);
			handleKeyInteraction("left", 0);
		} else {
			handleKeyInteraction("right", 0);
			handleKeyInteraction("left", 1);
		}

	}

	private void verticalMovement(double yOffset) {

		if (yOffset < HERO_Y_OFFSET) {
			handleKeyInteraction("up", 1);
		} else {
			handleKeyInteraction("up", 0);
		}

	}

}
