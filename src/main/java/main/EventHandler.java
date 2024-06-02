package main;

public class EventHandler {
    GamePanel gamePanel;
    EventRectangle[][][] eventRect;

    int previousEventX, previousEventY;
    boolean canTouchEvent = true;

    public EventHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        // зона срабатывания события
        eventRect = new EventRectangle[gamePanel.maxMap][gamePanel.maxWorldCol][gamePanel.maxWorldRow];

        int map = 0;
        int col = 0;
        int row = 0;

        while (map < gamePanel.maxMap && col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow) {
            eventRect[map][col][row] = new EventRectangle();

            eventRect[map][col][row].x = 23;
            eventRect[map][col][row].y = 23;
            eventRect[map][col][row].width = 2;
            eventRect[map][col][row].height = 2;

            eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
            eventRect[map][col][row].getEventRectDefaultY = eventRect[map][col][row].y;

            col++;

            if (col == gamePanel.maxWorldCol) {
                col = 0;
                row++;
            }

            if (row == gamePanel.maxWorldRow) {
                row = 0;
                map++;
            }
        }
    }

    // проверка события
    public void checkEvent() {
        // игрок находится на определенном расстоянии для повторного срабатывания события
        int xDistance = Math.abs(gamePanel.player.worldX - previousEventX);
        int yDistance = Math.abs(gamePanel.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);

        if (distance > gamePanel.tileSize / 2)
            canTouchEvent = true;

        if (canTouchEvent) {
            if (hit(0, 27, 16, "right", false))
                damagePit(0, 27, 16, gamePanel.dialogueState, false);

            else if (hit(0, 23, 18, "any", false))
                damagePit(0, 23, 18, gamePanel.dialogueState, true);

            else if (hit(0, 30, 37, "right", false))
                teleport(gamePanel.dialogueState, 8, 38);

            else if (hit(0, 8, 38, "up", false))
                teleport(gamePanel.dialogueState, 30, 37);

            else if (hit(0, 10, 39, "any", false))
                transition(1, 12, 13);

            else if (hit(1, 12, 13, "any", false))
                transition(0, 10, 39);

            else if (hit(0, 21, 12, "up", true) ||
                hit(0, 22, 12, "up", true) ||
                hit(0, 23, 12, "up", true) ||
                hit(0, 24, 12, "up", true) ||
                hit(0, 25, 12, "up", true) ||

                hit(0, 20, 8, "right", true) ||
                hit(0, 20, 9, "right", true) ||
                hit(0, 20, 10, "right", true) ||
                hit(0, 20, 11, "right", true) ||

                hit(0, 21, 7, "down", true) ||
                hit(0, 22, 7, "down", true) ||
                hit(0, 23, 7, "down", true) ||
                hit(0, 24, 7, "down", true) ||
                hit(0, 25, 7, "down", true) ||

                hit(0, 26, 8, "left", true) ||
                hit(0, 26, 9, "left", true) ||
                hit(0, 26, 10, "left", true) ||
                hit(0, 26, 11, "left", true))

                healingPool(gamePanel.dialogueState);
        }
    }

    // активацция события
    public boolean hit(int map, int col, int row, String reqDirection, boolean fullTile) {
        boolean hit = false;

        if (map == gamePanel.currentMap) {
            // позиция игрока
            gamePanel.player.solidArea.x = gamePanel.player.worldX + gamePanel.player.solidArea.x;
            gamePanel.player.solidArea.y = gamePanel.player.worldY + gamePanel.player.solidArea.y;

            // позиция события
            eventRect[map][col][row].x = col * gamePanel.tileSize + eventRect[map][col][row].x;
            eventRect[map][col][row].y = row * gamePanel.tileSize + eventRect[map][col][row].y;

            // использование для события всей плитки
            if (fullTile) {
                eventRect[map][col][row].width = gamePanel.tileSize;
                eventRect[map][col][row].height = gamePanel.tileSize;
            }

            // проверка направления игрока
            if (gamePanel.player.solidArea.intersects(eventRect[map][col][row]) && !eventRect[map][col][row].eventDone)
                if (gamePanel.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
                    hit = true;

                    previousEventX = gamePanel.player.worldX;
                    previousEventY = gamePanel.player.worldY;
                }

            // сброс
            gamePanel.player.solidArea.x = gamePanel.player.solidAreaDefaultX;
            gamePanel.player.solidArea.y = gamePanel.player.solidAreaDefaultY;
            eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
            eventRect[map][col][row].y = eventRect[map][col][row].getEventRectDefaultY;

        }
        
        return hit;
    }

    // яма
    public void damagePit(int map, int col, int row, int gameState, boolean oneTimeEvent) {
        gamePanel.gameState = gameState;
        gamePanel.playSoundEffect(6);
        gamePanel.userInterface.currentDialogue = "You fall into a pit!";
        gamePanel.player.currentLife -= 1;

        if (oneTimeEvent) eventRect[map][col][row].eventDone = true;

        canTouchEvent = false;
    }

    // лечение
    public void healingPool(int gameState) {
        if (gamePanel.keyHandler.interactPressed) {
            gamePanel.gameState = gameState;
            gamePanel.player.attackCanceled = true;
            gamePanel.playSoundEffect(2);

            if (gamePanel.player.currentLife < gamePanel.player.maxLife ||
                    gamePanel.player.currentMana < gamePanel.player.maxMana) {
                gamePanel.userInterface.currentDialogue = "You drink the water.\n" +
                        "Your health and mana have been restored.";

                gamePanel.player.currentLife = gamePanel.player.maxLife;
                gamePanel.player.currentMana = gamePanel.player.maxMana;

            } else gamePanel.userInterface.currentDialogue = "Your health and mana are full.";

            gamePanel.assetSetter.setMonster();
        }
    }

    // перемещение
    public void teleport(int gameState, int destinationX, int destinationY) {
        gamePanel.gameState = gameState;
        gamePanel.playSoundEffect(7);
        gamePanel.userInterface.currentDialogue = "Teleport!";

        gamePanel.player.worldX = gamePanel.tileSize * destinationX;
        gamePanel.player.worldY = gamePanel.tileSize * destinationY;
    }

    // переход между картами
    public void transition(int map, int col, int row) {
        gamePanel.currentMap = map;

        gamePanel.player.worldX = gamePanel.tileSize * col;
        gamePanel.player.worldY = gamePanel.tileSize * row;

        previousEventX = gamePanel.player.worldX;
        previousEventY = gamePanel.player.worldY;

        canTouchEvent = false;

        gamePanel.playSoundEffect(13);
    }
}
