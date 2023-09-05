package main;

import entity.OldMan;
import monster.GreenSlime;
import object.BlueHeart;

public class AssetSetter {
    GamePanel gamePanel;

    public AssetSetter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    // размещение предметов на карте
    public void setObject() {
        gamePanel.obj[0] = new BlueHeart(gamePanel);
    }

    // размещение нип на карте
    public void setNPC() {
        gamePanel.npc[0] = new OldMan(gamePanel);
        gamePanel.npc[0].worldX = gamePanel.tileSize * 21;
        gamePanel.npc[0].worldY = gamePanel.tileSize * 21;
    }

    // размещение монстров на карте
    public void setMonster() {
        gamePanel.monster[0] = new GreenSlime(gamePanel);
        gamePanel.monster[0].worldX = gamePanel.tileSize * 23;
        gamePanel.monster[0].worldY = gamePanel.tileSize * 36;

        gamePanel.monster[1] = new GreenSlime(gamePanel);
        gamePanel.monster[1].worldX = gamePanel.tileSize * 23;
        gamePanel.monster[1].worldY = gamePanel.tileSize * 37;
    }
}
