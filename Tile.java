import org.newdawn.slick.geom.*;
public class Tile {
    private float x, y, width, height;
    private Tower tower;

    public Tile(float x, float y, float width, float height) {
	this.x = x;
	this.y = y;
	this.width = width;
	this.height = height;
    }

    public float getX() {
	return x;
    }

    public float getY() {
	return y;
    }

    public float getWidth() {
	return width;
    }
    
    public float getHeight() {
	return height;
    }

    private void setTower(Tower t) {
	tower = t;
    }
    
    public boolean hasTower() {
	return tower == null;
    }

    public void placeTower(Tower t) {
	assert !hasTower();
	setTower(t);
    }

    public boolean contains(float x, float y) {
	return x >= this.x && x <= this.x + width &&
	    y >= this.y && y <= this.y + height;
    }
    
    public boolean isTraverseable() {
	return tower == null;
    }
}
