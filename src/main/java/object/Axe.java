package object;

import entity.Entity;
import main.GamePanel;

public class Axe extends Entity {
    public Axe(GamePanel gamePanel) {
        super(gamePanel);

        type = typeAxe;
        name = "Woodcutter's axe";
        downIdle = setup("/objects/axe", gamePanel.tileSize, gamePanel.tileSize);
        attackValue = 2;
        attackArea.width = 30;
        attackArea.height = 30;
        description = "[Woodcutter's axe]\nA bit rusty axe. It\nstill can cut some trees.";
        price = 75;
    }
}
