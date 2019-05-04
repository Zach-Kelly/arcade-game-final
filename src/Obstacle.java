import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;

@SuppressWarnings("serial")
public class Obstacle extends Rectangle {
	
	private String type;
	private Color fill;
	private Color outline;
	private static final float OUTLINE_THICKNESS = 5;
	
	public Obstacle(int x, int y, int width, int height, String type, Color fill, Color outline) {
		
		super(x, y, width, height);
		this.type = type;
		this.fill = fill;
		this.outline = outline;
		
	}
	
	public void drawOn(Graphics2D g2) {
		Stroke origStroke = g2.getStroke();
		g2.setColor(fill);
		g2.fill(this);	
		g2.setStroke(new BasicStroke(OUTLINE_THICKNESS));
		g2.setColor(this.outline);
		g2.draw(this);
		g2.setStroke(origStroke);
		
	}
	
	public void handleCollision(Entity entity) {
		System.out.println("Checked Collision");
		if (entity.dy>0) { //entity is above
			entity.dy=0;
		}
		else {
			entity.posX = this.x-(this.width+entity.width/2);
			entity.dx=0;
			System.out.println("Stopped Guy");
		}
	}
	
}
