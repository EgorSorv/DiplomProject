package main;

import entity.Player;
import object.GameObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    // SCREEN SETTINGS
    final int originalTileSize = 16; // начальный размер плитки для отображения объектов 16x16
    public final int scale = 3;

    public final int tileSize = originalTileSize * scale; // итоговый размер плитки 48x48
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // ширина 768 пикселей
    public final int screenHeight = tileSize * maxScreenRow; // высота 576 пикселей

    // WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    // FPS
    double FPS = 60;

    // SYSTEM
    TileManager tileManager = new TileManager(this);
    KeyHandler keyHandler = new KeyHandler(this);
    Sound music = new Sound();
    Sound soundEffect = new Sound();
    public UserInterface userInterface = new UserInterface(this);
    Thread gameThread; // создание потока
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public AssetSetter assetSetter = new AssetSetter(this);

    // ENTITY AND OBJECT
    public Player player = new Player(this, keyHandler);
    public GameObject[] obj = new GameObject[10]; // массив для хранения предметов

    // GAME STATE
    public int gameState;
    public final int playState = 1;
    public final int pauseState = 2;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight)); // размеры окна
        this.setBackground(Color.white); // цвет фона
        this.setDoubleBuffered(true); // улучшенный рендеринг
        this.addKeyListener(keyHandler); // распознование работы с клавиатурой
        this.setFocusable(true); // установка фокуса на получение данных с клавиатуры
    }

    public void setupGame() {
        assetSetter.setObject();

        playMusic(0);

        gameState = playState;
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
        // обновление состояния игры
        if (gameState == playState) {
            player.update();
            music.play();
            music.loop();
        }
        if (gameState == pauseState)
            music.stop();
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics); // отрисовка изображения

        Graphics2D graphics2D = (Graphics2D) graphics; // класс-наследник с расширенным функционалом

        // DEBUG (BEGIN)
        long drawStart = 0;

        if (keyHandler.checkDrawTime)
            drawStart = System.nanoTime();

        // TILE
        tileManager.draw(graphics2D);

        // OBJECT
        for (GameObject gameObject : obj)
            if (gameObject != null)
                gameObject.draw(graphics2D, this);

        // PLAYER
        player.draw(graphics2D);

        // UI
        userInterface.draw(graphics2D);

        //  DEBUG (END)
        if (keyHandler.checkDrawTime) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;

            graphics2D.setColor(Color.green);
            graphics2D.drawString("Draw Time: " + passed, 10, 400);
        }

        graphics2D.dispose(); // удаляет метод для освобождения памяти
    }

    // воспроизведение основной музыки
    public void playMusic(int index) {
        music.setFile(index);
        music.play();
        music.loop();
    }

    // остановка музыки
    public void stopMusic() {
        music.stop();
    }

    // воспроизведение звуковых эффектов
    public void playSoundEffect(int index) {
        soundEffect.setFile(index);
        soundEffect.play();
    }
}
