package com.utbm.info.vi51.buildingEvac.environment;

import org.janusproject.jaak.envinterface.body.TurtleBody;
import org.janusproject.jaak.spawner.JaakAreaSpawner;
import org.janusproject.jaak.turtle.Turtle;
import org.janusproject.kernel.address.AgentAddress;
import org.janusproject.kernel.time.KernelTimeManager;

public class BuildingSpawner extends JaakAreaSpawner 
{

	public BuildingSpawner(int x, int y, int width, int height)
    {
	    super(x,y,width,height);
    }
	
	
	@Override
    protected float computeSpawnedTurtleOrientation(KernelTimeManager arg0)
    {
	    // TODO Auto-generated method stub
	    return 0;
    }

	@Override
    protected Turtle createTurtle(KernelTimeManager arg0)
    {
	    return null;
    }

	@Override
    protected Object[] getTurtleActivationParameters(Turtle arg0,
            KernelTimeManager arg1)
    {
	    // TODO Auto-generated method stub
	    return null;
    }

	@Override
    protected boolean isSpawnable(KernelTimeManager arg0)
    {
	    // TODO Auto-generated method stub
	    return false;
    }

	@Override
    protected void turtleSpawned(Turtle arg0, TurtleBody arg1,
            KernelTimeManager arg2)
    {
	    // TODO Auto-generated method stub
	    
    }

	@Override
    protected void turtleSpawned(AgentAddress arg0, TurtleBody arg1,
            KernelTimeManager arg2)
    {
	    // TODO Auto-generated method stub
	    
    }

}
