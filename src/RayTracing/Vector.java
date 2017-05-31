package RayTracing;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

/**
 * Created by orrbarkat on 31/05/2017.
 */
public class Vector extends Vector3D {

    public Vector crossProduct(Vector other){
        return (Vector)super.crossProduct(other).negate();
    }
}
