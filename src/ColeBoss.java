import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

//TODO: add point value
public class ColeBoss extends Enemy {

	private static final double STAGE_1_X_VELOCITY = 10;
	private static final double STAGE_1_X_VELOCITY_MAX = 10;
	private static final double STAGE_1_X_DRAG = 0.01;
	private static final double STAGE_1_Y_VELOCITY = -6;
	private static final double STAGE_1_Y_VELOCITY_MAX = 7;
	private static final double STAGE_1_GRAVITY = 0.1;
	private static final int STAGE_1_WIDTH = 236;
	private static final int STAGE_1_HEIGHT = 309;
	private static final double STAGE_2_X_VELOCITY = 20;
	private static final double STAGE_2_X_VELOCITY_MAX = 20;
	private static final double STAGE_2_X_DRAG = 0.01;
	private static final double STAGE_2_Y_VELOCITY = -10;
	private static final double STAGE_2_Y_VELOCITY_MAX = 7;
	private static final double STAGE_2_GRAVITY = 0.1;
	private static final int STAGE_2_WIDTH = 152;
	private static final int STAGE_2_HEIGHT = 244;
	private static final double STAGE_3_X_VELOCITY = 0;
	private static final double STAGE_3_X_VELOCITY_MAX = 0;
	private static final double STAGE_3_X_DRAG = 0;
	private static final double STAGE_3_Y_VELOCITY = -30;
	private static final double STAGE_3_Y_VELOCITY_MAX = 20;
	private static final double STAGE_3_GRAVITY = 0.01;
	private static final int STAGE_3_WIDTH = 152;
	private static final int STAGE_3_HEIGHT = 240;
	private static final String SPRITE_PATH_1 = "src/Sprites/ColeStage1.png";
	private static final String SPRITE_PATH_2 = "src/Sprites/ColeStage2.png";
	private static final String SPRITE_PATH_3 = "src/Sprites/ColeStage3.png";
	private static final int MAX_HEALTH = 5;
	private static final int SHOT_DELAY = 500;
	private static final Font WIN_FONT = new Font("", Font.BOLD, 100);
	
	private int health = MAX_HEALTH;
	private int currentStage = 0;
	private Image stage1;
	private Image stage2;
	private Image stage3;
	private Image currentSprite;
	ArrayList<Entity> projectiles;
	private Random random = new Random();
	private long lastTimeShot = System.currentTimeMillis();

	public ColeBoss(int posX, int posY, ArrayList<Entity> projectiles) {

		super(posX, posY, STAGE_1_WIDTH, STAGE_1_HEIGHT, null, null, null);
		addMovementValues(0, 0, 0, 0, STAGE_1_Y_VELOCITY_MAX, STAGE_1_GRAVITY);
		ImageIcon stage1 = new ImageIcon(SPRITE_PATH_1);
		this.stage1 = stage1.getImage().getScaledInstance(STAGE_1_WIDTH, STAGE_1_HEIGHT, Image.SCALE_AREA_AVERAGING);
		ImageIcon stage2 = new ImageIcon(SPRITE_PATH_2);
		this.stage2 = stage2.getImage().getScaledInstance(STAGE_2_WIDTH, STAGE_2_HEIGHT, Image.SCALE_AREA_AVERAGING);
		ImageIcon stage3 = new ImageIcon(SPRITE_PATH_3);
		this.stage3 = stage3.getImage().getScaledInstance(STAGE_3_WIDTH, STAGE_3_HEIGHT, Image.SCALE_AREA_AVERAGING);
		this.currentSprite = this.stage1;
		this.projectiles = projectiles;
		
	}
	
	@Override
	public void updatePosition() {
		if (this.isTrapped) {
			this.isTrapped = false;
			this.health--;
		}
		if (isTrapped) {
			this.bubbleMovement();
		} else {
			this.checkLevelBoundaries();
			super.updatePosition();
			movementControl();
		}
		checkStage();		

	}
	
