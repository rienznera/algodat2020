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

		PriorityQueue<HuffNode> pq = new PriorityQueue<>();
		//Build HuffTree using HuffNode Class
		for(int i = 0; i < symbols.size(); i++){
			HuffNode hn = new HuffNode(frequency.get(i), symbols.get(i).charValue() );
			pq.add(hn);
		}

		HuffNode root = null;


		//solange Heap > 1 --> extrahiere die 2 kleinsten und erzeuge neuen Knoten mit
		while (pq.size() > 1){
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

