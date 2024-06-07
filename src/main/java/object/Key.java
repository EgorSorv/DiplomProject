package object;

import entity.Entity;
import main.GamePanel;


public class Key extends Entity {
    public Key(GamePanel gamePanel) {
        super(gamePanel);

        name = "Key";
        downIdle = setup("/objects/key", gamePanel.tileSize, gamePanel.tileSize);
        description = "[" + name + "]\nAn old key.\nIt opens something.";
        price = 100;
    }
}
