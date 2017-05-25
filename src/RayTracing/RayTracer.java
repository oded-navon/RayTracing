package RayTracing;


import java.awt.Transparency;
import java.awt.color.*;
import java.awt.image.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 *  Main class for ray tracing exercise.
 */
public class RayTracer {

	public int imageWidth;
	public int imageHeight;
	public RayTracing.Material sceneMaterial;
	public Light sceneLight;
	public Plane scenePlane;


	/**
	 * Runs the ray tracer. Takes scene file, output image file and image size as input.
	 */
	public static void main(String[] args) {

		try {

			RayTracer tracer = new RayTracer();

			// Default values:
			tracer.imageWidth = 500;
			tracer.imageHeight = 500;

			if (args.length < 2)
				throw new RayTracerException("Not enough arguments provided. Please specify an input scene file and an output image file for rendering.");

			String sceneFileName = args[0];
			String outputFileName = args[1];

			if (args.length > 3)
			{
				tracer.imageWidth = Integer.parseInt(args[2]);
				tracer.imageHeight = Integer.parseInt(args[3]);
			}


			// Parse scene file:
			tracer.parseScene(sceneFileName);

			// Render scene:
			tracer.renderScene(outputFileName);

//		} catch (IOException e) {
//			System.out.println(e.getMessage());
		} catch (RayTracerException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Parses the scene file and creates the scene. Change this function so it generates the required objects.
	 */
	public void parseScene(String sceneFileName) throws IOException, RayTracerException
	{
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
					int pos[] = {Integer.parseInt(params[0]),Integer.parseInt(params[1]),Integer.parseInt(params[2])};
					int lookAtPos[] = {Integer.parseInt(params[3]),Integer.parseInt(params[4]),Integer.parseInt(params[5])};
					int upVector[] = {Integer.parseInt(params[6]),Integer.parseInt(params[7]),Integer.parseInt(params[8])};
					float screenDist = Float.parseFloat(params[9]);
					float screenWidth = Float.parseFloat(params[10]);
					scene.Camera = new Camera(pos,lookAtPos,upVector,screenDist,screenWidth);

					System.out.println(String.format("Parsed camera parameters (line %d)", lineNum));
				}
				else if (code.equals("set"))
				{
					int backColor[] = {Integer.parseInt(params[0]),Integer.parseInt(params[1]),Integer.parseInt(params[2])};
					int shadowRays = Integer.parseInt(params[3]);
					int maxRecNum = Integer.parseInt(params[4]);
					int superSmapLev = Integer.parseInt(params[5]);
					scene.Settings = new Settings(backColor,shadowRays,maxRecNum,superSmapLev);

					System.out.println(String.format("Parsed general settings (line %d)", lineNum));
				}
				else if (code.equals("mtl"))
				{
					int diffCol[] = {Integer.parseInt(params[0]),Integer.parseInt(params[1]),Integer.parseInt(params[2])};
					int specCol[] = {Integer.parseInt(params[3]),Integer.parseInt(params[4]),Integer.parseInt(params[5])};
					int refCol[] = {Integer.parseInt(params[6]),Integer.parseInt(params[7]),Integer.parseInt(params[8])};
					float phongSpec = Float.parseFloat(params[9]);
					float trans = Float.parseFloat(params[10]);
					RayTracing.Material sceneMat = new RayTracing.Material(diffCol,specCol,refCol,phongSpec,trans);
					scene.Materials.add(sceneMat);

					System.out.println(String.format("Parsed material (line %d)", lineNum));
				}
				else if (code.equals("sph"))
				{
					int center[] = {Integer.parseInt(params[0]),Integer.parseInt(params[1]),Integer.parseInt(params[2])};
					int radius = Integer.parseInt(params[3]);
					int matIndex = Integer.parseInt(params[4]);

					Sphere sceneSphere = new Sphere(center,radius,matIndex);
					scene.Spheres.add(sceneSphere);

					System.out.println(String.format("Parsed sphere (line %d)", lineNum));
				}
				else if (code.equals("pln"))
				{
					int normal[] = {Integer.parseInt(params[0]),Integer.parseInt(params[1]),Integer.parseInt(params[2])};
					int offset = Integer.parseInt(params[3]);
					int planeMatIndex = Integer.parseInt(params[4]);

					Plane scenePlane = new Plane(normal,offset,planeMatIndex);
					scene.Planes.add(scenePlane);

					System.out.println(String.format("Parsed plane (line %d)", lineNum));
				}
				else if (code.equals("trg"))
				{
					int ver1[] = {Integer.parseInt(params[0]),Integer.parseInt(params[1]),Integer.parseInt(params[2])};
					int ver2[] = {Integer.parseInt(params[3]),Integer.parseInt(params[4]),Integer.parseInt(params[5])};
					int ver3[] = {Integer.parseInt(params[6]),Integer.parseInt(params[7]),Integer.parseInt(params[8])};
					int trigMatIndex = Integer.parseInt(params[9]);

					Triangle sceneTrig = new Triangle(ver1,ver2,ver3,trigMatIndex);
					scene.Triangles.add(sceneTrig);

					System.out.println(String.format("Parsed cylinder (line %d)", lineNum));
				}
				else if (code.equals("lgt"))
				{
					int lightPos[] = {Integer.parseInt(params[0]),Integer.parseInt(params[1]),Integer.parseInt(params[2])};
					int lightRgb[] = {Integer.parseInt(params[3]),Integer.parseInt(params[4]),Integer.parseInt(params[5])};
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

		System.out.println("Finished parsing scene file " + sceneFileName);

	}

	/**
	 * Renders the loaded scene and saves it to the specified file location.
	 */
	public void renderScene(String outputFileName)
	{
		long startTime = System.currentTimeMillis();

		// Create a byte array to hold the pixel data:
		byte[] rgbData = new byte[this.imageWidth * this.imageHeight * 3];


                // Put your ray tracing code here!
                //
                // Write pixel color values in RGB format to rgbData:
                // Pixel [x, y] red component is in rgbData[(y * this.imageWidth + x) * 3]
                //            green component is in rgbData[(y * this.imageWidth + x) * 3 + 1]
                //             blue component is in rgbData[(y * this.imageWidth + x) * 3 + 2]
                //
                // Each of the red, green and blue components should be a byte, i.e. 0-255


		long endTime = System.currentTimeMillis();
		Long renderTime = endTime - startTime;

                // The time is measured for your own conveniece, rendering speed will not affect your score
                // unless it is exceptionally slow (more than a couple of minutes)
		System.out.println("Finished rendering scene in " + renderTime.toString() + " milliseconds.");

                // This is already implemented, and should work without adding any code.
		saveImage(this.imageWidth, rgbData, outputFileName);

		System.out.println("Saved file " + outputFileName);

	}




	//////////////////////// FUNCTIONS TO SAVE IMAGES IN PNG FORMAT //////////////////////////////////////////

	/*
	 * Saves RGB data as an image in png format to the specified location.
	 */
	public static void saveImage(int width, byte[] rgbData, String fileName)
	{
		try {

			BufferedImage image = bytes2RGB(width, rgbData);
			ImageIO.write(image, "png", new File(fileName));

		} catch (IOException e) {
			System.out.println("ERROR SAVING FILE: " + e.getMessage());
		}

	}

	/*
	 * Producing a BufferedImage that can be saved as png from a byte array of RGB values.
	 */
	public static BufferedImage bytes2RGB(int width, byte[] buffer) {
	    int height = buffer.length / width / 3;
	    ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_sRGB);
	    ColorModel cm = new ComponentColorModel(cs, false, false,
	            Transparency.OPAQUE, DataBuffer.TYPE_BYTE);
	    SampleModel sm = cm.createCompatibleSampleModel(width, height);
	    DataBufferByte db = new DataBufferByte(buffer, width * height);
	    WritableRaster raster = Raster.createWritableRaster(sm, db, null);
	    BufferedImage result = new BufferedImage(cm, raster, false, null);

	    return result;
	}

	public static class RayTracerException extends Exception {
		public RayTracerException(String msg) {  super(msg); }
	}


}
