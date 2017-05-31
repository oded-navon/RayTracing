package RayTracing;


import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class Light
{
    private Vector3D Position;
    private Color RGB;
    private float specularIntensity;
    private float shadowIntensity;
    private float lightRadius;

    public Vector3D getPosition() {
        return Position;
    }

    public void setPosition(Vector3D position) {
        Position = position;
    }

    public Color getRGB() {
        return RGB;
    }

    public void setRGB(float[] RGB) {
        this.RGB = new Color(RGB);
    }


    public float getSpecularIntensity() {
        return specularIntensity;
    }

    public void setSpecularIntensity(float specularIntensity) {
        this.specularIntensity = specularIntensity;
    }

    public float getShadowIntensity() {
        return shadowIntensity;
    }

    public void setShadowIntensity(float shadowIntensity) {
        this.shadowIntensity = shadowIntensity;
    }

    public float getLightRadius() {
        return lightRadius;
    }

    public void setLightRadius(float lightRadius) {
        this.lightRadius = lightRadius;
    }

    public Light(float[] position, float[] RGB, float specularIntensity, float shadowIntensity, float lightRadius) {
        Position = new Vector3D(position[0], position[1], position[2]);
        this.RGB = new Color(RGB);
        this.specularIntensity = specularIntensity;

        this.shadowIntensity = shadowIntensity;
        this.lightRadius = lightRadius;
    }
}
