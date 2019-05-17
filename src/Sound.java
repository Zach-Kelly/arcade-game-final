import java.io.File;
import javax.sound.sampled.*;
import java.util.concurrent.TimeUnit;

public class Sound {

	/**
	 * Plays the background music
	 */
	public void backgroundMusic() {

		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(new File("src/Music/cowboys.wav"));
			Clip test = AudioSystem.getClip();
			test.open(ais);
			test.loop(Clip.LOOP_CONTINUOUSLY);
			while (!test.isRunning())
				Thread.sleep(1);
			while (test.isRunning())
				Thread.sleep(10);
			test.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		try {
			TimeUnit.SECONDS.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Plays the burger sound
	 */
	public static void burgerSound() {
		basicSound("src/Music/hamburger.wav");
	}

	/**
	 * Plays the jokerma sound
	 */
	public static void jokermaSound() {
		basicSound("src/Music/jokerma.wav");
	}

	/**
	 * Plays the Boss stage 1 sound
	 */
	public static void stage1Sound() {
		basicSound("src/Music/Stage1.wav");
	}

	/**
	 * Plays the boss stage 2 sound
	 */
	public static void stage2Sound() {
		basicSound("src/Music/Stage2.wav");
	}

	/**
	 * Plays the boss stage 3 sound
	 */
	public static void stage3Sound() {
		basicSound("src/Music/Stage3.wav");
	}

	public static void coleDeathSound() {
		basicSound("src/Music/ColeDeath.wav");
	}

	/**
	 * Plays the death sound
	 */
	public static void stinkySound() {
		basicSound("src/Music/stinky.wav");
	}

	/**
	 * Plays the fruit pick up sound
	 */
	public static void mopSound() {
		basicSound("src/Music/mopCollect.wav");
	}

	/**
	 * Plays the given sound file
	 * 
	 * @param soundPath the file path of the sound file
	 */
	private static void basicSound(String soundPath) {

		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(new File(soundPath));
			Clip test = AudioSystem.getClip();
			test.open(ais);
			test.start();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}
