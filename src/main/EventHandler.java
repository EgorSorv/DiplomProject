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
}
