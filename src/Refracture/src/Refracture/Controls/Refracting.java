package Refracture.Controls;

import Refracture.controls.Hittable;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;

/**
 * Defines the behavior of a refracting object.
 * @author Hazen
 */
public class Refracting extends Hittable{

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
    
	//The Function of that the Refracting SeisMantle does When hit by seismic waves
    public void Hit( boolean hit, String earthquakeX, String rayX) {
	throw new UnsupportedOperationException("Not Coded yet");
	} 
}
