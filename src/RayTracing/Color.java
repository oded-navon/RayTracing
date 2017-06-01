package RayTracing;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

/**
 * Created by orrbarkat on 31/05/2017.
 */
public class Color extends Vector3D {

    public Color(float r, float g, float b) {
        super(r,g,b);
    }

    public Color(float[] rgb) {
        super(rgb[0], rgb[1], rgb[2]);
    }


    public float[] getRGB() {
        float[] rgb = {((float) getX()),((float) getY()),((float) getZ())};
        return rgb;
    }

    public static Color getAsColor(Vector3D vec){
        return new Color(((float) vec.getX()),((float) vec.getY()),((float) vec.getZ()));
    }


    public Color mult(Color other){
        return new Color((float)(this.getX()*other.getX()),
                (float)(this.getY()*other.getY()),
                (float)(this.getZ()*other.getZ()));
    }

    public Color mult(double factor){
        return new Color((float)(this.getX() * factor),
                (float)(this.getY()* factor),
                (float)(this.getZ()* factor));
    }

    public Color mult(float factor){
        return new Color((float)(this.getX() * factor),
                (float)(this.getY()* factor),
                (float)(this.getZ()* factor));
    }

    public Color add(Color other){
        return getAsColor(super.add(other));
    }


    public String toString(){
        return this.scalarMultiply(255).toString();
    }
}
