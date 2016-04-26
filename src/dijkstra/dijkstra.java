package dijkstra;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

import java.io.File;
import java.util.Scanner;

public class dijkstra {
	static int source;
	static int target;
	static int numVertices;
	static int numEdges;
	static Node[] vertices;

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.print("Enter the file name with extention : ");
		try {
			File file = new File(input.nextLine());
			input = new Scanner(file);
			processInput(input);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		//run optimized dijkstra's
		try{
			double stime = System.currentTimeMillis();
			System.out.println("Length of path: " + findShortestDistanceOptimized(vertices[1], vertices[66666]));
			double etime = System.currentTimeMillis();
			double time = (etime - stime) / 1000.0;
			System.out.println("Time to generate using optimized dijkstra's: " + time);
		}catch(RuntimeException e){
			if(e.getMessage().equals("no path")){
				System.out.println("No path for vertices specified");
			}
		}
	}

	public static void processInput(Scanner input) {
		String line = input.nextLine();
		//check for spaces between sections
		while(line.equals("")){
			line = input.nextLine();
		}

		//set fields corresponding to first line
		String[] split = line.split("\\s+");
		numVertices = Integer.parseInt(split[0]);
		numEdges = Integer.parseInt(split[1]);
		vertices = new Node[numVertices];

		line = input.nextLine();
		//check for spaces between sections
		while(line.equals("")){
			line = input.nextLine();
		}

		//add all vertices
		for(int i = 0; i<numVertices; i++){

			//check for spaces before a line starts
			split = line.split("\\s+");
			int indexOfStart = 0;
			int indexOfCheck = 0;
			while(split[indexOfCheck].equals("")){
				indexOfStart++;
				indexOfCheck++;
			}

			vertices[i] = new Node(Integer.parseInt(split[indexOfStart]), Integer.parseInt(split[indexOfStart+1]), Integer.parseInt(split[indexOfStart+2]));
			line = input.nextLine();
		}

		line = input.nextLine();
		//check for spaces between sections
		while(line.equals("")){
			line = input.nextLine();
		}

		//next stream of text -> edges
		while(input.hasNextLine()){
			line = input.nextLine();
			//check for spaces between sections
			while(line.equals("")){
				line = input.nextLine();
			}

			//we're on the last line -> not an edge, set source and target node 
			if(!input.hasNextLine()){
				split = line.split("\\s+");
				//check for spaces before line starts
				int indexOfStart = 0;
				int indexOfCheck = 0;
				while(split[indexOfCheck].equals("")){
					indexOfStart++;
					indexOfCheck++;
				}
				//set source and target
				source = Integer.parseInt(split[indexOfStart]);
				target = Integer.parseInt(split[indexOfStart+1]);

				//otherwise not last line -> edge
			}else{
				split = line.split("\\s+");
				//check for spaces before a line starts
				int indexOfStart = 0;
				int indexOfCheck = 0;
				while(split[indexOfCheck].equals("")){
					indexOfStart++;
					indexOfCheck++;
				}
				int fromNodeIndex = Integer.parseInt(split[indexOfStart]);
				int toNodeIndex = Integer.parseInt(split[indexOfStart+1]);
				vertices[fromNodeIndex].addNeighbor(vertices[toNodeIndex]);
			}
		}
	}


	// optimized dijkstra's that uses A* 
	public static double findShortestDistanceOptimized(Node from, Node to) {
		int numExamined = 0;
		Queue<Node> toVisit = new PriorityQueue<Node>();
		//add source node to queue
		toVisit.add(from);
		
		//set all node distance values to infinity
		for(Node n: vertices){
			n.setDistanceFromSource(Integer.MAX_VALUE);
		}
		
		//set source distance to 0
		from.setDistanceFromSource(0);
		while (!toVisit.isEmpty()) {
			Node min = toVisit.remove();

			//found path
			if(min == to) {
				Node originalMin = min;
				//print out the path
				System.out.print("Path: " + min.getLabel());
				do{
					System.out.print("<-");
					System.out.print(min.getParent().getLabel());
					min = min.getParent();
				}while(min.getParent() != null);
				System.out.println("");
				System.out.println("Number of vertices examined: " + numExamined);
				//return length of the path
				return originalMin.getDistanceFromSource();
			}
			if(min.isVisited()) {
				continue;
			}
			min.setVisited(true);
			
			//relax
			for(Entry<Node, Double> neighborEntry : min.getNeighborList().entrySet()) {
				Node neighbor = neighborEntry.getKey();
				double adjacentDistance = min.getDistanceFromSource() + neighborEntry.getValue(); 
				numExamined++;
				
				if (neighbor.getDistanceFromSource() > adjacentDistance && !neighbor.isVisited()) {
					neighbor.setDistanceFromSource(adjacentDistance);
					neighbor.setParent(min);
					neighbor.setHValue(adjacentDistance + neighbor.getEuclideanToTarget(to)); // this line -> makes implementation into A*
					toVisit.add(neighbor);
					
					
				}
			}
		}
		throw new RuntimeException("no path");
	}

}
