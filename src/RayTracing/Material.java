package RayTracing;


public class Material
{
    public Material(float[] diffuseColor, float[] specularColor, float[] reflectionColor, float phongSpecularityCoefficient, float transparency) {
        DiffuseColor = diffuseColor;
        SpecularColor = specularColor;
        ReflectionColor = reflectionColor;
        PhongSpecularityCoefficient = phongSpecularityCoefficient;
        Transparency = transparency;
    }

    //Properties
    public float [] DiffuseColor;
    public float [] SpecularColor;
    public float [] ReflectionColor;
    public float PhongSpecularityCoefficient;
    public float Transparency;







}
