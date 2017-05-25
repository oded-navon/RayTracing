package RayTracing;


public class Camera
{
    private float[] Position;
    private float[] LookAtPosition;
    private float[] UpVector;
    private float ScreenDistance;
    private float ScreenWidth;

    public float[] getPosition() {
        return Position;
    }

    public float[] getLookAtPosition() {
        return LookAtPosition;
    }

    public float[] getUpVector() {
        return UpVector;
    }

    public float getScreenDistance() {
        return ScreenDistance;
    }

    public float getScreenWidth() {
        return ScreenWidth;
    }

    public Camera(float[] position, float[] lookAtPosition, float[] upVector, float screenDistance, float screenWidth) {

        Position = position;
        LookAtPosition = lookAtPosition;
        UpVector = upVector;
        ScreenDistance = screenDistance;
        ScreenWidth = screenWidth;
    }
}
