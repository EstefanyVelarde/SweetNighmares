
package Program;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Animation {
    public int speed, width, height, type = 0;
    
    private int pos = 0;
    
    public int count = 0, nImages;
    
    private BufferedImage[] images;
    private BufferedImage currentImg;
    
    public Animation(int speed, int width, int height, BufferedImage... arr) {
        this.speed = speed;
        this.width = width;
        this.height = height;
        
        images = new BufferedImage[arr.length];
        
        for (int i = 0; i < arr.length; i++) 
            images[i] = arr[i];
        
        nImages = arr.length;
    }
    
    public Animation(int speed, int width, int height, int type, BufferedImage... arr) {
        this.speed = speed;
        this.width = width;
        this.height = height;
        this.type = type;
        
        images = new BufferedImage[arr.length];
        
        for (int i = 0; i < arr.length; i++) 
            images[i] = arr[i];
        
        nImages = arr.length;
        
        currentImg = images[0];
    }
    
    public void runAnimation() {
        pos++;
        
        if(pos > speed) {
            pos = 0;
            nextImage();
        }
    }
    
    private void nextImage() {
        if(count < nImages)
            currentImg = images[count];
        
        count++;
        
        if(count >= nImages) {
            if(type == 0)
                count = 0;
        }
    }
    
    public void drawAnimation(Graphics g, int x, int y) {
        g.drawImage(currentImg, x, y, width, height, null);
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
}
