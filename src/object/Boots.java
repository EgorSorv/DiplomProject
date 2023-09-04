package object;

import entity.Entity;
import main.GamePanel;

public class Boots extends Entity {
    public Boots(GamePanel gamePanel) {
        super(gamePanel);

        name = "boots";

        downIdle = setup("/objects/boots.png");
    }
}
