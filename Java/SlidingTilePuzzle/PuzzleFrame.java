import java.util.Random;
public class PuzzleFrame {
	private int row;
	private int column;
	private int nullrow;
	private int nullcolumn;
	private boolean Move;
	
/**The constructor takes in a 2-D array and sets the values in the array to 1-15 and a 0
 * i.e.┌───┬───┬───┬───┐
	   │ 1 │ 2 │ 3 │ 4 │
	   ├───┼───┼───┼───┤
	   │ 5 │ 6 │ 7 │ 8 │
	   ├───┼───┼───┼───┤
	   │ 9 │ 10│ 11│ 12│
	   ├───┼───┼───┼───┤
	   │ 13│ 14│ 15│ 0 │
	   └───┴───┴───┴───┘
 * This is done by iterating through rows and columns and adding the value of i in to the index
 * this continues until we reach the last index (r=3 & c=3) and it inserts 0 in to the array
 **/
	
	public PuzzleFrame(int frame[][]){
		int i = 1;
		for(int r=0; r<frame.length; r++){
			for(int c=0; c<frame[r].length; c++){
				frame[r][c]=i++;
				if(r==3 && c==3){
					frame[r][c]=0;
				}
			}
		}
	}
	
	/**The isSolved method passes in a 2-D array and a boolean value to begin
	 *An array is set up with the value 1-15 and 0 in its indexes. These values simulate the 2-D array values
	 *The nested for-loop goes through the values in the 2-D array checking them against the values in the singles array
	 *If any value is not equal isSolved equates to false otherwise once the puzzle is solved in the correct order isSolved equates to true
	 **/
	public boolean isSolved(int[][]frame, boolean isSolved){
		int[] i={1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,0};
		int j = 0;
		isSolved = true;

			for(int r=0; r<frame.length; r++){
			for(int c=0; c<frame[r].length; c++){
				if(frame[r][c]!=i[j]){
					isSolved = false;
				}
				j++;
			}
		}	
		return isSolved;
	}
	
	/**The isMoveAllowed method works by taking the choice tile and comparing it to the 2-D array values until it finds a match
	 * it then stores the value of the row and column of the 2-D array in to the private ints 'row' and 'column'
	 * these values are used in a nested switch statement checking for that row and column and seeing if the surrounding tiles are the 0 tile
	 * if so a private boolean 'Move' is set to false otherwise it is set to false
	 * the switch statement works by the cases corresponding to the row and column value
	 **/
	
	public boolean isMoveAllowed(int[][]frame, int choice){
		for(int r=0; r<frame.length; r++){
			for(int c=0; c<frame[r].length; c++){
				if(frame[r][c]==choice){
					row=r;
					column=c;
				}
			}
		}
		switch(row){
		case 0: 
			switch(column){
			case 0: 
				if(0==frame[row][column+1] || 0==frame[row+1][column]){
					Move = true;
				}else{
					Move = false;
				}
				break;
			case 1:
				if(0==frame[row][column+1] || 0==frame[row+1][column] || 0==frame[row][column-1]){
					Move = true;
				}else{
					Move = false;
				}
				break;
			case 2:
				if(0==frame[row][column+1] || 0==frame[row+1][column] || 0==frame[row][column-1]){
					Move = true;
				}else{
					Move = false;
				}
				break;
			case 3: 
				if(0==frame[row][column-1] || 0==frame[row+1][column]){
					Move = true;
				}else{
					Move = false;
				}
				break;
			}
			break;
		case 1:
			switch(column){
			case 0: 
				if(0==frame[row][column+1] || 0==frame[row+1][column] || 0==frame[row-1][column]){
					Move = true;
				}else{
					Move = false;
				}
				break;
			case 1:
				if(0==frame[row][column+1] || 0==frame[row+1][column] || 0==frame[row][column-1] || 0==frame[row-1][column]){
					Move = true;
				}else{
					Move = false;
				}
				break;
			case 2:
				if(0==frame[row][column+1] || 0==frame[row+1][column] || 0==frame[row][column-1] || 0==frame[row-1][column]){
					Move = true;
				}else{
					Move = false;
				}
				break;
			case 3: 
				if(0==frame[row][column-1] || 0==frame[row+1][column] || 0==frame[row-1][column]){
					Move = true;
				}else{
					Move = false;
				}
				break;
			}
			break;
		
		case 2:
			switch(column){
			case 0: 
				if(0==frame[row][column+1] || 0==frame[row+1][column] || 0==frame[row-1][column]){
					Move = true;
				}else{
					Move = false;
				}
				break;
			case 1:
				if(0==frame[row][column+1] || 0==frame[row+1][column] || 0==frame[row][column-1] || 0==frame[row-1][column]){
					Move = true;
				}else{
					Move = false;
				}
				break;
			case 2:
				if(0==frame[row][column+1] || 0==frame[row+1][column] || 0==frame[row][column-1] || 0==frame[row-1][column]){
					Move = true;
				}else{
					Move = false;
				}
				break;
			case 3: 
				if(0==frame[row][column-1] || 0==frame[row+1][column] || 0==frame[row-1][column]){
					Move = true;
				}else{
					Move = false;
				}
				break;
			}
			break;
		
		
		case 3:
			switch(column){
			case 0: 
				if(0==frame[row][column+1] || 0==frame[row-1][column]){
					Move = true;
				}else{
					Move = false;
				}
				break;
			case 1:
				if(0==frame[row][column+1] || 0==frame[row-1][column] || 0==frame[row][column-1]){
					Move = true;
				}else{
					Move = false;
				}
				break;
			case 2:
				if(0==frame[row][column+1] || 0==frame[row-1][column] || 0==frame[row][column-1]){
					Move = true;
				}else{
					Move = false;
				}
				break;
			case 3: 
				if(0==frame[row][column-1] || 0==frame[row-1][column]){
					Move = true;
				}else{
					Move = false;
				}
				break;
			}
			break;
		
		}
		return Move;
		
	}
	
