package com.utbm.info.vi51.buildingEvac.environment;

import org.arakhne.afc.math.continous.object2d.Point2f;
import org.arakhne.afc.math.continous.object2d.Shape2f;
import org.janusproject.jaak.envinterface.perception.Obstacle;

public class WorldObject extends Obstacle {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2200867051750731125L;

	private Point2f position;

	//private boolean isTraversable; *May not be used !

	private Shape2f boundingBox;
	private boolean available=true;
	
	public boolean isAvailable() {
		return available;
	}
	public void setAvailable(boolean available) {
		this.available = available;
	}
	

	

}
