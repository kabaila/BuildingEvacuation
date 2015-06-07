package com.utbm.info.vi51.buildingEvac.utils;

import org.arakhne.afc.math.discrete.object2d.Point2i;



public class Connection {
	private NodeRecord fromNode;
    private Point2i endCoordinates;
    private double cost;
    
    public Connection(NodeRecord fromNode, Point2i endCoordinates) {
        this.fromNode = fromNode;
        this.endCoordinates = endCoordinates;
    }

	public NodeRecord getFromNode() {
		return fromNode;
	}

	public void setFromNode(NodeRecord fromNode) {
		this.fromNode = fromNode;
	}

	public Point2i getEndCoordinates() {
		return endCoordinates;
	}

	public void setEndCoordinates(Point2i endCoordinates) {
		this.endCoordinates = endCoordinates;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}
 

}
