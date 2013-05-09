import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;

public class Tower implements GameObject {
    Shape shape;
    Color color;
    float range;
    Line laser;
    int damage;
    int counter;
    int rateOfFire; // ms

    public Tower(Shape shape, Color color) {
	this.shape = shape;
	this.color = color;
	range = 150.0f;
	damage = 1;
	rateOfFire = 500;
    }

    public void update(int delta) {
    }
    
    public void render(Graphics g) {
	g.setColor(color);
	g.fill(shape);
	if (laser == null)
	    return;
	g.setColor(Color.magenta);
	g.draw(laser);
    }

    public void fire(Enemy target, int delta) {
	counter += delta;
	laser = new Line(shape.getCenterX(), shape.getCenterY(), target.getShape().getCenterX(), target.getShape().getCenterY());
	if (counter > rateOfFire) {
	    counter = 0;
	    target.setHp(target.getHp() - damage);
	}
    }

    public float getRange() {
	return range;
    }

    public Shape getShape() {
	return shape;
    }
}
