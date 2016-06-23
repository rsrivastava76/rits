package com.ritesh;

import java.util.ArrayList;
import java.util.List;

public class T {
	public static void main(String[] args) {
		String[] x = {"apple", "monkeywrench", "apple sauce", "apple", "apple pie", "monkey"};
		List<String> kkk = new ArrayList<String>();
		for (String w : x) {
			kkk.add(w);
		}
		System.out.println("ORIGINAL SIZE: " + kkk);
		List<String> mmm = removeSimilarWords(kkk);
		System.out.println("NEW SIZE: " + mmm);

	}

	// =====================================================
	static List<String> removeSimilarWords(List<String> startList) {
		List<String> endList = new ArrayList<>();
		for (String a : startList) {
			boolean foundBigger = false;
			for (String b : startList) {
				if (b.equals(a) || b.indexOf(a) == -1) {
					continue;
				}
				foundBigger = true;
				System.out.println("##########################################: " + a + ", " + b);
				break;
			}
			if (!foundBigger) {
				endList.add(a);
			}
		}
		return endList;
	}
	// =====================================================
}

