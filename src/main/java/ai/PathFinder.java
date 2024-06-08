package ai;

import main.GamePanel;

import java.util.ArrayList;

public class PathFinder {
    GamePanel gamePanel;
    Node[][] node; // двумерный массив узлов
    ArrayList<Node> openList = new ArrayList<>(); // список подходщих узлов для дальнейшей проверки
    public ArrayList<Node> pathList = new ArrayList<>(); // список узлов с наилучшим марщрутом до цели
    Node startNode, goalNode, currentNode; // стартовый, конечный и текущий узлы
    boolean goalReached = false; // проверка на достижение конечной цели
    int step = 0; // количество шагов для поиска оптимального маршрута

    public PathFinder(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        instantiateNodes();
    }

    // создание экземпляров узлов
    public void instantiateNodes() {
        node = new Node[gamePanel.maxWorldCol][gamePanel.maxWorldRow];

        int col = 0;
        int row = 0;

        while (col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow) {
            node[col][row] = new Node(col, row);

            col++;

            if (col == gamePanel.maxWorldCol) {
                col = 0;
                row++;
            }
        }
    }

    // сброс всех настроек узлов
    public void resetNodes() {
        int col = 0;
        int row = 0;

        while (col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow) {
            node[col][row].open = false;
            node[col][row].checked = false;
            node[col][row].solid = false;

            col++;

            if (col == gamePanel.maxWorldCol) {
                col = 0;
                row++;
            }
        }

        openList.clear();
        pathList.clear();
        goalReached = false;
        step = 0;
    }

    // установка значений узлов
    public void setNodes(int startCol, int startRow, int goalCol, int goalRow) {
        resetNodes();

        startNode = node[startCol][startRow];
        currentNode = startNode;
        goalNode = node[goalCol][goalRow];
        openList.add(currentNode);

        int col = 0;
        int row = 0;

        while (col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow) {
            // SOLID NODES
            // TILES
            int tileNum = gamePanel.tileManager.mapTileNum[gamePanel.currentMap][col][row];

            if (gamePanel.tileManager.tiles[tileNum].collision)
                node[col][row].solid = true;

            // INTERACTIVE TILES
            for (int i = 0; i < gamePanel.iTiles[1].length; i++) {
                if (gamePanel.iTiles[gamePanel.currentMap][i] != null &&
                gamePanel.iTiles[gamePanel.currentMap][i].destructible) {
                    int itCol = gamePanel.iTiles[gamePanel.currentMap][i].worldX / gamePanel.tileSize;
                    int itRow = gamePanel.iTiles[gamePanel.currentMap][i].worldY / gamePanel.tileSize;

                    node[itCol][itRow].solid = true;
                }
            }

            // NODES COST
            getCost(node[col][row]);

            col++;

            if (col == gamePanel.maxWorldCol) {
                col = 0;
                row++;
            }
        }
    }

    // определение растояний между узлами
    public void getCost(Node node) {
        int xDistance;
        int yDistance;

        // G COST
        xDistance = Math.abs(node.col - startNode.col);
        yDistance = Math.abs(node.row - startNode.row);
        node.gCost = xDistance + yDistance;

        // H COST
        xDistance = Math.abs(node.col - goalNode.col);
        yDistance = Math.abs(node.row - goalNode.row);
        node.hCost = xDistance + yDistance;

        // F COST
        node.fCost = node.gCost + node.hCost;
    }

    // поиск оптимального маршрута
    public boolean search() {
        while (!goalReached && step < 500) {
            int col = currentNode.col;
            int row = currentNode.row;

            // проверка текущего узла
            currentNode.checked = true;
            openList.remove(currentNode);

            // переход к узлам сверху, слева, справа и снизу от текущего
            if (row - 1 >= 0)
                openNode(node[col][row - 1]);

            if (col - 1 >= 0)
                openNode(node[col - 1][row]);

            if (row + 1 < gamePanel.maxWorldRow)
                openNode(node[col][row + 1]);

            if (col + 1 < gamePanel.maxWorldCol)
                openNode(node[col + 1][row]);

            // поиск наилучшего узла
            int bestNodeIndex = 0;
            int bestNodeFCost = 999;

            for (int i = 0; i < openList.size(); i++) {
                // F COST CHECK
                if (openList.get(i).fCost < bestNodeFCost) {
                    bestNodeIndex = i;
                    bestNodeFCost = openList.get(i).fCost;
                }
                // G COST CHECK
                else if (openList.get(i).fCost == bestNodeFCost)
                    if (openList.get(i).gCost < openList.get(bestNodeIndex).gCost)
                        bestNodeIndex = i;
            }

            // прервать цикл, если список подходящих узлов пуст
            if (openList.isEmpty())
                break;

            currentNode = openList.get(bestNodeIndex);

            if (currentNode == goalNode) {
                goalReached = true;
                trackThePath();
            }

            step++;
        }

        return goalReached;
    }

    // добавление узлов в список подходящих для выявления наилучшего маршрута
    public void openNode(Node node) {
        if (!node.open && !node.checked && !node.solid) {
            node.open = true;
            node.parent = currentNode;
            openList.add(node);
        }
    }

    // запись пути
    public void trackThePath() {
        Node current = goalNode;

        while (current != startNode) {
            pathList.addFirst(current);
            current = current.parent;
        }
    }
}
