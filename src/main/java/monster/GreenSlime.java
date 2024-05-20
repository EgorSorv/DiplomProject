package monster;

import entity.Entity;
import main.GamePanel;
import object.Coin;
import object.Heart;
import object.ManaCrystal;
import object.Rock;

import java.util.Random;

public class GreenSlime extends Entity {
    GamePanel gamePanel;

    public GreenSlime(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        type = typeMonster;
        name = "green slime";
        speed = 1;
        exp = 2;
        maxLife = 4;
        currentLife = maxLife;
        attack = 5;
        defense = 0;
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
        upIdle = setup("/monsters/greenslime_idle", gamePanel.tileSize, gamePanel.tileSize);
        up1 = setup("/monsters/greenslime_move", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("/monsters/greenslime_move", gamePanel.tileSize, gamePanel.tileSize);
        downIdle = setup("/monsters/greenslime_idle", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("/monsters/greenslime_move", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("/monsters/greenslime_move", gamePanel.tileSize, gamePanel.tileSize);
        leftIdle = setup("/monsters/greenslime_idle", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("/monsters/greenslime_move", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("/monsters/greenslime_move", gamePanel.tileSize, gamePanel.tileSize);
        rightIdle = setup("/monsters/greenslime_idle", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("/monsters/greenslime_move", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("/monsters/greenslime_move", gamePanel.tileSize, gamePanel.tileSize);
    }

    public void setAction() {
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

        int integer = new Random().nextInt(100) + 1;

        if (integer > 99 && !currentProjectile.alive && useProjectileCounter == 30) {
            currentProjectile.set(worldX, worldY, direction, true, this);

            gamePanel.projectiles.add(currentProjectile);

            useProjectileCounter = 0;
        }
    }

    // реакция на атаку
    public void damageReaction() {
        actionLockCounter = 0;
        direction = gamePanel.player.direction;
    }

    public void checkDrop() {
        int random = new Random().nextInt(100) + 1;

        // SET THE DROP
        if (random < 50) dropItem(new Coin(gamePanel));
        else if (random < 75) dropItem(new Heart(gamePanel));
        else dropItem(new ManaCrystal(gamePanel));
    }
}
