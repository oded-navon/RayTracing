package RayTracing;



public class Light
{
    private int[] Position;
    private int[] RGB;
    private float specularIntensity;
    private float shadowIntensity;
    private float lightRadius;

    public Light(int[] position, int[] RGB, float specularIntensity, float shadowIntensity, float lightRadius) {
        Position = position;
        this.RGB = RGB;
        this.specularIntensity = specularIntensity;
        this.shadowIntensity = shadowIntensity;
        this.lightRadius = lightRadius;
    }
}
