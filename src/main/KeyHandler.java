package main;

import entity.Player;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    GamePanel gp;
    public KeyHandler(GamePanel gp){
        muteOff = true;
        muteOn = false;
        this.gp = gp;
        checkDrawTime = false;
    }

    public boolean spacePressed, enterPressed, centerPressed, muteOff, muteOn, checkDrawTime;


    public int upPressed, downPressed, leftPressed, rightPressed,
            upKeyReleased, downKeyReleased, leftKeyReleased, rightKeyReleased;
    public long upKeyPressedTime, downKeyPressedTime, leftKeyPressedTime, rightKeyPressedTime;
    public int titleSelection = 1;

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        if (gp.gameState == gp.titleState){

            if(gp.ui.titleScreenState == 0){
                if (code == KeyEvent.VK_DOWN){
                    System.out.println("VK_DOWN");
                    titleSelection += 1;
                    gp.playSoundEffect(19);
                    if (titleSelection == 4){
                        titleSelection = 1;
                    }
                } else if (code == KeyEvent.VK_UP){
                    titleSelection -= 1;
                    gp.playSoundEffect(19);
                    if (titleSelection == 0){
                        titleSelection = 3;
                    }
                }
                if (code == KeyEvent.VK_ENTER && titleSelection == 1){
                    gp.ui.titleScreenState = 1;
                }
                else if (code == KeyEvent.VK_ENTER && titleSelection == 3){
                    System.exit(0);
                }
            } else if (gp.ui.titleScreenState == 1){

                if (code == KeyEvent.VK_DOWN){
                    titleSelection += 1;
                    gp.playSoundEffect(19);
                    if (titleSelection == 4){
                        titleSelection = 1;
                    }
                } else if (code == KeyEvent.VK_UP){
                    titleSelection -= 1;
                    gp.playSoundEffect(19);
                    if (titleSelection == 0){
                        titleSelection = 3;
                    }
                }

                // NOTE: Class selection menu - Do some character specific stuff later
                // Right now, pressing enter just starts game regardless
                if (code == KeyEvent.VK_ENTER){
                    gp.playSoundEffect(20);
                    gp.gameState = gp.playState;
                    gp.playMusic(0);
                }
            }


        }

        else if (gp.gameState == gp.playState){
            if (code == KeyEvent.VK_W) {
                upPressed = 1;
                upKeyReleased = 0;
                upKeyPressedTime = System.currentTimeMillis();
            }
            else if (code == KeyEvent.VK_S) {
                downPressed = 1;
                downKeyReleased = 0;
                downKeyPressedTime = System.currentTimeMillis();
            }
            if (code == KeyEvent.VK_A) {
                leftPressed = 1;
                leftKeyReleased = 0;
                leftKeyPressedTime = System.currentTimeMillis();
            }
            else if (code == KeyEvent.VK_D) {
                rightPressed = 1;
                rightKeyReleased = 0;
                rightKeyPressedTime = System.currentTimeMillis();
            }
            if (code == KeyEvent.VK_C) {
                centerPressed = true;
            }
            if (code == KeyEvent.VK_P){
                gp.gameState = gp.pauseState;
            }
            if (code == KeyEvent.VK_M) {
                muteOn = !muteOn;
                muteOff = !muteOff;
            }
            if (code == KeyEvent.VK_0) {
                muteOff = true;
                muteOn = false;
            }
            if (code == KeyEvent.VK_T) {
                checkDrawTime = !checkDrawTime;
            }
            if (code == KeyEvent.VK_SPACE) {
                spacePressed = true;
            }
            if (code == KeyEvent.VK_ENTER){
                enterPressed = true;
            }
        }
        // PAUSE STATE
        else if (gp.gameState == gp.pauseState){
            if (code == KeyEvent.VK_P){
                gp.gameState = gp.playState;
            }
        }

        // DIALOGUE STATE
        else if (gp.gameState == gp.dialogueState){

            if (code == KeyEvent.VK_SPACE){
                if (gp.ui.dialoguePaused) {
                    gp.ui.dialoguePaused = false;
                    gp.ui.continuePressed = true;
                    gp.playSoundEffect(18);
                    System.out.println("Space pressed, continue");
                } else {
                    if (gp.ui.dialogueLineNum < 8) {
                        gp.ui.dialogueLineNum ++;
                        gp.ui.characterNum = 0;
                        if (gp.ui.dialogueLineNum == 8){gp.ui.dialogueFinished = true;}
                    } // NOTE: Weird redundancy necessary to prevent glitch
                    else {gp.ui.dialogueFinished = true;}
                }
            }
            if (code == KeyEvent.VK_ENTER){
                gp.gameState = gp.playState;
                gp.ui.dialogueFinished = true;
                gp.doneCheckingDialogue = false;
            }



        }


        // ZOOM FUNCTION, DOESN'T WORK WELL WITH OBJECT SETTER, TOO LAZY TO MAKE IT WORK

//        if (code == KeyEvent.VK_UP){
//            gp.zoomInOut(1);
//        }
//        if (code == KeyEvent.VK_DOWN){
//            gp.zoomInOut(-1);
//        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();
        if (code == KeyEvent.VK_ENTER){
            enterPressed = false;
        }
        if (code == KeyEvent.VK_W) {
            upPressed = 0;
            upKeyReleased = 1;
            upKeyPressedTime = 0;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = 0;
            downKeyReleased = 1;
            downKeyPressedTime = 0;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = 0;
            leftKeyReleased = 1;
            leftKeyPressedTime = 0;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = 0;
            rightKeyReleased = 1;
            rightKeyPressedTime = 0;
        }
        if (code == KeyEvent.VK_P) {
            centerPressed = false;
        }
    }
}
