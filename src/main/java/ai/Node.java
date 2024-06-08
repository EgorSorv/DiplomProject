package ai;

public class Node {
    Node parent; // родительский узел
    public int col; // номер столбца
    public int row; // номер строки
    int gCost; // растояние от точки старта до текущей точки
    int hCost; // растояние от точки назначения до текущей точки
    int fCost; // сумма двух растояний
    boolean solid; // проверка узла на наличие припятствия
    boolean open; // узлы, подходящие для дальнейшей проверки
    boolean checked; // проверенные узлы

    public Node(int col, int row) {
        this.col = col;
        this.row = row;
    }
}
