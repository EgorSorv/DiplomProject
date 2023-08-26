package main;

import object.Key;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class UserInterface {
    GamePanel gamePanel;
    Font arial_40, arial_80B; // шрифт
    BufferedImage keyImage;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;

    // время в игре
    double playTime;
    DecimalFormat decimalFormat = new DecimalFormat("#0.00"); // округление до сотых

    public UserInterface(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B  = new Font("Arial", Font.BOLD, 80);

        Key key = new Key(gamePanel);
        keyImage = key.image;
    }

    // отображение сообщений
    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    // отображение интерфейса
    public void draw(Graphics2D graphics2D) {
        if (gameFinished) {
//            String text;
//            int textLength;
//            int x;
//            int y;
//
//            graphics2D.setFont(arial_40);
//            graphics2D.setColor(Color.white);
//
//            text = "You found the treasure!";
//
//            // длина строки
//            textLength = (int)graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();
//            // отображение текста по центру
//            x = gamePanel.screenWidth / 2 - textLength / 2;
//            y = gamePanel.screenHeight / 2 - gamePanel.tileSize * 3;
//
//            graphics2D.drawString(text, x, y);
//
//            text = "Your time is " + decimalFormat.format(playTime) + "!";
//
//            textLength = (int)graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();
//            x = gamePanel.screenWidth / 2 - textLength / 2;
//            y = gamePanel.screenHeight / 2 + gamePanel.tileSize * 4;
//
//            graphics2D.drawString(text, x, y);
//
//            graphics2D.setFont(arial_80B);
//            graphics2D.setColor(Color.yellow);
//
//            text = "Congratulations!";
//
//            textLength = (int)graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();
//            x = gamePanel.screenWidth / 2 - textLength / 2;
//            y = gamePanel.screenHeight / 2 + gamePanel.tileSize * 2;
//
//            graphics2D.drawString(text, x, y);

            gamePanel.gameThread = null; // выключение игры
        } else {
            graphics2D.setFont(arial_40);
            graphics2D.setColor(Color.white);
            graphics2D.drawImage(keyImage, gamePanel.tileSize / 2, gamePanel.tileSize / 2,
                    gamePanel.tileSize, gamePanel.tileSize, null);
            graphics2D.drawString("x " + gamePanel.player.hasKey, 74, 65); // текст

            // TIME
//            playTime += (double) 1 / 60;
//            graphics2D.drawString("Time:" + decimalFormat.format(playTime),
//                    gamePanel.tileSize * 11, 65);

            // MESSAGE
            if (messageOn) {
                graphics2D.setFont(graphics2D.getFont().deriveFont(30F)); // изменение размера текста
                graphics2D.drawString(message, gamePanel.tileSize / 2,  gamePanel.tileSize * 5);

                messageCounter++;

                // убрать сообщение через определенное время
                if (messageCounter > 120) {
                    messageCounter = 0;
                    messageOn = false;
                }
            }
        }
    }
}
