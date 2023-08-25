package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Chest extends GameObject {
    GamePanel gamePanel;

    public Chest(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        name = "chest";

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().
                    getResourceAsStream("/objects/chest.png")));
            utilityTool.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
