package ab1.impl.Angermann_RIenzner;

import ab1.Ab1;


public class Ab1Impl implements Ab1 {

	@Override
	public int binarySearch(int[] data, int element) {

		int low = 0;
		int high = data.length - 1;

		while (low <= high) {
			int mid = (low + high) /2;
			int midVal = data[mid];

			if (midVal < element)
				low = mid + 1;
			else if (midVal > element)
				high = mid - 1;
			else
				return mid; // Eintrag gefunden
		}
		return -1;  // Eintrag nicht gefunden


	}



	@Override
	public void shellSort(Integer[] data) {
		int n = data.length;

		for (int gap = n / 2; gap > 0; gap /= 2) {
			for (int i = gap; i < n; i++) {
				int key = data[i];
				int j = i;
				while (j >= gap && data[j - gap] > key) {
					data[j] = data[j - gap];
					j -= gap;
				}
				data[j] = key;
			}
		}

	}

	public static void main(String[] args){
			Ab1Impl ab1 = new Ab1Impl();
			Integer[] arr = {2,4,6,7,7,4,3};
			ab1.quickSortStable(arr);
	}


	@Override
	public void quickSortStable(Integer[] data) {
			Wrapper[] wrappArray = new Wrapper[data.length];

			//Copy Array into Wrapper Array with new Comparator (val&idx)
			for(int i=0;  i < data.length;i++){
				wrappArray[i]= new Wrapper(data[i], i);
			}

			wrapQuickSort(0, data.length-1, wrappArray);

			//rebuild Integer array

			for(int i=0; i<data.length; i++){
				data[i]=wrappArray[i].val;
			}

			//quickSort(0, data.length-1, data);
	}

	private void wrapQuickSort(int low, int high, Wrapper[] data){
		int i = low;
		int j = high;
		Wrapper pivot =  data[i+(j-i)/2]; //Mittleres Element = PIVOT

		//split in 2 Arrays
		while(i<j) {

			while (data[i].compareTo(pivot)<0) {
				i++;
			}
			while (data[j].compareTo(pivot)>0) {
				j--;
			}
			if (i <= j) {
				Wrapper temp = data[i];
				data[i] = data[j];
				data[j] = temp;
				i++;
				j--;
			}
		}
		if (low < j) { wrapQuickSort(low, j, data); } if (i < high) { wrapQuickSort(i, high,data); }
	}

	private void quickSort(int low, int high, Integer[] data){
		int i = low;
		int j = high;
		Wrapper pivot =  new Wrapper(data[i+(j-i)/2], i+(j-i)/2); //Mittleres Element = PIVOT

		//split in 2 Arrays
		while(i<j) {

			while (new Wrapper(data[i], i).compareTo(pivot)<0) {
				i++;
			}
			while (new Wrapper(data[j],j).compareTo(pivot)>0) {
				j--;
			}
			if (i <= j) {
				int temp = data[i];
				data[i] = data[j];
				data[j] = temp;
				i++;
				j--;
			}
		}
		if (low < j) { quickSort(low, j, data); } if (i < high) { quickSort(i, high,data); }

	}


	@Override
	public int[][] mult(int[][] m1, int[][] m2) {
		return new int[0][];
	}

	private class Wrapper implements Comparable<Wrapper>{
		private Integer val;
		private Integer idx;

		public Wrapper(Integer val, Integer idx) {
			this.val = val;
			this.idx = idx;
		}

		@Override
		public int compareTo(Wrapper o) {
			int cmp;
			cmp = this.val.compareTo(o.val);
			if(this.val.equals(o.val))
				cmp = this.idx.compareTo(o.idx);
			return cmp;
		}


	}
}
