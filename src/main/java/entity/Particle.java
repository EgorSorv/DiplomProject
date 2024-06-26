package entity;

import main.GamePanel;

import java.awt.*;

public class Particle extends Entity {
    Entity generator;
    Color color;
    int size;
    int xd; // направление частиц по осе x
    int yd; // направление частиц по осе y

    public Particle(GamePanel gamePanel, Entity generator, Color color,
                    int size, int speed, int maxLife, int xd, int yd) {
        super(gamePanel);

        this.generator = generator;
        this.color = color;
        this.size = size;
        this.speed = speed;
        this.maxLife = maxLife;
        this.xd = xd;
        this.yd = yd;

        currentLife = maxLife;

        int offset = gamePanel.tileSize / 2 - size / 2;
        worldX = generator.worldX + offset;
        worldY = generator.worldY + offset;
    }

    public void update() {
        currentLife--;

        if (currentLife < maxLife / 3) yd++;

        worldX += xd * speed;
        worldY += yd * speed;

        if (currentLife == 0) alive = false;
    }

    public void draw(Graphics2D graphics2D) {
        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

        graphics2D.setColor(color);
        graphics2D.fillRect(screenX, screenY, size, size);
    }
}
