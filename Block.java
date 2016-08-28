import java.awt.*;

public class Block extends Entity implements Renderable {
    public Block(Point point, Box box) {
        super(point, box);
    }

    public void render(Graphics g) {
        g.drawRect((int) (point.getX() - box.getW()/2), (int) (point.getY() - box.getH()/2), (int) box.getW(), (int) box.getH());
    }
}
