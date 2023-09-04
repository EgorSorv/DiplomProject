package object;

import entity.Entity;
import main.GamePanel;

public class BlueHeart extends Entity {
    public BlueHeart(GamePanel gamePanel) {
        super(gamePanel);

        name = "blueHeart";

        downIdle = setup("/objects/blueheart.png");
    }
}
