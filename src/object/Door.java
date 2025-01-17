package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Door extends Entity {

    public Door(GamePanel gp){
        super(gp);
        name = "Door";
        collision = true;
        down1 = setup("/objects/door", gp.tileSize, gp.tileSize);

    }
}
