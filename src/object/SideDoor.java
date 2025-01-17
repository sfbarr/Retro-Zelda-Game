package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class SideDoor extends Entity {
    public SideDoor(GamePanel gp){
        super(gp);
        name = "SideDoor";
        down1 = setup("/objects/sideDoor", gp.tileSize, gp.tileSize);
        collision = true;
    }
}
