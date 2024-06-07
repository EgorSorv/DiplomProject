package entity;

import main.GamePanel;
import object.*;

public class Merchant extends Entity {
    public Merchant (GamePanel gamePanel) {
        super(gamePanel);

        direction = "down";
        speed = 0;

        getImage();
        setDialogue();
        setItems();
    }

    public void getImage() {
        upIdle = setup("/npc/merchant_idle", gamePanel.tileSize, gamePanel.tileSize);
        up1 = setup("/npc/merchant_move", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("/npc/merchant_move", gamePanel.tileSize, gamePanel.tileSize);
        downIdle = setup("/npc/merchant_idle", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("/npc/merchant_move", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("/npc/merchant_move", gamePanel.tileSize, gamePanel.tileSize);
        leftIdle = setup("/npc/merchant_idle", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("/npc/merchant_move", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("/npc/merchant_move", gamePanel.tileSize, gamePanel.tileSize);
        rightIdle = setup("/npc/merchant_idle", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("/npc/merchant_move", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("/npc/merchant_move", gamePanel.tileSize, gamePanel.tileSize);
    }

    public void setDialogue() {
        dialogues[0] = "Welcome to my shop!";
    }

    // предметы у продавца
    public void setItems() {
        inventory.add(new RedPotion(gamePanel));
        inventory.add(new Key(gamePanel));
        inventory.add(new Sword(gamePanel));
        inventory.add(new Axe(gamePanel));
        inventory.add(new WoodShield(gamePanel));
        inventory.add(new BlueShield(gamePanel));
    }

    public void speak() {
        super.speak();

        gamePanel.gameState = gamePanel.tradeState;
        gamePanel.userInterface.npc = this;
    }
}
