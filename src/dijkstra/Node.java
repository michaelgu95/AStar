package dijkstra;

import java.util.LinkedHashMap;
import java.util.Map;

public class Node implements Comparable<Node> {

	private final int label;
	private double distanceFromSource;
	private final Map<Node, Double> neighborList;
	public int x;
	public int y;
	private Node parent;
	private boolean visited;
	private double hValue;

	public Node(int label, int x, int y) {
		this.label = label;
		this.neighborList = new LinkedHashMap<Node, Double>();
		this.x = x;
		this.y = y;
		this.visited = false;
		this.parent = null;
	}

	public void addNeighbor(Node node, double distance) {
		neighborList.put(node, distance);
	}

	public void addNeighbor(Node node){
		double dist = Math.sqrt(Math.pow(this.x -node.x ,2) +  Math.pow(this.y - node.y, 2));
		neighborList.put(node, dist);
		node.addNeighbor(this, dist);
	}


	public int getLabel() {
		return this.label;
	}

	public Map<Node, Double> getNeighborList() {
		return neighborList;
	}

	public double getDistanceFromSource() {
		return distanceFromSource;
	}

	public void setDistanceFromSource(double adjacentDistance) {
		this.distanceFromSource = adjacentDistance;
	}

	@Override
	public int compareTo(Node o) {
		return Double.compare(this.getHValue(), o.getHValue());
	}

	public Node getParent(){
		return this.parent;
	}

	public void setParent(Node p){
		this.parent = p;
	}

	public boolean isVisited(){
		return visited;
	}

	public void setVisited(boolean v){
		this.visited = v;
	}

	public double getEuclideanToTarget(Node target) {
		return Math.sqrt(Math.pow((double)(this.x - target.x), 2) + Math.pow((double)(this.y - target.y), 2));
	}
	
	public void setHValue(double h){
		this.hValue = h;
	}
	
	public double getHValue(){
		return this.hValue;
	}
	
	
}