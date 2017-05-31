package RayTracing;


import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public interface Shape
{
    double IntersectRay(Ray ray);

    Vector3D getNormal(Ray ray, double distance);

    int getMaterialIndex();
}
