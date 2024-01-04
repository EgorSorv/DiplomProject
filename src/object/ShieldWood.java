package object;

import entity.Entity;
import main.GamePanel;

public class ShieldWood extends Entity {
    public ShieldWood(GamePanel gamePanel) {
        super(gamePanel);

        name = "wood shield";
        downIdle = setup("/objects/shield_wood", gamePanel.tileSize, gamePanel.tileSize);
        defenseValue = 1;
        description = "[" + name + "]\nSimple wooden shield.";
    }
}
