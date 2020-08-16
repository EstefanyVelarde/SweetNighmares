package Program;

import Objects.AirMonster;
import Objects.Angel;
import java.awt.Graphics;
import java.util.LinkedList;

import Objects.ObjectId;
import Objects.Block;
import Objects.Boss;
import Objects.BossBullet;
import Objects.Bullet;
import Objects.Environment;
import Objects.Flag;
import Objects.GroundMonster;
import Objects.Mrx;
import static Objects.ObjectId.Boss;
import static Objects.ObjectId.BossBullet;
import Objects.Player;
import Objects.Rit;
import Objects.Zoe;
import static Program.Game.LEVEL;
import static Program.Game.imgLoader;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class World {
    
    public LinkedList<GameObject> back = new LinkedList<GameObject>();
    public LinkedList<GameObject> object = new LinkedList<GameObject>();
    public LinkedList<GameObject> block = new LinkedList<GameObject>();
    public LinkedList<GameObject> bullet = new LinkedList<GameObject>();
    
    public Player player;
            
    public Angel angel;
    
    public Boss boss;
    
    private GameObject tempObject;
    
    public Camera camera;
    
    public static int end;
    
    public HUD hud;
    
    public SoundEffect levelSound;
    
    public static enum PLAYER {
        RIT, 
        MRX,
        ZOE
    };
    
    public static PLAYER playerId = PLAYER.RIT;
    
    public World(Camera camera, HUD hud) {
        this.camera = camera;
        this.hud = hud;
    }
    
    public void loadLevel() throws IOException {
        Game.imgLoader.update(Game.LEVEL );
        
        
        levelSound = new SoundEffect("/Sound/level" + Game.LEVEL  + ".wav");
        
    	//Cargando imagenes del nivel
    	BufferedImage level = imgLoader.loadImage("/Images/Platforms/level" + Game.LEVEL + ".png");
        
    	int width = level.getWidth();
    	int height = level.getHeight();
        
    	//Codigo para saber en que pixel estamos 
        //https://www.dyclassroom.com/image-processing-project/how-to-get-and-set-pixel-value-in-java
    	for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int pixel = level.getRGB(i, j);
                int red = (pixel >> 16) & 0xff, 
                    green = (pixel >> 8) & 0xff, 
                    blue = (pixel) & 0xff;
                
                //END OF LEVEL
                if(red == 220 && green == 163 && blue == 163) 
                    end = i*62;
                
                //BACKGROUND
                if(red == 220 && green == 163 && blue == 198) 
                    addBack(new Environment(i*62, j*62, 1940, 1080, Game.LEVEL, this, ObjectId.Environment)); //BACKGROUND
                
                if(red == 157 && green == 109 && blue == 165) 
                    addBack(new Environment(i*62, j*62, 1940, 1080, 3, this, ObjectId.Environment)); //STATIC 
                
                
                //BLOCK
                if(red == 64 && green == 64 && blue == 65) 
                     addBlock(new Block(i*62, j*62, 62, 62, 0, ObjectId.Block)); //SUBGROUND
                
                if(red == 59 && green == 59 && blue == 79) 
                    addObject(new Block(i*62, j*62, 62, 62, 9, ObjectId.Block)); //SUBGROUND COLISION
                
                if(red == 255 && green == 255 && blue == 255) 
                    addObject(new Block(i*62, j*62, 62, 62, 1, ObjectId.Block)); //GROUND
                
                
                if(red == 138 && green == 138 && blue == 138) 
                    addObject(new Block(i*62, j*62, 209, 62, 2, ObjectId.Block)); //PLATFORM
                
                
                if(red == 255 && green == 0 && blue == 0) 
                    addObject(new Block(i*62, j*62, 62, 62, 3, ObjectId.Block)); //LAVA
                
                
                if(red == 236 && green == 236 && blue == 115) 
                    addBlock(new Block(i*62, j*62, 62, 62, 4, ObjectId.Block)); //SUBLAVA
                
                
                if(red == 90 && green == 142 && blue == 200) 
                    addBlock(new Block(i*62, j*62, 62, 62, 5, ObjectId.Block)); //BLOCK
                
                if(red == 41 && green == 84 && blue == 132) 
                    addObject(new Block(i*62, j*62, 62, 62, 12, ObjectId.Block)); //BLOCK COLLISION
                
                if(red == 255 && green == 255 && blue == 0) 
                    addObject(new Block(i*62, j*62, 62, 62, 6, ObjectId.Block)); //WALL 1
                
                 if(red == 255 && green == 150 && blue == 0) 
                    addObject(new Block(i*62, j*62, 62, 62, 7, ObjectId.Block)); //WALL 2
                 
                if(red == 0 && green == 0 && blue == 76) 
                    addObject(new Block(i*62, j*62, 62, 62, 8, ObjectId.Block)); //GROUND2
                
                if(red == 129 && green == 112 && blue == 112) 
                    addObject(new Block(i*62, j*62, 209, 62, 10, ObjectId.Block)); //PLATFORM (NOT MOVING)
                
                if(red == 54 && green == 54 && blue == 173) 
                    addObject(new Block(i*62, j*62, 209, 62, 11, ObjectId.Block)); //PLATFORM2
                
                if(red == 46 && green == 46 && blue == 123) 
                    addObject(new Block(i*62, j*62, 209, 62, 13, ObjectId.Block)); //PLATFORM2 (NOT MOVING)
                
                
                //FLAG
                if(red == 0 && green == 255 && blue == 0) 
                    addObject(new Flag(i*62, j*46, 163, 300, ObjectId.Flag)); //FLAG
                
                if(red == 109 && green == 160 && blue == 157) 
                    if(Game.LEVEL == 3) 
                        addObject(new Flag(i*62, j*62 - 10, 93, 200, ObjectId.Flag)); //FLAGBUG
                    else  
                        addObject(new Flag(i*62, j*64 - 125, 93, 200, ObjectId.Flag)); //FLAGBUG
                
                
                
                //AIR-MONSTER
                if(red == 0 && green == 255 && blue == 255)
                    addObject(new AirMonster(i*62, j*62, 112, 92, this, ObjectId.AirMonster));
                
                
                //GROUND-MONSTER
                if(red == 71 && green == 52 && blue == 2) 
                    addObject(new GroundMonster(i, j, 62, 62, 0, this, ObjectId.GroundMonster)); 
                
                
                if(red == 137 && green == 104 && blue == 13)
                    addObject(new GroundMonster(i, j, 62, 62, 1, this, ObjectId.GroundMonster)); //MOVING
                
                
                //SPIKE 
                if(red == 228 && green == 138 && blue == 29)
                    addObject(new GroundMonster(i*62, j*58, 150, 130, 2, this, ObjectId.GroundMonster));
                
                if(red == 50 && green == 56 && blue == 9)
                    addObject(new GroundMonster(i, j, 150, 130, 3, this, ObjectId.GroundMonster));
                
                
                //BOSS
                    if(red == 0 && green == 84 && blue == 37)
                        addObject(new Boss(i*58, j*56 + 32, 397, 443, this, ObjectId.Boss));
                    
                //PLAYER
                if(red == 0 && green == 0 && blue == 255) {
                    if(playerId == PLAYER.RIT) {
                        int shootSizes[] = {50, 100, 72, 32}; //RIT LEFT SHOOT SPRITE x - 50, BULLET Y, BULLET WIDTH, HEIGHT, 
                        int jumpSizes[][] = {{110, 124, 118, 108}, {176, 162, 166, 166}};
                        int downSizes[] = {90, 156, 7};
                        
                        player = new Rit(i*52, j*52, 90, 176, shootSizes, jumpSizes, downSizes, this, ObjectId.Player);
                        angel = new Angel(player.x, player.y, 72, 72, hud.HEALTH, ObjectId.Angel);
                    }
                    
                    if(playerId == PLAYER.MRX) {
                        int shootSizes[] = {20, 70, 32, 32};
                        int jumpSizes[][] = {{104, 104, 104, 104}, {164, 164, 164, 164}};
                        int downSizes[] = {112, 124, 8};
                        
                        player = new Mrx(i*52, j*52, 90, 176, shootSizes, jumpSizes, downSizes, this, ObjectId.Player);
                        angel = new Angel(player.x, player.y, 72, 72, hud.HEALTH, ObjectId.Angel);
                    }
                    
                    if(playerId == PLAYER.ZOE) {
                        int shootSizes[] = {110, 50, 72, 32};
                        int jumpSizes[][] = {{115, 115, 115, 115}, {175, 175, 175, 175}};
                        int downSizes[] = {112, 134, 8};
                        
                        player = new Zoe(i*52, j*52, 90, 176, shootSizes, jumpSizes, downSizes, this, ObjectId.Player);
                        angel = new Angel(player.x, player.y, 72, 72, hud.HEALTH, ObjectId.Angel);
                    }
                }
            }
        }
        
        levelSound.play(true);
    }
    
    public void nextLevel() throws IOException {
        clearList();
        
        camera.setX(0);
        camera.setY(0);
        
        LEVEL++;
        
        Game.state = Game.STATE.LOADING;
        
        loadLevel();
    }
    
    public void delete() {
        clearList();
        
        camera.setX(0);
        camera.setY(0);
        
        Game.state = Game.STATE.MENU;
        Game.hud.HEALTH = 3;
    }
    
    public void gameover() {
        clearList();
        
        camera.setX(0);
        camera.setY(0);
        
        Game.state = Game.STATE.GAMEOVER;
        Game.hud.HEALTH = 3;
    }

    public void tick() throws IOException {
        
        player.tick(object);
        
        if(!player.jumping && player.velX != 0 && !player.stepSound.isRunning() && !player.dead)
            player.stepSound.play(true);
        
        if(hud.HEALTH == 0) {
            
            if(player.nowImDead) {
                levelSound.stop();
                gameover();
            }
            player.dead = true;
            
            if(!player.deadSound.isRunning()) {
                levelSound.stop();
                player.stepSound.stop();
                player.deadSound.play(false);
            }
            
        } else {
            
            angel.tick(player.getX(), player.getY(), hud.HEALTH);

            Bullet temp;

            for(int i = 0; i < bullet.size(); i++) {
                temp = (Bullet) bullet.get(i);

                temp.tick(object);

                if((temp.x > temp.end ) || temp.deleteNow)
                    removeBullet(temp);
            }

            if(Game.LEVEL == 1)
                for(int i = 0; i < back.size(); i++) {
                    tempObject = back.get(i);

                    tempObject.tick(back);
                }

            for(int i = 0; i < object.size(); i++) {
                tempObject = object.get(i);

                tempObject.tick(object);

                if(tempObject.id == ObjectId.AirMonster) {
                    AirMonster monster = (AirMonster) tempObject;

                    if(monster.endMePlease) 
                        removeObject(monster);
                }

                if(tempObject.id == ObjectId.GroundMonster) {
                    GroundMonster monster = (GroundMonster) tempObject;

                    if(monster.endMePlease) 
                        removeObject(monster);
                }
                
                if(tempObject.id == ObjectId.Boss) {
                    Boss monster = (Boss) tempObject;

                    if(monster.delete) {
                        
                        removeObject(monster);
                        
                        levelSound.stop();
                        levelSound = new SoundEffect("/Sound/labanda.wav");
                        levelSound.play(true);
                        
                        Game.state = Game.STATE.FINISH;
                    }
                }
                
                if(tempObject.id == ObjectId.BossBullet) {
                    BossBullet bb = (BossBullet) tempObject;
                    
                    bb.tick(object);

                    if((bb.x < bb.end ))
                        removeObject(bb);
                }
            }
        }
    }

    public void draw(Graphics g) {
        
        for(int i = 0; i < back.size(); i++) {
            tempObject = back.get(i);

            tempObject.draw(g);
        }

        for(int i = 0; i < block.size(); i++) {
            tempObject = block.get(i);

            tempObject.draw(g);
        }

        for(int i = 0; i < object.size(); i++) {
            tempObject = object.get(i);

            tempObject.draw(g);
        }

        for(int i = 0; i < bullet.size(); i++) {
            tempObject = bullet.get(i);

            tempObject.draw(g);
        }

        player.draw(g);
        angel.draw(g, player.side);
        
    }
    
    public void clearList() {
        object.clear();
        block.clear();
        back.clear();
        bullet.clear();
    }

    public void addObject(GameObject object) {
        this.object.add(object);
    }
    
    public void addBullet(GameObject object) {
        this.bullet.add(object);
    }
    
    public void addBlock(GameObject object) {
        this.block.add(object);
    }
    
    public void addBack(GameObject object) {
        this.back.add(object);
    }

    public void removeObject(GameObject object) {
        this.object.remove(object);
    }
    
    public void removeObject(int object) {
        this.object.remove(object);
    }
    
    public void removeBullet(GameObject object) {
        this.bullet.remove(object);
    }
    
    public void removeBullet(int object) {
        this.bullet.remove(object);
    }
}
