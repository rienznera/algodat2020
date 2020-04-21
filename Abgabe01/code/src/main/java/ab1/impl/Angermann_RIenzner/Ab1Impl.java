package ab1.impl.Angermann_RIenzner;

import ab1.Ab1;

import java.util.Arrays;

public class Ab1Impl implements Ab1 {

	@Override
	public int binarySearch(int[] data, int element) {

		//Arrays.binarySearch(data, element);

		int low = 0;
		int high = data.length - 1;

		while (low <= high) {
			int mid = (low + high) >>> 1;
			int midVal = data[mid];

			if (midVal < element)
				low = mid + 1;
			else if (midVal > element)
				high = mid - 1;
			else
				return mid; // key found
		}
		return -1;  // key not found.


	}



	@Override
	public void shellSort(Integer[] data) {

	}

	@Override
	public void quickSortStable(Integer[] data) {

	}

	@Override
	public int[][] mult(int[][] m1, int[][] m2) {
		return new int[0][];
	}
}
