import java.util.*;
import java.lang.*;

public class FordFulkerson{
	private MyGraph 						capacities;
	private MyGraph 						flow;
	private MyGraph 						res_graph; //residual graph of graph
	private String 						source;
	private String 						terminal;
	private double 						max_flow;
	
	public FordFulkerson(){
		capacities = new MyGraph();
		flow = new MyGraph();
		res_graph = new MyGraph();
		max_flow = 0;
	}
	
	public FordFulkerson(MyGraph c, String s, String t){
		capacities = c;
		source = s;
		terminal = t;
		max_flow = 0;
		
		buildGraphs(); 
	}
	
	public void buildGraphs(){
		flow = new MyGraph();
		res_graph = new MyGraph();
		
		for(String vertex : capacities.getVertices()){
			flow.addVertex(vertex);
			res_graph.addVertex(vertex);
		}
		
		for(String from : capacities.getVertices()){
			for(String to : capacities.getEdgesFrom().get(from).keySet()){
				flow.addEdge(from, to, 0.0);
				res_graph.addEdge(from, to, capacities.getEdge(from, to));
			}
		}
		
	}
	
	// Returns a map of parent to child in order to retrace back the augmenting path
	public HashMap<String, String> findAugPath(String source){
		/*for(String v : flow.getEdgesFrom("a").keySet()){
			System.out.println(v);
		}*/
		res_graph.bfs(source);
		HashMap<String, String> augPath = new HashMap<String, String>();
		String vertex =  terminal;
		
		
		//Inverse the parent mapping to get the path from source to terminal;
		while(!vertex.equals(source) && res_graph.getParent().get(terminal) != null){
			augPath.put(res_graph.getParent().get(vertex), vertex);
			vertex = res_graph.getParent().get(vertex);
		}
		return augPath;
	}
	
	// Find the bottleneck of the augmenting path
	public Double findBottleneck(HashMap<String, String> path){
		double bottleneck = Double.POSITIVE_INFINITY;
		double temp = 0;
		for(String vertex : path.keySet()){
			if(!vertex.equals(terminal)){
				System.out.print(vertex + " ");
				System.out.print(path.get(vertex) + " ");
				System.out.println(res_graph.getEdge(vertex, path.get(vertex)));
				temp = res_graph.getEdge(vertex, path.get(vertex));
				if(temp < bottleneck){
					bottleneck = temp;
				}
			}
		}
		System.out.println();
		return bottleneck;
	}
	
	// Update the flow on the graph
	public void updateFlow(HashMap<String, String> path, double bottleneck){
		for(String vertex : path.keySet()){
			if(!vertex.equals(terminal)){
				//if forward edge
				if(capacities.getEdgesFrom().get(vertex).containsKey(path.get(vertex))){
					flow.addEdge(vertex, path.get(vertex), flow.getEdge(vertex, path.get(vertex)) + bottleneck);
				} 
				//if backward edge
				else{
					flow.addEdge(vertex, path.get(vertex), flow.getEdge(vertex, path.get(vertex)) - bottleneck);
				}
			}
		}
	}
	
	// Update the residual graph
	public void updateResGraph(HashMap<String, String> path, double bottleneck){
		String from;
		String to;
		for(String vertex : path.keySet()){
			from = vertex;
			to = path.get(vertex);
			//if capacity != flow
			if(capacities.getEdge(from, to) > flow.getEdge(from, to)){
				res_graph.addEdge(from, to, capacities.getEdge(from, to) - flow.getEdge(from, to));
				//if capacity - flow != 0, update backward edge
				if(capacities.getEdge(from, to) - flow.getEdge(from, to) != 0){
					res_graph.addEdge(to, from, flow.getEdge(from, to));
				} else if(res_graph.getEdgesFrom().get(to).containsKey(from)) {
					res_graph.getEdgesFrom().get(to).remove(from);
				}
			//if capacity == flow and the edge wasnt removed already, remove it
			}else if(res_graph.getEdgesFrom().get(vertex).containsKey(to)){
				res_graph.getEdgesFrom().get(vertex).remove(to);
			}
		}
	}
	
	public void executeFF(){
		max_flow = 0;
		
		HashMap<String, String> aug_path = new HashMap<String, String>();
		aug_path = findAugPath("a");
		/*for(String vertex : aug_path.keySet()){
			System.out.println(vertex);
		}*/
		double bottleneck = 0.0;
		while(!aug_path.isEmpty()){
			bottleneck = findBottleneck(aug_path);
			updateFlow(aug_path, bottleneck);
			updateResGraph(aug_path, bottleneck);
			max_flow = max_flow + bottleneck;
			aug_path = findAugPath(this.source);
		}
		System.out.println(max_flow);
	}
}



