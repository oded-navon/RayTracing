package RayTracing;



public class Light
{
    private float[] Position;
    private float[] RGB;
    private float specularIntensity;
    private float shadowIntensity;
    private float lightRadius;

    public Light(float[] position, float[] RGB, float specularIntensity, float shadowIntensity, float lightRadius) {
        Position = position;
        this.RGB = RGB;
        this.specularIntensity = specularIntensity;
        this.shadowIntensity = shadowIntensity;
        this.lightRadius = lightRadius;
    }
}
