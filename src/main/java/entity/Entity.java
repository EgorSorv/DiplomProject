package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
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
    public boolean pathFollow = false;
    public boolean monsterAgro = false;
    public boolean knockBack = false;

    // COUNTERS
    public int spriteCounter = 0; // интервал обновления изображения
    public int actionLockCounter = 0; // продолжительность поведения
    public int invincibleCounter = 0; // действие неуязвимости
    public int useProjectileCounter = 0; // перезарядка снаряда перед использованием
    int hpBarCounter = 0;
    int dyingCounter = 0;
    int knockBackCounter = 0;

    // CHARACTER ATTRIBUTES
    public String name;
    public int level;
    public int exp;
    public int nextLevelExp;
    public int maxLife; // максимальное здоровье
    public int currentLife; // текущее здоровье
    public int maxMana; // максимальная мана
    public int currentMana; // текущая мана
    public int strength;
    public int dexterity;
    public int attack;
    public int defense;
    public int ammo; // количество снарядов
    public int defaultSpeed;
    public int speed; // скорость перемещения
    public int coins;
    public Entity currentWeapon;
    public Entity currentShield;
    public Projectile currentProjectile;

    // ITEM ATTRIBUTES
    public ArrayList<Entity> inventory = new ArrayList<>(); // инвентарь
    public final int maxInventorySize = 20; // максимальный размер инвенторя
    public int attackValue; // значение атаки
    public int defenseValue; // значение защиты
    public int healValue; // значение лечения
    public int manaRestoreValue; // значение восстановления маны
    public int costValue; // значение стоимости
    public String description = "";
    public int manaCost; // стоимость действия в мане
    public int price; // стоимость предмета
    public int knockBackPower = 0; // сила отбрасывания

    // TYPE
    public int type;
    public final int typePlayer = 0;
    public final int typeNpc = 1;
    public final int typeMonster = 2;
    public final int typeSword = 3;
    public final int typeAxe = 4;
    public final int typeShield = 5;
    public final int typeConsumable = 6; // расходные предметы
    public final int typeNoneInventory = 7; // предметы, не попадают в инвентарь

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

    public void use(Entity entity) {}

    // выбор предмета для выпадения
    public void checkDrop() {}

    // выпадение предмета
    public void dropItem(Entity droppedItem) {
        for (int index = 0; index < gamePanel.obj[1].length; index++)
            if (gamePanel.obj[gamePanel.currentMap][index] == null) {
                gamePanel.obj[gamePanel.currentMap][index] = droppedItem;
                // предмет выпадает из объекта на тех же координатах
                gamePanel.obj[gamePanel.currentMap][index].worldX = worldX;
                gamePanel.obj[gamePanel.currentMap][index].worldY = worldY;
                break;
            }
    }

    // цвет частиц
    public Color getParticleColor() {
        return null;
    }

    // размер частиц
    public int getParticleSize() {
        return 0; // к-во пикселей
    }

    // скорость частиц
    public int getParticleSpeed() {
        return 0;
    }

    // продолжительность появления частиц
    public int getParticleMaxLife() {
        return 0;
    }

    // генерация частиц
    public void generateParticle(Entity generator, Entity target) {
        Color color = generator.getParticleColor();
        int size = generator.getParticleSize();
        int speed = generator.getParticleSpeed();
        int maxLife = generator.getParticleMaxLife();

        Particle topLeftParticle = new Particle(
                gamePanel, target, color, size, speed, maxLife, -2, -1);
        Particle topRightParticle = new Particle(
                gamePanel, target, color, size, speed, maxLife, 2, -1);
        Particle bottomLeftParticle = new Particle(
                gamePanel, target, color, size, speed, maxLife, -2, 1);
        Particle bottomRightParticle = new Particle(
                gamePanel, target, color, size, speed, maxLife, 2, 1);

        gamePanel.particles.add(topLeftParticle);
        gamePanel.particles.add(topRightParticle);
        gamePanel.particles.add(bottomLeftParticle);
        gamePanel.particles.add(bottomRightParticle);
    }

    public void update() {
        if (knockBack) {
            checkCollision();

            if (collisionOn || knockBackCounter == 10) {
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultSpeed;
            } else {
                switch (gamePanel.player.direction) {
                    case "up" -> worldY -= speed;
                    case "down" -> worldY += speed;
                    case "left" -> worldX -= speed;
                    case "right" -> worldX += speed;
                }
            }

            knockBackCounter++;
        } else {
            setAction();
            checkCollision();

            if (!collisionOn) {
                switch (direction) {
                    case "up" -> worldY -= speed;
                    case "down" -> worldY += speed;
                    case "left" -> worldX -= speed;
                    case "right" -> worldX += speed;
                }
            }
        }

        spriteCounter++;

        if (speed == 0) {
            if (spriteCounter > 24) {
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
        } else {
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
            } else {
                collisionOn = false;
                spriteNum = 0;
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

        // PROJECTILES COOLDOWN
        if (useProjectileCounter < 30)
            useProjectileCounter++;
    }

    // проверка на столкновение объектов
    public void checkCollision() {
        collisionOn = false;
        gamePanel.collisionChecker.checkTile(this);
        gamePanel.collisionChecker.checkObject(this, false);
        gamePanel.collisionChecker.checkEntity(this, gamePanel.npc);
        gamePanel.collisionChecker.checkEntity(this, gamePanel.monsters);
        gamePanel.collisionChecker.checkEntity(this, gamePanel.iTiles);
        boolean contactPlayer = gamePanel.collisionChecker.checkPlayer(this);

        if (this.type == typeMonster && contactPlayer)
            damagePlayer(attack);
    }

    // урон по игроку
    public void damagePlayer(int attackPower) {
        if (!gamePanel.player.invincible) {
            gamePanel.playSoundEffect(6);

            int damage = attackPower - gamePanel.player.defense;

            if (damage < 0) damage = 0;

            gamePanel.player.currentLife -= damage;
            gamePanel.player.invincible = true;
        }
    }

    public void draw(Graphics2D graphics2D) {
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
            if (type == typeMonster && hpBarOn) {
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

            graphics2D.drawImage(image, screenX, screenY, null);

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
        else alive = false;
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

    // поиск пути до точки на карте
    public void searchPath(int goalCol, int goalRow) {
        int startCol = (worldX + solidArea.x) / gamePanel.tileSize;
        int startRow = (worldY + solidArea.y) / gamePanel.tileSize;

        gamePanel.pathFinder.setNodes(startCol, startRow, goalCol, goalRow);

        if (gamePanel.pathFinder.search()) {
            int nextX = gamePanel.pathFinder.pathList.getFirst().col * gamePanel.tileSize;
            int nextY = gamePanel.pathFinder.pathList.getFirst().row * gamePanel.tileSize;

            // SOLID AREA
            int solidLeftX = worldX + solidArea.x;
            int solidRightX = worldX + solidArea.x + solidArea.width;
            int solidTopY = worldY + solidArea.y;
            int solidBottomY = worldY + solidArea.y + solidArea.height;

            if (solidTopY > nextY && solidLeftX >= nextX && solidRightX < nextX + gamePanel.tileSize)
                direction = "up";
            
            else if (solidTopY < nextY && solidLeftX >= nextX && solidRightX < nextX + gamePanel.tileSize)
                direction = "down";

            else if (solidTopY >= nextY && solidBottomY < nextY + gamePanel.tileSize) {
                if (solidLeftX > nextX)
                    direction = "left";

                if (solidLeftX < nextX)
                    direction = "right";
            }

            else if (solidTopY > nextY && solidLeftX > nextX) {
                direction = "up";
                checkCollision();

                if (collisionOn)
                    direction = "left";
            }

            else if (solidTopY > nextY && solidLeftX < nextX) {
                direction = "up";
                checkCollision();

                if (collisionOn)
                    direction = "right";
            }

            else if (solidTopY < nextY && solidLeftX > nextX) {
                direction = "down";
                checkCollision();

                if (collisionOn)
                    direction = "left";
            }

            else if (solidTopY < nextY && solidLeftX < nextX) {
                direction = "down";
                checkCollision();

                if (collisionOn)
                    direction = "right";
            }

            int nextCol = gamePanel.pathFinder.pathList.getFirst().col;
            int nextRow = gamePanel.pathFinder.pathList.getFirst().row;

            if (nextCol == goalCol && nextRow == goalRow)
                pathFollow = false;
        }
    }

    // поиск пути до игрока на карте
    public void chasePlayer(int goalCol, int goalRow) {
        int startCol = (worldX + solidArea.x) / gamePanel.tileSize;
        int startRow = (worldY + solidArea.y) / gamePanel.tileSize;

        gamePanel.pathFinder.setNodes(startCol, startRow, goalCol, goalRow);

        if (gamePanel.pathFinder.search()) {
            int nextX = gamePanel.pathFinder.pathList.getFirst().col * gamePanel.tileSize;
            int nextY = gamePanel.pathFinder.pathList.getFirst().row * gamePanel.tileSize;

            // SOLID AREA
            int solidLeftX = worldX + solidArea.x;
            int solidRightX = worldX + solidArea.x + solidArea.width;
            int solidTopY = worldY + solidArea.y;
            int solidBottomY = worldY + solidArea.y + solidArea.height;

            if (solidTopY > nextY && solidLeftX >= nextX && solidRightX < nextX + gamePanel.tileSize)
                direction = "up";

            else if (solidTopY < nextY && solidLeftX >= nextX && solidRightX < nextX + gamePanel.tileSize)
                direction = "down";

            else if (solidTopY >= nextY && solidBottomY < nextY + gamePanel.tileSize) {
                if (solidLeftX > nextX)
                    direction = "left";

                if (solidLeftX < nextX)
                    direction = "right";
            }

            else if (solidTopY > nextY && solidLeftX > nextX) {
                direction = "up";
                checkCollision();

                if (collisionOn)
                    direction = "left";
            }

            else if (solidTopY > nextY && solidLeftX < nextX) {
                direction = "up";
                checkCollision();

                if (collisionOn)
                    direction = "right";
            }

            else if (solidTopY < nextY && solidLeftX > nextX) {
                direction = "down";
                checkCollision();

                if (collisionOn)
                    direction = "left";
            }

            else if (solidTopY < nextY && solidLeftX < nextX) {
                direction = "down";
                checkCollision();

                if (collisionOn)
                    direction = "right";
            }
        }
    }
}
