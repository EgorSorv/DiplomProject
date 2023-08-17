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

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        x = 100;
        y = 100;
        speed = 4;
        direction = "down";
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

    public void update() {
        // обновление позиции игрока
        if (keyHandler.upPressed) {
            direction = "up";
            y -= speed;
        }
        else if (keyHandler.downPressed) {
            direction = "down";
            y += speed;
        }
        else if (keyHandler.leftPressed) {
            direction = "left";
            x -= speed;
        }
        else if (keyHandler.rightPressed) {
            direction = "right";
            x += speed;
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

        graphics2D.drawImage(image, x, y, gamePanel.tileSize, gamePanel.tileSize, null);
    }
}
