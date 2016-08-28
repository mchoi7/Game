public class State {
    private double time;
    private double ddx, ddy;
    private double maxdx, maxdy;
    private double fric, grav;
    private String name = "null";

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

    public double getDdx() {
        return ddx;
    }

    public void setDdx(double ddx) {
        this.ddx = ddx;
    }

    public double getDdy() {
        return ddy;
    }

    public void setDdy(double ddy) {
        this.ddy = ddy;
    }

    public double getMaxdx() {
        return maxdx;
    }

    public void setMaxdx(double maxdx) {
        this.maxdx = maxdx;
    }

    public double getMaxdy() {
        return maxdy;
    }

    public void setMaxdy(double maxdy) {
        this.maxdy = maxdy;
    }

    public double getFric() {
        return fric;
    }

    public void setFric(double fric) {
        this.fric = fric;
    }

    public double getGrav() {
        return grav;
    }

    public void setGrav(double grav) {
        this.grav = grav;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public double getTime() {
        return time;
    }
}