package hz.lin.sparkiMapper;

import java.util.Vector;

public class SingleReading {
    public double x, y, ang;
    public Vector<SingleDistance> distances;
    SingleReading(double x, double y, double ang) {
        this.x = x;
        this.y = y;
        this.ang = ang;
        distances = new Vector<SingleDistance>();
    }
}
