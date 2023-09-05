package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    GamePanel gamePanel;
    public boolean upPressed, downPressed, leftPressed, rightPressed, interactPressed;

    // DEBUG (DRAW_TIME)
    boolean checkDrawTime = false;

    public KeyHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode(); // возращает код клавиши

        // TITLE STATE
        if (gamePanel.gameState == gamePanel.titleState) {
            if (code == KeyEvent.VK_W) {
                gamePanel.userInterface.commandNumber--;

                if (gamePanel.userInterface.commandNumber < 0)
                    gamePanel.userInterface.commandNumber = 2;
            }

            if (code == KeyEvent.VK_S) {
                gamePanel.userInterface.commandNumber++;

                if (gamePanel.userInterface.commandNumber > 2)
                    gamePanel.userInterface.commandNumber = 0;
            }

            if (code == KeyEvent.VK_E) {
                if (gamePanel.userInterface.commandNumber == 0) {
                    gamePanel.gameState = gamePanel.playState;
                    gamePanel.playMusic(0);
                }

                if (gamePanel.userInterface.commandNumber == 2)
                    System.exit(0);
            }
        }

        // PLAY STATE
        else if (gamePanel.gameState == gamePanel.playState) {
            // MOVEMENT
            if (code == KeyEvent.VK_W)
                upPressed = true;
            if (code == KeyEvent.VK_A)
                leftPressed = true;
            if (code == KeyEvent.VK_S)
                downPressed = true;
            if (code == KeyEvent.VK_D)
                rightPressed = true;

            // PAUSE
            if (code == KeyEvent.VK_P)
                gamePanel.gameState = gamePanel.pauseState;

            // INTERACTION
            if (code == KeyEvent.VK_E)
                interactPressed = true;

            // DEBUG (KEY)
            if (code == KeyEvent.VK_F1)
                checkDrawTime = !checkDrawTime;
        }

        // PAUSE STATE
        else if (gamePanel.gameState == gamePanel.pauseState)
            if (code == KeyEvent.VK_P) {
                gamePanel.gameState = gamePanel.playState;

                gamePanel.music.play();
                gamePanel.music.loop();
            }

        // DIALOGUE STATE
        if (gamePanel.gameState == gamePanel.dialogueState)
            if (code == KeyEvent.VK_E)
                gamePanel.gameState = gamePanel.playState;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W)
            upPressed = false;

        if (code == KeyEvent.VK_A)
            leftPressed = false;

        if (code == KeyEvent.VK_S)
            downPressed = false;

        if (code == KeyEvent.VK_D)
            rightPressed = false;
    }
}
