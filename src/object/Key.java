package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;


public class Key extends GameObject {
    GamePanel gamePanel;

    public Key(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        name = "key";

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().
                    getResourceAsStream("/objects/key.png")));
            utilityTool.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
