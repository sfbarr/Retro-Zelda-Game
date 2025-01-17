package main;

import entity.EnemyTest;
import entity.Player;
import org.w3c.dom.Node;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;


public class NodeManager {

    GamePanel gp;
    public int[][] nodeArray;
    public int[][] pathArray;
    public int[][] adjacencyMatrix;
    Queue<int[]> arrayQueue = new LinkedList<>();
    boolean[][] visitedArr = new boolean[50][50];
    int enemyNum, upNum, downNum, leftNum, rightNum;


    public NodeManager(GamePanel gp){

        this.gp = gp;
        pathArray = new int[gp.maxWorldRow+1][gp.maxWorldCol+1];
        nodeArray = new int[gp.maxWorldRow][gp.maxWorldCol];
        this.adjacencyMatrix = new int[gp.maxWorldRow/2][gp.maxWorldCol/2];
        mapNodes("/maps/world01PathNodes.txt", pathArray);
        System.out.println("NodeManager created");




    }
    
    public void mapNodes(String mapFilePath, int[][] nodeNum){
        try {
            InputStream is = getClass().getResourceAsStream(mapFilePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;
            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {

                // Making a string for EACH LINE of Text File
                String line = br.readLine();

                while (col < gp.maxWorldCol) {

                    // Splitting line & making a String array of each number
                    String[] numbers = line.split(" ");

                    // Taking index of the String array and assigning each element to nodeArray
                    nodeNum[row][col] = Integer.parseInt(numbers[col]);

                    col++;
                }
                if (col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }

        } catch (Exception e){
            e.printStackTrace();
            System.out.println("LOAD MAP() FAILED");
        }

    }

    public void findRoute(GamePanel gp, EnemyTest enemy, Player player){
        System.out.println("Finding route");
        int col = enemy.worldX/gp.tileSize;
        int row = enemy.worldY/gp.tileSize;
       // int[] latLon = {row,col};
        int playerCol = player.worldX/gp.tileSize;
        int playerRow  = player.worldY/gp.tileSize;
        
        
        
        
      //  while (row < gp.maxWorldRow){

            while (col < gp.maxWorldCol && row < gp.maxWorldRow){
                
                enemyNum = pathArray[row][col];
                upNum = pathArray[row-1][col];
                downNum = pathArray [row+1][col];
                leftNum = pathArray[row][col-1];
                rightNum = pathArray[row][col+1];

                //arrayQueue.add(latLon);
                visitedArr[row][col] = true;
                if (pathArray[row-1][col] == 2){
                    row --;
                    System.out.println("Row: " + row + " Col: " + col);
                    enemy.worldY-= enemy.speed;
                    visitedArr[row][col] = true;
                    if (playerRow == row && playerCol == col){ break; }
                    if (pathArray[row-1][col] == 0){enemy.worldY -= gp.tileSize;
                        System.out.println("Leaving nodes");
                        break;}
                }
                else if (pathArray[row+1][col] == 2){
                    row ++;
                    enemy.worldY += enemy.speed;
                    System.out.println("Row: " + row + " Col: " + col);
                    visitedArr[row][col] = true;
                    if (playerRow == row && playerCol == col){ break; }
                    if (pathArray[row+1][col] == 0){enemy.worldY += gp.tileSize;
                        System.out.println("Leaving nodes"); break; }
                }
                if (pathArray[row][col-1] == 2){
                    col --;
                    enemy.worldX -= enemy.speed;
                    System.out.println("Row: " + row + " Col: " + col);
                    visitedArr[row][col] = true;
                    if (playerRow == row && playerCol == col){ break; }
                    if (pathArray[row][col-1] == 0){enemy.worldX -= gp.tileSize;
                        System.out.println("Leaving nodes"); break; }
                }

                else if (pathArray[row][col+1] == 2){
                    col++;
                    System.out.println("Row: " + row + " Col: " + col);
                    enemy.worldX += enemy.speed;
                    visitedArr[row][col] = true;
                    if (playerRow == row && playerCol == col){ break; }
                    if (pathArray[row][col+1] == 0){
                        enemy.worldX += gp.tileSize;
                        System.out.println("Leaving nodes"); break; }
                }
                break;
            }
//            col = 0;
//            row ++;
//        }

    }

    public void adjacencyGraph(EnemyTest enemy, Player player){
        // NOTE: CREATE NODE CLASS LATER
        // Then, create instance variables such as
        // X & Y as well as an array for each class
        // Containing all known connections.
        
        for (int row = 0; row < gp.maxWorldRow; row +=3){
            for (int col = 0; col < gp.maxWorldCol; col +=3){
                if (pathArray[row][col] == 2 || pathArray[row][col] == 0){
                    nodeArray[row][col] = 1;
                }
                System.out.print(nodeArray[row][col] + " ");
            }
            System.out.println();
        }

    }
    // A NODE CLASS... IS IT NECESSARY?

//    public Node nearestNode(Node node, Player player){
//        if (Math.abs(node.worldX - player.worldX) < gp.tileSize * 5){
//            return node;
//        } else {
//            return null;
//        }
//    }
    public void connectNodes(Node node, Player player){
        int nodeNum = 0;
        while (nodeNum < 100){

            // NOTE: Create an adjacency matrix to represent
            // ACTUAL NODES, not just X-Y coordinates where
            // there is or isn't a path.

            // The aforementioned 2D matrix will indicate which
            // nodes are connected to each other. The X & Y axis
            // are both the length of the number of nodes (so that
            // row 7 represents the same node as column 7).
            nodeNum ++;
        }
    }

}
