package RayTracing;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

/**
 * Created by orrbarkat on 27/05/2017.
 * from: http://introcs.cs.princeton.edu/java/33design/Vector.java.html
 */
public class Ray {
    private Vector3D point;
    private Vector3D direction;

    public Ray(Vector3D start, Vector3D dir)
    {
        point = start;
        direction = dir.normalize();
    }

    public Vector3D getPoint() {
        return point;
    }

    public Vector3D getDirection() {
        return direction;
    }

    public Vector3D getIntersection(double t){
        return getPoint().add(t, getDirection());
    }
}