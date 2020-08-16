package Objects;

import Program.Animation;
import Program.Camera;
import Program.Game;
import Program.GameObject;
import Program.HUD;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.LinkedList;

import Program.ImageLoader;
import Program.SoundEffect;
import Program.World;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Player extends GameObject {
    public SoundEffect shootSound, deadSound, nextSound, stepSound, hitSound, jumpSound;
    
    public int inmortal = 0;
    
    public int shootSizes[], jumpSizes[][], downSizes[];
    
    private float gravity = 0.5f;
    private final float speed = 20;
    
    public boolean down =  false, shooting =  false, dead = false, nowImDead =  false;
    
    private World world;
    
    protected Animation playerRunRight, playerRunLeft, 
                        playerIdleRight, playerIdleLeft, 
                        playerShootRight, playerShootLeft,
                        playerDeadRight, playerDeadLeft;
    
    ImageLoader imgLoader = Game.getImgLoader();

    public Player(int x, int y, int width, int height, World world, ObjectId id) {
        super(x, y, width, height, true, id);
        
        this.world = world;
        
        try {
            deadSound = new SoundEffect("/Sound/playerdead.wav");
            stepSound = new SoundEffect("/Sound/playerstep.wav");
            jumpSound = new SoundEffect("/Sound/playerjump.wav");
            hitSound = new SoundEffect("/Sound/playerhit.wav");
            nextSound = new SoundEffect("/Sound/next.wav");
        } catch (IOException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void tick(LinkedList<GameObject> object) {
        x += velX;
        y += velY; //Esto es gravedad

        if(velX > 0)
            side = true;

        if(velX < 0) 
            side = false;

        if(falling || jumping) { //Aumenta la velocidad de caida dependiendo de la gravedad
            velY += gravity;

            if(velY > speed) //Mantenemos velocidad <= 20
                velY = speed;
        }

        try {
            //preguntamos si colisiona con algun objeto del mundo
            collision(object);
        } catch (IOException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }

        if(dead) {
            if(side)
                playerDeadRight.runAnimation();
            else
                playerDeadLeft.runAnimation();
        } else {
            
            playerIdleRight.runAnimation();
            playerIdleLeft.runAnimation();

            playerRunRight.runAnimation();
            playerRunLeft.runAnimation();

            if(shooting) {
                if(side) 
                    playerShootRight.runAnimation();
                else
                    playerShootLeft.runAnimation();
            }
        }
    }

    public void draw(Graphics g) {
        if(dead) {
            if(side) {
                if(World.playerId == World.playerId.RIT)
                    playerDeadRight.drawAnimation(g, x - 100, y);
                else {
                    if(World.playerId == World.playerId.ZOE)
                        playerDeadRight.drawAnimation(g, x, y - 20);
                    else
                        playerDeadRight.drawAnimation(g, x, y);
                }
                if(playerDeadRight.count >= playerDeadRight.nImages) 
                    nowImDead = true;
                
            } else {
                if(World.playerId == World.playerId.ZOE)
                    playerDeadLeft.drawAnimation(g, x - 100, y - 20);
                else 
                    playerDeadLeft.drawAnimation(g, x, y);
                
                if(playerDeadLeft.count >= playerDeadLeft.nImages)
                    nowImDead = true;
            }
        } else {
            if(inmortal > 0) {
                if(inmortal%2 == 0) {
                    drawPlayer(g);

                } 
                inmortal--;

                    if(inmortal < 0)
                        inmortal = 0;
            } else {
                drawPlayer(g);
            }
        }
        
    }
    
    public void drawPlayer(Graphics g) {
        if(down && velX == 0) {
            width =  downSizes[0];
            height = downSizes[1];
            y += downSizes[2];
            
            if(side)
                g.drawImage(imgLoader.player[35], x, y, width, height, null);
            else
                g.drawImage(imgLoader.player[36], x, y, width, height, null);
        } else {
            if(shooting) {
                if(side) {
                    width = playerShootRight.getWidth();
                    height = playerShootRight.getHeight();

                    playerShootRight.drawAnimation(g, x, y);
                    
                    if(playerShootRight.count >= playerShootRight.nImages) {
                        shooting = false;
                        playerShootRight.count = 0;
                    }
                    
                } else {
                    width = playerShootLeft.getWidth();
                    height = playerShootLeft.getHeight();

                    playerShootLeft.drawAnimation(g, x - shootSizes[0], y);
                    
                    if(playerShootLeft.count >= playerShootLeft.nImages) {
                        shooting = false;
                        playerShootLeft.count = 0;
                    }
                }
                
            } else {

                if(jumping) {

                    if(side) {
                        if(velY < -7)
                            g.drawImage(imgLoader.player[16], x, y, jumpSizes[0][0], jumpSizes[1][0], null);
                        else if(velY < -3)
                            g.drawImage(imgLoader.player[17], x, y, jumpSizes[0][1], jumpSizes[1][1], null);
                        else if(velY < 5)
                            g.drawImage(imgLoader.player[18], x, y, jumpSizes[0][2], jumpSizes[1][2], null);
                        else if(velY <= 20)
                            g.drawImage(imgLoader.player[19], x, y, jumpSizes[0][3], jumpSizes[1][3], null);
                    } else {
                        if(velY < -7)
                            g.drawImage(imgLoader.player[21], x, y, jumpSizes[0][0], jumpSizes[1][0], null);
                        else if(velY < -3)
                            g.drawImage(imgLoader.player[22], x, y, jumpSizes[0][1], jumpSizes[1][1], null);
                        else if(velY < 5)
                            g.drawImage(imgLoader.player[23], x, y, jumpSizes[0][2], jumpSizes[1][2], null);
                        else if(velY <= 20)
                            g.drawImage(imgLoader.player[24], x, y, jumpSizes[0][3], jumpSizes[1][3], null);
                    }

                } else {

                    if(velX != 0) {
                        width = playerRunRight.getWidth();
                        height = playerRunRight.getHeight();

                        if(side)
                            playerRunRight.drawAnimation(g, x, y);
                        else
                            playerRunLeft.drawAnimation(g, x, y);
                    } else {
                        width = playerIdleRight.getWidth();
                        height = playerIdleRight.getHeight();

                        if(side)
                            playerIdleRight.drawAnimation(g, x, y);
                        else
                            playerIdleLeft.drawAnimation(g, x, y);
                    }
                }
            }
        }
    }
    
    private void collision(LinkedList<GameObject> object) throws IOException {
        for (int i = 0; i < world.object.size(); i++) {
            GameObject tempObject = world.object.get(i);

            if(tempObject.getId() == ObjectId.Block) {
                Block block = (Block) tempObject;
                
                //Arriba
                if(getBoundsTop().intersects(block.getBounds())) {
                    //Procuramos que la altura de nuestro player no choque con el block
                    y = tempObject.getY() + block.getHeight();
                    //Detenemos el salto
                    velY = 0;
                }

                //Abajo
                if(getBounds().intersects(block.getBounds())) {
                    if(block.type == 3)
                        world.hud.HEALTH = 0;
                    
                    //Posicionamos nuestro player justo arriba de nuestro block
                    y = block.getY() - height;
                    //Hacemos que nuestro player se detenga
                    velY = 0;
                    falling = false;
                    jumping = false;
                    
                    if(block.type == 2)
                        x += block.velX;
                } else {
                    //si no esta colisionando entonces sigue cayendo
                    falling = true;
                }

                //Derecha
                if(getBoundsRight().intersects(block.getBounds())) {
                    x = tempObject.getX() - width;
                }

                //Izquierda
                if(getBoundsLeft().intersects(block.getBounds())) {
                    x = tempObject.getX() + block.getWidth();
                }
                
           } else //COLISION CON BANDERA PARA CAMBIO DE NIVEL
                if(tempObject.getId() == ObjectId.Flag) {
                    if(getBounds().intersects(tempObject.getBounds())) {
                        if(Game.LEVEL == 3) {
                            x =  7100;
                            y = 1740;
                            inmortal = 10;
                        } else {
                            world.levelSound.stop();

                            stepSound.stop();

                            nextSound.play(false);
                            world.nextLevel();
                        }
                    }
                } 
        }
    }
    
    public void setDown(boolean down) {
        this.down = down;
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
