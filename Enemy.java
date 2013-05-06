import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;

public class Enemy implements GameObject {
    float x, y, velX, velY, width, height;
    Shape shape;

    public Enemy(float x, float y) {
	this.x = x;
	this.y = y;
	width = height = 30;
	shape = new Rectangle(x, y, width, height);
    }

    public void update(int delta) {
	x += velX * delta;
	y += velY * delta;

	// This is probably bad for performance.
	shape = new Rectangle(x, y, width, height);
    }

    public void render(Graphics g) {
	g.setColor(Color.green);
	g.fill(shape);
    }

    public Shape getShape() {
	return shape;
    }
}
