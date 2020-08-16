package Objects;


import Objects.ObjectId;
import Program.Animation;
import Program.Game;
import Program.GameObject;
import Program.ImageLoader;
import Program.World;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.Random;


public class Angel extends GameObject {
    ImageLoader imgLoader = Game.getImgLoader();
    
    World world;
    
    Animation angelMove, angelMoveLeft;
    
    public Angel(int x, int y, int width, int height, int health, ObjectId id) {
        super(x, y, width, height, true, id);
        
        if(health == 3) {
            angelMove = new Animation(7, width, height, imgLoader.angel[0] , imgLoader.angel[1], imgLoader.angel[2], imgLoader.angel[3]);

            angelMoveLeft = new Animation(7, width, height, imgLoader.angel[10] , imgLoader.angel[11], imgLoader.angel[12], imgLoader.angel[13]);
        } 
        
        if(health == 2) {
            angelMove = new Animation(7, width, height - 20, imgLoader.angel[4] , imgLoader.angel[5]);

            angelMoveLeft = new Animation(7, width, height - 20, imgLoader.angel[14] , imgLoader.angel[15]);
        } 
        
        if(health == 1 || health == 0) {
            angelMove = new Animation(7, width, height, imgLoader.angel[6] , imgLoader.angel[7], imgLoader.angel[8], imgLoader.angel[9]);

            angelMoveLeft = new Animation(7, width, height, imgLoader.angel[16] , imgLoader.angel[17], imgLoader.angel[18], imgLoader.angel[19]);
        } 
    }

    public void tick(LinkedList<GameObject> object) {
        
    }
    
    public void tick(int x, int y, int health) {
        this.x = x - 100;
        this.y = y;
        
        angelMove.runAnimation();
        
        angelMoveLeft.runAnimation();
    }

    public void draw(Graphics g, boolean side) {
        if(side)
            angelMove.drawAnimation(g, x, y);
        else 
            angelMoveLeft.drawAnimation(g, x, y);
    }

    public Rectangle getBounds() {
        return new Rectangle(x,  y, width, height);
    }

    public void draw(Graphics g) {
        
    }
}