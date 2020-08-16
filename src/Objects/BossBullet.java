
package Objects;

import Program.Animation;
import Program.Game;
import Program.GameObject;
import Program.ImageLoader;
import Program.World;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Random;

public class BossBullet extends GameObject {

    public int end;
    public boolean delete =  false, deleteNow = false;
    
    ImageLoader imgLoader = Game.getImgLoader();
    
    Animation bullet;
    
    World world;
    
    public BossBullet(int x, int y, int width, int height, int type, World world, ObjectId id) {
        super(x, y, width, height, false, id);
        
        this.world = world;
        
        velX = -5;
        
        end = x-Game.width;
        
        if(type == 1) {
            bullet = new Animation(7, width, height, imgLoader.boss[12], imgLoader.boss[13], imgLoader.boss[14], imgLoader.boss[15]);
        } else {
            Random rand = new Random();
            int randomNum = rand.nextInt((10 - 1) + 1) + 1;
            
            if(randomNum%2 == 0)
                bullet = new Animation(7, width, height, imgLoader.boss[16], imgLoader.boss[17], imgLoader.boss[18], imgLoader.boss[19]);
            else
                bullet = new Animation(7, width, height, imgLoader.boss[20], imgLoader.boss[21], imgLoader.boss[22], imgLoader.boss[23]);
                
        }
    }

    
    public void tick(LinkedList<GameObject> object) {
        
        x += velX;
        
        bullet.runAnimation();
        collision(object);
    }
    
     private void collision(LinkedList<GameObject> object) {
        Player player = world.player;

        if(getBounds().intersects(player.getBounds()) ||
           getBounds().intersects(player.getBoundsTop()) ||
           getBounds().intersects(player.getBoundsLeft()) ||
           getBounds().intersects(player.getBoundsRight())) {
            if(player.inmortal == 0) { 
                player.hitSound.play(false);
                world.hud.HEALTH --;
                world.hud.tick();
                world.angel = new Angel(world.player.getX(), world.player.getY(), 72, 72, world.hud.HEALTH, ObjectId.Angel);
            }
            player.inmortal = 20;
        }
     }
    
    
    public void draw(Graphics g) {
        
        bullet.drawAnimation(g, x, y);
            
    }

    
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
    
}
