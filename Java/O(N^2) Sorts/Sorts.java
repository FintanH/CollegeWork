/*all sorting methods described in report document*/

public class Sorts {
	
	/*Selection sort method*/
	public int[] selectionSort(int a[], int size)
	{
		int comparison = 0;
		int exchange = 0;
		int min = a[0];
		int minposition=0;
		int j = 0;
		while(j<size-1)
		{		
		for(int i=j; i<size-1; i++) //for loop finding the minimum element in the array
		  {
			if(min > a[i+1])
			{
				min = a[i+1];
				minposition = i+1;
			}
			comparison++;
		  }
		  if(a[j] != min){		//if statement checking if the minimum number is already in the correct position
		  a[minposition] = a[j];
		  a[j] = min;
		  exchange++;
		  }
		  j++;
		  min = a[j];
		}
		System.out.println("Selection Sort Comparisons:" + Integer.toString(comparison));
		System.out.println("Selection Sort Exchanges:" + Integer.toString(exchange));
		return a;
	}
	
	/*Insertion sort method*/
	public int[] insertionSort(int a[], int size)
	{
		int comparison = 0;
		int exchange = 0;
		int swap;
		for(int i=0; i<size-1; i++) //for loop comparing elements
		{
			int temp = i;
			comparison++;
			while(a[i]>a[i+1] && i>=0) //while loop to compare if swap made then check previous element as well
			{
				if(a[i]>a[i+1])
				{
					swap = a[i];
					a[i] = a[i+1];
					a[i+1] = swap;
					exchange++;
				}
			    if(i>0)
			    {
			    	i--;
			    }
			    comparison++;
			}
			i = temp;
		}
		System.out.println("Insertion Sort Comparisons:" + Integer.toString(comparison));
		System.out.println("Insertion Sort Exchanges:" + Integer.toString(exchange));
		return a;
	}
	
	/*Bubble sort method*/
	public int[] bubbleSort(int a[], int size){
		int comparison = 0;
		int exchange = 0;
		int swap;
		int swapcounter = 0;
		int complete = 0;
		
		do
		{
			for(int i=0; i<size-1; i++) // for loop comparing adjacent elements
			{
				comparison++;
				if(a[i]>a[i+1])
				{
					swap = a[i+1];
					a[i+1] = a[i];
					a[i] = swap;
					swapcounter++;
					exchange++;
				}
			}
				if(swapcounter == 0) // if statement to check if no swaps were made, if so set flag
				{
					complete = 1;
				}
				swapcounter = 0;
		}while(complete == 0);
		
		System.out.println("Bubble Sort Comparisons:" + Integer.toString(comparison));
		System.out.println("Bubble Sort Exchanges:" + Integer.toString(exchange));
		
		return a;
	}
	
	/*exchange sort method*/
	public int[] exchangeSort(int a[], int size){
		int comparison = 0;
		int exchange = 0;
		int swap;
		int j = 0;
		while(j<size-1)
		{
			for(int i=j; i<size-1; i++) // for loop to compare the necessary elements
			{
				comparison++;
				if(a[j]>a[i+1])
				{
					swap = a[j];
					a[j] = a[i+1];
					a[i+1] = swap;
					exchange++;
				}
			}
			j++;
		}
		
		System.out.println("Exchange Sort Comparisons:" + Integer.toString(comparison));
		System.out.println("Exchange Sort Exchanges:" + Integer.toString(exchange));
		
		return a;
	}
}
	

