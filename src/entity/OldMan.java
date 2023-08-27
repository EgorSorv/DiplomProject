package entity;

import main.GamePanel;

public class OldMan extends Entity {
    public OldMan (GamePanel gamePanel) {
        super(gamePanel);

        direction = "down";
        speed = 1;
    }
}
