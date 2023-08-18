package object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Chest extends GameObject {
    public Chest() {
        name = "chest";

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().
                    getResourceAsStream("/objects/chest.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
