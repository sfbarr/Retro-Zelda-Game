package monster;

import entity.Entity;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class GreenSlime extends Entity {
    GamePanel gp;
    public GreenSlime(GamePanel gp) {
        super(gp);
        name = "Green Slime";
        speed = 1;
        type = 2;
        maxLife = 4;
        life = maxLife;
        this.gp = gp;

        solidArea = new Rectangle(4, 24, 56, 40);
        solidAreaDefaultX = solidArea.x; solidAreaDefaultY = solidArea.y;
        getImage();
    }
    public void getImage(){
        up1 = setup("/monster/greenslime_down_1", gp.tileSize, gp.tileSize);
        upWater1 = setup("/monster/greenslime_down_1_underwater", gp.tileSize, gp.tileSize);
        up2 = setup("/monster/greenslime_down_2", gp.tileSize, gp.tileSize);
        upWater2 = setup("/monster/greenslime_down_2_underwater", gp.tileSize, gp.tileSize);
        down1 = setup("/monster/greenslime_down_1", gp.tileSize, gp.tileSize);
        downWater1 = setup("/monster/greenslime_down_1_underwater", gp.tileSize, gp.tileSize);
        down2 = setup("/monster/greenslime_down_2", gp.tileSize, gp.tileSize);
        downWater2 = setup("/monster/greenslime_down_2_underwater", gp.tileSize, gp.tileSize);
        left1 = setup("/monster/greenslime_down_1", gp.tileSize, gp.tileSize);
        leftWater1 = setup("/monster/greenslime_down_1_underwater", gp.tileSize, gp.tileSize);
        left2 = setup("/monster/greenslime_down_2", gp.tileSize, gp.tileSize);
        leftWater2 = setup("/monster/greenslime_down_2_underwater", gp.tileSize, gp.tileSize);
        right1 = setup("/monster/greenslime_down_1", gp.tileSize, gp.tileSize);
        rightWater1 = setup("/monster/greenslime_down_1_underwater", gp.tileSize, gp.tileSize);
        right2 = setup("/monster/greenslime_down_2", gp.tileSize, gp.tileSize);
        rightWater2 = setup("/monster/greenslime_down_2_underwater", gp.tileSize, gp.tileSize);
        death1 = setup("/monster/greenslime_dead_1", gp.tileSize, gp.tileSize);
        death2 = setup("/monster/greenslime_dead_2", gp.tileSize, gp.tileSize);
        death3 = setup("/monster/greenslime_dead_3", gp.tileSize, gp.tileSize);
    }
    public void setAction() {

        for (Entity npc : gp.npc){
            if (npc != null) {
                solidArea.x = worldX + solidAreaDefaultX;
                solidArea.y = worldY + solidAreaDefaultY;
                npc.solidArea.x = npc.worldX + npc.solidAreaDefaultX;
                npc.solidArea.y = npc.worldY + npc.solidAreaDefaultY;

                if (npc.solidArea.intersects(solidArea)) {
                    switch (direction) {
                        case "up" -> direction = "down";
                        case "down" -> direction = "up";
                        case "left" -> direction = "right";
                        case "right" -> direction = "left";
                    }
                }
                solidArea.x = solidAreaDefaultX;
                solidArea.y = solidAreaDefaultY;
                npc.solidArea.x = npc.solidAreaDefaultX;
                npc.solidArea.y = npc.solidAreaDefaultY;
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
    public void dyingAnimation(){
        deathCount ++;

        direction = "down";
        spriteNum = 1;

        speed = 0;
        underwater = false;

        if (deathCount <= 10){
            down1 = death1;
        } else if (deathCount <= 20){
            down1 = death2;
        } else {
            down1 = death3;
        }
    }
}
