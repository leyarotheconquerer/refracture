package Refracture;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;

/**
 * This is the base class for the entire game, Refracture.
 * As of right now, it contains test code. When run, it should
 * display a blue box. The camera is controlled using first person controls.
 * @author Hazen
 */
public class Refracture extends SimpleApplication {

    //Declare ApplicationStates
    GameAppState inGame;
    
    public static void main(String[] args) {

        Refracture app = new Refracture();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        Box b = new Box(Vector3f.ZERO, 1, 1, 1);
        Geometry geom = new Geometry("Box", b);

        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        geom.setMaterial(mat);

        rootNode.attachChild(geom);
        
        //Initialize the in-game application state
        //(Note: This will cause the initialize() function to run in GameAppState)
        inGame = new GameAppState();
        //Attach the in-game state
        this.stateManager.attach(inGame);
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
