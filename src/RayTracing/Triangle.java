package RayTracing;


public class Triangle
{
    private float[] vertex1;
    private float[] vertex2;
    private float[] vertex3;
    private int materialIndex;


    public Triangle(float[] vertex1, float[] vertex2, float[] vertex3, int materialIndex) {
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
        this.vertex3 = vertex3;
        this.materialIndex = materialIndex;
    }

    public float[] getVertex1() {
        return vertex1;
    }

    public float[] getVertex2() {
        return vertex2;
    }

    public float[] getVertex3() {
        return vertex3;
    }

    public int getMaterialIndex() {
        return materialIndex;
    }
}
