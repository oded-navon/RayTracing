package RayTracing;


import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class Sphere implements Shape
{
    private Vector3D center;
    private float radius;
    private int materialIndex;

    public Sphere(float[] center, float radius, int materialIndex)
    {
        this.center = new Vector3D(center[0], center[1], center[2]);
        this.radius = radius;
        this.materialIndex = materialIndex;
    }

    public Vector3D getCenter() {
        return center;
    }

    public float getRadius()
    {
        return radius;
    }

    public int getMaterialIndex() {
        return materialIndex;
    }

    @Override
    public double IntersectRay(Ray ray)
    {
        Vector3D rayDir = ray.getDirection();
        Vector3D rayPos = ray.getPoint();

        double aCoefficent = rayDir.dotProduct(rayDir);
        Vector3D eMinusC = rayPos.subtract(this.center);
        double bCoefficent = 2*rayDir.dotProduct(eMinusC);
        double cCoefficent = eMinusC.dotProduct(eMinusC) - Math.pow(this.getRadius(),2);

        double discriminant = Math.sqrt(Math.pow(bCoefficent,2)+4*aCoefficent*cCoefficent);
        //No intersection
        if (discriminant<0)
        {
            return Double.MAX_VALUE;
        }
        double singleSolution = (-bCoefficent)/(2*aCoefficent);
        double solution1 = (-bCoefficent+discriminant)/(2*aCoefficent);
        double solution2 = (-bCoefficent-discriminant)/(2*aCoefficent);

        return discriminant==0 ? singleSolution : Math.min(solution1,solution2);
    }

    @Override
    public Vector3D getNormal(Ray ray, double distance) {
        return null;
    }
}
