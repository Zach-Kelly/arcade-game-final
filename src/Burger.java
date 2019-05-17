
public class Burger extends Projectile {

	public static final String SPRITE_PATH = "src/Sprites/BigBuford.png";
	public static final int WIDTH = 60;
	public static final int HEIGHT = 60;
	public static final double HIT_BOX_SCALE = 1;

	public Burger(int startPosX, int startPosY, int initialDirection) {
		super(startPosX, startPosY, WIDTH, HEIGHT, SPRITE_PATH, HIT_BOX_SCALE, initialDirection);
	}

}
