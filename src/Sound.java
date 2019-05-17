import java.io.File;
import javax.sound.sampled.*;
import java.util.concurrent.TimeUnit;

public class Sound {

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

	public static void burgerSound() {
		basicSound("src/Music/hamburger.wav");
	}

	public static void jokermaSound() {
		basicSound("src/Music/jokerma.wav");
	}

	public static void stage1Sound() {
		basicSound("src/Music/Stage1.wav");
	}

	public static void stage2Sound() {
		basicSound("src/Music/Stage2.wav");
	}

	public static void stage3Sound() {
		basicSound("src/Music/Stage3.wav");
	}

	public static void coleDeathSound() {
		basicSound("src/Music/ColeDeath.wav");
	}

	public static void stinkySound() {
		basicSound("src/Music/stinky.wav");
	}

	public static void mopSound() {
		basicSound("src/Music/mopCollect.wav");
	}

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
