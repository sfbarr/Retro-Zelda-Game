package object;

import entity.Entity;
import main.GamePanel;
import main.ObjectSetter;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Key extends Entity {

    public Key(int base, GamePanel gp){
        super(gp);
        baseY = base;
        name = "Key";
        floating = true;

        down1 = setup("/objects/key", gp.tileSize, gp.tileSize);
    }
}
