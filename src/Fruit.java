
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

	/**
	 * Constructs a new Fruit
	 * 
	 * @param posX the starting x coordinate
	 * @param posY the starting y coordinate
	 */
	public Fruit(int posX, int posY) {

		super(posX, posY, WIDTH, HEIGHT, SPRITE_PATH);
		addMovementValues(X_VELOCITY, X_VELOCITY_MAX, X_DRAG, Y_VELOCITY, Y_VELOCITY_MAX, GRAVITY);
		this.setEdible(true);
		this.setPointValue(100);

	}

	@Override
	public void markForDeath() {

		Sound.mopSound();
		this.setDead(true);

	}

}
