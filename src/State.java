public final class State {
    public String name = "null";
    public final double ddx, ddy;
    public final double mdx, mdy;
    public final double cddx, cddy;
    public final double fric, res;
    public final double time;
    public final State parentState;

    public State() {
        ddx = 0;
        ddy = 0;
        mdx = 0;
        mdy = 0;
        cddx = 0;
        cddy = 0;
        fric = 0;
        res = 0;
        time = 0;
        parentState = this;
    }

    public State(double ddx, double ddy, double mdx, double mdy, double cddx, double cddy, double fric, double res, double time, State parentState) {
        this.ddx = ddx;
        this.ddy = ddy;
        this.mdx = mdx;
        this.mdy = mdy;
        this.cddx = cddx;
        this.cddy = cddy;
        this.fric = fric;
        this.res = res;
        this.time = time;
        this.parentState = parentState;
    }

    public void setName(String name) {
        this.name = name;
    }
}