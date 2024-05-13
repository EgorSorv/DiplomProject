package entity;

import main.GamePanel;

import java.util.Random;

public class OldMan extends Entity {
    public OldMan (GamePanel gamePanel) {
        super(gamePanel);

        direction = "down";
        speed = 1;

        getImage();
        setDialogue();
    }

    public void getImage() {
        upIdle = setup("/npc/oldman_up_idle", gamePanel.tileSize, gamePanel.tileSize);
        up1 = setup("/npc/oldman_up_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("/npc/oldman_up_2", gamePanel.tileSize, gamePanel.tileSize);
        downIdle = setup("/npc/oldman_down_idle", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("/npc/oldman_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("/npc/oldman_down_2", gamePanel.tileSize, gamePanel.tileSize);
        leftIdle = setup("/npc/oldman_left_idle", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("/npc/oldman_left_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("/npc/oldman_left_2", gamePanel.tileSize, gamePanel.tileSize);
        rightIdle = setup("/npc/oldman_right_idle", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("/npc/oldman_right_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("/npc/oldman_right_2", gamePanel.tileSize, gamePanel.tileSize);
    }

    public void setDialogue() {
        dialogues[0] = "Hello, lad!";
        dialogues[1] = "So you've come to this island\nto find the treasure?";
        dialogues[2] = "I used to be a great wizard but now...\nI'm a bit too old for taking an adventure.";
        dialogues[3] = "Well, good luck on you.";
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

    public void speak() {
        super.speak();
    }
}
