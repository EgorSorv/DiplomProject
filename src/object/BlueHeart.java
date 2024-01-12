package object;

import entity.Entity;
import main.GamePanel;

public class BlueHeart extends Entity {
    public BlueHeart(GamePanel gamePanel) {
        super(gamePanel);

        name = "blueHeart";
        image = setup("/objects/blueheart", gamePanel.tileSize, gamePanel.tileSize);
    }
}
