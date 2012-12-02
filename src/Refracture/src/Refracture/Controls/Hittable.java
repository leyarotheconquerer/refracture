
package Refracture.controls;

import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;

/**
 *
 * @author DeWae
 */
public abstract class Hittable extends AbstractControl {
    
boolean hit; // The boolean to see if the objects are hit.

String earthquakeX; // Identifies the earthquake that hit the object at hand.

String rayX; // Identifies what Ray that actually hit the object at hand.

}
