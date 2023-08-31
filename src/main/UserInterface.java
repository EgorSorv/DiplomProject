package main;

import entity.Entity;
import object.BlueHeart;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.concurrent.BlockingDeque;

public class UserInterface {
    GamePanel gamePanel;
    Graphics2D graphics2D;
    Font maruMonica; // шрифт
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;
    public String currentDialogue = "";

    public UserInterface(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        try {
            InputStream inputStream = getClass().getResourceAsStream("/font/MaruMonica.ttf");
            assert inputStream != null;
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, inputStream);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }

    // отображение сообщений
    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    // отображение интерфейса
    public void draw(Graphics2D graphics2D) {
        this.graphics2D = graphics2D;

        graphics2D.setFont(maruMonica);
        graphics2D.setColor(Color.white);

        // TITLE STATE
        if (gamePanel.gameState == gamePanel.titleState)
            drawTitleScreen();
        // PLAY STATE
        if (gamePanel.gameState == gamePanel.playState) {

        }
        // PAUSE STATE
        else if (gamePanel.gameState == gamePanel.pauseState)
            drawPauseScreen();
        // DIALOGUE STATE
        else if (gamePanel.gameState == gamePanel.dialogueState)
            drawDialogueScreen();
    }

    // главное меню
    public void drawTitleScreen() {
        // BACKGROUND
        graphics2D.setColor(new Color(0, 0,0));
        graphics2D.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

        // TITLE
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 96F));
        String text = "Game";

        int x = getXForCenteredText(text);
        int y = gamePanel.tileSize * 3;

        graphics2D.setColor(Color.darkGray);
        graphics2D.drawString(text, x + 4, y + 4); // тень

        graphics2D.setColor(Color.white);
        graphics2D.drawString(text, x, y);

        // IMAGE
        x = gamePanel.screenWidth / 2 - gamePanel.tileSize;
        y += gamePanel.tileSize * 2;
        graphics2D.drawImage(gamePanel.obj[0].image, x, y,
                gamePanel.tileSize * 2, gamePanel.tileSize * 2, null);

        // MENU
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 48F));

        text = "NEW GAME";
        x = getXForCenteredText(text);
        y += gamePanel.tileSize * 3.5;
        graphics2D.drawString(text, x, y);

        text = "LOAD GAME";
        x = getXForCenteredText(text);
        y += gamePanel.tileSize;
        graphics2D.drawString(text, x, y);

        text = "QUIT";
        x = getXForCenteredText(text);
        y += gamePanel.tileSize;
        graphics2D.drawString(text, x, y);
    }

    // надпись во время паузы
    public void drawPauseScreen() {
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 80F));
        String text = "PAUSED";
        int y = gamePanel.screenHeight / 2;

        graphics2D.drawString(text, getXForCenteredText(text), y);
    }

    // диалоговое окно
    public void drawDialogueScreen() {
        // WINDOW
        int x = gamePanel.tileSize * 2;
        int y = gamePanel.tileSize / 2;
        int width = gamePanel.screenWidth - (gamePanel.tileSize * 4);
        int height = gamePanel.tileSize * 4;

        drawSubWindow(x, y, width, height);

        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 32F));

        x += gamePanel.tileSize;
        y += gamePanel.tileSize;

        for (String line: currentDialogue.split("\n")) {
            graphics2D.drawString(line, x, y);
            y += 40;
        }
    }

    // фон окна
    public void drawSubWindow(int x, int y, int width, int height) {
        Color color = new Color(0, 0, 0, 210);
        graphics2D.setColor(color);

        graphics2D.fillRoundRect(x, y, width, height, 35, 35);

        color = new Color(255, 255, 255);
        graphics2D.setColor(color);

        graphics2D.setStroke(new BasicStroke(5)); // рамка
        graphics2D.drawRoundRect(x + 5, y + 5, width - 10, height - 10,
                25, 25);
    }

    public int getXForCenteredText(String text) {
        int length = (int) graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();

        return gamePanel.screenWidth / 2 - length / 2;
    }
}
