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
    public boolean idleCheck; // переменная для срабатывания idle анимаций во время движения

    // сплошная часть объекта
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    public int actionLockCounter = 0; // продолжительность поведения
    String[] dialogues = new String[20]; // массив для диалогов
    int dialogueIndex = 0;

    public Entity(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    // модель поведения
    public void setAction() {}
    public void speak() {}

    public void update() {
        if (!collisionOn) {
            setAction();

            gamePanel.collisionChecker.checkTile(this);
            gamePanel.collisionChecker.checkObject(this, false);
            gamePanel.collisionChecker.checkPlayer(this);

            if (!collisionOn) {
                switch (direction) {
                    case "up" -> worldY -= speed;
                    case "down" -> worldY += speed;
                    case "left" -> worldX -= speed;
                    case "right" -> worldX += speed;
                }
            }

            spriteCounter++;

            if (!collisionOn) {
                if (spriteCounter > 10 - speed) {
                    if (spriteNum == 0 && !idleCheck) {
                        spriteNum = 1;
                        idleCheck = true;
                    } else if (spriteNum == 0) {
                        spriteNum = 2;
                        idleCheck = false;
                    } else if (spriteNum == 1)
                        spriteNum = 0;
                    else if (spriteNum == 2)
                        spriteNum = 0;

                    spriteCounter = 0;
                }
            }
        } else {
            collisionOn = false;
            spriteNum = 0;
        }
    }

    public void draw (Graphics2D graphics2D) {
        BufferedImage image = null;

        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

        if (worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX &&
                worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX &&
                worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY &&
                worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY) {

            switch (direction) {
                case "up" -> {
                    if (spriteNum == 0)
                        image = upIdle;
                    if (spriteNum == 1)
                        image = up1;
                    if (spriteNum == 2)
                        image = up2;
                }
                case "down" -> {
                    if (spriteNum == 0)
                        image = downIdle;
                    if (spriteNum == 1)
                        image = down1;
                    if (spriteNum == 2)
                        image = down2;
                }
                case "left" -> {
                    if (spriteNum == 0)
                        image = leftIdle;
                    if (spriteNum == 1)
                        image = left1;
                    if (spriteNum == 2)
                        image = left2;
                }
                case "right" -> {
                    if (spriteNum == 0)
                        image = rightIdle;
                    if (spriteNum == 1)
                        image = right1;
                    if (spriteNum == 2)
                        image = right2;
                }
            }

            graphics2D.drawImage(image, screenX, screenY,
                    gamePanel.tileSize, gamePanel.tileSize, null);
        }
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
