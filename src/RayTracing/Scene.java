package RayTracing;


import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Scene {
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
        if (shape instanceof Sphere) {
            System.out.println("break point");
        }
        Material material = getMaterial(shape);
        HashMap<Light,Double> shadows = getSoftShadowCoefs(ray.getIntersection(closest.getDistance()));

        Color spec = getSpecularColor(shape,closest.getDistance(), ray, shadows);
        Color diffuse = getDiffuseColor(shape,ray,closest.getDistance(),shadows);
        Color transparent = getTransparencyColor(shape,ray,closest.getDistance(),recursion);
        Color reflect = getReflectionColor(shape,ray, closest.getDistance(), recursion);
        Color outputColor =  spec.add(diffuse).mult(1-material.getTransparency())
                .add(transparent)
                .add(reflect);

        return outputColor;
    }

    private HashMap<Light,Double> getSoftShadowCoefs(Vector3D hitPoint){
        HashMap<Light,Double> shadows = new HashMap<Light,Double>();
        for (Light light : lights){
            shadows.put(light, getSoftShadowForLight(hitPoint,light));
        }
        return shadows;
    }

    // get all the shapes intersecting
    private Intersection rayIntersection(Ray ray ){
        int i =-1;
        double temp = Double.MAX_VALUE;
        List<Double> distances = shapes.stream()
                .map(shape -> shape.IntersectRay(ray))
                .collect(Collectors.toList());
        for(int k=0 ; k<distances.size();k++){
            if (distances.get(k)>0 && distances.get(k) < Double.MAX_VALUE && distances.get(k) < temp) {
                temp = distances.get(k);
                i = k;
            }
        }
        Intersection res = new Intersection(i,temp);
        return res;
    }

    private boolean pointIsDark(Vector3D hitPoint, Light light){
        double distance = light.getPosition().distance(hitPoint);
        Ray lightRay = new Ray(light.getPosition(), hitPoint.subtract(light.getPosition()));
        Intersection closest = rayIntersection(lightRay);
        return Math.abs(closest.getDistance() - distance) > 0.1;
    }

    private double lightIntersectsWithPoint(Vector3D hitPoint, Vector3D light, double intensity)
    {
        double distance = light.distance(hitPoint), currentD = 0, res = 1 ;

        Ray lightRay = new Ray(light, hitPoint.subtract(light));
        Shape shape;
        for(int i =0 ; i<shapes.size(); i++){
            shape = shapes.get(i);
            currentD =  shape.IntersectRay(lightRay);
            if ( currentD > 0 && currentD + 0.001 < distance ){
                if (shape instanceof Sphere)
                    res *= getMaterial(shape).getTransparency();
                res *= getMaterial(shape).getTransparency();
            }
        }
        return res == 1 ? res : res + (1 - intensity);

    }

    private Color getSpecularColor(Shape shape, double distance, Ray inRay, HashMap<Light,Double> shadows){
        // let's compute R,
        Color result = new Color(0,0,0);
        Material material = getMaterial(shape);
        double phong_coef = material.getPhongSpecularityCoefficient();
        double shadow;
        for (Light light: lights){
            if ((shadow = shadows.get(light))<=0.00001)
                continue;
            Vector3D shapeNormal = shape.getNormal(inRay, distance);
            Vector3D lightDir = light.getPosition()
                    .subtract(inRay.getIntersection(distance))
                    .normalize();
            if (shapeNormal.dotProduct(lightDir) < 0)
                continue;
            Vector3D phongRay = shapeNormal.scalarMultiply(lightDir.dotProduct(shapeNormal)*2);
            phongRay = phongRay.subtract(lightDir); // .normalize();
            phongRay = inRay.getIntersection(distance).subtract(light.getPosition());
            phongRay = phongRay.add(-2*phongRay.dotProduct(shapeNormal),shapeNormal).normalize();
            double R_V = phongRay.dotProduct(inRay.getDirection().negate().normalize());
            double phongExponent = R_V > 0 ? light.getSpecularIntensity()*Math.pow(R_V, phong_coef) : 0.0;
            Color res = light.getRGB().mult(material.getSpecularColor()).mult(phongExponent).mult(shadow);
            result = result.add(res);
        }
        return result;
    }

    private Material getMaterial(Shape shape){
     return materials.get(shape.getMaterialIndex()-1);
    }

    private Color getReflectionColor(Shape shape, Ray ray, double distance, int recursion){
        if(recursion >= settings.getMaxRecursionLevel())
            return settings.getRGB();
        Vector3D N = shape.getNormal(ray,distance);
        Vector3D reflectionDir = ray.getDirection().subtract(2*ray.getDirection().dotProduct(N),N);
        Ray reflection = new Ray(ray.getIntersection(distance*0.99),reflectionDir);
        Color reflection_Color = getMaterial(shape).getReflectionColor();
        return computeRGBForRay(reflection, recursion + 1).mult(reflection_Color);
    }

    private Color getDiffuseColor(Shape shape, Ray inRay, double distance,HashMap<Light,Double> shadows )
    {
        Color finalColor = new Color(0,0,0);
        double shadow;
        for (Light light: lights){
            if ((shadow = shadows.get(light))<=0.00001)
                continue;
            finalColor = finalColor.add(getSingleLightDiffuseColor(shape, inRay, distance, light));//.mult(shadow));
        }
        return finalColor;
    }

    private Color getSingleLightDiffuseColor(Shape shape, Ray inRay, double distance,Light light)
    {
        //I_D = K_D* (N^ dot L^) * I_L
        Vector3D hit = inRay.getIntersection(distance);
        Vector3D normalizedReturningLightVectorL = light.getPosition().subtract(hit).normalize();
        Vector3D shapeNormal = shape.getNormal(inRay, distance);
        double nDotL = shapeNormal.dotProduct(normalizedReturningLightVectorL);
        if (nDotL < 0)
            return new Color(0,0,0);
        Color shapeMaterialDiffuseColor = new Color(getMaterial(shape).getDiffuseColor());

        return  shapeMaterialDiffuseColor.mult(nDotL).mult(light.getRGB());
    }


    private double getSoftShadowForLight(Vector3D hitPointer, Light light)
    {
        Vector3D lightVectorToHitPoint = hitPointer.subtract(light.getPosition());

        SoftShadows lightPlane = new SoftShadows(light,lightVectorToHitPoint);

        List<Vector3D> lightVectors = lightPlane.generateVectors(light,settings);

        double numOfIntersectingLights = lightVectors.stream()
                .map(lightVector -> lightIntersectsWithPoint(hitPointer,lightVector, light.getShadowIntensity()))
                .reduce(0.0, (a,b)-> a+b);

        return Double.min(1, numOfIntersectingLights/(Math.pow(settings.getShadowRay(),2)));
    }

    private Color getTransparencyColor(Shape shape, Ray ray, double distance, int recursion)
    {
        float transCoeff = getMaterial(shape).getTransparency();
        if(recursion >= settings.getMaxRecursionLevel())
            return settings.getRGB();
        if (transCoeff <= 0)
            return new Color(0,0,0);
        Ray forward = new Ray(ray.getIntersection(distance*1.01) , ray.getDirection());
        Color res = computeRGBForRay(forward, recursion+1);
        return res.mult(transCoeff);
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
