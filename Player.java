import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.*;

public class Player extends Actor {
    private final static int X = 0, Y = 1;
    private int time, direction, tap[] = new int[2], press[] = new int[2];
    private List<State> machine = new ArrayList<>();
    private State state;
    private boolean isAction, isAerial;
    private Controller controller;
    private State idle, walk, run, turn;

        /*
        Null,
        Hurt,

        Hop,
        Leap,
        Rise,
        Jump,
        Fall,
        Land,

        Idle,
        Walk,
        Dash,
        Run,
        Turn,
        Crouch,*/

    public Player(Point point, Box box) {
        super(point, box);
        initialize();
    }

    private void initialize() {
        idle = new State();
        idle.setName("Idle");
        idle.setFric(1);
        idle.setGrav(1);

        walk = new State();
        walk.setName("Walk");
        walk.setDdx(2);
        walk.setMaxdx(10);
        walk.setFric(1);
        walk.setGrav(1);

        run = new State();
        run.setName("Run");
        run.setDdx(1);
        run.setMaxdx(15);
        run.setFric(.5);
        run.setGrav(1);

        turn = new State();
        turn.setName("Turn");
        turn.setDdx(.5);
        turn.setMaxdx(10);
        turn.setFric(.25);
        turn.setGrav(1);
        turn.setTime(20);

        machine.add(idle);
        machine.add(walk);
        machine.add(run);
        machine.add(turn);
        state = idle;
    }

    public void update() {
        getInput();
        calculateState();
        registerMove();
        applyPhysics();
        move();
    }

    private void getInput() {
        tap[X] = (controller.getKeyTimer('D') == 0 ? 1 : 0) - (controller.getKeyTimer('A') == 0 ? 1 : 0);
        press[X] = (controller.getKey('D') ? 1 : 0) - (controller.getKey('A') ? 1 : 0);
        tap[Y] = (controller.getKeyTimer('W') == 0 ? 1 : 0) - (controller.getKeyTimer('S') == 0 ? 1 : 0);
        press[Y] = (controller.getKey('W') ? 1 : 0) - (controller.getKey('S') ? 1 : 0);
    }

    private void calculateState() {
        if(tap[0] != 0) {
            if(direction == tap[0]) {
                setState(run);
            }
        }

        if(press[0] != 0) {
            if(direction == -press[0]) {
                if(setState(turn))
                    direction = -direction;
            } else if(state != run) {
                if(setState(walk))
                    direction = press[0];
            }
        } else {
            if(dx == 0) {
                if(setState(idle))
                    direction = 0;
            }
        }
        time--;
    }

    private boolean setState(State state) {
        if(this.state != state && time <= 0) {
            this.state = state;
            time = state.getTime();
            return true;
        }
        return false;
    }

    private void registerMove() {
        if(abs(dx) < state.getMaxdx()) {
            dx += press[X]*state.getDdx();
            if(abs(dx) > state.getMaxdx())
                dx = signum(dx)*state.getMaxdx();
        }
        if(abs(dy) < state.getMaxdy()) {
            dy += press[Y]*state.getDdy();
            if(abs(dy) > state.getMaxdy())
                dy = signum(dy)*state.getMaxdy();
        }
    }

    private void applyPhysics() {
        dy += state.getGrav();
        dx = abs(dx) > state.getFric() ? (dx - signum(dx)*state.getFric()) : 0;
        dy = (abs(dy) > state.getFric() ? (dy - signum(dy)*state.getFric()) : 0) + state.getGrav();
    }

    public void process(List<Block> blocks) {
        isAerial = true;
        super.process(blocks);
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
                isAerial = distY > 0;
                point.setY(block.point.getY() + signum(distY)*(block.box.getH() + box.getH())/2);
            }
            return true;
        } return false;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public boolean isAction() {
        return isAction;
    }

    public boolean isAerial() {
        return isAerial;
    }

    public State getState() {
        return state;
    }

    public int getDirection() {
        return direction;
    }

    public int[] getTap() {
        return tap;
    }

    public int[] getPress() {
        return press;
    }
}
