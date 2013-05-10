import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;

public interface Tower extends GameObject {
    public void render(Graphics g);
    
    public void update(int delta);
    
    public void setTarget(Enemy target);

    public boolean hasTarget();

    public boolean targetInRange(Enemy enemy);

    public int getCost();
}
