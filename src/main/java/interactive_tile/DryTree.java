package interactive_tile;

import entity.Entity;
import main.GamePanel;

public class DryTree extends InteractiveTile {
    GamePanel gamePanel;

    public DryTree(GamePanel gamePanel, int col, int row) {
        super(gamePanel, col, row);
        this.gamePanel = gamePanel;
        this.worldX = gamePanel.tileSize * col;
        this.worldY = gamePanel.tileSize * row;

        destructible = true;
        currentLife = 3;

        downIdle = setup("/interactive_tiles/drytree", gamePanel.tileSize, gamePanel.tileSize);
    }

    public boolean isCorrectItem(Entity entity) {
        return entity.currentWeapon.type == typeAxe;
    }

    public void playSoundEffect() {
        gamePanel.playSoundEffect(11);
    }

    public InteractiveTile getDestroyedForm() {
        return new Trunk(gamePanel, worldX / gamePanel.tileSize, worldY / gamePanel.tileSize);
    }
}
