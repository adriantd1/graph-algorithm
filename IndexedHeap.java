import java.util.*;
import java.lang.*;

public class IndexedHeap{

	private ArrayList<Double> values;
	private ArrayList<String> name;
	private HashMap<String, Integer> nameToIndex;

	public IndexedHeap(){
		values = new ArrayList<Double>();
		name = new ArrayList<String>();
		nameToIndex = new HashMap<String, Integer>();

		values.add(0.0);
		name.add(null);
	}

	public int parentIndex(int i){
		return i/2;
	}

	public int leftChildIndex(int i){
		return i*2;
	}

	public int rightChildIndex(int i){
		return i*2+1;
	}

	public boolean isLeaf(int i){
		if(i*2>=values.size()){
			return true;
		}
		return false;
	}

	public ArrayList<String> getName(){
		return name;
	}

	public double getValue(String name){
		if(!nameToIndex.containsKey(name)){
			//throw new IllegalArgumentException;
		}

		return values.get(nameToIndex.get(name));
	}

	public String min(){
		return name.get(1);
	}

	public void upHeap(int i){
		if(i>1){
			if(values.get(i) < values.get(parentIndex(i))){
				swap(i,parentIndex(i));
				upHeap(parentIndex(i));
			}
		}
	}

	public void downHeap(int i){
		if(!isLeaf(i)){
			if(leftChildIndex(i)<values.size() && rightChildIndex(i)>=values.size()){
				if(values.get(i)>values.get(leftChildIndex(i))){
					swap(i, leftChildIndex(i));
				}
			}
			else{
				if(Math.min( values.get(leftChildIndex(i)), values.get(rightChildIndex(i)) ) < values.get(i) ){
					if(values.get(leftChildIndex(i))>values.get(rightChildIndex(i))){
						swap(i, rightChildIndex(i));
						downHeap(rightChildIndex(i));
					} else{
						swap(i, leftChildIndex(i));
						downHeap(leftChildIndex(i));
					}
				} 
			}
		}
	}

	public void swap(int i, int j){
		double tempValue = values.get(j);
		String tempName = name.get(j);

		values.set(j, values.get(i));
		name.set(j, name.get(i));

		values.set(i, tempValue);
		name.set(i, tempName);

		nameToIndex.put(name.get(i), i);
		nameToIndex.put(name.get(j), j);
	}

	public String removeMin(){
		String min = name.get(1);
		if(values.size()>2){
			//Replace the first element by the last and restore the heap
			
			nameToIndex.put(name.get(name.size()-1), 1);
			values.set(1, values.get(values.size()-1));
			values.remove(values.size()-1);
			name.set(1, name.remove(name.size()-1));

			downHeap(1);
		} else{
			values.remove(min);
			name.remove(min);
		}
			nameToIndex.remove(min);
			return min;
	}

	public void add(String name, double value){
		int index = values.size();
		values.add(value);
		this.name.add(name);
		nameToIndex.put(name, index);
		upHeap(index);
	}

	public void changePriority(String name, double new_value){
		int index = nameToIndex.get(name);
		double value = values.get(index);
		values.set(index, new_value);
		if(new_value>value){
			downHeap(index);
		} else {
			upHeap(index);
		}
	}
}