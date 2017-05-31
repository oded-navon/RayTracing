package RayTracing;


import org.apache.commons.math3.geometry.euclidean.threed.Vector;

public class Plane implements Shape
{
    private Vector normal;
    private double offset;
    private int materialIndex;

    public Vector getNormal() {
        return normal;
    }

    public double getOffset() {
        return offset;
    }

    public int getMaterialIndex() {
        return materialIndex;
    }

    public Plane(float[] normal, double offset, int materialIndex) {
        this.normal = new Vector(normal[0], normal[1], normal[2]);
        this.offset = offset;
        this.materialIndex = materialIndex;
    }

    public Plane(Vector normal, double offset, int materialIndex) {
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
        double res = numerator/denominator;
        return res > 0 ? res : Double.MAX_VALUE;
    }

    @Override
    public Vector getNormal(Ray ray, double distance) {
        return this.getNormal();
    }
}
