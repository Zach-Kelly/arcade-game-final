
public class Fruit extends Entity {
	public static final String SPRITE_PATH = "src/Sprites/Mop.png";
	public static final int WIDTH = 50;
	public static final int HEIGHT = 50;
	public static final double HIT_BOX_SCALE = 1;
	private static final double X_VELOCITY = 0;
	private static final double X_VELOCITY_MAX = 0;
	private static final double X_DRAG = 0;
	private static final double Y_VELOCITY = 0;
	private static final double Y_VELOCITY_MAX = 10;
	private static final double GRAVITY = 1;
	
	
	
	public Fruit(int posX, int posY) {
		super(posX, posY, WIDTH, HEIGHT, SPRITE_PATH);
		addMovementValues(X_VELOCITY, X_VELOCITY_MAX, X_DRAG, Y_VELOCITY, Y_VELOCITY_MAX, GRAVITY);
		this.isEdible = true;
		this.pointValue = 100;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void shootProjectile() {
		// TODO Auto-generated method stub

	}
	@Override
	public void updatePosition(){
		this.checkLevelBoundaries();
		super.updatePosition();
	}
	
	@Override
	public void markForDeath() {
		Sound.mopSound();
		this.isDead = true;
		
	}

}
