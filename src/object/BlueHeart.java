package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class BlueHeart extends GameObject {
    GamePanel gamePanel;

    public BlueHeart(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        name = "blueHeart";

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().
                    getResourceAsStream("/objects/blueheart.png")));
            utilityTool.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
