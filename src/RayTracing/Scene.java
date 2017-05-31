package RayTracing;


import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import sun.security.provider.SHA;

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

    public float[] computeRGBForRay(Ray ray){
        final float[] color = new float[1];
        float[] colors;
        float[] rgb = {0,0,0};
        double[] closest = rayIntersection(ray);
        // no intersction for the ray with the scene
        if(closest[1]<0)
            return settings.getRGB();

        // now we have a valid intersection and we should compute lighting
        Material material = materials.get(shapes.get((int)closest[0]).getMaterialIndex()-1);
        Vector3D hitPoint = ray.getIntersection(closest[1]);


//        for (Light light: lights){
//            Vector3D dir = hitPoint.subtract(
//                    light.getPosition())
//                    .normalize();
//            Ray lightRay = new Ray(light.getPosition(), dir);
//            closest = rayIntersection(lightRay);
//            // check that nothing is in the way from th light to the object
//            if(closest[1] >= light.getPosition().subtract(hitPoint).getNorm())
//////                colors = getColorForLight(hitPoint, light);
////                for(int k=0; k<3; k++){
////                    rgb[k] += colors[k];
////                }
//        }


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

    // get all the shapes intersecting
    private double[] rayIntersection(Ray ray ){
        int i = -1;
        double temp = Double.MAX_VALUE;
        List<Double> distances = shapes.stream()
                .map(shape -> shape.IntersectRay(ray))
                .collect(Collectors.toList());
        for(int k=0 ; k<distances.size();k++){
            if (distances.get(k)>0 && distances.get(k) < temp)
                temp = distances.get(k);
                i = k;
        }
        double[] res = {(double)i, temp};
        return res;
    }

//    private float[] getColorForLight(Vector3D hitPoint, Light light, Material material){
//        IntStream.range(0,3).forEach((int j) -> {
//            //background
//            color[0] = settings.getRGB()[j] * material.getTransparency() +
//                    (1-material.getTransparency()) * (material.getDiffuseColor()[j]+ material.getSpecularColor()[j]) +
//                    material.getReflectionColor()[j];
//            System.out.println(color[0]);
//            rgb[j] = color[0];
//        });
//    }

//    // compute diffuse using all lights
//    private float[] getDiffuseColor(Material material, Ray pixelRay, Vector hitPoint){
//
//    }

    private Color getSpecularColor(Shape shape, Ray pixelRay, double distance, Ray inRay){
        // let's compute R,
        Color result = new Color(0,0,0);
        Color oneLightSpec;
        Material material = materials.get(shape.getMaterialIndex()-1);
        double phong_coef = material.getPhongSpecularityCoefficient();
        for (Light light: lights){
            Vector3D shapeNormal = shape.getNormal(inRay, distance);
            Vector3D lightDir = light.getPosition()
                    .subtract(inRay.getIntersection(distance))
                    .normalize();
            Vector3D phongRay = shapeNormal.scalarMultiply(lightDir.dotProduct(shapeNormal)*2).subtract(lightDir).normalize();
            double R_V = phongRay.dotProduct(inRay.getDirection().negate().normalize());
            double phongExponent = R_V > 0 ? light.getSpecularIntensity()*Math.pow(R_V, phong_coef) : 0.0;
            result = result.add(material.getSpecularColor().mult(phong_coef));
        }
        return result;
    }
}
