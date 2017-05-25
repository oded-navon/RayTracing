package RayTracing;


public class Plane
{
    private int[] normal;
    private int offset;
    private int materialIndex;

    public int[] getNormal() {
        return normal;
    }

    public int getOffset() {
        return offset;
    }

    public int getMaterialIndex() {
        return materialIndex;
    }

    public Plane(int[] normal, int offset, int materialIndex) {
        this.normal = normal;

        this.offset = offset;
        this.materialIndex = materialIndex;
    }
}
