package main;

import entity.Entity;
import entity.Player;
import object.Chest;
import object.Door;
import object.Key;
import object.SuperObject;

import java.util.ArrayList;

public class CollisionChecker {
    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }


    // NOTE: TESTING FOR ENEMY TEST, MAY DELETE LATER


//    public void checkEnemyTile(Entity enemy){
//
//        int enemyLeftWorldX = enemy.worldX + enemy.solidArea.x;
//        int enemyRightWorldX = enemy.worldX + enemy.solidArea.x + enemy.solidArea.width;
//        int enemyTopWorldY = enemy.worldY + enemy.solidArea.y;
//        int enemyBottomWorldY = enemy.worldY + enemy.solidArea.y + enemy.solidArea.height;
//
//        int enemyLeftCol = enemyLeftWorldX/gp.tileSize;
//        int enemyRightCol = enemyRightWorldX/gp.tileSize;
//        int enemyTopRow = enemyTopWorldY/gp.tileSize;
//        int enemyBottomRow = enemyBottomWorldY/gp.tileSize;
//
//        int tileNum1, tileNum2, tileNum3, tileNum4;
//
//        switch(enemy.direction){
//            case "up":
//                enemyTopRow = (enemyTopWorldY - enemy.speed)/gp.tileSize;
//                tileNum1 = gp.tileM.mapTileNum[enemyTopRow][enemyLeftCol];
//                tileNum2 = gp.tileM.mapTileNum[enemyTopRow][enemyRightCol];
//                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision){
//
//                    if (!gp.tileM.tile[ gp.tileM.mapTileNum[(enemy.worldY + gp.tileSize/2)/gp.tileSize][(enemy.worldX - gp.tileSize)/gp.tileSize]].collision && enemy.worldX > 0) {
//                        enemy.worldX -= enemy.speed;
//                    } else if (enemy.worldX < gp.worldWidth) {
//                        enemy.worldX += enemy.speed;
//                    }
//                    enemy.collisionOn = true;
//
//                } else {enemy.collisionOn = false;}
//
//                // if (gp.tileM.mapTileNum[enemy.worldY+24/gp.tileSize][enemy.worldX+24/gp.tileSize] == 2)
////                Another way to figure out if Tile is SOLID or not, use Tile #
////                if ((tileNum1 == 1 || tileNum1 == 2) || (tileNum2 == 1 || tileNum2 == 2)) {
////                    enemy.collisionOn = true;  }
//                break;
//            case "down":
//                enemyBottomRow = (enemyBottomWorldY + enemy.speed)/ gp.tileSize;
//                tileNum1 = gp.tileM.mapTileNum[enemyBottomRow][enemyLeftCol];
//                tileNum2 = gp.tileM.mapTileNum[enemyBottomRow][enemyRightCol];
//                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision){
//
//                    if (!gp.tileM.tile[ gp.tileM.mapTileNum[(enemy.worldY + gp.tileSize/2)/gp.tileSize][(enemy.worldX - gp.tileSize/2)/gp.tileSize]].collision && enemy.worldX > 0) {
//                        enemy.worldX -= enemy.speed;
//                    } else if (enemy.worldX < gp.worldWidth && !gp.tileM.tile[gp.tileM.mapTileNum[(enemy.worldY + gp.tileSize/2)/gp.tileSize][(enemy.worldX + gp.tileSize/2)/gp.tileSize]].collision){
//                        enemy.worldX += enemy.speed;
//                    }
//                    enemy.collisionOn = true;
//                } else {enemy.collisionOn = false;}
//
//                break;
//            case "left":
//                enemyLeftCol = (enemyLeftWorldX - enemy.speed)/ gp.tileSize;
//                tileNum1 = gp.tileM.mapTileNum[enemyTopRow][enemyLeftCol];
//                tileNum2 = gp.tileM.mapTileNum[enemyBottomRow][enemyLeftCol];
//                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision){
//                    if (!gp.tileM.tile[ gp.tileM.mapTileNum[(enemy.worldY - 32)/gp.tileSize][(enemy.worldX)/gp.tileSize]].collision && enemy.worldY > 0) {
//                        enemy.worldY -= enemy.speed;
//                    } else if (enemy.worldY < gp.worldHeight) {
//                        enemy.worldY += enemy.speed;
//                    }
//                    enemy.collisionOn = true;
//                } else {enemy.collisionOn = false;}
//
//                break;
//            case "right":
//                enemyRightCol = (enemyRightWorldX + enemy.speed)/ gp.tileSize;
//                tileNum1 = gp.tileM.mapTileNum[enemyTopRow][enemyRightCol];
//                tileNum2 = gp.tileM.mapTileNum[enemyBottomRow][enemyRightCol];
//                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision){
//                    if (!gp.tileM.tile[ gp.tileM.mapTileNum[(enemy.worldY - 32)/gp.tileSize][(enemy.worldX + 32)/gp.tileSize]].collision && enemy.worldY > 0) {
//                        enemy.worldY -= enemy.speed;
//                    } else if (enemy.worldY < gp.worldHeight) {
//                        enemy.worldY += enemy.speed;
//                    }
//                    enemy.collisionOn = true;
//                } else {enemy.collisionOn = false;}
//                break;
//            case "northWest":
//                enemyTopRow = (enemyTopWorldY - enemy.speed)/gp.tileSize;
//                enemyLeftCol = (enemyLeftWorldX - enemy.speed)/gp.tileSize;
//                tileNum1 = gp.tileM.mapTileNum[enemyBottomRow][enemyLeftCol];
//                tileNum3 = gp.tileM.mapTileNum[enemyTopRow][enemyRightCol];
//                tileNum2 = gp.tileM.mapTileNum[(enemyBottomWorldY + enemy.speed)/gp.tileSize][enemyLeftWorldX/gp.tileSize];
//                tileNum4 = gp.tileM.mapTileNum[enemyTopWorldY/gp.tileSize][(enemyRightWorldX + enemy.speed)/gp.tileSize];
//
//                if (gp.tileM.tile[tileNum1].collision && !gp.tileM.tile[tileNum3].collision){
//                    enemy.collisionOn = true;
//                    if (enemy.worldY > gp.tileSize) {
//                        enemy.worldY -= enemy.speed;
//                    } else {
//                        enemy.direction = "down";
//                        enemy.worldY += enemy.speed;
//                    }
//                    System.out.println("Collision = true");
//                } else if (gp.tileM.tile[tileNum3].collision  && !gp.tileM.tile[tileNum1].collision){
//                    enemy.collisionOn = true;
//                    if (enemy.worldX > gp.tileSize) {
//                        enemy.worldX -= enemy.speed;
//                    } else {
//                        enemy.direction = "right";
//                        enemy.worldX += enemy.speed;
//                    }
//                    System.out.println("Collision = true");
//                } else if (gp.tileM.tile[tileNum3].collision  && gp.tileM.tile[tileNum1].collision) {
//                    enemy.collisionOn = true;
//                    enemy.direction = "southEast";
//                    if (!gp.tileM.tile[tileNum2].collision) { enemy.worldY += enemy.speed + 1;}
//                    if (!gp.tileM.tile[tileNum4].collision) { enemy.worldX += enemy.speed + 1;}
//                    System.out.println("collision = true!!!  Changing directions!");
//                } else {
//                    enemy.collisionOn = false;
//                }
//                break;
//            case "northEast":
//                enemyTopRow = (enemyTopWorldY - enemy.speed)/gp.tileSize;
//                enemyRightCol = (enemyRightWorldX + enemy.speed)/gp.tileSize;
//
//                // NOTE: Reset to 49 just in case index goes out of bounds from previous calculation
//                // enemyRightCol = (enemyRightCol == 50) ? 49: enemyRightCol;
//
//                tileNum1 = gp.tileM.mapTileNum[enemyTopRow][enemyLeftCol];
//                tileNum3 = gp.tileM.mapTileNum[enemyBottomRow][enemyRightCol];
//                tileNum2 = gp.tileM.mapTileNum[(enemyBottomWorldY + enemy.speed)/gp.tileSize][enemyRightWorldX/gp.tileSize];
//                tileNum4 = gp.tileM.mapTileNum[enemyTopWorldY/gp.tileSize][(enemyLeftWorldX - enemy.speed)/gp.tileSize];
//
//                if (gp.tileM.tile[tileNum1].collision && !gp.tileM.tile[tileNum3].collision){
//                    enemy.collisionOn = true;
//                    if (enemy.worldX < gp.worldWidth - gp.tileSize * 2) {
//                        enemy.worldX += enemy.speed;
//                    } else {
//                        enemy.direction = "left";
//                        enemy.worldX -= enemy.speed;
//                    }
//                    System.out.println("tile Collision = true");
//                } else if (gp.tileM.tile[tileNum3].collision  && !gp.tileM.tile[tileNum1].collision){
//                    enemy.collisionOn = true;
//                    if (enemy.worldY > gp.tileSize + enemy.speed) {
//                        enemy.worldY -= enemy.speed;
//                    } else {
//                        enemy.direction = "down";
//                        enemy.worldY += enemy.speed;
//                    }
//                    System.out.println("tile Collision = true");
//                } else if (gp.tileM.tile[tileNum3].collision  && gp.tileM.tile[tileNum1].collision) {
//                    enemy.collisionOn = true;
//                    enemy.direction = "southWest";
//                    if (!gp.tileM.tile[tileNum2].collision) { enemy.worldY += enemy.speed + 1;}
//                    if (!gp.tileM.tile[tileNum4].collision) { enemy.worldX -= enemy.speed + 1;}
//                    System.out.println("collision = true!!! Changing directions!");
//                } else {
//                    enemy.collisionOn = false;
//                }
//                break;
//            case "southWest":
//                enemyBottomRow = (enemyBottomWorldY + enemy.speed)/gp.tileSize;
//                enemyLeftCol = (enemyLeftWorldX - enemy.speed)/gp.tileSize;
//
//                tileNum1 = gp.tileM.mapTileNum[enemyTopRow][enemyLeftCol];
//                tileNum3 = gp.tileM.mapTileNum[enemyBottomRow][enemyRightCol];
//                tileNum2 = gp.tileM.mapTileNum[(enemyTopWorldY - enemy.speed)/gp.tileSize][enemyLeftWorldX/gp.tileSize];
//                tileNum4 = gp.tileM.mapTileNum[enemyBottomWorldY/gp.tileSize][(enemyRightWorldX + enemy.speed)/gp.tileSize];
//
//                if (gp.tileM.tile[tileNum1].collision && !gp.tileM.tile[tileNum3].collision){
//                    enemy.collisionOn = true;
//                    if (enemy.worldY < gp.worldHeight - enemy.speed) {
//                        enemy.worldY += enemy.speed;
//                    } else {
//                        enemy.direction = "up";
//                        enemy.worldY -= enemy.speed;
//                    }
//                    System.out.println("tile Collision = true");
//                } else if (gp.tileM.tile[tileNum3].collision  && !gp.tileM.tile[tileNum1].collision){
//                    enemy.collisionOn = true;
//                    if (enemy.worldX > enemy.speed * 2) {
//                        enemy.worldX -= enemy.speed;
//                    } else {
//                        enemy.direction = "right";
//                        enemy.worldX += enemy.speed;
//                    }
//                    System.out.println("tile Collision = true");
//                }  else if (gp.tileM.tile[tileNum3].collision  && gp.tileM.tile[tileNum1].collision) {
//                    enemy.collisionOn = true;
//                    enemy.direction = "northEast";
//                    if (!gp.tileM.tile[tileNum2].collision) { enemy.worldY -= enemy.speed + 1;}
//                    if (!gp.tileM.tile[tileNum4].collision) { enemy.worldX += enemy.speed + 1;}
//                    System.out.println("collision = true!!!  Changing directions! ");
//                } else {
//                    enemy.collisionOn = false;
//                }
//
//                break;
//            case "southEast":
//                enemyBottomRow = (enemyBottomWorldY + enemy.speed)/gp.tileSize;
//                enemyRightCol = (enemyRightWorldX + enemy.speed)/gp.tileSize;
//
//                tileNum1 = gp.tileM.mapTileNum[enemyBottomRow][enemyLeftCol];
//                tileNum3 = gp.tileM.mapTileNum[enemyTopRow][enemyRightCol];
//                tileNum2 = gp.tileM.mapTileNum[(enemyTopWorldY - enemy.speed)/gp.tileSize][enemyRightWorldX/gp.tileSize];
//                tileNum4 = gp.tileM.mapTileNum[enemyBottomWorldY/gp.tileSize][(enemyLeftWorldX - enemy.speed)/gp.tileSize];
//
//                if (gp.tileM.tile[tileNum1].collision && !gp.tileM.tile[tileNum3].collision){
//                    enemy.collisionOn = true;
//                    if (enemy.worldX < gp.worldWidth - gp.tileSize * 2) {
//                        enemy.worldX += enemy.speed;
//                    } else {
//                        enemy.direction = "left";
//                        enemy.worldX -= enemy.speed;
//                    }
//                    System.out.println("tile Collision = true");
//                } else if (gp.tileM.tile[tileNum3].collision  && !gp.tileM.tile[tileNum1].collision){
//                    enemy.collisionOn = true;
//                    if (enemy.worldY < gp.worldHeight - gp.tileSize * 2) {
//                        enemy.worldY += enemy.speed;
//                    } else {
//                        enemy.direction = "up";
//                        enemy.worldY -= enemy.speed;
//                    }
//                    System.out.println("tile Collision = true");
//                } else if (gp.tileM.tile[tileNum3].collision  && gp.tileM.tile[tileNum1].collision) {
//                    enemy.collisionOn = true;
//                    enemy.direction = "northWest";
//                    if (!gp.tileM.tile[tileNum2].collision) { enemy.worldY -= enemy.speed + 1;}
//                    if (!gp.tileM.tile[tileNum4].collision) { enemy.worldX -= enemy.speed + 1;}
//                    System.out.println("collision = true!!!, Changing directions! ");
//                } else {
//                    enemy.collisionOn = false;
//                }
//                break;
//        }
//
//    }
//        // Note: End of enemy test ^^


    public void checkTile(Entity entity) {

        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityMiddleWorldX = entity.worldX + gp.tileSize / 2;

        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;


        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityMiddleCol = entityMiddleWorldX / gp.tileSize;

        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        int tileNum1, tileNum2, tileNum3;
//        if (entity == gp.player) {
//            System.out.println("World Y: " + gp.player.worldY);
//            System.out.println("Top hitbox: " + entityTopWorldY);
//            System.out.println("Bottom hitbox: " + entityBottomWorldY);
//
//
//            tileNum1 = gp.tileM.mapTileNum[entityBottomRow][entityMiddleCol];
//            if (gp.tileM.tile[tileNum1].underwater){
//                gp.player.underwater = true;
//                System.out.println(tileNum1);
//                System.out.println("UNDERWATER UNDERWATER!");
//
//                //NOTE: FINISH UNDERWATER BUG FIX (Using player.underH20 v.s. Entity.underH20, making ent.h20 public)
//                // (cont.) After, add images for MONSTER & fix the collision issue.

        // Done... I think? I made the underwater method for all entities.

//            } else { gp.player.underwater = false; }
//
//        }

        switch (entity.direction) {
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityTopRow][entityLeftCol];
                tileNum2 = gp.tileM.mapTileNum[entityTopRow][entityRightCol];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                // if (gp.tileM.mapTileNum[entity.worldY+24/gp.tileSize][entity.worldX+24/gp.tileSize] == 2)
//                Another way to figure out if Tile is SOLID or not, use Tile #
//                if ((tileNum1 == 1 || tileNum1 == 2) || (tileNum2 == 1 || tileNum2 == 2)) {
//                    entity.collisionOn = true;  }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityBottomRow][entityLeftCol];
                tileNum2 = gp.tileM.mapTileNum[entityBottomRow][entityRightCol];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityTopRow][entityLeftCol];
                tileNum2 = gp.tileM.mapTileNum[entityBottomRow][entityLeftCol];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityTopRow][entityRightCol];
                tileNum2 = gp.tileM.mapTileNum[entityBottomRow][entityRightCol];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "northWest":
                entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityBottomRow][entityLeftCol];
                tileNum2 = gp.tileM.mapTileNum[entityTopRow][entityLeftCol];
                tileNum3 = gp.tileM.mapTileNum[entityTopRow][entityRightCol];

