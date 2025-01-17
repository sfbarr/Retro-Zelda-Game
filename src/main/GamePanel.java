package main;

import entity.Entity;
import entity.Player;
import tile.TileManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

public class GamePanel extends JPanel implements Runnable {

    //Screen Settings
    public final int originalTileSize = 16; // 16x16 tile
    public final double scale = 4;
    public int tileSize = (int) (originalTileSize * scale); // 48x48 pixels if Scale = 3   // 64 x 64 if scale = 4
    public int maxScreenCol = 17;
    public int maxScreenRow = 12;
    public int screenWidth = tileSize * maxScreenCol; // 768 pixels
    public int screenHeight = tileSize * maxScreenRow; // 576 pixels

    // WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    // FPS
    final int FPS = 60;
    int p = 0; // Update portal image every other run

    // Sine Wave for Floating Object Illusion
    int sinY = 0;
    int increment1 = 0;

    int drawTimeCount = 0;
    long drawAverage;
    long drawTotal;

    // ENEMY NODE STUFF - NOT USED IN THIS GAME VERSION
    //public NodeManager nodeM = new NodeManager(this);


    // SYSTEM & OBJECT INSTANTIATION
    public TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler(this);
    Sound music = new Sound();
    Sound se = new Sound();
    Sound se3 = new Sound();

    public CollisionChecker cChecker = new CollisionChecker(this);
    public ObjectSetter oSetter = new ObjectSetter(this);
    public UI ui = new UI(this, keyH);
    public EventHandler eHandler = new EventHandler(this);

    Thread gameThread;


    // ENTITY AND OBJECT
    public Player player = new Player(this, keyH);
    //public EnemyTest enemy = new EnemyTest(this, player);
    public Entity[] obj = new Entity[20];
    public Entity[] npc = new Entity[10];
    public Entity[] monsters = new Entity[10];
    public ArrayList<Entity> entityList = new ArrayList<>();

    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public boolean endDialogueTimeEstablished = false;
    public boolean doneCheckingDialogue = true;
    public long endDialogueTime = System.currentTimeMillis();
    BufferedImage img = null;

