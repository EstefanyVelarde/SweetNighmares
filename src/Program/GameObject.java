package Program;

import Objects.ObjectId;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public abstract class GameObject {
    protected ObjectId id;
    
    protected int width, height;
    public int x;
    public int y;
    protected int nImg, pos;
    protected float velX = 0, velY = 0;
    protected boolean falling = true, jumping = false, side = true;
    
    protected BufferedImage image[];
    
    public GameObject(int x, int y, int width, int height, boolean side, ObjectId id) {
        this.x = x;
        this.y = y;
        
        this.width = width;
        this.height = height;
        
        this.id = id;
        
        this.side = side;
    }
    
    public abstract void tick(LinkedList<GameObject> object);
    public abstract void draw(Graphics g);
    public abstract Rectangle getBounds();
    
    //Identificador del objeto, jugadores/villanos/plataformas seran GameObject 
    public ObjectId getId() {
    	return id;
    }
    
    public boolean getSide() {
        return side;
    }

    public void setSide(boolean side) {
        this.side = side;
    }
    
    public boolean isFalling() {
        return falling;
    }

    public void setFalling(boolean falling) {
        this.falling = falling;
    }

    public boolean isJumping() {
        return jumping;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public void setWidth(int width) {
        this.width = width;
    }
    
    public void setHeight(int height) {
        this.height = height;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.x = x;
    }

    public float getVelX() {
        return velX;
    }

    public float getVelY() {
        return velY;
    }

    public void setVelX(float velX) {
        this.velX = velX;
    }

    public void setVelY(float velY) {
        this.velY = velY;
    }
    
    
}
