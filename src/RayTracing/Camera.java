package RayTracing;


import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class Camera
{
    private Vector3D Position;
    private Vector3D LookAtPosition;
    private Vector3D UpVector;
    private float ScreenDistance;
    private float ScreenWidth;

    public Vector3D getPosition() {
        return Position;
    }

    public Vector3D getLookAtPosition() {
        return LookAtPosition;
    }

    public Vector3D getUpVector() {
        return UpVector;
    }

    public float getScreenDistance() {
        return ScreenDistance;
    }

    public float getScreenWidth() {
        return ScreenWidth;
    }

    public Camera(float[] position, float[] lookAtPosition, float[] upVector, float screenDistance, float screenWidth) {

        Position = new Vector3D(position[0], position[1], position[2]);
        LookAtPosition = new Vector3D(lookAtPosition[0], lookAtPosition[1], lookAtPosition[2]);
        UpVector = new Vector3D(upVector[0], upVector[1], upVector[2]);
        ScreenDistance = screenDistance;
        ScreenWidth = screenWidth;
    }

    public Camera(Camera cam, double width ) {

        Position = cam.getPosition();
        LookAtPosition = cam.getLookAtPosition();
        UpVector = cam.getUpVector();
        ScreenDistance = cam.getScreenDistance();
        ScreenWidth = (float) width;
    }

}
