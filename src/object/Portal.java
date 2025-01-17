package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Portal extends Entity {

    public Portal(GamePanel gp){
        super(gp);
        name = "Portal";
        down1 = setup("/objects/portal1", gp.tileSize, gp.tileSize);
        direction = "down";
    }
}
