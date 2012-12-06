package Refracture;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.input.InputManager;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.debug.WireBox;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.jme3.terrain.heightmap.AbstractHeightMap;
import com.jme3.terrain.heightmap.ImageBasedHeightMap;
import com.jme3.texture.Texture;
import com.sun.accessibility.internal.resources.accessibility;
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
    Node mapRoot;
    
    /** The building parent node. */
    Node buildingRoot;
    
    /** The earthquake parent node. */
    Node earthquakeRoot;
    
    @Override
    public void initialize(AppStateManager stateManager, Application app)
    {
        super.initialize(stateManager, app);
        
        //Store the passed in application
        this.app = (SimpleApplication) app;
        
        //Register the input devices
        initKeys();
        
        ////////////////////////////////////////////////////////////////////////
        //Load all the nodes////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////
        
        /**
         * A pointer to the current position in the scene graph.
         * Initialized to the root node.
         */
        Node iterator = this.app.getRootNode();
        
        /**
         * A test variable that I'm sure is destined to be used for always and
         * eternity.
         */
        Node parameter = this.app.getRootNode();
        
        //Load the lights
        makeSun();
        
        //Load the map structure////////////////////////////////////////////////
        logger.log(Level.INFO, "Creating map nodes");
        mapRoot = new Node("mapRoot");
        iterator.attachChild(mapRoot);
        
        //Fill in the children of "mapRoot"
        iterator = (Node)iterator.getChild("mapRoot");
        iterator.attachChild(new Node("destructibleTerrain"));
        iterator.attachChild(new Node("indestructibleTerrain"));
        
        //Fill in the children of "indestructibleTerrain"
        iterator = (Node)iterator.getChild("indestructibleTerrain");
        iterator.attachChild(makeTerrain("terrain"));
        iterator.getChild("terrain").setLocalTranslation(
                new Vector3f(0,-30f,0));
        
        //Reset the iterator
        iterator = this.app.getRootNode();
        
        //Load the building structure///////////////////////////////////////////
        logger.log(Level.INFO, "Creating building nodes");
        buildingRoot = new Node("buildingRoot");
        iterator.attachChild(buildingRoot);
        
        //Fill in the children of "buildingRoot"
        iterator = (Node)iterator.getChild("buildingRoot");
        iterator.attachChild(new Node("defenses"));
        iterator.attachChild(new Node("other"));
        
        //Fill in the children of "other"
        iterator = (Node) iterator.getChild("other");
        parameter = (Node) this.app.getRootNode().getChild("mapRoot");
        parameter = (Node) parameter.getChild("indestructibleTerrain");
        iterator.attachChild(makeTestBuilding(new Vector3f(0,0,0), "testBuilding",
                (TerrainQuad) parameter.getChild("terrain")));
        
        //Reset the iterator
        iterator = this.app.getRootNode();
        
        //Load the earthquake structure/////////////////////////////////////////
        logger.log(Level.INFO, "Creating earthquake nodes");
        earthquakeRoot = new Node("earthquakeRoot");
        iterator.attachChild(earthquakeRoot);
        
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
    
    //General Definitions///////////////////////////////////////////////////////
    
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
        this.app.getRootNode().addLight(ambient);
    }
    
     //Map Definitions//////////////////////////////////////////////////////////
    
    /**
     * Defines the map terrain.
     */
    private Node makeTerrain(String name)
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
            terrain = new TerrainQuad(name, 64, 257, heightMap.getHeightMap());
            terrain.setMaterial(mat);
            
            terrain.updateModelBound();
            /*
            WireBox geom = new WireBox();
            geom.fromBoundingBox(terrain.getWorldBound());*/
            
            return terrain;
        } catch (Exception ex) {
            Logger.getLogger(GameAppState.class.getName()).log(Level.SEVERE,
                    "Unable to generate terrain.", ex);
        }
        
        return null;
    }
    
    //Building Definitions//////////////////////////////////////////////////////
    
    /**
     * Loads a test building and places it.
     */
    private Spatial makeTestBuilding(Vector3f localTranslation, String name, TerrainQuad terrain)
    {
        //Load the model
        Spatial building = this.app.getAssetManager().loadModel("Models/testbuilding.j3o");
        building.setName(name);
        building.setLocalTranslation(localTranslation);
        
        //Load the texture
        Material mat = new Material(this.app.getAssetManager(),
                "Common/MatDefs/Light/Lighting.j3md");
        mat.setTexture("DiffuseMap",
                this.app.getAssetManager().loadTexture("Textures/testbuilding.png"));
        building.setMaterial(mat);
        
        //Move the building onto the terrain
        /*
        //Commented out so that I can test some stuffs for placement
        logger.log(Level.INFO, "Finding ground collision of {0}", name);
        Ray ray = new Ray(localTranslation,
                new Vector3f(0,-1f,0).normalizeLocal());
        CollisionResults results = new CollisionResults();
        this.app.getRootNode().collideWith(ray, results);
        
        for (int i = 0; i < results.size(); i++) {
            String object = results.getCollision(i).getGeometry().getName();
            Vector3f point = results.getCollision(i).getContactPoint();
            logger.log(Level.INFO, "Collision at {0} with {1}",
                    new Object[]{point.toString(), object});
        }
        
        CollisionResult closest = results.getClosestCollision();
        logger.log(Level.INFO, "Moving {0} to {1}",
                new Object[]{name, closest.getContactPoint().toString()});
        building.setLocalTranslation(closest.getContactPoint());
        //*/
        
        logger.setLevel(Level.FINE);
        
        logger.log(Level.INFO, "Trying to put the building on the ground");
        float x, y, z;
        x = localTranslation.x;
        z = localTranslation.z;
        float height = terrain.getHeightmapHeight(new Vector2f(x,z));
        y = terrain.getLocalTranslation().y + height;
        logger.log(Level.INFO, "Putting the building at ({0}, {1}, {2}) where {1} = {4} - {3}",
                new Object[]{x,y,z,height, terrain.getLocalTranslation().y});
        building.setLocalTranslation(new Vector3f(x,y,z));
        
        return building;
    }
    
    ActionListener actionListener = new ActionListener() {

        public void onAction(String name, boolean isPressed, float tpf) {
            if(isPressed)
            {
                if(name.equals("Click"))
                {
                    CollisionResults results = new CollisionResults();
                    
                    Vector2f click2d = app.getInputManager().getCursorPosition();
                    Vector3f click3d = app.getCamera().getWorldCoordinates(
                            new Vector2f(click2d.x, click2d.y),0).clone();
                    Vector3f dir = app.getCamera().getWorldCoordinates(
                            new Vector2f(click2d.x, click2d.y), 1f).subtractLocal(click3d).normalizeLocal();
                    
                    Ray ray = new Ray(click3d, dir);
                    
                    app.getRootNode().collideWith(ray, results);
                    
                    logger.log(Level.INFO, "Doom destruction and collisions...");
                    for (int i = 0; i < results.size(); i++) {
                        logger.log(Level.INFO, "Collision {0}", new Object[]{results.getCollision(i)});
                    }
                }
            }
        }
    };
    
    private void initKeys()
    {
        InputManager inputManager = this.app.getInputManager();
        inputManager.addMapping("Click", new KeyTrigger(MouseInput.BUTTON_LEFT));
        inputManager.setCursorVisible(true);
        
        inputManager.addListener(actionListener, new String[]{"Click"});
    }
}
