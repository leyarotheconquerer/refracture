package Refracture.Controls;

import com.jme3.app.SimpleApplication;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;
import com.jme3.terrain.geomipmap.TerrainQuad;

/**
 * Defines a control for placing a building.
 * @author Hazen
 */
public class Placing extends AbstractControl{
    
    /** A reference to the overall application */
    SimpleApplication app;
    
    Node iterator;
    
    /** A reference to the terrain object */
    TerrainQuad terrain;

    public Placing(SimpleApplication app) {
        this.app = app;
        this.iterator = (Node) this.app.getRootNode().getChild("mapNode");
        this.iterator = (Node) iterator.getChild("indestructibleTerrain");
        this.terrain = (TerrainQuad) iterator.getChild("terrain");
    }
    
    public void Place()
    {
        this.setEnabled(false);
    }
    
    @Override
    protected void controlUpdate(float tpf) {
        
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Control cloneForSpatial(Spatial spatial) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Find the height of the terrain at a given point.
     * @param location A 3D vector referencing an (x,z) position on the map.
     * @return A 3D vector pointing to the point surface point on the map at the
     * (x,z) position on the map
     */
    private Vector3f getGroundHeight(Vector3f location)
    {   
        return new Vector3f(
                location.x,
                terrain.getLocalTranslation().y + 
                    terrain.getHeight(new Vector2f(location.x, location.z)),
                location.z);
    }
    
}
