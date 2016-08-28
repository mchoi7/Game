import java.awt.*;
import java.util.List;

import static java.lang.Math.abs;
import static java.lang.Math.signum;

public abstract class Actor extends Entity implements Updatable, Renderable {
    protected double dx, dy;

    public Actor(Point point, Box box) {
        super(point, box);
    }

    protected void collide(Actor actor) {
    }

    protected boolean collide(Block block) {
        if(this.intersects(block)) {
            double distX = point.getX() - dx - block.point.getX();
            double distY = point.getY() - dy - block.point.getY();
            if(abs(distX/distY) > block.box.getW()/block.box.getH()) {
                dx = 0;
                point.setX(block.point.getX() + signum(distX)*(block.box.getW() + box.getW())/2);
            } else {
                dy = 0;
                point.setY(block.point.getY() + signum(distY)*(block.box.getH() + box.getH())/2);
            }
            return true;
        } return false;
    }

    public void move() {
        point.addX(dx);
        point.addY(dy);
    }

    public abstract void update();

    public void process(List<Block> blocks) {
        for(Block block : blocks)
            if(collide(block))
                break;
    }

    public void render(Graphics g) {
        g.drawRect((int) (point.getX() - box.getW()/2), (int) (point.getY() - box.getH()/2), (int) box.getW(), (int) box.getH());
    }
}