import java.awt.Color;
import java.util.HashMap;

public class Hero extends Entity {

	private static final Color HERO_COLOR = Color.RED;
	private static final double GRAVITY = 1;
	private static final double TERMINAL_VELOCITY = 19;
	private static final double Y_VELOCITY = -30;
	private static final double X_DRAG = 0.5;
	private static final double X_VELOCITY = 2;
	private static final double X_VELOCITY_MAX = 10;
	private KeyboardListener listener;
	private HashMap<String, Integer> keyStates;

	public Hero(int startPosX, int startPosY) {

		super(startPosX, startPosY, 20, 10, HERO_COLOR);
		this.dx = 0;
		this.dy = 0;
		this.keyStates = new HashMap<String, Integer>();
		this.keyStates.put("up", 0);
		this.keyStates.put("left", 0);
		this.keyStates.put("right", 0);

	}

	@Override
	public void checkObstacleCollision(Obstacle o) {

		this.hitBox.setRect(this.posX, this.posY, this.hitBox.getWidth(), this.hitBox.getHeight());
		if (o.intersects(this.hitBox)) {
			if (o.getType() == 0) {
				if (this.dy >= 0) {
					this.posY = o.getMinY() - this.hitBox.getHeight();
					this.dy = 0;
					this.onGround = true;
				}
			}
			if (o.getType() == 1) {
				if (this.hitBox.getCenterX() - o.getCenterX() < 0) { // left of wall
					this.posX = o.getMinX() - this.hitBox.getWidth();
				} else {
					this.posX = o.getMaxX();
				}
			}
		}

	}

	@Override
	public void updatePosition() {

		updateHorizontalPosition();
		updateVerticalPosition();

	}

	private void updateVerticalPosition() {

		if (this.onGround) {
			this.dy += this.keyStates.get("up") * Y_VELOCITY;
			this.onGround = false;
		}
		if (this.dy > TERMINAL_VELOCITY) {
			this.dy = TERMINAL_VELOCITY;
		}
		this.posY = this.posY + this.dy;
		this.dy += GRAVITY;

	}

	private void updateHorizontalPosition() {

		this.dx += X_VELOCITY * (this.keyStates.get("right") - this.keyStates.get("left"));
		if (this.dx > 0) {
			if (this.dx > X_VELOCITY_MAX) {
				this.dx = X_VELOCITY_MAX;
			}
			this.posX += this.dx;
			this.dx = Math.max(0, this.dx - X_DRAG);
		} else if (this.dx < 0) {
			if (this.dx < -X_VELOCITY_MAX) {
				this.dx = -X_VELOCITY_MAX;
			}
			this.posX += this.dx;
			this.dx = Math.min(0, this.dx + X_DRAG);
		}

	}

	public void handleKeyInteraction(String key, int state) {
		this.keyStates.put(key, state);
	}

	public KeyboardListener getListener() {
		return this.listener;
	}

}
