package Objects;

import Program.Game;
import Program.GameObject;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import Program.ImageLoader;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Block extends GameObject {
    public int type;
    
    public int velX, start, end;
    
    
    Random rand = new Random();
    
    ImageLoader imgLoader = Game.getImgLoader();
    
    public Block(int x, int y, int width, int height, int type, ObjectId id) {
        super(x, y, width, height, true, id);
        
        this.type = type;
        
        switch(type) {
            case 2: case 11:
                
                if ((rand.nextInt(90) + 10) % 2 == 0) 
                    velX = -2;
                else 
                    velX = 2;
                    
                start = x-150;
                end = x+150;
            break;
        }
    }

    public void tick(LinkedList<GameObject> object) {
        if(type == 2 || type == 11) {
            x += velX;
            if(x <= start || x >=  end)
                velX *= -1;
        }
            
    }

    public void draw(Graphics g) {
        g.drawImage(imgLoader.block[type], x, y, width, height, null);
        
    }

    public Rectangle getBounds() {
        return new Rectangle(x,  y, width, height);
    }
}
