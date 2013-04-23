import java.util.Random;
public class SortMain {
	public static void main(String args[])
	{
		int n;
		int[] numbers;
		int[] heapsort;
		int[] quicksort;
		int[] mergesort;
		int[] hdata;
		int[] qdata;
		int[] mdata;
		
		Terminal terminal = new Terminal();
		Random random = new Random(System.currentTimeMillis());
		
		System.out.println("Enter N:");
		n = terminal.readInt();
		
		numbers = new int[n];
		heapsort = quicksort = mergesort = numbers;
		hdata = qdata = mdata = new int[2];
		
		for(int i = 0; i<n; i++)
		{
			int number = (Math.abs(random.nextInt()+1))%100000;
			numbers[i] = number;
		}
		
		System.out.println("Original:");
		printArray(numbers);
		System.out.println();
		
		Heapsort hsort = new Heapsort(numbers);
		heapsort = hsort.heapsort();
		hdata = hsort.data();
		
		Quicksort qsort = new Quicksort(numbers);
		quicksort = qsort.quicksort(0, n-1);
		qdata = qsort.data();
		
		Mergesort msort = new Mergesort(numbers);
		mergesort = msort.returnArray();
		mdata = msort.data();
		
		System.out.println("Heapsort:");
		printArray(heapsort);
		
		System.out.println("Quicksort:");
		printArray(quicksort);
		
		System.out.println("Mergesort:");
		printArray(mergesort);
		
		
		System.out.print("Heapsort Comparison,Heapsort Exchange");
		System.out.print(",Quicksort Comparison,Quicksort Exchange");
		System.out.println(",Mergesort Comparison,Mergesort Exchange");
		printData(hdata);
		printData(qdata);
		printData(mdata);
		System.out.println();
		
		
	}
	
	public static void printArray(int[] a)
	{
		for(int i=0; i<a.length; i++)
		{
			System.out.print(a[i] + "\n");
		}
	}
	
	public static void printData(int[] data)
	{
		for(int i=0; i<data.length; i++)
		{
			System.out.print(data[i] + ",");
		}
	}
}
