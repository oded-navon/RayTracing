package RayTracing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Oded_navon on 11/05/2017.
 */
public class RayTracingUtils {

    /**
     * Parses the scene file and creates the scene. Change this function so it generates the required objects.
     */
    public static Scene parseScene(String sceneFileName) throws IOException, RayTracer.RayTracerException
    { // TODO: refactor out to a class
        FileReader fr = new FileReader(sceneFileName);

        BufferedReader r = new BufferedReader(fr);
        String line = null;
        int lineNum = 0;
        System.out.println("Started parsing scene file " + sceneFileName);

        Scene scene = new Scene();

        while ((line = r.readLine()) != null)
        {
            line = line.trim();
            ++lineNum;

            if (line.isEmpty() || (line.charAt(0) == '#'))
            {  // This line in the scene file is a comment
                continue;
            }
            else
            {
                String code = line.substring(0, 3).toLowerCase();
                // Split according to white space characters:
                String[] params = line.substring(3).trim().toLowerCase().split("\\s+");

                if (code.equals("cam"))
                {
                    float pos[] = {Float.parseFloat(params[0]),Float.parseFloat(params[1]),Float.parseFloat(params[2])};
                    float lookAtPos[] = {Float.parseFloat(params[3]),Float.parseFloat(params[4]),Float.parseFloat(params[5])};
                    float upVector[] = {Float.parseFloat(params[6]),Float.parseFloat(params[7]),Float.parseFloat(params[8])};
                    float screenDist = Float.parseFloat(params[9]);
                    float screenWidth = Float.parseFloat(params[10]);
                    scene.Camera = new Camera(pos,lookAtPos,upVector,screenDist,screenWidth);

                    System.out.println(String.format("Parsed camera parameters (line %d)", lineNum));
                }
                else if (code.equals("set"))
                {
                    float backColor[] = {Float.parseFloat(params[0]),Float.parseFloat(params[1]),Float.parseFloat(params[2])};
                    int shadowRays = Integer.parseInt(params[3]);
                    int maxRecNum = Integer.parseInt(params[4]);
                    int superSmapLev = Integer.parseInt(params[5]);
                    scene.Settings = new Settings(backColor,shadowRays,maxRecNum,superSmapLev);

                    System.out.println(String.format("Parsed general settings (line %d)", lineNum));
                }
                else if (code.equals("mtl"))
                {
                    float diffCol[] = {Float.parseFloat(params[0]),Float.parseFloat(params[1]),Float.parseFloat(params[2])};
                    float specCol[] = {Float.parseFloat(params[3]),Float.parseFloat(params[4]),Float.parseFloat(params[5])};
                    float refCol[] = {Float.parseFloat(params[6]),Float.parseFloat(params[7]),Float.parseFloat(params[8])};
                    float phongSpec = Float.parseFloat(params[9]);
                    float trans = Float.parseFloat(params[10]);
                    RayTracing.Material sceneMat = new RayTracing.Material(diffCol,specCol,refCol,phongSpec,trans);
                    scene.Materials.add(sceneMat);

                    System.out.println(String.format("Parsed material (line %d)", lineNum));
                }
                else if (code.equals("sph"))
                {
                    float center[] = {Float.parseFloat(params[0]),Float.parseFloat(params[1]),Float.parseFloat(params[2])};
                    float radius = Float.parseFloat(params[3]);
                    int matIndex = Integer.parseInt(params[4]);

                    Sphere sceneSphere = new Sphere(center,radius,matIndex);
                    scene.Spheres.add(sceneSphere);

                    System.out.println(String.format("Parsed sphere (line %d)", lineNum));
                }
                else if (code.equals("pln"))
                {
                    float normal[] = {Float.parseFloat(params[0]),Float.parseFloat(params[1]),Float.parseFloat(params[2])};
                    float offset = Float.parseFloat(params[3]);
                    int planeMatIndex = Integer.parseInt(params[4]);

                    Plane scenePlane = new Plane(normal,offset,planeMatIndex);
                    scene.Planes.add(scenePlane);

                    System.out.println(String.format("Parsed plane (line %d)", lineNum));
                }
                else if (code.equals("trg"))
                {
                    float ver1[] = {Float.parseFloat(params[0]),Float.parseFloat(params[1]),Float.parseFloat(params[2])};
                    float ver2[] = {Float.parseFloat(params[3]),Float.parseFloat(params[4]),Float.parseFloat(params[5])};
                    float ver3[] = {Float.parseFloat(params[6]),Float.parseFloat(params[7]),Float.parseFloat(params[8])};
                    int trigMatIndex = Integer.parseInt(params[9]);

                    Triangle sceneTrig = new Triangle(ver1,ver2,ver3,trigMatIndex);
                    scene.Triangles.add(sceneTrig);

                    System.out.println(String.format("Parsed cylinder (line %d)", lineNum));
                }
                else if (code.equals("lgt"))
                {
                    float lightPos[] = {Float.parseFloat(params[0]),Float.parseFloat(params[1]),Float.parseFloat(params[2])};
                    float lightRgb[] = {Float.parseFloat(params[3]),Float.parseFloat(params[4]),Float.parseFloat(params[5])};
                    float specIntensity = Float.parseFloat(params[6]);
                    float shadIntensity = Float.parseFloat(params[7]);
                    float lightRadius = Float.parseFloat(params[8]);

                    scene.Lights.add(new Light(lightPos,lightRgb,specIntensity,shadIntensity,lightRadius));

                    System.out.println(String.format("Parsed light (line %d)", lineNum));
                }
                else
                {
                    System.out.println(String.format("ERROR: Did not recognize object: %s (line %d)", code, lineNum));
                }
            }
        }

        // It is recommended that you check here that the scene is valid,
        // for example camera settings and all necessary materials were defined.
        // TODO: check validity of the scene

        System.out.println("Finished parsing scene file " + sceneFileName);
        fr.close();
        r.close();
        return scene;
    }





}
