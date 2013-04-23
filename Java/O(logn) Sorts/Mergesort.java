
public class Mergesort {
  private int[] A;
  private int[] temp;
  private int comparison;
  private int exchange;

  private int length;

  public Mergesort(int[] array) {
	comparison = exchange = 0;
    A = array;
    length = array.length;
    temp = new int[length];
    mergesort(0, length-1);
  }

  private void mergesort(int first, int n) {
    // Check if low is smaller then high, if not then the array is sorted
	comparison++;
    if (first < n) 
    {
      // Get the index of the element which is in the middle
      int middle = first + (n - first) / 2;
      // Sort the left side of the array
      mergesort(first, middle);
      // Sort the right side of the array
      mergesort(middle + 1, n);
      // Combine them both
      merge(first, middle, n);
    }
  }

  private void merge(int first, int middle, int end) {

    // Copy both parts into the helper array
	comparison++;
    for (int i = first; i <= end; i++) {
      comparison++;
      temp[i] = A[i];
    }

    int i = first;
    int j = middle + 1;
    int k = first;
    // Copy the smallest values from either the left or the right side back
    // to the original array
    comparison++;
    while (i <= middle && j <= end) {
      comparison += 2;
      if (temp[i] <= temp[j]) {
        A[k] = temp[i];
        exchange++;
        i++;
      }else{
    	comparison++;
        A[k] = temp[j];
        exchange++;
        j++;
      }
      k++;
    }
    // Copy the rest of the left side of the array into the target array
    comparison++;
    while (i <= middle) {
      comparison++;
      A[k] = temp[i];
      exchange++;
      k++;
      i++;
    }

  }
  
  public int[] returnArray()
  {
	  return A;
  }
  
  public int[] data()
  {
		int data[] = new int[2];
		data[0] = comparison;
		data[1] = exchange;
		return data;
  }
} 
