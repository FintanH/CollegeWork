
public class Conversion {
	private Node infix; //pointer to linked list containing infix expression
	private Node postfix; //pointer to linked list containing postfix expression
	private EvaluationNode evalpost; //pointer to linked list containing postfix expression that also contains integer values
	private Node termMarker; //used to mark where brackets end
	
	/*
	 * Method checking if expression is a valid infix expression
	 * Takes in String
	 * Returns boolean isInfix
	 */
	public boolean isInfix(String infix_string)
	{
		int opencounter = 0;
		int closecounter = 0;
		boolean isInfix = true;
		
		if(((infix_string.charAt(0) < 'A' || infix_string.charAt(0) >'Z') && (infix_string.charAt(0) < 'a' || infix_string.charAt(0) >'z')) && (infix_string.charAt(0) != '('))
		{
			return isInfix = false; //if the first character is not a variable or '('
		}
		
		if((infix_string.charAt(infix_string.length()-1) < 'A' || infix_string.charAt(infix_string.length()-1) >'Z') && (infix_string.charAt(infix_string.length()-1) < 'a' || infix_string.charAt(infix_string.length()-1) >'z') && (infix_string.charAt(infix_string.length()-1) != '!') && (infix_string.charAt(infix_string.length()-1) != ')'))
		{
			return isInfix = false; //if the last character is not a variable or ')' or '!'
		}
		
		for(int i = 0; i<infix_string.length(); i++)
		{
			
			if(infix_string.charAt(i) == '(')
			{
				opencounter++; //counts open brackets
			}
			
			if(infix_string.charAt(i) == ')')
			{
				closecounter++; //counts closing brackets
			}
		}
		
		if(opencounter != closecounter)
		{
			return isInfix = false; //if number of opening brackets is not equal closing brackets
		}
			
		for(int i = 0; i<infix_string.length(); i++)
		{
			if((infix_string.charAt(i) < 'A' || infix_string.charAt(i) >'Z') && (infix_string.charAt(i) < 'a' || infix_string.charAt(i) >'z') && (infix_string.charAt(i) != '+') && (infix_string.charAt(i) != '-') && (infix_string.charAt(i) != '*') && (infix_string.charAt(i) != '/') && (infix_string.charAt(i) != '^') && (infix_string.charAt(i) != '!') && (infix_string.charAt(i) != '(') && (infix_string.charAt(i) != ')'))
			{
				return isInfix = false; //if character is alien to the program i.e. not A-Z, a-z, +, -, *, /, ^, !, (, )
			}
			
			if((infix_string.charAt(i) == '+') || (infix_string.charAt(i) == '-') || (infix_string.charAt(i) == '*') || (infix_string.charAt(i) == '/') || (infix_string.charAt(i) == '^'))
			{
				if((infix_string.charAt(i+1) < 'A' || infix_string.charAt(i+1) >'Z') && (infix_string.charAt(i+1) < 'a' || infix_string.charAt(i+1) >'z') && (infix_string.charAt(i+1) != '(') && (infix_string.charAt(i+1) == '!'))
				{
					return isInfix = false; //if the character beside an operator is not an operand or is a !
				}
			}
			
			if(i != infix_string.length()-1)
			{
				if((infix_string.charAt(i) >= 'A' && infix_string.charAt(i) <= 'Z') || (infix_string.charAt(i) >= 'a' && infix_string.charAt(i) <= 'z'))
				{
					if((infix_string.charAt(i+1) >= 'A' && infix_string.charAt(i+1) <= 'Z') || (infix_string.charAt(i+1) >= 'a' && infix_string.charAt(i+1) <= 'z'))
					{
						return isInfix = false; //if two operands are beside each other
					}
					
				}
			}
		}
		
		return isInfix; // otherwise return true
	}
	