    public GamePanel(){
        System.out.println("TILE SIZE: " + tileSize + "  SCALE:  " + scale);
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

//    public void zoomInOut(int i){
//        int oldWorldWidth = tileSize * maxWorldCol;
//        tileSize += i;
//        int newWorldWidth = tileSize * maxWorldCol;
//        double multiplier = (float)newWorldWidth/(float)oldWorldWidth;
//        player.worldX = (int)(player.worldX * multiplier);
//        player.worldY = (int)(player.worldY * multiplier);
//    }

    public void setUpGame() {
        oSetter.setObject(1);
        oSetter.setNPC();
        oSetter.setMonster();
        gameState = titleState;
        playSoundEffect2(24);

    }

    public void startGameThread(){

        gameThread = new Thread(this);
        gameThread.start();
    }


//    @Override
//    public void run() {
//
//        double drawInterval = 1000.0/FPS; // 0.0166 seconds
//        double nextDrawTime = System.currentTimeMillis() + drawInterval;
//
//        while(gameThread != null) {
//
//            // 1 UPDATE: update information such as char position
//            update();
//            // 2 DRAW: Draw the screen with the updated info
//            repaint();
//
//            try {
//                double remainingTime = nextDrawTime - System.currentTimeMillis();
//
//                if (remainingTime < 0) {
//                    remainingTime = 0;
//                }
//                Thread.sleep((long) remainingTime);
//
//                nextDrawTime += drawInterval;
//
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }
    public void run(){

        double drawInterval = 1000000000f/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        long timer = 0;
        int drawCount = 0;
        while (gameThread != null) {

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }
            if (timer >= 1000000000) {
                System.out.println(drawCount);
                drawCount = 0;
                timer = 0;
            }

        }
    }

    public void update(){

        if (gameState == playState){
            // PLAYER
            player.update();
            // NPC
            for (Entity entity : npc) {
                if (entity != null) {
                    entity.update();
                }
            }
            // MonGsTerRz!
            for (int i = 0; i < monsters.length; i++) {
                if (monsters[i] != null) {
                    if (monsters[i].life > 0) {
                        monsters[i].update();
                    } else if (monsters[i].deathCount > 30){
                        monsters[i] = null;
                    }
                }
            }
            if (p == 1) {
                oSetter.updatePortal();
                oSetter.updateFloating();
                p = 0;
            } else {p = 1;}

            // NOTE: for FLOATING SWORD! See the "updateFloating()" method




            // CHECKING ABILITY TO RE-ENTER DIALOGUE
            if (!doneCheckingDialogue) {
                checkDialogueAbility();
            }
        }
        if (gameState == pauseState){
            // nothing
        }



    }
    public void paintComponent(Graphics g){

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g; // Turning Graphics g into Graphics 2D

        // DEBUG
        long drawStart = 0;
        if (keyH.checkDrawTime) { drawStart = System.nanoTime(); }

        // TITLE SCREEN
        if (gameState == titleState){

            try {
                img = ImageIO.read(this.getClass().getResource("/title/16bit_blood_drip.gif"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            g2.drawImage(img, 0, 2, screenWidth,250, null);
            ImageIcon icon = new ImageIcon(img);
            JLabel label = new JLabel(icon);
            label.setVisible(true);
            this.add(label);


            ui.draw(g2);
        }
        // OTHER STUFF
        else {
            // TILE
            tileM.draw(g2); // TYPE BEFORE PLAYER, otherwise tiles cover player


            // ADD ALL ENTITIES TO ARRAY LIST
            entityList.add(player);

            for (Entity entity : npc) {
                if (entity != null) {
                    entityList.add(entity);
                }
            }
            for (Entity monster : monsters) {
                if (monster != null) {
                    entityList.add(monster);
                }
            }
            for (Entity entity : obj) {
                if (entity != null) {
                    entityList.add(entity);
                }
            }

            // SORT ENTITIES by Y-Values
            // NOTE: I DON'T UNDERSTAND THE COLLECTIONS FRAMEWORK, READ UP LATER

            Collections.sort(entityList, new Comparator<Entity>() {

                @Override
                public int compare(Entity e1, Entity e2) {
                    int result = Integer.compare(e1.worldY, e2.worldY);
                    return result;
                }
            });

            // DRAW ENTITIES
            for (Entity entity : entityList) {


                if (Objects.equals(entity.name, "Sword")){
//                    g2.rotate(-Math.PI/4, entity.screenX + tileSize/2f, entity.screenY + tileSize/2f);
                    entity.draw(g2, this);
//                    g2.rotate(Math.PI/4, entity.screenX + tileSize/2f, entity.screenY + tileSize/2f);
                } else {
                    entity.draw(g2, this);
                }




                // NOTE: USE TO CHECK ALL ENTITIES -- diagnostic purposes

//                System.out.println("Name: " + entity.getClass().getSimpleName() + ", Col: " + (entity.worldX/tileSize) + ", row: " + (entity.worldY/tileSize));
//                if (Objects.equals(entity.name, "Sword")){
//                    System.out.println("SWORD FOUND SWORD FOUND");
//                    System.out.println("Row: " + entity.worldY/tileSize + " Col: " + entity.worldX/tileSize);
//                }
            }

            for (int i = 0; i < entityList.size(); i++){
                entityList.clear();
            }

//            // NOTE: Previous sine wave for FLOATING, i.e. key position
//            if (gameState == playState) {
//                increment1++;
//                sinY = (int) (-12 * (Math.cos(Math.PI / 36 * increment1)) + 12);
//                if (increment1 == 72) {
//                    increment1 = 0;
//                }
//                oSetter.updateObject(sinY);
//            }

            // UI
            ui.draw(g2);
        }

        // DEBUG
        if(keyH.checkDrawTime) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;

            // FINDING AVERAGE DRAW TIME
            drawTotal += passed;
            drawTimeCount += 1;
            drawAverage = drawTotal/drawTimeCount;
            System.out.println("Draw Time:" + passed);
            System.out.println("Draw Average: " + drawAverage);

            g2.setColor(Color.white);
            g2.drawString("Draw Time: " + passed, 10, 400);
            //System.out.println("Draw Start: " + drawStart + " Draw End: " + drawEnd);

        }

        g2.dispose();
    }
    public void playMusic(int i) {

        music.setFile(i);
        music.play();
        music.loop();
    }
    public void stopMusic() {
        music.stop();
    }
    public void playSoundEffect(int i) {
        se.setSE(i);
        se.playSE();
    }
    public void playSoundEffect2(int i) {
        se3.setSE(i);
        se3.playSE();
    }

    public void stopSoundEffect(){
        se.stop2();
    }
    public void checkDialogueAbility(){
        if (ui.dialogueFinished && !endDialogueTimeEstablished){
            endDialogueTime = System.currentTimeMillis();
            endDialogueTimeEstablished = true;
        }
        if (System.currentTimeMillis() - endDialogueTime > 2500){
            endDialogueTimeEstablished = false;
            doneCheckingDialogue = true;
            ui.dialogueFinished = false;
            ui.dialogueLineNum = 0;
            ui.dialoguePaused = false;
            ui.continuePressed = false;
            ui.characterNum = 0;
        }
    }
}
