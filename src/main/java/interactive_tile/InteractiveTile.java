package interactive_tile;

import entity.Entity;
import main.GamePanel;

public class InteractiveTile extends Entity {
    GamePanel gamePanel;
    public boolean destructible = false; // разрушаемость объекта

    public InteractiveTile(GamePanel gamePanel, int col, int row) {
        super(gamePanel);
        this.gamePanel = gamePanel;
    }

    // проверка на наличие нужного предмета
    public boolean isCorrectItem(Entity entity) {
        return false;
    }

    public void playSoundEffect() {}

    public InteractiveTile getDestroyedForm() {
        return null;
    }

    public void update() {
        if (invincible)
            invincibleCounter++;

        if (invincibleCounter > 20) {
            invincible = false;
            invincibleCounter = 0;
        }
    }
}
