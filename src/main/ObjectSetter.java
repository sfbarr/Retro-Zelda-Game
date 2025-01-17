package main;

import entity.OldMan;
import monster.GreenSlime;
import object.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;


public class ObjectSetter {
    GamePanel gp;
    UtilityTool utool;
    int numb = 1;
    int floatCount, floatOffset = 0;

    ObjectSetter(GamePanel gp){

        this.gp = gp;
        utool = new UtilityTool();
    }
    public void setObject(int i) {
        if (i == 1) {
            gp.obj[1] = new Portal(gp);
            gp.obj[1].worldX = 42 * gp.tileSize;
            gp.obj[1].worldY = 6 * gp.tileSize;
            gp.obj[1].direction = "down";

            gp.obj[2] = new Door(gp);
            gp.obj[2].worldX = 23 * gp.tileSize;
            gp.obj[2].worldY = 10 * gp.tileSize;

            gp.obj[3] = new Sword( 22 * gp.tileSize, gp);
            gp.obj[3].worldX = 42 * gp.tileSize;
            gp.obj[3].worldY = 22 * gp.tileSize;


        } else if (i == 2) {
            gp.obj[1] = new Portal(gp);
            gp.obj[1].worldX = 44 * gp.tileSize;
            gp.obj[1].worldY = 7 * gp.tileSize;

        }
    }
    public void updateFloating(){

        // NOTE: This could be a waste of RAM / CPU / memory
        for (int i = 0; i < gp.obj.length; i++) {
            if (gp.obj[i] != null && gp.obj[i].floating) {

                floatOffset = (int) (16 * Math.sin( (Math.PI * floatCount) /30));
                gp.obj[i].worldY = gp.obj[i].baseY - floatOffset;

                floatCount++;
                if (floatCount > 60){
                    floatCount = 0;
                }

            }
        }
    }
    public void updatePortal(){
        for (int i = 0; i < gp.obj.length; i++) {
            if (gp.obj[i] != null && Objects.equals(gp.obj[i].name, "Portal")) {
                try {
                    BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/objects/portal" + numb + ".png"));
                    gp.obj[i].down1 = utool.scaleImage(image, gp.tileSize, gp.tileSize);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (numb == 6){ numb = 0; }
                numb ++;

            }
        }
    }

    // Replaces previous closed door index with open door (using saved coordinates & openDoor.png)
    public void openDoor(int index, boolean side){
        int x = gp.obj[index].worldX;
        int y = gp.obj[index].worldY;
        gp.obj[index] = (side)? new OpenSideDoor(gp) : new OpenDoor(gp);
        gp.obj[index].worldX = x;
        gp.obj[index].worldY = y;
    }
    public void clearObjects(){
        for (int i = 0; i < gp.obj.length; i++){
            if (gp.obj[i] != null) {
                gp.obj[i] = null;
            }
        }
    }

    public void setNPC(){

        gp.npc[0] = new OldMan(gp);
        gp.npc[0].worldX = gp.tileSize * 23;
        gp.npc[0].worldY = gp.tileSize * 14;
        gp.npc[0].dialogues = new String[]{
                "Hello there, young man. Are you going towards the Badlands?",
                "If you are, I must warn you that not many make it back to tell the tale.",
                "But what would I know, I've scarcely left these kingdom walls my entire life!",
                "Going East, lad? If you are, I'm sure you already know what evil awaits you there.",
                "I only know one man who lives out yonder, he was a blacksmith named Alistair.",
                "Who knows if the man is still a blacksmith, or still alive for that matter,",
                "but in any case, keep an eye out for him. There's no way you'll survive out",
                "there practicing fisticuffs - you'll need a real weapon, perhaps a longsword.",
        };

        gp.npc[1] = new OldMan(gp);
        gp.npc[1].worldX = gp.tileSize * 23;
        gp.npc[1].worldY = gp.tileSize * 21;
        gp.npc[1].dialogues = new String[]{
                "Greetings, lad. You must be the Traveler I've been hearing about.",
                "What far away lands are you off to? .................... THE BADLANDS?",
                "Young man, I'll admit that I've gotten a bit cautious in my old age",
                "when it comes to adventures in faraway lands, but this?",
                "This isn't just an adventure, no. This is a death sentence you're talking about!",
                "I mean, why else would they call them the Badlands? The first part of that name",
                "is literally BAD! Okay... I think I've made my point. You can't save everyone,",
                "I suppose. Well, if you really are going out there, get yourself a sword ASAP!",
        };
    }
    public void setMonster(){
        gp.monsters[0] = new GreenSlime(gp);
        gp.monsters[0].worldX = 20 * gp.tileSize;
        gp.monsters[0].worldY = 6 * gp.tileSize;

        gp.monsters[1] = new GreenSlime(gp);
        gp.monsters[1].worldX = 21 * gp.tileSize;
        gp.monsters[1].worldY = 2 * gp.tileSize;
    }

}
