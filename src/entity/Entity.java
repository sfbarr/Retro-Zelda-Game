package entity;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Entity {

    GamePanel gp;
    public int screenX, screenY;

    // STATE & SPECS
    public int worldX, worldY;
    public String direction = "down";
    public boolean underwater = false;
    public boolean invincible = false;
    public boolean attacking = false;
    public boolean dying = false;
    public int spriteNum = 1;


    // IMAGES
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2,
            upWater1, upWater2, downWater1, downWater2, leftWater1, leftWater2,
            rightWater1, rightWater2;
    public BufferedImage upAtk1, upAtk2, downAtk1, downAtk2,
            leftAtk1, leftAtk2, rightAtk1, rightAtk2;
    public BufferedImage death1, death2, death3;

    // COLLISION
    public Rectangle solidArea = new Rectangle(12,16,40,48);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    public boolean playerCollisionOn = false;

    // ATTACK DETECTION
    public Rectangle attackArea = new Rectangle(0,0,0,0);

    // DIALOGUE
    public boolean conversable = false;
    public String[] dialogues = new String[20];


    // Counters
    public int actionCounter = 0;
    public int spriteCounter = 0;
    public int invincibleCounter = 0;
    public int deathCount = 0;
    int rotateCounter = 0;


    // PREVIOUS SUPER-OBJECT CLASS VARIABLES
    public BufferedImage image, image2, image3;
    public String name;
    public boolean collision = false;
    public int baseY;
    public boolean floating = false;


    // CHARACTER ATTRIBUTES
    public int type; // 0 = player, 1 = NPC, 2 = Monster
    public int speed;   // MIGHT HAVE TO CHANGE TO DOUBLE LATER :(
    public int maxLife;
    public int life;


    public Entity(GamePanel gp){
        this.gp = gp;
    }
    // SET-ACTION Method OVERRIDDEN by INDIVIDUAL NPC CLASSES
    public void setAction(){}
    public void speak(){}
    public void dyingAnimation(){}

    // UPDATE METHOD SHARED AMONG ALL NPC's
    public void update() {
        setAction();
        collisionOn = false;
        playerCollisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkEntity(this, gp.npc);
        gp.cChecker.checkEntity(this, gp.monsters);
        boolean playerContact = gp.cChecker.checkPlayer(this);
        if (playerContact && type == 2){
            if (!gp.player.invincible){
                int se = (int) (Math.random() * 100);
                if (se <= 50){ gp.playSoundEffect(31); } else { gp.playSoundEffect(32); }
                gp.player.life --; gp.player.invincible = true;
            }
        }

        gp.cChecker.checkWater(this);


        // IF COLLISION IS FALSE, PLAYER CAN MOVE
        if (!collisionOn && !playerCollisionOn){

            switch (direction) {
                case "up" -> worldY -= speed;
                case "down" -> worldY += speed;
                case "left" -> worldX -= speed;
                case "right" -> worldX += speed;
            }
        }

        // NOTE: if CollisionOn but NOT playerCollision, we turn NPC around after collisions:

        else if (!playerCollisionOn) {
            switch (direction) {
                case "up" -> {direction = "down"; worldY += speed; collisionOn = false; }
                case "down" -> { direction = "up"; worldY -= speed; collisionOn = false; }
                case "left" -> { direction = "right"; worldX += speed; collisionOn = false; }
                case "right" -> { direction = "left"; worldX -= speed; collisionOn = false; }
            }

        // If PLAYER Collision, Dialogue State initiated
        } else if (!gp.ui.dialogueFinished && this.conversable){
            System.out.println("CollisionOn AND playerCollision for: " + this.getClass().getSimpleName());
            gp.playSoundEffect(15);
            gp.gameState = gp.dialogueState;
            gp.ui.currentEntity = this;
            switch (gp.player.direction) {
                case "up" -> direction = "down";
                case "down" -> direction = "up";
                case "left" -> direction = "right";
                case "right" -> direction = "left";
            }
        }

        if (worldX <= 0) {
            worldX += 5;
            direction = "right";
        }
        if (worldX > gp.worldWidth - gp.tileSize){
            worldX -= 5;
            direction = "left";
        }
        if (worldY <= 0) {
            worldY += 5;
            direction = "down";
        }
        if (worldY > gp.worldHeight - gp.tileSize){
            worldY -= 5;
            direction = "up";
        }
        if (!playerCollisionOn) {
            spriteCounter++;
        }
        if (spriteCounter > 15) {
            spriteNum = (spriteNum == 1) ? 2 : 1;
            spriteCounter = 0;
        }

        // MONSTER HIT TIMING
        if (invincible){
            invincibleCounter++;
            if (invincibleCounter > 40){
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }
    public void draw(Graphics2D g2, GamePanel gp) {

        screenX = worldX - gp.player.worldX + gp.player.screenX;
        screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
//            if (this.getClass().getSimpleName().equals("OldMan") ||
//                    this.getClass().getSimpleName().equals("Player")) {

            BufferedImage image = null;

            switch (direction) {
                case "up" -> {
                    if (spriteNum == 1) {
                        image = (underwater) ? upWater1 : up1;
                    } else if (spriteNum == 2) {
                        image = (underwater) ? upWater2 : up2;
                    }
                }
                case "down" -> {
                    if (spriteNum == 1) {
                        image = (underwater) ? downWater1 : down1;
                    } else if (spriteNum == 2) {
                        image = (underwater) ? downWater2 : down2;
                    }
                }
                case "left", "northWest", "southWest" -> {
                    if (spriteNum == 1) {
                        image = (underwater) ? leftWater1 : left1;
                    }
                    if (spriteNum == 2) {
                        image = (underwater) ? leftWater2 : left2;
                    }
                }
                case "right", "northEast", "southEast" -> {
                    if (spriteNum == 1) {
                        image = (underwater) ? rightWater1 : right1;
                    }
                    if (spriteNum == 2) {
                        image = (underwater) ? rightWater2 : right2;
                    }
                }
                default -> image = this.image;
            }

            // Monster Health Bar
            if (type == 2 && Math.abs(gp.player.worldY - worldY) < gp.tileSize * 2
            && Math.abs(gp.player.worldX - worldX) < gp.tileSize * 2) {

                switch (life) {
                    case 4 -> g2.setColor(new Color(0, 255, 0));
                    case 3 -> g2.setColor(new Color(255, 255, 0));
                    case 2 -> g2.setColor(new Color(255, 153, 0));
                    case 1 -> g2.setColor(new Color(255, 0, 0));
                    case 0 -> g2.setColor(Color.BLACK);
                }
                int width = gp.tileSize - ((4-life)*12);
                g2.fillRect(screenX , screenY - 12,  width, 6);

                g2.setColor(Color.BLACK);
                g2.fillRect(screenX + width, screenY - 12, gp.tileSize - width, 6);
            }

            if (invincible){
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            }
            if (dying){ dyingAnimation() ;}


            // Attempt at rotation
            if (Objects.equals(name, "Sword")){
                Graphics2D g3 = g2;

                double sigma = (2*Math.PI)/12f;
                sigma = sigma * rotateCounter;
                rotateCounter++;


                g3.rotate(-sigma, screenX + image.getWidth()/2f, screenY + image.getHeight()/2f);
                g3.drawImage(image, screenX, screenY, null);
                g3.rotate(sigma, screenX + image.getWidth()/2f, screenY + image.getHeight()/2f);
//                Shape RoundRectangle2D = new RoundRectangle2D() {
//                    @Override
//                    public double getArcWidth() {
//                        return gp.tileSize * 2;
//                    }
//
//                    @Override
//                    public double getArcHeight() {
//                        return gp.tileSize * 2;
//                    }
//
//                    @Override
//                    public void setRoundRect(double x, double y, double w, double h, double arcWidth, double arcHeight) {
//
//                    }
//
//                    @Override
//                    public double getX() {
//                        return screenX - gp.tileSize/2f - 10;
//                    }
//
//                    @Override
//                    public double getY() {
//                        return screenY - gp.tileSize/2f - 10;
//                    }
//
//                    @Override
//                    public double getWidth() {
//                        return gp.tileSize * 3;
//                    }
//
//                    @Override
//                    public double getHeight() {
//                        return gp.tileSize * 3;
//                    }
//
//                    @Override
//                    public boolean isEmpty() {
//                        return false;
//                    }
//
//                    @Override
//                    public Rectangle2D getBounds2D() {
//                        return null;
//                    }
//                };
//                g3.setColor(Color.RED);
//                Stroke s = new BasicStroke(1);
//                g3.setStroke(s);
//                g3.draw(RoundRectangle2D);

                return;
            }


            g2.drawImage(image, screenX, screenY, null);

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
    }

    public BufferedImage setup(String imagePath, int width, int height){

        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            image = uTool.scaleImage(image, width, height);

        } catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }


}
