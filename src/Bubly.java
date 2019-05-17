
public class Bubly extends Projectile {

	public static final String SPRITE_PATH = "src/Sprites/Bubly.png";
	public static final int WIDTH = 50;
	public static final int HEIGHT = 50;

	/**
	 * Constructs a new Bubly projectile
	 * 
	 * @param startPosX        the starting x coordinate
	 * @param startPosY        the starting y coordinate
	 * @param initialDirection the initial direction (left or right)
	 */
	public Bubly(int startPosX, int startPosY, int initialDirection) {
		super(startPosX, startPosY, WIDTH, HEIGHT, SPRITE_PATH, initialDirection);
	}

}
