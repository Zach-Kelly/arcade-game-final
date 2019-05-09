import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Shooter extends Enemy {
	public static final int SHOOTER_WIDTH=100;
	public static final int SHOOTER_HEIGHT=100;
	private static final String SPRITE_PATH = "src/Sprites/fortniteburger.png";
	private static final double X_VELOCITY = 0.2;
	private static final double X_VELOCITY_MAX = 6;
	private static final double X_DRAG = 0.1;
	private static final double Y_VELOCITY = -6;
	private static final double Y_VELOCITY_MAX = 7;
	private static final double GRAVITY = 0.1;
	
	private boolean canShoot;
	private int timeOfLastShot;
	

	public Shooter(int posX, int posY, Hero hero, ArrayList<Entity> entity) {
		super(posX, posY, SHOOTER_WIDTH, SHOOTER_HEIGHT, SPRITE_PATH, hero, entity);
		addMovementValues(X_VELOCITY, X_VELOCITY_MAX, X_DRAG, Y_VELOCITY, Y_VELOCITY_MAX, GRAVITY);
		this.canShoot = true;
		this.timeOfLastShot = 0;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void movementControl() {
		// TODO Auto-generated method stub
		Point2D.Double heroPosition = this.hero.getPosition();
		double xOffset = heroPosition.x - this.posX;
		double yOffset = heroPosition.y - this.posY;
		horizontalMovement(xOffset);
		verticalMovement(yOffset);
		
	}
	
	private void horizontalMovement(double xOffset) {

		if (Math.abs(xOffset) < 400) {
			handleKeyInteraction("left", 0);
			handleKeyInteraction("right", 0);
		} 
		else if (xOffset > 0) {
			handleKeyInteraction("right", 1);
			handleKeyInteraction("left", 0);
		} 
		else {
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
	public Projectile shootProjectile() {
		// TODO Auto-generated method stub
		int xOffset = (int) (this.hero.posX - this.posX);
		checkIfCanShoot();
		if(canShoot) {
		
			if (xOffset>0) {
				Burger fortniteBurger = new Burger((int) this.posX, (int) this.posY, -1);
				timeOfLastShot = (int) System.nanoTime();
				return fortniteBurger;
			}
			else {
				Burger fortniteBurger = new Burger((int) this.posX, (int) this.posY, 1);
				timeOfLastShot = (int) System.nanoTime();
				return fortniteBurger;
			}
		}
		return null;
	}
	
	private void checkIfCanShoot() {
		int now = (int) System.nanoTime();
		if (now-timeOfLastShot> 1000) {
			this.canShoot = true;
		}
		else {
			this.canShoot = false;
		}
		
	}
	

	@Override
	public void die() {
		// TODO Auto-generated method stub

	}

}
