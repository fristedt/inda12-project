import java.util.ArrayList;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;

public class Enemy implements GameObject {
    Shape shape;
    Color color;
    float velocity;
    ArrayList<Tile> path;

    float destinationX;
    float destinationY;
    destinationX = destinationY = -1;

    public Enemy(Shape shape, Color color, float velocity, ArrayList<Tile> path) {
	this.shape = shape;
	this.color = color;
	this.velocity = velocity;
	this.path = path;
    }

    public void update(int delta) {
	// Chech if destination is set.
	if (destinationX < 0 || destinationY < 0) {
	    setNewDestination();
	}
	// If destination is reached, get the next one.
	if (isDestinationReached()) {
	    setNewDestination();
	}
		    
	// Move toward destination.
	move();
    }

    public void render(Graphics g) {
	g.setColor(color);
	g.fill(shape);
    }
}
