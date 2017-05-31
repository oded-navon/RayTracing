package RayTracing;


public class Settings
{
    private Color RGB;
    private int ShadowRay;
    private int MaxRecursionLevel;
    private int SuperSamplingLevel;


    public Color getRGB() {
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

        this.RGB = new Color(RGB[0], RGB[1],RGB[2]);
        ShadowRay = shadowRay;
        MaxRecursionLevel = maxRecursionLevel;
        SuperSamplingLevel = superSamplingLevel;
    }

}
