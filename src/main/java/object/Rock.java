package object;

import entity.Projectile;
import main.GamePanel;

public class Rock extends Projectile {
    GamePanel gamePanel;

    public Rock(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        name = "Rock";
        speed = 8;
        maxLife = 80;
        currentLife = maxLife;
        attack = 2;
        manaCost = 1;
        alive = false;

        getImage();
    }

    public void getImage() {
        upIdle = setup("/projectiles/rock", gamePanel.tileSize, gamePanel.tileSize);
        up1 = setup("/projectiles/rock", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("/projectiles/rock", gamePanel.tileSize, gamePanel.tileSize);
        downIdle = setup("/projectiles/rock", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("/projectiles/rock", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("/projectiles/rock", gamePanel.tileSize, gamePanel.tileSize);
        leftIdle = setup("/projectiles/rock", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("/projectiles/rock", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("/projectiles/rock", gamePanel.tileSize, gamePanel.tileSize);
        rightIdle = setup("/projectiles/rock", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("/projectiles/rock", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("/projectiles/rock", gamePanel.tileSize, gamePanel.tileSize);
    }
}
