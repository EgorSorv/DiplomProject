package monster;

import entity.Entity;
import main.GamePanel;

import java.util.Random;

public class GreenSlime extends Entity {
    public GreenSlime(GamePanel gamePanel) {
        super(gamePanel);

        entityType = 2;
        name = "green slime";
        speed = 1;
        maxLife = 4;
        currentLife = maxLife;

        solidArea.x = 3;
        solidArea.y = 18;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 42;
        solidArea.height = 30;

        getImage();
    }

    public void getImage() {
        upIdle = setup("/monsters/greenslime_idle");
        up1 = setup("/monsters/greenslime_move");
        up2 = setup("/monsters/greenslime_move");
        downIdle = setup("/monsters/greenslime_idle");
        down1 = setup("/monsters/greenslime_move");
        down2 = setup("/monsters/greenslime_move");
        leftIdle = setup("/monsters/greenslime_idle");
        left1 = setup("/monsters/greenslime_move");
        left2 = setup("/monsters/greenslime_move");
        rightIdle = setup("/monsters/greenslime_idle");
        right1 = setup("/monsters/greenslime_move");
        right2 = setup("/monsters/greenslime_move");
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
    }
}
