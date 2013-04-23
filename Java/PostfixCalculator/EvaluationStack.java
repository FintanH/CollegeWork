
public class EvaluationStack {
private EvaluationNode head;
	
	public EvaluationStack(){
		head = null;
	}
	
	public boolean isEmpty()
	{
		return head == null;
	}
	
	public void push(float operand)
	{
		head = new EvaluationNode(operand, '0', head);
	}
	
	public float pop()
	{
		if(head == null)
		{
			return 0;
		}else{
			float i = head.getVar();
			head = head.setNext(head.getNext());
			return i;
		}
	}
	
	public float peek()
	{
		float i;
		if(head == null)
		{
			return 0;
		}else{
			return i = head.getVar();
		}
	}

}
