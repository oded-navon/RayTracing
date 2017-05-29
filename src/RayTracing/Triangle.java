package RayTracing;


import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class Triangle implements Shape
{
    private Vector3D vertex1;
    private Vector3D vertex2;
    private Vector3D vertex3;
    private int materialIndex;


    public Triangle(float[] vertex1, float[] vertex2, float[] vertex3, int materialIndex) {
        this.vertex1 = new Vector3D(vertex1[0],vertex1[1],vertex1[2]);
        this.vertex1 = new Vector3D(vertex2[0],vertex2[1],vertex2[2]);
        this.vertex1 = new Vector3D(vertex3[0],vertex3[1],vertex3[2]);
        this.materialIndex = materialIndex;
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
    public float IntersectRay(Ray ray) {

        return 0;
    }
}
