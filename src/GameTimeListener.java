import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameTimeListener implements ActionListener {
	private LevelComponent levelComponent;
	
	public GameTimeListener(LevelComponent levelComponent) {
		// TODO Auto-generated constructor stub
		this.levelComponent = levelComponent;
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		advanceOneTick();

	}
	
	public void advanceOneTick() {
		//System.out.println("Current time " + System.currentTimeMillis());
		this.levelComponent.drawScreen();
	}

}
