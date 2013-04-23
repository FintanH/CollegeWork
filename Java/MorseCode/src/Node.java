
public class Node {
		private char item;
		private Node dash;
		private Node dot;
		
		public Node(char item, Node dash, Node dot)
		{
			this.item = item;
			this.dash = dash;
			this.dot = dot;
		}
		
		public Node(){
			item = 0;
			dash = null;
			dot = null;
		}
		
		public char getItem()
		{
			return item;
		}
		
		public Node getDash()
		{
			return dash;
		}
		
		public Node getDot()
		{
			return dot;
		}
		
		public char setItem(char i)
		{
			return item = i;
		}
		
		public Node setDash(Node n)
		{
			dash = n;
			return dash;
		}
		
		public Node setDot(Node n)
		{
			dot = n;
			return dot;
		}
}
