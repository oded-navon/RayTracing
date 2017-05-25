package com.company;


public class Material
{
    public Material(int[] diffuseColor, int[] specularColor, int[] reflectionColor, float phongSpecularityCoefficient, float transparency) {
        DiffuseColor = diffuseColor;
        SpecularColor = specularColor;
        ReflectionColor = reflectionColor;
        PhongSpecularityCoefficient = phongSpecularityCoefficient;
        Transparency = transparency;
    }

    //Properties
    public int [] DiffuseColor;
    public int [] SpecularColor;
    public int [] ReflectionColor;
    public float PhongSpecularityCoefficient;
    public float Transparency;







}
