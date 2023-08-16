package entity;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {
    GamePanel gamePanel;
    KeyHandler keyHandler;

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;
    }

    public void setDefaultValues() {
        x = 100;
        y = 100;
        speed = 4;
    }

    public void update() {
        // обновление позиции игрока
        if (keyHandler.upPressed)
            y -= speed;
        else if (keyHandler.downPressed)
            y += speed;
        else if (keyHandler.leftPressed)
            x -= speed;
        else if (keyHandler.rightPressed)
            x += speed;
    }
}
