package main;

public class EventHandler {
    GamePanel gamePanel;
    EventRectangle[][] eventRect;

    int previousEventX, previousEventY;
    boolean canTouchEvent = true;

    public EventHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        // зона срабатывания события
        eventRect = new EventRectangle[gamePanel.maxWorldCol][gamePanel.maxWorldRow];

        int col = 0;
        int row = 0;

        while (col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow) {
            eventRect[col][row] = new EventRectangle();

            eventRect[col][row].x = 23;
            eventRect[col][row].y = 23;
            eventRect[col][row].width = 2;
            eventRect[col][row].height = 2;

            eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
            eventRect[col][row].getEventRectDefaultY = eventRect[col][row].y;

            col++;

            if (col == gamePanel.maxWorldCol) {
                col = 0;
                row++;
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
            if (hit(27, 16, "right", false))
                damagePit(27, 16, gamePanel.dialogueState, false);

            if (hit(23, 18, "any", false))
                damagePit(23, 18, gamePanel.dialogueState, true);

            if (hit(30, 37, "right", false))
                teleport(gamePanel.dialogueState, 8, 38);

            if (hit(8, 38, "up", false))
                teleport(gamePanel.dialogueState, 30, 37);

            if (hit(21, 12, "up", true) ||
                hit(22, 12, "up", true) ||
                hit(23, 12, "up", true) ||
                hit(24, 12, "up", true) ||
                hit(25, 12, "up", true) ||

                hit(20, 8, "right", true) ||
                hit(20, 9, "right", true) ||
                hit(20, 10, "right", true) ||
                hit(20, 11, "right", true) ||

                hit(21, 7, "down", true) ||
                hit(22, 7, "down", true) ||
                hit(23, 7, "down", true) ||
                hit(24, 7, "down", true) ||
                hit(25, 7, "down", true) ||

                hit(26, 8, "left", true) ||
                hit(26, 9, "left", true) ||
                hit(26, 10, "left", true) ||
                hit(26, 11, "left", true))

                healingPool(gamePanel.dialogueState);
        }
    }

    // активацция события
    public boolean hit(int col, int row, String reqDirection, boolean fullTile) {
        boolean hit = false;

        // позиция игрока
        gamePanel.player.solidArea.x = gamePanel.player.worldX + gamePanel.player.solidArea.x;
        gamePanel.player.solidArea.y = gamePanel.player.worldY + gamePanel.player.solidArea.y;

        // позиция события
        eventRect[col][row].x = col * gamePanel.tileSize + eventRect[col][row].x;
        eventRect[col][row].y = row * gamePanel.tileSize + eventRect[col][row].y;

        if (fullTile) {
            eventRect[col][row].width = gamePanel.tileSize;
            eventRect[col][row].height = gamePanel.tileSize;
        }
        
        // проверка направления игрока
        if (gamePanel.player.solidArea.intersects(eventRect[col][row]) && !eventRect[col][row].eventDone)
            if (gamePanel.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
                hit = true;

                previousEventX = gamePanel.player.worldX;
                previousEventY = gamePanel.player.worldY;
            }
        
        // сброс
        gamePanel.player.solidArea.x = gamePanel.player.solidAreaDefaultX;
        gamePanel.player.solidArea.y = gamePanel.player.solidAreaDefaultY;
        eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
        eventRect[col][row].y = eventRect[col][row].getEventRectDefaultY;

        return hit;
    }

    // яма
    public void damagePit(int col, int row, int gameState, boolean oneTimeEvent) {
        gamePanel.gameState = gameState;
        gamePanel.playSoundEffect(6);
        gamePanel.userInterface.currentDialogue = "You fall into a pit!";
        gamePanel.player.currentLife -= 1;

        if (oneTimeEvent)
            eventRect[col][row].eventDone = true;

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

                gamePanel.assetSetter.setMonster();
            } else gamePanel.userInterface.currentDialogue = "Your health and mana are full.";
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
}
