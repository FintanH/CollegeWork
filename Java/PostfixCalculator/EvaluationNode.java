
public class EvaluationNode {
	private char item;
	private float var;
	private EvaluationNode next;
	
	public EvaluationNode(float operand, char item, EvaluationNode next)
	{
		this.item = item;
		this.var = operand;
		this.next = next;
	}
	
	public EvaluationNode(){
		var = 0;
		item = 0;
		next = null;
	}
	
	public float getVar()
	{
		return var;
	}
	
	public char getItem()
	{
		return item;
	}
	
	public EvaluationNode getNext()
	{
		return next;
	}
	
	public float setItem(char c)
	{
		return item = c;
	}
	
	public float setVar(float i)
	{
		return var = i;
	}
	
	public EvaluationNode setNext(EvaluationNode n)
	{
		next = n;
		return next;
	}

}

