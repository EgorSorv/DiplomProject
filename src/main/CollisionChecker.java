package main;

import entity.Entity;

public class CollisionChecker {
    GamePanel gamePanel;

    public CollisionChecker(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void checkTile (Entity entity) {
        // границы сплошной зоны объекта
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        // соответствующие границе столбцы и строки
        int entityLeftCol = entityLeftWorldX / gamePanel.tileSize;
        int entityRightCol = entityRightWorldX / gamePanel.tileSize;
        int entityTopRow = entityTopWorldY / gamePanel.tileSize;
        int entityBottomRow = entityBottomWorldY / gamePanel.tileSize;

        int tileNum1, tileNum2; // плитки, в которые объект пытается пройти

        // проверка плитки, куда перемещается объект на то, является ли она сплошной
        switch (entity.direction) {
            case "up" -> {
                entityTopRow = (entityTopWorldY - entity.speed) / gamePanel.tileSize;
                tileNum1 = gamePanel.tileManager.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gamePanel.tileManager.mapTileNum[entityRightCol][entityTopRow];

                if (gamePanel.tileManager.tiles[tileNum1].collision ||
                        gamePanel.tileManager.tiles[tileNum2].collision)
                    entity.collisionOn = true;
            }
            case "down" -> {
                entityBottomRow = (entityBottomWorldY + entity.speed) / gamePanel.tileSize;
                tileNum1 = gamePanel.tileManager.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gamePanel.tileManager.mapTileNum[entityRightCol][entityBottomRow];

                if (gamePanel.tileManager.tiles[tileNum1].collision ||
                        gamePanel.tileManager.tiles[tileNum2].collision)
                    entity.collisionOn = true;
            }
            case "left" -> {
                entityLeftCol = (entityLeftWorldX - entity.speed) / gamePanel.tileSize;
                tileNum1 = gamePanel.tileManager.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gamePanel.tileManager.mapTileNum[entityLeftCol][entityBottomRow];

                if (gamePanel.tileManager.tiles[tileNum1].collision ||
                        gamePanel.tileManager.tiles[tileNum2].collision)
                    entity.collisionOn = true;
            }
            case "right" -> {
                entityRightCol = (entityRightWorldX + entity.speed) / gamePanel.tileSize;
                tileNum1 = gamePanel.tileManager.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gamePanel.tileManager.mapTileNum[entityRightCol][entityBottomRow];

                if (gamePanel.tileManager.tiles[tileNum1].collision ||
                        gamePanel.tileManager.tiles[tileNum2].collision)
                    entity.collisionOn = true;
            }
        }
    }

    // проверка объекта
    public int checkObject(Entity entity, boolean player) {
        int index = 999;

        for (int i = 0; i < gamePanel.obj.length; i++) {
            if (gamePanel.obj[i] != null) {
                // сплошная область сущности
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;
                // сплошная область объекта
                gamePanel.obj[i].solidArea.x = gamePanel.obj[i].worldX + gamePanel.obj[i].solidArea.x;
                gamePanel.obj[i].solidArea.y = gamePanel.obj[i].worldY + gamePanel.obj[i].solidArea.y;

                switch (entity.direction) {
                    case "up" -> {
                        entity.solidArea.y -= entity.speed;

                        if(entity.solidArea.intersects(gamePanel.obj[i].solidArea)) {
                            if (gamePanel.obj[i].collision)
                                entity.collisionOn = true;

                            if (player)
                                index = i;
                        }
                    }
                    case "down" -> {
                        entity.solidArea.y += entity.speed;

                        if (entity.solidArea.intersects(gamePanel.obj[i].solidArea)) {
                            if (gamePanel.obj[i].collision)
                                entity.collisionOn = true;

                            if (player)
                                index = i;
                        }
                    }
                    case "left" -> {
                        entity.solidArea.x -= entity.speed;

                        if (entity.solidArea.intersects(gamePanel.obj[i].solidArea)) {
                            if (gamePanel.obj[i].collision)
                                entity.collisionOn = true;

                            if (player)
                                index = i;
                        }
                    }
                        case "right" -> {
                            entity.solidArea.x += entity.speed;

                            if (entity.solidArea.intersects(gamePanel.obj[i].solidArea)) {
                                if (gamePanel.obj[i].collision)
                                    entity.collisionOn = true;

                                if (player)
                                    index = i;
                            }
                        }
                    }

                // сброс
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gamePanel.obj[i].solidArea.x = gamePanel.obj[i].solidAreaDefaultX;
                gamePanel.obj[i].solidArea.y = gamePanel.obj[i].solidAreaDefaultY;
            }
        }

        return index;
    }
}
