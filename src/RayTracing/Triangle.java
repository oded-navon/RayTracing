package RayTracing;


import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class Triangle implements Shape
{
    private Vector3D vertex1;
    private Vector3D vertex2;
    private Vector3D vertex3;
    private int materialIndex;
    private Plane trianglePlane;


    public Triangle(float[] vertex1, float[] vertex2, float[] vertex3, int materialIndex) {
        this.vertex1 = new Vector3D(vertex1[0],vertex1[1],vertex1[2]);
        this.vertex2 = new Vector3D(vertex2[0],vertex2[1],vertex2[2]);
        this.vertex3 = new Vector3D(vertex3[0],vertex3[1],vertex3[2]);
        this.materialIndex = materialIndex;
        Vector3D normal = this.getNormal(null,0);

        this.trianglePlane = new Plane(normal,normal.dotProduct(this.getVertex1()),this.getMaterialIndex());
    }

    public Vector3D getVertex1() {
        return vertex1;
    }

    public Vector3D getVertex2() {
        return vertex2;
    }

    public Vector3D getVertex3() {
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
            return -1;
        }
        //check that the intersection is inside the triangle
        Vector3D vec1 = vertex1.subtract(ray.getPoint());
        Vector3D vec2 = vertex2.subtract(ray.getPoint());
        Vector3D vec3 = vertex3.subtract(ray.getPoint());
        //first side
        Vector3D norm1 = vec1.crossProduct(vec2).negate().normalize();
        if (ray.getDirection().dotProduct(norm1)<0)
        {
            return -1;
        }
        //second side
        Vector3D norm2 = vec2.crossProduct(vec3).negate().normalize();
        if (ray.getDirection().dotProduct(norm2)<0)
        {
            return -1;
        }
        //third side
        Vector3D norm3 = vec3.crossProduct(vec1).negate().normalize();
        if (ray.getDirection().dotProduct(norm3)<0)
        {
            return -1;
        }
        return planeInter;
    }

    @Override
    public Vector3D getNormal(Ray ray, double distance) {
        Vector3D v1 = getVertex1().subtract(getVertex2());
        return getVertex3().subtract(getVertex2())
                .crossProduct(v1)
                .negate()
                .normalize();
    }
}
