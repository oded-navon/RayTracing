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
        Vector3D normalForInter = this.getNormal(ray,getDistForIntersection(ray));
        //check if the ray and plane are parallel
        if (ray.getDirection().crossProduct(normalForInter).getNorm()<=0.0001)
        {
            return Double.MAX_VALUE;
        }

        double numerator = (this.getOffset() - ray.getPoint().dotProduct(normalForInter));
        double denominator = ray.getDirection().dotProduct(normalForInter);
        double res = numerator/denominator;
        return res > 0 ? res : Double.MAX_VALUE;
    }

    public double getDistForIntersection(Ray r)
    {
        return (this.offset - normal.dotProduct(r.getPoint())) / (r.getDirection().dotProduct(normal));

    }

    @Override
    public Vector3D getNormal(Ray ray, double distance)
    {
        Vector3D hitPoint = ray.getIntersection(distance);
        Vector3D a = hitPoint.add(normal);
        Vector3D b = hitPoint.add(normal.scalarMultiply(-1));
        double ad = a.subtract(ray.getPoint()).getNorm();
        double bd = b.subtract(ray.getPoint()).getNorm();
        return ad < bd ? normal : normal.negate();
    }
}
