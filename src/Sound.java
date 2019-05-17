import java.io.File;
import javax.sound.sampled.*;
import java.util.concurrent.TimeUnit;

public class Sound {

// Shows two ways to play sounds successfully in Java program

	public void backgroundMusic() {
		// First way doesn't use files explicitly:
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(new File("src/Music/cowboys.wav"));
			Clip test = AudioSystem.getClip();

			test.open(ais);
			test.loop(Clip.LOOP_CONTINUOUSLY);
			// Need to give sound time to start, then time finish playing, if at end of
			// program:
			while (!test.isRunning())
				Thread.sleep(1);
			while (test.isRunning())
				Thread.sleep(10);
			test.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		// Second way does use files explicitly:

		// Need to give sound time to play, if at end of program:
		try {
			TimeUnit.SECONDS.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void burgerSound() {
		// First way doesn't use files explicitly:
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(new File("src/Music/hamburger.wav"));
			Clip test = AudioSystem.getClip();

			test.open(ais);
			test.start();
			// Need to give sound time to start, then time finish playing, if at end of
			// program:

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		// Second way does use files explicitly:

		// Need to give sound time to play, if at end of program:

	}

	public static void jokermaSound() {

		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(new File("src/Music/jokerma.wav"));
			Clip test = AudioSystem.getClip();
			test.open(ais);
			test.start();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
	
	public static void stage1Sound() {

		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(new File("src/Music/Stage1.wav"));
			Clip test = AudioSystem.getClip();
			test.open(ais);
			test.start();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
	
	public static void stage2Sound() {

		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(new File("src/Music/Stage2.wav"));
			Clip test = AudioSystem.getClip();
			test.open(ais);
			test.start();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
	
	public static void stage3Sound() {

		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(new File("src/Music/Stage3.wav"));
			Clip test = AudioSystem.getClip();
			test.open(ais);
			test.start();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
	
	public static void coleDeathSound() {

		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(new File("src/Music/ColeDeath.wav"));
			Clip test = AudioSystem.getClip();
			test.open(ais);
			test.start();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
	
	public static void stinkySound() {

		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(new File("src/Music/stinky.wav"));
			Clip test = AudioSystem.getClip();
			test.open(ais);
			test.start();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
	
	public static void mopSound() {
		// First way doesn't use files explicitly:
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(new File("src/Music/mopCollect.wav"));
			Clip test = AudioSystem.getClip();

			test.open(ais);
			test.start();
			// Need to give sound time to start, then time finish playing, if at end of
			// program:

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
