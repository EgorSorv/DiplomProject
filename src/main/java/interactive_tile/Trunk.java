package interactive_tile;

import main.GamePanel;

public class Trunk extends InteractiveTile {
    GamePanel gamePanel;

    public Trunk(GamePanel gamePanel, int col, int row) {
        super(gamePanel, col, row);
        this.gamePanel = gamePanel;
        this.worldX = gamePanel.tileSize * col;
        this.worldY = gamePanel.tileSize * row;

        downIdle = setup("/interactive_tiles/trunk", gamePanel.tileSize, gamePanel.tileSize);

        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = 0;
        solidArea.height = 0;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}
