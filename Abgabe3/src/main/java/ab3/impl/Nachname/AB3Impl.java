package ab3.impl.Nachname;

import ab3.AB3;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AB3Impl implements AB3 {

	@Override
	public SearchInfoKMP findPatternKMP(String text, String pattern) {
		int[] preProcessing = new int[pattern.length()+1];
		KMPPreProcessing(preProcessing, pattern);

		SearchInfoKMP returnValue = new SearchInfoKMP(new ArrayList<>(), new ArrayList<>());

		int i =0,j =0;
		while(i < text.length()){
			while((j>=0) && (text.charAt(i) != pattern.charAt(j))){
				returnValue.getJumps().add(j-preProcessing[j]);
				j = preProcessing[j];
			}
			i++;
			j++;
			if(j==pattern.length()){
				returnValue.getPositions().add(i-pattern.length());
				returnValue.getJumps().add(j-preProcessing[j]);
				j = preProcessing[j];
			}
		}

		return returnValue;
	}

	private void KMPPreProcessing(int[] preProcessing, String pattern){
		int i = 0;
		int j = -1;
		preProcessing[i] = j;

		while(i<pattern.length()){
			while((j>=0) && (pattern.charAt(i) != pattern.charAt(j))){
				j = preProcessing[j];
			}
			i++;
			j++;
			preProcessing[i] = j;
		}
	}
	@Override
	public List<Integer> findPatternEndlAutomat(String text, String pattern) {
		int[][] automatTable = new int[pattern.length()+1][256];
		List<Integer> positions = new ArrayList<>();
		buildAutomat(pattern, automatTable);
		int i, state = 0;

		for(i=0; i<text.length(); i++){
			state = automatTable[state][text.charAt(i)];
			if(state == pattern.length()){
				positions.add(i-pattern.length()+1);
			}
		}
		return positions;
	}

	private void buildAutomat(String pattern, int[][] automatTable){

		for(int state=0; state <= pattern.length(); ++state){
			for(int i=0; i<256; ++i){
				automatTable[state][i] = getState(pattern, state, i);
			}
		}
	}

	private int getState(String pattern, int state, int i){
		if(state < pattern.length() && i == pattern.charAt(state)){
			return state +1;
		}

		for(int next = state; next > 0; next--){
			if(pattern.charAt(next-1) == i){
				for(int j=0; j<next-1; j++){
					if(pattern.charAt(j) != pattern.charAt(state-next+1+j)){
						break;
					}
					if(j == next-1){
						return next;
					}
				}
			}
		}
		return 0;
	}


	@Override
	public Set<SymbolCode> huffmanCoding(List<Character> symbols, List<Integer> frequency) {
		return null;
	}
}
