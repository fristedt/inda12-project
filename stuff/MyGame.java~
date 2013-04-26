import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;
import java.util.*;

public class MyGame extends BasicGame {
    ArrayList<GameObject> gameObjects;
    
    private interface GameObject {
	void render(Graphics g);
	void update(int delta);
    }

    private class Tower {
	float x, y, width, height, rateOfFire;
	Shape shape;
	Ammo ammo;

	public Tower(float x, float y) {
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
    
    private class Ammo {
	float x, y, velX, velY;
	Shape shape;
	
	public Ammo () {
	}
    }

    private class Enemy implements GameObject {
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

    public MyGame(String title) {
	super(title);
	gameObjects = new ArrayList<GameObject>();
    }

    public void init(GameContainer gc) {
	spawnWave(gc, 3);
    }

    public void update(GameContainer gc, int delta) {
	for (GameObject go : gameObjects)
	    go.update(delta);
    }

    public void render(GameContainer gc, Graphics g) {
	for (GameObject go : gameObjects)
	    go.render(g);
    }

    private void spawnWave(GameContainer gc, int lvl) {
	int width = gc.getWidth();
	for (int i = 0; i < 3; i++) {
	    for (int j = 0; j < 10; j++) {
		gameObjects.add(new Enemy(48 * j, 35 * i));
	    }
	}
    }
    
    public static void main(String[] args) throws SlickException {
	AppGameContainer app = new AppGameContainer(new MyGame("My game"));
	app.setTargetFrameRate(60);
	app.setDisplayMode(480, 640, false);
	app.start();
    }
}
