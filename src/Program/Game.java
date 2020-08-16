
package Program;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Game extends Canvas implements Runnable {
    public static Thread gameThread;
    boolean running;
    
    private static Graphics graphics;
    
    public static ImageLoader imgLoader;
    
    public static int width, height;
    public static int LEVEL;
    
    //Mundo, contiene nuestra lista objetos
    public static World world;
    
    public static HUD hud;
    
    public static Menu menu;
    
    static Camera camera;
    
    public static SoundEffect menuSound;
    
    KeyInput key = new KeyInput();
    MouseInput  mouse = new MouseInput();
    
    public static enum STATE {
        MENU, 
        PLAYER,
        GAME, 
        LOADING,
        GAMEOVER,
        FINISH,
        CREDITS,
        HELP
    };
    
    public static STATE state;
    
    Game() {
        
        this.addKeyListener(key);
        this.addMouseListener(mouse);
    }
    
    private void init() throws IOException {
        
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        width = gd.getDisplayMode().getWidth();
        height = gd.getDisplayMode().getHeight();
        
        state = STATE.MENU;
        LEVEL = 1;
        
        menuSound = new SoundEffect("/Sound/menu.wav");
    	imgLoader = new ImageLoader();
        menu = new Menu(0, width, height, 0);
    	camera = new Camera(0, 0);
        hud = new HUD();
    	world = new World(camera, hud);
        
        world.delete();
        
        menuSound.play(true);
        key.update(world, this);
        mouse.update(menu);
    }

    public synchronized void start() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    
    public void run() {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        running =  true;
        
    	this.requestFocus();
        
        //Game loop code https://stackoverflow.com/questions/18283199/java-main-game-loop
        //Ejecuta el juego a 60 ticks por segundo, basicamente configura el juego a FPS a 60 y lo renderiza continuamente
        //Por lo que la parte de renderizacion de nuestro juego se procesa tan rapido como la computadora puede funcionar
        //Hace que las imagenes se muestren muy rapido, pero actualizandose a una cantidad fija de 60 (Ticks).
        
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int updates = 0;
        int frames = 0;
        
        while(running){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1){
                if (gameThread.isInterrupted()) 
                    break;
                
                try {
                    tick();
                } catch (IOException ex) {
                    Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                }
                updates++;
                delta--;
            }
            
            if (gameThread.isInterrupted()) 
                break;
            
            draw();
                
            frames++;

            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
//                System.out.println("FPS: " + frames + " TICKS: " + updates);
                frames = 0;
                updates = 0;
            }
        }
    }
    
    private void tick() throws IOException { //Actualizaciones
        if(state == STATE.MENU)
            menu.tick();
        
        if(state == STATE.GAME) {
            //Llamamos al manejador de gameObjects (nuestro mundo) para que en cada tick se vayan actualizando nuestros elementos
            //Aqui no se dibuja la imagen, solo se actualizan los valores de nuestros objetos, cada que nuestro Game Loop llame a esta funcion
            world.tick(); 
            hud.tick();
            
            camera.tick(world.player);
        }
    }
    
    private void draw() { //Graficos
        BufferStrategy bs = this.getBufferStrategy();
        
        //Este metodo se va a estar llamando varias veces
        //Aseguramos que nuestro buffer n-o se inicialice otra vez en el transcurso
        if(bs == null) {
            this.createBufferStrategy(3); //Se pone la cantidad de buffers que queremos detras de la imagen (triple buffering)
            return;
        }
        
        graphics = bs.getDrawGraphics();
        
        Graphics2D g2d = (Graphics2D) graphics; //creamos g2d para metodo translate (para manejar el enfoque de la camara)
        
        //--------------------------------EMPIEZA A DIBUJAR AQUI----
        
        graphics.setColor(Color.black);
        graphics.fillRect(0, 0, getWidth(), getHeight()); //Pantalla negra
        
        if(state == STATE.MENU) {
            menu.type = 0;
            menu.draw(graphics);
        }
        
        if(state == STATE.LOADING) {
            menu.type = 1;
            menu.draw(graphics);
        }
        
        if(state == STATE.PLAYER) {
            menu.type = 2;
            menu.draw(graphics);
        }
        
        if(state == STATE.GAMEOVER) {
            menu.type = 3;
            menu.draw(graphics);
        }
        
        if(state == STATE.FINISH) {
            menu.type = 4;
            menu.draw(graphics);
        }
        
        if(state == STATE.CREDITS) {
            menu.type = 5;
            menu.draw(graphics);
        }
        
        if(state == STATE.HELP) {
            menu.type = 6;
            menu.draw(graphics);
        }
        
        if(state == STATE.GAME) {
        
            //BACKGROUND

            graphics.drawImage(imgLoader.background, 0, 0, 1940, 1080, this);

            g2d.translate(camera.getX(), camera.getY()); //Inicio del enfoque

            world.draw(graphics);

            g2d.translate(-camera.getX(), -camera.getY()); //Fin del enfoque

        }
        //------------------------------------- Termina aqui--------
        
        graphics.dispose();
        bs.show();
    }
    
    public static void sleepGameThread() {
        try {
            gameThread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static int getGameWidth() {
        return width;
    }
    
    public static int getGameHeight() {
        return height;
    }
    
    public static ImageLoader getImgLoader() {
        return imgLoader;
    }
    
    public static Graphics getGameGraphics() {
        return graphics;
    }
    
    public static void main(String args[]) {
        new Screen(1650, 1080, "Sweet Nightmare", new Game());
    }
}
