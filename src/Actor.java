import java.awt.*;

import static java.lang.Math.abs;
import static java.lang.Math.copySign;
import static java.lang.Math.signum;

public abstract class Actor extends Entity implements Updatable, Renderable {
    protected Scene scene;
    protected double dx, dy;
    protected boolean normal[] = new boolean[4];

    public Actor(Point point, Box box, Scene scene) {
        super(point, box);
        this.scene = scene;
    }

    protected void process() {
        for(int i = 0; i < normal.length; i++)
            normal[i] = false;
        for(Block block : scene.getBlocks())
            if(collide(block))
                break;
    }

    protected void collide(Actor actor) {
    }

    protected boolean collide(Block block) {
        if(this.intersects(block)) {
            double distX = point.getX() - dx - block.point.getX();
            double distY = point.getY() - dy - block.point.getY();
            if(abs(distX)*block.box.getH() > abs(distY)*block.box.getW()) {
                dx = 0;
                normal[distX > 0 ? 1 : 2] = true;
                point.setX(block.point.getX() + signum(distX)*(block.box.getW() + box.getW())/2);
            } else {
                dy = 0;
                normal[distY > 0 ? 0 : 2] = true;
                point.setY(block.point.getY() + signum(distY)*(block.box.getH() + box.getH())/2);
            }
            return true;
        } return false;
    }

    protected void move(double dx, double dy) {
        point.addX(dx);
        point.addY(dy);
    }

    public abstract void update();

    public void render(Graphics g) {
        g.drawRect((int) (point.getX() - box.getW()/2), (int) (point.getY() - box.getH()/2), (int) box.getW(), (int) box.getH());
    }

    public boolean[] getNormal() {
        return normal;
    }
}