package main;

import object.Key;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UserInterface {
    GamePanel gamePanel;
    Font arial_40; // шрифт
    BufferedImage keyImage;
    public boolean messageOn = false;
    public String message = "";

    public UserInterface(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        arial_40 = new Font("Arial", Font.PLAIN, 40);

        Key key = new Key();
        keyImage = key.image;
    }

    // отображение сообщений
    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    // отображение интерфейса
    public void draw(Graphics2D graphics2D) {
        graphics2D.setFont(arial_40);
        graphics2D.setColor(Color.white);
        graphics2D.drawImage(keyImage, gamePanel.tileSize / 2, gamePanel.tileSize / 2,
                gamePanel.tileSize, gamePanel.tileSize, null);
        graphics2D.drawString("x " + gamePanel.player.hasKey, 74, 65); // текст

        // MESSAGE
        if (messageOn) {
            graphics2D.setFont(graphics2D.getFont().deriveFont(30F)); // изменение размера текста
            graphics2D.drawString(message, gamePanel.tileSize / 2,  gamePanel.tileSize * 5);
        }
    }
}
