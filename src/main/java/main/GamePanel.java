package main;

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

    double FPS = 60;

    KeyHandler keyHandler = new KeyHandler();
    Thread gameThread; // создание потока

    // начальная позиция игрока
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight)); // размеры окна
        this.setBackground(Color.black); // цвет фона
        this.setDoubleBuffered(true); // улучшенный рендеринг
        this.addKeyListener(keyHandler); // распознование работы с клавиатурой
        this.setFocusable(true); // установка фокуса на получение данных с клавиатуры
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start(); // запуск потока
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS; // частота обновления
        double delta = 0;
        long lastTime = System.nanoTime(); // начало интервала
        long currentTime; // конец интервала

        while (gameThread != null) {
            currentTime = System.nanoTime(); // обновление конца интервала

            delta += (currentTime - lastTime) / drawInterval; // накапливание интервалов до требуемого времени

            lastTime = currentTime; // обновление начала интервала

            if (delta >= 1) {
                update(); // обновление данных игры
                repaint(); // встроенный метод вызова paintComponent
                delta--; // сброс счетчика
            }
        }
    }

    public void update() {
        // обновление позиции игрока
        if (keyHandler.upPressed)
            playerY -= playerSpeed;
        else if (keyHandler.downPressed)
            playerY += playerSpeed;
        else if (keyHandler.leftPressed)
            playerX -= playerSpeed;
        else if (keyHandler.rightPressed)
            playerX += playerSpeed;
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics); // отрисовка изображения

        Graphics2D graphics2D = (Graphics2D) graphics; // класс-наследник с расширенным функционалом

        graphics2D.setColor(Color.white);
        graphics2D.fillRect(playerX, playerY, tileSize, tileSize); // рисует прямоугольник (игрок)
        graphics2D.dispose(); // удаляет метод для освобождения памяти
    }
}
