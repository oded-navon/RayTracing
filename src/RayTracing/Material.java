package RayTracing;


public class Material
{
    public Material(float[] diffuseColor, float[] specularColor, float[] reflectionColor, float phongSpecularityCoefficient, float transparency) {
        this.diffuseColor = diffuseColor;
        this.specularColor = new Color(specularColor[0], specularColor[1],specularColor[2]);
        this.reflectionColor = reflectionColor;
        this.phongSpecularityCoefficient = phongSpecularityCoefficient;
        setTransparency(transparency);
    }

    //Properties
    public float [] diffuseColor;
    public Color specularColor;
    public float [] reflectionColor;
    public float phongSpecularityCoefficient;
    private float transparency;

    public Color getSpecularColor() {
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
