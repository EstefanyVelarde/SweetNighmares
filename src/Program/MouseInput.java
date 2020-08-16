/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Program;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Estefany
 */
public class MouseInput implements MouseListener {
    Menu menu;
    
    public void update(Menu menu) {
        this.menu = menu;
    }
    
    public void mouseClicked(MouseEvent e) {
        
    }
    
    public void mousePressed(MouseEvent e) {
        int x = e.getX(), y = e.getY();
        
        if(Game.state == Game.STATE.MENU) {
            //START 
            if(x >= menu.start.x  && x <= menu.start.x + 135) {
                if(y >= menu.start.y && y <= menu.start.y + 60) {
                    Game.state = Game.STATE.PLAYER;
                }
            }
            
            //HELP 
            if(x >= menu.help.x  && x <= menu.help.x + 135) {
                if(y >= menu.help.y && y <= menu.help.y + 60) {
                    Game.state = Game.STATE.HELP;
                }
            }

            //QUIT
            if(x >= menu.quit.x  && x <= menu.quit.x + 135) {
                if(y >= menu.quit.y && y <= menu.quit.y + 60) {
                   System.exit(0);
                }
            }
            
        } else {
            if(Game.state == Game.STATE.PLAYER) {
                
                //PLAYER 1
                if(x >= menu.player1.x  && x <= menu.player1.x + menu.player1.width) {
                    if(y >= menu.player1.y && y <= menu.player1.y + menu.player1.height) {
                        World.playerId = World.PLAYER.RIT;
                        Game.imgLoader.loadPlayer();
                        try {
                            Game.world.loadLevel();
                        } catch (IOException ex) {
                            Logger.getLogger(MouseInput.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        Game.state = Game.STATE.GAME;
                        Game.menuSound.stop();
                    }
                }
                
                //PLAYER 2
                if(x >= menu.player2.x  && x <= menu.player2.x + menu.player2.width) {
                    if(y >= menu.player2.y && y <= menu.player2.y + menu.player2.height) {
                        World.playerId = World.PLAYER.MRX;
                        Game.imgLoader.loadPlayer();
                        try {
                            Game.world.loadLevel();
                        } catch (IOException ex) {
                            Logger.getLogger(MouseInput.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        Game.state = Game.STATE.GAME;
                        Game.menuSound.stop();
                    }
                }
                
                //PLAYER 3
                if(x >= menu.player3.x  && x <= menu.player3.x + menu.player3.width) {
                    if(y >= menu.player3.y && y <= menu.player3.y + menu.player3.height) {
                        World.playerId = World.PLAYER.ZOE;
                        Game.imgLoader.loadPlayer();
                        try {
                            Game.world.loadLevel();
                        } catch (IOException ex) {
                            Logger.getLogger(MouseInput.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        Game.state = Game.STATE.GAME;
                        Game.menuSound.stop();
                    }
                }
            }
            
            if(Game.state == Game.STATE.LOADING) {
                if(x >= menu.next.x  && x <= menu.next.x + menu.next.width) {
                    if(y >= menu.next.y && y <= menu.next.y + menu.next.height) {
                        Game.state = Game.STATE.GAME;
                    }
                }
            }
            
            if(Game.state == Game.STATE.GAMEOVER) {
                if(x >= menu.next.x  && x <= menu.next.x + menu.next.width) {
                    if(y >= menu.next.y && y <= menu.next.y + menu.next.height) {
                        Game.state = Game.STATE.MENU;
                    }
                }
            }
            
            if(Game.state == Game.STATE.FINISH) {
                if(x >= menu.next.x  && x <= menu.next.x + menu.next.width) {
                    if(y >= menu.next.y && y <= menu.next.y + menu.next.height) {
                        Game.state = Game.STATE.MENU;
                    }
                }
            }
        }
        
    }

    
    public void mouseReleased(MouseEvent e) {
        
    }

    
    public void mouseEntered(MouseEvent e) {
        
    }

    
    public void mouseExited(MouseEvent e) {
        
    }
    
}
