import java.util.*;

public class MyGraph {

	protected HashSet<String> 					vertices;
	protected HashMap<String, Boolean> 			visited;

	// Map of parent for BFS/DFS

	protected HashMap<String, String>				parent;

	// (u, w, weight) ==> (vertex_from, vertex_to, edge_weight)

	protected HashMap<String, HashMap<String, Double>> edges_from;

	public MyGraph(){
		vertices 		= new HashSet<String>();
		visited 		= new HashMap<String, Boolean>();
		parent 			= new HashMap<String, String>();
		edges_from 		= new HashMap<String, HashMap<String, Double>>();	
	}

	public MyGraph(HashSet<String> vertices, HashMap<String, Boolean> visited, HashMap<String, String> parent, HashMap<String, HashMap<String, Double>> edges_from){
		this.vertices = vertices;
		this.visited = visited;
		this.parent = parent;
		this.edges_from = edges_from;
	}
	
	//########### Getters ###########
	public HashSet<String> getVertices(){
		return vertices;
	}
	
	public HashMap<String, Boolean> getVisited(){
		return visited;
	}
	
	public HashMap<String, String> getParent(){
		return parent;
	}
	
	public HashMap<String, HashMap<String, Double>> getEdgesFrom(){
		return edges_from;
	}
	//################################
	
	public void addVertex(String name){
		/*if (vertices.contains(name))
				//throw new IllegalArgumentException(‘Vertex ‘ + name + ‘ already exists’);*/

		vertices.add(name);
		edges_from.put(name, new HashMap<String, Double>());
	}

	public void addEdge(String from, String to, Double weight){
		edges_from.get(from).put(to, weight);
	}
	
	public HashMap<String, Double> getEdgesFrom(String v){
		return edges_from.get(v);
	}

	public String getParent(String v){
		return parent.get(v);
	}

	public double getEdge(String u, String v){
		if(u != null && v != null){
			return edges_from.get(u).get(v);
		} else{
			return 0.0;
		}
	}

	public void bfs(String source){
		// First reinitialize parent and visited
		for( String vertex: this.getVertices() ){
			visited.put(vertex, false);
			parent.put(vertex, null);
		}

		visited.put(source, true);

		LinkedList<String> queue = new LinkedList<String>();

		parent.put(source, source);
		queue.addLast(source);

		while(!queue.isEmpty()){
			source = queue.remove();

			for(String u: edges_from.get(source).keySet()){
				if(!visited.get(u)){
					queue.addLast(u);
					parent.put(u, source);
					visited.put(u, true);
				}
			}
		}
	}

}