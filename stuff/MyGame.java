import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;
import java.util.*;

public class MyGame extends BasicGame {
    // gameWidth / gameHeight must equal xTiles / yTiles in the init method below.
    static final int gameWidth = 320;
    static final int uiWidth = gameWidth / 2;
    static final int gameHeight = 640;

    ArrayList<GameObject> gameObjects;
    ArrayList<Button> buttons;
    Grid gameGrid;
    Grid uiGrid;
    Tile enemySpawn;
    Tile enemyExit;

    public MyGame(String title) {
	super(title);
    }

    public void init(GameContainer gc) {
	// Create game grid.
	int xTiles = 9;
	int yTiles = 18;
	gameGrid = new Grid(xTiles, yTiles, 0, 0, gameWidth / xTiles, gameHeight / yTiles);

	// Create UI grid.
	int xUiTiles = 2;
	int yUiTiles = 8;
	uiGrid = new Grid(xUiTiles, yUiTiles, gameWidth, 0, uiWidth / xUiTiles, gc.getHeight() / yUiTiles);
	
	// Create a button.
	buttons = new ArrayList<Button>();
	Tile tmp = uiGrid.getTile(0, 0);
	buttons.add(new Button(tmp.getX(), tmp.getY(), tmp.getWidth(), tmp.getHeight(), "MY BUTTON"));

	gameObjects = new ArrayList<GameObject>();

	// Set enemy spawn and exit.
	enemySpawn = gameGrid.getTile(xTiles / 2, 0);
	enemyExit = gameGrid.getTile(xTiles / 2, yTiles - 1);
    }

    public void update(GameContainer gc, int delta) {
	for (GameObject go : gameObjects)
	    go.update(delta);
    }

    public void render(GameContainer gc, Graphics g) {
	// Draw grid
	g.setColor(Color.white);
	for (Tile tile : gameGrid) 
	    g.drawRect(tile.getX(), tile.getY(), tile.getWidth(), tile.getHeight());

	// Color enemy spawn and exit.
	g.setColor(Color.yellow);
	g.fillRect(enemySpawn.getX(), enemySpawn.getY(), enemySpawn.getWidth(), enemySpawn.getHeight());
	g.fillRect(enemyExit.getX(), enemyExit.getY(), enemyExit.getWidth(), enemyExit.getHeight());

	// Render GameObjects.
	for (GameObject go : gameObjects)
	    go.render(g);

	// Take a guess at what this does.
	renderUI(gc, g);
    }

    public void mousePressed(int button, int x, int y) {
	// Check for eventual button clicks.
	for (Button b : buttons) {
	    if (b.getShape().contains((float)x, (float)y)) 
		b.toggleMarked();
	}
    }

    private void renderUI(GameContainer gc, Graphics g) {
	// Render TowerMenu
	for (Button button : buttons) {
	    g.setColor(button.getColor());
	    g.draw(button.getShape());
	}
    }

    // Tiles are nodes. Both start and end should be in g.
    private void BFS(Tile start, Tile end, Grid g) {
	Tile tmp;
	Tile[] neighbour;
	LinkedList<Tile> queue = new LinkedList<Tile>();
	HashSet<Tile> markedTiles = new HashSet<Tile>();
	queue.add(start);
	markedTiles.add(start);
	while(queue.size() > 0) {
	    tmp = queue.poll();
	    if (tmp.equals(end)) {
		// I AM FINNISH!
	    }
	    neighbours = g.getNeighbours(tmp);
	    for (int i = 0; i < neighbours.length; i++) {
		if (markedTiles.contains(neighbours[i]))
		    continue;
		markedTiles.add(neighbours[i]);
		queue.add(neighbours[i]);
	    }
	}
    }

    private void spawnWave(GameContainer gc, int lvl) {
    }
    
    public static void main(String[] args) throws SlickException {
	AppGameContainer app = new AppGameContainer(new MyGame("My game"));
	app.setDisplayMode(gameWidth + uiWidth, gameHeight, false);
	app.start();
    }
}
