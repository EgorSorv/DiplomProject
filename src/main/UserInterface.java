package main;

import entity.Entity;
import object.Heart;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class UserInterface {
    GamePanel gamePanel;
    Graphics2D graphics2D;
    Font maruMonica; // шрифт
    BufferedImage heart_full, heart_half, heart_blank;
    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();
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
        Entity heart = new Heart(gamePanel);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;
    }

    // обновление сообщений
    public void addMessage(String text) {
        message.add(text);
        messageCounter.add(0);
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
            drawMessage();
        }

        // PAUSE STATE
        if (gamePanel.gameState == gamePanel.pauseState) {
            drawPlayerLife();
            drawPauseScreen();
        }

        // DIALOGUE STATE
        if (gamePanel.gameState == gamePanel.dialogueState)
            drawDialogueScreen();

        // CHARACTER STATE
        else if (gamePanel.gameState == gamePanel.characterState)
            drawCharacterScreen();
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

    // отображение сообщений
    public void drawMessage() {
        int messageX = gamePanel.tileSize;
        int messageY = gamePanel.tileSize * 4;

        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 32F));

        for (int i = 0; i < message.size(); i++) {
            if (message.get(i) != null) {
                graphics2D.setColor(Color.black);
                graphics2D.drawString(message.get(i), messageX + 2, messageY + 2);
                graphics2D.setColor(Color.white);
                graphics2D.drawString(message.get(i), messageX, messageY);

                int counter = messageCounter.get(i) + 1;
                messageCounter.set(i, counter);
                messageY += 50;

                if (messageCounter.get(i) > 180) {
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
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
        graphics2D.drawImage(gamePanel.obj[0].downIdle, x, y,
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

    // инфо по игроку
    public void drawCharacterScreen() {
        final int frameX = gamePanel.tileSize;
        final int frameY = gamePanel.tileSize;
        final int frameWidth = gamePanel.tileSize * 6;
        final int frameHeight = gamePanel.tileSize * 10;


        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        graphics2D.setColor(Color.white);
        graphics2D.setFont(graphics2D.getFont().deriveFont(32F));

        int textX = frameX + 20;
        int textY = frameY + gamePanel.tileSize;
        final int lineHeight = 35;

        graphics2D.drawString("Level", textX, textY);
        textY += lineHeight;

        graphics2D.drawString("EXP", textX, textY);
        textY += lineHeight;

        graphics2D.drawString("EXP for next level", textX, textY);
        textY += lineHeight;

        graphics2D.drawString("HP", textX, textY);
        textY += lineHeight;

        graphics2D.drawString("Strength", textX, textY);
        textY += lineHeight;

        graphics2D.drawString("Dexterity", textX, textY);
        textY += lineHeight;

        graphics2D.drawString("Attack", textX, textY);
        textY += lineHeight;

        graphics2D.drawString("Defense", textX, textY);
        textY += lineHeight;

        graphics2D.drawString("Coins", textX, textY);
        textY += lineHeight + 20;

        graphics2D.drawString("Weapon", textX, textY);
        textY += lineHeight + 15;

        graphics2D.drawString("Shield", textX, textY);

        int tailX = (frameX + frameWidth) - 30;
        textY = frameY + gamePanel.tileSize;
        String value;

        value = String.valueOf(gamePanel.player.level);
        textX = getXForAlignToRightText(value, tailX);
        graphics2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.player.exp);
        textX = getXForAlignToRightText(value, tailX);
        graphics2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.player.nextLevelExp);
        textX = getXForAlignToRightText(value, tailX);
        graphics2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = gamePanel.player.currentLife + "/" + gamePanel.player.maxLife;
        textX = getXForAlignToRightText(value, tailX);
        graphics2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.player.strength);
        textX = getXForAlignToRightText(value, tailX);
        graphics2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.player.dexterity);
        textX = getXForAlignToRightText(value, tailX);
        graphics2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.player.attack);
        textX = getXForAlignToRightText(value, tailX);
        graphics2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.player.defense);
        textX = getXForAlignToRightText(value, tailX);
        graphics2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.player.coins);
        textX = getXForAlignToRightText(value, tailX);
        graphics2D.drawString(value, textX, textY);
        textY += lineHeight;

        graphics2D.drawImage(gamePanel.player.currentWeapon.downIdle,
                tailX - gamePanel.tileSize, textY - 15, null);

        textY += gamePanel.tileSize;

        graphics2D.drawImage(gamePanel.player.currentShield.downIdle,
                tailX - gamePanel.tileSize, textY - 15, null);
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

    public int getXForAlignToRightText(String text, int tailX) {
        int length = (int) graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();

        return tailX - length;
    }
}
