package RayTracing;


public class Settings
{
    private Color BackgroundColor;
    private int ShadowRay;
    private int MaxRecursionLevel;
    private int SuperSamplingLevel;


    public Color getBackgroundColor() {
        return BackgroundColor;
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

    public Settings(float[] BackgroundColor, int shadowRay, int maxRecursionLevel, int superSamplingLevel) {

        this.BackgroundColor = new Color(BackgroundColor[0], BackgroundColor[1], BackgroundColor[2]);
        ShadowRay = shadowRay;
        MaxRecursionLevel = maxRecursionLevel;
        SuperSamplingLevel = superSamplingLevel;
    }

}
