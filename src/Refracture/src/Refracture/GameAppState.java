package Refracture;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
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
            /** The physical map itself */
            Geometry terrain;
    
    @Override
    public void initialize(AppStateManager stateManager, Application app)
    {   
        super.initialize(stateManager, app);
        
        //Store the passed in application
        this.app = (SimpleApplication) app;
        
        //Load the nodes
        
        //Load the map structure
        logger.log(Level.INFO, "Creating map nodes");
        map = new Node("map");
        //Add sub-nodes
        destructibleTerrain = new Node("destructibleTerrain");
        map.attachChild(destructibleTerrain);
        
        indestructibleTerrain = new Node("indestructibleTerrain");
        map.attachChild(indestructibleTerrain);
        
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
}
