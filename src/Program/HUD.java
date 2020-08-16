package Program;

import java.awt.Color;
import java.awt.Graphics;

public class HUD {
    
    public static int HEALTH = 3;
    
    public void tick() {
        if(HEALTH < 0)
            HEALTH = 0;
        
        if(HEALTH > 200)
            HEALTH = 200;
    }
    
    public int tick(int health) {
        if(health < 0)
            health = 0;
        
        return health;
    }
    
    public void draw(Graphics g, int health, int x, int y) {
        g.setColor(Color.black);
        g.fillRect(x + 100, y - 50, 200, 30);
        g.setColor(Color.gray);
        g.fillRect(x + 100, y - 50, health * 7, 30);
        g.setColor(Color.black);
        g.drawRect(x + 100, y - 50, 200, 30);
        
    }
}
