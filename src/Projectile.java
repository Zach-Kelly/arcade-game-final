import java.awt.geom.Rectangle2D;

public abstract class Projectile extends Entity {

	private static final int PROJECTILE_WIDTH = 68;
	private static final int PROJECTILE_HEIGHT = 92;
	private static final String SPRITE_PATH = "src/Sprites/Hero.png";
	private static final double X_VELOCITY = 2;
	private static final double X_VELOCITY_MAX = 10;
	private static final double X_DRAG = 0.5;
	private static final double Y_VELOCITY = -20;
	private static final double Y_VELOCITY_MAX = 19;
	private static final double GRAVITY = 1;

	private double hitBoxScale;
	private double hitBoxXOffset;
	private double hitBoxYOffset;

	public Projectile(int startPosX, int startPosY, int width, int height, String spritePath, double hitBoxScale) {

		super(startPosX, startPosY, PROJECTILE_WIDTH, PROJECTILE_HEIGHT, SPRITE_PATH, null);
		addMovementValues(X_VELOCITY, X_VELOCITY_MAX, X_DRAG, Y_VELOCITY, Y_VELOCITY_MAX, GRAVITY);
		this.hitBoxScale = hitBoxScale;
		this.hitBoxXOffset = width * (1 - hitBoxScale);
		this.hitBoxYOffset = height * (1 - hitBoxScale);

	}

	@Override
	protected void updateHitBox() {

		double adjustedX = posX + this.hitBoxXOffset;
		double adjustedY = posY + this.hitBoxYOffset;
		double adjustedWidth = width * hitBoxScale;
		double adjustedHeight = height * hitBoxScale;
		this.obstacleHitBox = new Rectangle2D.Double(adjustedX, adjustedY, adjustedWidth, adjustedHeight);

	}

}
