package tile;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int[][] mapTileNum;
    public int[][] mapTileNum2;
    public int[][] treeTileNum;
    Tile tree;
    String mapFilePath;



    public TileManager(GamePanel gp) {
        this.gp = gp;

        // Make tile array for different background tiles
        tile = new Tile[50];

        // Make int array with elements corresponding to Tile Array index.
        // i.e. if mapTileNum[14][8] is '2',  then it chooses Tile[2], or 'Water.png'
        mapTileNum = new int[gp.maxWorldRow+1][gp.maxWorldCol+1];
        mapTileNum2 = new int[gp.maxWorldRow+1][gp.maxWorldCol+1];
        //treeTileNum = new int[gp.maxWorldRow][gp.maxWorldCol];

        getTileImage();
        loadMap("/maps/world01.txt", mapTileNum);  // Set Correct Map
        loadMap("/maps/world01.txt", mapTileNum2);
        //loadMap("/maps/worldTrees01.txt", treeTileNum); // Get tree positions
    }
    public void getTileImage(){
            setup(0, "grass3", false);
            setup(1, "grass3", false);
            setup(2, "grass3", false);
            setup(3, "grass3", false);
            setup(4, "grass3", false);
            setup(5, "grass3", false);
            setup(6, "grass3", false);
            setup(7, "grass3", false);
            setup(8, "grass3", false);
            setup(9, "grass3", false);
            setup(10, "grass3", false);
            setup(11, "wallCracked", true);
            setup(12, "water", false);
            tile[12].underwater = true;
            setup(13, "earth", false);
            setup(14, "sand", false);
            setup(15, "tree", true);

            setup(16, "waterTopEdge", false);
            tile[16].underwater = true;
            setup(17, "waterTopLeft", false);
            tile[17].underwater = true;
            setup(18, "waterLeftEdge", false);
            tile[18].underwater = true;
            setup(19, "waterBottomLeft", false);
            tile[19].underwater = true;
            setup(20, "waterBottomEdge", false);
            tile[20].underwater = true;
            setup(21, "waterBottomRight", false);
            tile[21].underwater = true;
            setup(22, "waterRightEdge", false);
            tile[22].underwater = true;
            setup(23, "waterTopRight", false);
            tile[23].underwater = true;

            setup(24, "waterTopLeftCorner", false);
            tile[24].underwater = true;
            setup(25, "waterBottomLeftCorner", false);
            tile[25].underwater = true;
            setup(26, "waterBottomRightCorner", false);
            tile[26].underwater = true;
            setup(27, "waterTopRightCorner", false);
            tile[27].underwater = true;

            setup(28,"roadTop",false);
            setup(29,"roadTopLeft",false);
            setup(30,"roadLeft",false);
            setup(31,"roadBottomLeft",false);
            setup(32,"roadBottom",false);
            setup(33,"roadBottomRight",false);
            setup(34,"roadRight",false);
            setup(35,"roadTopRight",false);







    }

    public void setup(int index, String imagePath, boolean collision){

        UtilityTool uTool = new UtilityTool();
        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imagePath + ".png"));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    // NOTE: PROOF OF WORKING IntelliJ-IDEA and WORKING PROJECT OF MINE
    public void loadMap(String mapFilePath, int[][] tileNum) {

        try {
            InputStream is = getClass().getResourceAsStream(mapFilePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;
            while (row < gp.maxWorldRow) {

                // Making a string for EACH LINE of Text File
                String line = br.readLine();

                while (col < gp.maxWorldCol) {

                    // Splitting line & making a String array of each number
                    String[] numbers = line.split(" ");

                    // Taking index of the String array and assigning each element to int variable
                    int num = Integer.parseInt(numbers[col]);

                    // Assigning the number stored by 'num' to the 2D Map Array
                    tileNum[row][col] = num;

                    col++;
                }
                // Not sure if this is even needed :(
                // if (col == gp.maxWorldCol) {
                    col = 0;
                    row++;

            }

        } catch (Exception e){
            e.printStackTrace();
            System.out.println("LOAD MAP() FAILED");
        }

    }

    public void draw(Graphics2D g2){

        int worldCol = 0;
        int worldRow = 0;

//        while (worldCol < gp.maxWorldRow && worldRow < gp.maxWorldRow) {
//
//            // Taking 0, 1, or 2 from 2D Map Array, assign to 'tileNum'
//            int tileNum = mapTileNum[worldCol][worldRow];
//
//            int worldX = worldCol * gp.tileSize;
//            int worldY = worldRow * gp.tileSize;
//            int screenX = worldX - gp.player.worldX + gp.player.screenX;
//            int screenY = worldY - gp.player.worldY + gp.player.screenY;
//
//            g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
//            worldCol++;
//
//            if (worldCol == gp.maxWorldCol) {
//                worldCol = 0;
//                worldRow++;
//            }
//        }

        while (worldCol < gp.maxWorldRow && worldRow < gp.maxWorldRow) {

            // Taking 0, 1, or 2 from 2D Map Array, assign to 'tileNum'

            int tileNum = mapTileNum[worldRow][worldCol];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                    worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                    worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                    worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

                g2.drawImage(tile[tileNum].image, screenX, screenY, null);

//                if (treeTileNum[worldRow][worldCol] == 1){
//                    g2.drawImage(tree.image, screenX, screenY, gp.tileSize, gp.tileSize, null);
//                }

            }
            worldCol++;


            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }

        }

        // NOTE: Neat and interesting way of projecting past map edges

