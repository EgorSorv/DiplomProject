package object;

import entity.Entity;
import main.GamePanel;

public class BlueShield extends Entity {

    public BlueShield(GamePanel gamePanel) {
        super(gamePanel);

        type = typeShield;
        name = "Blue shield";
        downIdle = setup("/objects/shield_blue", gamePanel.tileSize, gamePanel.tileSize);
        defenseValue = 2;
        description = "[" + name + "]\nA shiny blue shield.";
        price = 250;
    }
}
