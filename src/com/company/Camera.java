package com.company;


public class Camera
{
    private int[] Position;
    private int[] LookAtPosition;
    private int[] UpVector;
    private float ScreenDistance;
    private float ScreenWidth;

    public int[] getPosition() {
        return Position;
    }

    public int[] getLookAtPosition() {
        return LookAtPosition;
    }

    public int[] getUpVector() {
        return UpVector;
    }

    public float getScreenDistance() {
        return ScreenDistance;
    }

    public float getScreenWidth() {
        return ScreenWidth;
    }

    public Camera(int[] position, int[] lookAtPosition, int[] upVector, float screenDistance, float screenWidth) {

        Position = position;
        LookAtPosition = lookAtPosition;
        UpVector = upVector;
        ScreenDistance = screenDistance;
        ScreenWidth = screenWidth;
    }
}
