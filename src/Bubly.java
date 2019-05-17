
public class Bubly extends Projectile {

	public static final String SPRITE_PATH = "src/Sprites/Bubly.png";
	public static final int WIDTH = 50;
	public static final int HEIGHT = 50;
	public static final double HIT_BOX_SCALE = 1;

	public Bubly(int startPosX, int startPosY, int initialDirection) {
		super(startPosX, startPosY, WIDTH, HEIGHT, SPRITE_PATH, HIT_BOX_SCALE, initialDirection);
	}
	
}
