package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Door extends GameObject {
    GamePanel gamePanel;

    public Door(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        name = "door";

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().
                    getResourceAsStream("/objects/door.png")));
            utilityTool.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }

        collision = true;
    }
}
