import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;
import java.util.*;

/* ONCE CLASS TO RULE THEM ALL; THE GOD CLASS! */
public class MyGame extends BasicGame {
    // gameWidth / gameHeight must equal xTiles / yTiles in the init method below.
    static final int gameWidth = 320;
    static final int uiWidth = gameWidth / 2;
    static final int gameHeight = 640;

    ArrayList<GameObject> gameObjects;
    ArrayList<Button> buttons;
    ArrayList<Tile> tilePath; // Contains the tiles that make up the shortest path.
    ArrayList<ArrayList<Tile>> shortestPaths;
    ArrayList<Tower> towers; 
    ArrayList<Enemy> enemies;
    ArrayList<Enemy> taggedKilledEnemies;
    ArrayList<Enemy> taggedEscapedEnemies;
    Grid gameGrid;
    Grid uiGrid;
    Tile enemySpawn;
    Tile enemyExit;

    int xTiles = 9;
    int yTiles = 18;
    int xUiTiles = 2;
    int yUiTiles = 8;

    int lives = 25;
    int money = 25;
    float resellFactor = 0.75f;

    int enemyHp = 50;

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

	// // Create a button.
	// buttons = new ArrayList<Button>();
	// Tile tmp = uiGrid.getTile(0, 0);
	// buttons.add(new Button(tmp.getX(), tmp.getY(), tmp.getWidth(), tmp.getHeight(), null));

	gameObjects = new ArrayList<GameObject>();
	towers = new ArrayList<Tower>();
	enemies = new ArrayList<Enemy>();
	taggedKilledEnemies = new ArrayList<Enemy>();
	taggedEscapedEnemies = new ArrayList<Enemy>();

	// Set enemy spawn and exit.
	enemySpawn = gameGrid.getTile(xTiles / 2, 0);
	enemyExit = gameGrid.getTile(xTiles / 2, yTiles - 1);

	updateShortestPath();
    }

    public void update(GameContainer gc, int delta) {
	if (lives <= 0) {
	    // TODO: Give better feedback.
	    System.exit(0);
	}

	// Spawn enemy every half second.
	counter += delta;
	if (counter >= 500) {
	    counter = 0;
	    spawnEnemy();
	}

	// Makes the tower fire at the enemy closest to the exit.
	for (Tower tower : towers) {
	    if (tower.hasTarget())
		continue;
	    for (Enemy enemy : enemies) {
		if (tower.targetInRange(enemy))
		    tower.setTarget(enemy);
	    }
	}

	// Loop through gameObjects, check for enemies to remove, and UPDATE ALL THE OBJECTS.
	for (GameObject go : gameObjects) {
	    if (go instanceof Enemy) {
		Enemy enemy = (Enemy)go;
		if (enemyExit.contains(enemy.getShape().getCenterX(), enemy.getShape().getCenterY()))
		    taggedEscapedEnemies.add(enemy); // Gotta do this because of concurrent modification shit.

		if (enemy.getHp() <= 0)
		    taggedKilledEnemies.add(enemy);
	    }
	    go.update(delta);
	}
	removeTaggedEnemies();
    }
    
    private ArrayList<Tile> updateShortestPath() {
	tilePath = getShortestPath(enemyExit, enemySpawn, gameGrid);
	shortestPaths = new ArrayList<ArrayList<Tile>>();
	for (Tile tile : gameGrid)
	    shortestPaths.add(getShortestPath(enemyExit, tile, gameGrid));
	return tilePath;
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
 	g.setColor(Color.white);
	for (Tile tile : tilePath) {
	    g.fillRect(tile.getX(), tile.getY(), tile.getWidth(), tile.getHeight());
	}
    }

    public void mouseReleased(int button, int x, int y) {
	for (Tile tile : gameGrid) {
	    if (tile.contains(x, y)) {
		if (tile.hasTower()) {
		    removeTower(tile);
		    return;
		}
		placeTower(tile);
		// Remove tower instantly if it blocks the path.
		if (updateShortestPath() == null || tile == enemyExit || tile == enemySpawn) {
		    removeTower(tile);
		}
		return;
	    }
	}
    }

    public void removeTower(Tile tile) {
	Tower tower = tile.removeTower();
	gameObjects.remove(tower);
	money += tower.getCost() * resellFactor;
	updateShortestPath();
    }
	    

    private void placeTower(Tile tile) {
	Shape shapeTmp = new Circle(tile.getCenterX(), tile.getCenterY(), tile.getWidth() / 3);
	Tower tower = new BeamTower(shapeTmp, Color.blue);

	if (tower.getCost() > money) 
	    return; // TODO: Give user feedback.

	money -= tower.getCost();
	
	// Add the tower here so it can be updated and rendered.
	gameObjects.add(tower);

	// Add the tower so it can fire.
	towers.add(tower);

	// Add the tower here so pathfinding works.
	tile.placeTower(tower);

	updateShortestPath();
    }

    private void renderUI(GameContainer gc, Graphics g) {
	// // Render TowerMenu
	// for (Button button : buttons) {
	//     g.setColor(button.getColor());
	//     g.draw(button.getShape());
	// }
	g.setColor(Color.white);
	Tile livesTile = uiGrid.getTile(0, 0);
	g.drawString("Lives: " + lives + "\n" +
		     "Money: " + money, 
		     livesTile.getX(), livesTile.getY());
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
	Enemy enemy = new Enemy(shape, color, velocity, shortestPaths, enemySpawn, enemyHp += 3);
	gameObjects.add(enemy);
	enemies.add(0, enemy);
    }
    
    private void removeTaggedEnemies() {
	// TODO: Enemies that reach the exit should not warrant a reward.
	for (Enemy enemy : taggedKilledEnemies) {
	    gameObjects.remove(enemy);
	    enemies.remove(enemy);
	    money += enemy.getReward();
	}
	for (Enemy enemy : taggedEscapedEnemies) {
	    gameObjects.remove(enemy);
	    enemies.remove(enemy);
	    lives--;
	}
	taggedKilledEnemies = new ArrayList<Enemy>();
	taggedEscapedEnemies = new ArrayList<Enemy>();
    }

    public static void main(String[] args) throws SlickException {
	AppGameContainer app = new AppGameContainer(new MyGame("My game"));
	app.setDisplayMode(gameWidth + uiWidth, gameHeight, false);
	app.start();
    }
}
