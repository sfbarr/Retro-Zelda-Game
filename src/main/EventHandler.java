package main;

import org.w3c.dom.css.Rect;

import java.awt.*;
import java.util.Objects;

public class EventHandler {
    GamePanel gp;
    EventRect[][] eventRect;

    int previousEventX, previousEventY;
    boolean canTouchEvent = true;


    boolean lifeTaken, lifeHealed, teleported = false;

    public EventHandler(GamePanel gp){
        this.gp = gp;

        eventRect = new EventRect[gp.maxWorldRow][gp.maxWorldCol];

        int row = 0;
        int col = 0;

        while (row < gp.maxWorldRow && col < gp.maxWorldCol){
            eventRect[row][col] = new EventRect();
            eventRect[row][col].x = eventRect[row][col].y = eventRect[row][col].eventRectDefaultX = eventRect[row][col].eventRectDefaultY = ((gp.tileSize/2) - 16);
            eventRect[row][col].width = eventRect[row][col].height = 32;

            col ++;
            if (col == gp.maxWorldCol){
                col = 0;
                row++;
            }
        }


    }
    public void checkEvent(){

        // Check if player is more than 1 tile away from last event

        int xDistance = Math.abs(gp.player.worldX - previousEventX);
        int yDistance = Math.abs(gp.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);
        if (distance >= gp.tileSize){
            canTouchEvent = true;
        }
        if (canTouchEvent) {
            if (hit(29, 29, "any")) {
                damagePit(29, 29);
            }

            if (hit(6, 23, "any")) {
                healingPool(6, 23);
            }
            if (hit(5, 20, "any")) {
                telePortal(5, 20, 6, 23);
            }
//            if (!swordObtained){
//                if (hit(22, 42, "any")) {
//                    obtainSword();
//                }
//            }
        }
    }
    public boolean hit(int row, int col, String reqDirection) {
        boolean hit = false;

        // GETTING worldX of PLAYER HIT BOX (Player X + Hit-box X)
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;

        // Getting worldX of event Rectangle
        eventRect[row][col].x = col * gp.tileSize + eventRect[row][col].x;
        eventRect[row][col].y = row * gp.tileSize + eventRect[row][col].y;
        // Checking if Player Hit Box collides with eventRect solid area
        // BIG NOTE: Previous iteration included ""&& !eventRect[row][col].eventDone"" in this condition!
        // Removing it allows the hit method to return true even when the event is done, leaving the decision to the callee
        if (gp.player.solidArea.intersects(eventRect[row][col]) && !eventRect[row][col].eventDone) {
            if (gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")){
                hit = true;
                System.out.println("intersects AND Event *Not* Done");

                previousEventX = gp.player.worldX;
                previousEventY = gp.player.worldY;
            }
        }
//        else if (!gp.player.solidArea.intersects(eventRect[row][col]) && eventRect[row][col].eventDone) {
//            eventRect[row][col].eventDone = false;
//            System.out.println("doesn't intersect, and event done. Resetting to event NOT done");
//        }
        // Resetting hit box X & Y values.
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventRect[row][col].x = eventRect[row][col].eventRectDefaultX;
        eventRect[row][col].y = eventRect[row][col].eventRectDefaultY;
        return hit;
    }
    public void damagePit(int row, int col){
        gp.ui.messageOn = true;
        gp.ui.message = "You just fell into a pit!";
        gp.player.life --;
        canTouchEvent = false;

//        eventRect[row][col].eventDone = true;
        System.out.println("DMG EVENT DONE");
        System.out.println(System.currentTimeMillis() - 1000000000);
        System.out.println(gp.player.life);
    }
    public void healingPool(int row, int col){
        gp.ui.messageOn = true;
        gp.ui.message = "Press [ENTER] to Drink";
        gp.ui.messageCounter = 140;

        if (gp.keyH.enterPressed) {
            gp.ui.messageCounter = 0;
            gp.ui.message = "You drank from the fairy fountain... you've been healed!";
            gp.player.life = gp.player.maxLife;
            canTouchEvent = false;
//            eventRect[row][col].eventDone = true;
        }

    }
    public void telePortal(int row, int col ,int destinationRow, int destinationCol){

        gp.ui.messageOn = true;
        gp.ui.message = "Press [ENTER] to use the Tele-Portal";
        gp.ui.messageCounter = 140;
        if (gp.keyH.enterPressed) {
            gp.ui.messageCounter = 0;
            gp.ui.message = "You fell into a wormhole... Where are you now?";
            gp.player.worldX = destinationCol * gp.tileSize + gp.tileSize/2;
            gp.player.worldY = destinationRow * gp.tileSize + gp.tileSize/2;
            canTouchEvent = false;
//            eventRect[row][col].eventDone = true;
        }

    }

//    public void obtainSword(){
//        NOTE: moved to --> pickUpObject() method in Player.java
//    }


//    public void resetEvent(){
//        lifeTaken = false;
//        lifeHealed = false;
//        teleported = false;
//    }
//    public void dmgEvent(int row, int col, String direction, int type){
//        if (type == 1){
//            if (hit(row,col, direction)){
//                if (!eventRect[row][col].eventDone) {
//                    damagePit(row, col);
//                }
//            } else { eventRect[row][col].eventDone = false; }
//        } else if (type == 2) {
//            if (hit(row,col, direction)){
//                if (!eventRect[row][col].eventDone){
//                    healingPool(row,col);
//                }
//            } else { eventRect[row][col].eventDone = false; }
//        }
//    }
}
