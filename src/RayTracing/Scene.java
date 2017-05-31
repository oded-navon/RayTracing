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
        Color outputColor =  getSpecularColor(shape,closest.getDistance(), ray).add(getDiffuseColor(shape,ray,closest.getDistance())).mult(1-material.getTransparency()
                ).add(getTransperencyColor(shape,ray,closest.getDistance(),recursion)
                ).add(getReflectionColor(shape,ray, closest.getDistance(), recursion)
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
        Ray lightRay = new Ray(light.getPosition(), light.getPosition().subtract(hitPoint));
        Intersection closest = rayIntersection(lightRay);
        return Math.abs(closest.getDistance()- distance) > 0.1;
    }

    private Color getSpecularColor(Shape shape, double distance, Ray inRay){
        // let's compute R,
        Color result = new Color(0,0,0);
        Material material = getMaterial(shape);
        double phong_coef = material.getPhongSpecularityCoefficient();
        for (Light light: lights){
            if (pointIsDark(inRay.getIntersection(distance), light))
                continue;
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
        Vector3D N = shape.getNormal(ray,distance);
        Vector3D reflectionDir = ray.getDirection().subtract(2*ray.getDirection().dotProduct(N),N);
        Ray reflection = new Ray(ray.getIntersection(distance*0.99),reflectionDir);
        Color reflection_Color = getMaterial(shape).getReflectionColor();
        return computeRGBForRay(reflection, recursion + 1).mult(reflection_Color);
    }
    private Color getDiffuseColor(Shape shape, Ray inRay, double distance)
    {
        Color finalColor = new Color(new float[] {0,0,0});
        for (Light light : lights)
        {
            if (pointIsDark(inRay.getIntersection(distance), light))
                continue;
            finalColor = finalColor.add(getSingleLightDiffuseColor(shape, inRay, distance, light));
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

        Color shapeMaterialDiffuseColor = new Color(getMaterial(shape).getDiffuseColor());

        return  shapeMaterialDiffuseColor.mult(nDotL).mult(light.getRGB());
    }


    // compute diffuse using all lights
//    private float[] getSoftShadowForLight(Material material, Ray pixelRay, Vector3D hitPoint,Light light)
//    {
//        double lightRadius = light.getLightRadius();
//        double numOfCells = lightRadius/settings.getShadowRay();
//
////        Plane perpenPlane = new Plane(light.getPosition().normalize(),;
////
////        Vector3D startingLight = light.getPosition().subtract(
////                upVector.scalarMultiply(lightRadius/2)).subtract(
////                ViewerVector.scalarMultiply(lightRadius/2));
//
//        List<Vector3D> lightVectors;
//
//        Random randGen = new Random();
//        for (int i=0 ; i<settings.getShadowRay() ; i++)
//        {
//            for (int j = 0; j < settings.getShadowRay(); j++)
//            {
//                double x = randGen.nextDouble();
//                double y = randGen.nextDouble();
//                while (x == 0.0) x = randGen.nextDouble();
//                while (y == 0.0) y = randGen.nextDouble();
//
//
//
//                Vector3D normal = light.getPosition().subtract(hitPoint).normalize();
//                Vector3D upVector = camera.getUpVector().crossProduct(normal).normalize();
//                Vector3D ViewerVector = normal.crossProduct(upVector);
//
//
////                Vector3D currentLight = startingLight.add(upVector.scalarMultiply(j * numOfCells)).add(ViewerVector.scalarMultiply(i * numOfCells));
//
//                currentLight = currentLight.add(upVector.scalarMultiply(numOfCells * x)).add(
//                        ViewerVector.scalarMultiply(numOfCells * y));
//
//                lightVectors.add(currentLight);
//            }
//        }
//    }

    private Color getTransperencyColor(Shape shape, Ray ray, double distance, int recursion){
        if(recursion >= settings.getMaxRecursionLevel())
            return settings.getRGB();
        float transCoeff = getMaterial(shape).getTransparency();
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
