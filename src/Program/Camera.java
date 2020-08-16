package Program;

import java.util.LinkedList;


public class Camera {
    private int x, y;
    
    public Camera(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void tick(GameObject player) {
        //Tweening algorithm https://gamedev.stackexchange.com/questions/138756/smooth-camera-movement-java

        int targetX = (int) (-player.getX() + Game.width / 3 + 16);
        int aux = x;
        aux += (targetX - x) * 0.1;
//        
//        if(Game.LEVEL != 2 && x > -2190)
            if(player.getY() - y < 100 || player.getY() > 500) {
                int targetY = (int) (-player.getY() + Game.height / 2 + 64);
                y += (targetY - y) * 0.1;
            } 
        
        if(aux < 0)
            x += (targetX - x) * 0.1;
        
//        System.out.println("CAMERA XY: "+x+" "+y);
//        System.out.println("PLAYER XY: "+player.getY()+" "+player.getY());
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
	
}
