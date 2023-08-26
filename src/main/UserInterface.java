package main;

import java.awt.*;
import java.text.DecimalFormat;

public class UserInterface {
    GamePanel gamePanel;
    Graphics2D graphics2D;
    Font arial_40, arial_80B; // шрифт
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
    }

    // отображение сообщений
    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    // отображение интерфейса
    public void draw(Graphics2D graphics2D) {
        this.graphics2D = graphics2D;

        graphics2D.setFont(arial_40);
        graphics2D.setColor(Color.white);

        if (gamePanel.gameState == gamePanel.playState) {

        } else if (gamePanel.gameState == gamePanel.pauseState)
            drawPauseScreen();
    }

    // надрись во время паузы
    public void drawPauseScreen() {
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 80F));
        String text = "PAUSED";
        int y = gamePanel.screenHeight / 2;

        graphics2D.drawString(text, getXForCenteredText(text), y);
    }

    public int getXForCenteredText(String text) {
        int length = (int) graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();

        return gamePanel.screenWidth / 2 - length / 2;
    }
}
