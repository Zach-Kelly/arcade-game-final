import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameTimeListener implements ActionListener {

	private LevelComponent levelComponent;

	/**
	 * Constructs a new GameTimeListener
	 * 
	 * @param levelComponent
	 */
	public GameTimeListener(LevelComponent levelComponent) {
		this.levelComponent = levelComponent;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.levelComponent.updateScreen();
	}

}
