import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;

public class Tower {
    int x, y;
    float width, height, rateOfFire;
    Shape shape;
    Ammo ammo;

    private class Ammo {
	float x, y, velX, velY;
	Shape shape;
	
	public Ammo () {
	}
    }

    public Tower(int x, int y) {
	this.x = x;
	this.y = y;

	width = height = 30;
	rateOfFire = 1.0f;
	shape = new Rectangle(x, y, width, height);
    }

    // Fires a shot at (x, y).
    public void fire(float x, float y) {
	    
    }

    public Shape getShape() {
	return shape;
    }
}
