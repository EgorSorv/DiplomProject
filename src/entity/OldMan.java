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
        upIdle = setup("/npc/oldman_up_idle");
        up1 = setup("/npc/oldman_up_1");
        up2 = setup("/npc/oldman_up_2");
        downIdle = setup("/npc/oldman_down_idle");
        down1 = setup("/npc/oldman_down_1");
        down2 = setup("/npc/oldman_down_2");
        leftIdle = setup("/npc/oldman_left_idle");
        left1 = setup("/npc/oldman_left_1");
        left2 = setup("/npc/oldman_left_2");
        rightIdle = setup("/npc/oldman_right_idle");
        right1 = setup("/npc/oldman_right_1");
        right2 = setup("/npc/oldman_right_2");
    }

    public void setDialogue() {
        dialogues[0] = "STANDING HERE \n I REALIZE";
        dialogues[1] = "I'm just an old man";
        dialogues[2] = "But in the past I was know \n as the greatest wizard of these lands...";
        dialogues[3] = "Ab'dul Zef-fir!!!";
        dialogues[4] = "So... what do you think?";
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