	/**The SlideTile method first finds the 0 or 'null' part of the puzzle and stores this values index in two variable 'nullrow' & 'nullcolumn'
	 * The method is dependent on the isMoveAllowed method as it does not do its function of swapping tiles if Move=false
	 * This stops the user from sliding a tile that cannot actually be slid   
	 **/
	
	public int[][] SlideTile(int frame[][], int choice){
		for(int r=0; r<frame.length; r++){
			for(int c=0; c<frame[r].length; c++){
				if(frame[r][c]==0){
					nullrow=r;
					nullcolumn=c;
				}
			}
		}
		if(Move==true){
			frame[nullrow][nullcolumn]=choice;
			frame[row][column]=0;
		}
		return frame;
	}
	
	/**The ShuffleTiles method uses the Random class taken from the Random java library
	 * A while loop is set up to say while an int i<(a given number), then it creates a random integer between 1 and 15 and passes it in to the isMoveAllowed method
	 * then once Move is true for this random value it slides that tile in to the open space, thus shuffling the tiles
	 **/
	
	public int[][] ShuffleTiles(int frame[][]){
		int i = 0;
		int random = 0;
		Random randomObj = new Random();
		while (i<9999999){  //max integer: 2147483647 doesnt display or work for this
		random = randomObj.nextInt(15)+1;
		isMoveAllowed(frame, random);
		if(Move == true){
		SlideTile(frame, random);
		}
		i++;
		}
		return frame;
	}
	
	/**Taking the unicode values for the border I made an initial string that forms the top of the Frame
	 * the first row is then iterated through to print out the number and their borders
	 * the if statement compensates for the two digit numbers making the boxes neat and alligned
	 * it continues to concatenate the string with the middle sections and concatenates the values of the next row
	 * once each row has been passed in to the string the bottom is passed in to complete the grid
	**/
	public String toString(int frame[][], String Border){
		Border = "┌───┬───┬───┬───┐\n│ ";
		int row = 0;
		int column = 0;
			
		for(column=0; column<frame[row].length; column++){
			if(frame[row][column]<10){
				Border = Border + Integer.toString(frame[row][column]) + " │ " ;
			}else{
				Border = Border + Integer.toString(frame[row][column]) + "│ " ;
			}
			}
		
		Border = Border + "\n├───┼───┼───┼───┤\n│ ";
		
		row=1;
		
		for(column=0; column<frame[row].length; column++){
			if(frame[row][column]<10){
				Border = Border + Integer.toString(frame[row][column]) + " │ " ;
			}else{
				Border = Border + Integer.toString(frame[row][column]) + "│ " ;
			}
		}
		
		Border = Border + "\n├───┼───┼───┼───┤\n│ ";
		
		row=2;
		
		for(column=0; column<frame[row].length; column++){
			if(frame[row][column]<10){
				Border = Border + Integer.toString(frame[row][column]) + " │ " ;
			}else{
				Border = Border + Integer.toString(frame[row][column]) + "│ " ;
			}
		}
		
		Border = Border + "\n├───┼───┼───┼───┤\n│ ";
		
		row=3;
		
		for(column=0; column<frame[row].length; column++){
			if(frame[row][column]<10){
				Border = Border + Integer.toString(frame[row][column]) + " │ " ;
			}else{
				Border = Border + Integer.toString(frame[row][column]) + "│ " ;
			}
		}
		
		Border = Border + "\n└───┴───┴───┴───┘";
		
		return Border;
	}
		
}
	



