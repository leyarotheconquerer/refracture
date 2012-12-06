package Refracture.Controls;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;

/**
 * Defines the behavior of an earthquake generator.
 * @author Hazen
 */
public class EarthquakeGenerator extends AbstractControl {

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
