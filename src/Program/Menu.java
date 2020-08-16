
package Program;

import Objects.Bullet;
import Objects.Environment;
import Objects.ObjectId;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.LinkedList;
import static javafx.scene.paint.Color.color;

public class Menu {
    private int width, height, x, x2, velX = -2;
    public int type;
    private Font font;
    
    public Rectangle start, help, quit, next, player1, player2, player3; 
    
    Animation title;
    
    public Menu(int x, int width, int height, int type) {
        this.width = width;
        this.height = height;
        
        this.type = type;
        
        if(type == 0)
        title = new Animation(8, width, height, Game.imgLoader.loadImage("/Images/Background/title1.png"), 
                                                Game.imgLoader.loadImage("/Images/Background/title2.png"), 
                                                Game.imgLoader.loadImage("/Images/Background/title3.png"), 
                                                Game.imgLoader.loadImage("/Images/Background/title4.png"), 
                                                Game.imgLoader.loadImage("/Images/Background/title5.png"), 
                                                Game.imgLoader.loadImage("/Images/Background/title6.png"), 
                                                Game.imgLoader.loadImage("/Images/Background/title7.png"), 
                                                Game.imgLoader.loadImage("/Images/Background/title8.png"), 
                                                Game.imgLoader.loadImage("/Images/Background/title10.png"), 
                                                Game.imgLoader.loadImage("/Images/Background/title11.png"), 
                                                Game.imgLoader.loadImage("/Images/Background/title12.png"), 
                                                Game.imgLoader.loadImage("/Images/Background/title13.png"), 
                                                Game.imgLoader.loadImage("/Images/Background/title14.png"), 
                                                Game.imgLoader.loadImage("/Images/Background/title15.png"), 
                                                Game.imgLoader.loadImage("/Images/Background/title16.png"));
    }
    
    public void tick() {
        if(type == 0)
            title.runAnimation();
    }

    public void draw(Graphics g) {
        
        switch(type) {
            
            //MAIN MENU SCREEN
            case 0:
                
                start = new Rectangle (width/3, height/2 + height/10, 135, 60);
                help = new Rectangle (width/3 + 225, height/2 + height/10, 135, 60);
                quit = new Rectangle (width/3 + 450, height/2 + height/10, 135, 60);
                
                title.drawAnimation(g, 0, -100);
                
                g.drawImage(Game.imgLoader.loadImage("/Images/Background/startButton.png"), start.x, start.y, start.width, start.height, null);
                g.drawImage(Game.imgLoader.loadImage("/Images/Background/helpButton.png"), help.x, help.y, help.width, help.height, null);
                g.drawImage(Game.imgLoader.loadImage("/Images/Background/quitButton.png"), quit.x, quit.y, quit.width, quit.height, null);
            break;
            
            //NEXT LEVEL SCREEN
            case 1: 
                next = new Rectangle (width/2 - width/4, height/2 - height/4, width/2, height/2);
                
                g.drawImage(Game.imgLoader.loadImage("/Images/Background/nextlevel.png"), next.x, next.y, next.width, next.height, null);
            break;
            
            //PLAYER SCREEN
            case 2: 
                player1 = new Rectangle (width/4, height/2 - 150, 202, 303);
                player2 = new Rectangle (player1.x + 350, height/2 - 150, 202, 303);
                player3 = new Rectangle (player2.x + 350, height/2 - 150, 202, 303);
                
                g.drawImage(Game.imgLoader.loadImage("/Images/Background/player.png"), 0, 0, width, height, null);
                
                g.drawImage(Game.imgLoader.loadImage("/Images/Background/playertitle.png"), width/3, 100, width/3, 116, null);
                
                g.drawImage(Game.imgLoader.loadImage("/Images/Background/ritButton2.png"), player1.x, player1.y, player1.width, player1.height, null);
                g.drawImage(Game.imgLoader.loadImage("/Images/Background/mrxButton2.png"), player2.x, player2.y, player2.width, player2.height, null);
                g.drawImage(Game.imgLoader.loadImage("/Images/Background/zoeButton2.png"), player3.x, player3.y, player3.width, player3.height, null);
                
                g.drawImage(Game.imgLoader.loadImage("/Images/Background/gotomenuplayer.png"), 15, 15, 306, 54, null);
            break;
            
            //GAMEOVER SCREEN
            case 3: 
                next = new Rectangle (width/2 - width/4, height/2 - height/4, width/2, height/2);
                
                g.drawImage(Game.imgLoader.loadImage("/Images/Background/gameover.png"), next.x, next.y, next.width, next.height, null);
                
                g.drawImage(Game.imgLoader.loadImage("/Images/Background/gotomenu.png"), 15, 15, 306, 54, null);
            break;
            
            //FINISH SCREEN
            case 4: 
                next = new Rectangle (width/2 - width/4, height/2 - height/4, width/2, height/2);
                
                g.drawImage(Game.imgLoader.loadImage("/Images/Background/finished.png"), next.x, next.y, next.width, next.height, null);
                
                g.drawImage(Game.imgLoader.loadImage("/Images/Background/gotomenuplayer.png"), 15, 15, 306, 54, null);
                
                g.drawImage(Game.imgLoader.loadImage("/Images/Background/gotocredits.png"), 15, 80, 306, 54, null);
            break;
            
            //CREDITS SCREEN
            case 5: 
                g.drawImage(Game.imgLoader.loadImage("/Images/Background/creditstitle.png"), width/3, 100, width/3, height-height*2/10, null);
                
                g.drawImage(Game.imgLoader.loadImage("/Images/Background/gotomenu.png"), 15, 15, 306, 54, null);
            break;
            
            //HELP SCREEN
            case 6: 
                g.drawImage(Game.imgLoader.loadImage("/Images/Background/help.png"), width/3, 100, width/3, height - height*2/10, null);
                
                g.drawImage(Game.imgLoader.loadImage("/Images/Background/gotomenuplayer.png"), 15, 15, 306, 54, null);
            break;
        }
    }
}
