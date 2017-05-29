package RayTracing;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    public float[] intersectWithRay(Ray ray){
        final float[] color = new float[1];
        int i = -1;
        double temp = Double.MAX_VALUE;
        float[] rgb = {0,0,0};
        List<Double> distances = shapes.stream().map(shape -> shape.IntersectRay(ray)).collect(Collectors.toList());
        for(int k=0 ; k<distances.size();k++){
            if (distances.get(k)>0 && distances.get(k) < temp)
                temp = distances.get(k);
                i = k;
        }

        if(i<0)
            return settings.getRGB();

        // now we have a valid intersection and we should compute lighting
        Material material = materials.get(shapes.get(i).getMaterialIndex());


        IntStream.range(0,3).forEach((int j) -> {
            //background
            color[0] = settings.getRGB()[j] * material.getTransparency() +
                    (1-material.getTransparency()) * (material.getDiffuseColor()[j]+ material.getSpecularColor()[j]) +
                    material.getReflectionColor()[j];
            System.out.println(color[0]);
            rgb[j] = color[0];
        });
        return rgb;
    }
}
