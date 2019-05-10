
public class Burger extends Projectile {
public static final String SPRITE_PATH = "src/Sprites/big_buford.png";
public static final int WIDTH = 100;
public static final int HEIGHT = 100;
public static final double HIT_BOX_SCALE=1;
	public Burger(int startPosX, int startPosY, int initialDirection) {
		super(startPosX, startPosY, WIDTH, HEIGHT, SPRITE_PATH, HIT_BOX_SCALE, initialDirection);
		// TODO Auto-generated constructor stub
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
