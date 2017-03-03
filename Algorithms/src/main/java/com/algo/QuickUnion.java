package com.algo;

public class QuickUnion {

	private int[] id;
	
	public QuickUnion(int i){
		
		id = new int [i];
		for (int j = 0; j < i; j++){
			id[j] = j;
		}
	}
	
	private int root(int i){
		while(i != id[i]){
			i = id[i];
		}
		return i;
	}
	
	public boolean connected (int p, int q) {
		return root(p) == root(q);
	}
	
	public void union (int p, int q){
		int i = root(p);
		int j = root(q);
		
		id[i] = j;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