//                if (gp.tileM.tile[tileNum1].collision && !gp.tileM.tile[tileNum3].collision){
//                    entity.direction = "up";
//                } else if (gp.tileM.tile[tileNum3].collision && !gp.tileM.tile[tileNum1].collision){
//                entity.direction = "left";
//            }

                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision
                        || gp.tileM.tile[tileNum3].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "northEast":
                entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
                entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;

                // NOTE: Reset to 49 just in case index goes out of bounds from previous calculation
                // entityRightCol = (entityRightCol == 50) ? 49: entityRightCol;

                tileNum1 = gp.tileM.mapTileNum[entityTopRow][entityLeftCol];
                tileNum2 = gp.tileM.mapTileNum[entityTopRow][entityRightCol];
                tileNum3 = gp.tileM.mapTileNum[entityBottomRow][entityRightCol];

//                if (gp.tileM.tile[tileNum1].collision && !gp.tileM.tile[tileNum3].collision){
//                    entity.direction = "right";
//                } else if (gp.tileM.tile[tileNum3].collision && !gp.tileM.tile[tileNum1].collision){
//                    entity.direction = "up";
//                }

                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision
                        || gp.tileM.tile[tileNum3].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "southWest":
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;

                tileNum1 = gp.tileM.mapTileNum[entityTopRow][entityLeftCol];
                tileNum2 = gp.tileM.mapTileNum[entityBottomRow][entityLeftCol];
                tileNum3 = gp.tileM.mapTileNum[entityBottomRow][entityRightCol];
