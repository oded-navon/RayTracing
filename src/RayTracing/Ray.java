package RayTracing;
import javax.vecmath.Vector3d;
/**
 * Created by orrbarkat on 27/05/2017.
 * from: http://introcs.cs.princeton.edu/java/33design/Vector.java.html
 */
public class Ray {
    private Vector3d point;
    private Vector3d direction;

    public Ray(Vector3d start, Vector3d dir) {
        point = start;
        direction = dir;
    }


}