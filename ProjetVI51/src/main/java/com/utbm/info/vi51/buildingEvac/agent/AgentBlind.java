package com.utbm.info.vi51.buildingEvac.agent;

import org.janusproject.jaak.envinterface.body.TurtleBody;
import org.janusproject.jaak.envinterface.body.TurtleBodyFactory;
import org.janusproject.jaak.envinterface.frustum.PointTurtleFrustum;

public class AgentBlind extends WorkingAgent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
// GENERATE THE BODY
	@Override
	 protected TurtleBody createTurtleBody(TurtleBodyFactory factory) {
	    // Create a frustum with a cross-shape with branches of size 5
	    PointTurtleFrustum frustum = new PointTurtleFrustum();
	 
	    // Create the body with the frustum
	    TurtleBody body = factory.createTurtleBody(getAddress(), frustum);
	   //Set ID
        if(body != null && _informationsTurtle != null)
            body.setSemantic(_informationsTurtle);
        
        return body;
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
