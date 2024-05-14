package object;

import entity.Projectile;
import main.GamePanel;

public class Fireball extends Projectile {
    GamePanel gamePanel;

    // параметры огненного шара
    public Fireball(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        name = "Fireball";
        speed = 10;
        maxLife = 30;
        currentLife = maxLife;
        attack = 2;
        manaCost = 1;
        alive = false;
        getImage();
    }

    public void getImage() {
        upIdle = setup("/projectiles/fireball_up_idle", gamePanel.tileSize, gamePanel.tileSize);
        up1 = setup("/projectiles/fireball_up_move", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("/projectiles/fireball_up_move", gamePanel.tileSize, gamePanel.tileSize);
        downIdle = setup("/projectiles/fireball_down_idle", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("/projectiles/fireball_down_move", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("/projectiles/fireball_down_move", gamePanel.tileSize, gamePanel.tileSize);
        leftIdle = setup("/projectiles/fireball_left_idle", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("/projectiles/fireball_left_move", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("/projectiles/fireball_left_move", gamePanel.tileSize, gamePanel.tileSize);
        rightIdle = setup("/projectiles/fireball_right_idle", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("/projectiles/fireball_right_move", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("/projectiles/fireball_right_move", gamePanel.tileSize, gamePanel.tileSize);
    }
}
