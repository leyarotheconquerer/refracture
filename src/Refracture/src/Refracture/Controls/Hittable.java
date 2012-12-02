
package Refracture.controls;

import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;

/**
 *
 * @author DeWae
 */
public abstract class Hittable extends AbstractControl {    

String earthquakeX; // Identifies the earthquake that hit the object at hand.

String rayX; // Identifies what Ray that actually hit the object at hand.

   // This Function is invoked when something hittable is struck
	public void hitAction(){
	// Get name of Earthquake, set to earthquakeX
	// Get the name of the ray, set to rayX
	// Pass strength from ray to extended classes
        }
}
