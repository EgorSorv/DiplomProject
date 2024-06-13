package object;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

import java.awt.*;

public class Rock extends Projectile {
    GamePanel gamePanel;

    public Rock(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        name = "Rock";
        speed = 8;
        maxLife = 20;
        currentLife = maxLife;
        attack = 4;
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

    // проверка текущей маны у игрока для использования снаряда
    public boolean haveResource(Entity entity) {
        return entity.ammo >= manaCost;
    }

    // вычитание стоимости снаряда
    public void subtractResource(Entity entity) {
        entity.ammo -= manaCost;
    }

    public Color getParticleColor() {
        return new Color(20, 20, 20);
    }

    public int getParticleSize() {
        return 6;
    }

    public int getParticleSpeed() {
        return 1;
    }

    public int getParticleMaxLife() {
        return 20;
    }
}
