import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

public class ColeBoss extends Enemy {

	private static final double STAGE_1_X_VELOCITY = 15;
	private static final double STAGE_1_X_VELOCITY_MAX = 15;
	private static final double STAGE_1_X_DRAG = 0.1;
	private static final double STAGE_1_Y_VELOCITY = -6;
	private static final double STAGE_1_Y_VELOCITY_MAX = 7;
	private static final double STAGE_1_GRAVITY = 0.1;
	private static final int STAGE_1_WIDTH = 236;
	private static final int STAGE_1_HEIGHT = 309;
	private static final double STAGE_2_X_VELOCITY = 20;
	private static final double STAGE_2_X_VELOCITY_MAX = 20;
	private static final double STAGE_2_X_DRAG = 0.1;
	private static final double STAGE_2_Y_VELOCITY = -10;
	private static final double STAGE_2_Y_VELOCITY_MAX = 7;
	private static final double STAGE_2_GRAVITY = 0.1;
	private static final int STAGE_2_WIDTH = 152;
	private static final int STAGE_2_HEIGHT = 244;
	private static final double STAGE_3_X_VELOCITY = 0;
	private static final double STAGE_3_X_VELOCITY_MAX = 0;
	private static final double STAGE_3_X_DRAG = 0;
	private static final double STAGE_3_Y_VELOCITY = -30;
	private static final double STAGE_3_Y_VELOCITY_MAX = 30;
	private static final double STAGE_3_GRAVITY = 0.2;
	private static final int STAGE_3_WIDTH = 152;
	private static final int STAGE_3_HEIGHT = 240;
	private static final String SPRITE_PATH_1 = "src/Sprites/ColeStage1.png";
	private static final String SPRITE_PATH_2 = "src/Sprites/ColeStage2.png";
	private static final String SPRITE_PATH_3 = "src/Sprites/ColeStage3.png";
	private static final int MAX_HEALTH = 1;
	private static final int SHOT_DELAY = 250;
	private static final Font WIN_FONT = new Font("", Font.BOLD, 80);
	private static final int NUM_FRUIT_DROPS = 20;
	private static final int FRUIT_DROP_DELAY = 500;
	private static final int HITBOX_HEIGHT = 1;
	private static final int OFFSCREEN_X = 2000;
	private static final int MIDSCREEN_X = 450;
	private static final int MIDSCREEN_Y = 500;

	private int health = MAX_HEALTH;
	private int currentStage = 0;
	private Image stage1;
	private Image stage2;
	private Image stage3;
	private Image currentSprite;
	private ArrayList<Entity> projectiles;
	private Random random = new Random();
	private long lastTimeShot = System.currentTimeMillis();
	private ArrayList<Entity> fruit;
	private int currentNumFruitDropped = 0;
	long lastFruitDropped = System.currentTimeMillis();

	/**
	 * Constructs a new ColeBoss
	 * 
	 * @param posX        the starting x coordinate
	 * @param posY        the starting y coordinate
	 * @param projectiles the list of all enemy projectiles
	 * @param fruit       the list of all fruit
	 */
	public ColeBoss(int posX, int posY, ArrayList<Entity> projectiles, ArrayList<Entity> fruit) {

		super(posX, posY, STAGE_1_WIDTH, STAGE_1_HEIGHT, null, null);
		addMovementValues(0, 0, 0, 0, STAGE_1_Y_VELOCITY_MAX, STAGE_1_GRAVITY);
		ImageIcon stage1 = new ImageIcon(SPRITE_PATH_1);
		this.stage1 = stage1.getImage().getScaledInstance(STAGE_1_WIDTH, STAGE_1_HEIGHT, Image.SCALE_AREA_AVERAGING);
		ImageIcon stage2 = new ImageIcon(SPRITE_PATH_2);
		this.stage2 = stage2.getImage().getScaledInstance(STAGE_2_WIDTH, STAGE_2_HEIGHT, Image.SCALE_AREA_AVERAGING);
		ImageIcon stage3 = new ImageIcon(SPRITE_PATH_3);
		this.stage3 = stage3.getImage().getScaledInstance(STAGE_3_WIDTH, STAGE_3_HEIGHT, Image.SCALE_AREA_AVERAGING);
		this.currentSprite = this.stage1;
		this.projectiles = projectiles;
		this.fruit = fruit;

	}

	@Override
	public void updatePosition() {
		if (isTrapped()) {
			setTrapped(false);
			this.health--;
			if (this.currentStage == 4) {
				this.setPosX(MIDSCREEN_X);
				this.setPosY(MIDSCREEN_Y);
				this.setDy(0);
				this.setDx(0);
			}
		}
		super.updatePosition();
		checkStage();

	}

	/**
	 * Controls transitions between stages of the boss
	 */
	private void checkStage() {

		if (this.currentStage == 0 && this.health < 0) {
			stage1Transition();
		} else if (this.currentStage == 1 && this.health < 0) {
			stage2Transition();
		} else if (this.currentStage == 2 && this.health < 0) {
			stage3Transition();
		} else if (this.currentStage == 3 && this.health < 0) {
			this.currentStage++;
			Sound.coleDeathSound();
			stop();
		} else if (this.currentStage == 4) {
			stage4();
		}

	}

