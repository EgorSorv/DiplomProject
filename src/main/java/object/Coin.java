package object;

import entity.Entity;
import main.GamePanel;

public class Coin extends Entity {
    GamePanel gamePanel;

    public Coin(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        type = typeNoneInventory;
        name = "Gold coin";
        costValue = 1;

        downIdle = setup("/objects/coin", gamePanel.tileSize, gamePanel.tileSize);
    }

    public void use(Entity entity) {
        gamePanel.playSoundEffect(1);
        gamePanel.userInterface.addMessage("Coin + " + costValue);
        gamePanel.player.coins += costValue;
    }
}
