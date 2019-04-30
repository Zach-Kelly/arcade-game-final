import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.Timer;

/**
 * The main class for your arcade game.
 * 
 * You can design your game any way you like, but make the game start
 * by running main here.
 * 
 * Also don't forget to write javadocs for your classes and methods.
 * 
 * @author Zach Kelly and Trey Kline
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		JFrame frame = new JFrame("Bubble Bobble");
		LevelComponent component = new LevelComponent(frame);		
		component.handleLoadLevel("src/Levels/Level_1.txt");
		
		frame.add(component, BorderLayout.CENTER);
		
		//gameFrame.pack();
		frame.setSize(1000, 1000);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//TODO: the timer
//		Timer timer = new Timer(DELAY, advanceListener);
//		timer.start();
		
	}

}
