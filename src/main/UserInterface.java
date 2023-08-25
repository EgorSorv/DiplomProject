package main;

import object.Key;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UserInterface {
    GamePanel gamePanel;
    Font arial_40; // шрифт
    BufferedImage keyImage;

    public UserInterface(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        arial_40 = new Font("Arial", Font.PLAIN, 40);

        Key key = new Key();
        keyImage = key.image;
    }

    // отображение интерфейса
    public void draw(Graphics2D graphics2D) {
        graphics2D.setFont(arial_40);
        graphics2D.setColor(Color.white);
        graphics2D.drawImage(keyImage, gamePanel.tileSize / 2, gamePanel.tileSize / 2,
                gamePanel.tileSize, gamePanel.tileSize, null);
        graphics2D.drawString("x " + gamePanel.player.hasKey, 74, 50); // текст
    }
}
