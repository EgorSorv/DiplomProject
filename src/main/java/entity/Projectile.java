package entity;

import main.GamePanel;

// снаряды
public class Projectile extends Entity {
    Entity entity;

    public Projectile(GamePanel gamePanel) {
        super(gamePanel);
    }

    public void set(int worldX, int worldY, String direction, boolean alive, Entity entity) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direction;
        this.alive = alive;
        this.entity = entity;
        this.currentLife = maxLife;
    }

    public void update() {
        if (entity == gamePanel.player) {
            int monsterIndex = gamePanel.collisionChecker.checkEntity(this, gamePanel.monster);

            if (monsterIndex != -1) {
                gamePanel.player.damageMonster(monsterIndex, attack);
                generateParticle(entity.currentProjectile, gamePanel.monster[monsterIndex]);
                alive = false;
            }
        } else {
            boolean contactPlayer = gamePanel.collisionChecker.checkPlayer(this);

            if (!gamePanel.player.invincible && contactPlayer) {
                damagePlayer(attack);
                generateParticle(entity.currentProjectile, gamePanel.player);
                alive = false;
            }
        }

        switch (direction) {
            case "up" -> worldY -= speed;
            case "down" -> worldY += speed;
            case "left" -> worldX -= speed;
            case "right" -> worldX += speed;
        }

        currentLife --;
        if (currentLife <= 0) alive = false;

        spriteCounter++;
        if (spriteCounter > 12) {
            if (spriteNum == 1) spriteNum = 0;
            else if (spriteNum == 0) spriteNum = 1;
            spriteCounter = 0;
        }
    }

    public boolean haveResource(Entity entity) {
        return false;
    }

    public void subtractResource(Entity entity) {}
}
