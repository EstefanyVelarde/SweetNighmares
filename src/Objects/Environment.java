package Objects;

import Program.Game;
import Program.GameObject;
import Program.ImageLoader;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import Program.World;

public class Environment extends GameObject {

    ImageLoader imgLoader = Game.getImgLoader();
    World world;
    private int type, x2;
    
    public Environment(int x, int y, int width, int height, int type, World world, ObjectId id) {
        super(x, y, width, height, false, id);
        
        this.type = type;
        this.world = world;
        
        switch(type) {
            //BACKGROUND
            case 1:
                this.velX = -2;
                y -= 150;
                
                x2 = x;
            break;
                
            case 2:
                y -= 30;
            break;
            
            case 3:
                y -= 30;
                x2 = x;
            break;
        }
    }

    
    public void tick(LinkedList<GameObject> object) {
        switch(type) {
            
            //BACKGROUND
            case 1:
                
                x += velX;

                if(x + width < 0) {
                    x = world.end;
                }
                
                if(x2 + width < 0) {
                    x2 = world.end;
                }
                
            break;
            
            case 3:
                if(x2 + width < 0) {
                    x2 = world.end;
                }
            break;
        }
        
    }

    
    public void draw(Graphics g) {
        switch(type) {
            case 1: 
                g.drawImage(imgLoader.layer1, x, y, width, height, null);
                g.drawImage(imgLoader.layer2, -world.player.getX()/4 + x2, -95, width, height, null);
            break;
            
            case 2: 
                g.drawImage(imgLoader.layer1, x, y - 700, width, height + 800, null);
                g.drawImage(imgLoader.layer2, x, y - 700, width, height + 800, null);
            break;
            
            case 3: 
                g.drawImage(imgLoader.layer1, x, y - 700, width, height + 800, null);
                g.drawImage(imgLoader.layer2,  -world.player.getX()/4 + x2, y - 400, width, height + 800, null);
            break;
        }
    }
    
    
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
    
}
