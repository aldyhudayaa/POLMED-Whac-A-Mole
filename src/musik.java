import javax.sound.sampled.*;
import java.net.URL;

public class musik {
    Clip[] clip = new Clip[5]; 
    URL musikURL[] = new URL[5];

    public musik(){
        musikURL[0] = getClass().getResource("musik/tampilanmenu.wav");
        musikURL[1] = getClass().getResource("musik/closing.wav");
        musikURL[2] = getClass().getResource("musik/pilihmenu.wav");
        musikURL[3] = getClass().getResource("musik/gameplay.wav");
        musikURL[4] = getClass().getResource("musik/menyedihkan.wav");
    }

    public void setFile(int i){
        try{
            if(clip[i] != null && clip[i].isRunning()) {
                clip[i].stop();
                clip[i].close();
            }
            AudioInputStream input = AudioSystem.getAudioInputStream(musikURL[i]);
            clip[i] = AudioSystem.getClip();
            clip[i].open(input);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void play(int i){
        if(clip[i] != null) {
            clip[i].setFramePosition(0);
            clip[i].start();
        }
    }

    public void stop(int i){
        if(clip[i] != null && clip[i].isRunning()) {
            clip[i].stop();
        }
    }

    public void loop(int i){
        if(clip[i] != null) {
            clip[i].loop(Clip.LOOP_CONTINUOUSLY);
        }
    }
    
    public void stopAll(){
        for(Clip clip : clip) {
            if(clip != null && clip.isRunning()) {
                clip.stop();
            }
        }
    }
}

