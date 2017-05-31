package RayTracing;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.round;


/**
 * Created by orrbarkat on 27/05/2017.
 */
public class PixelPlane {
    public static final byte RED = 0;
    public static final byte GREEN = 1;
    public static final byte BLUE = 2;

    private int imageWidth = 500;
    private int imageHeight = 500;
    private float[][][] rgb;
    private Vector3D topLeft;
    private Vector3D horizontalstep;
    private Vector3D verticalstep;
    private Camera cam;
    private Light light;


    public PixelPlane(Scene scene){
        rgb = new float[imageHeight][imageWidth][3];
        cam = scene.camera;
        setTopLeft();
    }



    public PixelPlane(int height, int width, Camera camera){
        imageHeight = height;
        imageWidth = width;
        rgb = new float[imageWidth][imageHeight][3];
        cam = camera;
        setTopLeft(camera);
    }

    public PixelPlane(Light lightRay,Vector3D lightVectorToHitPoint,){
        light = lightRay;
        setTopLeft(lightVectorToHitPoint);
        setSteps(lightVectorToHitPoint);
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    public byte[] getAsByteArray(){
        int i,j,base = 0;
        byte[] rgbData = new byte[this.imageWidth * this.imageHeight * 3];
        for(i=0; i<getImageWidth(); i++){
            for(j=0; j<getImageHeight(); j++){
                base = (j * this.imageWidth + i) * 3;
                rgbData[base] = packToByte(rgb[i][j][RED]);
                rgbData[base+GREEN] = packToByte(rgb[i][j][GREEN]);
                rgbData[base+BLUE] = packToByte(rgb[i][j][BLUE]);
            }
        }
        return rgbData;
    }

    private byte packToByte(double color){
        double byteColor = Double.min(Double.max(0, color), 1);
        return (byte) round(255*byteColor);
    }

    public void setPixelColor(int x, int y, float[] color){
        rgb[x][y] = color;
    }

    public float[] getPixelColor(int x, int y){
        return rgb[x][y];
    }

    public Vector3D getPixelPosition(double x, double y){
        return topLeft.add(stepUp(-y))
                .add(stepRight(x));
    }

    private void setTopLeft(Camera camera){
        Vector3D center = camera.getLookAtPosition()
                .subtract(camera.getPosition())
                .normalize()
                .scalarMultiply(camera.getScreenDistance());
        center = camera.getPosition()
                .add(center);
        topLeft = center.add(stepUp((((double)imageHeight)/2)-0.5))
                .subtract(stepRight((((double)imageWidth)/2)-0.5));
    }


    private void setSteps(Vector3D lightVectorToHitPoint)
    {
        horizontalstep = lightVectorToHitPoint.crossProduct(light.getPosition()).normalize();
        verticalstep = lightVectorToHitPoint.crossProduct(horizontalstep).normalize();
    }
    private void setTopLeft(Vector3D lightVectorToHitPoint){
        topLeft = lightVectorToHitPoint.add(stepUpLight((((double)light.getLightRadius())/2)-0.5))
                .add(stepLeftLight((((double)light.getLightRadius())/2)-0.5));
    }

    private Vector3D stepUpLight(double numOfSteps){
        return verticalstep.scalarMultiply(numOfSteps);
    }
    private Vector3D stepDownLight(double numOfSteps){
        return verticalstep.scalarMultiply(-numOfSteps);
    }
    private Vector3D stepRightLight(double numOfSteps){
        return horizontalstep.scalarMultiply(numOfSteps);
    }
    private Vector3D stepLeftLight(double numOfSteps){
        return horizontalstep.scalarMultiply(-numOfSteps);
    }


    public List<Vector3D> generateVectors(Light light,Settings settings)
    {
        List<Vector3D> result = new ArrayList<>();
        Random randGen = new Random();
        float sizeOfCell = light.getLightRadius()/settings.getShadowRay();

        for (int i=0 ; i<settings.getShadowRay() ; i++)
        {
            for (int j = 0; j < settings.getShadowRay(); j++)
            {
                //nextDouble returns a number between 0.0 and 1.0
                double x = randGen.nextDouble();
                double y = randGen.nextDouble();
                while (x == 0.0) x = randGen.nextDouble();
                while (y == 0.0) y = randGen.nextDouble();

                // Go to the cell
                Vector3D currentLight = topLeft.add(stepDownLight(j * sizeOfCell)).add((stepRightLight(i * sizeOfCell)));
                // move to random point in it
                currentLight = currentLight.add(stepDownLight(x * sizeOfCell)).add((stepRightLight(y * sizeOfCell)));
                //add to result
                result.add(currentLight);
            }
        }
    }



    private Vector3D stepUp(double numOfSteps){
        if (verticalstep == null){
            double size = cam.getScreenWidth() / imageWidth  ;
            verticalstep = cam.getUpVector()
                    .normalize()
                    .scalarMultiply(size);
        }
        return verticalstep.scalarMultiply(numOfSteps);
    }

    private Vector3D stepRight(double numOfSteps){
        if (horizontalstep == null){
            double size = cam.getScreenWidth() / imageWidth  ;
            horizontalstep = cam.getLookAtPosition()
                    .subtract(cam.getPosition())
                    .crossProduct(cam.getUpVector())
                    .negate()
                    .normalize()
                    .scalarMultiply(size);
        }
        return horizontalstep.scalarMultiply(numOfSteps);
    }

    public Ray constructRayTroughPixel(int x, int y){
        Vector3D pos = getPixelPosition(x,y);
        return new Ray(pos, pos.subtract(cam.getPosition()));
    }
}
