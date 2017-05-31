package RayTracing;
import org.apache.commons.math3.geometry.euclidean.threed.Vector;

/**
 * Created by orrbarkat on 27/05/2017.
 * from: http://introcs.cs.princeton.edu/java/33design/Vector.java.html
 */
public class Ray {
    private Vector point;
    private Vector direction;

    public Ray(Vector start, Vector dir)
    {
        point = start;
        direction = dir;
    }

    public Vector getPoint() {
        return point;
    }

    public Vector getDirection() {
        return direction;
    }

    public Vector getIntersection(double t){
        return getPoint().add(t, getDirection());
    }
}