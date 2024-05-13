package object;

import entity.Entity;
import main.GamePanel;

public class Chest extends Entity {
    public Chest(GamePanel gamePanel) {
        super(gamePanel);

        name = "chest";

        downIdle = setup("/objects/chest", gamePanel.tileSize, gamePanel.tileSize);
    }
}
