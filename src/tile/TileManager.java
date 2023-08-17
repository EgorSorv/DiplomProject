package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class TileManager {
    GamePanel gamePanel;
    Tile[] tiles; // массив для хранения плиток

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        tiles = new Tile[10];

        getTileImage();
    }

    public void getTileImage() {
        try {
            tiles[0] = new Tile();
            tiles[0].image = ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/tile/grass.png")));

            tiles[1] = new Tile();
            tiles[1].image = ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/tile/wall.png")));

            tiles[2] = new Tile();
            tiles[2].image = ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/tile/water.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
