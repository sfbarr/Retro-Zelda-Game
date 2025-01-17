package object;

import entity.Entity;
import main.GamePanel;

public class Sword extends Entity {
    public Sword(int base, GamePanel gp) {
        super(gp);
        baseY = base;
        name = "Sword";
        direction = "down";
        spriteNum = 1;
        floating = false;

        down1 = setup("/objects/newSword", gp.tileSize * 3/2, gp.tileSize * 3/2);
    }
}
