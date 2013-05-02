import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;

public class Tower implements GameObject {
    float x, y;
    float width, height, rateOfFire;

    public Tower(float x, float y) {
	this.x = x;
	this.y = y;

	width = height = 30;
	rateOfFire = 1.0f;
    }

    public void update(int delta) {
    }

    public void render(Graphics g) {
	System.out.println("RENDERING TOWER " + this.hashCode());
    }
}
