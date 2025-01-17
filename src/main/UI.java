package main;

import entity.Entity;
import entity.OldMan;
import object.Heart;
import object.Key;
import object.SuperObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;


public class UI {

    KeyHandler keyH;

    GamePanel gp;
    Graphics2D g2;

    // TITLE
    BufferedImage cursor;
    int cursorGap;
    String[] titleArray;

    public Font arial_20, arial_40, papyrus, luminari, nicerNightie, oldFax;
    public boolean messageOn = false;
    public String message = "";
    int dialogueCounter;
    int messageCounter;
    int dialogueLineNum = 0;
    int characterNum = 0;
    int width = 0;
    int h = 0;
    char[] letters = new char[1];
    public Entity currentEntity;

    // DIALOGUE & TITLE SCREEN STUFF
    public boolean dialogueFinished = false;
    public boolean dialoguePaused = false;
    public boolean continuePressed = false;
    public boolean firstLineSounded = false;
    public int titleScreenState = 0; // Zero = 1st screen, one = 2nd screen

    // HEADS UP DISPLAY - Health Percentage + HEARTS <3
    BufferedImage fullHeart, halfHeart, emptyHeart;


    public void displayMessage(String text) {
        message = text;
        messageOn = true;
    }

    public UI(GamePanel gp, KeyHandler keyh) {
        this.gp = gp;
        this.keyH = keyh;

        try {
            InputStream is = getClass().getResourceAsStream("/font/NicerNightie.ttf");
            nicerNightie = Font.createFont(Font.TRUETYPE_FONT, is);
            is = getClass().getResourceAsStream("/font/Old Fax.ttf");
            oldFax = Font.createFont(Font.TRUETYPE_FONT, is);

        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_20 = new Font("Arial", Font.PLAIN, 20);
        papyrus = new Font("Papyrus", Font.PLAIN, 20);
        luminari = new Font("Luminari", Font.PLAIN, 21);

        // CURSOR IMAGE
        try {
            cursor = ImageIO.read(this.getClass().getResource("/title/32bitCursor.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        cursorGap = (int) (1.5 * gp.tileSize);
        titleArray = new String[]{"New Game", "Load Game", "Quit"};

        // CREATING HUD & HEARTS
        Entity heart = new Heart(gp);
        fullHeart = heart.image;
        halfHeart = heart.image2;
        emptyHeart = heart.image3;

    }


    public void draw(Graphics2D g2) {

        this.g2 = g2;
        g2.setColor(Color.white);
        g2.setFont(nicerNightie);
        // TITLE STATE
        if (gp.gameState == gp.titleState){
            drawTitleScreen();
        }

        // PLAY STATE
        if (gp.gameState == gp.playState) {
            // PlayState stuff
            dialogueCounter = 0;
            drawPlayerHP();
            // EVENT MESSAGE
            if (messageOn){
                printMessage(message);
                messageCounter++;
                if (messageCounter > 150){
                    messageOn = false;
                    messageCounter = 0;
//                    gp.eHandler.resetEvent();

                }
            }
        }
        // PAUSE STATE
        if (gp.gameState == gp.pauseState) {
            drawPauseScreen();
            drawPlayerHP();
        }
        // DIALOGUE STATE
        if (gp.gameState == gp.dialogueState) {

            // NOTE: I could *probably* give drawDialogue()
            // responsibility to the ENTITY, not the game state.

            drawDialogueScreen();
            drawPlayerHP();
        }

    }
    public void drawPlayerHP(){
//        gp.player.life = 5;

        int x = gp.tileSize/2;
        int y = gp.tileSize/2;
        int i = 0;

        // DRAW EMPTY HEARTS
        while (i < gp.player.maxLife/2) {
            g2.drawImage(emptyHeart, x, y, null);
            i++;
            x += gp.tileSize;
        }
        x = gp.tileSize/2;
        y = gp.tileSize/2;
        i = 0;
        // DRAW CURRENT LIFE
        while (i < gp.player.life){
            g2.drawImage(halfHeart, x, y, null);
            i++;
            if (i < gp.player.life){
                g2.drawImage(fullHeart, x, y, null);
            }
            i++;
            x += gp.tileSize;
        }
    }
    public void drawTitleScreen(){

        if (titleScreenState == 0) {
            // TITLE NAME
            g2.setFont(g2.getFont().deriveFont(Font.ITALIC, 90F));
            String name = "Badlands Adventure";
            int x = getXforCenteredText(name);
            int y = 2* gp.tileSize + 10;

            g2.setColor(Color.RED);
            g2.drawString(name,x,y);

            y = (gp.screenHeight - gp.tileSize*5) + 16;

            if (keyH.titleSelection > 0 && keyH.titleSelection < 4) {
                x = getXforCenteredText(titleArray[keyH.titleSelection - 1]);
                g2.drawImage(cursor, x - gp.tileSize - (3 * gp.originalTileSize), y + (cursorGap * keyH.titleSelection) - cursorGap, gp.tileSize, gp.tileSize, null);
            }

            x = getXforCenteredText(titleArray[0]);
            y = gp.screenHeight - gp.tileSize * 4;
            g2.drawString(titleArray[0], x, y);

            x = getXforCenteredText(titleArray[1]);
            y += gp.tileSize * 1.5;
            g2.drawString(titleArray[1], x, y);

            y += gp.tileSize * 1.5;
            x = getXforCenteredText(titleArray[2]);
            g2.drawString(titleArray[2], x, y);
        }
        else if (titleScreenState == 1){

            g2.setColor(Color.RED);
            g2.setFont(g2.getFont().deriveFont(42F));

            String text = "Select your class!";
            int x  = getXforCenteredText(text);
            int y = gp.tileSize * 3;
            g2.drawString(text, x, y);

            text = "Fighter";
            x = getXforCenteredText(text);
            y += gp.tileSize * 2;
            g2.drawString(text, x, y);
            if (keyH.titleSelection == 1){
                g2.drawImage(cursor, x - (gp.tileSize * 3/2), (y - gp.tileSize/2) - 8, gp.tileSize, gp.tileSize, null);
            }

            text = "Thief";
            x = getXforCenteredText(text);
            y += gp.tileSize * 2;
            g2.drawString(text, x, y);
            if (keyH.titleSelection == 2){
                g2.drawImage(cursor, x - (gp.tileSize * 3/2), (y - gp.tileSize/2) - 8, gp.tileSize, gp.tileSize, null);
            }

            text = "Sorcerer";
            x = getXforCenteredText(text);
            y += gp.tileSize * 2;
            g2.drawString(text, x, y);
            if (keyH.titleSelection == 3){
                g2.drawImage(cursor, x - (gp.tileSize * 3/2), (y - gp.tileSize/2) - 8, gp.tileSize, gp.tileSize, null);
            }
        }
    }

    public void drawPauseScreen() {

        String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight / 2;
        dialogueCounter = 0;
        dialogueLineNum = 0;
        g2.drawString(text, x, y);
    }

    public void drawDialogueScreen() {

        // WINDOW
        // Settings for Bottom Dialogue Box:
        int x = gp.tileSize + gp.tileSize / 2;
        int y = gp.screenHeight / 2 + gp.tileSize + gp.tileSize / 2;
        int width = gp.screenWidth - (3 * gp.tileSize);
        int height = gp.screenHeight / 2 - (2 * gp.tileSize);

        // Settings for Top Dialogue Box:
        // int x = gp.tileSize;
        // int y = gp.tileSize/2;
        // int width = gp.screenWidth - (2 * gp.tileSize);
        // int height = gp.screenHeight/2 - (2 * gp.tileSize);
        drawSubWindow(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28f));
        x += gp.tileSize/2;
        y += gp.tileSize/4;
        printDialogue(x, y);
    }

    public void drawSubWindow(int x, int y, int width, int height) {
        Color c = new Color(0, 0, 0, 190);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 40, 40);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 30, 30);
    }
    // HUGE NOTE: Cool idea for letter rollout: Use "String.toCharArray()" to index each letter!!!!


    public int getXforCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth / 2 - length / 2;
    }
    public void printMessage(String message){
        g2.setFont(luminari);
        g2.setColor(Color.BLACK);
        int x = getXforCenteredText(message);
        drawSubWindow(x - gp.tileSize/2, gp.screenHeight/2 + gp.screenHeight/3,
                (int) (g2.getFontMetrics().getStringBounds(message, g2).getWidth() + gp.tileSize),
                g2.getFontMetrics().getHeight() + gp.tileSize/2);
        g2.setColor(Color.white);
        g2.drawString(message, x, gp.screenHeight/2 + gp.screenHeight/3 + gp.tileSize/2);
    }


