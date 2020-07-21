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
	public int quickselect(int[] data, int k) {
        int left = 0;
        int right = data.length-1;
        if(data.length==0 || k<1){ //empty array or unvalid k
            return data[-1];
        }
        if(k>data.length){ //k bigger than array-size
            return data[k];
        }
       return selectRec(data, left,right,k-1);


    }

    private static int selectRec(int[] data, int left, int right, int k) { //submethod with extended params for recursive call
        if (left == right) { // CornerCase = nur ein Element
            return data[left];
        }


        int pivotVal =findPivot(data,left,right);
        int  pivotIndex = partition(data, left, right, pivotVal);

        if (k == pivotIndex) {// The pivot is in its final sorted position
            return data[k];
        } else if (k < pivotIndex) {
            return selectRec(data, left, pivotIndex - 1, k);
        } else {
            return selectRec(data, pivotIndex + 1, right, k);
        }
    }

    private static int partition(int[] data, int left, int right, int pivotValue) {
	    //find Pivot index O(n)
        int idx = 0;
        for(idx = left; idx<right;idx++){
            if(data[idx] == pivotValue)break;
        }


        swap(data, idx, right); // move pivot to end
        int storeIndex = left;
        for(int i = left; i < right; i++) {
            if(data[i] < pivotValue) {
                swap(data, storeIndex, i);
                storeIndex++;
            }
        }
        swap(data, right, storeIndex); // Move pivot to final place
        return storeIndex;
    }
    
    private static void swap(int[] data, int i1, int i2){ //simple triangle swap
        if (i1 != i2) {
            int temp = data[i1];
            data[i1] = data[i2];
            data[i2] = temp;
        }

    }

    private static int findPivot(int[] data, int left, int right){

	    return getMedianOfMedian(data,left , right);

    }

    public static int med5(int[] data, int start, int end){ //method to find median of 5
        if(start==end)return data[start];

        //copy array for insertion sort-->  Obsolet changed access in loop relative to start-index (saved 100ms with quickselect singleCall
        /*int[]arr = new int[end-start+1];
        for(int i = 0; i<= end-start; i++){
            arr[i]=data[start+i];
        }*/
        //sort elements with insertion sort --> best performance with only 5 elements!
        int n = end-start+1;
        for (int i = 1; i < n; ++i) {
            int key = data[i+start];
            int j = i - 1;
            while (j >= 0 && data[j+start] > key) {
                data[start +j + 1] = data[start+j];
                j = j - 1;
            }
            data[start+j + 1] = key;
        }
        //return median
        return data[start + n/2];
    }

    //get value for median of median
    private static int getMedianOfMedian(int[] data, int start, int end){
	    int n=end-start;
        if(n<=5)
            return med5(data,start,end);//less than 5 elements left
        int nGroups = n/5 + (n%5==0?0:1);
        int[] medians = new int[nGroups];//array of medians of sub-groups

        for(int i=0;i<nGroups;i++){
            int groupStart=i*5+start;
            int groupEnd = Math.min(end, groupStart+5-1);
            medians[i]=med5(data,groupStart,groupEnd);
        }
        return getMedianOfMedian(medians,0,medians.length-1); //recursive call with array containing the medians of subgroups
    }


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
