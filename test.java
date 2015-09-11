import java.util.*;

public class test{

	public static void main(String args[]){
		MyGraph graph = new MyGraph();

		graph.addVertex("a");
		graph.addVertex("b");
		graph.addVertex("c");
		graph.addVertex("d");
		graph.addVertex("e");
		graph.addVertex("f");
		graph.addVertex("g");

		graph.addEdge("a", "b", 3.0);
		graph.addEdge("a", "c", 10.0);
		graph.addEdge("c", "d", 2.0);
		graph.addEdge("c", "f", 5.0);
		graph.addEdge("b", "f", 2.0);
		graph.addEdge("b", "g", 1.0);
		graph.addEdge("d", "e", 1.0);
		graph.addEdge("f", "e", 4.0);
		graph.addEdge("g", "e", 2.0);

		//tests the ford-fulkerson algorithm
		FordFulkerson alg = new FordFulkerson(graph, "a", "e");
		alg.executeFF();
		System.out.println();
		
		//tests the Dijkstra algorithm
		String source = "a";
		
		Dijkstra alg1 = new Dijkstra();
		alg1.executeDijkstra(graph, source);
		System.out.println("The shortest distance from " + source + " is:");
		for(String name : alg1.getVisited()){
			System.out.println(name + " " + alg1.getDistance().get(name));
		}
	}
	
	// #### QUICKSORT ####
	public static void quicksort(int[] array,int lo, int hi){
		if(lo<hi){
			int p = partition(array, lo, hi);
			quicksort(array, lo, p-1);
			quicksort(array, p+1, hi);
		}
	}

	public static int partition(int[] array, int lo, int hi){
		int pivot = array[hi];
		int i = lo - 1;
		for(int j = lo; j <= hi-1; j++){
			if(array[j] <= pivot){
				i++;
				swap(array,i,j);
			}
		}
		i++;
		swap(array,i,hi);
		return i;
	}

	// ######### SWAP ###########
	public static void swap(int[] array, int i, int j){
		int temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}

	// ######## HEAPSORT ###########
	public static void heapify(int[] array, int count){
		int start = (int)Math.floor((count - 2)/2);
		while(start>=0){
			siftDown(array, start, count - 1);
			start = start - 1;
		}
	}

	public static void siftDown(int[] array, int start, int end){
		int root = start;
		while(root*2+1 <= end){
			int child = root*2 + 1;
			int to_swap = root;

			if(array[to_swap] < array[child]){
				to_swap = child;
			}

			if((child+1)<=end && array[to_swap] < array[child+1]){
				to_swap = child +1;
			}

			if(to_swap == root){
				return;
			} else{
				swap(array,root, to_swap);
				root = to_swap;
			}
		}
	}

	public static void heapsort(int[] array){
		int count = array.length;
		heapify(array, count);

		int end = count - 1;
		while(end>0){
			swap(array,end, 0);
			end = end - 1;
			siftDown(array, 0, end);
		}
	}
}