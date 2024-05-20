package object;

import entity.Entity;
import main.GamePanel;

public class ManaCrystal extends Entity {
    GamePanel gamePanel;

    public ManaCrystal(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        type = typeNoneInventory;
        name = "Mana Crystal";
        manaRestoreValue = 1;

        downIdle = setup("/objects/manacrystal", gamePanel.tileSize, gamePanel.tileSize);
        image = setup("/objects/manacrystal", gamePanel.tileSize, gamePanel.tileSize);
        image2 = setup("/objects/manacrystal_blank", gamePanel.tileSize, gamePanel.tileSize);
    }

    public void use(Entity entity) {
        gamePanel.playSoundEffect(2);
        gamePanel.userInterface.addMessage("Mana + " + manaRestoreValue);
        entity.currentLife += manaRestoreValue;
    }
}
