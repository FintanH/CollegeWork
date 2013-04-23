
public class SortMain {
	public static void main(String args[])
	{
		Sorts SortApp = new Sorts();
		
		int numbers1[] = {100,97,59,58,55};
		SortApp.selectionSort(numbers1, numbers1.length);
		SortApp.selectionSort(numbers1, numbers1.length);
		for(int i=0; i<numbers1.length; i++){
			System.out.println(Integer.toString(numbers1[i]));
		}
		System.out.println("\n");
		
		int numbers2[] = {100,97,59,58,55};
		SortApp.insertionSort(numbers2, numbers2.length);
		SortApp.insertionSort(numbers2, numbers2.length);
		for(int i=0; i<numbers2.length; i++){
			System.out.println(Integer.toString(numbers2[i]));
		}
		System.out.println("\n");
		
		int numbers3[] = {100,97,59,58,55};
		SortApp.bubbleSort(numbers3, numbers3.length);
		SortApp.bubbleSort(numbers3, numbers3.length);
		for(int i=0; i<numbers3.length; i++){
			System.out.println(Integer.toString(numbers3[i]));
		}
		System.out.println("\n");
		
		int numbers4[] = {100,97,59,58,55};
		SortApp.exchangeSort(numbers4, numbers4.length);
		SortApp.exchangeSort(numbers4, numbers4.length);
		for(int i=0; i<numbers4.length; i++){
			System.out.println(Integer.toString(numbers4[i]));
		}
	}
	
	public static int[] reverseOrder(int a[], int size)
	{
		for(int i = 0; i<((size/2)-1); i++)
		{
			int swap = a[i];
			a[i] = a[size-(i+1)];
			a[size-(i+1)] = swap;
		}
		
		return a;
	}
}

