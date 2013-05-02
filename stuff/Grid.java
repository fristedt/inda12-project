import java.util.*;

public class Grid implements Iterable<Tile> {
    Tile[][] grid;
    int width, height;
    
    public Grid(int width, int height, float gridX, float gridY, float tileWidth, float tileHeight) {
	this.width = width;
	this.height = height;
	grid = new Tile[width][height];
	for (int i = 0; i < width; i++) 
	    for (int j = 0; j < height; j++) 
		grid[i][j] = new Tile(gridX + i * tileWidth, gridY + j * tileHeight, tileWidth, tileHeight);
    }

    private class MyIterator implements Iterator<Tile> {
	Tile[][] grid;
	int i, j;
	int width, height;
	
	public MyIterator(Tile[][] grid, int width, int height) {
	    this.grid = grid;
	    i = j = 0;
	    this.width = width;
	    this.height = height;
	}

	public boolean hasNext() {
	    return !(i + 1 == width && j == height);
	}

	public Tile next() {
	    if (!hasNext()) 
		throw new NoSuchElementException();

	    if (j == height) {
		j = 0;
		return grid[i++][j];
	    }
				
	    return grid[i][j++];
	}
	
	public void remove() {
	    // FUCK YOU COMPILER.
	}
    }

    public Iterator<Tile> iterator() {
	return new MyIterator(grid, width, height);
    }

    public Tile[] getNeighbours(Tile tile) {
	int[] xAndY = getXY(tile);
	int x = xAndY[0];
	int y = xAndY[1];

	if (x == 0 && y == 0) 
	    return new Tile[]{grid[0][1], grid[1][0]};
	else if (x == 0 && y + 1 < height) 
	    return new Tile[]{grid[0][y - 1], grid[0][y + 1], grid[1][y]};
	else if (y == 0 && x + 1 < width)
	    return new Tile[]{grid[x - 1][0], grid[x + 1][0], grid[x][1]};
	else if (x + 1 == width && y + 1 == height)
	    return new Tile[]{grid[x][y - 1], grid[x - 1][y]};
	else 
	    return new Tile[]{grid[x][y - 1], grid[x][y + 1], grid[x - 1][y], grid[x + 1][y]};
    }

    public Tile getTile(int x, int y) {
	assert x >= 0;
	assert y >= 0;
	assert x < width;
	assert y < height;
	
	return grid[x][y];
    }

<<<<<<< HEAD
    public void placeTower(int x, int y, Tower t) {
	assert x >= 0;
	assert y >= 0;
	assert x < width;
	assert y < height;

	assert grid[x][y] == null;
	grid[x][y].setTower(t);
    }

    /* Returns x at index 0 and y at index 1 beacuse Java doesn't have duples and I won't make a specific data structure. */
    public int[] getXY(Tile tile) {
	for (int x = 0; x < width; x++) 
	    for (int y = 0; y < height; y++)
		if (tile.equals(grid[x][y]))
		    return new int[]{x, y};
	return null;
    }

    
=======
>>>>>>> 1b8b7bcdc18a38f9b4e388b5aa00da3db44f69f1
    public int getSize() {
	return width * height;
    }
}