//                if (gp.tileM.tile[tileNum1].collision && !gp.tileM.tile[tileNum3].collision){
//                    entity.direction = "down";
//                } else if (gp.tileM.tile[tileNum3].collision && !gp.tileM.tile[tileNum1].collision){
//                    entity.direction = "left";
//                }

                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision
                        || gp.tileM.tile[tileNum3].collision) {
                    entity.collisionOn = true;
                }

                break;
            case "southEast":
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
                entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;

                tileNum1 = gp.tileM.mapTileNum[entityBottomRow][entityLeftCol];
                tileNum2 = gp.tileM.mapTileNum[entityBottomRow][entityRightCol];
                tileNum3 = gp.tileM.mapTileNum[entityTopRow][entityRightCol];

//                if (gp.tileM.tile[tileNum1].collision && !gp.tileM.tile[tileNum3].collision){
//                    entity.direction = "right";
//                } else if (gp.tileM.tile[tileNum3].collision && !gp.tileM.tile[tileNum1].collision){
//                    entity.direction = "down";
//                }

                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision
                        || gp.tileM.tile[tileNum3].collision) {
                    entity.collisionOn = true;
                }
                break;
        }

    }

    public int checkObject(Entity entity, boolean player) {
        int index = 999;

        for (int i = 0; i < gp.obj.length; i++) {
            if (gp.obj[i] != null) {

                // Get entity's solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;
                // Get the object's solid area position
                gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
                gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;

                switch (entity.direction) {
                    case "up" -> entity.solidArea.y -= entity.speed;
                    case "down" -> entity.solidArea.y += entity.speed;
                    case "left" -> entity.solidArea.x -= entity.speed;
                    case "right" -> entity.solidArea.x += entity.speed;
                    case "northWest" -> {
                        entity.solidArea.y -= entity.speed;
                        entity.solidArea.x -= entity.speed;
                    }
                    case "northEast" -> {
                        entity.solidArea.y -= entity.speed;
                        entity.solidArea.x += entity.speed;
                    }
                    case "southWest" -> {
                        entity.solidArea.y += entity.speed;
                        entity.solidArea.x -= entity.speed;
                    }
                    case "southEast" -> {
                        entity.solidArea.y += entity.speed;
                        entity.solidArea.x += entity.speed;
                    }
                }
                if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                    if (gp.obj[i].collision) {
                        entity.collisionOn = true;
                    }
                    if (player) {
                        index = i;
                    }

                }


                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
                gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
            }
        }
        return index;
    }

    // NPC or Monsters only
    public int checkEntity(Entity entity, Entity[] target) {
        int index = 999;

        for (int i = 0; i < target.length; i++) {
            if (target[i] != null && target[i] != entity) {
                // Get player's solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;
                // Get the NPC's solid area position
                target[i].solidArea.x = target[i].worldX + target[i].solidArea.x;
                target[i].solidArea.y = target[i].worldY + target[i].solidArea.y;

                // Checking where CALLER *will* be
                switch (entity.direction) {
                    case "up" -> entity.solidArea.y -= entity.speed;
                    case "down" -> entity.solidArea.y += entity.speed;
                    case "left" -> entity.solidArea.x -= entity.speed;
                    case "right" -> entity.solidArea.x += entity.speed;
                    case "northWest" -> {
                        entity.solidArea.y -= entity.speed;
                        entity.solidArea.x -= entity.speed;
                    }
                    case "northEast" -> {
                        entity.solidArea.y -= entity.speed;
                        entity.solidArea.x += entity.speed;
                    }
                    case "southWest" -> {
                        entity.solidArea.y += entity.speed;
                        entity.solidArea.x -= entity.speed;
                    }
                    case "southEast" -> {
                        entity.solidArea.y += entity.speed;
                        entity.solidArea.x += entity.speed;
                    }
                }
                if (entity.solidArea.intersects(target[i].solidArea)) {
                    entity.collisionOn = true;
                    index = i;
                }

                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                target[i].solidArea.x = target[i].solidAreaDefaultX;
                target[i].solidArea.y = target[i].solidAreaDefaultY;
            }
        }
        return index;
    }

    public boolean checkPlayer(Entity entity) {

        boolean playerContact = false;

        // Get entity's solid area position
        entity.solidArea.x = entity.worldX + entity.solidArea.x;
        entity.solidArea.y = entity.worldY + entity.solidArea.y;
        // Get the player's solid area position
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;

        // Affects NON-PLAYER entity's movement
        switch (entity.direction) {
            case "up" -> entity.solidArea.y -= entity.speed;
            case "down" -> entity.solidArea.y += entity.speed;
            case "left" -> entity.solidArea.x -= entity.speed;
            case "right" -> entity.solidArea.x += entity.speed;
            case "northWest" -> {
                entity.solidArea.y -= entity.speed;
                entity.solidArea.x -= entity.speed;
            }
            case "northEast" -> {
                entity.solidArea.y -= entity.speed;
                entity.solidArea.x += entity.speed;
            }
            case "southWest" -> {
                entity.solidArea.y += entity.speed;
                entity.solidArea.x -= entity.speed;
            }
            case "southEast" -> {
                entity.solidArea.y += entity.speed;
                entity.solidArea.x += entity.speed;
            }
        }
        if (entity.solidArea.intersects(gp.player.solidArea)) {
            entity.playerCollisionOn = true;
            playerContact = true;
        }

        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;

        return playerContact;
    }

    public void checkWater(Entity entity){
        int playerMiddleCol = (entity.worldX + (gp.tileSize/2)) / gp.tileSize;
        int playerMiddleRow = (entity.worldY + (gp.tileSize/2)) / gp.tileSize;

        int tn;

        if (entity.worldY + (gp.tileSize * 2) < gp.worldHeight && entity.worldX + (gp.tileSize * 2) < gp.worldWidth) {

            int playerTopRow = (entity.worldY + (gp.tileSize / 4)) / gp.tileSize;
            int playerBottomRow = (entity.worldY + (gp.tileSize * 9 / 8)) / gp.tileSize;
            int playerLeftCol = (entity.worldX + (gp.tileSize/ 4)) / gp.tileSize;
            int playerRightCol = (entity.worldX + (gp.tileSize * 3/4)) / gp.tileSize;

            switch (entity.direction) {
                case "up", "down" -> {
                    // If water tile south of player, they are on the north shore.
                    if (gp.tileM.tile[gp.tileM.mapTileNum[(entity.worldY + (gp.tileSize * 2)) / gp.tileSize][playerMiddleCol]].underwater) {
                        tn = gp.tileM.mapTileNum[playerTopRow][playerMiddleCol];
                    } else {
                        tn = gp.tileM.mapTileNum[playerBottomRow][playerMiddleCol];
                    }
                    entity.underwater = gp.tileM.tile[tn].underwater;
                }
                case "left", "right" -> {
                    // If tile just East of player is water, they are on the Western shore.
                    if (gp.tileM.tile[gp.tileM.mapTileNum[playerMiddleRow][(entity.worldX + (gp.tileSize * 2)) / gp.tileSize]].underwater) {
                        tn = gp.tileM.mapTileNum[playerMiddleRow][playerLeftCol];
                    } else {
                        tn = gp.tileM.mapTileNum[playerMiddleRow][playerRightCol];
                    }
                    entity.underwater = gp.tileM.tile[tn].underwater;
                }
                default -> {
                    tn = gp.tileM.mapTileNum[playerMiddleRow][playerMiddleCol];
                    entity.underwater = gp.tileM.tile[tn].underwater;
                }
            }
        }
    }

