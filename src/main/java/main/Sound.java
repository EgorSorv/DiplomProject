package main;

import javax.sound.sampled.*;
import java.net.URL;

public class Sound {
    Clip clip;
    URL[] soundURL = new URL[30]; // массив звуков
    FloatControl floatControl; // тип получаемых значений для изменения громкости (-80f до 6f)
    int volumeScale = 3;
    float volume;

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
        soundURL[11] = getClass().getResource("/sounds/cuttree.wav");
    }

    // извлечение звука
    public void setFile(int index) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundURL[index]);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);

            // прием значений для изменения громкости
            floatControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

            checkVolume();
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

    // изменение громкости
    public void checkVolume() {
        switch (volumeScale) {
            case 0 -> volume = -80f;
            case 1 -> volume = -20f;
            case 2 -> volume = -12f;
            case 3 -> volume = -5f;
            case 4 -> volume = 1f;
            case 5 -> volume = 6f;
        }

        floatControl.setValue(volume);
    }
}
