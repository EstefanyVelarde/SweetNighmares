package Program;

import Objects.Bullet;
import Objects.ObjectId;
import static Objects.ObjectId.Bullet;
import static Objects.ObjectId.Player;
import Objects.Player;
import static Program.Game.world;
import Program.GameObject;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import Program.World;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KeyInput implements KeyListener {
    World world;
    Game game;
    
    private boolean shoot = false;
    
    public void update(World world, Game game) {
        this.world = world;
        this.game = game;
        shoot = false;
        
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        
        if(Game.STATE.GAME == Game.state) {
            
            Player player = world.player;
                if(!player.dead) {
                    if (key == KeyEvent.VK_D) {
                        player.setVelX(7);
                        player.stepSound.play(true);
                    }

                    if(key == KeyEvent.VK_A) {
                        player.setVelX(-7);
                        player.stepSound.play(true);
                    }

                    if(key == KeyEvent.VK_W && !player.isJumping()) {
                        player.setJumping(true);
                        player.setVelY(-17);
                        
                        player.stepSound.stop();
                        player.jumpSound.play(false);
                    }

                    if(key == KeyEvent.VK_S) {
                        player.setDown(true);
                    }

                    if(key == KeyEvent.VK_SPACE) {
                        if(!shoot) {
                            if(player.side)
                                world.addBullet(new Bullet(player.getX() + player.width, player.getY() + player.shootSizes[1],  player.shootSizes[2], player.shootSizes[3], player.side, ObjectId.Bullet));
                            else {
                                if(World.playerId == World.PLAYER.ZOE)
                                    world.addBullet(new Bullet(player.getX() - 150, player.getY() + player.shootSizes[1],  player.shootSizes[2], player.shootSizes[3], player.side, ObjectId.Bullet));
                                else
                                    world.addBullet(new Bullet(player.getX(), player.getY() + player.shootSizes[1],  player.shootSizes[2], player.shootSizes[3], player.side, ObjectId.Bullet));
                            }
                            player.shooting = true;
                        }                   
                        
                        if(!shoot)
                            player.shootSound.play(false);
                        
                        shoot = true;
                    }


                if(key == KeyEvent.VK_ESCAPE) {
                    
                    world.levelSound.stop();
                    
                    game.running = false;
                    
                    game.gameThread.interrupt();

                    try {
                        game.gameThread.join();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(KeyInput.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    game.start();
                }
            }    
        } else { 
        
            if(Game.state == Game.STATE.MENU) {
                if(key == KeyEvent.VK_ENTER || key == KeyEvent.VK_SPACE) {
                    Game.state = Game.STATE.PLAYER;
                }

                if(key == KeyEvent.VK_ESCAPE) {
                    System.exit(0);
                }
            } else {
        
                if(Game.state == Game.STATE.PLAYER) {
                    if(key == KeyEvent.VK_ENTER || key == KeyEvent.VK_SPACE) {
                        Random rand = new Random();
                        int randomNum = rand.nextInt((3 - 1) + 1) + 1;

                        switch(randomNum) {
                            case 1: World.playerId = World.PLAYER.RIT; break;
                            case 2: World.playerId = World.PLAYER.MRX; break;    
                            case 3: World.playerId = World.PLAYER.ZOE; break;
                        }               

                        Game.imgLoader.loadPlayer();
                        
                        try {
                            Game.world.loadLevel();
                        } catch (IOException ex) {
                            Logger.getLogger(KeyInput.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        Game.state = Game.STATE.GAME;
                        
                        Game.menuSound.stop();
                        
                    }

                    if(key == KeyEvent.VK_ESCAPE) {
                        Game.state = Game.STATE.MENU;
                    }
                } else {
        
                    if(Game.state == Game.STATE.HELP) {
                        if(key == KeyEvent.VK_ESCAPE) {
                            Game.state = Game.STATE.MENU;
                        }
                    }
                    
                    if(Game.state == Game.STATE.CREDITS) {
                        if(key == KeyEvent.VK_ENTER || key == KeyEvent.VK_SPACE) {
                            world.levelSound.stop();
                            
                            game.running = false;

                            game.gameThread.interrupt();

                            try {
                                game.gameThread.join();
                            } catch (InterruptedException ex) {
                                Logger.getLogger(KeyInput.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            game.start();
                        }
                    }

                    if(Game.state == Game.STATE.LOADING) {
                        if(key == KeyEvent.VK_ENTER || key == KeyEvent.VK_SPACE) {
                            Game.state = Game.STATE.GAME;
                        }
                    }
        
                    if(Game.state == Game.STATE.GAMEOVER) {
                        if(key == KeyEvent.VK_ENTER || key == KeyEvent.VK_SPACE) {
                            Game.state = Game.STATE.MENU;
                            
                            world.player.deadSound.stop();
                            
                            game.running = false;

                            game.gameThread.interrupt();

                            try {
                                game.gameThread.join();
                            } catch (InterruptedException ex) {
                                Logger.getLogger(KeyInput.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            game.start();
                        }
                    }
        
                    if(Game.state == Game.STATE.FINISH) {
                        if(key == KeyEvent.VK_ENTER || key == KeyEvent.VK_SPACE) {
                            Game.state = Game.STATE.CREDITS;
                        }

                        if(key == KeyEvent.VK_ESCAPE) {
                            world.levelSound.stop();
                            
                            game.running = false;

                            game.gameThread.interrupt();

                            try {
                                game.gameThread.join();
                            } catch (InterruptedException ex) {
                                Logger.getLogger(KeyInput.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            game.start();
                        }
                    }
                }
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if(Game.STATE.GAME == Game.state) {
            Player player = world.player;
            if(!player.dead) {
                if (key == KeyEvent.VK_D) {
                    player.setVelX(0);
                    player.side = true;
                    
                    player.stepSound.stop();
                }

                if(key == KeyEvent.VK_A) {
                    player.setVelX(0); 
                    player.side = false;
                    
                    player.stepSound.stop();
                }

                if(key == KeyEvent.VK_S) {
                    player.setDown(false);
                    player.setY(-player.downSizes[2]);
                }

                if(key == KeyEvent.VK_SPACE) {
                    shoot = false;
                }
            } else {
                player.setVelX(0);
            }
        }
    }

    public void keyTyped(KeyEvent arg0) {


    }
}
