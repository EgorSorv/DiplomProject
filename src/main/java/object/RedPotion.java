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
        price = 25;
    }

    public void use(Entity entity) {
        gamePanel.gameState = gamePanel.dialogueState;
        gamePanel.userInterface.currentDialogue = "You drink the " + name + "!\n" +
                "Your live has been recovered.";

        entity.currentLife += healValue;

        gamePanel.playSoundEffect(2);
    }
}
