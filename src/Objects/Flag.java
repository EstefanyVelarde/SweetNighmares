
package Objects;

import Program.Game;
import Program.GameObject;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

public class Flag extends GameObject {
    
    public Flag(int x, int y, int width, int height, ObjectId id) {
        super(x, y, width, height, false, id);
    }

    
    public void tick(LinkedList<GameObject> object) {
        
    }

    
    public void draw(Graphics g) {
        g.drawImage(Game.imgLoader.flag, x, y, width, height, null);
    }

    
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
    
}
