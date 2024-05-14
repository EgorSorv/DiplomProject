package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    GamePanel gamePanel;
    public boolean upPressed, downPressed, leftPressed, rightPressed, interactPressed, shotKeyPressed;

    // DEBUG (DRAW_TIME)
    boolean showDebugText  = false;

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
            titleState(code);
        }

        // PLAY STATE
        else if (gamePanel.gameState == gamePanel.playState) {
            playState(code);
        }

        // PAUSE STATE
        else if (gamePanel.gameState == gamePanel.pauseState)
            pauseState(code);

        // DIALOGUE STATE
        else if (gamePanel.gameState == gamePanel.dialogueState)
            dialogueState(code);

        // CHARACTER STATE
        else if (gamePanel.gameState == gamePanel.characterState)
            characterState(code);
    }

    public void titleState(int code) {
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

    public void playState(int code) {
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

        // CHARACTER INFO
        if (code == KeyEvent.VK_I)
            gamePanel.gameState = gamePanel.characterState;

        // INTERACTION
        if (code == KeyEvent.VK_E)
            interactPressed = true;

        // MAGIC USE
        if (code == KeyEvent.VK_F)
            shotKeyPressed = true;

        // DEBUG
        if (code == KeyEvent.VK_F1)
            showDebugText = !showDebugText;

        // MAP REFRESH
        if (code == KeyEvent.VK_F2)
            gamePanel.tileManager.loadMap("/maps/world.txt");
    }

    public void pauseState(int code) {
        if (code == KeyEvent.VK_P) {
            gamePanel.gameState = gamePanel.playState;

            gamePanel.music.play();
            gamePanel.music.loop();
        }
    }

    public void dialogueState(int code) {
        if (code == KeyEvent.VK_E)
            gamePanel.gameState = gamePanel.playState;
    }

    public void characterState(int code) {
        if (code == KeyEvent.VK_I)
            gamePanel.gameState = gamePanel.playState;

        if (code == KeyEvent.VK_W)
            if (gamePanel.userInterface.slotRow != 0) {
                gamePanel.userInterface.slotRow--;
                gamePanel.playSoundEffect(9);
            }
        if (code == KeyEvent.VK_A)
            if (gamePanel.userInterface.slotCol != 0) {
                gamePanel.userInterface.slotCol--;
                gamePanel.playSoundEffect(9);
            }
        if (code == KeyEvent.VK_S)
            if (gamePanel.userInterface.slotRow != 3) {
                gamePanel.userInterface.slotRow++;
                gamePanel.playSoundEffect(9);
            }
        if (code == KeyEvent.VK_D)
            if (gamePanel.userInterface.slotCol != 4) {
                gamePanel.userInterface.slotCol++;
                gamePanel.playSoundEffect(9);
            }
        if (code == KeyEvent.VK_E)
            gamePanel.player.selectItem();
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

        if (code == KeyEvent.VK_F)
            shotKeyPressed = false;
    }
}
