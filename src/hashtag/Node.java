package hashtag;

public class Node {
	private int degree;
	private Node left;
	private Node right;
	private Node parent;
	private Node child;
	private Boolean childCut;
	private String hashtag;
	private int count;
	private Boolean max;
	
	public Node(String hashtag, int count) {
		this.hashtag = hashtag;
//		this.degree = 0;
//		this.childCut = false;
		this.count = count;
		this.right = this;
		this.left = this;
	}
	
	public int getDegree() {
		return degree;
	}
	public void setDegree(int degree) {
		this.degree = degree;
	}
	public Node getLeft() {
		return left;
	}
	public void setLeft(Node left) {
		this.left = left;
	}
	public Node getRight() {
		return right;
	}
	public void setRight(Node right) {
		this.right = right;
	}
	public Node getParent() {
		return parent;
	}
	public void setParent(Node parent) {
		this.parent = parent;
	}
	public Node getChild() {
		return child;
	}
	public void setChild(Node child) {
		this.child = child;
	}
	public Boolean getChildCut() {
		return childCut;
	}
	public void setChildCut(Boolean childCut) {
		this.childCut = childCut;
	}
	public Boolean getMax() {
		return max;
	}
	public void setMax(Boolean max) {
		this.max = max;
	}

	public String getHashtag() {
		return hashtag;
	}

	public void setHashtag(String hashtag) {
		this.hashtag = hashtag;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
}