    public void printDialogue(int x, int y) {
        System.out.println("Dialogue line num: " + dialogueLineNum);
        if (!dialogueFinished && !dialoguePaused){
//            if (!firstLineSounded){
//                gp.playSoundEffect(21);
//                firstLineSounded = true;
//            }
            letters = currentEntity.dialogues[dialogueLineNum].toCharArray();
            x = getXforCenteredText(currentEntity.dialogues[dialogueLineNum]);
            h = (int) g2.getFontMetrics().getStringBounds(currentEntity.dialogues[dialogueLineNum], g2).getHeight();
            for (int i = 0; i < characterNum; i++) {
                String letter = String.valueOf(letters[i]);
                x = getXforCenteredText(currentEntity.dialogues[dialogueLineNum]);
                if (dialogueLineNum < 4) {
                    g2.drawString(letter, x + width, y + h + (dialogueLineNum * (h + h/2)));

                } else {
                    g2.drawString(letter, x + width, y + h + (dialogueLineNum - 4) * (h + h/2));
                }
                width += (int) g2.getFontMetrics().getStringBounds(letter, g2).getWidth();
            }
            width = 0;
            dialogueCounter ++;
            if (dialogueCounter % 2 == 0){
                characterNum++;
            }
            if (dialogueCounter % 8 == 0){
                gp.playSoundEffect(23);
            }
            if (characterNum == letters.length){
                if (dialogueLineNum < currentEntity.dialogues.length) {
                    System.out.println(" Characternum: " + characterNum + "  letters Length: " + letters.length);
                    dialogueLineNum++;
                    System.out.println("Dialogue Line num: " + dialogueLineNum);
                    characterNum = 0;
                    if (dialogueLineNum == 8) {dialogueFinished = true;}
//                gp.playSoundEffect(21);
                } else {
                    System.out.println("Dialogue finished");
                    dialogueFinished = true;
                }
            }
        }


        if (dialogueLineNum == 4 && !continuePressed) {
            dialoguePaused = true;
            String continueText = "Press SPACE to Continue";
            x = getXforCenteredText(continueText);
            g2.drawString(continueText, x, gp.screenHeight - gp.tileSize);
        }


        if (dialogueLineNum > 0){
            if (dialogueLineNum <= 4 && !continuePressed) {

                for (int i = 0; i < dialogueLineNum; i++) {
                    char[] letters = currentEntity.dialogues[i].toCharArray();
                    for (int j = 0; j < letters.length; j++){

                        String letter = String.valueOf(letters[j]);
                        h = (int) g2.getFontMetrics().getStringBounds(letter, g2).getHeight();
                        x = getXforCenteredText(currentEntity.dialogues[i]);
                        g2.drawString(letter, x + width , y + h + (i * (h + h/2)));
                        width += g2.getFontMetrics().getStringBounds(letter, g2).getWidth();
                    }
                    width = 0;
                }
            }
//            if (dialogueLineNum == 4 && dialoguePaused){
//                for (int i = 0; i < dialogueLineNum; i++) {
//                    x = getXforCenteredText(currentEntity.dialogues[i]);
//                    g2.setFont(luminari);
//                    g2.drawString(currentEntity.dialogues[i], x, y + ((i + 1) * h));
//                }
//            }

            else if (!dialoguePaused) {

                for (int i = 4; i < dialogueLineNum; i++) {
                    char[] letters = currentEntity.dialogues[i].toCharArray();
                    for (int j = 0; j < letters.length; j++){

                        String letter = String.valueOf(letters[j]);
                        h = (int) g2.getFontMetrics().getStringBounds(letter, g2).getHeight();
                        x = getXforCenteredText(currentEntity.dialogues[i]);
                        g2.drawString(letter, x + width , y + h + (i-4) * (h + h/2));
                        width += g2.getFontMetrics().getStringBounds(letter, g2).getWidth();
                    }
//
                    width = 0;
                }


//                for (int i = 4; i < dialogueLineNum; i++){
//
//                    x = getXforCenteredText(currentEntity.dialogues[i]);
//                    g2.drawString(currentEntity.dialogues[i], x, y + h + (i-4) * (h + h/2));
//                }

            }
        }


    }

}