	/**
	 * Actions for going to stage 1
	 */
	private void stage1Transition() {

		this.currentStage++;
		this.health = MAX_HEALTH;
		addMovementValues(STAGE_1_X_VELOCITY, STAGE_1_X_VELOCITY_MAX, STAGE_1_X_DRAG, STAGE_1_Y_VELOCITY,
				STAGE_1_Y_VELOCITY_MAX, STAGE_1_GRAVITY);
		Sound.stage1Sound();
		handleKeyInteraction("left", 1);

	}

	/**
	 * Actions for going to stage 2
	 */
	private void stage2Transition() {

		this.setWidth(STAGE_2_WIDTH);
		this.setHeight(STAGE_2_HEIGHT);
		this.currentStage++;
		this.health = MAX_HEALTH;
		addMovementValues(STAGE_2_X_VELOCITY, STAGE_2_X_VELOCITY_MAX, STAGE_2_X_DRAG, STAGE_2_Y_VELOCITY,
				STAGE_2_Y_VELOCITY_MAX, STAGE_2_GRAVITY);
		Sound.stage2Sound();
		stop();
		handleKeyInteraction("up", 1);
		this.currentSprite = this.stage2;

	}

	/**
	 * Actions for going to stage 3
	 */
	private void stage3Transition() {

		this.setWidth(STAGE_3_WIDTH);
		this.setHeight(STAGE_3_HEIGHT);
		this.currentStage++;
		this.health = MAX_HEALTH;
		addMovementValues(STAGE_3_X_VELOCITY, STAGE_3_X_VELOCITY_MAX, STAGE_3_X_DRAG, STAGE_3_Y_VELOCITY,
				STAGE_3_Y_VELOCITY_MAX, STAGE_3_GRAVITY);
		Sound.stage3Sound();
		stop();
		handleKeyInteraction("up", 1);
		this.currentSprite = this.stage3;

	}

	/**
	 * Actions for during stage 4
	 */
	private void stage4() {

		if (System.currentTimeMillis() > this.lastFruitDropped + FRUIT_DROP_DELAY
				&& this.currentNumFruitDropped < NUM_FRUIT_DROPS) {
			this.lastFruitDropped = System.currentTimeMillis();
			int adjustedX = (int) this.getPosX() + this.getWidth() / 2;
			int adjustedY = (int) this.getPosY() + this.getHeight() / 2;
			this.fruit.add(new Fruit(adjustedX, adjustedY));
			this.currentNumFruitDropped++;
		}
		if (this.currentNumFruitDropped == NUM_FRUIT_DROPS) {
			this.setPosX(OFFSCREEN_X);
		}

	}

	@Override
	protected void updateHitBox() {

		int hitBoxY = (int) (this.getPosY() + this.getHeight() - HITBOX_HEIGHT);
		this.getObstacleHitBox().setRect(this.getPosX(), hitBoxY, this.getWidth(), HITBOX_HEIGHT);
		this.getFullHitBox().setFrame(this.getPosX(), this.getPosY(), this.getWidth(), this.getHeight());

	}

	/**
	 * Stops the boss from actively moving
	 */
	public void stop() {
		handleKeyInteraction("up", 0);
		handleKeyInteraction("left", 0);
		handleKeyInteraction("right", 0);
	}

	@Override
	public void movementControl() {

		if (this.currentStage == 1) {
			if (Math.abs(this.getDx()) < 1) {
				if (this.isLastDirectionRight()) {
					handleKeyInteraction("left", 1);
					handleKeyInteraction("right", 0);
				} else {
					handleKeyInteraction("left", 0);
					handleKeyInteraction("right", 1);
				}
			} else {
				stop();
			}
		} else if (this.currentStage == 2) {
			if (Math.abs(this.getDx()) < 1) {
				this.setDx(this.random.nextDouble() * STAGE_2_X_VELOCITY);
				if (this.isLastDirectionRight()) {
					this.setDx(this.getDx() * -1);
				}
			} else {
				handleKeyInteraction("left", 0);
				handleKeyInteraction("right", 0);
			}
		} else if (this.currentStage == 3) {
			int adjustedX = (int) this.getPosX() + this.getWidth() / 2;
			int adjustedY = (int) this.getPosY() + this.getHeight() / 2;
			if (System.currentTimeMillis() >= this.lastTimeShot + SHOT_DELAY) {
				this.projectiles.add(new Burger(adjustedX, adjustedY, this.random.nextInt(2)));
				this.lastTimeShot = System.currentTimeMillis();
			}
		}

	}

	@Override
	public void drawOn(Graphics2D g2, JComponent observer) {

		g2.drawImage(this.currentSprite, (int) this.getPosX(), (int) this.getPosY(), observer);
		if (this.currentStage == 4) {
			g2.setColor(Color.RED);
			g2.setFont(WIN_FONT);
			int winTextX1 = 80;
			int winTextY1 = 400;
			int winTextX2 = 300;
			int winTextY2 = 600;
			g2.drawString("CONGRATULATIONS!", winTextX1, winTextY1);
			g2.drawString("YOU WIN!", winTextX2, winTextY2);
		}

	}

}
