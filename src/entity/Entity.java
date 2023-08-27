package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public abstract class Entity {
    GamePanel gamePanel;
    public int worldX, worldY; // координаты мира
    public int speed; // скорость перемещения
    public BufferedImage upIdle, up1, up2, downIdle, down1, down2,
            leftIdle, left1, left2, rightIdle, right1, right2; // набор изображений
    public String direction; // направление движения
    public int spriteCounter = 0; // интервал обновления изображения
    public int spriteNum = 0; // номер изображения
    public boolean check; // переменная для срабатывания idle анимаций во время движения

    // сплошная часть объекта
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;

    public Entity(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public  BufferedImage setup(String imagePath) {
        UtilityTool utilityTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().
                    getResourceAsStream(imagePath + ".png")));
            image = utilityTool.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }
}
