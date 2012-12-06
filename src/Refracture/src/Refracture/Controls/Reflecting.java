package Refracture.Controls;

import Refracture.controls.Hittable;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;

/**
 * Defines the behavior of a reflecting object.
 * @author Hazen
 */
public class Reflecting extends Hittable{

    @Override
    protected void controlUpdate(float tpf) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Control cloneForSpatial(Spatial spatial) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


	//The Function of that the Reflective SeisMantle does When hit by seismic waves
    public void Hit( boolean hit, String earthquakeX, String rayX) {
	throw new UnsupportedOperationException("Not Coded yet");
	} 
    
}
