package Program;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageLoader {
    public BufferedImage player[], angel[], boss[], block[], dead[], background, layer1, layer2, flag;
    
    public ImageLoader() {
        player = new BufferedImage[54];
        angel =  new BufferedImage[21];
        boss = new BufferedImage[28];
        block = new BufferedImage[15];
        dead = new BufferedImage[11];
        
        //DEAD IMAGES
        for (int i = 0; i < 5; i++) 
            dead[i] = loadImage("/Images/Effects/ground-explode" + (i + 1) + ".png");
        
        for (int i = 5; i < 10; i++) 
            dead[i] = loadImage("/Images/Effects/air-explode" + (i - 4) + ".png");
        
        
        //ANGEL IMAGES
        for (int i = 0; i < 4; i++) 
            angel[i] = loadImage("/Images/Characters/Angel/a" + (i+1) + ".png"); //LIFE 1
        
        for (int i = 4; i < 6; i++)
            angel[i] = loadImage("/Images/Characters/Angel/c2/a" + (i - 3) + ".png"); //LIFE 2
        
        for (int i = 6; i < 10; i++)
            angel[i] = loadImage("/Images/Characters/Angel/c3/a" + (i - 5) + ".png"); //LIFE 3
        
        for (int i = 10; i < 14; i++) 
            angel[i] = loadImage("/Images/Characters/Angel/b" + (i - 9) + ".png"); //LIFE 1 left
        
        for (int i = 14; i < 16; i++)
            angel[i] = loadImage("/Images/Characters/Angel/c2/b" + (i - 13) + ".png"); //LIFE 2 left
        
        for (int i = 16; i < 20; i++)
            angel[i] = loadImage("/Images/Characters/Angel/c3/b" + (i - 15) + ".png"); //LIFE 3 left
         
        
        //BLOCKS IMAGES
        block[0] = loadImage("/Images/Platforms/subground.png"); //SUBGROUND
        block[1] = loadImage("/Images/Platforms/ground.png");   //GROUND
        block[2] = loadImage("/Images/Platforms/tileFloating.png"); //PLATFORM
        block[3] = loadImage("/Images/Platforms/lava.png");     //LAVA
        block[4] = loadImage("/Images/Platforms/lava2.png");     //SUBLAVA
        block[5] = loadImage("/Images/Platforms/block.png");   //BLOCK
        block[6] = loadImage("/Images/Platforms/wall1.png");     //WALL 1
        block[7] = loadImage("/Images/Platforms/wall2.png");     //WALL 2
        block[8] = loadImage("/Images/Platforms/ground2.png");   //GROUND 2
        block[9] = loadImage("/Images/Platforms/subground.png");   //SUBGROUND
        block[10] = loadImage("/Images/Platforms/tileFloating.png"); //PLATFORM
        block[11] = loadImage("/Images/Platforms/tileFloating2.png"); //PLATFORM2
        block[12] = loadImage("/Images/Platforms/block.png");   //BLOCK COLL
        block[13] = loadImage("/Images/Platforms/tileFloating2.png"); //PLATFORM2
        
        
        //LEVEL FLAG
        flag = loadImage("/Images/Background/next.png");
        
        loadBoss();
        
        update(Game.LEVEL);
    }
    
    public void loadBoss() {
        //BOSS IMAGES
            for (int i = 0; i < 2; i++) 
            boss[i] = loadImage("/Images/Monsters/Boss/idle ("+ (i + 1) +").png"); //IDLE
            
        for (int i = 2; i < 5; i++) 
            boss[i] = loadImage("/Images/Monsters/Boss/attack ("+ (i - 1) +").png"); //ATTACK
        
        for (int i = 5; i < 7; i++) 
            boss[i] = loadImage("/Images/Monsters/Boss/hit ("+ (i - 4) +").png"); //HIT
        
        for (int i = 8; i < 12; i++) 
            boss[i] = loadImage("/Images/Monsters/Boss/dead ("+ (i - 7) +").png"); //DEAD
        
        for (int i = 12; i < 16; i++) 
            boss[i] = loadImage("/Images/Monsters/Boss/meteoro ("+ (i - 11) +").png"); //METEORO
        
        for (int i = 16; i < 20; i++) 
            boss[i] = loadImage("/Images/Monsters/Boss/missile ("+ (i - 15) +").png"); //MISIL
        
        for (int i = 20; i < 24; i++) 
            boss[i] = loadImage("/Images/Monsters/Boss/missileb ("+ (i - 19) +").png"); //MISIL 2
    }
    
    public void loadPlayer() {
        //PLAYER IMAGES
        for (int i = 0; i < 4; i++) 
            player[i] = loadImage("/Images/Characters/" + World.playerId + "/idle"+ (i+1) +".png"); //IDLE
        
        for (int i = 4; i < 8; i++) 
            player[i] = loadImage("/Images/Characters/" + World.playerId + "/idleleft"+ (i-3) +".png"); //IDLE LEFT
        
        for (int i = 8; i < 12; i++) 
            player[i] = loadImage("/Images/Characters/" + World.playerId + "/running"+ (i-7) +".png"); //RUNNING
        
        for (int i = 12; i < 16; i++) 
            player[i] = loadImage("/Images/Characters/" + World.playerId + "/runningleft"+ (i-11) +".png"); //RUNNING LEFT
        
        for (int i = 16; i < 21; i++) 
            player[i] = loadImage("/Images/Characters/" + World.playerId + "/jumping"+ (i-15) +".png"); //JUMPING 
        
        for (int i = 21; i < 26; i++) 
            player[i] = loadImage("/Images/Characters/" + World.playerId + "/jumpingleft"+ (i-20) +".png"); //JUMPING LEFT
        
        for (int i = 27; i < 30; i++) 
            player[i] = loadImage("/Images/Characters/" + World.playerId + "/shooting"+ (i-26) +".png"); //SHOOTING 
        
        player[30] = loadImage("/Images/Characters/" + World.playerId + "/Bullet-B.png"); //BULLET
        
        player[31] = loadImage("/Images/Characters/" + World.playerId + "/Bullet-B2.png"); //BULLET LEFT
        
        for (int i = 32; i < 35; i++) 
            player[i] = loadImage("/Images/Characters/" + World.playerId + "/shootingleft"+ (i-31) +".png"); //SHOOTING
        
        player[35] = loadImage("/Images/Characters/" + World.playerId + "/down.png"); //DOWN
        
        player[36] = loadImage("/Images/Characters/" + World.playerId + "/downleft.png"); //DOWN left
        
        for (int i = 37; i < 45; i++) 
            player[i] = loadImage("/Images/Characters/" + World.playerId + "/gameover"+ (i-36) +".png"); //DEAD RIGHT

        for (int i = 45; i < 53; i++) 
            player[i] = loadImage("/Images/Characters/" + World.playerId + "/gameoverleft"+ (i-44) +".png"); //DEAD LEFT
        
    }
    
    public void update(int level) {
        //LEVEL BACKGROUND & LAYERS
        background = loadImage("/Images/Background/level" + level + ".png"); //BACKGROUND
        layer1 = loadImage("/Images/Background/layer1_level" + level + ".png"); //LAYER 1
        layer2 = loadImage("/Images/Background/layer2_level" + level + ".png"); //LAYER 2
        
    }
    
    public BufferedImage loadImage(String path) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResource(path));
        } catch (IOException e) {

        }
        return image;
    }
}
