package com.utbm.info.vi51.buildingEvac.agent;

import java.util.ArrayList;

import org.arakhne.afc.math.continous.object2d.Vector2f;
import org.arakhne.afc.math.discrete.object2d.Point2i;
import org.janusproject.jaak.turtle.Turtle;

import com.utbm.info.vi51.buildingEvac.environment.Construct;



public abstract class WorkingAgent extends Turtle {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7070049382116463262L;

	
	private String _currentState = "";
    protected Point2i _currentDestination;
    protected AgentID _informationsTurtle;
// PATH
    protected ArrayList<Point2i> _currentPath;
// TARGET 
    private Construct _currentConstructDestination;
// POSITION'S MEMENTO
    protected Point2i _previousPosition;
    protected boolean _isOnSamePosition;  
    protected int _nbFailMoves = 0;
    
	 public static final String INIT = "INIT";
	 public static final String ARRIVED = "ARRIVED";
// CORRESPONDS TO THE TASKS DONE IN NORMAL SATE 
	 public static final String WANDERING = "WANDERING";
	 public static final String WORKING ="WORKING";
	 public static final String GO_TO_NEXT_ROOM= "GO_TO_NEXT_ROOM";
//CORRESPONDS TO SEARCHING FOR A SAFE EXIT CALMLY
	 public static final String FIND_EXIT = "FIND_EXIT";
// CORRESPONDS TO THE PANIC STATE
	 public static final String FREAK_OUT = "FREAK_OUT";
	 public static final String PETRIFIED = "PETRIFIED";
// TO LEAVE THE FINAL EXIT DOOR
	 public static final String LEAVE_BUILDING = "LEAVE_BUILDING";
	
	
	 
	 public WorkingAgent() {
	        super();
	        //System.out.println("suicide : "+canCommitSuicide());
	        setCommitSuicide(true);
	        _informationsTurtle = new AgentID(this);
	        changeCurrentState(WANDERING);
	            
	    }
	
// TO MANIPULATE CURRENTE STATE
	 public void changeCurrentState(String state, boolean removeDestinations) {
	       _currentState = state;
	       if(removeDestinations)
	       {
	          _currentPath = null;
	          _currentConstructDestination = null;
	          _currentDestination=null; 
	       }
	    }
	 
	 public void changeCurrentState(String state) {
	        changeCurrentState(state, true);
	    }
	 public String getCurrentState() {
	        return _currentState;
	    }
//--------------------------------------------------

//WHAT CAN ANY AGENT DO	 
    protected abstract void findWayOut();
    
    protected abstract void panic();
    
    protected void kill() {
        killMe();
    }
//--------------------------------------------------	
// SEEK
    // TARGET CALCULATION
    protected Point2i calculateSeekPosition()
    { 
    	Point2i seekPosition =(_currentConstructDestination == null)? _currentDestination:_currentConstructDestination.getInteractCenter();
    	return seekPosition;
    }
    //ARRIVE
    protected boolean arrived() {
        Point2i seekPosition = this.calculateSeekPosition();
        if(seekPosition == null) return false;
        return seekPosition.equals(getPosition());
    }
    protected void seek() {
		
		Point2i seekPosition=this.calculateSeekPosition();
		Point2i position = this.getPosition();
		if(seekPosition != null && !position.equals(seekPosition)) {
            Vector2f direction = new Vector2f();
            direction.sub(seekPosition, position);
            direction.normalize();
            this.setHeading(direction);
            moveForward(1);
        
             }
		}
	protected void seekDestination(){

		Point2i seekPosition=this.calculateSeekPosition();
		if(seekPosition == null) return;
	    // currentPosition<-----Distance------>seekPosition
		float distance = seekPosition.distance(getPosition());
 //LOG  //System.out.println("+distance+" "+seekPosition+" "+getPosition());
		     //case1: Arrived on destination
		     if(distance == 1.0) 
	              {
		    	 	Point2i relativePoint = new Point2i(seekPosition.getX() - this.getPosition().getX(),seekPosition.getY()-this.getPosition().getY());
		     		System.out.println("OK DISTANCE "+relativePoint);
		     		move(relativePoint.getX(), relativePoint.getY(), true);
	            
		     		changeCurrentState(ARRIVED);
	            
		     		return;
	              }
		     //case2: Close to destination
		     if(distance < 10.0)
		        {
		            _currentPath = null;
		        }
		    //case3: Known Path
		     if(_currentPath != null)
		        {
		    	    //Follow path
		            gotoNextNodeInPath(); 
		            return;
		        }
		     //Move
		     seek();
		     
		     //case4: Blocked in same position
		     if(_isOnSamePosition)
		        {
		            //System.out.println("BLOCKED");
		            Point2i endPosition = seekPosition;
		            _currentPath = com.utbm.info.vi51.buildingEvac.utils.Astar.findPath(this.getPosition(), endPosition, 100);
		            if(_currentPath != null && _currentPath.size() > 0)
		                _currentPath.remove(0);
		            
		        }
	}	
    //FAILED MOVE
	protected boolean failedMove(){
		if(_isOnSamePosition)
            _nbFailMoves++;
        else
            _nbFailMoves = 0;
        if(_nbFailMoves > 20)
        {
            //goToAPlayingConcert();
            return true;
        }
        if(_nbFailMoves > 10)
        {
            Direction randomDir = Direction.getRandom();
            //System.out.println("BLOCKED :'("+randomDir.toString());
            setHeading(randomDir.toFloat());
            moveForward(1);
            return true;
        }
        return false;
		
	}
//--------------------------------------------------
//Path Following
	protected void gotoNextNodeInPath() {
        if(_currentPath == null || _currentPath.size() == 0)
            return;
        Point2i p = _currentPath.remove(0);
        if(_currentPath.size() == 0)
            _currentPath = null;
        Vector2f direction = new Vector2f();
        direction.sub(p,this.getPosition());
        direction.normalize();
        this.setHeading(direction);
        moveForward(1);
    }
//---------------------------------------------------
}
