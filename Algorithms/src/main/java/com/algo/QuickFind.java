package com.algo;

public class QuickFind {

	private int[] id;
	
	public QuickFind(int i){
		
		id = new int [i];
		for (int j = 0; j < i; j++){
			id[j] = j;
		}
	}
	
	public boolean connected (int p, int q) {
		return id[p] == id[q];
	}
	
	public void union (int p, int q){
		int pid = id[p];
		int qid = id[q];
		
		for (int i = 0; i < id.length; i++) {
			if(id[i] == pid) {
				id[i] = qid;
			}
		}
	}
	
	public static void main(String[] args) {
		System.out.println("hi");
	}

}
