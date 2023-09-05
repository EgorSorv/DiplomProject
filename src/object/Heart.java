package object;

import entity.Entity;
import main.GamePanel;

public class Heart extends Entity {
    public Heart(GamePanel gamePanel) {
        super(gamePanel);

        name = "heart";

        image = setup("/objects/heart_full", gamePanel.tileSize, gamePanel.tileSize);
        image2 = setup("/objects/heart_half", gamePanel.tileSize, gamePanel.tileSize);
        image3 = setup("/objects/heart_blank", gamePanel.tileSize, gamePanel.tileSize);
    }
}
