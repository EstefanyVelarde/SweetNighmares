
package Objects;

import Program.Animation;
import Program.Game;
import Program.GameObject;
import Program.SoundEffect;
import Program.World;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

public class AirMonster extends GameObject {
    private World world;
    
    public boolean dead = false, endMePlease =  false;
    
    public int bullet;
    
    private Animation monsterMove, monsterMoveRight, monsterPuff;
    public SoundEffect deadSound;
            
            
    public AirMonster(int x, int y, int width, int height, World world, ObjectId id) throws IOException {
        super(x, y, width, height, false, id);
        
        Random rand = new Random();
        int randomNum = rand.nextInt((10 - 1) + 1) + 1;
        
        if(randomNum%2 == 0)
            velX = 5;
        else 
            velX = -5;
        
        velY = 0; //MOVIMIENTO PAJARO EPILEPTICO
        
        
        deadSound = new SoundEffect("/Sound/puff.wav");
        
        randomNum = rand.nextInt((4 - 1) + 1) + 1;
        
        monsterMove = new Animation(7, width, height, Game.imgLoader.loadImage("/Images/Monsters/Air-Monsters/flying" + (randomNum) + " (1).png"), 
                                                      Game.imgLoader.loadImage("/Images/Monsters/Air-Monsters/flying" + (randomNum) + " (2).png"), 
                                                      Game.imgLoader.loadImage("/Images/Monsters/Air-Monsters/flying" + (randomNum) + " (3).png"), 
                                                      Game.imgLoader.loadImage("/Images/Monsters/Air-Monsters/flying" + (randomNum) + " (4).png"));
                    
        monsterMoveRight = new Animation(7, width, height, Game.imgLoader.loadImage("/Images/Monsters/Air-Monsters/flying" + (randomNum) + " (5).png"), 
                                                           Game.imgLoader.loadImage("/Images/Monsters/Air-Monsters/flying" + (randomNum) + " (6).png"), 
                                                           Game.imgLoader.loadImage("/Images/Monsters/Air-Monsters/flying" + (randomNum) + " (7).png"), 
                                                           Game.imgLoader.loadImage("/Images/Monsters/Air-Monsters/flying" + (randomNum) + " (8).png"));
        this.world = world;
    }

    
    public void tick(LinkedList<GameObject> object) {
        collision(object);
        
        if(dead) {
            if(!deadSound.isRunning())
                deadSound.play(false);
            monsterPuff.runAnimation();
        } else {
            
            x += velX;
            y += velY;
            
            monsterMoveRight.runAnimation();

            monsterMove.runAnimation();
        }
    }

    
    public void draw(Graphics g) {
        if(dead) {
            monsterPuff.drawAnimation(g, x, y);
            
            if(monsterPuff.count >= monsterPuff.nImages )
                endMePlease = true;
        } else {
        
            if(velX > 0)
                monsterMoveRight.drawAnimation(g, x, y);
            else
                monsterMove.drawAnimation(g, x, y);
        }
    }
    
    private void collision(LinkedList<GameObject> object) {
        GameObject tempObject;
        
        for (int i = 0; i < world.object.size(); i++) {
            tempObject = world.object.get(i);

            if(tempObject.getId() == ObjectId.Block) {
                Block block = (Block) tempObject;
                
                //Arriba
                if(getBoundsTop().intersects(tempObject.getBounds())) {
                    y = tempObject.getY() + block.getHeight();
                    velY *= -1;
                }

                //Abajo
                if(getBounds().intersects(tempObject.getBounds())) 
                    y = tempObject.getY() - height;
                    velY *= -1;

                //Derecha
                if(getBoundsRight().intersects(tempObject.getBounds())) {
                    x = tempObject.getX() - width;
                    velX *= -1;
                }

                //Izquierda
                if(getBoundsLeft().intersects(tempObject.getBounds())) {
                    x = tempObject.getX() + block.getWidth();
                    velX *= -1;
                }
           }
        }
        
        if(!dead) {
            for (int i = 0; i < world.bullet.size(); i++) {
                tempObject = world.bullet.get(i);

                if(getBoundsTop().intersects(tempObject.getBounds()) ||
                    getBounds().intersects(tempObject.getBounds()) ||
                    getBoundsRight().intersects(tempObject.getBounds()) ||
                    getBoundsLeft().intersects(tempObject.getBounds())) {
                    dead = true;
                    world.removeBullet(i);

                    monsterPuff = new Animation(6, width, height, 1, Game.imgLoader.dead[5],
                                                                     Game.imgLoader.dead[6],
                                                                     Game.imgLoader.dead[7],
                                                                     Game.imgLoader.dead[8],
                                                                     Game.imgLoader.dead[9]);
                }
            } 
        
            playerCollision();
        }
    }
    
    public void playerCollision() {
        Player player = world.player;

        if(getBoundsTop().intersects(player.getBounds()) ||
           getBounds().intersects(player.getBoundsTop()) ||
           getBoundsRight().intersects(player.getBoundsLeft()) ||
           getBoundsLeft().intersects(player.getBoundsRight())) {
            if(player.inmortal == 0) { 
                player.hitSound.play(false);
                world.hud.HEALTH --;
                world.hud.tick();
                world.angel = new Angel(world.player.getX(), world.player.getY(), 72, 72, world.hud.HEALTH, ObjectId.Angel);
            }
            player.inmortal = 20;
        }
    }
    
    public Rectangle getBounds() {
        return new Rectangle(x+(width/2)-((width/2)/2), y+(height/2), width/2, height/2);
    }

    public Rectangle getBoundsTop() {
        return new Rectangle(x+(width/2)-((width/2)/2), y, width/2, height/2);
    }

    public Rectangle getBoundsRight() {
        return new Rectangle(x+width-5, y+5, 5, height-10);
    }

    public Rectangle getBoundsLeft() {
        return new Rectangle(x, y+5, 5, height-10);
    }
    
}
