package RayTracing;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class SoftShadows
{

        private float[][][] rgb;
        private Vector3D topLeft;
        private Vector3D horizontalstep;
        private Vector3D verticalstep;
        private Camera cam;
        private Light light;


        public SoftShadows(Light lightRay,Vector3D lightVectorToHitPoint){
            light = lightRay;
            setSteps(lightVectorToHitPoint);
            setTopLeft(lightVectorToHitPoint);
        }

        private void setSteps(Vector3D lightVectorToHitPoint)
        {
            horizontalstep = lightVectorToHitPoint.orthogonal();
            horizontalstep = horizontalstep.normalize();
            verticalstep = lightVectorToHitPoint.crossProduct(horizontalstep).normalize();
        }

        private void setTopLeft(Vector3D lightPoint){
            topLeft = lightPoint.add(stepUpLight((((double)light.getLightRadius())/2)-0.5))
                    .add(stepLeftLight((((double)light.getLightRadius())/2)-0.5));
        }

        private Vector3D stepUpLight(double numOfSteps){
            return verticalstep.scalarMultiply(numOfSteps);
        }
        private Vector3D stepDownLight(double numOfSteps){
            return verticalstep.scalarMultiply(-numOfSteps);
        }
        private Vector3D stepRightLight(double numOfSteps){
            return horizontalstep.scalarMultiply(numOfSteps);
        }
        private Vector3D stepLeftLight(double numOfSteps){
            return horizontalstep.scalarMultiply(-numOfSteps);
        }


        public List<Vector3D> generateVectors(Light light, Settings settings)
        {
            List<Vector3D> result = new ArrayList<>();

            if (light.getLightRadius() == 0 || settings.getShadowRay() == 1){
                result.add(light.getPosition());
            }else {

                Random randGen = new Random();
                float sizeOfCell = 2 * light.getLightRadius() / settings.getShadowRay();

                for (int i = 0; i < settings.getShadowRay(); i++) {
                    for (int j = 0; j < settings.getShadowRay(); j++) {
                        //nextDouble returns a number between 0.0 and 1.0
                        double x = randGen.nextDouble();
                        double y = randGen.nextDouble();
                        while (x == 0.0) x = randGen.nextDouble() - 0.5;
                        while (y == 0.0) y = randGen.nextDouble() - 0.5;

                        // Go to the cell
                        Vector3D currentLight = topLeft.add(stepDownLight(j * sizeOfCell)).add((stepRightLight(i * sizeOfCell)));
                        // move to random point in it
                        currentLight = currentLight.add(stepDownLight(x * sizeOfCell)).add((stepRightLight(y * sizeOfCell)));
                        //add to result
                        result.add(currentLight);
                    }
                }
            }
            return result;
        }

}


