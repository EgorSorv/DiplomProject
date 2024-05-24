package interactive_tile;

import entity.Entity;
import main.GamePanel;

import java.awt.*;

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

    public Color getParticleColor() {
        return new Color(65, 50, 30);
    }

    public int getParticleSize() {
        return 6;
    }

    public int getParticleSpeed() {
        return 1;
    }

    public int getParticleMaxLife() {
        return 20;
    }
}
