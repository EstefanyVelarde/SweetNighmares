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
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

public class GroundMonster extends GameObject {
    private World world;
    private float gravity = 0.5f;
    private final float speed = 20;
    
    private int movingSize[][] = {{132, 112}, {102, 112}, {102, 112}, {102, 112}, {142, 102}, {112, 122}, {100, 176}, {122, 112}, {100, 176}};
    private int staticSize[][] = {{82, 122}, {132, 112}, {82, 122}, {142, 112}, {132, 112}};
    private int spikeSize[][] = {{96, 112}, {132, 112}, {112, 112}};
    
    private Animation monsterMove, monsterMoveRight, monsterPuff;
    
    public boolean dead = false, endMePlease =  false;
    public int type, randomNum;
    public int bullet;
    
    public SoundEffect deadSound;
    
    public GroundMonster(int x, int y, int width, int height, int type, World world, ObjectId id) throws IOException {
        super(x, y, width, height, false, id);
        
        this.type = type;
                
        Random rand = new Random();
        
        deadSound = new SoundEffect("/Sound/puff.wav");
        
        switch(type) {
            case 0:
                velX = 0;
                velY = 0;
                
                randomNum = rand.nextInt((4 - 0) + 1) + 0;
                
                    
                width = staticSize[randomNum][0];
                height = staticSize[randomNum][1];
                
                this.width = width;
                this.height = height;
                
                this.x *= 62;
                this.y *= 62;
                
                monsterMove = new Animation(7, width, height, Game.imgLoader.loadImage("/Images/Monsters/Ground-Monsters/static" + (randomNum + 1) + " (1).png"), 
                                                              Game.imgLoader.loadImage("/Images/Monsters/Ground-Monsters/static" + (randomNum + 1) + " (2).png"), 
                                                              Game.imgLoader.loadImage("/Images/Monsters/Ground-Monsters/static" + (randomNum + 1) + " (3).png"), 
                                                              Game.imgLoader.loadImage("/Images/Monsters/Ground-Monsters/static" + (randomNum + 1) + " (4).png"));
            break;
            
            case 1:
                velX = -3;
                velY = 0;
                
                randomNum = rand.nextInt((8 - 0) + 1) + 0;
                
                width = movingSize[randomNum][0];
                height = movingSize[randomNum][1];
                
                this.width = width;
                this.height = height;
                
                if(randomNum == 6 || randomNum == 8) {
                    this.x *= 62;
                    if(Game.LEVEL == 2) {
                        if(y == 42)
                            this.y = y * 58 + 100;
                        else
                            this.y = y * 56;
                        
                    }
                    else
                        this.y *= 58;
                } else {
                    this.x *= 62;
                    this.y *= 62;
                }
                
                monsterMove = new Animation(7, width, height, Game.imgLoader.loadImage("/Images/Monsters/Ground-Monsters/moving" + (randomNum + 1) + " (1).png"), 
                                                              Game.imgLoader.loadImage("/Images/Monsters/Ground-Monsters/moving" + (randomNum + 1) + " (2).png"));
                    
                monsterMoveRight = new Animation(7, width, height, Game.imgLoader.loadImage("/Images/Monsters/Ground-Monsters/moving" + (randomNum + 1) + " (3).png"), 
                                                                   Game.imgLoader.loadImage("/Images/Monsters/Ground-Monsters/moving" + (randomNum + 1) + " (4).png"));
            break;
            
            case 2:
                velX = 0;
                velY = 0;
                
                if(Game.LEVEL == 3) 
                    this.y += 100;
                
                
                monsterMove = new Animation(10, width, height, Game.imgLoader.loadImage("/Images/Monsters/Spikes/spike1.png"), 
                                                               Game.imgLoader.loadImage("/Images/Monsters/Spikes/spike2.png"), 
                                                               Game.imgLoader.loadImage("/Images/Monsters/Spikes/spike3.png"), 
                                                               Game.imgLoader.loadImage("/Images/Monsters/Spikes/spike4.png"));
            break;
            
            case 3:
                velX = 0;
                velY = 0;
                
                randomNum = rand.nextInt((2 - 0) + 1) + 0;
                
                    
                width = spikeSize[randomNum][0];
                height = spikeSize[randomNum][1];
                
                this.width = width;
                this.height = height;
                
                this.x *= 62;
                this.y *= 58;
                
                monsterMove = new Animation(7, width, height, Game.imgLoader.loadImage("/Images/Monsters/Spikes/spike (" + (randomNum + 1) + ").png"));
            break;
        }
        
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
        
            if(type == 1)
                monsterMoveRight.runAnimation();

            monsterMove.runAnimation();
        }
    }

    
    public void draw(Graphics g) {
        if(dead) {
            monsterPuff.drawAnimation(g, x, y + height - 112 );
            
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
                if(getBoundsTop().intersects(block.getBounds())) {
                    y = tempObject.getY() + block.getHeight();
                    velY *= -1;
                }

                //Abajo
                if(getBounds().intersects(block.getBounds())) {
                    y = tempObject.getY() - height;
                    velY *= -1;
                }

                //Derecha
                if(getBoundsRight().intersects(block.getBounds())) {
                    x = tempObject.getX() - width;
                    velX *= -1;
                }

                //Izquierda
                if(getBoundsLeft().intersects(block.getBounds())) {
                    x = tempObject.getX() + block.getWidth();
                    velX *= -1;
                }
           }
        } 
            
        if(!dead) {
            if(type != 2 && type != 3)    
                for (int i = 0; i < world.bullet.size(); i++) {
                    tempObject = world.bullet.get(i);

                    if(getBoundsTop().intersects(tempObject.getBounds()) ||
                        getBounds().intersects(tempObject.getBounds()) ||
                        getBoundsRight().intersects(tempObject.getBounds()) ||
                        getBoundsLeft().intersects(tempObject.getBounds())) {
                        dead = true;
                        world.removeBullet(i);

                        monsterPuff = new Animation(6, 132, 112, 1, Game.imgLoader.dead[0],
                                                                         Game.imgLoader.dead[1],
                                                                         Game.imgLoader.dead[2],
                                                                         Game.imgLoader.dead[3],
                                                                         Game.imgLoader.dead[4]);
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
            if(type != 2 && type != 3) {
                if(player.inmortal == 0) { 
                    player.hitSound.play(false);
                    world.hud.HEALTH --;
                    world.hud.tick();
                    world.angel = new Angel(world.player.getX(), world.player.getY(), 72, 72, world.hud.HEALTH, ObjectId.Angel);
                }

                player.inmortal = 20;
            } else {
                world.hud.HEALTH = 0;
            }
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