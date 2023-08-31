package main;

import object.GameObject;
import object.Heart;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class UserInterface {
    GamePanel gamePanel;
    Graphics2D graphics2D;
    Font maruMonica; // шрифт
    BufferedImage heart_full, heart_half, heart_blank;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;
    public String currentDialogue = "";
    public int commandNumber = 0;

    public UserInterface(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        try {
            InputStream inputStream = getClass().getResourceAsStream("/font/MaruMonica.ttf");
            assert inputStream != null;
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, inputStream);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        // CREATE HUD OBJECT
        GameObject heart = new Heart(gamePanel);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;
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
            drawPlayerLife();
        }
        // PAUSE STATE
        else if (gamePanel.gameState == gamePanel.pauseState)
            drawPauseScreen();
        // DIALOGUE STATE
        else if (gamePanel.gameState == gamePanel.dialogueState)
            drawDialogueScreen();
    }

    // здоровье игрока
    public void drawPlayerLife() {
        int x = gamePanel.tileSize / 2;
        int y = gamePanel.tileSize / 2;
        int i = 0;

        // DRAW MAX LIFE
        while (i < gamePanel.player.maxLife / 2) {
            graphics2D.drawImage(heart_blank, x, y, null);
            i++;
            x += gamePanel.tileSize;
        }

        // RESET
        x = gamePanel.tileSize / 2;
        i = 0;

        // DRAW CURRENT LIFE
        while (i < gamePanel.player.currentLife) {
            graphics2D.drawImage(heart_half, x, y, null);
            i++;

            if (i < gamePanel.player.currentLife)
                graphics2D.drawImage(heart_full, x, y, null);

            i++;
            x += gamePanel.tileSize;
        }
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

        if (commandNumber == 0)
            graphics2D.drawString("<>", x - gamePanel.tileSize, y);

        text = "LOAD GAME";
        x = getXForCenteredText(text);
        y += gamePanel.tileSize;
        graphics2D.drawString(text, x, y);

        if (commandNumber == 1)
            graphics2D.drawString("<>", x - gamePanel.tileSize, y);

        text = "QUIT";
        x = getXForCenteredText(text);
        y += gamePanel.tileSize;
        graphics2D.drawString(text, x, y);

        if (commandNumber == 2)
            graphics2D.drawString("<>", x - gamePanel.tileSize, y);
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

        int count = countLines(currentDialogue);
        y = (height + 11 * count) / 2;

        // текст
        for (String line: currentDialogue.split("\n")) {
            graphics2D.drawString(line, getXForCenteredText(line), y);
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

    public int countLines(String text) {
        int count = 1;

        for (char element : text.toCharArray())
            if (element == '\n')
                count++;

        return count;
    }

    // выравнивание по x
    public int getXForCenteredText(String text) {
        int length = (int) graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();

        return (gamePanel.screenWidth - length) / 2;
    }
}
