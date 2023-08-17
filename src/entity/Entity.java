package entity;

import java.awt.image.BufferedImage;

public abstract class Entity {
    public int x, y, speed;
    public BufferedImage upIdle, up1, up2, downIdle, down1, down2,
            leftIdle, left1, left2, rightIdle, right1, right2;
    public String direction;
    public int spriteCounter = 0;
    public int spriteNum = 0;
}
