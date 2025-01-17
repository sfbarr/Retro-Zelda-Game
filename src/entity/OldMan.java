package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import javax.management.relation.Role;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class OldMan extends Entity{

    public int screenX, screenY;


    public OldMan(GamePanel gp){
        super(gp);

        direction = "down";
        speed = 1;
        underwater = false;
        conversable = true;

        getImage();
        setDialogue();
    }
    public void setDialogue(){
//        dialogues[0] = "Hello there, young man. Are you going towards the Badlands?";
//        dialogues[1] = "If you are, I must warn you that not many make it back to tell the tale.";
//        dialogues[2] = "But what would I know, I've scarcely left these kingdom walls my entire life!";
//        dialogues[3] = "Going East, lad? If you are, I'm sure you already know what evil awaits you there.";
//        dialogues[4] = "I only know one man who lives out yonder, he was a blacksmith named Alistair.";
//        dialogues[5] = "Who knows if the man is still a blacksmith, or still alive for that matter,";
//        dialogues[6] = "but in any case, keep an eye out for him. There's no way you'll survive out";
//        dialogues[7] = "there practicing fisticuffs - you'll need a real weapon, perhaps a longsword.";
    }
    public void speak(){
//        if (dialogues[dialogueIndex] == null) {
//            dialogueIndex = 0;
//        }
//            gp.ui.currentDialogue = dialogues[dialogueIndex];
//            dialogueIndex++;

    }
    public void setAction(){

        for (Entity mon : gp.monsters){
            if (mon != null) {
                solidArea.x = worldX + solidAreaDefaultX;
                solidArea.y = worldY + solidAreaDefaultY;
                mon.solidArea.x = mon.worldX + mon.solidAreaDefaultX;
                mon.solidArea.y = mon.worldY + mon.solidAreaDefaultY;

                if (mon.solidArea.intersects(solidArea)) {
                    switch (direction) {
                        case "up" -> direction = "down";
                        case "down" -> direction = "up";
                        case "left" -> direction = "right";
                        case "right" -> direction = "left";
                    }
                }
                solidArea.x = solidAreaDefaultX;
                solidArea.y = solidAreaDefaultY;
                mon.solidArea.x = mon.solidAreaDefaultX;
                mon.solidArea.y = mon.solidAreaDefaultY;
            }
        }

        actionCounter++;

        if (actionCounter > 360) {
            Random random = new Random();
            int i = random.nextInt(100) + 1; // PICK number from 1-100

            if (i <= 25) {
                direction = "up";
            } else if (i <= 50) {
                direction = "down";
            } else if (i <= 75) {
                direction = "left";
            } else direction = "right";
            actionCounter = 0;
        }
    }


    public void getImage(){
//        try {

//            up1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("npc/old_man_up1.png"));
//            up2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("npc/old_man_up2.png"));
////            upWater1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/boy_up_underwater2.png"));
////            upWater2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/boy_up_underwater.png"));
//            down1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("npc/old_man_down1.png"));
//            down2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("npc/old_man_down2.png"));
////            downWater1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/boy_down_underwater2.png"));
////            downWater2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/boy_down_underwater.png"));
//            left1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("/npc/old_man_left1.png"));
//            left2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("/npc/old_man_left2.png"));
////            leftWater1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/boy_left_underwater.png"));
////            leftWater2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/boy_left_underwater2.png"));
//            right1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("/npc/old_man_right1.png"));
//            right2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("/npc/old_man_right2.png"));
////            rightWater1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/boy_right_underwater.png"));
////            rightWater2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/boy_right_underwater2.png"));

            up1 = setup("/npc/old_man_up1", gp.tileSize, gp.tileSize);
            up2 = setup("/npc/old_man_up2", gp.tileSize, gp.tileSize);
//            upWater1 = setup("boy_up_underwater");
//            upWater2 = setup("boy_up_underwater2");
            down1 = setup("/npc/old_man_down1", gp.tileSize, gp.tileSize);
            down2 = setup("/npc/old_man_down2", gp.tileSize, gp.tileSize);
//            downWater1 = setup("boy_down_underwater");
//            downWater2 = setup("boy_down_underwater2");
            left1 = setup("/npc/old_man_left1", gp.tileSize, gp.tileSize);
            left2 = setup("/npc/old_man_left2", gp.tileSize, gp.tileSize);
//            leftWater1 = setup("boy_left_underwater");
//            leftWater2 = setup("boy_left_underwater2");
            right1 = setup("/npc/old_man_right1", gp.tileSize, gp.tileSize);
            right2 = setup("/npc/old_man_right2", gp.tileSize, gp.tileSize);
//            rightWater1 = setup("boy_right_underwater");
//            rightWater2 = setup("boy_right_underwater2");

//        }catch (IOException e) {
//            e.printStackTrace();
//        }
    }

}
