package entity;
import main.GamePanel;
import main.KeyHandler;
import main.ObjectSetter;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Set;

public class Player extends Entity {

    GamePanel gp;
    KeyHandler keyH;

    public final int screenX, screenY;

//    public int hasKey = 0;
    int c = 1;
    int k = 1;
    int preJump;
    double teleportTime;
    int tt = 1;


    public boolean hasBoots, swordObtained = false;
    public boolean chestFound = false;
    public boolean teleported = false;
    public boolean attackable = true;
    public int invincibleCounter = 0;
    public int attackCounter = 0;
    public int atkNum = 1;

    public Player(GamePanel gp, KeyHandler keyH){

        super(gp);
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - gp.tileSize/2;
        screenY = gp.screenHeight/2 - gp.tileSize/2;

        solidArea = new Rectangle(gp.tileSize/4, gp.tileSize/2, gp.tileSize/2, gp.tileSize/2);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        attackArea.width = attackArea.height = 48;

        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
    }
    public void setDefaultValues(){

        worldX = gp.tileSize * 34;
        worldY = gp.tileSize * 23;
        speed = 6;
        direction = "down";
        maxLife = 6;
        life = maxLife;


    }
    public void getPlayerImage(){

            up1 = setup("/player/boy_up_1", gp.tileSize, gp.tileSize);
            up2 = setup("/player/boy_up_2", gp.tileSize, gp.tileSize);
            upWater1 = setup("/player/boy_up_underwater", gp.tileSize, gp.tileSize);
            upWater2 = setup("/player/boy_up_underwater2", gp.tileSize, gp.tileSize);
            down1 = setup("/player/boy_down_1", gp.tileSize, gp.tileSize);
            down2 = setup("/player/boy_down_2", gp.tileSize, gp.tileSize);
            downWater1 = setup("/player/boy_down_underwater", gp.tileSize, gp.tileSize);
            downWater2 = setup("/player/boy_down_underwater2", gp.tileSize, gp.tileSize);
            left1 = setup("/player/boy_left_1", gp.tileSize, gp.tileSize);
            left2 = setup("/player/boy_left_2", gp.tileSize, gp.tileSize);
            leftWater1 = setup("/player/boy_left_underwater", gp.tileSize, gp.tileSize);
            leftWater2 = setup("/player/boy_left_underwater2", gp.tileSize, gp.tileSize);
            right1 = setup("/player/boy_right_1", gp.tileSize, gp.tileSize);
            right2 = setup("/player/boy_right_2", gp.tileSize, gp.tileSize);
            rightWater1 = setup("/player/boy_right_underwater", gp.tileSize, gp.tileSize);
            rightWater2 = setup("/player/boy_right_underwater2", gp.tileSize, gp.tileSize);
    }
    public void getPlayerAttackImage(){
        upAtk1 = setup("/player/boy_up_attack_1", gp.tileSize, gp.tileSize * 2);
        upAtk2 = setup("/player/boy_up_attack_2", gp.tileSize, gp.tileSize * 2);
        downAtk1 = setup("/player/boy_down_attack_1", gp.tileSize, gp.tileSize * 2);
        downAtk2 = setup("/player/boy_down_attack_2", gp.tileSize, gp.tileSize * 2);
        leftAtk1 = setup("/player/boy_left_attack_1", gp.tileSize * 2, gp.tileSize);
        leftAtk2 = setup("/player/boy_left_attack_2", gp.tileSize * 2, gp.tileSize);
        rightAtk1 = setup("/player/boy_right_attack_1", gp.tileSize * 2, gp.tileSize);
        rightAtk2 = setup("/player/boy_right_attack_2", gp.tileSize * 2, gp.tileSize);
    }
    public void update(){

        if (attacking){
            attack();
        }

        if (keyH.muteOn && c == 1){
            gp.stopMusic();
            c = 0;
        } else if (keyH.muteOff && c == 0){
            gp.playMusic(0);
            c = 1;
        }


        int totalKeyPressed = keyH.upPressed + keyH.downPressed + keyH.leftPressed + keyH.rightPressed;
        if (totalKeyPressed == 1) {
            if (keyH.upPressed == 1) {
                direction = "up";

                long keyPressedLength;
                if (keyH.upKeyPressedTime == 0) {
                    keyPressedLength = 0;
                } else {
                    keyPressedLength = System.currentTimeMillis() - keyH.upKeyPressedTime;
                }

                if (hasBoots && keyPressedLength > 200 && speed < 10) {
                    speed = speed + 1;
                }

            } else if (keyH.downPressed == 1) {
                direction = "down";

                long keyPressedLength;
                if (keyH.downKeyPressedTime == 0) {
                    keyPressedLength = 0;
                } else {
                    keyPressedLength = System.currentTimeMillis() - keyH.downKeyPressedTime;
                }

                if (hasBoots && keyPressedLength > 200 && speed < 10) {
                    speed = speed + 1;
                }
            }
            else if (keyH.leftPressed == 1) {
                direction = "left";

                long keyPressedLength;
                if (keyH.leftKeyPressedTime == 0) {
                    keyPressedLength = 0;
                } else {
                    keyPressedLength = System.currentTimeMillis() - keyH.leftKeyPressedTime;
                }

                if (hasBoots && keyPressedLength > 200 && speed < 10) {
                    speed = speed + 1;
                }
            } else if (keyH.rightPressed == 1) {
                direction = "right";

                long keyPressedLength;
                if (keyH.rightKeyPressedTime == 0) {
                    keyPressedLength = 0;
                } else {
                    keyPressedLength = System.currentTimeMillis() - keyH.rightKeyPressedTime;
                }

                if (hasBoots && keyPressedLength > 200 && speed < 10) {
                    speed = speed + 1;
                }
            }
        } else if (totalKeyPressed > 1) {
            if (keyH.upPressed + keyH.leftPressed == 2) {
                direction = "northWest";

                long keyPressedLength;
                if (keyH.upKeyPressedTime == 0) {
                    keyPressedLength = 0;
                } else {
                    keyPressedLength = System.currentTimeMillis() - keyH.upKeyPressedTime;
                }

                if (hasBoots && keyPressedLength > 200 && speed < 10) {
                    speed = speed + 1;
                }
            } else if (keyH.upPressed + keyH.rightPressed == 2) {
                direction = "northEast";

                long keyPressedLength;
                if (keyH.upKeyPressedTime == 0) {
                    keyPressedLength = 0;
                } else {
                    keyPressedLength = System.currentTimeMillis() - keyH.upKeyPressedTime;
                }

                if (hasBoots && keyPressedLength > 200 && speed < 10) {
                    speed = speed + 1;
                }
            } else if (keyH.downPressed + keyH.leftPressed == 2) {
                direction = "southWest";

                long keyPressedLength;
                if (keyH.downKeyPressedTime == 0) {
                    keyPressedLength = 0;
                } else {
                    keyPressedLength = System.currentTimeMillis() - keyH.downKeyPressedTime;
                }

                if (hasBoots && keyPressedLength > 200 && speed < 10) {
                    speed = speed + 4;
                }
            } else if (keyH.downPressed + keyH.rightPressed == 2) {
                direction = "southEast";

                long keyPressedLength;
                if (keyH.downKeyPressedTime == 0) {
                    keyPressedLength = 0;
                } else {
                    keyPressedLength = System.currentTimeMillis() - keyH.downKeyPressedTime;
                }

                if (hasBoots && keyPressedLength > 200 && speed < 10) {
                    speed = speed + 4;
                }
            }
        }
        // Checking ability to take damage again
        if (invincible){
            invincibleCounter++;
            if (invincibleCounter > 60){
                invincible = false;
                invincibleCounter = 0;
            }
        }

        if (!keyH.spacePressed) {
            preJump = worldY;
        } else if (gp.gameState == gp.playState) {
            if (k == 1) {
                gp.playSoundEffect(14);
            }
            //System.out.println("Jump initiated \n   preJump = " + preJump + "  worldY = " + worldY );
            //System.out.println("Parabola(k) =  " + parabola(k));
            worldY = (int) (preJump + parabola(k));
            k++;
            System.out.println("K = " + k);
            if (k == 31) {
                k = 1;
                keyH.spacePressed = false;
                System.out.println("Jump finished");
            }
        }

        // Setting Map Boundaries
        if (worldX < 0 ){
            worldX = 0;
        } if (worldX >= gp.worldWidth - gp.tileSize) {
            worldX = (gp.worldWidth) - gp.tileSize;
        } if (worldY < 0 ){
            worldY = 0;
        } if (worldY > gp.worldHeight - gp.tileSize) {
            worldY = gp.worldHeight - gp.tileSize;
        }

        // CHECKING TILE COLLISION
        collisionOn = false;
        gp.cChecker.checkTile(this);

        // CHECKING OBJECT COLLISION
        int objIndex = gp.cChecker.checkObject(this, true);
        pickUpObject(objIndex);

        // CHECK NPC COLLISION
        int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
        checkNPC(npcIndex);
        // CHECK MONSTER COLLISION
        int monIndex = gp.cChecker.checkEntity(this, gp.monsters);
        checkMonster(monIndex);

        // CHECK EVENT COLLISION
        gp.eHandler.checkEvent();


        int uw = (underwater) ? 1 : 0;
        // CHECK UNDERWATER STATUS
        gp.cChecker.checkWater(this);

        // NOTE: Sound Effect for water:
//        if (underwater && uw == 0){
//            gp.playSoundEffect(25);
//        }


        // IF COLLISION IS FALSE, PLAYER CAN MOVE
        if (!collisionOn && totalKeyPressed == 1){

            if (keyH.upPressed == 1){ worldY -= speed;}
            else if (keyH.downPressed == 1){worldY += speed;}
            if (keyH.leftPressed == 1){ worldX -= speed;}
            else if (keyH.rightPressed == 1) { worldX += speed; }

        } else if (!collisionOn && totalKeyPressed > 1){
            switch (direction) {
                case "northWest" -> {
                    worldX -= speed;
                    worldY -= speed;
                }
                case "northEast" -> {
                    worldX += speed;
                    worldY -= speed;
                }
                case "southWest" -> {
                    worldX -= speed;
                    worldY += speed;
                }
                case "southEast" -> {
                    worldX += speed;
                    worldY += speed;
                }
            }

        // "Potential Alternative if Necessary"

//        } else if (!collisionOn && totalKeyPressed > 1){
//            if (keyH.upPressed + keyH.leftPressed == 2){
//                worldX -= speed / 10;
//                worldY -= speed / 10;
//            } else if (keyH.upPressed + keyH.rightPressed == 2){
//                worldX += speed / 10;
//                worldY -= speed / 10;
//            } else if (keyH.downPressed + keyH.leftPressed == 2){
//                worldX -= speed / 10;
//                worldY += speed / 10;
//            }else if (keyH.downPressed + keyH.rightPressed == 2){
//                worldX += speed / 10;
//                worldY += speed / 10;
//            }
        }

        // nothing here if key acceleration is not used

        if (hasBoots && totalKeyPressed == 0){
            speed = 40;
        }

        if (keyH.centerPressed){
           worldX = 25 * gp.tileSize; worldY = 20 * gp.tileSize;
        }





        // Sprite Animation
        spriteCounter++;
        if (spriteCounter > 15 && totalKeyPressed != 0) {
            if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }


        // Checking [ENTER] key for Attack

    }

