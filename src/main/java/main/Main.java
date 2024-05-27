package main;

import javax.swing.*;
import java.awt.*;

public class Main {
   public static JFrame window, fullWindow;
    public static void main(String[] args) {
        window = new JFrame(); // создание окна
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // закрытие окна по нажатию на кнопку "X"
        window.setResizable(false); // фиксирование размера окна
        window.setTitle("Game"); // название окна

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel); // добавление параметров экрана

        window.pack(); // установка оптимального размера для окна

        window.setLocationRelativeTo(null); // отображение окна по центру
        window.setVisible(true); // установка отображения окна

        gamePanel.setupGame();
        gamePanel.startGameThread();
    }
}
