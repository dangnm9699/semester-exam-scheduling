package implement.common;

public class Pair {
    public int fi;
    public int se;

    public Pair(int fi, int se) {
        this.fi = fi;
        this.se = se;
    }

    public boolean equal(int fi, int se) {
        return (this.fi == fi && this.se == se) || (this.fi == se && this.se == fi);
    }
}
