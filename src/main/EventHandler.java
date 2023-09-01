package main;

import java.awt.*;

public class EventHandler {
    GamePanel gamePanel;
    Rectangle eventRect;
    int eventRectDefaultX, getEventRectDefaultY;

    public EventHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        // зона срабатывания события
        eventRect = new Rectangle();
        eventRect.x = 23;
        eventRect.y = 23;
        eventRect.width = 2;
        eventRect.height = 2;
        eventRectDefaultX = eventRect.x;
        getEventRectDefaultY = eventRect.y;
    }

    // проверка события
    public void checkEvent() {
        if (hit(27, 16, "right"))
            damagePit(gamePanel.dialogueState);
        else {

            eventRect.width = gamePanel.tileSize;
            eventRect.height = gamePanel.tileSize;

            if (hit(21, 12, "up") ||
                hit(22, 12, "up") ||
                hit(23, 12, "up") ||
                hit(24, 12, "up") ||

                hit(25, 12, "up") ||
                hit(20, 8, "right") ||
                hit(20, 9, "right") ||
                hit(20, 10, "right") ||
                hit(20, 11, "right") ||

                hit(21, 7, "down") ||
                hit(22, 7, "down") ||
                hit(23, 7, "down") ||
                hit(24, 7, "down") ||
                hit(25, 7, "down") ||

                hit(26, 8, "left") ||
                hit(26, 9, "left") ||
                hit(26, 10, "left") ||
                hit(26, 11, "left")) {

                healingPool(gamePanel.dialogueState);

                eventRect.width = 2;
                eventRect.height = 2;
            }
        }
    }

    // активацция события
    public boolean hit(int eventCol, int eventRow, String reqDirection) {
        boolean hit = false;

        // позиция игрока
        gamePanel.player.solidArea.x = gamePanel.player.worldX + gamePanel.player.solidArea.x;
        gamePanel.player.solidArea.y = gamePanel.player.worldY + gamePanel.player.solidArea.y;

        // позиция события
        eventRect.x = eventCol * gamePanel.tileSize + eventRect.x;
        eventRect.y = eventRow * gamePanel.tileSize + eventRect.y;
        
        // проверка направления игрока
        if (gamePanel.player.solidArea.intersects(eventRect))
            if (gamePanel.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any"))
                hit = true;
        
        // сброс
        gamePanel.player.solidArea.x = gamePanel.player.solidAreaDefaultX;
        gamePanel.player.solidArea.y = gamePanel.player.solidAreaDefaultY;
        eventRect.x = eventRectDefaultX;
        eventRect.y = getEventRectDefaultY;

        return hit;
    }

    // яма
    public void damagePit(int gameState) {
        gamePanel.gameState = gameState;
        gamePanel.userInterface.currentDialogue = "You fall into a pit!";
        gamePanel.player.currentLife -= 1;
    }

    // лечение
    public void healingPool(int gameState) {
        if (gamePanel.keyHandler.interactPressed) {
            gamePanel.gameState = gameState;
            if (gamePanel.player.currentLife < gamePanel.player.maxLife) {
                gamePanel.userInterface.currentDialogue = "You drink the water.\nYour health has been increased.";
                gamePanel.player.currentLife += 1;
            } else gamePanel.userInterface.currentDialogue = "Your health is full.";
        }
    }
}
