package ab3.impl.AngermannHuberRienzner;

import ab3.AB3;

import java.util.*;

public class AB3Impl implements AB3 {

	@Override
	public SearchInfoKMP findPatternKMP(String text, String pattern) {
		return null;
	}

	@Override
	public List<Integer> findPatternEndlAutomat(String text, String pattern) {
		return null;
	}

	@Override
	public Set<SymbolCode> huffmanCoding(List<Character> symbols, List<Integer> frequency) {

		MinHeap pq = new MinHeap(symbols.size());

		//Build HuffTree using HuffNode Class
		for(int i = 0; i < symbols.size(); i++){
			HuffNode hn = new HuffNode(frequency.get(i), symbols.get(i).charValue() );
			pq.add(hn);

		}

		HuffNode root = null;


		//solange Heap > 1 --> extrahiere die 2 kleinsten und erzeuge neuen Knoten mit
		while (pq.size() > 1) {
			HuffNode first = pq.peek();
			pq.poll();
			HuffNode second = pq.peek();
			pq.poll();




			//neuer Node mit addierter Wertigkeit
			HuffNode n = new HuffNode((first.data + second.data), '-');
			n.left = first;
			n.right = second;
			root = n;
			pq.add(n);
		}


		//Build resultSet

		Set<AB3.SymbolCode> ret = new HashSet<SymbolCode>();
		return getCodes(root, "", ret);

	}

	private static Set<AB3.SymbolCode> getCodes(HuffNode root, String s, Set<SymbolCode> ret) {

		if (root.left == null && root.right	== null) {
			// c is the character in the node
			AB3.SymbolCode sc  = new AB3.SymbolCode(root.c, s.toString());
			ret.add(sc);
			//System.out.println(root.c + ":" + s);


		}
		if(root.left != null)
		getCodes(root.left, s + "0", ret);
		if(root.left != null)
		getCodes(root.right, s + "1",ret);

		return ret;
	}

}

class HuffNode implements Comparable<HuffNode> {
	int data;
	char c;


	public HuffNode(int _data, char _c) {
		this.data = _data;
		this.c = _c;
		this.left = null;
		this.right = null;
	}


	HuffNode left;
	HuffNode right;

	@Override
	public int compareTo(HuffNode o) {
		return this.data - o.data;
	}

}

//own implementation of MinHeap
class MinHeap {
	HuffNode[] nodes;
	int size;
	MinHeap(int size){
		//assure there's enough space for additional Nodes
		this.nodes = new HuffNode[size+1];
		this.size=0;
	}

	public HuffNode peek(){
		return nodes[1];
	}

	public void poll(){
		int parent, child;
		HuffNode temp;
		if (this.size!= 0) {

			temp = nodes[size--];

			parent = 1;
			child = 2;
			while (child <= size) {
				if (child < size && nodes[child].data > nodes[child + 1].data)
					child++;
				if (temp.data <= nodes[child].data)
					break;

				nodes[parent] = nodes[child];
				parent = child;
				child *= 2;
			}
			nodes[parent] = temp;

		}
	}

	public void add(HuffNode newNode)
	{

		nodes[++size] = newNode;
		int pos = size;
		while (pos != 1 && newNode.data < nodes[pos/2].data)
		{
			nodes[pos] = nodes[pos/2];
			pos /=2;
		}
		nodes[pos] = newNode;

	}

	public int size() {
		return size;
	}



}




