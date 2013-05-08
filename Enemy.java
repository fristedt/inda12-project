import java.util.ArrayList;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;

public class Enemy implements GameObject {
    Shape shape;
    Color color;
    float maxVelocity; // The length of the movement vector.
    ArrayList<Tile> path;
    Tile currentTile;
    int currentIndex;

    public Enemy(Shape shape, Color color, float maxVelocity, ArrayList<Tile> path) {
	this.shape = shape;
	this.color = color;
	this.maxVelocity = maxVelocity;
	this.path = path;
	currentIndex = 0;
	currentTile = path.get(0);
    }

    public void update(int delta) {
	move(delta);
    }

    private void move(int delta) {
	Vector2f position = new Vector2f(shape.getCenterX(), shape.getCenterY());
	
	if (currentTile.contains(position.getX(), position.getY())) {
	    if (currentTile == path.get(path.size() - 1)) 
		return; // Should decrease lives here.
	    currentIndex++;
	    currentTile = path.get(currentIndex);
	}

	Vector2f target = new Vector2f(currentTile.getCenterX(), currentTile.getCenterY());

	Vector2f velocityVector = new Vector2f(target.getX() - position.getX(), target.getY() - position.getY());
	velocityVector = velocityVector.getNormal();
	velocityVector = velocityVector.scale(maxVelocity).scale(delta);

	position = position.add(velocityVector);
	shape.setCenterX(position.getX());
	shape.setCenterY(position.getY());
    }

    public void render(Graphics g) {
	g.setColor(color);
	g.fill(shape);
    }
}
