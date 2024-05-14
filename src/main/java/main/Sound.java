package main;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class Sound {
    Clip clip;
    URL[] soundURL = new URL[30]; // массив звуков

    public Sound() {
        soundURL[0] = getClass().getResource("/sounds/Game.wav");
        soundURL[1] = getClass().getResource("/sounds/coin.wav");
        soundURL[2] = getClass().getResource("/sounds/powerup.wav");
        soundURL[3] = getClass().getResource("/sounds/unlock.wav");
        soundURL[4] = getClass().getResource("/sounds/fanfare.wav");
        soundURL[5] = getClass().getResource("/sounds/hitmonster.wav");
        soundURL[6] = getClass().getResource("/sounds/receivedamage.wav");
        soundURL[7] = getClass().getResource("/sounds/swingweapon.wav");
        soundURL[8] = getClass().getResource("/sounds/levelup.wav");
        soundURL[9] = getClass().getResource("/sounds/cursor.wav");
        soundURL[10] = getClass().getResource("/sounds/burning.wav");
    }

    // извлечение звука
    public void setFile(int index) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundURL[index]);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // запуск звука
    public void play() {
        clip.start();
    }

    // зацикливание звука
    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    // остановка звука
    public void stop() {
        clip.stop();
    }
}
