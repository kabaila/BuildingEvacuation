package com.utbm.info.vi51.buildingEvac.agent;

import java.util.Collection;
import java.util.Random;

import org.arakhne.afc.math.discrete.object2d.Point2i;
import org.janusproject.jaak.envinterface.body.TurtleBody;
import org.janusproject.jaak.envinterface.body.TurtleBodyFactory;
import org.janusproject.jaak.envinterface.frustum.CircleTurtleFrustum;
import org.janusproject.jaak.envinterface.perception.Perceivable;
import org.janusproject.jaak.envinterface.time.JaakTimeManager;
import org.janusproject.kernel.message.Message;
import org.janusproject.kernel.message.ObjectMessage;

import com.utbm.info.vi51.buildingEvac.environment.BuildingMap;
import com.utbm.info.vi51.buildingEvac.environment.WorldObject;
import com.utbm.info.vi51.buildingEvac.utils.RandomUtils;



public class AgentYoung extends WorkingAgent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//The number of young agent generated
	private static int aYoung_Nb = 0;
	//Total duration in [ms] to reach to panic
	private long _totalPanicGauge; 
    private long _currentPanicGauge;
	// Radius of the frustrum
    CircleTurtleFrustum _frustum;
    private int RADIUSFRUSTRUM = 3;
    
    private long _timeSincePreviousAction;
    
// GENERATE THE BODY		
		@Override
		 protected TurtleBody createTurtleBody(TurtleBodyFactory factory) {
		    // Create a frustum 
		    CircleTurtleFrustum frustum = new CircleTurtleFrustum(RADIUSFRUSTRUM);
		 
		    // Create the body with the frustum
		    TurtleBody body = factory.createTurtleBody(getAddress(), frustum);
		    //Set ID
		      if(body != null && _informationsTurtle != null)
		         body.setSemantic(_informationsTurtle);  
		        return body;		
		      }
		
		

		public AgentYoung() {
			super();
			changeCurrentState(INIT);
			_totalPanicGauge = RandomUtils.getRand(1*10*1000, 1*100*1000);
			_currentPanicGauge = 0;
			_informationsTurtle = new AgentID(this);
			_frustum = new CircleTurtleFrustum(RADIUSFRUSTRUM);
		     aYoung_Nb++;
		}



		@Override
		protected void findWayOut() {
			// TODO Auto-generated method stub
			
		}
        // Freak-out
		@Override
		protected void panic() {
			// TODO Auto-generated method stub
			Random rand = new Random();
			if (rand.nextBoolean())
			{
				changeCurrentState(FREAK_OUT);
			}
			else changeCurrentState(PETRIFIED);
		}
		
		
       protected void youngAgentBehaviour()
       {
    	   Collection<Perceivable> perception = getPerception();
           
           JaakTimeManager jaakTimeManager = getJaakTimeManager();
           
           _isOnSamePosition = getPosition().equals(_previousPosition);
           _previousPosition = getPosition();
           
           if(getCurrentState() != WORKING)
               if(failedMove()) return;
           //System.out.println("currentState "+getCurrentState()+" "+getPosition()+" "+_isOnSamePosition);

           if(getPosition().x() > 157)
           {
               System.out.println("kill me");
               killMe();
           }
           // perceive someone in panic mode
           Message m = this.getMessage(); 
           if(m instanceof ObjectMessage) {
               if(((ObjectMessage)m).getContent() instanceof Point2i) {
                   Random rand = new Random();
            	   if (rand.nextBoolean())
       			{
       				panic();
       			}
                 //  this._currentDestination=((Point2i)((ObjectMessage)m).getContent());
                   
               }
           }
           if(getCurrentState() == FREAK_OUT||getCurrentState() == PETRIFIED) {
               for(Perceivable p : perception) {
                   //SEND PANIC INFLUENCE
                   if (p.isTurtle()) {
                       AgentID t = (AgentID)p.getSemantic();
                       this.forwardMessage(new ObjectMessage(this._currentDestination),t.getOwner().getAddress());
                   }
               }
               
               if(getCurrentState() == FREAK_OUT)
               {
            	   //freakOut();
               }
               else{ //bePetrified}
               return;
           }
          }
           _currentPanicGauge += jaakTimeManager.getWaitingDuration();
           
           switch(getCurrentState())
           {
           case INIT: // State initial
               goToADesk(perception);
               changeCurrentState(WORKING);
               // goTo(new Point2i(120,60));
               break;
               
           case WORKING:
        	   this.sleep(15000);
        	   changeCurrentState(WANDERING);
        	   
        	   break;
           case WANDERING:
        	   this.setHeading(this.getHeadingAngle()+RandomUtils.randomBinomial((float)Math.PI/4));
               moveForward(1);
               break;
           
           case FIND_EXIT:
        	   for (Perceivable p:perception)
         	   {
         		   if(p.isObstacle())
         		   {
         			 if (p instanceof BuildingMap)
         			  
         			   {
         				   _currentPath =com.utbm.info.vi51.buildingEvac.utils.Astar.findPath(this.getPosition(), p.getPosition(), 100);
         			   }
         		   }
         		   else
         		   {
         			   changeCurrentState(state);
         		   }
         	   }
        		
        	
        	   
        	   break;
           default:
        	   
        	   if(_timeSincePreviousAction > 10*1000)
               {
                   _timeSincePreviousAction = 0;
                   changeCurrentState(WANDERING);
               }
               System.out.println("position : "+getPosition());
               break;
        	   
           }
}



	private void goToADesk(Collection<Perceivable> perception) {
		
		for (Perceivable p:perception)
 	   {
 		   if(p.isObstacle())
 		   {
 			 WorldObject Ob =  (WorldObject) p;
 			   if(Ob.isAvailable())
 			   {
 				   _currentDestination = Ob.getPosition();
 			   }
 		   }
 	   }
		
	}
       
       }
