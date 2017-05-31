package RayTracing;


import org.apache.commons.math3.geometry.euclidean.threed.Vector;

public interface Shape
{
    double IntersectRay(Ray ray);

    Vector getNormal(Ray ray, double distance);

    int getMaterialIndex();
}
