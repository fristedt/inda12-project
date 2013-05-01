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

    public Tile getTile(int x, int y) {
	assert x >= 0;
	assert y >= 0;
	assert x < width;
	assert y < height;
	
	return grid[x][y];
    }

    public void placeTower(int x, int y, Tower t) {
	assert x >= 0;
	assert y >= 0;
	assert x < width;
	assert y < height;

	assert grid[x][y] == null;
	grid[x][y].setTower(t);
    }
    
    public int getSize() {
	return width * height;
    }
}