    public void attack(){
//        System.out.println(atkNum + " count: " + attackCounter + " Attackable? " + attackable + " Attacking? " + attacking);

        attackCounter ++;
        if (attackCounter <= 5){
            if (attackCounter == 1) {
                gp.playSoundEffect(8);
                gp.playSoundEffect(30); }
            atkNum = 1;
        } else if (attackCounter <= 25){

            atkNum = 2;

            // Save current world X, Y, solidArea
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            // Adjust player worldX, worldY for attackArea
            switch (direction) {
                case "up": worldY -= attackArea.height; break;
                case "down": worldY += attackArea.height; break;
                case "left": worldX -= attackArea.width; break;
                case "right": worldX += attackArea.width; break;
            }
            // attackArea becomes solidArea
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;
            // Check monster collision with the updated X, Y
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monsters);
            damageMonster(monsterIndex);

            // Reset values
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;

        } else {
            atkNum = 1;
            attackCounter = 0;
            attacking = false;
            System.out.println("ATK done");
        }
    }

    public void pickUpObject(int index){
        if (index != 999){
            switch (gp.obj[index].name) {
                case "Door" -> {
                    gp.oSetter.openDoor(index, false);
                    gp.playSoundEffect(1);
                }
                case "SideDoor" -> {
                    gp.oSetter.openDoor(index, true);
                    gp.playSoundEffect(1);
                }
                case "Sword" -> {
                    swordObtained = true;
                    for (int i = 0; i < gp.obj.length; i++){
                        if (gp.obj[i] != null){
                            if (Objects.equals(gp.obj[i].name, "Sword")) {
                                gp.obj[i] = null;
                                break;
                            }
                        }
                    }
                    gp.playSoundEffect(4);
                }
                case "Portal" -> {
                    System.out.println("portal");
                    if (tt == 1 && !teleported) {
                        gp.stopMusic();
                        gp.oSetter.clearObjects();
                        gp.tileM.loadMap("/maps/world02.txt", gp.tileM.mapTileNum);
                        gp.oSetter.setObject(2);
                        gp.playSoundEffect(11);
                        teleported = true;
                        tt = 2;
                    } else if (tt == 2 && teleported) {
                        gp.tileM.loadMap("/maps/world01.txt", gp.tileM.mapTileNum);
                        gp.oSetter.setObject(1);
                        gp.playMusic(1);
                        teleported = false;
                        tt = 1;
                    }
                }
                default -> {
                }
            }
        }
    }

    public void checkNPC(int i){

        // IF we want NPC dialogue to be triggered by enter, use:
        // if (keyH.enterPressed && i != 999)

        if (i != 999){
            System.out.println("You're colliding with an NPC!");
            if (!gp.ui.dialogueFinished) {
                gp.playSoundEffect(15);
                gp.gameState = gp.dialogueState;
                switch (direction) {
                    case "up" -> gp.npc[i].direction = "down";
                    case "down" -> gp.npc[i].direction = "up";
                    case "left" -> gp.npc[i].direction = "right";
                    case "right" -> gp.npc[i].direction = "left";
                }
            }
            gp.npc[i].speak();
            gp.ui.currentEntity = gp.npc[i];

        } else if (keyH.enterPressed && swordObtained){
            if (attackable && !gp.ui.messageOn) {
                attacking = true;
                attackable = false;
            }
        } else if (!keyH.enterPressed){
            attackable = true;
        }
    }
    public void checkMonster(int i){

        if (i != 999){
            if (!invincible){
                double rand = Math.random();
                int se = (int) (rand * 100);
                if (se <= 50) {
                    gp.playSoundEffect(31);
                } else {
                    gp.playSoundEffect(32);
                }
                life --;
                invincible = true;
            }
            System.out.println("You're colliding with MONSTER! index: " + i);
        }
    }
    public void damageMonster(int i){
        if (i != 999){
            if (!gp.monsters[i].invincible){
                gp.monsters[i].life --;
                gp.monsters[i].invincible = true;
                if (gp.monsters[i].life >= 1) {
                    gp.playSoundEffect(27);
                } else {
                    gp.playSoundEffect(28);
                }

                if (gp.monsters[i].life <= 0){
                    gp.monsters[i].dying = true;
                }
            }
        } else {
            System.out.println("Miss");
        }
    }


    public void draw(Graphics2D g2, GamePanel gp) {
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        BufferedImage image = null;

        switch (direction) {
            case "up" -> {
                if (attacking){
                    image = (atkNum == 1) ? upAtk1 : upAtk2;
                    tempScreenY = screenY - gp.tileSize;
                } else if (underwater) {
                    image = (spriteNum == 1) ? upWater1 : upWater2;
                } else {
                    image = (spriteNum == 1) ? up1 : up2;
                }
            } case "down" -> {
                if (attacking){
                    image = (atkNum == 1) ? downAtk1 : downAtk2;
                } else if (underwater) {
                    image = (spriteNum == 1) ? downWater1 : downWater2;
                } else {
                    image = (spriteNum == 1) ? down1 : down2;
                }
            } case "left", "northWest", "southWest" -> {
                if (attacking){
                    image = (atkNum == 1) ? leftAtk1 : leftAtk2;
                    tempScreenX = screenX - gp.tileSize;
                } else if (underwater) {
                    image = (spriteNum == 1) ? leftWater1 : leftWater2;
                } else {
                    image = (spriteNum == 1) ? left1 : left2;
                }
            } case "right", "northEast", "southEast" -> {
                if (attacking){
                    image = (atkNum == 1) ? rightAtk1 : rightAtk2;
                } else if (underwater) {
                    image = (spriteNum == 1) ? rightWater1 : rightWater2;
                } else {
                    image = (spriteNum == 1) ? right1 : right2;
                }
            }
        }

//  My Mediocre attempt at a walking animation
//        if (leftRightStep == 0) {
//            switch (direction) {
//                case "up":
//                    image = up1;
//                    break;
//                case "down":
//                    image = down1;
//                    break;
//                case "left":
//                    image = left1;
//                    break;
//                case "right":
//                    image = right1;
//                    break;
//            }
//            posOrNeg = 1;
//        }
//        else if (leftRightStep == 30) {
//            switch (direction) {
//                case "up":
//                    image = up2;
//                    break;
//                case "down":
//                    image = down2;
//                    break;
//                case "left":
//                    image = left2;
//                    break;
//                case "right":
//                    image = right2;
//                    break;
//            }
//            posOrNeg = -1;
//        }
//
//        leftRightStep = leftRightStep + posOrNeg;

        // Set transparent if invincible
        if (invincible){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        }
        g2.drawImage(image, tempScreenX, tempScreenY, null);

        // Reset Alpha (transparency)
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

    }


    public float parabola(int k){
        //(int) (-12 * (Math.cos(Math.PI/36 * k)) + 12);
        return (float) ((float) (gp.scale - 2) * (Math.pow(k-15,2)/(3.75)) - (60 * (gp.scale - 2)));
    }
}
