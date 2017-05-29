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
        cam = scene.Camera;
        setTopLeft();
    }

    public PixelPlane(int hight, int width, Scene scene){
        imageHeight = hight;
        imageWidth = width;
        rgb = new int[imageWidth][imageHeight][3];
        cam = scene.Camera;
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
        int i,j,base = 0,0,0;
        byte[] rgbData = new byte[this.imageWidth * this.imageHeight * 3];
        for(i=0; i<getImageWidth(); i++){
            for(j=0; j<getImageHeight(); j++){
                base = (j * this.imageWidth + i) * 3;
                rgbData[base] = rgbData[i][j][RED];
                rgbData[base+GREEN] = rgbData[i][j][GREEN];
                rgbData[base+BLUE] = rgbData[i][j][BLUE];
            }
        }
        return rgbData;
    }

    public void setPixelColor(int x, int y, byte red, byte green, byte blue){

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
        Vector3D corner = center.add(stepUp(((double)imageHeight)/2))
                .subtract(stepRight(((double)imageWidth)/2);
        topLeft =  corner;
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
        Vector3D dir = getPixelPosition(x,y).subtract(cam.getPosition());
        return new Ray(getPixelPosition(x,y), dir);
    }
}
