package data;

import entity.Entity;
import main.GamePanel;
import object.*;

import javax.sql.DataSource;
import java.io.*;
import java.util.Objects;

public class SaveLoad {
    GamePanel gamePanel;

    public SaveLoad(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void save() {
        try {
            ObjectOutputStream save = new ObjectOutputStream(new FileOutputStream("save.dat"));
            DataStorage data = new DataStorage();

            // PLAYER STATS
            data.level = gamePanel.player.level;
            data.maxLife = gamePanel.player.maxLife;
            data.currentLife = gamePanel.player.currentLife;
            data.maxMana = gamePanel.player.maxMana;
            data.currentMana = gamePanel.player.currentMana;
            data.strength = gamePanel.player.strength;
            data.dexterity = gamePanel.player.dexterity;
            data.exp = gamePanel.player.exp;
            data.nextLevelExp = gamePanel.player.nextLevelExp;
            data.coins = gamePanel.player.coins;

            // PLAYER INVENTORY
            for (int i = 0; i < gamePanel.player.inventory.size(); i++) {
                data.inventory.add(gamePanel.player.inventory.get(i).name);
            }

            // PLAYER INVENTORY
            gamePanel.player.getEquipmentSlots();
            data.currentWeaponSlot = gamePanel.player.currentWeaponSlot;
            data.currentShieldSlot = gamePanel.player.currentShieldSlot;

            // OBJECTS ON MAP
            data.mapObjects = new String[gamePanel.maxMap][gamePanel.obj[1].length];
            data.mapObjectWorldX = new int[gamePanel.maxMap][gamePanel.obj[1].length];
            data.mapObjectWorldY = new int[gamePanel.maxMap][gamePanel.obj[1].length];

            for (int mapNum = 0; mapNum < gamePanel.maxMap; mapNum++) {
                for (int i = 0; i < gamePanel.obj[1].length; i++) {
                    if (gamePanel.obj[mapNum][i] == null)
                        data.mapObjects[mapNum][i] = "NA";
                    else {
                        data.mapObjects[mapNum][i] = gamePanel.obj[mapNum][i].name;
                        data.mapObjectWorldX[mapNum][i] = gamePanel.obj[mapNum][i].worldX;
                        data.mapObjectWorldY[mapNum][i] = gamePanel.obj[mapNum][i].worldY;
                    }
                }
            }

            save.writeObject(data);
        } catch(Exception e) {
            System.out.println("Save error!");
        }
    }

    public void load() {
        try {
            ObjectInputStream load = new ObjectInputStream(new FileInputStream("save.dat"));
            DataStorage data = (DataStorage) load.readObject();

            // PLAYER STATS
            gamePanel.player.level = data.level;
            gamePanel.player.maxLife = data.maxLife;
            gamePanel.player.currentLife = data.currentLife;
            gamePanel.player.maxMana = data.maxMana;
            gamePanel.player.currentMana = data.currentMana;
            gamePanel.player.strength = data.strength;
            gamePanel.player.dexterity = data.dexterity;
            gamePanel.player.exp = data.exp;
            gamePanel.player.nextLevelExp = data.nextLevelExp;
            gamePanel.player.coins = data.coins;

            // PLAYER INVENTORY
            gamePanel.player.inventory.clear();

            for (int i = 0; i < data.inventory.size(); i++)
                gamePanel.player.inventory.add(getObject(data.inventory.get(i)));

            // PLAYER EQUIPMENT
            gamePanel.player.currentWeapon = gamePanel.player.inventory.get(data.currentWeaponSlot);
            gamePanel.player.currentShield = gamePanel.player.inventory.get(data.currentShieldSlot);
            gamePanel.player.getAttack();
            gamePanel.player.getDefense();
            gamePanel.player.getPlayerAttackImage();

            // OBJECTS ON MAP
            for (int mapNum = 0; mapNum < gamePanel.maxMap; mapNum++){
                for (int i = 0; i < gamePanel.obj[1].length; i++) {
                    if (Objects.equals(data.mapObjects[mapNum][i], "NA"))
                        gamePanel.obj[mapNum][i] = null;
                    else {
                        gamePanel.obj[mapNum][i] = getObject(data.mapObjects[mapNum][i]);
                        gamePanel.obj[mapNum][i].worldX = data.mapObjectWorldX[mapNum][i];
                        gamePanel.obj[mapNum][i].worldY = data.mapObjectWorldY[mapNum][i];
                    }
                }
            }
        } catch(Exception e) {
            System.out.println("Load error!");
        }
    }

    public Entity getObject(String name) {
        Entity obj = null;

        switch (name) {
            case "Woodcutter's axe" -> obj = new Axe(gamePanel);
            case "Blue shield" -> obj = new BlueShield(gamePanel);
            case "Red potion" -> obj = new RedPotion(gamePanel);
            case "Old sword" -> obj = new Sword(gamePanel);
            case "Wood shield" -> obj = new WoodShield(gamePanel);
        }

        return obj;
    }
}