	/*
	 * Method that makes and infix linked list
	 * Takes in char
	 * Returns nothing
	 */
	public void setInfix(char item){
		Node i = new Node();
		Node j = new Node();
		Node n = new Node(item, null);
		if(infix == null){
			infix = new Node(item, null); //If list is empty add node
		}else{
			i = infix;
			while(i != null)
			{
				j = i;
				i = i.getNext();
			}
			
			j.setNext(n); //otherwise add node to the end of the list
		}
	}
	
	/*
	 * Method that makes postfix linked list
	 * Takes a char and adds it to the list
	 * Works in same fashion as setInfix()
	 */
	public void setPostfix(char item){
		Node i = new Node();
		Node j = new Node();
		Node n = new Node(item, null);
		if(postfix == null){
			postfix = new Node(item, null);
		}else{
			i = postfix;
			while(i != null)
			{
				j = i;
				i = i.getNext();
			}
			
			j.setNext(n);
		}
	}
	
	/*
	 * Makes a list that contains variables and an empty spot for values
	 * Initialises the variables to be the same as the postix variables and the values to be 0
	 */
	public void evaluationList()
	{
		Node pTemp = postfix;
		EvaluationNode eTemp, temp = null;
		while(pTemp != null)
		{
			if(evalpost == null)
			{
				evalpost = new EvaluationNode(0, pTemp.getItem(),null); //if empty then node = (value=0, variable=postfix.Item, null)
			}else{
				eTemp = evalpost;
				while(eTemp != null)
				{
					temp = eTemp;
					eTemp = eTemp.getNext();
				}
				EvaluationNode n = new EvaluationNode(0, pTemp.getItem(), null); // add node to end of the list
				temp.setNext(n);
			}
			pTemp = pTemp.getNext();
		}
	}
	
	/*
	 * Adds the values to the evaluationlist
	 * Takes in char and float
	 * Finds corresponding char and gives it value 
	 */
	public void assignVariables(char a, float x)
	{
		boolean var_assigned = false;
		EvaluationNode temp = evalpost;
		while(temp != null && temp.getItem() != a && var_assigned == false)
		{
			temp = temp.getNext(); //find char in list
		}
		while(temp != null)
		{
			if(temp.getItem() == a)
			{
				temp.setVar(x); // set all chars that are the same to have same value
			}
			temp = temp.getNext();
		}
	}
	
	/*
	 * Method initialises new stack and new term node pointing to the infix list
	 * Calls on conversion method
	 * Once conversion is done it empties the stack
	 */
	public void convertEquation(){
		Stack stack = new Stack();
		Node term = new Node();
		term = infix;
	
			conversions(term, stack);
			
			while(stack.isEmpty()==false){
				setPostfix(stack.pop());
			}
	}
	
	/*
	 * Method checks precedence of operators
	 * Takes in char which equals an operator
	 * Returns int value for the level of precedence
	 */
	public int precedence(char operator)
	{
		int p = 0;
		if(operator == '+')
		{
			return p = 1;
		}else if(operator == '-'){
			return p = 1;
		}else if(operator == '*'){
			return p = 2;
		}else if(operator == '/'){
			return p = 2;
		}else if(operator == '^'){
			return p = 3;
		}else if(operator == '!'){
			return p = 4;
		}else if(operator == '('){
			return p = 5;
		}
		return p;
	}
	
	/*
	 * Method to deal with brackets
	 * Calls on conversions method
	 * New stack is initialised before entering method
	 * Empties new stack once done
	 */
	public void brackets(Node term, Stack stack){
		term = term.getNext();
		conversions(term, stack);
		while(stack.isEmpty() == false){
			setPostfix(stack.pop());
		}
	}
	
