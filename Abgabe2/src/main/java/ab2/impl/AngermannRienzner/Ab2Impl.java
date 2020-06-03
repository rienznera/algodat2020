package ab2.impl.AngermannRienzner;

import ab2.Ab2;

public class Ab2Impl implements Ab2 {

	@Override
	public void toHeap(int[] data) {

        for(int i = data.length-1; i >=0; i--){
            heapM(data, i);
        }

	}

    private static void heapM(int[] data, int i) {

        int largest;
        int left = i * 2+1;
        int right = i * 2 + 2;
        if (((left < data.length) && (data[left] > data[i]))) {
            largest = left;
        } else {
            largest = i;
        }

        if (((right < data.length) && (data[right] > data[largest]))) {
            largest = right;
        }
        if (largest != i) {
            int temp = data[i];
            data[i]=data[largest];
            data[largest] = temp;


            heapM(data, largest);
        }
    }

	@Override
	public int quickselect(int[] data, int k) { return 0; }

	@Override
	public void insertIntoHashSet(int[] hashtable, int element) { }

	@Override
	public boolean containedInHashSet(int[] hashtable, int element) {
	    for(int i=0; i < hashtable.length; i++){
	        if(hashtable[i]>=0)
	            return true;
        }
        return false;
    }

    private int h(int val, int size){
	    return val % size;
    }
}
