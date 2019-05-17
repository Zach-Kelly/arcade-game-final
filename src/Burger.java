
public class Burger extends Projectile {

	public static final String SPRITE_PATH = "src/Sprites/BigBuford.png";
	public static final int WIDTH = 60;
	public static final int HEIGHT = 60;

	/**
	 * Constructs a new Burger projectile
	 * 
	 * @param startPosX        the starting x coordinate
	 * @param startPosY        the starting y coordinate
	 * @param initialDirection the initial direction (left or right)
	 */
	public Burger(int startPosX, int startPosY, int initialDirection) {
		super(startPosX, startPosY, WIDTH, HEIGHT, SPRITE_PATH, initialDirection);
	}

}
