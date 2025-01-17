package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Chest extends Entity {

    public Chest(GamePanel gp){
        super(gp);
        name = "Chest";
        down1 = setup("/objects/newChest", gp.tileSize, gp.tileSize);
        collision = true;

    }
}
