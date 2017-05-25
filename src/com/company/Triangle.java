package com.company;


public class Triangle
{
    private int[] vertex1;
    private int[] vertex2;
    private int[] vertex3;
    private int materialIndex;


    public Triangle(int[] vertex1, int[] vertex2, int[] vertex3, int materialIndex) {
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
        this.vertex3 = vertex3;
        this.materialIndex = materialIndex;
    }

    public int[] getVertex1() {
        return vertex1;
    }

    public int[] getVertex2() {
        return vertex2;
    }

    public int[] getVertex3() {
        return vertex3;
    }

    public int getMaterialIndex() {
        return materialIndex;
    }
}
