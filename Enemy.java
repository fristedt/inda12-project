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
    int hp;
    int maxHp;

    public Enemy(Shape shape, Color color, float maxVelocity, ArrayList<Tile> path) {
	this.shape = shape;
	this.color = color;
	this.maxVelocity = maxVelocity;
	this.path = path;
	currentIndex = 0;
	currentTile = path.get(0);
	maxHp = hp = 100;
    }

    public void update(int delta) {
	move(delta);
    }

    public Shape getShape() {
	return shape;
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

    public void setHp(int hp) {
	this.hp = hp;
    }

    public int getHp() {
	return hp;
    }

    public void render(Graphics g) {
	g.setColor(color);
	g.fill(shape);
	renderHpBar(g);
    }

    private void renderHpBar(Graphics g) {
	float barHeight = shape.getHeight() / 5;
	g.setColor(Color.red);
	g.fillRect(shape.getX(), shape.getY() - 5.0f, shape.getWidth(), barHeight);
	g.setColor(Color.green);
	float health = (float)hp / (float)maxHp * shape.getWidth();
	g.fillRect(shape.getX(), shape.getY() - 5.0f,  health, barHeight);
    }
}
