
public class SlidingApp {
	/**
	 * The main method initially shuffles the tiles and prints them out to the terminal
	 * The user has then the choice to:
	 * 1)Solve the puzzle
	 * or
	 * 2)Quit
	 * If the user wishes to solve the puzzle then the program continues to ask which tile would the user wish to slide
	 * A message comes up if an incorrect tile is chose i.e. not beside 0
	 * The user can continue to solve or quit
	 * Once the puzzle is solved then the user can choose whether to solve another puzzle or quit
	 * If they wish to solve another the tiles are shuffled once again
	 */
	public static void main(String[] args){
		int[][] Tiles = new int [4][4];
		boolean Solved = true;
		String Frame ="";
		int UserChoice = 0;
		int UI = 0;
		int Reset = 1;
		int Moves = 0;
		boolean Move = false;
		
		
		Terminal theTerminal = new Terminal();
		
		theTerminal.println("Welcome to the the 15-Puzzle game!\nThe game mimics the sliding tile picture game, but uses numbers instead of a picture\nThe correct solution to the game is:\n┌───┬───┬───┬───┐\n│ 1 │ 2 │ 3 │ 4 │\n├───┼───┼───┼───┤\n│ 5 │ 6 │ 7 │ 8 │\n├───┼───┼───┼───┤\n│ 9 │ 10│ 11│ 12│\n├───┼───┼───┼───┤\n│ 13│ 14│ 15│ 0 │\n└───┴───┴───┴───┘");

		theTerminal.println("\nYour puzzle is:");
			PuzzleFrame FrameObject = new PuzzleFrame(Tiles);
			FrameObject.ShuffleTiles(Tiles);
			Frame = FrameObject.toString(Tiles, Frame);
			theTerminal.println(Frame);
		
				do{
				theTerminal.println("Would you like to: \n1)Solve the puzzle \n2)Quit");
				UI = theTerminal.readInt(); //users menu choice
				do{
				switch(UI){
					case 1:
						theTerminal.println("Choose a tile to move:");
						UserChoice = theTerminal.readInt(); //users tile choice
						
						
						Move = FrameObject.isMoveAllowed(Tiles, UserChoice);  //
							if(Move == true){								 //
						Moves++;											//
						FrameObject.SlideTile(Tiles, UserChoice);          //Slides a tile for
						Frame = FrameObject.toString(Tiles, Frame);       //the users choice of tile
						theTerminal.println(Frame);                      //and checks the puzzle for its solved state
						Solved = FrameObject.isSolved(Tiles, Solved);   //
						
						if(Solved == true){																		//
							theTerminal.println("Puzzle is solved in " + Moves + " moves");										   //
							theTerminal.println("Would you like to solve another puzzle? \n1)Yes \n2)No");	  //
							Reset = theTerminal.readInt();													 //
							if(Reset == 1){																	//This section is if the
								FrameObject = new PuzzleFrame(Tiles);									   //puzzle is solved
								FrameObject.ShuffleTiles(Tiles);										  //If so the user makes a choice
								Frame = FrameObject.toString(Tiles, Frame);								 //If it is equal to 1
								theTerminal.println(Frame);												//the puzzle is reset
							}else{																	   //
								UI = 2;																  //
							}																		 //
						}																			//
							
							}else{
								theTerminal.println("That is an invalid move");
							}
						break;
						
					case 2:
						break;
						
					default: 
						theTerminal.println("Invalid user choice");
						break;
						
				}
				}while(Solved==false);
				}while(UI != 2);
				theTerminal.println("Exiting program");
				
	}
}

