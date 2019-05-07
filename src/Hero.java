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

	public Hero(int startPosX, int startPosY) {
		
		super(startPosX, startPosY, HERO_WIDTH, HERO_HEIGHT, SPRITE_PATH);
		addMovementValues(X_VELOCITY, X_VELOCITY_MAX, X_DRAG, Y_VELOCITY, Y_VELOCITY_MAX, GRAVITY);

	}

}
