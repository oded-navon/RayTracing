package RayTracing;


public class Material
{
    public Material(float[] diffuseColor, float[] specularColor, float[] reflectionColor, float phongSpecularityCoefficient, float transparency) {
        this.diffuseColor = diffuseColor;
        this.specularColor = specularColor;
        this.reflectionColor = reflectionColor;
        this.phongSpecularityCoefficient = phongSpecularityCoefficient;
        setTransparency(transparency);
    }

    //Properties
    public float [] diffuseColor;
    public float [] specularColor;
    public float [] reflectionColor;
    public float phongSpecularityCoefficient;
    private float transparency;

    public float[] getSpecularColor() {
        return specularColor;
    }

    public void setSpecularColor(float[] specularColor) {
        this.specularColor = specularColor;
    }

    public float[] getReflectionColor() {
        return reflectionColor;
    }

    public void setReflectionColor(float[] reflectionColor) {
        this.reflectionColor = reflectionColor;
    }

    public float getPhongSpecularityCoefficient() {
        return phongSpecularityCoefficient;
    }

    public void setPhongSpecularityCoefficient(float phongSpecularityCoefficient) {
        this.phongSpecularityCoefficient = phongSpecularityCoefficient;
    }

    public float[] getDiffuseColor() {
        return diffuseColor;
    }

    public void setDiffuseColor(float[] diffuseColor) {
        this.diffuseColor = diffuseColor;
    }

    public float getTransparency() {

        return transparency;
    }

    public void setTransparency(float transparency) {
        this.transparency = transparency;
    }
}
