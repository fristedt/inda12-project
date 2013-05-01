import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;

public class Button {
    private Shape shape;
    private Color defaultColor = Color.white;
    private Color markedColor = Color.red;
    private Color currentColor = defaultColor;
    private String text;
    private boolean marked = false;

    public Button(float x, float y, float width, float height, String text) {
	shape = new Rectangle(x, y, width, height);
	this.text = text;
    }

    public Shape getShape() {
	return shape;
    }

    public Color getColor() {
	return currentColor;
    }

    public void toggleMarked() {
	marked = !marked;
	if (marked)
	    currentColor = markedColor;
	else
	    currentColor = defaultColor;
    }
}
