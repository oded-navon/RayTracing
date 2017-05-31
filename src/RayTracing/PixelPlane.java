package RayTracing;

import org.apache.commons.math3.geometry.euclidean.threed.Vector;

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
    private Vector topLeft;
    private Vector horizontalstep;
    private Vector verticalstep;
    private Camera cam;


    public PixelPlane(Scene scene){
        rgb = new float[imageHeight][imageWidth][3];
        cam = scene.camera;
        setTopLeft();
    }

    public PixelPlane(int hight, int width, Scene scene){
        imageHeight = hight;
        imageWidth = width;
        rgb = new float[imageWidth][imageHeight][3];
        cam = scene.camera;
        setTopLeft();
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

    public Vector getPixelPosition(double x, double y){
        return topLeft.add(stepUp(-y))
                .add(stepRight(x));
    }

    private void setTopLeft(){
        Vector center = cam.getLookAtPosition()
                .subtract(cam.getPosition())
                .normalize()
                .scalarMultiply(cam.getScreenDistance());
        center = cam.getPosition()
                .add(center);
        topLeft = center.add(stepUp((((double)imageHeight)/2)-0.5))
                .subtract(stepRight((((double)imageWidth)/2)-0.5));
    }

    private Vector stepUp(double numOfSteps){
        if (verticalstep == null){
            double size = cam.getScreenWidth() / imageWidth  ;
            verticalstep = cam.getUpVector()
                    .normalize()
                    .scalarMultiply(size);
        }
        return verticalstep.scalarMultiply(numOfSteps);
    }

    private Vector stepRight(double numOfSteps){
        if (horizontalstep == null){
            double size = cam.getScreenWidth() / imageWidth  ;
            horizontalstep = cam.getLookAtPosition()
                    .subtract(cam.getPosition())
                    .crossProduct(cam.getUpVector())
                    .normalize()
                    .scalarMultiply(size);
        }
        return verticalstep.scalarMultiply(numOfSteps);
    }

    public Ray constructRayTroughPixel(int x, int y){
        Vector pos = getPixelPosition(x,y);
        return new Ray(pos, pos.subtract(cam.getPosition()));
    }
}
