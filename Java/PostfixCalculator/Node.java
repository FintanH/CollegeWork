
public class Node {
	private char item;
	private Node next;
	
	public Node(char item, Node next)
	{
		this.item = item;
		this.next = next;
	}
	
	public Node(){
		item = 0;
		next = null;
	}
	
	public char getItem()
	{
		return item;
	}
	
	public Node getNext()
	{
		return next;
	}
	
	public char setItem(char i)
	{
		return item = i;
	}
	
	public Node setNext(Node n)
	{
		next = n;
		return next;
	}

}
