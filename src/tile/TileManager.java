package tile;

import main.GamePanel;

public class TileManager {
    GamePanel gamePanel;
    Tile[] tiles; // массив для хранения плиток

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        tiles = new Tile[10];

        getTileImage();
    }

    public void getTileImage() {

    }
}
