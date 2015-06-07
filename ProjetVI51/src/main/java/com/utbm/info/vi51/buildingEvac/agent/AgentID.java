package com.utbm.info.vi51.buildingEvac.agent;

import java.lang.ref.WeakReference;

import org.janusproject.kernel.agent.Agent;

public class AgentID {
	
private WeakReference<Agent> _owner;
    
    public AgentID(Agent owner)
    {
        this._owner = new WeakReference<Agent>(owner);
    }
    
    public Agent getOwner()
    {
        return _owner.get();
    }


}