	private void checkStage() {
		System.out.println();
		System.out.print("stage: ");
		System.out.print(this.currentStage);
		System.out.println();
		if (this.currentStage == 0 && this.health < 0) {
			this.currentStage++;
			this.health = MAX_HEALTH;
			addMovementValues(STAGE_1_X_VELOCITY, STAGE_1_X_VELOCITY_MAX, STAGE_1_X_DRAG, STAGE_1_Y_VELOCITY,
					STAGE_1_Y_VELOCITY_MAX, STAGE_1_GRAVITY);
			Sound.stage1Sound();
			this.keyStates.put("left", 1);
		} else if (this.currentStage == 1 && this.health < 0) {
			this.width = STAGE_2_WIDTH;
			this.height = STAGE_2_HEIGHT;
			this.currentStage++;
			this.health = MAX_HEALTH;
			addMovementValues(STAGE_2_X_VELOCITY, STAGE_2_X_VELOCITY_MAX, STAGE_2_X_DRAG, STAGE_2_Y_VELOCITY,
					STAGE_2_Y_VELOCITY_MAX, STAGE_2_GRAVITY);
			Sound.stage2Sound();
			stop();
			this.keyStates.put("up", 1);
			this.currentSprite = this.stage2;
		} else if (this.currentStage == 2 && this.health < 0) {
			this.width = STAGE_3_WIDTH;
			this.height = STAGE_3_HEIGHT;
			this.currentStage++;
			this.health = MAX_HEALTH;
			addMovementValues(STAGE_3_X_VELOCITY, STAGE_3_X_VELOCITY_MAX, STAGE_3_X_DRAG, STAGE_3_Y_VELOCITY,
					STAGE_3_Y_VELOCITY_MAX, STAGE_3_GRAVITY);
			Sound.stage3Sound();
			stop();
			this.keyStates.put("up", 1);
			this.currentSprite = this.stage3;
		} else if (this.currentStage == 3 && this.health < 0) {
			this.currentStage++;
			Sound.coleDeathSound();
			stop();
		}
		
	}
	
	@Override
	protected void updateHitBox() {
		
		int hitBoxY = (int) (this.posY + this.height - HITBOX_HEIGHT);
		this.obstacleHitBox.setRect(this.posX, hitBoxY, this.width, HITBOX_HEIGHT);
		this.fullHitBox.setFrame(this.posX, this.posY, this.width, this.height);
		
	}
	
	public void stop() {
		this.keyStates.put("up", 0);
		this.keyStates.put("left", 0);
		this.keyStates.put("right", 0);
	}

	@Override
	public void movementControl() {
		
		if (this.currentStage == 1) {
			if (Math.abs(this.dx) < 1) {
				if (this.lastDirectionRight) {
					this.keyStates.put("left", 1);
					this.keyStates.put("right", 0);
				} else {
					this.keyStates.put("left", 0);
					this.keyStates.put("right", 1);
				}
			} else {
				stop();
			}
		} else if (this.currentStage == 2) {
			if (Math.abs(this.dx) < 1) {
				this.dx = this.random.nextDouble() * STAGE_2_X_VELOCITY;
				if (this.lastDirectionRight) {
					this.dx *= -1;
				}
			} else {
				this.keyStates.put("left", 0);
				this.keyStates.put("right", 0);
			}
		} else if (this.currentStage == 3) {
			int adjustedX = (int) this.posX + this.width / 2;
			int adjustedY = (int) this.posY + this.height / 2;
			if (System.currentTimeMillis() >= this.lastTimeShot + SHOT_DELAY) {
				this.projectiles.add(new Burger(adjustedX, adjustedY, this.random.nextInt(2)));
				this.lastTimeShot = System.currentTimeMillis();
			}
		}

	}

	@Override
	public void shootProjectile() {

	}
	
	@Override
	public void drawOn(Graphics2D g2, JComponent observer) {
		
		g2.drawImage(this.currentSprite, (int) this.posX, (int) this.posY, observer);
		g2.draw(this.fullHitBox);
		if (this.currentStage == 4) {
			g2.setColor(Color.RED);
			g2.setFont(WIN_FONT);
			g2.drawString("CONGRATULATIONS!", 200, 400);
			g2.drawString("    YOU WIN!", 200, 600);
		}
		
	}
	
	

}
