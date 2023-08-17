package entity;

import java.awt.image.BufferedImage;

public abstract class Entity {
    public int x, y, speed; // координаты и скорость
    public BufferedImage upIdle, up1, up2, downIdle, down1, down2,
            leftIdle, left1, left2, rightIdle, right1, right2; // набор изображений
    public String direction; // направление движения
    public int spriteCounter = 0; // интервал обновления изображения
    public int spriteNum = 0; // номер изображения
    public boolean check; // переменная для срабатывания idle анимаций во время движения
}
