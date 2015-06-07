package com.utbm.info.vi51.buildingEvac.environment;

import java.util.ArrayList;

import org.arakhne.afc.math.discrete.object2d.Point2i;
import org.janusproject.jaak.envinterface.perception.Obstacle;

public class Construct {
	protected int Xsg;
	protected int Ysg;
	
	protected int Xid;
	protected int Yid;

// Exit door center
	protected int Xci; 
	protected int Yci;
	
	protected ArrayList<Obstacle> o;
        
        public Point2i getInteractCenter()
        {
            return new Point2i(Xci, Yci);
        }
	
}
