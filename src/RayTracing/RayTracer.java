package RayTracing;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

/**
 *  Main class for ray tracing exercise.
 */
public class RayTracer {

//	public int imageWidth;
//	public int imageHeight;
	public Scene scene;
	public PixelPlane pixelPlane;

	public Scene getScene() {
		return scene;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}

	public PixelPlane getPixelPlane() {
		return pixelPlane;
	}

	public void setPixelPlane(PixelPlane pixelPlane) {
		this.pixelPlane = pixelPlane;
	}

	/**
	 * Runs the ray tracer. Takes scene file, output image file and image size as input.
	 */
	public static void main(String[] args) {

		try {

			RayTracer tracer = new RayTracer();

//			// Default values:
//			tracer.imageWidth = 500;
//			tracer.imageHeight = 500;

			if (args.length < 2)
				throw new RayTracerException("Not enough arguments provided. Please specify an input scene file and an output image file for rendering.");

			String sceneFileName = args[0];
			String outputFileName = args[1];
			// Parse scene file:
			tracer.scene =  RayTracingUtils.parseScene(sceneFileName);

			tracer.pixelPlane = (args.length > 3) ?
					new PixelPlane(Integer.parseInt(args[2]),Integer.parseInt(args[3]),tracer.scene.camera ) :
					new PixelPlane(500,500, tracer.scene.camera);

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
	 * Renders the loaded scene and saves it to the specified file location.
	 */
	public void renderScene(String outputFileName)
	{
		long startTime = System.currentTimeMillis();
		Ray ray;
		float[] rgb;

		for(int x=0; x<pixelPlane.getImageWidth(); x++){
			for(int y=0; y<pixelPlane.getImageHeight(); y++){
				//System.out.println("x: "+x+" Y: "+y);
				if(y==499){
					//System.out.println("got here");
				}
				ray = pixelPlane.constructRayTroughPixel(x,y);
				//rgb = superSample(ray);
				rgb = scene.computeRGBForRay(ray,0).getRGB();
				pixelPlane.setPixelColor(x,y,rgb);
			}
		}


		long endTime = System.currentTimeMillis();
		Long renderTime = endTime - startTime;

                // The time is measured for your own conveniece, rendering speed will not affect your score
                // unless it is exceptionally slow (more than a couple of minutes)
		System.out.println("Finished rendering scene in " + renderTime.toString() + " milliseconds.");

                // This is already implemented, and should work without adding any code.
		saveImage(pixelPlane.getImageWidth(), pixelPlane.getAsByteArray(), outputFileName);

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

	private float[] superSample(Ray ray){
		double factor = Math.pow(scene.settings.getSuperSamplingLevel(),-2);
		Color result = RayTracingUtils.createRandomRays(ray, pixelPlane, scene.settings.getSuperSamplingLevel())
			   .stream()
				.map(currentRay -> scene.computeRGBForRay(currentRay,0))
				.reduce(new Color(0,0,0), (res, zeva) -> res.add(zeva))
			   .mult(factor);
		return result.getRGB();
	}


}
