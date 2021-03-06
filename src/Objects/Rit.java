
package Objects;

import Program.Animation;
import Program.Camera;
import Program.Game;
import Program.GameObject;
import Program.HUD;
import Program.ImageLoader;
import Program.SoundEffect;
import Program.World;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;

public class Rit extends Player{
    
    public Rit(int x, int y, int width, int height, int[] shoot, int[][] jump, int down[], World world, ObjectId id) throws IOException {
        super(x, y, width, height, world, id);
        
        shootSizes = shoot;
        jumpSizes = jump;
        downSizes = down;
        
        shootSound = new SoundEffect("/Sound/ritshoot.wav");
        
        playerIdleRight = new Animation(4, 90, 176, imgLoader.player[0], imgLoader.player[1], imgLoader.player[2], imgLoader.player[3]);
        playerIdleLeft = new Animation(4, 90, 176, imgLoader.player[4], imgLoader.player[5], imgLoader.player[6], imgLoader.player[7]);
        
        playerRunRight = new Animation(3, 120, 172, imgLoader.player[8], imgLoader.player[9], imgLoader.player[10], imgLoader.player[11]);
        playerRunLeft = new Animation(3, 120, 172, imgLoader.player[12], imgLoader.player[13], imgLoader.player[14], imgLoader.player[15]);
        
        playerShootRight = new Animation(3, 142, 182, 1, imgLoader.player[27], imgLoader.player[28], imgLoader.player[29]);
        playerShootLeft = new Animation(3, 142, 182, 1, imgLoader.player[32], imgLoader.player[33], imgLoader.player[34]);
        
        playerDeadRight = new Animation(15, 200, 182, 1, imgLoader.player[37], imgLoader.player[38], imgLoader.player[39], imgLoader.player[40], 
                imgLoader.player[41], imgLoader.player[42], imgLoader.player[43], imgLoader.player[44]);
        
        playerDeadLeft = new Animation(15, 200, 182, 1, imgLoader.player[45], imgLoader.player[46], imgLoader.player[47], imgLoader.player[48], 
                imgLoader.player[49], imgLoader.player[50], imgLoader.player[51], imgLoader.player[52]);
        
    }
}
  