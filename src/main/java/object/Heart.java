package object;

import entity.Entity;
import main.GamePanel;

public class Heart extends Entity {
    GamePanel gamePanel;

    public Heart(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        type = typeNoneInventory;
        name = "heart";
        healValue = 2;

        downIdle = setup("/objects/heart_full", gamePanel.tileSize, gamePanel.tileSize);
        image = setup("/objects/heart_full", gamePanel.tileSize, gamePanel.tileSize);
        image2 = setup("/objects/heart_half", gamePanel.tileSize, gamePanel.tileSize);
        image3 = setup("/objects/heart_blank", gamePanel.tileSize, gamePanel.tileSize);
    }

    public void use(Entity entity) {
        gamePanel.playSoundEffect(2);
        gamePanel.userInterface.addMessage("Life + " + healValue);
        entity.currentLife += healValue;
    }
}
