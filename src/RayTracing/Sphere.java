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

//    @Override
//    public double IntersectRay(Ray ray)
//    {
//        Vector3D rayDir = ray.getDirection();
//        Vector3D rayPos = ray.getPoint();
//
//        double aCoefficent = rayDir.dotProduct(rayDir);
//        Vector3D eMinusC = rayPos.subtract(this.center);
//        double bCoefficent = 2*rayDir.dotProduct(eMinusC);
//        double cCoefficent = eMinusC.dotProduct(eMinusC) - Math.pow(this.getRadius(),2);
//
//        double discriminant = Math.sqrt(Math.pow(bCoefficent,2)+4*aCoefficent*cCoefficent);
//        //No intersection
//        if (discriminant<0)
//        {
//            return Double.MAX_VALUE;
//        }
//        double singleSolution = (-bCoefficent)/(2*aCoefficent);
//        singleSolution = singleSolution > 0 ? singleSolution : Double.MAX_VALUE;
//        double solution1 = (-bCoefficent+discriminant)/(2*aCoefficent);
//        solution1 = solution1 > 0 ? solution1 : Double.MAX_VALUE;
//        double solution2 = (-bCoefficent-discriminant)/(2*aCoefficent);
//        solution2 = solution2 > 0 ? solution2 : Double.MAX_VALUE;
//
//        return discriminant==0 ? singleSolution : Math.min(solution1,solution2);
//    }

    @Override
    public double IntersectRay(Ray ray){
        Vector3D L = getCenter().subtract(ray.getPoint());
        double ca = L.dotProduct(ray.getDirection());
        if (ca < 0)
            return Double.MAX_VALUE;
        double distance_squared = L.dotProduct(L) - ca*ca;
        double r_sqrd = getRadius()*getRadius();
        if (distance_squared > r_sqrd)
            return Double.MAX_VALUE;
        double thc = Math.sqrt(r_sqrd - distance_squared);
        return ca - thc;
    }

    @Override
    public Vector3D getNormal(Ray ray, double distance) {
        return ray.getIntersection(distance)
                .subtract(getCenter())
                .normalize();
    }
}
