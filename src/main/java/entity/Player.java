package entity;

import main.GamePanel;
import main.KeyHandler;
import object.Fireball;
import object.Key;
import object.WoodShield;
import object.Sword;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends Entity {
    KeyHandler keyHandler;
    public final int screenX, screenY; // позиция игрока на экране
    public boolean attackCanceled = false;
    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxInventorySize = 20;

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        super(gamePanel);
        this.keyHandler = keyHandler;

        screenX = gamePanel.screenWidth / 2 - gamePanel.tileSize / 2; // перенос персонажа в центр экрана
        screenY = gamePanel.screenHeight / 2 - gamePanel.tileSize / 2;

        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
        setItems();
    }

    public void setDefaultValues() {
        worldX = gamePanel.tileSize * 23;
        worldY = gamePanel.tileSize * 21;
        speed = 4;
        direction = "down";

        // PLAYER STATUS
        level = 1;
        exp = 0;
        nextLevelExp = 5;
        maxLife = 6;
        currentLife = maxLife;
        maxMana = 4;
        currentMana = maxMana;
        strength = 1;
        dexterity = 1;
        coins = 0;

        currentWeapon = new Sword(gamePanel);
        currentShield = new WoodShield(gamePanel);
        currentProjectile = new Fireball(gamePanel);

        attack = getAttack();
        defense = getDefense();
    }

    // вещи в инвентаре по умолчанию
    public void setItems() {
        inventory.add(currentWeapon);
        inventory.add(currentShield);
        inventory.add(new Key(gamePanel));
    }

    public int getAttack() {
        attackArea = currentWeapon.attackArea;
        return strength * currentWeapon.attackValue;
    }

    public int getDefense() {
        return dexterity * currentShield.defenseValue;
    }

    // получение изображения
    public void getPlayerImage() {
        upIdle = setup("/player/Player_up_idle", gamePanel.tileSize, gamePanel.tileSize);
        up1 = setup("/player/Player_up_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("/player/Player_up_2", gamePanel.tileSize, gamePanel.tileSize);
        downIdle = setup("/player/Player_down_idle", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("/player/Player_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("/player/Player_down_2", gamePanel.tileSize, gamePanel.tileSize);
        leftIdle = setup("/player/Player_left_idle", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("/player/Player_left_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("/player/Player_left_2", gamePanel.tileSize, gamePanel.tileSize);
        rightIdle = setup("/player/Player_right_idle", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("/player/Player_right_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("/player/Player_right_2", gamePanel.tileSize, gamePanel.tileSize);
    }

    public void getPlayerAttackImage() {
        if (currentWeapon.type == typeSword) {
            attackUp1 = setup("/player/player_attack_up_1",
                    gamePanel.tileSize, gamePanel.tileSize * 2);
            attackUp2 = setup("/player/player_attack_up_2",
                    gamePanel.tileSize, gamePanel.tileSize * 2);
            attackDown1 = setup("/player/player_attack_down_1",
                    gamePanel.tileSize, gamePanel.tileSize * 2);
            attackDown2 = setup("/player/player_attack_down_2",
                    gamePanel.tileSize, gamePanel.tileSize * 2);
            attackLeft1 = setup("/player/player_attack_left_1",
                    gamePanel.tileSize * 2, gamePanel.tileSize);
            attackLeft2 = setup("/player/player_attack_left_2",
                    gamePanel.tileSize * 2, gamePanel.tileSize);
            attackRight1 = setup("/player/player_attack_right_1",
                    gamePanel.tileSize * 2, gamePanel.tileSize);
            attackRight2 = setup("/player/player_attack_right_2",
                    gamePanel.tileSize * 2, gamePanel.tileSize);
        }

        if (currentWeapon.type == typeAxe) {
            attackUp1 = setup("/player/player_axe_up_1",
                    gamePanel.tileSize, gamePanel.tileSize * 2);
            attackUp2 = setup("/player/player_axe_up_2",
                    gamePanel.tileSize, gamePanel.tileSize * 2);
            attackDown1 = setup("/player/player_axe_down_1",
                    gamePanel.tileSize, gamePanel.tileSize * 2);
            attackDown2 = setup("/player/player_axe_down_2",
                    gamePanel.tileSize, gamePanel.tileSize * 2);
            attackLeft1 = setup("/player/player_axe_left_1",
                    gamePanel.tileSize * 2, gamePanel.tileSize);
            attackLeft2 = setup("/player/player_axe_left_2",
                    gamePanel.tileSize * 2, gamePanel.tileSize);
            attackRight1 = setup("/player/player_axe_right_1",
                    gamePanel.tileSize * 2, gamePanel.tileSize);
            attackRight2 = setup("/player/player_axe_right_2",
                    gamePanel.tileSize * 2, gamePanel.tileSize);
        }
    }

    // обновление позиции игрока
    public void update() {
        if (attacking) {
            attacking();

        } else if (!collisionOn) {
            if (keyHandler.upPressed || keyHandler.downPressed ||
                    keyHandler.leftPressed || keyHandler.rightPressed ||
                    keyHandler.interactPressed) {
                if (keyHandler.upPressed)
                    direction = "up";
                else if (keyHandler.downPressed)
                    direction = "down";
                else if (keyHandler.leftPressed)
                    direction = "left";
                else if (keyHandler.rightPressed)
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

                // PLAYER MOVEMENT
                if (!collisionOn && !keyHandler.interactPressed) {
                    switch (direction) {
                        case "up" -> worldY -= speed;
                        case "down" -> worldY += speed;
                        case "left" -> worldX -= speed;
                        case "right" -> worldX += speed;
                    }
                }

                if (keyHandler.interactPressed && !attackCanceled) {
                    gamePanel.playSoundEffect(7);
                    attacking = true;
                    spriteCounter = 0;
                }

                attackCanceled = false;
                gamePanel.keyHandler.interactPressed = false;

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
            } else spriteNum = 0;

        } else {
            collisionOn = false;
            spriteNum = 0;
        }

        if (gamePanel.keyHandler.shotKeyPressed && !currentProjectile.alive
                && useProjectileCounter == 30 && currentProjectile.haveResource(this)) {
            // SET DEFAULT COORDINATES, DIRECTION AND USER ENTITY
            currentProjectile.set(worldX, worldY, direction, true, this);

            // SUBTRACT THE COST
            currentProjectile.subtractResource(this);

            gamePanel.projectiles.add(currentProjectile);

            useProjectileCounter = 0;

            gamePanel.playSoundEffect(10);
        }

        // INVINCIBLE
        if (invincible) {
            invincibleCounter++;

            if (invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }

        // PROJECTILES COOLDOWN
        if (useProjectileCounter < 30)
            useProjectileCounter++;
    }

    // игрок атакует
    public void attacking() {
        spriteCounter++;

        if (spriteCounter <= 5) spriteNum = 1;
        else if (spriteCounter <= 25) {
            spriteNum = 2;

            // текущие значения позиции игрока
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            // смещение зоны удара
            switch (direction) {
                case "up" -> worldY -= attackArea.height;
                case "down" -> worldY += attackArea.height;
                case "left" -> worldX -= attackArea.width;
                case "right" -> worldX += attackArea.width;
            }

            // зона удара становится сплошной зоной
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            // проверка попал ли монстр в зону удара
            int monsterIndex = gamePanel.collisionChecker.checkEntity(this, gamePanel.monster);
            damageMonster(monsterIndex, attack);

            // восстановление значений позиции игрока
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        } else {
            spriteNum = 1;
            spriteCounter = 0;
             attacking = false;
        }
    }

    // подобрать объект
    public void pickUpObject(int index) {
        if (index != -1) {
            if (gamePanel.obj[index].type == typeNoneInventory) {
                // NONE INVENTORY ITEMS
                gamePanel.obj[index].use(this);
            } else {
                // INVENTORY ITEMS
                String text;

                if (inventory.size() != maxInventorySize) {
                    inventory.add(gamePanel.obj[index]);
                    gamePanel.playSoundEffect(1);
                    text = "Got a " + gamePanel.obj[index].name + "!";
                } else text = "You can't carry any more!";

                gamePanel.userInterface.addMessage(text);
            }

            gamePanel.obj[index] = null;
        }
    }

    // взаимодействие с нип
    public void interactNPC(int index) {
        if (gamePanel.keyHandler.interactPressed) {
            if (index != -1) {
                attackCanceled = true;
                gamePanel.gameState = gamePanel.dialogueState;
                gamePanel.npc[index].speak();
            }
        }
    }

    // контакт с монстром
    public void contactMonster(int index) {
        if (index != -1)
            if (!invincible && !gamePanel.monster[index].dying) {
                gamePanel.playSoundEffect(6);

                int damage = gamePanel.monster[index].attack - defense;

                if (damage < 0)
                    damage = 0;

                currentLife -= damage;
                invincible = true;
            }
    }

    // нанесение урона монстрам
    public void damageMonster(int index, int attackPower) {
        if (index != -1)
            if (!gamePanel.monster[index].invincible) {
                gamePanel.playSoundEffect(5);

                int damage = attackPower - gamePanel.monster[index].defense;

                if (damage < 0)
                    damage = 0;

                gamePanel.monster[index].currentLife -= damage;
                gamePanel.userInterface.addMessage(damage + " damage!");

                gamePanel.monster[index].invincible = true;
                gamePanel.monster[index].damageReaction();

                if (gamePanel.monster[index].currentLife <= 0) {
                    gamePanel.monster[index].dying = true;

                    gamePanel.userInterface.addMessage("killed the " + gamePanel.monster[index].name + "!");
                    gamePanel.userInterface.addMessage(gamePanel.monster[index].exp + " exp");

                    exp += gamePanel.monster[index].exp;
                    checkLevelUp();
                }
            }
    }

    // повышение уровня
    public void checkLevelUp() {
        if (exp >= nextLevelExp) {
            level++;
            nextLevelExp *= 2;
            maxLife += 2;
            currentLife = maxLife;
            strength++;
            dexterity++;
            attack = getAttack();
            defense = getDefense();

            gamePanel.playSoundEffect(8);
            gamePanel.gameState = gamePanel.dialogueState;
            gamePanel.userInterface.currentDialogue = "You are level " + level + " now!\nYou fell stronger!";
        }
    }

    // использование предметов
    public void selectItem() {
        int itemIndex = gamePanel.userInterface.getItemIndexOnSlot();

        if (itemIndex < inventory.size()) {
            Entity selectedItem = inventory.get(itemIndex);

            if (selectedItem.type == typeSword || selectedItem.type == typeAxe) {
                currentWeapon = selectedItem;
                attack = getAttack();
                getPlayerAttackImage();
            }

            if (selectedItem.type == typeShield) {
                currentShield = selectedItem;
                defense = getDefense();
            }

            if (selectedItem.type == typeConsumable) {
                selectedItem.use(this);
                inventory.remove(itemIndex);
            }
        }
    }

    // отрисовка изображений
    public void draw(Graphics2D graphics2D) {
        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        switch (direction) {
            case "up" -> {
                if (attacking) {
                    tempScreenY = screenY - gamePanel.tileSize;

                    if (spriteNum == 1) image = attackUp1;
                    if (spriteNum == 2) image = attackUp2;
                } else {
                    if (spriteNum == 0) image = upIdle;
                    if (spriteNum == 1) image = up1;
                    if (spriteNum == 2) image = up2;
                }
            }
            case "down" -> {
                if (attacking) {
                    if (spriteNum == 1) image = attackDown1;
                    if (spriteNum == 2) image = attackDown2;
                } else {
                    if (spriteNum == 0) image = downIdle;
                    if (spriteNum == 1) image = down1;
                    if (spriteNum == 2) image = down2;
                }
            }
            case "left" -> {
                if (attacking) {
                    tempScreenX = screenX - gamePanel.tileSize;

                    if (spriteNum == 1) image = attackLeft1;
                    if (spriteNum == 2) image = attackLeft2;
                } else {
                    if (spriteNum == 0) image = leftIdle;
                    if (spriteNum == 1) image = left1;
                    if (spriteNum == 2) image = left2;
                }
            }
            case "right" -> {
                if (attacking) {
                    if (spriteNum == 1) image = attackRight1;
                    if (spriteNum == 2) image = attackRight2;
                } else {
                    if (spriteNum == 0) image = rightIdle;
                    if (spriteNum == 1) image = right1;
                    if (spriteNum == 2) image = right2;
                }
            }
        }

        // игрок становится прозрачным
        if (invincible) changeTransparency(graphics2D, 0.4f);

        graphics2D.drawImage(image, tempScreenX, tempScreenY, null);

        // сброс прозрачности
        changeTransparency(graphics2D, 1f);
    }
}