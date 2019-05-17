import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.JComponent;

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
	private static final int MAX_NUM_PROJECTILES = 3;

	private ArrayList<Projectile> heroProjectiles = new ArrayList<Projectile>();
	private boolean shootReleased = true;

	/**
	 * Constructs a new Hero
	 * 
	 * @param startPosX the starting x coordinate
	 * @param startPosY the starting y coordinate
	 */
	public Hero(int startPosX, int startPosY) {

		super(startPosX, startPosY, HERO_WIDTH, HERO_HEIGHT, SPRITE_PATH);
		addMovementValues(X_VELOCITY, X_VELOCITY_MAX, X_DRAG, Y_VELOCITY, Y_VELOCITY_MAX, GRAVITY);

	}

	/**
	 * @return the Hero position
	 */
	public Point2D.Double getPosition() {
		return new Point2D.Double(this.getPosX(), this.getPosY());
	}

	@Override
	public void updatePosition() {

		if (!this.isDead()) {
			super.updatePosition();
			Projectile p;
			for (int i = this.heroProjectiles.size() - 1; i > -1; i--) {
				p = this.heroProjectiles.get(i);
				if (p.isDead()) {
					this.heroProjectiles.remove(p);
				} else {
					p.updatePosition();
				}
			}
		}

	}

	@Override
	public void drawOn(Graphics2D g2, JComponent observer) {

		if (!this.isDead()) {
			super.drawOn(g2, observer);
			for (Projectile p : this.heroProjectiles) {
				p.drawOn(g2, observer);
			}
		}

	}

	@Override
	public void checkObstacleCollision(Obstacle o) {

		super.checkObstacleCollision(o);
		for (Projectile p : this.heroProjectiles) {
			p.checkObstacleCollision(o);
		}

	}

	/**
	 * Checks if the Hero projectiles are colliding with the given Entity
	 * 
	 * @param e the entity to be checked for collision
	 */
	public void checkHeroProjectileCollision(Enemy e) {

		for (Projectile p : this.heroProjectiles) {
			if (p.getFullHitBox().intersects(e.getFullHitBox())) {
				p.markForDeath();
				e.setTrapped(true);
				e.setTimeTrapped(System.currentTimeMillis());
			}
		}

	}

	/**
	 * Shoots a projectile
	 */
	public void shootProjectile() {

		if (!this.isDead()) {
			if (this.getKeyState("shoot") == 0) {
				this.shootReleased = true;
			}
			if (this.heroProjectiles.size() < MAX_NUM_PROJECTILES && this.shootReleased) {
				int direction = 1;
				if (getKeyState("shoot") == 1) {
					if (this.isLastDirectionRight()) {
						direction = -1;
					}
					this.shootReleased = false;
					this.heroProjectiles.add(new Bubly((int) this.getPosX(), (int) this.getPosY(), direction));
				}
			}
		}

	}

}
