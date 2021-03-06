package com.utbm.info.vi51.buildingEvac.agent;

import org.janusproject.jaak.envinterface.body.TurtleBody;
import org.janusproject.jaak.envinterface.body.TurtleBodyFactory;
import org.janusproject.jaak.envinterface.frustum.CircleTurtleFrustum;

import com.utbm.info.vi51.buildingEvac.utils.RandomUtils;

public class AgentMotionless extends WorkingAgent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//The number of Motionless agent generated
	private static int aMotionless_Nb = 0;

	//Total duration in [ms] to reach to panic
	private long _totalPanicGauge; 
    private long _currentPanicGauge;
    
	// Radius of the frustrum
    CircleTurtleFrustum _frustum;
	private int RADIUSFRUSTRUM = 3;
	
	
// GENERATE THE BODY	
	@Override
	 protected TurtleBody createTurtleBody(TurtleBodyFactory factory) {
	    // Create a frustum
	    CircleTurtleFrustum frustum = new CircleTurtleFrustum(RADIUSFRUSTRUM);
	 
	    // Create the body with the frustum
	    TurtleBody body = factory.createTurtleBody(getAddress(), frustum);
	    // Set ID
        if(body != null && _informationsTurtle != null)
            body.setSemantic(_informationsTurtle);
        
        return body;
	  }
	
	 public AgentMotionless() {
		super();
		changeCurrentState(INIT);
		_totalPanicGauge = RandomUtils.getRand(1*10*1000, 1*100*1000);
		_currentPanicGauge = 0;
		_informationsTurtle = new AgentID(this);
		_frustum = new CircleTurtleFrustum(RADIUSFRUSTRUM);
		aMotionless_Nb ++;
	}

	@Override
	public void findWayOut() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void panic() {
		// TODO Auto-generated method stub
		
	}

}
