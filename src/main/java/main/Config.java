package main;

import java.io.*;
import java.nio.file.Path;

public class Config {
    GamePanel gamePanel;

    public Config(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    // сохранение основных настроек игры
    public void saveConfig() {
        Path saveData = Path.of("gameConfig.txt"); // сохранение в выбранный файл

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(saveData.toFile())); // запись в файл

            // FULL SCREEN
            if (gamePanel.fullScreenOn)
                writer.write("Full_screen_on");
            else
                writer.write("Full_screen_off");

            writer.newLine();

            // MUSIC VOLUME
            writer.write(String.valueOf(gamePanel.music.volumeScale));
            writer.newLine();

            // SOUND EFFECTS VOLUME
            writer.write(String.valueOf(gamePanel.soundEffect.volumeScale));
            writer.newLine();

            writer.close(); // прекращение записи в файл
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // чтение файла
    public void loadConfig() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("gameConfig.txt"));

            String line = reader.readLine(); // чтение фацла построчно

            // FULL SCREEN
            gamePanel.fullScreenOn = line.equals("Full_screen_on");

            // MUSIC VOLUME
            line = reader.readLine();
            gamePanel.music.volumeScale = Integer.parseInt(line);

            // SOUND EFFECTS VOLUME
            line = reader.readLine();
            gamePanel.soundEffect.volumeScale = Integer.parseInt(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
