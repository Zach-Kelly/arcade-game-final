import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Hero extends Entity {

	private static final int HERO_WIDTH = 68;
	private static final int HERO_HEIGHT = 92;
	private static final String SPRITE_PATH = "src/Sprites/Hero.png";
	private static final double X_VELOCITY = 2;
	private static final double X_VELOCITY_MAX = 10;
	private static final double X_DRAG = 0.5;
	private static final double Y_VELOCITY = -20;
	private static final double Y_VELOCITY_MAX = 19;
	private static final double GRAVITY = 1;
	
	public Hero(int startPosX, int startPosY, ArrayList<Entity> entities) {
		
		super(startPosX, startPosY, HERO_WIDTH, HERO_HEIGHT, SPRITE_PATH, entities);
		addMovementValues(X_VELOCITY, X_VELOCITY_MAX, X_DRAG, Y_VELOCITY, Y_VELOCITY_MAX, GRAVITY);
		
	}
	
	public Point2D.Double getPosition() {
		return new Point2D.Double(this.posX, this.posY);
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
