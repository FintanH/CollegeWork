import java.util.*;
public class InfixPostfixPrefixMain {

	public static void main(String args[])
	{
		Scanner scan = new Scanner(System.in);
		String postfix = "";
		String temp = "";
		String YorN = " ";
		boolean isinfix;
		char var;
		float val;
		char [] setvals;
		boolean isAssigned = false;
		
		while(YorN.charAt(0) != 'Y' && YorN.charAt(0) != 'N')
		{
			System.out.println("Do you wish to make an equation? Y/N");
			YorN = "";
			YorN = scan.next();
		}
		
		while(YorN.charAt(0) == 'Y')
		{
			Conversion equation = new Conversion();
			isinfix = false;
			System.out.println("Equations will be accepted with variables in the form; a to z; A to Z.");
			System.out.println("Acceptable functions are; \n+ for addition \n- for subtraction \n* for multiplication \n/ for division \n^ for exponent \n! for factorial");
			System.out.println("Number values are not accepted but once an equation is made you can assign values to variables");
			
			while(isinfix == false)
			{
				System.out.println("Enter Equation:");
				temp = scan.next();
				
				isinfix = equation.isInfix(temp);
	
				if(isinfix == false)
				{
					System.out.println("Not a correct infix equation; please enter one correctly");
				}
			}
			
			for(int i = 0; i<temp.length(); i++)
			{
				var = temp.charAt(i);//passes in infix expression to make list
				equation.setInfix(var);
			}
			
			equation.convertEquation();//converts equation
			
			System.out.println("Postfix is:");
			System.out.println(postfix = equation.postfixToString());//prints postfix expression
			
			equation.evaluationList();
			
			setvals = new char[temp.length()];//array to check if variable has been assigned a value
			
			for(int i = 0; i<temp.length(); i++)
			{
				var = temp.charAt(i);
				if((var >= 'A' && var <= 'Z') || (var >= 'a' && var <= 'z'))
				{
					for(int j = 0; j<setvals.length; j++)
					{
						if(setvals[j] == var)
						{
							isAssigned = true;
						}
					}
					
					if(isAssigned == false)//only allow user to set values for variables that haven't been assigned
					{
						setvals[i] = var;
						System.out.println("Enter Integer Value for variable: " + var);
						val = scan.nextInt();
						equation.assignVariables(var, val);
					}
					isAssigned = false;
				}
			}
			
			val = equation.Evaluate();//equate expression
			System.out.println("Equation Evaluates to: " + val);
			
			System.out.println("Do you wish to make an equation? Y/N");
			YorN = scan.next();
		}
		System.out.println("Exited Program");
	}
}
