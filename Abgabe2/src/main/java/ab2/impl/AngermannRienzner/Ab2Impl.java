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
	public void insertIntoHashSet(int[] hashtable, int element) {
	   if(!containedInHashSet(hashtable, element)) {
           //insert
           int idx = h(element, hashtable.length);
           int idxbeg = idx;
           for (int i = 0; i < hashtable.length; i++) {
               if (hashtable[idx] == element) {
                   //already contained
                   break;
               } else if (hashtable[idx] == -1) {
                   //free --> insert
                   hashtable[idx] = element;
                   break;
               } else {
                   idx = s(idxbeg, i, hashtable.length);
               }
           }
       }

    }

	@Override
	public boolean containedInHashSet(int[] hashtable, int element) {
	    int idx = h(element, hashtable.length);
        int idxbeg = idx;
        for(int i=0; i<hashtable.length; i++){
            if (hashtable[idx] == element) {
                //true
                return true;
            }
            else if(hashtable[idx] == -1){
                //not contained
                return false;
            }else{
                //sondieren
                idx = s(idxbeg, i, hashtable.length);
            }
        }

        //element not found
            return false;

    }

    private int h(int val, int size){
	    return val % size;
    }

    private int s(int j, int k, int size){
        return (j + k) % size;
    }
}
