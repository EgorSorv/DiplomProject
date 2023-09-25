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

    // IMAGES
    public BufferedImage upIdle, up1, up2, downIdle, down1, down2,
            leftIdle, left1, left2, rightIdle, right1, right2; // набор изображений
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2,
            attackLeft1, attackLeft2, attackRight1, attackRight2;
    public BufferedImage image, image2, image3;

    // SOLID AREA
    public Rectangle solidArea = new Rectangle();
    public int solidAreaDefaultX, solidAreaDefaultY;
    public Rectangle attackArea = new Rectangle(); // зона удара

    // STATES
    public int worldX, worldY; // координаты мира
    public String direction = "down"; // направление движения
    public int spriteNum = 0; // номер изображения

    // DIALOGUE
    int dialogueIndex = 0;
    String[] dialogues = new String[20]; // массив для диалогов

    // BOOLEAN FLAGS
    public boolean collision = false;
    public boolean collisionOn = false;
    public boolean invincible = false;  // неуязвимость
    public boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;
    public boolean idleCheck; // переменная для срабатывания idle анимаций во время движения
    boolean hpBarOn = false;

    // COUNTERS
    public int spriteCounter = 0; // интервал обновления изображения
    public int actionLockCounter = 0; // продолжительность поведения
    public int invincibleCounter = 0; // действие неуязвимости
    int hpBarCounter = 0;
    int dyingCounter = 0;

    // CHARACTER ATTRIBUTES
    public int entityType; // тип сущности
    public String name;
    public int level;
    public int exp;
    public int nextLevelExp;
    public int maxLife; // максимальное здоровье
    public int currentLife; // текущее здоровье
    public int strength;
    public int dexterity;
    public int attack;
    public int defense;
    public int speed; // скорость перемещения
    public int coins;
    public Entity currentWeapon;
    public Entity currentShield;

    // ITEM ATTRIBUTES
    public int attackValue;
    public int defenseValue;

    public Entity(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        solidArea.x = 4 * gamePanel.scale - 1;
        solidArea.y = 8 * gamePanel.scale - 1;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 8 * gamePanel.scale - 1;
        solidArea.height = 8 * gamePanel.scale - 1;
    }

    // модель поведения
    public void setAction() {}
    public void damageReaction() {}
    public void speak() {
        if(dialogues[dialogueIndex] == null)
            dialogueIndex = 0;

        gamePanel.userInterface.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;

        // поворот нип к игроку
        switch (gamePanel.player.direction) {
            case "up" -> direction = "down";
            case "down" -> direction = "up";
            case "left" -> direction = "right";
            case "right" -> direction = "left";
        }
    }

    public void update() {
        if (!collisionOn) {
            setAction();

            gamePanel.collisionChecker.checkTile(this);
            gamePanel.collisionChecker.checkObject(this, false);
            gamePanel.collisionChecker.checkEntity(this, gamePanel.npc);
            gamePanel.collisionChecker.checkEntity(this, gamePanel.monster);
            boolean contactPlayer = gamePanel.collisionChecker.checkPlayer(this);

            if (this.entityType == 2 && contactPlayer)
                if (!gamePanel.player.invincible) {
                    gamePanel.playSoundEffect(6);

                    int damage = attack - gamePanel.player.defense;

                    if (damage < 0)
                        damage = 0;

                    gamePanel.player.currentLife -= damage;
                    gamePanel.player.invincible = true;
                }

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

            // INVINCIBLE
            if (invincible) {
                invincibleCounter++;

                if (invincibleCounter > 40) {
                    invincible = false;
                    invincibleCounter = 0;
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
                    if (spriteNum == 0) image = upIdle;
                    if (spriteNum == 1) image = up1;
                    if (spriteNum == 2) image = up2;
                }
                case "down" -> {
                    if (spriteNum == 0) image = downIdle;
                    if (spriteNum == 1) image = down1;
                    if (spriteNum == 2) image = down2;
                }
                case "left" -> {
                    if (spriteNum == 0) image = leftIdle;
                    if (spriteNum == 1) image = left1;
                    if (spriteNum == 2) image = left2;
                }
                case "right" -> {
                    if (spriteNum == 0) image = rightIdle;
                    if (spriteNum == 1) image = right1;
                    if (spriteNum == 2) image = right2;
                }
            }

            //  MONSTER HP BAR
            if (entityType == 2 && hpBarOn) {
                double oneScale = (double) gamePanel.tileSize / maxLife; // длина 1 ПЗ
                double hpBarValue = oneScale * currentLife; // длина текущего ПЗ

                graphics2D.setColor(new Color(35, 35, 35));
                graphics2D.fillRect(screenX - 1, screenY - 16, gamePanel.tileSize + 2, 12);

                graphics2D.setColor(new Color(255, 0, 30));
                graphics2D.fillRect(screenX, screenY - 15, (int) hpBarValue, 10);

                hpBarCounter++;

                if (hpBarCounter > 600) {
                    hpBarCounter = 0;
                    hpBarOn = false;
                }
            }

            if (invincible) {
                hpBarOn = true;
                hpBarCounter = 0;
                changeTransparency(graphics2D, 0.4f);
            }

            if (dying)
                dyingAnimation(graphics2D);

            graphics2D.drawImage(image, screenX, screenY,
                    gamePanel.tileSize, gamePanel.tileSize, null);

            changeTransparency(graphics2D, 1f);
        }
    }

    // анимация смерти
    public void dyingAnimation(Graphics2D graphics2D) {
        dyingCounter++;

        int time = 5;

        if (dyingCounter <= time) changeTransparency(graphics2D, 0f);
        else if (dyingCounter <= time * 2) changeTransparency(graphics2D, 1f);
        else if (dyingCounter <= time * 3) changeTransparency(graphics2D, 0f);
        else if (dyingCounter <= time * 4) changeTransparency(graphics2D, 1f);
        else if (dyingCounter <= time * 5) changeTransparency(graphics2D, 0f);
        else if (dyingCounter <= time * 6) changeTransparency(graphics2D, 1f);
        else if (dyingCounter <= time * 7) changeTransparency(graphics2D, 0f);
        else if (dyingCounter <= time * 8) changeTransparency(graphics2D, 1f);
        else { dying = false; alive = false; }
    }

    // изменение прозрачности
    public void changeTransparency(Graphics2D graphics2D, float value) {
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, value));
    }

    public  BufferedImage setup(String imagePath, int width, int height) {
        UtilityTool utilityTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().
                    getResourceAsStream(imagePath + ".png")));
            image = utilityTool.scaleImage(image, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }
}
