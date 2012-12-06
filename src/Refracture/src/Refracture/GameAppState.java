package Refracture;

import Refracture.Controls.Camera;
import Refracture.Misc.Directions;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.collision.CollisionResults;
import com.jme3.input.InputManager;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.input.event.MouseMotionEvent;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
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
    Node mapRoot;
    
    /** The building parent node. */
    Node buildingRoot;
    
    /** The camera node. */
    CameraNode camNode;
    
    /** The earthquake parent node. */
    Node earthquakeRoot;
    
    /** The terrain object containing the heights of the map */
    TerrainQuad terrain;
    
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
        
        //Load the camera
        makeCamera();
        
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
        terrain = makeTerrain("terrain");
        iterator.attachChild(terrain);
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
        //Update the camera location based on mouse movement
        updateCamera();
        
        //logger.log(Level.INFO, "Updating the in-game state");
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
    
    /**
     * Defines the position and parent node of the camera.
     */
    private void makeCamera()
    {
        //Disable the first person camera
        app.getFlyByCamera().setEnabled(false);
        app.getInputManager().setCursorVisible(true);
        
        //Create the camera node to control the camera
        camNode = new CameraNode("cameraNode", app.getCamera());
        
        //Add motion control to the camera
        camNode.addControl(new Camera());
        
        //Add the camera control to the root node
        app.getRootNode().attachChild(camNode);
        
        //Set the initial position of the camera
        camNode.setLocalTranslation(0, 50f, -10f);
        
        //Set the initial rotation of the camera
        camNode.setLocalRotation(new Quaternion().fromAngleAxis(
                (float) (Math.PI / 3),
                new Vector3f(1f, 0, 0)));
    }
    
     //Map Definitions//////////////////////////////////////////////////////////
    
    /**
     * Defines the map terrain.
     */
    private TerrainQuad makeTerrain(String name)
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
        building.setLocalTranslation(getGroundHeight(localTranslation));
        
        return building;
    }
    
    //General functions
    //This section contains general functions that are used by other functions
    //in the GameAppState.
    
    /**
     * Handles one time input events such as clicking or pressing a key on the keyboard.
     */
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
                        logger.log(Level.INFO, "Collision {0}",
                                new Object[]{results.getCollision(i)});
                    }
                }
            }
        }
    };
    
    /**
     * Register the input devices with the input manager.
     */
    private void initKeys()
    {
        InputManager inputManager = this.app.getInputManager();
        inputManager.addMapping("Click", new KeyTrigger(MouseInput.BUTTON_LEFT));
        
        inputManager.addListener(actionListener, new String[]{
            "Click"});
    }
    
    /**
     * Updates the camera location based on the mouse location.
     */
    private void updateCamera()
    {
        //Get the mouse position as a 2D vector
        Vector2f click2d = app.getInputManager().getCursorPosition();
        int margin = 50;
        
        //logger.log(Level.INFO, "Mouse at {0}",
                //new Object[]{click2d});
        
        //Check the margins
        if(click2d.x < margin) //Going left
        {
            camNode.getControl(Camera.class).enableMoveX(Directions.LEFT);
        }
        else if(click2d.x > app.getCamera().getWidth() - margin) //Going right
        {
            camNode.getControl(Camera.class).enableMoveX(Directions.RIGHT);
        }
        else //Not horizontally moving
        {
            camNode.getControl(Camera.class).disableMoveX();
        }
        
        if(click2d.y < margin) //Going up
        {
            camNode.getControl(Camera.class).enableMoveY(Directions.UP);
        }
        else if(click2d.y > app.getCamera().getHeight() - margin) //Going down
        {
            camNode.getControl(Camera.class).enableMoveY(Directions.DOWN);
        }
        else //Not vertically moving
        {
            camNode.getControl(Camera.class).disableMoveY();
        }
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
                    terrain.getHeightmapHeight(new Vector2f(location.x, location.z)),
                location.z);
    }
}
