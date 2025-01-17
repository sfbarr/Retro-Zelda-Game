package entity;

import main.GamePanel;
import main.NodeManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class EnemyTest extends Entity {

    GamePanel gp;
    Player player;

    public BufferedImage skeleton;

    public int up, down, left, right;
    public int screenX, screenY;
    public int worldRow, worldCol;

    public EnemyTest(GamePanel gp, Player player){
        super(gp);
        this.gp = gp;
        this.player = player;
        collisionOn = false;

        solidArea = new Rectangle(8, 16, 32, 32);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDefaultValues();
        getEnemyImage();

    }

    public void setDefaultValues(){

        worldX = gp.tileSize * 8;
        worldY = gp.tileSize * 8;
        speed = 3;

    }

    public void getEnemyImage(){

        try {
            skeleton = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/ghoul.png"));
        } catch (IOException e) {
        e.printStackTrace();
        }
    }

    public void update(){
        int startPosY = worldY;
        int startPosX = worldX;

        // TO DO: FIGURE NODE SYSTEM
        //gp.nodeM.findRoute(gp, this, player);
        //gp.nodeM.adjacencyGraph(this, player);
        //gp.nodeM.directTraffic(this,player);


//        worldCol = worldX/gp.tileSize;
//        worldRow = worldY/ gp.tileSize;
//        if (gp.nodeM.nodeArray2D[worldRow][worldCol] == 2){
//            gp.nodeM.findRoute(gp,this,player);
//        } else {

            // NOTE: Maybe include collision bc rn enemy can run through walls, only
            // world X and Y are affected
            // when he "collides" with a known wall

//        if (collisionOn) {
//            switch (direction) {
//                case "up":
//                    worldY += gp.tileSize;
//                case "down":
//                    worldY -= gp.tileSize;
//                case "left":
//                    worldX += gp.tileSize;
//                case "right":
//                    worldX -= gp.tileSize;
//                case "northWest":
//                    worldX += gp.tileSize;
//                    worldY += gp.tileSize;
//                case "northEast":
//                    worldX -= gp.tileSize;
//                    worldY += gp.tileSize;
//                case "southWest":
//                    worldX += gp.tileSize;
//                    worldY -= gp.tileSize;
//                case "southEast":
//                    worldX -= gp.tileSize;
//                    worldY -= gp.tileSize;
//            }
//        }


//        if (player.worldX > worldX + speed) {
////               worldX += speed;
//            right = 1;
//            left = 0;
//        } else if (player.worldX < worldX - speed) {
////               worldX -= speed;
//            right = 0;
//            left = 1;
//        } else {
//            right = 0;
//            left = 0;
//        }
//        if (player.worldY > worldY + speed) {
////               worldY += speed;
//            down = 1;
//            up = 0;
//        } else if (player.worldY < worldY - speed) {
////               worldY -= speed;
//            down = 0;
//            up = 1;
//
//        } else {
//            down = 0;
//            up = 0;
//        }
//
//        if (right + left + down + up == 1) {
//            System.out.println("uni directional");
//            direction = (right == 1) ? "right" : direction;
//            direction = (left == 1) ? "left" : direction;
//            direction = (down == 1) ? "down" : direction;
//            direction = (up == 1) ? "up" : direction;
//        } else if (right + left + down + up == 2) {
//            System.out.println("omniDirectional ");
//            direction = (up + left == 2) ? "northWest" : direction;
//            direction = (up + right == 2) ? "northEast" : direction;
//            direction = (down + left == 2) ? "southWest" : direction;
//            direction = (down + right == 2) ? "southEast" : direction;
//        }

            if (!collisionOn) {
                if (player.worldX > worldX - speed && player.worldX < worldX + speed) {
                    if (player.worldY < worldY - speed) {
                        direction = "up";
                    } else if (player.worldY > worldY + speed) {
                        direction = "down";
                    }
                } else if (player.worldY > worldY - speed && player.worldY < worldY + speed) {
                    if (player.worldX < worldX - speed) {
                        direction = "left";
                    } else if (player.worldX > worldX + speed) {
                        direction = "right";
                    }
                }
                if (player.worldX < worldX - speed && player.worldY < worldY - speed) {
                    direction = "northWest";
                }
                if (player.worldX > worldX + speed && player.worldY < worldY - speed) {
                    direction = "northEast";
                }
                if (player.worldX < worldX - speed && player.worldY > worldY + speed) {
                    direction = "southWest";
                }
                if (player.worldX > worldX + speed && player.worldY > worldY + speed) {
                    direction = "southEast";
                }
            }


            this.collisionOn = false;
//            gp.cChecker.checkEnemyTile(this);


            if (!collisionOn) {

                switch (direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                    case "northWest":
                        worldX -= speed;
                        worldY -= speed;
                        break;
                    case "northEast":
                        worldX += speed;
                        worldY -= speed;
                        break;
                    case "southWest":
                        worldX -= speed;
                        worldY += speed;
                        break;
                    case "southEast":
                        worldX += speed;
                        worldY += speed;
                        break;
                    default:
                        break;
                }
            }
//            else if (collisionOn){
//                switch (direction){
//                    case "up":
//                        worldY -= speed;
//                    case "down":
//                        worldY += speed;
//                    case "left":
//                        worldX -= speed;
//                    case "right":
//                        worldX += speed;
//                    case "northWest":
//                        worldX -= speed;
//                        worldY -= speed;
//                    case "northEast":
//                        worldX += speed;
//                        worldY -= speed;
//                    case "southWest":
//                        worldX -= speed;
//                        worldY += speed;
//                    case "southEast":
//                }
//            }

            //if (Math.abs(worldY - player.worldY) < gp.tileSize * 2) {
            //   speed = 1;
            // }

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
    }
    public void draw(Graphics2D g2){
        screenX = worldX - gp.player.worldX + gp.player.screenX;
        screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

            g2.drawImage(skeleton, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }

    }
}
