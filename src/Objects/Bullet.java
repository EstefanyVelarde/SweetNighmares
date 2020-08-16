/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objects;

import Program.Animation;
import Program.Game;
import Program.GameObject;
import Program.ImageLoader;
import Program.World;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

/**
 *
 * @author Estefany
 */
public class Bullet extends GameObject {

    public int end;
    public boolean delete =  false, deleteNow = false;
    
    ImageLoader imgLoader = Game.getImgLoader();
    
    Animation bulletMove, hit;
    
    public Bullet(int x, int y, int width, int height, boolean side, ObjectId id) {
        super(x, y, width, height, side, id);
        
        if(side) 
            velX = 15;
        else
            velX = -15;
        
        end = x+Game.width;
        
        if(World.playerId == World.PLAYER.MRX) {
            bulletMove = new Animation(7, width, height, Game.imgLoader.loadImage("/Images/Characters/MRX/Bullet-B.png"), 
                                                         Game.imgLoader.loadImage("/Images/Characters/MRX/Bullet-B2.png"), 
                                                         Game.imgLoader.loadImage("/Images/Characters/MRX/Bullet-B3.png"));
        }
        
        
        hit = new Animation(5, 62, 62, 1, Game.imgLoader.loadImage("/Images/Effects/Got-Hit.png"), 
                                                  Game.imgLoader.loadImage("/Images/Effects/Got-Hit.png"));
    }

    
    public void tick(LinkedList<GameObject> object) {
        
        if(delete) {
            hit.runAnimation();
        } else {
            x += velX;
            
            collision(object);

            if(World.playerId == World.PLAYER.MRX) {
                bulletMove.runAnimation();
            }
        }
    }
    
     private void collision(LinkedList<GameObject> object) {
     
        for (int i = 0; i < object.size(); i++) {
            GameObject tempObject = object.get(i);

            if(tempObject.getId() == ObjectId.Block) {
                Block block = (Block) tempObject;
                
                if(getBounds().intersects(block.getBounds())) {
                    delete = true;
                }
            } 
        }
     }
    
    
    public void draw(Graphics g) {
        if(delete) {
            hit.drawAnimation(g, x, y);
            
            if(hit.count >= hit.nImages)
                deleteNow = true;
        } else {
            if(World.playerId == World.PLAYER.MRX) {
                bulletMove.drawAnimation(g, x, y);
            } else {
                if(side)
                  g.drawImage(imgLoader.player[30], x, y, width, height, null);

                else 
                  g.drawImage(imgLoader.player[31], x, y, width, height, null);
            }
        }
    }

    
    public Rectangle getBounds() {
        return new Rectangle(x, y, 16, 16);
    }
    
}
