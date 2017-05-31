package RayTracing;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

/**
 * Created by orrbarkat on 31/05/2017.
 */
public class Color extends Vector3D {

    public Color(float r, float g, float b) {
        super(r,g,b);
    }

    public float[] getRGB() {
        float[] rgb = {((float) getX()),((float) getY()),((float) getZ())};
        return rgb;
    }

    public static Color getAsColor(Vector vec){
        return new Color(((float) vec.getX()),((float) vec.getY()),((float) vec.getZ()));
    }


    public Color mult(Color other){
        return new Color((float)(this.getX()*other.getX()),
                (float)(this.getY()*other.getY()),
                (float)(this.getZ()*other.getZ()));
    }
}