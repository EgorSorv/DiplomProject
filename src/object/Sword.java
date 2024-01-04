package object;

import entity.Entity;
import main.GamePanel;

public class Sword extends Entity {

    public Sword(GamePanel gamePanel) {
        super(gamePanel);

        name = "sword";
        downIdle = setup("/objects/sword", gamePanel.tileSize, gamePanel.tileSize);
        attackValue = 1;
        description = "[" + name + "]\nAn old sword.";
    }
}
