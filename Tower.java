import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;

public class Tower implements GameObject {
    Shape shape;
    Color color;
    float range;

    public Tower(Shape shape, Color color) {
	this.shape = shape;
	this.color = color;
    }

    public void update(int delta) {
    }
    
    public void render(Graphics g) {
	g.setColor(color);
	g.fill(shape);
    }
}
