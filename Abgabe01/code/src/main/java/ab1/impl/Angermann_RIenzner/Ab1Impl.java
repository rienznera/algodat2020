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
		int[][] ret = null;
		int[] help = new int[4];
		int n=0;
		help[0] = m1.length;
		help[1] = m1[0].length;
		help[2] = m2.length;
		help[3] = m2[0].length;
		for(int i = 0; i < 4; i++) {
			if(n < help[i]) {
				n = help[i];
			}
		}

		//normal multiplication if n < 16
		if(n < 16) {
			ret = normalMult(m1, m2);
		} else {
			if(m1.length != m1[0].length || m2.length != m2[0].length) {
				m1 = makeMatrixSquare(m1, n);
				m2 = makeMatrixSquare(m2, n);
			}
			ret = strassenMult(m1, m2);
		}
		return ret;
	}

	private int[][] normalMult(int[][] m1, int[][] m2) {
		int dim1 = m1.length;    // x of m1
		int dim2 = m2.length;   // x of m2 = y of m1
		int dim3 = m2[0].length; // y of m2

		int[][] result = new int[dim1][dim3];

		for (int i = 0; i < dim1; i++) {
			for (int j = 0; j < dim3; j++) {
				int val = 0;
				for (int k = 0; k < dim2; k++) {
					val += m1[i][k] * m2[k][j];
				}
				result[i][j] = val;
			}
		}

		return result;
	}

	public int[][] makeMatrixSquare(int[][]m, int n) {
		int[][] ret = new int[n][n];
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				if(m.length <= i) {
					ret[i][j] = 0;
				} else if (m[0].length <= j) {
					ret[i][j] = 0;
				} else {
					ret[i][j] = m[i][j];
				}
			}
		}

		return ret;
	}

	public int[][] strassenMult(int [][] m1, int [][] m2)
	{
		int n = m1.length;

		int[][] result = new int[n][n];

		if((n%2 != 0 ) && (n !=1))
		{
			int[][] a1, b1, c1;
			int n1 = n+1;
			a1 = new int[n1][n1];
			b1 = new int[n1][n1];
			c1 = new int[n1][n1];

			for(int i=0; i<n; i++)
				for(int j=0; j<n; j++)
				{
					a1[i][j] = m1[i][j];
					b1[i][j] = m2[i][j];
				}
			c1 = strassenMult(a1, b1);
			for(int i=0; i<n; i++)
				for(int j=0; j<n; j++)
					result[i][j] =c1[i][j];
			return result;
		}

		if(n == 1)
		{
			result[0][0] = m1[0][0] * m2[0][0];
		}
		else
		{
			int[][] A11 = new int[n/2][n/2];
			int[][] A12 = new int[n/2][n/2];
			int[][] A21 = new int[n/2][n/2];
			int[][] A22 = new int[n/2][n/2];

			int[][] B11 = new int[n/2][n/2];
			int[][] B12 = new int[n/2][n/2];
			int[][] B21 = new int[n/2][n/2];
			int[][] B22 = new int[n/2][n/2];

			split(m1, A11, 0 , 0);
			split(m1, A12, 0 , n/2);
			split(m1, A21, n/2, 0);
			split(m1, A22, n/2, n/2);

			split(m2, B11, 0 , 0);
			split(m2, B12, 0 , n/2);
			split(m2, B21, n/2, 0);
			split(m2, B22, n/2, n/2);

			int[][] P1 = strassenMult(add(A11, A22), add(B11, B22));
			int[][] P2 = strassenMult(add(A21, A22), B11);
			int[][] P3 = strassenMult(A11, sub(B12, B22));
			int[][] P4 = strassenMult(A22, sub(B21, B11));
			int[][] P5 = strassenMult(add(A11, A12), B22);
			int[][] P6 = strassenMult(sub(A21, A11), add(B11, B12));
			int[][] P7 = strassenMult(sub(A12, A22), add(B21, B22));

			int[][] C11 = add(sub(add(P1, P4), P5), P7);
			int[][] C12 = add(P3, P5);
			int[][] C21 = add(P2, P4);
			int[][] C22 = add(sub(add(P1, P3), P2), P6);

			copy(C11, result, 0 , 0);
			copy(C12, result, 0 , n/2);
			copy(C21, result, n/2, 0);
			copy(C22, result, n/2, n/2);
		}
		return result;
	}



	public int[][] sub(int[][] A, int[][] B)
	{
		int n = A.length;

		int[][] result = new int[n][n];

		for(int i=0; i<n; i++)
			for(int j=0; j<n; j++)
				result[i][j] = A[i][j] - B[i][j];

		return result;
	}

	public int[][] add(int[][] A, int[][] B)
	{
		int n = A.length;

		int[][] result = new int[n][n];

		for(int i=0; i<n; i++)
			for(int j=0; j<n; j++)
				result[i][j] = A[i][j] + B[i][j];

		return result;
	}

	public void split(int[][] p1, int[][] c1, int iB, int jB)
	{
		for(int i1 = 0, i2=iB; i1<c1.length; i1++, i2++)
			for(int j1 = 0, j2=jB; j1<c1.length; j1++, j2++)
			{
				c1[i1][j1] = p1[i2][j2];
			}
	}

	public void copy(int[][] c1, int[][] p1, int iB, int jB)
	{
		for(int i1 = 0, i2=iB; i1<c1.length; i1++, i2++)
			for(int j1 = 0, j2=jB; j1<c1.length; j1++, j2++)
			{
				p1[i2][j2] = c1[i1][j1];
			}
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
