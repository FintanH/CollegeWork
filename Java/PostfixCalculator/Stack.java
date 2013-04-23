
public class Stack {
	private Node head;
	
	public Stack(){
		head = null;
	}
	
	public boolean isEmpty()
	{
		return head == null;
	}
	
	public void push(char operator)
	{
		head = new Node(operator, head);
	}
	
	public char pop()
	{
		if(head == null)
		{
			return 0;
		}else{
			char i = head.getItem();
			head = head.setNext(head.getNext());
			return i;
		}
	}
	
	public char peek()
	{
		char i;
		if(head == null)
		{
			return 0;
		}else{
			return i = head.getItem();
		}
	}

}
