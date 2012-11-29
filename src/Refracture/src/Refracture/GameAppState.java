package Refracture;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.jme3.terrain.heightmap.HeightMap;
import com.jme3.terrain.heightmap.HillHeightMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The application state of Refracture in-game.
 * This is a class that extends JMonkey functionality. It allows the game to
 * separate the in-game state from other states (e.g. main menu, tech tree,
 * etc).
 * @author Hazen
 */
public class GameAppState extends AbstractAppState {
    
    /** The local logger */
    private static final Logger logger = Logger.getLogger(GameAppState.class.getName());
    
    /** A local instance of the overall game */
    private SimpleApplication app;
    
    //Node Section
    
    /** The map parent node. */
    Node map;
        /** Terrain that is influenced by earthquakes. */
        Node destructibleTerrain;
        /** Terrain that is not influenced by earthquakes. */
        Node indestructibleTerrain;
    
    @Override
    public void initialize(AppStateManager stateManager, Application app)
    {   
        super.initialize(stateManager, app);
        
        //Store the passed in application
        this.app = (SimpleApplication) app;
        
        //Load the nodes
        
        //Load the lights
        makeSun();
        
        //Load the map structure
        logger.log(Level.INFO, "Creating map nodes");
        map = new Node("map");
        this.app.getRootNode().attachChild(map);
        //Add sub-nodes
        destructibleTerrain = new Node("destructibleTerrain");
        map.attachChild(destructibleTerrain);
        
        indestructibleTerrain = new Node("indestructibleTerrain");
        map.attachChild(indestructibleTerrain);
        
        indestructibleTerrain.attachChild(makeTerrain());
        
        /*Box b = new Box(new Vector3f(0,0,0),2f,0.1f,2f);
        Geometry cube = new Geometry("Test Cube", b);
        Material mat = new Material(this.app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Orange);
        cube.setMaterial(mat);
        cube.setLocalTranslation(new Vector3f(0,-1f,0));
        indestructibleTerrain.attachChild(cube);*/
        
        logger.log(Level.INFO, "GameAppState initialize not fully implemented.");
    }
    
    @Override
    public void cleanup()
    {
        super.cleanup();
        
        logger.log(Level.INFO, "GameAppState cleanup not yet implemented.");
    }
    
    @Override
    public void setEnabled(boolean enabled)
    {
        super.setEnabled(enabled);
        
        logger.log(Level.INFO, "GameAppState setEnabled not yet implemented.");
    }
    
    //Note that update is only called while the state is both attached and enabled.
    @Override
    public void update(float tpf)
    {
        logger.log(Level.INFO, "Updating the in-game state");
    }
    
    //Loading section
    //This section contains the definitions for all the different objects used
    //in the game. The reason these are not classes is that an object in
    //jMonkey uses the component system and is thus defined by what it consists
    //of rather than what it is.
    
    /**
     * Defines the map terrain.
     */
    private Node makeTerrain()
    {
        TerrainQuad terrain;
        
        try {
            //Generate the height map
            HeightMap heightMap = new HillHeightMap(257, 100, 30, 50);
            
            //Get the material
            Material mat = new Material(this.app.getAssetManager(),
                    "Common/MatDefs/Light/Lighting.j3md");
            mat.setTexture("ColorMap",
                    this.app.getAssetManager().loadTexture("MapTest.png"));
            
            //Create the terrain object
            terrain = new TerrainQuad("Terrain", 64, 257, heightMap.getHeightMap());
            terrain.setMaterial(mat);
            
            return terrain;
        } catch (Exception ex) {
            Logger.getLogger(GameAppState.class.getName()).log(Level.SEVERE,
                    "Unable to generate terrain.", ex);
        }
        
        return null;
    }
    
    /**
     * Defines a directional light to light the entire scene.
     */
    private void makeSun()
    {
        DirectionalLight sun = new DirectionalLight();
        sun.setColor(ColorRGBA.White);
        sun.setDirection(new Vector3f(-1f, -1f, -1f).normalizeLocal());
        sun.setName("Sun");
        this.app.getRootNode().addLight(sun);
        
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(ColorRGBA.White);
        ambient.setName("Ambient");
    }
}
