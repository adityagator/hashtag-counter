package hashtag;

import java.util.ArrayList;
import java.util.List;

public class FibonacciHeap {

	private Node maxNode;
	private int heapSize = 0;

	public Node getMaxNode() {
		return maxNode;
	}

	public void setMaxNode(Node maxNode) {
		this.maxNode = maxNode;
	}

	public void insert(Node newNode, int count) {
//		Node newNode = new Node(node, count);
		if (getMaxNode() != null) {
			newNode.setRight(maxNode.getRight());
			newNode.setLeft(maxNode);
			maxNode.setRight(newNode);
			newNode.getRight().setLeft(newNode);

			if (newNode.getCount() > maxNode.getCount())
				setMaxNode(newNode);
		} else {
			setMaxNode(newNode);
		}
		heapSize = heapSize + 1;
	}

	private static final double logValue = 1.0 / Math.log((1.0 + Math.sqrt(5.0)) / 2.0);

	public void meldHeap() {
		int size = ((int) Math.floor(Math.log(heapSize) * logValue)) + 1;
		List<Node> arr = new ArrayList<Node>(heapSize);
		int roots = 0;
		Node max = maxNode;

		for (int i = 0; i < heapSize; i++) {
			arr.add(null);
		}

		if (max != null) {
			roots = roots + 1;
			max = max.getRight();
			while (max != maxNode) {
				roots = roots + 1;
				max = max.getRight();
			}
		}

		while (roots > 0) {
			int deg = max.getDegree();
			Node right = max.getRight();

			while (true) {
				Node n = arr.get(deg);
				if (n == null) {
					break;
				}

				if (max.getCount() < n.getCount()) {
					Node t = n;
					n = max;
					max = t;
				}
				connect(n, max);
				arr.set(deg, null);
				deg = deg + 1;
			}
			arr.set(deg, max);
			max = right;
			roots = roots - 1;
		}

		maxNode = null;
		for (int i = 0; i < size; i++) {
			Node n = arr.get(i);
			if (n == null)
				continue;
			if (maxNode == null) 
				maxNode = n;
			else {
				n.getLeft().setRight(n.getRight());
				n.getRight().setLeft(n.getLeft());
				n.setLeft(maxNode);
				n.setRight(maxNode.getRight());
				maxNode.setRight(n);
				n.getRight().setLeft(n);

				if (n.getCount() > maxNode.getCount())
					maxNode = n;

			}
		}
	}

	public Node extractMax() {
//		Node nextMax = maxNode;
		if (maxNode != null) {
			int children = maxNode.getDegree();
			while (children > 0) {
				Node temp = maxNode.getChild().getRight();
				maxNode.getChild().getLeft().setRight(maxNode.getChild().getRight());
				maxNode.getChild().getRight().setLeft(maxNode.getChild().getLeft());

				maxNode.getChild().setLeft(maxNode);
				maxNode.getChild().setRight(maxNode.getRight());
				maxNode.setRight(maxNode.getChild());
				maxNode.getChild().getRight().setLeft(maxNode);

				maxNode.getChild().setParent(null);
				maxNode.setChild(temp);
				children--;
			}
			maxNode.getLeft().setRight(maxNode.getRight());
			maxNode.getRight().setLeft(maxNode.getLeft());

			if (maxNode != maxNode.getRight()) {
				maxNode = maxNode.getRight();
				meldHeap();
			} else {
				maxNode = null;
			}
			heapSize = heapSize - 1;
		}
		return maxNode;
	}

	public void increaseKey(Node node, int increase) {
		node.setCount(node.getCount() + increase);

		if ((node.getParent() != null) && (node.getCount() > node.getParent().getCount())) {
			cut(node, node.getParent());
			cascadeCut(node.getParent());
		}

		if (node.getCount() > maxNode.getCount())
			maxNode = node;
	}

	public void cut(Node node, Node parent) {
		// remove x from childlist of y and decrement degree[y]
		parent.setDegree(parent.getDegree() - 1);
		node.getLeft().setRight(node.getRight());
		node.getRight().setLeft(node.getLeft());

		// reset y.child if necessary
		if (parent.getChild() == node) {
			parent.setChild(node.getRight());
		}

		if (parent.getDegree() == 0) {
			parent.setChild(null);
		}

		// add x to root list of heap
		node.setLeft(maxNode);
		node.setRight(maxNode.getRight());
		maxNode.setRight(node);
		node.getRight().setLeft(node);

		// set parent[x] to nil
		node.setParent(null);

		// set mark[x] to false
		node.setChildCut(false);
	}

	public void cascadeCut(Node node) {
		Node parent = node.getParent();

		// if there's a parent...
		if (parent != null) {
			// if y is unmarked, set it marked
			if (!node.getChildCut()) {
				node.setChildCut(true);
			} else {
				// it's marked, cut it from parent
				cut(node, parent);

				// cut its parent as well
				cascadeCut(parent);
			}
		}
	}

	public void connect(Node node1, Node node2) {
		// remove y from root list of heap
		node1.getLeft().setRight(node1.getRight());
		node1.getRight().setLeft(node1.getLeft());

		// make y a child of x
		node1.setParent(node2);

		if (node2.getChild() != null) {
			node1.setLeft(node2.getChild());
			node1.setRight(node2.getChild().getRight());
			node2.getChild().setRight(node1);
			node1.getRight().setLeft(node1);
		} else {
			node2.setChild(node1);
			node1.setRight(node1);
			node1.setLeft(node1);
		}

		// increase degree[x]
		node2.setDegree(node2.getDegree() + 1);

		// set child cut to false
		node1.setChildCut(false);
	}

}
