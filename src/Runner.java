import java.awt.geom.Point2D;
import java.util.ArrayList;

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

	public Runner(int posX, int posY, Hero hero, ArrayList<Entity> entities) {

		super(posX, posY, RUNNER_WIDTH, RUNNER_HEIGHT, SPRITE_PATH, hero, entities);
		addMovementValues(X_VELOCITY, X_VELOCITY_MAX, X_DRAG, Y_VELOCITY, Y_VELOCITY_MAX, GRAVITY);

	}

	@Override
	public void movementControl() {

		Point2D.Double heroPosition = this.hero.getPosition();
		double xOffset = heroPosition.x - this.posX;
		double yOffset = heroPosition.y - this.posY;
		
		horizontalMovement(xOffset, yOffset);
		verticalMovement(yOffset);

	}
	
	private void horizontalMovement(double xOffset, double yOffset) {
		
		if (Math.abs(xOffset) < 20) {
			handleKeyInteraction("left", 1);
			handleKeyInteraction("right", 1);
		} else if (xOffset > 0) {
			handleKeyInteraction("right", 1);
			handleKeyInteraction("left", 0);
		} else {
			handleKeyInteraction("right", 0);
			handleKeyInteraction("left", 1);
		}
		
	}
	
	private void verticalMovement(double yOffset) {
		
		if (yOffset < 0) {
			handleKeyInteraction("up", 1);
		} else {
			handleKeyInteraction("up", 0);
		}
		
	}

	@Override
	public void updatePosition() {

		super.updatePosition();
		movementControl();

	}

	@Override
	public void die() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void shootProjectile() {
		// TODO Auto-generated method stub
		
	}

}
