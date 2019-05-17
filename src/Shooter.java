import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Shooter extends Enemy {

	public static final int SHOOTER_WIDTH = 100;
	public static final int SHOOTER_HEIGHT = 100;
	private static final String SPRITE_PATH = "src/Sprites/FortniteBurger.png";
	private static final double X_VELOCITY = 0.2;
	private static final double X_VELOCITY_MAX = 6;
	private static final double X_DRAG = 0.1;
	private static final double Y_VELOCITY = -6;
	private static final double Y_VELOCITY_MAX = 7;
	private static final double GRAVITY = 0.1;
	private static final int SHOOTING_DELAY = 750;
	private static final int HERO_X_OFFSET = 400;
	private static final int HERO_Y_OFFSET = 50;

	private long timeOfLastShot = System.currentTimeMillis();
	private ArrayList<Entity> projectiles;

	/**
	 * Costructs a new shooter
	 * 
	 * @param posX        the starting x coordinate
	 * @param posY        the starting y coordinate
	 * @param hero        the hero
	 * @param projectiles the list of all enemy projectiles
	 */
	public Shooter(int posX, int posY, Hero hero, ArrayList<Entity> projectiles) {

		super(posX, posY, SHOOTER_WIDTH, SHOOTER_HEIGHT, SPRITE_PATH, hero);
		addMovementValues(X_VELOCITY, X_VELOCITY_MAX, X_DRAG, Y_VELOCITY, Y_VELOCITY_MAX, GRAVITY);
		this.projectiles = projectiles;

	}

	@Override
	public void movementControl() {

		shootProjectile();
		Point2D.Double heroPosition = this.getHero().getPosition();
		double xOffset = heroPosition.x - this.getPosX();
		double yOffset = heroPosition.y - this.getPosY();
		horizontalMovement(xOffset);
		verticalMovement(yOffset);

	}

	/**
	 * Controls horizontal movement
	 * 
	 * @param xOffset the distance from the hero needed to change behavior
	 */
	private void horizontalMovement(double xOffset) {

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

	/**
	 * Controls vertical movement
	 * 
	 * @param yOffset the distance from the hero needed to change behavior
	 */
	private void verticalMovement(double yOffset) {

		if (yOffset < 0) {
			handleKeyInteraction("up", 1);
		} else {
			handleKeyInteraction("up", 0);
		}

	}

	/**
	 * Shoots a projectile
	 */
	public void shootProjectile() {

		if (!isTrapped()) {
			int xOffset = (int) (this.getHero().getPosX() - this.getPosX());
			int yOffset = (int) (this.getHero().getPosY() - this.getPosY());
			int direction = 1;
			if (System.currentTimeMillis() - timeOfLastShot > SHOOTING_DELAY) {
				if (xOffset > 0) {
					direction = -1;
				}
				if (Math.abs(yOffset) <= HERO_Y_OFFSET) {
					Sound.burgerSound();
					timeOfLastShot = System.currentTimeMillis();
					this.projectiles.add(new Burger((int) this.getPosX(), (int) this.getPosY(), direction));
				}
			}
		}

	}

}