	/*
	 * Method to convert infix to postfix
	 * Takes in a stack and node
	 */
	public void conversions(Node term, Stack stack)
	{
		int precedence1;
		int precedence2;

		while(term.getItem() != ')')
		{

			if((term.getItem()>='A' && term.getItem() <= 'Z') || (term.getItem()>='a' && term.getItem() <= 'z'))
			{
				setPostfix(term.getItem());//if variable put straight to postfix node
				term = term.getNext();
			}
			
			else if(stack.isEmpty()==true && term.getItem() != '(')
			{
				stack.push(term.getItem());//if operator and stack is empty push operator
				term = term.getNext();
			}else{
				precedence1 = precedence(term.getItem());//otherwise assign precedence
				precedence2 = precedence(stack.peek());
				
				if(precedence2 >= precedence1)//now check precedence
				{
					if(term.getItem() != '^')//precedence of operator on stack is greater and not '^' since '^' is right associative
					{
					setPostfix(stack.pop());//pop stack 
					stack.push(term.getItem());//then push operator
					}else{
						stack.push(term.getItem());//otherwise just push
					}
				}else{
					if(term.getItem() == '(')
					{
						Stack temp = new Stack();//if operator is '(' make new stack
						brackets(term, temp);// pass into brackets method
						term = termMarker;// marks last term which was being pointed to which should be ')'
					}else{
						stack.push(term.getItem());
					}
				}
				term = term.getNext();//next item in list
			}
			
			if(term == null){
				term = new Node(')', null);//if end is reached node will be [)][null]
			}
			
			if(term.getItem()==')'){
				termMarker = term;//if end of brackets is found mark the last term known
			}
		}

	}
	
	/*
	 * Concatenates postfix node's chars to postfix string 
	 * Returns string in terms of postfix expression
	 */
	public String postfixToString()
	{
		String p = "";
		Node post = postfix;
		while(post != null){
			p += post.getItem();
			post = post.getNext();
		}
		return p;
	}
	
	/*
	 * Concatenates infix node's chars to infix string 
	 * Returns string in terms of infix expression
	 */
	public String infixToString()
	{
		String i = "";
		Node in = infix;
		while(in != null){
			i += in.getItem();
			in = in.getNext();
		}
		return i;
	}
	
	/*
	 * Evaluates the expression once given values
	 * Returns float value which is the answer of the expression
	 */
	public float Evaluate()
	{
		EvaluationStack stack = new EvaluationStack();
		EvaluationNode eval = evalpost;
		float op1;
		float op2;
		while(eval != null)
		{
			if((eval.getItem() >= 'A' && eval.getItem() <= 'Z') || (eval.getItem() >= 'a' && eval.getItem() <= 'z'))
			{
				stack.push(eval.getVar());
			}else if(eval.getItem() == '+'){
				op1 = stack.pop();
				op2 = stack.pop();
				stack.push(op1+op2);
			}else if(eval.getItem() == '-'){
				op1 = stack.pop();
				op2 = stack.pop();
				stack.push(op2-op1);
			}else if(eval.getItem() == '*'){
				op1 = stack.pop();
				op2 = stack.pop();
				stack.push(op2*op1);
			}else if(eval.getItem() == '/'){
				op1 = stack.pop();
				op2 = stack.pop();
				if(op1 != 0){
					stack.push(op2/op1);
				}else{
					System.out.println("ERROR: Dividing by zero");//if dividing by zero send error message
					System.out.println("Returning value 0");
					return 0;
				}
			}else if(eval.getItem() == '^'){
				op1 = stack.pop();
				op2 = stack.pop();
				stack.push((float)Math.pow(op2,op1));
			}else if(eval.getItem() == '!'){
				op1 = stack.pop();
				stack.push(factorial(op1));//uses factorial method to calculate the n!
			}
			eval = eval.getNext();
		}
		return stack.pop();
	}
	
	/*
	 * Takes in float and calculates its factorial
	 * Returns n!
	 */
	public float factorial(float op)
	{
		float i = op - 1;// i = n-1
		if(op == 0){
			return op = 1;
		}else{
			while(i != 0){
				op = op * i;// n! = (n*n-1*n-2*...1)
				i--;
			}
		 return op;
		}
	}
	
}
