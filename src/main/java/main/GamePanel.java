package main;

import ai.PathFinder;
import data.SaveLoad;
import entity.Entity;
import entity.Player;
import interactive_tile.InteractiveTile;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable {

    // SCREEN SETTINGS
    final int originalTileSize = 16; // начальный размер плитки для отображения объектов 16x16
    public final int scale = 3;

    public final int tileSize = originalTileSize * scale; // итоговый размер плитки 48x48
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // ширина 960 пикселей
    public final int screenHeight = tileSize * maxScreenRow; // высота 576 пикселей

    // WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int maxMap = 10; // максимальное количество карт
    public int currentMap = 0; // текущая карта

    // FOR FULL SCREEN
    int fullScreenWidth = screenWidth;
    int fullScreenHeight = screenHeight;
    BufferedImage windowScreen;
    Graphics2D graphics2D;
    public boolean fullScreenOn; // проверка на работающий полноэкранный режим

    // FPS
    double FPS = 60;

    // SYSTEM
    public TileManager tileManager = new TileManager(this);
    public KeyHandler keyHandler = new KeyHandler(this);
    Sound music = new Sound();
    Sound soundEffect = new Sound();
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public AssetSetter assetSetter = new AssetSetter(this);
    public UserInterface userInterface = new UserInterface(this);
    public EventHandler eventHandler = new EventHandler(this);
    Config config = new Config(this); // конфигурация игры
    public PathFinder pathFinder = new PathFinder(this);
    SaveLoad saveLoad = new SaveLoad(this);
    Thread gameThread; // создание потока

    // ENTITY AND OBJECT
    public Player player = new Player(this, keyHandler);
    public Entity[][] obj = new Entity[maxMap][20]; // массив для хранения предметов
    public Entity[][] npc = new Entity[maxMap][10]; // массив для хранения нип
    public Entity[][] monsters = new Entity[maxMap][20]; // массив для хранения монстров
    public InteractiveTile[][] iTiles = new InteractiveTile[maxMap][50]; // массив с объектами для взаимодействия
    public Entity[][] projectiles = new Entity[maxMap][20]; // список всех снарядов
    public ArrayList<Entity> particles = new ArrayList<>(); // список всех частиц
    ArrayList<Entity> entities = new ArrayList<>(); // список всех сущностей

    // GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;
    public final int settingsState = 5;
    public final int gameOverState = 6;
    public final int transitionState = 7;
    public final int tradeState = 8;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight)); // размеры окна
        this.setBackground(Color.black); // цвет фона
        this.setDoubleBuffered(true); // улучшенный рендеринг
        this.addKeyListener(keyHandler); // распознование работы с клавиатурой
        this.setFocusable(true); // установка фокуса на получение данных с клавиатуры
    }

    public void setupGame() {
        assetSetter.setObject();
        assetSetter.setNPC();
        assetSetter.setMonster();
        assetSetter.setInteractiveTile();

        gameState = titleState;

        setWindowScreen();

        switchScreenSize();
    }

    // повторный запуск игры с контрольной точки
    public void retry() {
        player.invincible = false;
        player.setDefaultPosition();
        player.restoreCharacterStatus();

        assetSetter.setNPC();
        assetSetter.setMonster();
    }

    // перезапуск игры
    public void restart() {
        player.setDefaultPosition();
        player.setDefaultValues();
        player.setItems();

        assetSetter.setObject();
        assetSetter.setNPC();
        assetSetter.setMonster();
        assetSetter.setInteractiveTile();

        currentMap = 0;
    }

    // вывод игры на весь экран
    public void setFullScreen() {
        // GET LOCAL SCREEN DEVICE
        GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();
        graphicsDevice.setFullScreenWindow(Main.window);

        // GET FULL SCREEN WIDTH AND HEIGHT
        fullScreenWidth = Main.window.getWidth();
        fullScreenHeight = Main.window.getHeight();
    }

    // вывод игры в окне
    public void setWindowScreen() {
        windowScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        graphics2D = (Graphics2D) windowScreen.getGraphics();

        fullScreenWidth = screenWidth;
        fullScreenHeight = screenHeight;
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
                drawToTempScreen();
                drawToScreen();
                delta--; // сброс счетчика
            }
        }
    }

    public void update() {
        // обновление состояния игры
        if (gameState == playState) {
            // PLAYER
            player.update();

            // NPC
            for (int i = 0; i < npc[1].length; i++)
                if (npc[currentMap][i] != null)
                    npc[currentMap][i].update();

            // MONSTER
            for (int i = 0; i < monsters[1].length; i++)
                if (monsters[currentMap][i] != null) {
                    if (monsters[currentMap][i].alive && !monsters[currentMap][i].dying)
                        monsters[currentMap][i].update();
                    else if (!monsters[currentMap][i].alive) {
                        monsters[currentMap][i].checkDrop();
                        monsters[currentMap][i] = null;
                    }
                }

            // PROJECTILE
            for (int i = 0; i < projectiles[1].length; i++)
                if (projectiles[currentMap][i] != null) {
                    if (projectiles[currentMap][i].alive) projectiles[currentMap][i].update();
                    else projectiles[currentMap][i] = null;
                }

            // PARTICLE
            for (int i = 0; i < particles.size(); i++)
                if (particles.get(i) != null) {
                    if (particles.get(i).alive) particles.get(i).update();
                    else particles.remove(i);
                }

            // INTERACTIVE TILE
            for (int i = 0; i < iTiles[1].length; i++)
                if (iTiles[currentMap][i] != null)
                    iTiles[currentMap][i].update();
        }

        if (gameState == pauseState)
            stopMusic();
    }

    //  изменение размера экрана
    public void switchScreenSize() {
        if (fullScreenOn) {
            Main.window.dispose();
            Main.window.setUndecorated(true); // убрать рамку окна
            setFullScreen();
            Main.window.setVisible(true);
        } else {
            Main.window.dispose();
            Main.window.setUndecorated(false);
            setWindowScreen();
            Main.window.setVisible(true);
        }
    }

    // отрисовка временного экрана
    public void drawToTempScreen() {
        // DEBUG (BEGIN)
        long drawStart = 0;

        if (keyHandler.showDebugText)
            drawStart = System.nanoTime();

        // TITLE SCREEN
        if (gameState == titleState) {
            userInterface.draw(graphics2D);
        } else {
            // TILE
            tileManager.draw(graphics2D);

            // INTERACTIVE TILE
            for (int i = 0; i < iTiles[1].length; i++)
                if (iTiles[currentMap][i] != null)
                    iTiles[currentMap][i].draw(graphics2D);

            // ADD ENTITIES
            entities.add(player);

            for (int i = 0; i < npc[1].length; i++)
                if (npc[currentMap][i] != null)
                    entities.add(npc[currentMap][i]);

            for (int i = 0; i < monsters[1].length; i++)
                if (monsters[currentMap][i] != null)
                    entities.add(monsters[currentMap][i]);

            for (int i = 0; i < obj[1].length; i++)
                if (obj[currentMap][i] != null)
                    entities.add(obj[currentMap][i]);

            for (int i = 0; i < projectiles[1].length; i++)
                if (projectiles[currentMap][i] != null)
                    entities.add(projectiles[currentMap][i]);

            for (Entity particle : particles)
                if (particle != null)
                    entities.add(particle);

            // SORT ENTITIES
            entities.sort(Comparator.comparingInt(e -> e.worldY));

            // DRAW ENTITIES
            for (Entity entity : entities) entity.draw(graphics2D);

            // RESET ENTITIES
            entities.clear();

            // UI
            userInterface.draw(graphics2D);
        }

        //  DEBUG (END)
        if (keyHandler.showDebugText) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;

            graphics2D.setFont(new Font("Arial", Font.PLAIN, 20));
            graphics2D.setColor(Color.green);

            int x = 10;
            int y = 400;
            int lineHeight = 20;

            graphics2D.drawString("WorldX: " + player.worldX, x, y);
            y += lineHeight;
            graphics2D.drawString("WorldY: " + player.worldY, x, y);
            y += lineHeight;
            graphics2D.drawString("Col: " + (player.worldX + player.solidArea.x) / tileSize, x, y);
            y += lineHeight;
            graphics2D.drawString("Row: " + (player.worldY + player.solidArea.y) / tileSize, x, y);
            y += lineHeight;
            graphics2D.drawString("Draw Time: " + passed, x, y);
        }
    }

    // отрисовка основного экрана
    public void drawToScreen() {
        Graphics graphics = getGraphics();
        graphics.drawImage(windowScreen, 0, 0, fullScreenWidth, fullScreenHeight, null);
        graphics.dispose();
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
