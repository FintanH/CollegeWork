
public class MorseCodeMain {
	
	public static void main(String args[])
	{
		MorseCode new_code = new MorseCode();
		new_code.ReferenceTree();
		Terminal terminal = new Terminal();
		int UI = 0;
		String input = "";
		String output = "";
		
		do{
			terminal.println("Would you like to: \n1)Translate a sentence into morse \n2)Translate morse into english \n3)Quit");
			UI = terminal.readInt();
			
			switch(UI)
			{
			case 1:
				terminal.println("Enter sentence:");
				input = terminal.readString();
				input = input.toUpperCase();
				input = input.trim();
				if(new_code.isSentence(input))
				{
					output = new_code.translateSentence(input);
					terminal.println(output);
				}else{
					terminal.println("Error in input");
				}
				break;
				
			case 2:
				terminal.println("Enter morse:");
				input = terminal.readString();
				input = input.trim();
				if(new_code.isMorseCode(input))
				{
					output = new_code.translateMorse(input);
					terminal.println(output);
				}else{
					terminal.println("Error in input");
				}
				break;
				
			case 3:
				terminal.println("Exiting program.");
				break;
				
			default:
				terminal.println("Incorrect user choice");
				break;
			}
		}while(UI != 3);
	}

}
