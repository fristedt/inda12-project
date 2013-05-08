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
    ArrayList<Tile> tilePath; // Contains the tiles that make up the shortest path.
    ArrayList<Float> floatPath; // Contains a trail of floats that -||-.
    Grid gameGrid;
    Grid uiGrid;
    Tile enemySpawn;
    Tile enemyExit;

    int xTiles = 9;
    int yTiles = 18;
    int xUiTiles = 2;
    int yUiTiles = 8;

    long counter; // Used to spawn enemies.

    Button pickedButton;

    public MyGame(String title) {
	super(title);
    }

    public void init(GameContainer gc) {
	// Create game grid.
	gameGrid = new Grid(xTiles, yTiles, 0, 0, gameWidth / xTiles, gameHeight / yTiles);

	// Create UI grid.
	uiGrid = new Grid(xUiTiles, yUiTiles, gameWidth, 0, uiWidth / xUiTiles, gc.getHeight() / yUiTiles);

	// Create a button.
	buttons = new ArrayList<Button>();
	Tile tmp = uiGrid.getTile(0, 0);
	buttons.add(new Button(tmp.getX(), tmp.getY(), tmp.getWidth(), tmp.getHeight(), null));

	gameObjects = new ArrayList<GameObject>();

	// Set enemy spawn and exit.
	enemySpawn = gameGrid.getTile(xTiles / 2, 0);
	enemyExit = gameGrid.getTile(xTiles / 2, yTiles - 1);

	updateShortestPath();
    }

    public void update(GameContainer gc, int delta) {
	counter += delta;
	if (counter >= 500) {
	    counter = 0;
	    spawnEnemy();
	}

	for (GameObject go : gameObjects)
	    go.update(delta);
    }
    
    private void updateShortestPath() {
	tilePath = getShortestPath(enemyExit, enemySpawn, gameGrid);
    }

    public void render(GameContainer gc, Graphics g) {
	drawGridAndStuff(g);

	// Render GameObjects.
	for (GameObject go : gameObjects)
	    go.render(g);
	
	// Take a guess at what this does.
	renderUI(gc, g);
    }

    public void drawGridAndStuff(Graphics g) {
	// Draw grid
	g.setColor(Color.white);
	for (Tile tile : gameGrid) 
	    g.drawRect(tile.getX(), tile.getY(), tile.getWidth(), tile.getHeight());

	// Color enemy spawn and exit.
	g.setColor(Color.yellow);
	g.fillRect(enemySpawn.getX(), enemySpawn.getY(), enemySpawn.getWidth(), enemySpawn.getHeight());
	g.fillRect(enemyExit.getX(), enemyExit.getY(), enemyExit.getWidth(), enemyExit.getHeight());

	// Draw shortest path for debugging purposes.
 	g.setColor(Color.green);
	for (Tile tile : tilePath) {
	    g.fillRect(tile.getX(), tile.getY(), tile.getWidth(), tile.getHeight());
	}
    }

    public void mouseReleased(int button, int x, int y) {
	for (Tile tile : gameGrid) {
	    if (tile.contains(x, y)) {
		if (tile.hasTower()) {
		    Tower tower = tile.removeTower();
		    gameObjects.remove(tower);
		    updateShortestPath();
		    return;
		}
		placeTower(tile);
	    }
	}
    }
    

    private void placeTower(Tile tile) {
	Shape shapeTmp = new Circle(tile.getCenterX(), tile.getCenterY(), tile.getWidth() / 3);
	Tower tower = new Tower(shapeTmp, Color.blue);

	// Add the tower here so it can be updated and rendered.
	gameObjects.add(tower);
	// Add the tower here so pathfinding works.
	tile.placeTower(tower);
	updateShortestPath();
    }

    private void renderUI(GameContainer gc, Graphics g) {
	// Render TowerMenu
	for (Button button : buttons) {
	    g.setColor(button.getColor());
	    g.draw(button.getShape());
	}
    }

    // Returns the shortest path (in reverse order) between start and end in grid g. Returns null if no path exists.
    private ArrayList<Tile> getShortestPath(Tile start, Tile end, Grid g) {
	ArrayList<Tile> neighbours;
	// The key is the tile being added to the queue and the value is the tile adding the key.
	HashMap<Tile, Tile> previousTile = new HashMap<Tile, Tile>();

	LinkedList<Tile> queue = new LinkedList<Tile>();
	HashSet<Tile> markedTiles = new HashSet<Tile>();

	queue.add(start);
	markedTiles.add(start);
	Tile tmp;
	while(queue.size() > 0) {
	    tmp = queue.poll();
	    if (tmp.equals(end)) {
		// DO THE BACKTRACK!
		ArrayList<Tile> shortestPath = new ArrayList<Tile>();
		shortestPath.add(end); // Gotta add the end.
		Tile t = end;
		while (!t.equals(start)) {
		    t = previousTile.get(t);
		    shortestPath.add(t);
		}
		return shortestPath;
	    }
	    neighbours = g.getNeighbours(tmp);
	    for (Tile tmp2 : neighbours) {
		if (markedTiles.contains(tmp2))
		    continue;
		// Put a reference to where you came from so we can backtrack to find the shortest path.
		previousTile.put(tmp2, tmp);
		markedTiles.add(tmp2);
		queue.add(tmp2);
	    }
	}
	return null;
    }

    private void spawnEnemy() {
	Shape shape = new Rectangle(enemySpawn.getX() + enemySpawn.getWidth() / 4, enemySpawn.getY(), enemySpawn.getWidth()/ 2, enemySpawn.getHeight() / 2);
	Color color = Color.red;
	float velocity = 0.1f;
	gameObjects.add(new Enemy(shape, color, velocity, tilePath));
    }

    public static void main(String[] args) throws SlickException {
	AppGameContainer app = new AppGameContainer(new MyGame("My game"));
	app.setDisplayMode(gameWidth + uiWidth, gameHeight, false);
	app.start();
    }
}
