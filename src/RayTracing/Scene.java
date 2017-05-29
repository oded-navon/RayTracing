package RayTracing;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Math.round;

public class Scene
{
    public Camera camera;
    public Settings settings;
    public List<Material> materials;
    public List<Shape> shapes = new ArrayList<>();
//    public List<Sphere> Spheres;
//    public List<Plane> Planes;
//    public List<Triangle> Triangles;
    public List<Light> lights;

    public Scene()
    {
        materials = new ArrayList<Material>();
        lights = new ArrayList<Light>();
    }

    public int[] intersectWithRay(Ray ray){
        final double[] color = new double[1];
        int[] rgb = {0,0,0};
        List<Double> distances = shapes.stream().map(shape -> shape.IntersectRay(ray)).collect(Collectors.toList());
        int i = IntStream.range(0,distances.size())
                .reduce(0,(a,b) -> distances.get(a) <= distances.get(b)? a : b);
        Material material = materials.get(shapes.get(i).getMaterialIndex());
        IntStream.range(0,3).forEach((int j) -> {
            //background
            color[0] = settings.getRGB()[j] * material.getTransparency() +
                    (1-material.getTransparency()) * (material.getDiffuseColor()[j]+ material.getSpecularColor()[j]) +
                    material.getReflectionColor()[j];
            System.out.println(color[0]);
            rgb[j] = Integer.min((int)round(color[0] *255),255);
            rgb[j] = Integer.max(rgb[j],0);
        });
        return rgb;
    }
}
