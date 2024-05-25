package main;

import entity.OldMan;
import interactive_tile.DryTree;
import monster.GreenSlime;
import object.*;

public class AssetSetter {
    GamePanel gamePanel;

    public AssetSetter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    // размещение предметов на карте
    public void setObject() {
        int index = 0;

        gamePanel.obj[index] = new BlueHeart(gamePanel);
        index++;

        gamePanel.obj[index] = new Coin(gamePanel);
        gamePanel.obj[index].worldX = gamePanel.tileSize * 25;
        gamePanel.obj[index].worldY = gamePanel.tileSize * 23;
        index++;

        gamePanel.obj[index] = new Coin(gamePanel);
        gamePanel.obj[index].worldX = gamePanel.tileSize * 21;
        gamePanel.obj[index].worldY = gamePanel.tileSize * 19;
        index++;

        gamePanel.obj[index] = new Coin(gamePanel);
        gamePanel.obj[index].worldX = gamePanel.tileSize * 26;
        gamePanel.obj[index].worldY = gamePanel.tileSize * 21;
        index++;

        gamePanel.obj[index] = new Axe(gamePanel);
        gamePanel.obj[index].worldX = gamePanel.tileSize * 33;
        gamePanel.obj[index].worldY = gamePanel.tileSize * 21;
        index++;

        gamePanel.obj[index] = new BlueShield(gamePanel);
        gamePanel.obj[index].worldX = gamePanel.tileSize * 35;
        gamePanel.obj[index].worldY = gamePanel.tileSize * 21;
        index++;

        gamePanel.obj[index] = new RedPotion(gamePanel);
        gamePanel.obj[index].worldX = gamePanel.tileSize * 22;
        gamePanel.obj[index].worldY = gamePanel.tileSize * 27;
    }

    // размещение нип на карте
    public void setNPC() {
        gamePanel.npc[0] = new OldMan(gamePanel);
        gamePanel.npc[0].worldX = gamePanel.tileSize * 21;
        gamePanel.npc[0].worldY = gamePanel.tileSize * 21;
    }

    // размещение монстров на карте
    public void setMonster() {
        int index = 0;

        gamePanel.monster[index] = new GreenSlime(gamePanel);
        gamePanel.monster[index].worldX = gamePanel.tileSize * 21;
        gamePanel.monster[index].worldY = gamePanel.tileSize * 38;
        index++;

        gamePanel.monster[index] = new GreenSlime(gamePanel);
        gamePanel.monster[index].worldX = gamePanel.tileSize * 23;
        gamePanel.monster[index].worldY = gamePanel.tileSize * 42;
        index++;

        gamePanel.monster[index] = new GreenSlime(gamePanel);
        gamePanel.monster[index].worldX = gamePanel.tileSize * 24;
        gamePanel.monster[index].worldY = gamePanel.tileSize * 37;
        index++;

        gamePanel.monster[index] = new GreenSlime(gamePanel);
        gamePanel.monster[index].worldX = gamePanel.tileSize * 34;
        gamePanel.monster[index].worldY = gamePanel.tileSize * 42;
        index++;

        gamePanel.monster[index] = new GreenSlime(gamePanel);
        gamePanel.monster[index].worldX = gamePanel.tileSize * 38;
        gamePanel.monster[index].worldY = gamePanel.tileSize * 42;
    }

    public void setInteractiveTile() {
        int index = 0;

        gamePanel.iTiles[index] = new DryTree(gamePanel, 28, 37);
        index++;

        gamePanel.iTiles[index] = new DryTree(gamePanel, 27, 12);
        index++;

        gamePanel.iTiles[index] = new DryTree(gamePanel, 28, 12);
        index++;

        gamePanel.iTiles[index] = new DryTree(gamePanel, 29, 12);
        index++;

        gamePanel.iTiles[index] = new DryTree(gamePanel, 30, 12);
        index++;

        gamePanel.iTiles[index] = new DryTree(gamePanel, 31, 12);
        index++;

        gamePanel.iTiles[index] = new DryTree(gamePanel, 32, 12);
        index++;

        gamePanel.iTiles[index] = new DryTree(gamePanel, 33, 12);
    }
}
