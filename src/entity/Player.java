package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity {
    GamePanel gamePanel;
    KeyHandler keyHandler;

    public final int screenX, screenY; // позиция игрока на экране

    public int hasKey = 0; // количество ключей у игрока

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        screenX = gamePanel.screenWidth / 2 - gamePanel.tileSize / 2; // перенос персонажа в центр экрана
        screenY = gamePanel.screenHeight / 2 - gamePanel.tileSize / 2;

        solidArea = new Rectangle(); // сплошная часть игрока
        solidArea.x = 4 * gamePanel.scale - 1;
        solidArea.y = 8 * gamePanel.scale - 1;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.height = 8 * gamePanel.scale - 1;
        solidArea.width = 8 * gamePanel.scale - 1;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = gamePanel.tileSize * 23;
        worldY = gamePanel.tileSize * 21;
        speed = 4;
        direction = "down";
        check = false;
    }

    // получение изображения
    public void getPlayerImage() {
        try {
            upIdle = ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/player/Player_up_idle.png")));

            up1 = ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/player/Player_up_1.png")));

            up2 = ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/player/Player_up_2.png")));

            downIdle = ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/player/Player_down_idle.png")));

            down1 = ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/player/Player_down_1.png")));

            down2 = ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/player/Player_down_2.png")));

            leftIdle = ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/player/Player_left_idle.png")));

            left1 = ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/player/Player_left_1.png")));

            left2 = ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/player/Player_left_2.png")));

            rightIdle = ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/player/Player_right_idle.png")));

            right1 = ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/player/Player_right_1.png")));

            right2 = ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/player/Player_right_2.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // обновление позиции игрока
    public void update() {
        if (keyHandler.upPressed || keyHandler.downPressed ||
                keyHandler.leftPressed || keyHandler.rightPressed) {

            if (keyHandler.upPressed)
                direction = "up";
            else if (keyHandler.downPressed)
                direction = "down";
            else if (keyHandler.leftPressed)
                direction = "left";
            else
                direction = "right";

            // CHECK TILE COLLISION
            collisionOn = false;
            gamePanel.collisionChecker.checkTile(this);

            // CHECK OBJECT COLLISION
            int objectIndex = gamePanel.collisionChecker.checkObject(this, true);
            pickUpObject(objectIndex);

            if (!collisionOn) {
                switch (direction) {
                    case "up" -> worldY -= speed;
                    case "down" -> worldY += speed;
                    case "left" -> worldX -= speed;
                    case "right" -> worldX += speed;
                }
            }

            spriteCounter++;

            if (spriteCounter > 10 - speed) {
                if (spriteNum == 0 && !check) {
                    spriteNum = 1;
                    check = true;
                } else if (spriteNum == 0) {
                    spriteNum = 2;
                    check = false;
                } else if (spriteNum == 1)
                    spriteNum = 0;
                else if (spriteNum == 2)
                    spriteNum = 0;

                spriteCounter = 0;
            }
        }
        else
            spriteNum = 0;
    }

    // подобрать объект
    public void pickUpObject(int index) {
        if (index != -1) {
            String objectName = gamePanel.obj[index].name;

            switch (objectName) {
                case "key" -> {
                    gamePanel.playSoundEffect(1);
                    hasKey++;
                    gamePanel.obj[index] = null;
                    gamePanel.userInterface.showMessage("You got a key!");
                }
                case "door" -> {
                    if (hasKey > 0) {
                        gamePanel.playSoundEffect(3);
                        gamePanel.obj[index] = null;
                        hasKey--;
                        gamePanel.userInterface.showMessage("The door is now open!");
                    }
                    else
                        gamePanel.userInterface.showMessage("You need a key!");
                }
                case "boots" -> {
                    gamePanel.playSoundEffect(2);
                    speed += 1;
                    gamePanel.obj[index] = null;
                    gamePanel.userInterface.showMessage("Speed up!");
                }
                case "chest" -> {
                    gamePanel.userInterface.gameFinished = true;
                    gamePanel.stopMusic();
                    gamePanel.playSoundEffect(4);
                }
            }
        }
    }

    // отрисовка изображений
    public void draw(Graphics2D graphics2D) {
        BufferedImage image = null;

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

        graphics2D.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
    }
}
