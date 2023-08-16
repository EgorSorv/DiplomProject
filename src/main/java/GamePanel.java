import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    // SCREEN SETTINGS
    final int originalTileSize = 16; // начальный размер плитки для отображения объектов 16x16
    final int scale = 3;

    final int tileSize = originalTileSize * scale; // итоговый размер плитки 48x48
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol; // ширина 768 пикселей
    final int screenHeight = tileSize * maxScreenRow; // высота 576 пикселей

    Thread gameThread; // создание потока

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight)); // размеры окна
        this.setBackground(Color.black); // цвет фона
        this.setDoubleBuffered(true); // улучшенный рендеринг
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start(); // запуск потока
    }

    @Override
    public void run() {
        while (gameThread != null) {
            repaint(); // встроенный метод вызова paintComponent
        }
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics); // отрисовка изображения

        Graphics2D graphics2D = (Graphics2D) graphics; // класс-наследник с расширенным функционалом

        graphics2D.setColor(Color.white);
        graphics2D.fillRect(100, 100, tileSize, tileSize); // рисует прямоугольник
        graphics2D.dispose(); // удаляет метод для освобождения памяти
    }
}
