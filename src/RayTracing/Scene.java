package RayTracing;


import java.util.ArrayList;
import java.util.List;

public class Scene
{
    public Camera Camera;
    public Settings Settings;
    public List<Material> Materials;
    public List<Sphere> Spheres;
    public List<Plane> Planes;
    public List<Triangle> Triangles;
    public List<Light> Lights;

    public Scene()
    {
        Materials = new ArrayList<Material>();
        Spheres = new ArrayList<Sphere>();
        Planes = new ArrayList<Plane>();
        Triangles = new ArrayList<Triangle>();
        Lights = new ArrayList<Light>();
    }


}
