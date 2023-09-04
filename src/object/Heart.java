package object;

import entity.Entity;
import main.GamePanel;

public class Heart extends Entity {
    public Heart(GamePanel gamePanel) {
        super(gamePanel);

        name = "heart";

        image = setup("/objects/heart_full");
        image2 = setup("/objects/heart_half");
        image3 = setup("/objects/heart_blank");
    }
}
