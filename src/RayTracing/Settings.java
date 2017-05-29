package RayTracing;


public class Settings
{
    private float RGB[];
    private int ShadowRay;
    private int MaxRecursionLevel;
    private int SuperSamplingLevel;


    public float[] getRGB() {
        return RGB;
    }

    public int getShadowRay() {
        return ShadowRay;
    }

    public int getMaxRecursionLevel() {
        return MaxRecursionLevel;
    }

    public int getSuperSamplingLevel() {
        return SuperSamplingLevel;
    }

    public Settings(float[] RGB, int shadowRay, int maxRecursionLevel, int superSamplingLevel) {

        this.RGB = RGB;
        ShadowRay = shadowRay;
        MaxRecursionLevel = maxRecursionLevel;
        SuperSamplingLevel = superSamplingLevel;
    }

}
