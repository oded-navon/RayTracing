package RayTracing;


public class Settings
{
    private int RGB[];
    private int ShadowRay;
    private int MaxRecursionLevel;
    private int SuperSamplingLevel;


    public int[] getRGB() {
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

    public Settings(int[] RGB, int shadowRay, int maxRecursionLevel, int superSamplingLevel) {

        this.RGB = RGB;
        ShadowRay = shadowRay;
        MaxRecursionLevel = maxRecursionLevel;
        SuperSamplingLevel = superSamplingLevel;
    }
}
