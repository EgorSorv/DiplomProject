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

        // SETTINGS STATE
        else if (gamePanel.gameState == gamePanel.settingsState)
            settingsState(code);

        // GAME OVER STATE
        else if (gamePanel.gameState == gamePanel.gameOverState)
            gameOverState(code);

        // TRADE STATE
        else if (gamePanel.gameState == gamePanel.tradeState)
            tradeState(code);
    }

    public void titleState(int code) {
        if (code == KeyEvent.VK_W) {
            gamePanel.userInterface.commandNumber--;
            gamePanel.playSoundEffect(9);

            if (gamePanel.userInterface.commandNumber < 0)
                gamePanel.userInterface.commandNumber = 2;
        }

        if (code == KeyEvent.VK_S) {
            gamePanel.userInterface.commandNumber++;
            gamePanel.playSoundEffect(9);

            if (gamePanel.userInterface.commandNumber > 2)
                gamePanel.userInterface.commandNumber = 0;
        }

        if (code == KeyEvent.VK_E) {
            gamePanel.playSoundEffect(9);

            if (gamePanel.userInterface.commandNumber == 0) {
                gamePanel.gameState = gamePanel.playState;
                gamePanel.playMusic(0);
            }

            if (gamePanel.userInterface.commandNumber == 1) {
                gamePanel.saveLoad.load();
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

        // SETTINGS
        if (code == KeyEvent.VK_ESCAPE)
            gamePanel.gameState = gamePanel.settingsState;

        // DEBUG
        if (code == KeyEvent.VK_F1)
            showDebugText = !showDebugText;

        // MAP REFRESH
        if (code == KeyEvent.VK_F2) {
            switch (gamePanel.currentMap) {
                case 0 -> gamePanel.tileManager.loadMap("/maps/world.txt", 0);
                case 1 -> gamePanel.tileManager.loadMap("/maps/interior01.txt", 1);
            }
        }
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

        if (code == KeyEvent.VK_ESCAPE) {
            gamePanel.userInterface.commandNumber = 1;
            gamePanel.gameState = gamePanel.settingsState;
        }

        if (code == KeyEvent.VK_E) {
            gamePanel.playSoundEffect(9);
            gamePanel.player.selectItem();
        }

        playerInventory(code);
    }

    public void settingsState(int code) {
        if (code == KeyEvent.VK_ESCAPE)
            gamePanel.gameState = gamePanel.playState;

        if (code == KeyEvent.VK_E) {
            interactPressed = true;
            gamePanel.playSoundEffect(9);

            if (gamePanel.userInterface.commandNumber == 3 && gamePanel.music.volumeScale < 5) {
                gamePanel.music.volumeScale++;
                gamePanel.music.checkVolume();
                gamePanel.playSoundEffect(9);
            } else if (gamePanel.userInterface.commandNumber == 3 && gamePanel.music.volumeScale == 5) {
                gamePanel.music.volumeScale = 0;
                gamePanel.music.checkVolume();
                gamePanel.playSoundEffect(9);
            } else if (gamePanel.userInterface.commandNumber == 4 && gamePanel.soundEffect.volumeScale < 5) {
                gamePanel.soundEffect.volumeScale++;
                gamePanel.playSoundEffect(9);
            } else if (gamePanel.userInterface.commandNumber == 4 && gamePanel.soundEffect.volumeScale == 5) {
                gamePanel.soundEffect.volumeScale = 0;
                gamePanel.playSoundEffect(9);
            }
        }

         if (code == KeyEvent.VK_W) {
             if (gamePanel.userInterface.subState == 0) {
                 gamePanel.userInterface.commandNumber--;
                 gamePanel.playSoundEffect(9);

                 if (gamePanel.userInterface.commandNumber < 0)
                     gamePanel.userInterface.commandNumber = 7;
             } else if (gamePanel.userInterface.subState == 2 || gamePanel.userInterface.subState == 3) {
                 gamePanel.userInterface.commandNumber--;
                 gamePanel.playSoundEffect(9);

                 if (gamePanel.userInterface.commandNumber < 0)
                     gamePanel.userInterface.commandNumber = 1;

             }
         }

         if (code == KeyEvent.VK_S) {
             if (gamePanel.userInterface.subState == 0) {
                 gamePanel.userInterface.commandNumber++;
                 gamePanel.playSoundEffect(9);

                 if (gamePanel.userInterface.commandNumber > 7)
                     gamePanel.userInterface.commandNumber = 0;
             } else if (gamePanel.userInterface.subState == 2 || gamePanel.userInterface.subState == 3) {
                 gamePanel.userInterface.commandNumber++;
                 gamePanel.playSoundEffect(9);

                 if (gamePanel.userInterface.commandNumber > 1)
                     gamePanel.userInterface.commandNumber = 0;

             }
         }

        if (gamePanel.userInterface.subState == 0) {
            if (code == KeyEvent.VK_A) {
                if (gamePanel.userInterface.commandNumber == 3 && gamePanel.music.volumeScale > 0) {
                    gamePanel.music.volumeScale--;
                    gamePanel.music.checkVolume();
                    gamePanel.playSoundEffect(9);
                } else if (gamePanel.userInterface.commandNumber == 4 && gamePanel.soundEffect.volumeScale > 0) {
                    gamePanel.soundEffect.volumeScale--;
                    gamePanel.playSoundEffect(9);
                }
            }

            if (code == KeyEvent.VK_D) {
                if (gamePanel.userInterface.commandNumber == 3 && gamePanel.music.volumeScale < 5) {
                    gamePanel.music.volumeScale++;
                    gamePanel.music.checkVolume();
                    gamePanel.playSoundEffect(9);
                } else if (gamePanel.userInterface.commandNumber == 4 && gamePanel.soundEffect.volumeScale < 5) {
                    gamePanel.soundEffect.volumeScale++;
                    gamePanel.playSoundEffect(9);
                }
            }
        }
    }

    public void gameOverState(int code) {
        if (code == KeyEvent.VK_W) {
            gamePanel.userInterface.commandNumber--;

            if (gamePanel.userInterface.commandNumber < 0)
                gamePanel.userInterface.commandNumber = 1;

            gamePanel.playSoundEffect(9);
        }

        if (code == KeyEvent.VK_S) {
            gamePanel.userInterface.commandNumber++;

            if (gamePanel.userInterface.commandNumber > 1)
                gamePanel.userInterface.commandNumber = 0;

            gamePanel.playSoundEffect(9);
        }

        if (code == KeyEvent.VK_E) {
            if (gamePanel.userInterface.commandNumber == 0) {
                gamePanel.playSoundEffect(9);
                gamePanel.gameState = gamePanel.playState;
                gamePanel.retry();
                gamePanel.playMusic(0);
            } else if (gamePanel.userInterface.commandNumber == 1) {
                gamePanel.playSoundEffect(9);
                gamePanel.gameState = gamePanel.titleState;
                gamePanel.restart();
            }
        }
    }

    public void tradeState(int code) {
        if (code== KeyEvent.VK_E)
            interactPressed = true;

        if (gamePanel.userInterface.subState == 0) {
            if (code == KeyEvent.VK_W) {
                gamePanel.userInterface.commandNumber--;

                if (gamePanel.userInterface.commandNumber < 0)
                    gamePanel.userInterface.commandNumber = 2;

                gamePanel.playSoundEffect(9);
            }

            if (code == KeyEvent.VK_S) {
                gamePanel.userInterface.commandNumber++;

                if (gamePanel.userInterface.commandNumber > 2)
                    gamePanel.userInterface.commandNumber = 0;

                gamePanel.playSoundEffect(9);
            }
        }

        if (gamePanel.userInterface.subState == 1) {
            npcInventory(code);

            if (code == KeyEvent.VK_ESCAPE) {
                gamePanel.userInterface.subState = 0;
                gamePanel.playSoundEffect(9);
            }
        }

        if (gamePanel.userInterface.subState == 2) {
            playerInventory(code);

            if (code == KeyEvent.VK_ESCAPE) {
                gamePanel.userInterface.subState = 0;
                gamePanel.playSoundEffect(9);
            }
        }
    }

    // перемещение по инвентарю игрока
    public void playerInventory(int code) {
        if (code == KeyEvent.VK_W)
            if (gamePanel.userInterface.playerSlotRow != 0) {
                gamePanel.userInterface.playerSlotRow--;
                gamePanel.playSoundEffect(9);
            }

        if (code == KeyEvent.VK_A)
            if (gamePanel.userInterface.playerSlotCol != 0) {
                gamePanel.userInterface.playerSlotCol--;
                gamePanel.playSoundEffect(9);
            }

        if (code == KeyEvent.VK_S)
            if (gamePanel.userInterface.playerSlotRow != 3) {
                gamePanel.userInterface.playerSlotRow++;
                gamePanel.playSoundEffect(9);
            }

        if (code == KeyEvent.VK_D)
            if (gamePanel.userInterface.playerSlotCol != 4) {
                gamePanel.userInterface.playerSlotCol++;
                gamePanel.playSoundEffect(9);
            }
    }

    // перемещение по инвентарю торговца
    public void npcInventory(int code) {
        if (code == KeyEvent.VK_W)
            if (gamePanel.userInterface.npcSlotRow != 0) {
                gamePanel.userInterface.npcSlotRow--;
                gamePanel.playSoundEffect(9);
            }

        if (code == KeyEvent.VK_A)
            if (gamePanel.userInterface.npcSlotCol != 0) {
                gamePanel.userInterface.npcSlotCol--;
                gamePanel.playSoundEffect(9);
            }

        if (code == KeyEvent.VK_S)
            if (gamePanel.userInterface.npcSlotRow != 3) {
                gamePanel.userInterface.npcSlotRow++;
                gamePanel.playSoundEffect(9);
            }

        if (code == KeyEvent.VK_D)
            if (gamePanel.userInterface.npcSlotCol != 4) {
                gamePanel.userInterface.npcSlotCol++;
                gamePanel.playSoundEffect(9);
            }
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
