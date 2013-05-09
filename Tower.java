import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;

public class Tower implements GameObject {
    Shape shape;
    Color color;
    float range;

    // WEAPON SYSTEMS ONLINE.
    Enemy target;
    int damage;
    int counter;
    int rateOfFire; // ms
    int laserDuration;
    int durationCounter;

    public Tower(Shape shape, Color color) {
	this.shape = shape;
	this.color = color;
	range = 150.0f;
	damage = 34;
	counter = rateOfFire = 1500;
	laserDuration = 500;
    }

    public void update(int delta) {
	if (target == null)
	    return;

	if (target.getHp() <= 0) {
	    target = null;
	    return;
	}

	if (!targetInRange(target)) {
	    target = null;
	    return;
	}

	counter += delta;
	if (rateOfFire <= counter) {
	    counter = 0;
	    target.setHp(target.getHp() - damage);
	}
    }
    
    public void render(Graphics g) {
	g.setColor(color);
	g.fill(shape);

	if (target == null)
	    return;
	g.setColor(Color.magenta);
	g.draw(new Line(shape.getCenterX(), shape.getCenterY(), target.getShape().getCenterX(), target.getShape().getCenterY()));
    }

    public void setTarget(Enemy target) {
	this.target = target;
    }

    public float getRange() {
	return range;
    }

    public Shape getShape() {
	return shape;
    }

    public boolean hasTarget() {
	return target != null;
    }

    public boolean targetInRange(Enemy enemy) {
	Vector2f enemyCenter = new Vector2f(enemy.getShape().getCenterX(), enemy.getShape().getCenterY());
	Vector2f towerCenter = new Vector2f(shape.getCenterX(), shape.getCenterY());
	Vector2f vector = new Vector2f(towerCenter.getX() - enemyCenter.getX(), towerCenter.getY() - enemyCenter.getY());
	float vectorLength = (float)Math.sqrt(Math.pow(vector.getX(), 2) + Math.pow(vector.getY(), 2));
	return vectorLength <= range;
    }
}
