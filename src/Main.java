import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.Timer;

/**
 * The main class for your arcade game.
 * 
 * You can design your game any way you like, but make the game start by running
 * main here.
 * 
 * Also don't forget to write javadocs for your classes and methods.
 * 
 * @author Zach Kelly and Trey Kline
 *
 */
//TODO: organize everything
//TODO: add a pause between level transitions
public class Main {

	private static final int DELAY = 10;
	
	public static void main(String[] args) {

		JFrame frame = new JFrame("Bubble Bobble");
		LevelComponent component = new LevelComponent(frame);
		frame.add(component, BorderLayout.CENTER);

		component.setFocusable(true);
		frame.setSize(1000, 1000);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		component.handleLoadLevel(0);
		Timer timer = new Timer(DELAY, new GameTimeListener(component));
		timer.start();

	}

}
