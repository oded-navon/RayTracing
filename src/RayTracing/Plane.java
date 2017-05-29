package RayTracing;


import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class Plane implements Shape
{
    private Vector3D normal;
    private double offset;
    private int materialIndex;

    public Vector3D getNormal() {
        return normal;
    }

    public double getOffset() {
        return offset;
    }

    public int getMaterialIndex() {
        return materialIndex;
    }

    public Plane(float[] normal, double offset, int materialIndex) {
        this.normal = new Vector3D(normal[0], normal[1], normal[2]);
        this.offset = offset;
        this.materialIndex = materialIndex;
    }

    public Plane(Vector3D normal, double offset, int materialIndex) {
        this.normal = normal;
        this.offset = offset;
        this.materialIndex = materialIndex;
    }

    @Override
    public double IntersectRay(Ray ray)
    {
        //check if the ray and plane ar parallel
        if (ray.getDirection().crossProduct(this.getNormal()).getNorm()<=0.0001)
        {
            return Double.MAX_VALUE;
        }

        double numerator = -(ray.getPoint().dotProduct(this.getNormal())+this.getOffset());
        double denominator = ray.getDirection().dotProduct(this.getNormal());

        return numerator/denominator;
    }

    @Override
    public Vector3D getNormal(Ray ray, double distance) {
        return this.getNormal();
    }
}
