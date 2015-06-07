package com.utbm.info.vi51.buildingEvac.environment;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.janusproject.jaak.envinterface.body.TurtleBody;
import org.janusproject.jaak.envinterface.channel.GridStateChannel;
import org.janusproject.jaak.environment.model.JaakEnvironment;
import org.janusproject.jaak.environment.solver.ActionApplier;
import org.janusproject.jaak.spawner.JaakSpawner;
import org.janusproject.kernel.address.AgentAddress;
import org.janusproject.kernel.agent.Kernels;

import com.utbm.info.vi51.buildingEvac.agent.AgentID;
import com.utbm.info.vi51.buildingEvac.gui.FloorPanel;
import com.utbm.info.vi51.buildingEvac.math.Rectangle2f;

public class Environment 
{
	private Rectangle2f worldSize;
	
	//private State state;
	//private static Astar astar;
	
	private org.janusproject.jaak.environment.solver.InfluenceSolver influenceSolver;

	private Floor[] floor;
	
    //Set a wrapped environment.
    private static final boolean isWrappedEnvironment = false;
    
    //Maximal number of employees.
    private static final int EMPLOYEE_NUMBER = 10;
    
    //Width of the spawn area.
    private static final int SPAWN_WIDTH = 10;
    
    //Height of the spawn area.
    private static final int SPAWN_HEIGHT = 10;
    
    //X coordinate of the spawn area.
    private static final int SPAWN_X = 140;
    
    //X coordinate of the spawn area.
    private static final int SPAWN_Y = 10;
    
    //Width of the Jaak grid.
    private static final int WIDTH = 120;
    
    //Height of the Jaak grid.
    private static final int HEIGHT = 100;
    
//    public static FestivalMap _festivalMap;
//    
//    private static ConcertsManager _concertManager;
    private static TurtleManager _turtleManager;
    
    
    public static JaakEnvironment createEnvironment()
    {
    	// Create the Jaak environment with the correct size.
        JaakEnvironment environment = new JaakEnvironment(WIDTH, HEIGHT);
        ActionApplier ap = environment.getActionApplier();
        
        for(int x = 0;x < WIDTH;x++)
        {
        	ap.putObject(0, 0, new Wall());
        	ap.putObject(x, HEIGHT-1, new Wall());
        }
        
        for(int y=0;y<HEIGHT;y++)
        {
        	ap.putObject(0, y, new Wall());
        	ap.putObject(WIDTH-1, y, new Wall());
        }
        
        
        _turtleManager = new TurtleManager(environment);
        _turtleManager.start();
 
        environment.setWrapped(isWrappedEnvironment);
        return environment;
    	
    }
    
    public static JaakSpawner createSpawner(JaakEnvironment environment)
    {
        return new BuildingSpawner(SPAWN_X,SPAWN_Y,SPAWN_WIDTH,SPAWN_HEIGHT);
    }
    
    public static FloorPanel createPanel(AgentAddress kernelAddress, JaakEnvironment environment) 
    {
        GridStateChannel channel = Kernels.get().getChannelManager().getChannel(kernelAddress, GridStateChannel.class);
        if (channel==null) throw new IllegalStateException();
        
       // astar = new Astar(channel);
        return new FloorPanel(channel, environment);
    }
    
    
    
    public static class TurtleManager extends Thread
   {
       
       private JaakEnvironment _environment;
       
       public TurtleManager(JaakEnvironment environment)
       {
           _environment = environment;
       }

       private TurtleManager()
       {
           throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
       }
       
       public void run()
       {
           
           while(true)
           {
               for(int x =0  ; x < _environment.getWidth() ; ++x)
               {
                   for(int y = 0 ; y < _environment.getHeight(); ++y)
                   {
                     //  System.out.println("TORTUE : "+x+" "+y);
                       for(TurtleBody turtle : _environment.getTurtles(x, y))
                       {
                          AgentID agentId = ((AgentID)turtle.getSemantic());
                         if(agentId != null && agentId.getOwner() != null && !agentId.getOwner().isAlive())
                         {
                               _environment.removeBodyFor(agentId.getOwner().getAddress());
                         }
                          
                           _environment.getActionApplier().removeTurtle(x, y, turtle);
                       }
                   }
               }
              // System.out.println("NEXT");
               try {
                   Thread.sleep(100);
               } catch (InterruptedException ex)
               {
                   Logger.getLogger(Environment.class.getName()).log(Level.SEVERE, null, ex);
               }
           }
           
       }
    }
	
}
