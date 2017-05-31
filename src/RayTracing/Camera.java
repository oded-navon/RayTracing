package RayTracing;


import org.apache.commons.math3.geometry.euclidean.threed.Vector;

public class Camera
{
    private Vector Position;
    private Vector LookAtPosition;
    private Vector UpVector;
    private float ScreenDistance;
    private float ScreenWidth;

    public Vector getPosition() {
        return Position;
    }

    public Vector getLookAtPosition() {
        return LookAtPosition;
    }

    public Vector getUpVector() {
        return UpVector;
    }

    public float getScreenDistance() {
        return ScreenDistance;
    }

    public float getScreenWidth() {
        return ScreenWidth;
    }

    public Camera(float[] position, float[] lookAtPosition, float[] upVector, float screenDistance, float screenWidth) {

        Position = new Vector(position[0], position[1], position[2]);
        LookAtPosition = new Vector(lookAtPosition[0], lookAtPosition[1], lookAtPosition[2]);
        UpVector = new Vector(upVector[0], upVector[1], upVector[2]);
        ScreenDistance = screenDistance;
        ScreenWidth = screenWidth;
    }

}
