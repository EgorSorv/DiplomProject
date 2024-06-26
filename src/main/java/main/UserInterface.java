package main;

import entity.Entity;
import object.Coin;
import object.Heart;
import object.ManaCrystal;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class UserInterface {
    GamePanel gamePanel;
    Graphics2D graphics2D;
    Font maruMonica; // шрифт
    BufferedImage heart_full, heart_half, heart_blank, crystal_full, crystal_blank, gold_coin;
    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();
    public String currentDialogue = "";
    public int commandNumber = 0;
    public int playerSlotCol = 0;
    public int playerSlotRow = 0;
    public int npcSlotCol = 0;
    public int npcSlotRow = 0;
    int subState = 0;
    int counter = 0;
    public Entity npc;

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

        Entity crystal = new ManaCrystal(gamePanel);
        crystal_full = crystal.image;
        crystal_blank = crystal.image2;

        Entity coin = new Coin(gamePanel);
        gold_coin = coin.downIdle;
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
        if (gamePanel.gameState == gamePanel.characterState) {
            drawCharacterScreen();
            drawInventory(gamePanel.player, true);
        }

        // SETTINGS STATE
        if (gamePanel.gameState == gamePanel.settingsState) {
            drawSettingsScreen();
        }

        // GAME OVER STATE
        if (gamePanel.gameState == gamePanel.gameOverState) {
            drawGameOverScreen();
        }

        // TRANSITION STATE
        if (gamePanel.gameState == gamePanel.transitionState)
            drawTransition();

        // TRADE STATE
        if (gamePanel.gameState == gamePanel.tradeState)
            drawTradeScreen();
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

        // DRAW MAX MANA
        x = gamePanel.tileSize / 2 - 5;
        y = (int) (gamePanel.tileSize * 1.5);
        i = 0;

        while (i < gamePanel.player.maxMana) {
            graphics2D.drawImage(crystal_blank, x, y, null);
            i++;
            x += 35;
        }

        // DRAW CURRENT MANA
        x = gamePanel.tileSize / 2 - 5;
        y = (int) (gamePanel.tileSize * 1.5);
        i = 0;

        while (i < gamePanel.player.currentMana) {
            graphics2D.drawImage(crystal_full, x, y, null);
            i++;
            x += 35;
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
        graphics2D.drawImage(gamePanel.obj[0][0].image, x, y,
                gamePanel.tileSize * 2, gamePanel.tileSize * 2, null);

        // MENU
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 48F));

        text = "NEW GAME";
        x = getXForCenteredText(text);
        y += (int) (gamePanel.tileSize * 3.5);
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
        int x = gamePanel.tileSize * 3;
        int y = gamePanel.tileSize / 2;
        int width = gamePanel.screenWidth - (gamePanel.tileSize * 6);
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
        final int frameX = gamePanel.tileSize * 2;
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

        graphics2D.drawString("Mana", textX, textY);
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
        textY += lineHeight + 10;

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

        value = gamePanel.player.currentMana + "/" + gamePanel.player.maxMana;
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
                tailX - gamePanel.tileSize, textY - 25, null);

        textY += gamePanel.tileSize;

        graphics2D.drawImage(gamePanel.player.currentShield.downIdle,
                tailX - gamePanel.tileSize, textY - 25, null);
    }

    // инвентарь игрока
    public void drawInventory(Entity entity, boolean displayCursor) {
        // FRAME
        int frameX;
        int frameY = gamePanel.tileSize;
        int frameWidth = gamePanel.tileSize * 6;
        int frameHeight = gamePanel.tileSize * 5;
        int slotCol;
        int slotRow;

        if (entity == gamePanel.player) {
            frameX = gamePanel.tileSize * 12;
            slotCol = playerSlotCol;
            slotRow = playerSlotRow;
        } else {
            frameX = gamePanel.tileSize * 2;
            slotCol = npcSlotCol;
            slotRow = npcSlotRow;
        }

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // SLOT
        final int slotXstart = frameX + 20;
        final int slotYstart = frameY + 20;
        int slotX = slotXstart;
        int slotY = slotYstart;
        int slotSize = gamePanel.tileSize + 3;

        // DRAW PLAYER'S ITEMS
        for (int i = 0; i < entity.inventory.size(); i++) {
            // EQUIP CURSOR
            if (entity.inventory.get(i) == entity.currentWeapon ||
            entity.inventory.get(i) == entity.currentShield) {
                   graphics2D.setColor(new Color(240, 190, 90));
                   graphics2D.fillRoundRect(slotX, slotY, gamePanel.tileSize,
                           gamePanel.tileSize, 10, 10);
            }

            graphics2D.drawImage(entity.inventory.get(i).downIdle, slotX, slotY, null);

            slotX += slotSize;

            if (i == 4 || i == 9 || i == 14) {
                slotX = slotXstart;
                slotY += slotSize;
            }
        }

        if (displayCursor) {
            // CURSOR
            int cursorX = slotXstart + slotSize * slotCol;
            int cursorY = slotYstart + slotSize * slotRow;
            int cursorWidth = gamePanel.tileSize;
            int cursorHeight = gamePanel.tileSize;

            // DRAW CURSOR
            graphics2D.setColor(Color.white);
            graphics2D.setStroke(new BasicStroke(3));
            graphics2D.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

            // DESCRIPTION FRAME
            int dFrameX = frameX;
            int dFrameY = frameY + frameHeight;
            int dFrameWidth = frameWidth;
            int dFrameHeight = gamePanel.tileSize * 3;

            // DRAW DESCRIPTION TEXT
            int textX = dFrameX + 20;
            int textY = dFrameY + gamePanel.tileSize;
            graphics2D.setFont(graphics2D.getFont().deriveFont(28F));

            int itemIndex = getItemIndexOnSlot(slotCol, slotRow);

            // разделение и печать строки
            if (itemIndex < entity.inventory.size()) {

                drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);


                for (String line : entity.inventory.get(itemIndex).description.split("\n")) {
                    graphics2D.drawString(line, textX, textY);
                    textY += 32;
                }
            }
        }
    }

    // окно с настройками
    public void drawSettingsScreen() {
        graphics2D.setColor(Color.white);
        graphics2D.setFont(graphics2D.getFont().deriveFont(32F));

        // SUB WINDOW
        int frameX = gamePanel.tileSize * 6;
        int frameY = gamePanel.tileSize;
        int frameWidth = gamePanel.tileSize * 8;
        int frameHeight = gamePanel.tileSize * 10;

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        switch (subState) {
            case 0 -> mainSettings(frameX, frameY);
            case 1 -> controlSettings(frameX, frameY);
            case 2 -> returnToTitleSettings(frameY);
            case 3 -> quitSettings(frameY);
        }

        gamePanel.keyHandler.interactPressed = false;
    }

    // конец игры
    public void drawGameOverScreen() {
        int x;
        int y;
        String text;

        // затемнение экрана
        graphics2D.setColor(new Color(0, 0, 0, 150));
        graphics2D.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

        // шрифт
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 110F));
        text = "Game Over";

        // тень надписи
        graphics2D.setColor(Color.black);
        x = getXForCenteredText(text);
        y = gamePanel.tileSize * 4;
        graphics2D.drawString(text, x, y);

        // надпись
        graphics2D.setColor(Color.white);
        graphics2D.drawString(text, x - 4, y - 4);

        // перезапуск
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 50F));
        text = "Retry";
        x = getXForCenteredText(text);
        y += gamePanel.tileSize * 4;
        graphics2D.drawString(text, x, y);

        if (commandNumber == 0) {
            graphics2D.drawString("<>", x - 41, y);
        }

        // возврат на главный экран
        text = "Quit";
        x = getXForCenteredText(text);
        y += 55;
        graphics2D.drawString(text, x, y);

        if (commandNumber == 1) {
            graphics2D.drawString("<>", x - 41, y);
        }
    }

    // переход
    public void drawTransition() {
       counter++;

       graphics2D.setColor(new Color(0, 0, 0, counter * 5));
       graphics2D.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

       if (counter == 50) {
           counter = 0;
           gamePanel.gameState = gamePanel.playState;

           gamePanel.currentMap = gamePanel.eventHandler.tempMap;
           gamePanel.player.worldX = gamePanel.tileSize * gamePanel.eventHandler.tempCol;
           gamePanel.player.worldY = gamePanel.tileSize * gamePanel.eventHandler.tempRow;

           gamePanel.eventHandler.previousEventX = gamePanel.player.worldX;
           gamePanel.eventHandler.previousEventY = gamePanel.player.worldY;
       }
    }

    // окно торговли
    public void drawTradeScreen() {
        switch (subState) {
            case 0 -> tradeSelectItem();
            case 1 -> tradeBuyItem();
            case 2 -> tradeSellItem();
        }

        gamePanel.keyHandler.interactPressed = false;
    }

    // основное окно настроек
    public void mainSettings(int frameX, int frameY) {
        int textX;
        int textY;

        // TITLE
        String text = "Settings";
        textX = getXForCenteredText(text);
        textY = frameY + gamePanel.tileSize;
        graphics2D.drawString(text, textX, textY);

        // CONTINUE
        textX = frameX + gamePanel.tileSize;
        textY += gamePanel.tileSize;
        graphics2D.drawString("Continue", textX, textY);

        if (commandNumber == 0) {
            graphics2D.drawString("<>", textX - 26, textY);

            if (gamePanel.keyHandler.interactPressed)
                gamePanel.gameState = gamePanel.playState;
        }

        // CHARACTER ATTRIBUTES AND STATUS
        textY += gamePanel.tileSize;
        graphics2D.drawString("Character", textX, textY);

        if (commandNumber == 1) {
            graphics2D.drawString("<>", textX - 26, textY);

            if (gamePanel.keyHandler.interactPressed)
                gamePanel.gameState = gamePanel.characterState;
        }
        // FULL SCREEN ON/OFF
        textY += gamePanel.tileSize;
        graphics2D.drawString("Full Screen", textX, textY);

        if (commandNumber == 2) {
            graphics2D.drawString("<>", textX - 26, textY);

            if (gamePanel.keyHandler.interactPressed) {
                gamePanel.fullScreenOn = !gamePanel.fullScreenOn;
                gamePanel.switchScreenSize();
            }
        }

        // MUSIC
        textY += gamePanel.tileSize;
        graphics2D.drawString("Music", textX, textY);

        if (commandNumber == 3)
            graphics2D.drawString("<>", textX - 26, textY);

        // SOUND EFFECTS
        textY += gamePanel.tileSize;
        graphics2D.drawString("Sound effects", textX, textY);

        if (commandNumber == 4)
            graphics2D.drawString("<>", textX - 26, textY);

        // CONTROL
        textY += gamePanel.tileSize;
        graphics2D.drawString("Control", textX, textY);

        if (commandNumber == 5) {
            graphics2D.drawString("<>", textX - 26, textY);
            if (gamePanel.keyHandler.interactPressed) {
                subState = 1;
                commandNumber = 0;
            }
        }

        // RETURN TO TITLE
        textY += gamePanel.tileSize;
        graphics2D.drawString("Return to Title", textX, textY);

        if (commandNumber == 6) {
            graphics2D.drawString("<>", textX - 26, textY);

            if (gamePanel.keyHandler.interactPressed) {
                subState = 2;
                commandNumber = 0;
            }
        }

        // QUIT
        textY += gamePanel.tileSize;
        graphics2D.drawString("Quit", textX, textY);

        if (commandNumber == 7) {
            graphics2D.drawString("<>", textX - 26, textY);

            if (gamePanel.keyHandler.interactPressed) {
                subState = 3;
                commandNumber = 0;
            }
        }
        // FULL SCREEN CHECK BOX
        textX = frameX + (int) (gamePanel.tileSize * 4.5);
        textY = frameY + gamePanel.tileSize * 3 + 24;
        graphics2D.setStroke(new BasicStroke(3));
        graphics2D.drawRect(textX, textY, 24, 24);

        if (gamePanel.fullScreenOn)
            graphics2D.fillRect(textX, textY, 24, 24);

        // MUSIC VOLUME
        textY += gamePanel.tileSize;
        graphics2D.drawRect(textX, textY, 120, 24);

        int volumeWidth = 24 * gamePanel.music.volumeScale;
        graphics2D.fillRect(textX, textY, volumeWidth, 24);

        // SOUND EFFECTS VOLUME
        textY += gamePanel.tileSize;
        graphics2D.drawRect(textX, textY, 120, 24);

        volumeWidth = 24 * gamePanel.soundEffect.volumeScale;
        graphics2D.fillRect(textX, textY, volumeWidth, 24);

        gamePanel.config.saveConfig(); // сохранение настроек
    }

    // окно управления
    public void controlSettings(int frameX, int frameY) {
        int textX;
        int textY;

        String text = "Control";
        textX = getXForCenteredText(text);
        textY = frameY + gamePanel.tileSize;
        graphics2D.drawString(text, textX, textY);

        textX = frameX + gamePanel.tileSize;
        textY += gamePanel.tileSize;

        // MOVEMENT
        graphics2D.drawString("Move", textX, textY);
        textY += gamePanel.tileSize;

        // INTERACT
        graphics2D.drawString("Interact/Attack", textX, textY);
        textY += gamePanel.tileSize;

        // CAST
        graphics2D.drawString("Cast Magic/Throw", textX, textY);
        textY += gamePanel.tileSize;

        // INVENTORY
        graphics2D.drawString("Inventory", textX, textY);
        textY += gamePanel.tileSize;

        // PAUSE
        graphics2D.drawString("Pause", textX, textY);
        textY += gamePanel.tileSize;

        // SETTINGS
        graphics2D.drawString("Settings", textX, textY);

        textX = frameX + gamePanel.tileSize * 6;
        textY = frameY + gamePanel.tileSize * 2;

        // MOVEMENT KEYS
        graphics2D.drawString("WASD", textX, textY);
        textY += gamePanel.tileSize;

        // INTERACT KEY
        graphics2D.drawString("E", textX, textY);
        textY += gamePanel.tileSize;

        // CAST KEY
        graphics2D.drawString("F", textX, textY);
        textY += gamePanel.tileSize;

        // INVENTORY KEY
        graphics2D.drawString("I", textX, textY);
        textY += gamePanel.tileSize;

        // PAUSE KEY
        graphics2D.drawString("P", textX, textY);
        textY += gamePanel.tileSize;

        // SETTINGS KEY
        graphics2D.drawString("Esc", textX, textY);

        // RETURN TO SETTINGS
        textX = frameX + gamePanel.tileSize;
        textY = frameY + gamePanel.tileSize * 9;
        graphics2D.drawString("Back", textX, textY);

        if (commandNumber == 0) {
            graphics2D.drawString("<>", textX - 26, textY);

            if (gamePanel.keyHandler.interactPressed) {
                subState = 0;
                commandNumber = 5;
            }
        }
    }

    // окно для подтверждения выхода на главный экран
    public void returnToTitleSettings(int frameY) {
        int textY = frameY + gamePanel.tileSize * 3;

        currentDialogue = "Return to the \ntitle screen?";

        for (String line: currentDialogue.split("\n")) {
            graphics2D.drawString(line, getXForCenteredText(line), textY);
            textY += 40;
        }

        String text = "Yes";
        int textX = getXForCenteredText(text);
        textY += gamePanel.tileSize * 3;
        graphics2D.drawString(text, textX, textY);

        if (commandNumber == 0) {
            graphics2D.drawString("<>", textX - 26, textY);

            if (gamePanel.keyHandler.interactPressed) {
                subState = 0;
                gamePanel.stopMusic();
                gamePanel.restart();
                gamePanel.gameState = gamePanel.titleState;
            }
        }

        text = "No";
        textX = getXForCenteredText(text);
        textY += gamePanel.tileSize;
        graphics2D.drawString(text, textX, textY);

        if (commandNumber == 1) {
            graphics2D.drawString("<>", textX - 26, textY);

            if (gamePanel.keyHandler.interactPressed) {
                subState = 0;
                commandNumber = 6;
            }
        }
    }

    // окно для подтверждения выхода на главный экран
    public void quitSettings(int frameY) {
        int textY = frameY + gamePanel.tileSize * 3;

        currentDialogue = "Close the \ngame?";

        for (String line: currentDialogue.split("\n")) {
            graphics2D.drawString(line, getXForCenteredText(line), textY);
            textY += 40;
        }

        String text = "Yes";
        int textX = getXForCenteredText(text);
        textY += gamePanel.tileSize * 3;
        graphics2D.drawString(text, textX, textY);

        if (commandNumber == 0) {
            graphics2D.drawString("<>", textX - 26, textY);

            if (gamePanel.keyHandler.interactPressed) {
                subState = 0;
                System.exit(0);
            }
        }

        text = "No";
        textX = getXForCenteredText(text);
        textY += gamePanel.tileSize;
        graphics2D.drawString(text, textX, textY);

        if (commandNumber == 1) {
            graphics2D.drawString("<>", textX - 26, textY);

            if (gamePanel.keyHandler.interactPressed) {
                subState = 0;
                commandNumber = 7;
            }
        }
    }

    // выбор предмета у торговца
    public void tradeSelectItem() {
        drawDialogueScreen();

        // WINDOW
        int x = gamePanel.tileSize * 15;
        int y = gamePanel.tileSize * 4;
        int width = gamePanel.tileSize * 3;
        int height = (int) (gamePanel.tileSize * 3.5);

        drawSubWindow(x, y, width, height);

        // TEXT
        x += gamePanel.tileSize;
        y += gamePanel.tileSize;
        graphics2D.drawString("Buy", x, y);

        if (commandNumber == 0) {
            graphics2D.drawString("<>", x - 26, y);

            if (gamePanel.keyHandler.interactPressed) {
                subState = 1;
                gamePanel.playSoundEffect(9);
            }
        }
        y += gamePanel.tileSize;
        graphics2D.drawString("Sell", x, y);

        if (commandNumber == 1) {
            graphics2D.drawString("<>", x - 26, y);

            if (gamePanel.keyHandler.interactPressed) {
                subState = 2;
                gamePanel.playSoundEffect(9);
            }
        }

        y += gamePanel.tileSize;
        graphics2D.drawString("Leave", x, y);

        if (commandNumber == 2) {
            graphics2D.drawString("<>", x - 26, y);

            if (gamePanel.keyHandler.interactPressed) {
                commandNumber = 0;
                gamePanel.playSoundEffect(9);

                gamePanel.gameState = gamePanel.dialogueState;
                currentDialogue = "Come again!";
            }
        }
    }

    // покупка предмета
    public void tradeBuyItem() {
        // инвентарь игрока
        drawInventory(gamePanel.player, false);

        // инвентарь продавца
        drawInventory(npc, true);

        int x;
        int y = gamePanel.tileSize * 9;
        int width = gamePanel.tileSize * 6;
        int height = gamePanel.tileSize * 2;

        // окно с дополнительной информацией
        x = gamePanel.tileSize * 2;

        drawSubWindow(x, y, width, height);
        graphics2D.drawString("[Esc] Back", x + 24, y + 60);

        // окно с количеством монет у игрока
        x = gamePanel.tileSize * 12;

        drawSubWindow(x, y, width, height);
        graphics2D.drawString("Coins: " + gamePanel.player.coins, x + 24, y + 60);
        graphics2D.drawImage(gold_coin, x + gamePanel.tileSize * 5, y + 33, 32, 32, null);

        // окно стоимости
        int itemIndex = getItemIndexOnSlot(npcSlotCol, npcSlotRow);
        
        if (itemIndex < npc.inventory.size()) {
            x = (int) (gamePanel.tileSize * 5.5);
            y = (int) (gamePanel.tileSize * 5.5);
            width = (int) (gamePanel.tileSize * 2.5);
            height =  gamePanel.tileSize;

            drawSubWindow(x, y, width, height);

            int price = npc.inventory.get(itemIndex).price;
            graphics2D.drawString(String.valueOf(price), x + 15, y + 35);

            graphics2D.drawImage(gold_coin, (int) (x + gamePanel.tileSize * 1.5), y + 7, 32, 32,
                    null);

            // покупка предмета
            if (gamePanel.keyHandler.interactPressed) {
                if (price > gamePanel.player.coins) {
                    subState = 0;
                    gamePanel.gameState = gamePanel.dialogueState;
                    currentDialogue = "You don't have enough coins.";
                } else if (gamePanel.player.inventory.size() == gamePanel.player.maxInventorySize) {
                    subState = 0;
                    gamePanel.gameState = gamePanel.dialogueState;
                    currentDialogue = "Your inventory is full.";
                } else {
                    gamePanel.player.coins -= price;
                    gamePanel.player.inventory.add(npc.inventory.get(itemIndex));
                    gamePanel.playSoundEffect(3);
                }
            }
        }
    }

    // продажа предмета
    public void tradeSellItem() {
        drawInventory(gamePanel.player, true);

        int x;
        int y = gamePanel.tileSize * 9;
        int width = gamePanel.tileSize * 6;
        int height = gamePanel.tileSize * 2;

        // окно с дополнительной информацией
        x = gamePanel.tileSize * 2;

        drawSubWindow(x, y, width, height);
        graphics2D.drawString("[Esc] Back", x + 24, y + 60);

        // окно с количеством монет у игрока
        x = gamePanel.tileSize * 12;

        drawSubWindow(x, y, width, height);
        graphics2D.drawString("Coins: " + gamePanel.player.coins, x + 24, y + 60);
        graphics2D.drawImage(gold_coin, x + gamePanel.tileSize * 5, y + 33, 32, 32, null);

        // окно стоимости
        int itemIndex = getItemIndexOnSlot(playerSlotCol, playerSlotRow);

        if (itemIndex < gamePanel.player.inventory.size()) {
            x = (int) (gamePanel.tileSize * 15.5);
            y = (int) (gamePanel.tileSize * 5.5);
            width = (int) (gamePanel.tileSize * 2.5);
            height =  gamePanel.tileSize;

            drawSubWindow(x, y, width, height);

            int price = gamePanel.player.inventory.get(itemIndex).price;
            graphics2D.drawString(String.valueOf(price), x + 15, y + 35);

            graphics2D.drawImage(gold_coin, (int) (x + gamePanel.tileSize * 1.5), y + 7, 32, 32,
                    null);

            // продажа предмета
            if (gamePanel.keyHandler.interactPressed) {
                if (gamePanel.player.inventory.get(itemIndex) == gamePanel.player.currentWeapon ||
                gamePanel.player.inventory.get(itemIndex) == gamePanel.player.currentShield) {
                    subState = 0;
                    gamePanel.gameState = gamePanel.dialogueState;
                    currentDialogue = "You must unequip an item to sell it.";
                } else {
                    gamePanel.player.inventory.remove(itemIndex);
                    gamePanel.player.coins += price;
                    gamePanel.playSoundEffect(3);
                }
            }
        }
    }

    // индекс предмета в инвентаре
    public int getItemIndexOnSlot(int slotCol, int slotRow) {
        return slotCol + slotRow * 5;
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
