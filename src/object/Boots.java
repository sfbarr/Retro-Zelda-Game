package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Boots extends Entity {

    public Boots(int base, GamePanel gp){
        super(gp);
        baseY = base;
        floating = true;
        name = "Boots";

        down1 = setup("/objects/boots", gp.tileSize, gp.tileSize);
    }
}
