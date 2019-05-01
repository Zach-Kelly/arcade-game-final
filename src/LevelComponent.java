import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class LevelComponent extends JComponent {
	
	private LevelLoader levelLoader;
	private JFrame frame;
	
	public LevelComponent(JFrame frame) {
		this.levelLoader = new LevelLoader();
		this.frame = frame;
	}
	
	public void handleLoadLevel(String path) {
		this.levelLoader.loadFile(path);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		this.frame.getContentPane().setBackground(this.levelLoader.getBgColor());
		this.levelLoader.drawLevel(g2);
		
	}
	
}
