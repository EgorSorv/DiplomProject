package object;

import entity.Entity;
import main.GamePanel;

public class ManaCrystal extends Entity {
    GamePanel gamePanel;

    public ManaCrystal(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        name = "Mana Crystal";
        image = setup("/objects/manacrystal", gamePanel.tileSize, gamePanel.tileSize);
        image2 = setup("/objects/manacrystal_blank", gamePanel.tileSize, gamePanel.tileSize);
    }


}
