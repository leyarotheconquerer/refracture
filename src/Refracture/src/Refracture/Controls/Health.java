package Refracture.Controls;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;

/**
 * The health characteristic of any given unit.
 * @author Hazen
 */
public class Health extends AbstractControl {
    int health;

    // Function of struck object taking damage
    public void takeDamage(int Strength){
	throw new UnsupportedOperationException("Not coded yet.");
    }
    //Function to repair object's health
    public void repair(int repairStrength){
	throw new UnsupportedOperationException("Not coded yet.");
    }
	
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
    
}
