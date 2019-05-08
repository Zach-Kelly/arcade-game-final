import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;

@SuppressWarnings("serial")
public class Obstacle extends Rectangle2D.Double {

	private int type;
	private Color fill;
	private Color outline;
	private static final float OUTLINE_THICKNESS = 1;

	public Obstacle(double x, double y, double width, double height, int type, Color fill, Color outline) {

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

	public int getSubtype() {
		return type;
	}

}
