import java.io.Serializable;

import static java.lang.Math.abs;

public abstract class Entity implements Serializable {
    protected Point point;
    protected Box box;

    public Entity(Point point, Box box) {
        this.point = point;
        this.box = box;
    }

    public boolean intersects(Entity entity) {
        return abs(point.getX() - entity.point.getX()) < (box.getW() + entity.box.getW())/2 &&
                abs(point.getY() - entity.point.getY()) < (box.getH() + entity.box.getH())/2;
    }
}
