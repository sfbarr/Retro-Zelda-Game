package object;

import entity.Entity;
import main.GamePanel;

public class Heart extends Entity {
    public Heart(GamePanel gp){
        super(gp);
        name = "Heart";
        image = setup("/objects/fullHeart", gp.tileSize, gp.tileSize);
        image2 = setup("/objects/halfHeart", gp.tileSize, gp.tileSize);
        image3 = setup("/objects/emptyHeart", gp.tileSize, gp.tileSize);

    }

}
