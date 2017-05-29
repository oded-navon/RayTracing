package RayTracing;


import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class Plane implements Shape
{
    private Vector3D normal;
    private float offset;
    private int materialIndex;

    public Vector3D getNormal() {
        return normal;
    }

    public float getOffset() {
        return offset;
    }

    public int getMaterialIndex() {
        return materialIndex;
    }

    public Plane(float[] normal, float offset, int materialIndex) {
        this.normal = new Vector3D(normal[0], normal[1], normal[2]);
        this.offset = offset;
        this.materialIndex = materialIndex;
    }


    @Override
    public double IntersectRay(Ray ray) {

    }
}
