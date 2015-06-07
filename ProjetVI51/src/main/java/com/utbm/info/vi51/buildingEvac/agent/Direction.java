package com.utbm.info.vi51.buildingEvac.agent;

import org.arakhne.afc.math.discrete.object2d.Point2i;
import com.utbm.info.vi51.buildingEvac.utils.RandomUtils;

public enum Direction {
	
	NORTH((float) (-Math.PI/2), "NORTH", new Point2i(0,-1)),
    NORTHWEST((float)(-3*Math.PI/4), "NORTHWEST", new Point2i(1,-1)),
    WEST((float)(Math.PI), "WEST", new Point2i(1,0)),
    SOUTHWEST((float)(3*Math.PI/4), "SOUTHWEST", new Point2i(1,1)),
    SOUTH((float) (Math.PI/2), "SOUTH", new Point2i(0,1)),
    SOUTHEAST((float)(Math.PI/4), "SOUTHEAST", new Point2i(-1,1)),
    EAST((float) 0, "EAST", new Point2i(-1,0)),
    NORTHEAST((float)(-Math.PI/4), "NORTHEAST", new Point2i(-1,-1));
	
	private float value;
    private String name;
    private Point2i relative;
    
    Direction(float value, String name, Point2i relative) {
        this.value=value;
        this.name = name;
        this.relative = relative;
    }
    
    public float toFloat() {
        return value;
    }
    
    public String toString() {
        return name;
    }
    public Point2i toRelativePoint() {
        return relative;
    }
    
    public Direction getPrevious() {
        if(ordinal()-1 < 0)
            return values()[values().length-1];
        return values()[(ordinal()-1) % values().length];
    }
    public Direction getNext() {
        
        return values()[(ordinal()+1) % values().length];
    }
    
 public Direction getOpposite() {
        
        return values()[(ordinal()+4) % values().length];
    }
    
    public static Direction getRandom(){
        return values()[RandomUtils.getRand(values().length - 1)];
    }
    
    public static Direction getSens(Point2i relativePoint)
    {
        for(Direction d : values())
        {
            if(d.toRelativePoint().getX() == relativePoint.getX() && d.toRelativePoint().getY() == relativePoint.getY())
                return d;
        }
        return null;
    }
    
	

}