//        worldCol = 0;
//        worldRow = 0;
//        if (gp.player.worldX > gp.worldWidth - gp.player.screenX){
//
//            while (worldRow < gp.maxWorldRow) {
//                int tileNum = mapTileNum[worldRow][worldCol];
//
//                int worldRX = (worldCol * gp.tileSize) + gp.worldWidth;
//                int worldRY = worldRow * gp.tileSize;
//                int screenRX = worldRX - gp.player.worldX + gp.player.screenX;
//                int screenRY = worldRY - gp.player.worldY + gp.player.screenY;
//
//                g2.drawImage(tile[tileNum].image, screenRX, screenRY, null);
//
//                worldCol++;
//                if (worldCol == gp.maxWorldCol){
//                    worldCol = 0;
//                    worldRow ++;
//                }
//            }
//        }

//        g2.drawImage(tree.image, (gp.tileSize * 2) - gp.player.worldX + gp.player.screenX, -gp.player.worldY + gp.player.screenY, 48, 48, null);
//        g2.drawImage(tree.image, (gp.tileSize * 4) - gp.player.worldX + gp.player.screenX, (gp.tileSize * 2) - gp.player.worldY + gp.player.screenY, 48, 48, null);
//        g2.drawImage(tree.image, (gp.tileSize * 7) - gp.player.worldX + gp.player.screenX, gp.tileSize - gp.player.worldY + gp.player.screenY, 48, 48, null);
//        g2.drawImage(tree.image, (gp.tileSize * 10) - gp.player.worldX + gp.player.screenX, (gp.tileSize * 3) - gp.player.worldY + gp.player.screenY, 48, 48, null);
//        g2.drawImage(tree.image, (gp.tileSize * 11) - gp.player.worldX + gp.player.screenX, - gp.player.worldY + gp.player.screenY, 48, 48, null);
//        g2.drawImage(tree.image, (gp.tileSize * 13)- gp.player.worldX + gp.player.screenX, (gp.tileSize * 2) - gp.player.worldY + gp.player.screenY, 48, 48, null);
//        g2.drawImage(tree.image, (gp.tileSize * 15) - gp.player.worldX + gp.player.screenX, - gp.player.worldY + gp.player.screenY, 48, 48, null);
    }

}
