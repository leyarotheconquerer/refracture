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
import com.jme3.scene.Node;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.jme3.terrain.heightmap.AbstractHeightMap;
import com.jme3.terrain.heightmap.ImageBasedHeightMap;
import com.jme3.texture.Texture;
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
    
    @Override
    public void initialize(AppStateManager stateManager, Application app)
    {   
        super.initialize(stateManager, app);
        
        //Store the passed in application
        this.app = (SimpleApplication) app;
        
        //Load the nodes
        
        /**
         * A pointer to the current position in the scene graph.
         * Initialized to the root node.
         */
        Node iterator = this.app.getRootNode();
        
        //Load the lights
        makeSun();
        
        //Load the map structure
        logger.log(Level.INFO, "Creating map nodes");
        map = new Node("map");
        iterator.attachChild(map);
        
        //Fill in the children of "map"
        iterator = (Node)iterator.getChild("map");
        iterator.attachChild(new Node("destructibleTerrain"));
        iterator.attachChild(new Node("indestructibleTerrain"));
        
        //Fill in the children of "indestructibleTerrain"
        iterator = (Node)iterator.getChild("indestructibleTerrain");
        iterator.attachChild(makeTerrain());
        iterator.getChild("Terrain").setLocalTranslation(
                new Vector3f(0,-30f,0));
        
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
            AbstractHeightMap heightMap = null;
            Texture heightImage = this.app.getAssetManager().loadTexture("Textures/MapTest2.jpg");
            heightMap = new ImageBasedHeightMap(heightImage.getImage(), 0.25f);
            heightMap.load();
            
            //Get the material
            Material mat = new Material(this.app.getAssetManager(),
                    "Common/MatDefs/Terrain/Terrain.j3md");
            mat.setTexture("Alpha",
                    this.app.getAssetManager().loadTexture("Textures/MapTest.png"));
            
            Texture ground = this.app.getAssetManager().loadTexture("Textures/GroundTest.png");
            ground.setWrap(Texture.WrapMode.Repeat);
            mat.setTexture("Tex1", ground);
            
            Texture grass1 = this.app.getAssetManager().loadTexture("Textures/GrassTest.png");
            grass1.setWrap(Texture.WrapMode.Repeat);
            mat.setTexture("Tex2", grass1);
            
            Texture grass2 = this.app.getAssetManager().loadTexture("Textures/GrassTest2.png");
            grass2.setWrap(Texture.WrapMode.Repeat);
            mat.setTexture("Tex3", grass2);
            
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
