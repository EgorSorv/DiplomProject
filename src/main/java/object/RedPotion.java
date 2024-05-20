package object;

import entity.Entity;
import main.GamePanel;

public class RedPotion extends Entity {
    GamePanel gamePanel;

    public RedPotion(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        type = typeConsumable;
        name = "Red potion";
        healValue = 5;
        downIdle = setup("/objects/potion_red", gamePanel.tileSize, gamePanel.tileSize);
        description = "[" + name + "]\nHeals your wounds\nby " + healValue + " HP.";
    }

    public void use(Entity entity) {
        gamePanel.gameState = gamePanel.dialogueState;
        gamePanel.userInterface.currentDialogue = "You drink the " + name + "!\n" +
                "Your live has been recovered.";
        entity.currentLife += healValue;

        if (gamePanel.player.currentLife > gamePanel.player.maxLife)
            gamePanel.player.currentLife = gamePanel.player.maxLife;

        gamePanel.playSoundEffect(2);
    }
}
