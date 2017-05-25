package RayTracing;


public class Plane
{
    private float[] normal;
    private float offset;
    private int materialIndex;

    public float[] getNormal() {
        return normal;
    }

    public float getOffset() {
        return offset;
    }

    public int getMaterialIndex() {
        return materialIndex;
    }

    public Plane(float[] normal, float offset, int materialIndex) {
        this.normal = normal;

        this.offset = offset;
        this.materialIndex = materialIndex;
    }
}
