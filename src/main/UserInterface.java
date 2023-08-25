package main;

import java.awt.*;

public class UserInterface {
    GamePanel gamePanel;
    Font arial_40; // шрифт

    public UserInterface(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        arial_40 = new Font("Arial", Font.PLAIN, 40);
    }

    // отображение интерфейса
    public void draw(Graphics2D graphics2D) {
        graphics2D.setFont(arial_40);
        graphics2D.setColor(Color.white);
        graphics2D.drawString("Key = " + gamePanel.player.hasKey, 50, 50); // текст
    }
}
