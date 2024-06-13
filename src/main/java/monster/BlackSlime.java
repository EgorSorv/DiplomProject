package monster;

import entity.Entity;
import main.GamePanel;
import object.*;

import java.util.Random;

public class BlackSlime extends Entity {
    GamePanel gamePanel;

    public BlackSlime(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        type = typeMonster;
        name = "black slime";
        defaultSpeed = 2;
        speed = defaultSpeed;
        exp = 4;
        maxLife = 8;
        currentLife = maxLife;
        attack = 6;
        defense = 1;
        currentProjectile = new Rock(gamePanel);

        solidArea.x = 3;
        solidArea.y = 18;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 42;
        solidArea.height = 30;

        getImage();
    }

    public void getImage() {
        upIdle = setup("/monsters/blackslime_idle", gamePanel.tileSize, gamePanel.tileSize);
        up1 = setup("/monsters/blackslime_move", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("/monsters/blackslime_move", gamePanel.tileSize, gamePanel.tileSize);
        downIdle = setup("/monsters/blackslime_idle", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("/monsters/blackslime_move", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("/monsters/blackslime_move", gamePanel.tileSize, gamePanel.tileSize);
        leftIdle = setup("/monsters/blackslime_idle", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("/monsters/blackslime_move", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("/monsters/blackslime_move", gamePanel.tileSize, gamePanel.tileSize);
        rightIdle = setup("/monsters/blackslime_idle", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("/monsters/blackslime_move", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("/monsters/blackslime_move", gamePanel.tileSize, gamePanel.tileSize);
    }

    public void update() {
        super.update();

        int xDistance = Math.abs(worldX - gamePanel.player.worldX);
        int yDistance = Math.abs(worldY - gamePanel.player.worldY);
        int tileDistance = (xDistance + yDistance) / gamePanel.tileSize;

        if (!monsterAgro && tileDistance < 2) {
            int random = new Random().nextInt(100) + 1;

            if (random > 50)
                monsterAgro = true;
        }

        if (monsterAgro && tileDistance > 10)
            monsterAgro = false;
    }

    public void setAction() {
        if (monsterAgro) {
            int goalCol = (gamePanel.player.worldX + gamePanel.player.solidArea.x) / gamePanel.tileSize;
            int goalRow = (gamePanel.player.worldY + gamePanel.player.solidArea.y) / gamePanel.tileSize;

            chasePlayer(goalCol, goalRow);

            int random = new Random().nextInt(100) + 1;

            if (random > 99 && !currentProjectile.alive && useProjectileCounter == 30) {
                currentProjectile.set(worldX, worldY, direction, true, this);

                for (int i = 0; i < gamePanel.projectiles[1].length; i++) {
                    if (gamePanel.projectiles[gamePanel.currentMap][i] == null) {
                        gamePanel.projectiles[gamePanel.currentMap][i] = currentProjectile;
                        break;
                    }
                }

                useProjectileCounter = 0;
            }
        } else {
            actionLockCounter++;

            if (actionLockCounter == 120) {
                Random random = new Random();
                int integer = random.nextInt(100) + 1;

                if (integer <= 25)
                    direction = "up";
                else if (integer <= 50)
                    direction = "down";
                else if (integer <= 75)
                    direction = "left";
                else
                    direction = "right";

                actionLockCounter = 0;
            }
        }
    }

    // реакция на атаку
    public void damageReaction() {
        actionLockCounter = 0;
        monsterAgro = true;
    }

    public void checkDrop() {
        int random = new Random().nextInt(100) + 1;

        // SET THE DROP
        if (random < 50) dropItem(new Coin(gamePanel));
        else if (random < 75) dropItem(new Heart(gamePanel));
        else dropItem(new ManaCrystal(gamePanel));
    }
}
