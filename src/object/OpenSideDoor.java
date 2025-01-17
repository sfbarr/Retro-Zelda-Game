package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OpenSideDoor extends Entity {
    public OpenSideDoor(GamePanel gp){
        super(gp);
        name = "OpenSideDoor";
        down1 = setup("/objects/openSideDoor", gp.tileSize, gp.tileSize);
    }
}