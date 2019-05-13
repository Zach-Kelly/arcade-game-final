import sun.audio.*;
import javax.swing.*;
import sun.audio.*;
import java.io.*;

public class Sound {

	public Sound() {
		// TODO Auto-generated constructor stub
	}
	
	public static void music() {
		AudioPlayer MGP = AudioPlayer.player;
		AudioData MD;
		AudioStream BGM; 
		ContinuousAudioDataStream loop = null;
		
		try {
			
		File musicFile = new File("src/Music/backgroundmusic.mp3");
		System.out.println("Found File");
		
		FileInputStream inputStream = new FileInputStream(musicFile);
		System.out.println("Made input stream");
		
		BGM = new AudioStream(inputStream);
		System.out.println("Made audio stream");
		
		MD = BGM.getData();
		
		System.out.println("got data");
		loop = new ContinuousAudioDataStream(MD);
		System.out.println("Made loop");
		}
		catch(IOException error){
			System.out.print("File Not Found");
		}
		
		MGP.start(loop);
	}

}
