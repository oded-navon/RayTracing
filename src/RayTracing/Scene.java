package RayTracing;


import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public Color computeRGBForRay(Ray ray, int recursion){
        if (recursion >= settings.getMaxRecursionLevel())
                return settings.getRGB();

        Intersection closest = rayIntersection(ray);
        // no intersction for the ray with the scene
        if(closest.getShapeIdx() < 0)
            return settings.getRGB();

        // now we have a valid intersection and we should compute lighting
        Shape shape = shapes.get(closest.getShapeIdx()); // this convertion might cause problems
        Material material = getMaterial(shape);
        Vector3D hitPoint = ray.getIntersection(closest.getDistance());
        Color outputColor =  settings.getRGB().mult(material.getTransparency()) // backgorund color
                .add( // diffuse + specular
                        getSpecularColor(shape,closest.getDistance(), ray)
//                .add(getDiffuseColor()) // diffuse color
                        .mult(1-material.getTransparency())
                ).add(// reflection color
                    getReflectionColor(shape,ray, closest.getDistance(), recursion)
                );
        return outputColor;
    }

    // get all the shapes intersecting
    private Intersection rayIntersection(Ray ray ){
        int i =-1;
        double temp = Double.MAX_VALUE;
        List<Double> distances = shapes.stream()
                .map(shape -> shape.IntersectRay(ray))
                .collect(Collectors.toList());
        for(int k=0 ; k<distances.size();k++){
            if (distances.get(k)>0 && distances.get(k) < temp) {
                temp = distances.get(k);
                i = k;
            }
        }
        Intersection res = new Intersection(i,temp);
        return res;
    }

    private Color getSpecularColor(Shape shape, double distance, Ray inRay){
        // let's compute R,
        Color result = new Color(0,0,0);
        Color oneLightSpec;
        Material material = getMaterial(shape);
        double phong_coef = material.getPhongSpecularityCoefficient();
        for (Light light: lights){
            Vector3D shapeNormal = shape.getNormal(inRay, distance);
            Vector3D lightDir = light.getPosition()
                    .subtract(inRay.getIntersection(distance))
                    .normalize();
            Vector3D phongRay = shapeNormal.scalarMultiply(lightDir.dotProduct(shapeNormal)*2).subtract(lightDir).normalize();
            double R_V = phongRay.dotProduct(inRay.getDirection().negate().normalize());
            double phongExponent = R_V > 0 ? light.getSpecularIntensity()*Math.pow(R_V, phong_coef) : 0.0;
            result = result.add(light.getRGB().mult(phongExponent));
        }
        return result;
    }

    private Material getMaterial(Shape shape){
     return materials.get(shape.getMaterialIndex()-1);
    }

    private Color getReflectionColor(Shape shape, Ray ray, double distance, int recursion){
        if(recursion >= settings.getMaxRecursionLevel())
            return settings.getRGB();

        Ray reflection = new Ray(ray.getIntersection(distance*0.99), ray.getDirection());
        return computeRGBForRay(reflection, recursion + 1);
    }

    class Intersection{
        private int shapeIdx = -1;
        private double distance = -1;

        Intersection(int shape, double distance){
            this.setShapeIdx(shape);
            this.setDistance(distance);
        }

        public int getShapeIdx() {
            return shapeIdx;
        }

        public void setShapeIdx(int shapeIdx) {
            this.shapeIdx = shapeIdx;
        }

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }
    }
}
