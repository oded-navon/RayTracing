package com.company;


public class Sphere
{
    private int[] center;
    private int radius;
    private int materialIndex;

    public Sphere(int[] center, int radius, int materialIndex)
    {
        this.center = center;
        this.radius = radius;
        this.materialIndex = materialIndex;
    }

    public int[] getCenter() {
        return center;
    }

    public int getRadius() {
        return radius;
    }

    public int getMaterialIndex() {
        return materialIndex;
    }
}
