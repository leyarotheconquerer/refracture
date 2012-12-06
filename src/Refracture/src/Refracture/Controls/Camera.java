/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Refracture.Controls;

import Refracture.Misc.Directions;
import com.jme3.export.InputCapsule;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.OutputCapsule;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This control defines the motion of the camera.
 * @author Hazen
 */
public class Camera extends AbstractControl {
    
    /**
     * Here's me placing a logger in here says Hazen...
     * just cluttering code. Hope you enjoy the mess.
     */
    private final static Logger logger = Logger.getLogger(Camera.class.getName());
    
    /** Flags indicating motion along each axis */
    boolean moveX = false;
    Directions horizDir = Directions.LEFT;
    
    boolean moveY = false;
    Directions vertDir = Directions.UP;

    public boolean isMoveLeft() {
        return moveX && horizDir == Directions.LEFT;
    }
    
    public boolean isMoveRight() {
        return moveX && horizDir == Directions.RIGHT;
    }
    
    public boolean isMoveUp() {
        return moveY && vertDir == Directions.UP;
    }
    
    public boolean isMoveDown() {
        return moveY && vertDir == Directions.DOWN;
    }

    public void enableMoveX(Directions horizDir) {
        this.setEnabled(true);
        this.moveX = true;
        this.horizDir = horizDir;
    }

    public void enableMoveY(Directions vertDir) {
        this.setEnabled(true);
        this.moveY = true;
        this.vertDir = vertDir;
    }
    
    public void disableMoveY() {
        this.moveY = false;
        if(!moveX)
        {
            this.enabled = false;
        }
    }
    
    public void disableMoveX() {
        this.moveX = false;
        if(!moveY)
        {
            this.enabled = false;
        }
    }

    @Override
    protected void controlUpdate(float tpf) {
        //Contains the movement of the camera this frame
        Vector3f camMove = new Vector3f(0, 0, 0);
        
        if(moveX)
        {
            if(horizDir == Directions.LEFT)
            {
                camMove.addLocal(new Vector3f(1f, 0, 0));
            }
            else
            {
                camMove.addLocal(new Vector3f(-1f, 0, 0));
            }
        }
        
        if(moveY)
        {
            if(vertDir == Directions.UP)
            {
                camMove.addLocal(new Vector3f(0, 0, -1f));
            }
            else
            {
                camMove.addLocal(new Vector3f(0,0,1f));
            }
        }
        
        //Scale the camera motion by the frame rate
        camMove.normalizeLocal().multLocal(tpf*100);
        
        logger.log(Level.INFO, "And I''m moving this much {0}", camMove.toString());
        
         getSpatial().move(camMove);
        
    }
    
    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        //Only needed for rendering-related operations,
        //not called when spatial is culled.
    }
    
    public Control cloneForSpatial(Spatial spatial) {
        Camera control = new Camera();
        //TODO: copy parameters to new Control
        control.setSpatial(spatial);
        return control;
    }
    
    @Override
    public void read(JmeImporter im) throws IOException {
        super.read(im);
        InputCapsule in = im.getCapsule(this);
        //TODO: load properties of this Control, e.g.
        //this.value = in.readFloat("name", defaultValue);
    }
    
    @Override
    public void write(JmeExporter ex) throws IOException {
        super.write(ex);
        OutputCapsule out = ex.getCapsule(this);
        //TODO: save properties of this Control, e.g.
        //out.write(this.value, "name", defaultValue);
    }
}
