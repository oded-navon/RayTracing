package RayTracing;


public class Sphere implements Shape
{
    private float[] center;
    private float radius;
    private int materialIndex;

    public Sphere(float[] center, float radius, int materialIndex)
    {
        this.center = center;
        this.radius = radius;
        this.materialIndex = materialIndex;
    }

    public float[] getCenter() {
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
    public float IntersectRay(Ray ray)
    {

    }
}
