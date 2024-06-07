package object;

import entity.Entity;
import main.GamePanel;

public class WoodShield extends Entity {
    public WoodShield(GamePanel gamePanel) {
        super(gamePanel);

        type = typeShield;
        name = "Wood shield";
        downIdle = setup("/objects/shield_wood", gamePanel.tileSize, gamePanel.tileSize);
        defenseValue = 1;
        description = "[" + name + "]\nSimple wooden shield.";
        price = 35;
    }
}
