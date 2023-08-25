package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Boots extends GameObject {
    GamePanel gamePanel;

    public Boots(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        name = "boots";

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().
                    getResourceAsStream("/objects/boots.png")));
            utilityTool.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
