package entity;

import main.GamePanel;
import main.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {
    KeyHandler keyHandler;
    public final int screenX, screenY; // позиция игрока на экране

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        super(gamePanel);
        this.keyHandler = keyHandler;

        screenX = gamePanel.screenWidth / 2 - gamePanel.tileSize / 2; // перенос персонажа в центр экрана
        screenY = gamePanel.screenHeight / 2 - gamePanel.tileSize / 2;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = gamePanel.tileSize * 23;
        worldY = gamePanel.tileSize * 21;
        speed = 4;
        direction = "down";

        maxLife = 6;
        currentLife = maxLife;
    }

    // получение изображения
    public void getPlayerImage() {
        upIdle = setup("/player/Player_up_idle");
        up1 = setup("/player/Player_up_1");
        up2 = setup("/player/Player_up_2");
        downIdle = setup("/player/Player_down_idle");
        down1 = setup("/player/Player_down_1");
        down2 = setup("/player/Player_down_2");
        leftIdle = setup("/player/Player_left_idle");
        left1 = setup("/player/Player_left_1");
        left2 = setup("/player/Player_left_2");
        rightIdle = setup("/player/Player_right_idle");
        right1 = setup("/player/Player_right_1");
        right2 = setup("/player/Player_right_2");
    }

    // обновление позиции игрока
    public void update() {
        if (!collisionOn) {
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
                gamePanel.collisionChecker.checkTile(this);

                // CHECK OBJECT COLLISION
                int objectIndex = gamePanel.collisionChecker.checkObject(this, true);
                pickUpObject(objectIndex);

                // CHECK NPC COLLISION
                int npcIndex = gamePanel.collisionChecker.checkEntity(this, gamePanel.npc);
                interactNPC(npcIndex);

                // CHECK MONSTER COLLISION
                int monsterIndex = gamePanel.collisionChecker.checkEntity(this, gamePanel.monster);
                contactMonster(monsterIndex);

                // CHECK EVENT
                gamePanel.eventHandler.checkEvent();

                gamePanel.keyHandler.interactPressed = false;

                // PLAYER MOVEMENT
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
            }
            else spriteNum = 0;
        } else {
            collisionOn = false;
            spriteNum = 0;
        }

        // INVINCIBLE
        if (invincible) {
            invincibleCounter++;

            if (invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

    // подобрать объект
    public void pickUpObject(int index) {
        if (index != -1) {

        }
    }

    // взаимодействие с нип
    public void interactNPC(int index) {
        if (index != -1) {
            if (gamePanel.keyHandler.interactPressed) {
                gamePanel.gameState = gamePanel.dialogueState;
                gamePanel.npc[index].speak();
            }
        }
    }

    // контакт с монстром
    public void contactMonster(int index) {
        if (index != -1)
            if (!invincible) {
                currentLife -= 1;
                invincible = true;
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

        // игрок становится прозрачным
        if (invincible)
            graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));

        graphics2D.drawImage(image, screenX, screenY, null);

        // сброс прозрачности
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
}
