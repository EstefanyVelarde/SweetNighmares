
package Objects;

import Program.Animation;
import Program.Game;
import Program.GameObject;
import Program.HUD;
import Program.SoundEffect;
import Program.World;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Boss extends GameObject {
    private World world;
    
    HUD hud;
    
    public SoundEffect shootSound, deadSound, hitSoundw;
    
    private float gravity = 0.5f;
    private final float speed = 20;
    
    private Animation bossIdle, attack, attack2, bossDead, bossHit, monsterPuff;
    private Random rand;
    
    public boolean dead = false, inmortal =  false, delete = false, gotHit = false, battlesong;
    public int type, randomNum, killing, stopHittin;
    public int bullet, HEALTH = 30;
    
    public Boss(int x, int y, int width, int height, World world, ObjectId id) {
        super(x, y, width, height, false, id);
        
        this.world = world;
        
        try {
            deadSound = new SoundEffect("/Sound/puff.wav");
            shootSound = new SoundEffect("/Sound/bossshoot.wav");
        } catch (IOException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        hud = new HUD();
        
        rand = new Random();
        
        bossIdle = new Animation(25, 397, 443, Game.imgLoader.boss[0], Game.imgLoader.boss[1]);
        
        attack = new Animation(20, 588, 425, 1, Game.imgLoader.boss[2], Game.imgLoader.boss[3], Game.imgLoader.boss[4]);
        
        attack2 = new Animation(30, 588, 425, 1, Game.imgLoader.boss[2], Game.imgLoader.boss[3]);
        
        bossHit = new Animation(20, 480, 450, 1, Game.imgLoader.boss[5], Game.imgLoader.boss[6], Game.imgLoader.boss[5], Game.imgLoader.boss[6]);
    }

    
    public void tick(LinkedList<GameObject> object) {
        if(world.player.x >= 7000) {
            if(!battlesong) {
                
                try {
                    world.levelSound.stop();
                    world.levelSound = new SoundEffect("/Sound/battle.wav");
                    world.levelSound.play(true);
                } catch (IOException ex) {
                    Logger.getLogger(Boss.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                battlesong = true;
            }
            
            if(!inmortal && !gotHit && !dead) {
                randomNum = rand.nextInt((100 - 1) + 1) + 1;

                if(randomNum == 10) {
                    inmortal = true;

                    randomAttack();
                }
            }

            collision(object);
            
            hud.tick(HEALTH);
            
            if(inmortal) {
                if(killing == 1)
                    attack.runAnimation();
                else
                    attack2.runAnimation();
            } else {
                if(gotHit) {
                    bossHit.runAnimation();
                } else {
                    if(dead) { 
                        if(!deadSound.isRunning()) {
                            SoundEffect laugh;
                            try {
                                laugh = new SoundEffect("/Sound/Boss laugh.wav");
                                laugh.play(false);
                            } catch (IOException ex) {
                                Logger.getLogger(Boss.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            deadSound.play(false);
                        }
                        monsterPuff.runAnimation();
                    } else 
                        bossIdle.runAnimation();
                }
            }
        }
    }
    
    public void randomAttack() {
        randomNum = rand.nextInt((10- 1) + 1) + 1;
        
        if(randomNum%2 == 0) {
            killing = 1;
            world.addObject(new BossBullet(x - 160, y + height/2, 300, height - height/2, 1, world, ObjectId.BossBullet));
        } else {
            killing = 2;
            world.addObject(new BossBullet(x + 300, y, width - 50, 45, 2, world, ObjectId.BossBullet));
        }
        
        shootSound.play(false);
    }
    
    public void draw(Graphics g) {
        hud.draw(g, HEALTH, x, y);
        
        if(world.player.x >= 7150) {
            if(inmortal && !gotHit && !dead) {
                if(killing == 1) {
                    attack.drawAnimation(g, x - 160, y + 20);

                    if(attack.count >= attack.nImages ) {
                        inmortal = false;
                        attack.count = 0;
                    }

                } else {
                    attack2.drawAnimation(g, x - 160, y + 20);

                    if(attack2.count >= attack2.nImages ) {
                        inmortal = false;
                        attack2.count = 0;
                    }
                }

            } else {
                if(gotHit) {
                    bossHit.drawAnimation(g, x, y);

                    if(bossHit.count >= bossHit.nImages ) {
                        gotHit = false;
                        bossHit.count = 0;
                    }

                } else {

                    if(dead) {
                        monsterPuff.drawAnimation(g, x, y);

                        if(monsterPuff.count >= monsterPuff.nImages ) 
                            delete = true;
                    } else {
                        bossIdle.drawAnimation(g, x, y);
                    }
                }
            }
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
            
        if(!dead && !inmortal) {
            for (int i = 0; i < world.bullet.size(); i++) {
                Bullet b = (Bullet) world.bullet.get(i);
                
                if(!b.delete) {
                    if(getBoundsTop().intersects(b.getBounds()) ||
                        getBounds().intersects(b.getBounds()) ||
                        getBoundsRight().intersects(b.getBounds()) ||
                        getBoundsLeft().intersects(b.getBounds())) {

                        HEALTH--;

                        if(HEALTH <= 0) {
                            dead = true;
                            
                            monsterPuff = new Animation(10, width, height, 1, Game.imgLoader.dead[0],Game.imgLoader.dead[1],
                                                                        Game.imgLoader.dead[2],Game.imgLoader.dead[3],
                                                                        Game.imgLoader.dead[4]);
                        } else {
                            if(stopHittin == 10){
                                randomAttack();
                                stopHittin = 0;
                            }else {
                                stopHittin++;
                                gotHit = true;
                            }
                        }
                        
                        b.delete = true;
                    }
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
