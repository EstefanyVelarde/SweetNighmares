
package Program;

import java.awt.Dimension;
import javax.swing.JFrame;

public class Screen {
    
    public Screen(int width, int height, String title, Game game) {
        
        JFrame frame = new JFrame(title);
        frame.add(game);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        frame.setVisible(true);
        
        game.start();
    }
    
}
