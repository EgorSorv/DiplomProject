package main;

import entity.Merchant;
import entity.OldMan;
import interactive_tile.DryTree;
import monster.BlackSlime;
import monster.GreenSlime;
import monster.RedSlime;
import object.*;

public class AssetSetter {
    GamePanel gamePanel;

    public AssetSetter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    // размещение предметов на карте
    public void setObject() {
        int mapNum = 0;
        int index = 0;

        gamePanel.obj[mapNum][index] = new BlueHeart(gamePanel);
        index++;

        gamePanel.obj[mapNum][index] = new Axe(gamePanel);
        gamePanel.obj[mapNum][index].worldX = gamePanel.tileSize * 37;
        gamePanel.obj[mapNum][index].worldY = gamePanel.tileSize * 41;
        index++;

        gamePanel.obj[mapNum][index] = new BlueShield(gamePanel);
        gamePanel.obj[mapNum][index].worldX = gamePanel.tileSize * 13;
        gamePanel.obj[mapNum][index].worldY = gamePanel.tileSize * 9;
    }

    // размещение нип на карте
    public void setNPC() {
        int mapNum = 0;
        int i = 0;

        gamePanel.npc[mapNum][i] = new OldMan(gamePanel);
        gamePanel.npc[mapNum][i].worldX = gamePanel.tileSize * 21;
        gamePanel.npc[mapNum][i].worldY = gamePanel.tileSize * 21;

        mapNum = 1;
        i = 0;

        gamePanel.npc[mapNum][i] = new Merchant(gamePanel);
        gamePanel.npc[mapNum][i].worldX = gamePanel.tileSize * 12;
        gamePanel.npc[mapNum][i].worldY = gamePanel.tileSize * 7;
    }

    // размещение монстров на карте
    public void setMonster() {
        int mapNum = 0;
        int index = 0;

        gamePanel.monsters[mapNum][index] = new GreenSlime(gamePanel);
        gamePanel.monsters[mapNum][index].worldX = gamePanel.tileSize * 21;
        gamePanel.monsters[mapNum][index].worldY = gamePanel.tileSize * 38;
        index++;

        gamePanel.monsters[mapNum][index] = new GreenSlime(gamePanel);
        gamePanel.monsters[mapNum][index].worldX = gamePanel.tileSize * 23;
        gamePanel.monsters[mapNum][index].worldY = gamePanel.tileSize * 42;
        index++;

        gamePanel.monsters[mapNum][index] = new GreenSlime(gamePanel);
        gamePanel.monsters[mapNum][index].worldX = gamePanel.tileSize * 24;
        gamePanel.monsters[mapNum][index].worldY = gamePanel.tileSize * 37;
        index++;

        gamePanel.monsters[mapNum][index] = new BlackSlime(gamePanel);
        gamePanel.monsters[mapNum][index].worldX = gamePanel.tileSize * 34;
        gamePanel.monsters[mapNum][index].worldY = gamePanel.tileSize * 42;
        index++;

        gamePanel.monsters[mapNum][index] = new BlackSlime(gamePanel);
        gamePanel.monsters[mapNum][index].worldX = gamePanel.tileSize * 38;
        gamePanel.monsters[mapNum][index].worldY = gamePanel.tileSize * 42;

        index++;

        gamePanel.monsters[mapNum][index] = new RedSlime(gamePanel);
        gamePanel.monsters[mapNum][index].worldX = gamePanel.tileSize * 34;
        gamePanel.monsters[mapNum][index].worldY = gamePanel.tileSize * 7;

        index++;

        gamePanel.monsters[mapNum][index] = new RedSlime(gamePanel);
        gamePanel.monsters[mapNum][index].worldX = gamePanel.tileSize * 40;
        gamePanel.monsters[mapNum][index].worldY = gamePanel.tileSize * 7;

        index++;

        gamePanel.monsters[mapNum][index] = new RedSlime(gamePanel);
        gamePanel.monsters[mapNum][index].worldX = gamePanel.tileSize * 34;
        gamePanel.monsters[mapNum][index].worldY = gamePanel.tileSize * 11;

        index++;

        gamePanel.monsters[mapNum][index] = new RedSlime(gamePanel);
        gamePanel.monsters[mapNum][index].worldX = gamePanel.tileSize * 40;
        gamePanel.monsters[mapNum][index].worldY = gamePanel.tileSize * 11;
    }

    public void setInteractiveTile() {
        int mapNum = 0;
        int index = 0;

        gamePanel.iTiles[mapNum][index] = new DryTree(gamePanel, 28, 37);
        index++;

        gamePanel.iTiles[mapNum][index] = new DryTree(gamePanel, 27, 12);
        index++;

        gamePanel.iTiles[mapNum][index] = new DryTree(gamePanel, 28, 12);
        index++;

        gamePanel.iTiles[mapNum][index] = new DryTree(gamePanel, 29, 12);
        index++;

        gamePanel.iTiles[mapNum][index] = new DryTree(gamePanel, 30, 12);
        index++;

        gamePanel.iTiles[mapNum][index] = new DryTree(gamePanel, 31, 12);
        index++;

        gamePanel.iTiles[mapNum][index] = new DryTree(gamePanel, 32, 12);
        index++;

        gamePanel.iTiles[mapNum][index] = new DryTree(gamePanel, 33, 12);
        index++;

        gamePanel.iTiles[mapNum][index] = new DryTree(gamePanel, 18, 40);
        index++;

        gamePanel.iTiles[mapNum][index] = new DryTree(gamePanel, 17, 40);
        index++;

        gamePanel.iTiles[mapNum][index] = new DryTree(gamePanel, 16, 40);
        index++;

        gamePanel.iTiles[mapNum][index] = new DryTree(gamePanel, 15, 40);
        index++;

        gamePanel.iTiles[mapNum][index] = new DryTree(gamePanel, 14, 40);
        index++;

        gamePanel.iTiles[mapNum][index] = new DryTree(gamePanel, 13, 40);
        index++;

        gamePanel.iTiles[mapNum][index] = new DryTree(gamePanel, 13, 41);
        index++;

        gamePanel.iTiles[mapNum][index] = new DryTree(gamePanel, 12, 41);
        index++;

        gamePanel.iTiles[mapNum][index] = new DryTree(gamePanel, 11, 41);
        index++;

        gamePanel.iTiles[mapNum][index] = new DryTree(gamePanel, 10, 41);
        index++;

        gamePanel.iTiles[mapNum][index] = new DryTree(gamePanel, 10, 40);
        index++;

        gamePanel.iTiles[mapNum][index] = new DryTree(gamePanel, 38, 13);
    }
}
