package interactive_tile;

import entity.Entity;
import main.GamePanel;

import java.awt.*;

public class InteractiveTile extends Entity {
    GamePanel gamePanel;
    public boolean destructible = false; // разрушаемость объекта

    public InteractiveTile(GamePanel gamePanel, int col, int row) {
        super(gamePanel);
        this.gamePanel = gamePanel;
    }

    // проверка на наличие нужного предмета
    public boolean isCorrectItem(Entity entity) {
        return false;
    }

    public void playSoundEffect() {}

    public InteractiveTile getDestroyedForm() {
        return null;
    }

    public void update() {
        if (invincible)
            invincibleCounter++;

        if (invincibleCounter > 20) {
            invincible = false;
            invincibleCounter = 0;
        }
    }

    public void draw(Graphics2D graphics2D) {
        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

        if (worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX &&
            worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX &&
            worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY &&
            worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY)

            graphics2D.drawImage(downIdle, screenX, screenY, null);
    }
}
