package object;

import entity.Entity;
import main.GamePanel;

public class Sword extends Entity {

    public Sword(GamePanel gamePanel) {
        super(gamePanel);

        type = typeSword;
        name = "Old sword";
        downIdle = setup("/objects/sword", gamePanel.tileSize, gamePanel.tileSize);
        attackValue = 1;
        attackArea.width = 36;
        attackArea.height = 36;
        description = "[" + name + "]\nAn old sword.";
    }
}
