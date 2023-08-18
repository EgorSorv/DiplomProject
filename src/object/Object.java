package object;

import java.awt.image.BufferedImage;

public abstract class Object {
    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public int worldX, worldY;
}
