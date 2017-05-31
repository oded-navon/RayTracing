package RayTracing;


import org.apache.commons.math3.geometry.euclidean.threed.Vector;

public class Light
{
    private Vector Position;
    private float[] RGB;
    private float specularIntensity;
    private float shadowIntensity;
    private float lightRadius;

    public Vector getPosition() {
        return Position;
    }

    public void setPosition(Vector position) {
        Position = position;
    }

    public float[] getRGB() {
        return RGB;
    }

    public void setRGB(float[] RGB) {
        this.RGB = RGB;
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
        Position = new Vector(position[0], position[1], position[2]);
        this.RGB = RGB;
        this.specularIntensity = specularIntensity;

        this.shadowIntensity = shadowIntensity;
        this.lightRadius = lightRadius;
    }
}
