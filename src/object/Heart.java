package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Heart extends GameObject {
    GamePanel gamePanel;

    public Heart(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        name = "heart";

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().
                    getResourceAsStream("/objects/heart_full.png")));
            image2 = ImageIO.read(Objects.requireNonNull(getClass().
                    getResourceAsStream("/objects/heart_half.png")));
            image3 = ImageIO.read(Objects.requireNonNull(getClass().
                    getResourceAsStream("/objects/heart_blank.png")));

            image = utilityTool.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize);
            image2 = utilityTool.scaleImage(image2, gamePanel.tileSize, gamePanel.tileSize);
            image3 = utilityTool.scaleImage(image3, gamePanel.tileSize, gamePanel.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
