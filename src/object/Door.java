package object;

import entity.Entity;
import main.GamePanel;

public class Door extends Entity {
    public Door(GamePanel gamePanel) {
        super(gamePanel);

        name = "door";

        downIdle = setup("/objects/door");

        collision = true;
    }
}
