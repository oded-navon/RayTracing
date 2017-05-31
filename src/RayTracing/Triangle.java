package RayTracing;


import org.apache.commons.math3.geometry.euclidean.threed.Vector;

public class Triangle implements Shape
{
    private Vector vertex1;
    private Vector vertex2;
    private Vector vertex3;
    private int materialIndex;
    private Plane trianglePlane;


    public Triangle(float[] vertex1, float[] vertex2, float[] vertex3, int materialIndex) {
        this.vertex1 = new Vector(vertex1[0],vertex1[1],vertex1[2]);
        this.vertex1 = new Vector(vertex2[0],vertex2[1],vertex2[2]);
        this.vertex1 = new Vector(vertex3[0],vertex3[1],vertex3[2]);
        this.materialIndex = materialIndex;
        Vector normal = this.getNormal(null,0);

        this.trianglePlane = new Plane(normal,normal.dotProduct(this.getVertex1()),this.getMaterialIndex());
    }

    public Vector getVertex1() {
        return vertex1;
    }

    public Vector getVertex2() {
        return vertex2;
    }

    public Vector getVertex3() {
        return vertex3;
    }

    public int getMaterialIndex() {
        return materialIndex;
    }

    @Override
    public double IntersectRay(Ray ray)
    {
        double planeInter = this.trianglePlane.IntersectRay(ray);
        if (planeInter==Double.MAX_VALUE || planeInter<0)
        {
            return Double.MAX_VALUE;
        }
        //check that the intersection is inside the triangle
        Vector vec1 = vertex1.subtract(ray.getPoint());
        Vector vec2 = vertex2.subtract(ray.getPoint());
        Vector vec3 = vertex3.subtract(ray.getPoint());
        //first side
        Vector norm1 = vec1.crossProduct(vec2).normalize();
        if (ray.getDirection().dotProduct(norm1)<0)
        {
            return Double.MAX_VALUE;
        }
        //second side
        Vector norm2 = vec2.crossProduct(vec3).normalize();
        if (ray.getDirection().dotProduct(norm2)<0)
        {
            return Double.MAX_VALUE;
        }
        //third side
        Vector norm3 = vec3.crossProduct(vec1).normalize();
        if (ray.getDirection().dotProduct(norm3)<0)
        {
            return Double.MAX_VALUE;
        }
        return planeInter;
    }

    @Override
    public Vector getNormal(Ray ray, double distance) {
        Vector v1 = getVertex1().subtract(getVertex2());
        return getVertex3().subtract(getVertex2())
                .crossProduct(v1)
                .normalize();
    }
}
