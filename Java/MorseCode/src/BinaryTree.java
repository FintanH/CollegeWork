
public class BinaryTree {
	private Node root;
	
	public BinaryTree()
	{
		root = new Node();
	}
	
	public Node getRoot()
	{
		return root;
	}
	
	public void createBranch(String morse, String character)
	{
		Node n = root;
		for(int i = 0; i<morse.length(); i++)
		{
			if(morse.charAt(i) == '.')
			{
				if(n.getDot() == null)
				{
					n.setDot(new Node());
					n = n.getDot();
				}else{
					n = n.getDot();
				}
			}else if(morse.charAt(i) == '-'){
				if(n.getDash() == null)
				{
					n.setDash(new Node());
					n = n.getDash();
				}else{
					n = n.getDash();
				}
			}
		}
		n.setItem(character.charAt(0));
	}
}
