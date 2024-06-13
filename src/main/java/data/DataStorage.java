package data;

import java.io.Serializable;
import java.util.ArrayList;

public class DataStorage implements Serializable {
    // PLAYER STATS
    int level;
    int maxLife;
    int currentLife;
    int maxMana;
    int currentMana;
    int strength;
    int dexterity;
    int exp;
    int nextLevelExp;
    int coins;

    // PLAYER INVENTORY
    ArrayList<String> inventory = new ArrayList<>();
    int currentWeaponSlot;
    int currentShieldSlot;

    // OBJECTS ON MAP
    String[][] mapObjects;
    int[][] mapObjectWorldX;
    int[][] mapObjectWorldY;
}
