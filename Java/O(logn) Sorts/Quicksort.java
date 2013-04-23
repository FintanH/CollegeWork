
public class Quicksort {
	private int[]sortsarray;
	private int comparison;
	private int exchange;
	
	Quicksort(int array[])
	{
		comparison = exchange = 0;
		sortsarray = array;
	}
	
	public int[] quicksort(int left, int right)
	{
		int i = left;
		int j = right;
		int[] A = sortsarray;
		int x = A[((left+right)/2)];
		int y;
		comparison++;
		while(i<j)
		{
			comparison++;
			
			comparison++;
			while(A[i] < x)
			{	
				comparison++;
				i++;
			}

			comparison++;
			while(A[j] > x)
			{
				comparison++;
				j--;
			}
			
			if(i<=j)
			{
				y = A[i];
				exchange++;
				A[i] = A[j];
				exchange++;
				A[j] = y;
				exchange++;
				
				i++;
				j--;
			}
		}
		
		if(left < j)
		{
			quicksort(left, j);
		}
		
		if(i < right)
		{
			quicksort(i, right);
		}
		
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
