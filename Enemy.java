import java.util.ArrayList;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;

public class Enemy implements GameObject {
    Shape shape;
    Color color;
    float maxVelocity; // The length of the movement vector.
    ArrayList<Tile> path;
    ArrayList<ArrayList<Tile>> paths;
    Tile currentTile;
    int currentIndex;
    int hp;
    int maxHp;
    int reward;

    public Enemy(Shape shape, Color color, float maxVelocity, ArrayList<ArrayList<Tile>> paths, Tile firstTile, int hp) {
	this.shape = shape;
	this.color = color;
	this.maxVelocity = maxVelocity;
	this.paths = paths;
	maxHp = this.hp = hp;
	currentTile = firstTile;

	setPath();

	reward = 1;
    }

    private void setPath() {
	int shortestPathLength = Integer.MAX_VALUE;

	// Dear compiler,
	// Fuck yourself.
	ArrayList<Tile> shortestPath = null; 

	for (ArrayList<Tile> path : paths) {
	    // Not all paths are valid.
	    if (path == null)
		continue;
	    int i = 0;
	    for (Tile tile : path) {
		i++;
		if (tile != currentTile) 
		    continue;
		if (path.size() - i >= shortestPathLength) 
		    continue;

		shortestPath = path;
		shortestPathLength = path.size() - i;
	    }
	}
	path = shortestPath;
    }	

    public void update(int delta) {
	setCurrentTile();

	setPath();

	move(delta);
    }

    private void setCurrentTile() {
	float shortestDistance = Float.MAX_VALUE;
	Tile closestTile = null;
	for (Tile tile : path) {
	    double distance = Math.sqrt(Math.pow(shape.getCenterX() - tile.getCenterX(), 2) + Math.pow(shape.getCenterY() - tile.getCenterY(), 2)); 
	    if (shortestDistance < distance)
		continue;
	    if (tile.hasTower())
		continue;

	    shortestDistance = (float)distance;
	    closestTile = tile;
	}
	currentTile = closestTile;
	currentIndex = path.indexOf(currentTile);
    }

    public Shape getShape() {
	return shape;
    }
    
    private void move(int delta) {
	Vector2f position = new Vector2f(shape.getCenterX(), shape.getCenterY());
	
	if (currentTile.contains(position.getX(), position.getY())) {
	    currentIndex++;
	    if (currentIndex >= path.size())
		return;
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

    public int getReward() {
	return reward;
    }
}
