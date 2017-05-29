package RayTracing;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;


/**
 * Created by orrbarkat on 27/05/2017.
 */
public class PixelPlane {
    public static final byte RED = 0;
    public static final byte GREEN = 1;
    public static final byte BLUE = 2;

    private int imageWidth = 500;
    private int imageHeight = 500;
    private int[][][] rgb;
    private Vector3D topLeft;
    private Vector3D horizontalstep;
    private Vector3D verticalstep;
    private Camera cam;


    public PixelPlane(Scene scene){
        rgb = new int[imageHeight][imageWidth][3];
        cam = scene.camera;
        setTopLeft();
    }

    public PixelPlane(int hight, int width, Scene scene){
        imageHeight = hight;
        imageWidth = width;
        rgb = new int[imageWidth][imageHeight][3];
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
                rgbData[base] = (byte) rgb[i][j][RED];
                rgbData[base+GREEN] = (byte)rgb[i][j][GREEN];
                rgbData[base+BLUE] = (byte)rgb[i][j][BLUE];
            }
        }
        return rgbData;
    }

    public void setPixelColor(int x, int y, int[] color){
        rgb[x][y] = color;
    }

    public int[] getPixelColor(int x, int y){
        return rgb[x][y];
    }

    public Vector3D getPixelPosition(double x, double y){
        return topLeft.add(stepUp(-y))
                .add(stepRight(x));
    }

    private void setTopLeft(){
        Vector3D center = cam.getLookAtPosition()
                .subtract(cam.getPosition())
                .normalize()
                .scalarMultiply(cam.getScreenDistance());
        center = cam.getPosition()
                .add(center);
        topLeft = center.add(stepUp((((double)imageHeight)/2)-0.5))
                .subtract(stepRight((((double)imageWidth)/2)-0.5));
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
                    .normalize()
                    .scalarMultiply(size);
        }
        return verticalstep.scalarMultiply(numOfSteps);
    }

    public Ray constructRayTroughPixel(int x, int y){
        Vector3D pos = getPixelPosition(x,y);
        return new Ray(pos, pos.subtract(cam.getPosition()));
    }
}
