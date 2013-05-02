import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;

public class Button {
    private Shape shape;
    private Color defaultColor = Color.white;
    private Color markedColor = Color.red;
    private Color currentColor = defaultColor;
    private boolean marked = false;
    private Tower tower;

    public Button(float x, float y, float width, float height, Tower tower) {
	shape = new Rectangle(x, y, width, height);
    }

    public Shape getShape() {
	return shape;
    }

    public Color getColor() {
	return currentColor;
    }

    public Tower getTower() {
	return tower;
    }

    public void toggleMarked() {
	marked = !marked;
	if (marked)
	    currentColor = markedColor;
	else
	    currentColor = defaultColor;
    }
}
