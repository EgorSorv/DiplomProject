package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
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
                    getClass().getResourceAsStream("/tiles/grass.png")));

            tiles[1] = new Tile();
            tiles[1].image = ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/tiles/wall.png")));

            tiles[2] = new Tile();
            tiles[2].image = ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/tiles/water.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D graphics2D) {
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        // автозаполнение экрана
        while (col < gamePanel.maxScreenCol && row < gamePanel.maxScreenRow) {
            graphics2D.drawImage(tiles[0].image, x, y, gamePanel.tileSize, gamePanel.tileSize, null);

            col++;
            x += gamePanel.tileSize;

            if (col == gamePanel.maxScreenCol) {
                col = 0;
                x = 0;
                row++;
                y += gamePanel.tileSize;
            }
        }
    }
}
