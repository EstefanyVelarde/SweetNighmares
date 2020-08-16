
package Program;

import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundEffect {
    //https://gist.github.com/figengungor/5673813/revisions
    private Clip clip;

    // Constructor to construct each element of the enum with its own sound file.
    public SoundEffect(String soundFileName) throws IOException {
       try {
          // Use URL (instead of File) to read from disk and JAR.
          URL url = this.getClass().getResource(soundFileName);
          // Set up an audio input stream piped from the sound file.
          AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
          // Get a clip resource.
          clip = AudioSystem.getClip();
          // Open audio clip and load samples from the audio input stream.
          clip.open(audioInputStream);
       } catch (UnsupportedAudioFileException e) {
          e.printStackTrace();
       } catch (IOException e) {
          e.printStackTrace();
       } catch (LineUnavailableException e) {
          e.printStackTrace();
       }
    }

    // Play or Re-play the sound effect from the beginning, by rewinding.
    public void play(Boolean loop) {
          if (clip.isRunning())
             clip.stop();   // Stop the player if it is still running
          
          clip.setFramePosition(0); // rewind to the beginning
          clip.start();     // Start playing
          
          if(loop)//Loop if loop parameter is true
                   clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    
    public boolean isRunning() {
        if (clip.isRunning())
             return true;
        else
            return false;
    }

    public void stop() //stop playing and rewind to be played again from the beginning
    {
        clip.stop();
        clip.setFramePosition(0);
    }
    
}