/*  PREVIOUS ITERATION OF UI --- KEY FINDING & DRAW TIME CALCULATION

public void draw(Graphics g2){

        if (gameFinished) {


            String text, timeText;
            int textLength;
            int x;
            int y;

            text = "Treasure Acquired!!! Congratulations :)";
            g2.setFont(rockwell);
            g2.setColor(Color.YELLOW);
            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            System.out.println("Text length : " + textLength);
            System.out.println(" Screen width: " + gp.screenWidth/2);


            x = gp.screenWidth/2 - textLength/2;
            y = gp.screenHeight/2 + gp.tileSize * 4;
            g2.drawString(text, x, y);

            timeText = String.format("Time: %.2f" , playTime);
            textLength = (int)g2.getFontMetrics().getStringBounds(timeText, g2).getCenterX();
            g2.drawString(timeText, gp.screenWidth/2 - textLength, gp.screenHeight - gp.tileSize);

            gp.gameThread = null;

        } else {
            g2.setFont(arial_40);
            g2.setColor(Color.white);
//            g2.drawImage(keyImage, gp.tileSize/2,gp.tileSize/2, gp.tileSize, gp.tileSize, null);
//            g2.drawString("x " + gp.player.hasKey, gp.tileSize + gp.originalTileSize * 2, gp.originalTileSize * 4);

            // TIME
            playTime += (double) 1/60;
//            System.out.println("Playtime: " + playTime);
//            g2.drawString(String.format("Time: %.2f",playTime), gp.tileSize * 11, gp.originalTileSize * 4);

            // MESSAGE
            if (messageOn) {
                g2.setFont(papyrus);
                g2.drawString(message, gp.tileSize/2, gp.tileSize * 5);

                messageCounter ++;
                if (messageCounter > 120){
                    messageCounter = 0;
                    messageOn = false;
                }
            }
        }
  }
 */
