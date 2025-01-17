package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OpenDoor extends Entity {
    public OpenDoor(GamePanel gp){
        super(gp);
        name = "OpenDoor";
        down1 = setup("/objects/openDoor", gp.tileSize, gp.tileSize);
    }
}
