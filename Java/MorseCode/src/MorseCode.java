
public class MorseCode {
	private BinaryTree morse;
	private String[][] table;
	
	public MorseCode()
	{
		morse = new BinaryTree();
		FileInput file = new FileInput();
		table = file.createTable();
	}
	
	public void ReferenceTree()
	{
		for(int i=0; i<table.length; i++)
		{
			morse.createBranch(table[i][1], table[i][0]);
		}
	}
	
	public String translateMorse(String morse)
	{
		String translation = "";
		int i = 0;
		Node n = this.morse.getRoot(); 
		
		while(i<morse.length())
		{
			if(morse.charAt(i) == ' ' && morse.charAt(i+1) == ' '){
				translation += n.getItem();
				translation += " ";
				n = this.morse.getRoot();
			}
			else if(morse.charAt(i) == '.')
			{
				if(n.getDot() != null)
				{
					n = n.getDot();
				}else{
					return "Error in entry";
				}
			}else if(morse.charAt(i) == '-'){
				if(n.getDash() != null)
				{
					n = n.getDash();
				}else{
					return "Error in entry";
				}
			}else if(morse.charAt(i) == ' '){
				if(n != this.morse.getRoot())
				{
					translation += n.getItem();
					n = this.morse.getRoot();
				}
			}
			i++;
		}
		translation += n.getItem();

		return translation;
	}
	
	public String translateSentence(String sentence)
	{
		String translation = "";
		
		for(int i=0; i<sentence.length(); i++)
		{
			if(sentence.charAt(i) >= '&' && sentence.charAt(i) <= 'Z')
			{
				translation += table[sentence.charAt(i) - '&'][1];
				translation += " ";
			}
			
			if(sentence.charAt(i) == ' ')
			{
				translation += " ";
			}
		}
		return translation;
	}
	
	public boolean isMorseCode(String morse)
	{
		boolean is_morse = true;
		
		for(int i=0; i<morse.length(); i++)
		{
			if((morse.charAt(i) != '.' && morse.charAt(i) != '-' && morse.charAt(i) != ' ') || (morse.charAt(i) == ' ' && (morse.charAt(i+1) != ' ' && morse.charAt(i+1) != '.' && morse.charAt(i+1) != '-')))
			{					
				is_morse = false;
			}
		}
		return is_morse;
	}
	
	public boolean isSentence(String sentence)
	{
		boolean is_sentence = true;
		
		for(int i=0; i<sentence.length(); i++)
		{
			if(!(sentence.charAt(i) >= 'a' && sentence.charAt(i) <= 'z'))
			{
				if(sentence.charAt(i) != ' ')
				{
					if(sentence.charAt(i) - '&' > 0 && sentence.charAt(i) - '&' < table.length)
					{
						if(sentence.charAt(i) != table[sentence.charAt(i) - '&'][0].charAt(0))
						{
							is_sentence = false;
						}
					}else{
						is_sentence = false;
					}
				}
			}
		}
		return is_sentence;
	}
	
}