//    public void checkMonster(Entity npc, Entity[] monsterArr) {
//        for (Entity monster : monsterArr) {
//            if (monster != null && npc != null) {
//
//                // Get the player's solid area position
//                npc.solidArea.x = npc.worldX + npc.solidAreaDefaultX;
//                npc.solidArea.y = npc.worldY + npc.solidAreaDefaultY;
//                monster.solidArea.x = monster.worldX + monster.solidAreaDefaultX;
//                monster.solidArea.y = monster.worldY + monster.solidAreaDefaultY;
//
//                if (npc.solidArea.intersects(monster.solidArea)) {
//                    npc.collisionOn = true;
//                    System.out.println("NPC COLLISION!");
//                    break;
//                }
//
//                npc.solidArea.x = npc.solidAreaDefaultX;
//                npc.solidArea.y = npc.solidAreaDefaultY;
//                monster.solidArea.x = monster.solidAreaDefaultX;
//                monster.solidArea.y = monster.solidAreaDefaultY;
//            } else {
//                System.out.println("NPC == null :(");
//            }
//        }
//    }
//    public void checkNPC(Entity[] monsterArr, Entity[] npcArr) {
//        System.out.println("BANG BANG");
//        for (monster : monsterArr) {
//            for (Entity npc : npcArr) {
//                System.out.println("Booong boong");
//                if (monster != null && npc != null) {
//
//                    // Get the player's solid area position
//                    monster.solidArea.x = monster.worldX + monster.solidAreaDefaultX;
//                    monster.solidArea.y = monster.worldY + monster.solidAreaDefaultY;
//                    npc.solidArea.x = npc.worldX + npc.solidAreaDefaultX;
//                    npc.solidArea.y = npc.worldY + npc.solidAreaDefaultY;
//
//                    if (monster.solidArea.intersects(npc.solidArea)) {
//                        monster.collisionOn = true;
//                        System.out.println("MONSTER COLLISION!");
//                        break;
//                    }
//                    monster.solidArea.x = monster.solidAreaDefaultX;
//                    monster.solidArea.y = monster.solidAreaDefaultY;
//                    npc.solidArea.x = npc.solidAreaDefaultX;
//                    npc.solidArea.y = npc.solidAreaDefaultY;
//                } else {
//                    System.out.println("Mon == null :(");
//                }
//            }
//        }
//    }
}
