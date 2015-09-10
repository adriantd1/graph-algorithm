import java.util.*;
import java.lang.*;

public class Dijkstra{
	private IndexedHeap 			pq;
	private HashSet<String> 			toVisit;
	private HashSet<String> 			visited;
	private HashMap<String, String>		parent;
	private HashMap<String, Double>		distance;
	private String source;

	public Dijkstra(){
		pq		 	= new IndexedHeap();
		toVisit 	 = new HashSet<String>();
		visited 	 = new HashSet<String>()	;
		parent		 = new HashMap<String, String>();
		distance	 = new HashMap<String, Double>();
	}

	public void initializePQ(MyGraph graph){
		for(String vertex : graph.getVertices()){
			pq.add(vertex, Double.POSITIVE_INFINITY);
			toVisit.add(vertex);
			distance.put(vertex, Double.POSITIVE_INFINITY);
			parent.put(vertex, null);
		}
	}

	public HashSet<String> getVisited(){
		return visited;
	}

	public HashMap<String, Double> getDistance(){
		return distance;
	}

	public void executeDijkstra(MyGraph graph, String start){

		initializePQ(graph);

		source = start;
		pq.changePriority("a", 0.0);
		distance.put(source, 0.0);
		parent.put(source, source);
		visited.add(source);
		toVisit.remove(source);

		String last_added = pq.removeMin();
		HashMap<String, Double> neighbouring_edges = graph.getEdgesFrom(last_added);
		
		while(!toVisit.isEmpty()){
			/*for(String vertex : visited){
				System.out.print(vertex + " ");
			}
			System.out.println();*/
			// First change the priorities considering the new crossing edges from last_added.
			// last_added is u and vertex is v in the edge (u,v). weight(u,v) is graph.getEdge(u,v)
			for(String vertex : neighbouring_edges.keySet()){
				if(toVisit.contains(vertex)){
					if(parent.get(vertex) == null){
						parent.put(vertex, last_added);
						distance.put(vertex, distance.get(last_added) + graph.getEdge(last_added, vertex));
						pq.changePriority(vertex, distance.get(vertex));
					} else{
						//if the new crossing edges allow for a shorter path to vertex
						if(distance.get(last_added) + graph.getEdge(last_added, vertex) < distance.get(vertex)){
							parent.put(vertex, last_added);
							distance.put(vertex, distance.get(last_added) + graph.getEdge(last_added, vertex));
							pq.changePriority(vertex, distance.get(vertex));
						}
					}
				}
			}
			//Once the pq has been updated, remove the min vertex
			last_added = pq.removeMin();
			neighbouring_edges = graph.getEdgesFrom(last_added);
			toVisit.remove(last_added);
			visited.add(last_added);
		}
	}
}



