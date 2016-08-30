import java.awt.*;

import static java.lang.Math.*;

public class Player extends Actor {
    private final static char L = 'D', R = 'A', U = 'W', D = 'S';
    private State state, lastState;
    private Controller controller;
    private double dir, time;
    private State ground, aerial, action;
    private State idle, walk, dash, run, turn, land;
    private State fall;
    

    public Player(Point point, Box box, Scene scene) {
        super(point, box, scene);
        initialize();
    }

    private void initialize() {
        ground = new State();
        aerial = new State();
        action = new State();
        /* ddx, ddy, mdx, mdy, cddx, cddy, fric, res, time */
        idle = new State(0, 10, 0, 10, 0, 1, .5, 0, 0, ground);
        idle.setName("Idle");
        walk = new State(.75, 10, 4, 7, 0, 1, .5, 0, 0, ground);
        walk.setName("Walk");
        dash = new State(1.5, 6, 5, 6, 0, 1, .5, 0, 20, ground);
        dash.setName("Dash");
        run = new State(.5, 6, 5, 6, 0, 1, .25, 0, 0, ground);
        run.setName("Run");
        turn = new State(.25, 5, 6, 5, 0, 1, .125, 0, 0, ground);
        turn.setName("Turn");
        land = new State(.125, 0, 1, 0, 0, 1, .25, 0, 5, ground);
        land.setName("Land");
        fall = new State(.5, .5, .5, 5, 0, 1, .025, .025, 0, aerial);
        fall.setName("Fall");

        state = idle;
        lastState = idle;
    }

    public void update() {
        setDirection();
        setState();
        applyInput();
        applyPhysics();
        move(dx, dy);
        process();
        info = "State " + normal[2] + " " + lastState.name;
    }


    String info;

    private void setDirection() {
        if (controller.getKey('D') != controller.getKey('A'))
            dir = controller.getKey('D') ? 1 : 0 - (controller.getKey('A') ? 1 : 0);
        else if (controller.getKey('D') && controller.getKey('A')) {
            if (controller.getKeyPress('D') != controller.getKeyPress('A'))
                dir = controller.getKeyPress('D') > controller.getKeyPress('A') ? 1 : -1;
        } else dir = 0;
    }

    private void setState() {
        /*
        if (!normal[2]) {
            if(state.parentState != aerial)
                setState(fall);
            else {

            }
        } else {
            if(state.parentState != ground) {
                setState(land);
            } else {
                if (state == idle) {
                    if (dir != 0 || dx != 0)
                        setState(walk);
                } else if (state == walk) {
                    if (dir == 0) {
                        if (dx == 0)
                            setState(idle);
                    } else if (controller.getKeyDoubleTap(L) || controller.getKeyDoubleTap(R))
                        setState(dash);
                    else if (dir == -signum(dx)) {
                        time = 0;
                        setState(turn);
                    }
                } else if (state == dash) {
                    if (time < 0)
                        setState(run);
                    else if (dir == -signum(dx)) {
                        time = 0;
                        setState(dash);
                    }
                } else if (state == run) {
                    if (dir == 0 && abs(dx) < state.mdx / 2)
                        setState(walk);
                    else if (dir == -signum(dx)) {
                        time = 0;
                        setState(turn);
                    }
                } else if (state == turn) {
                    if (dir == signum(dx)) {
                        setState(lastState);
                    }
                } else if (state == land) {
                    if (time < 0) {
                    } else {
                    }
                }
            }
        }
        */
        time--;
    }

    private void setState(State state) {
        if (time <= 0) {
            lastState = this.state;
            this.state = state;
            time = state.time;
        }
    }

    private void applyInput() {
        double acc = dir * state.ddx;
        if (signum(acc) * dx <= state.mdx)
            dx = signum(acc) * (dx + acc) <= state.mdx ? dx + acc : signum(acc) * state.mdx;
        acc = (controller.getKey('W') ? -1 : 0 - (controller.getKey('S') ? -1 : 0)) * state.ddy;
        if (signum(acc) * dy <= state.mdy)
            dy = signum(acc) * (dy + acc) <= state.mdy ? dy + acc : signum(acc) * state.mdy;
    }

    private void applyPhysics() {
        dx += state.cddx;
        dy += state.cddy;
        dx -= abs(dx) > state.fric ? signum(dx) * state.fric : dx;
        dy -= abs(dy) > state.fric ? signum(dx) * state.fric : dy;
    }

    public void render(Graphics g) {
        super.render(g);
        g.drawString(info, 100, 120);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public State getState() {
        return state;
    }
}
