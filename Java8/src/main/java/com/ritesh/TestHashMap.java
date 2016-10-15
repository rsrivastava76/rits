package com.ritesh;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class TestHashMap {

	public static void main(String[] args) {

		/**
		 * Using HashMap
		 */
		System.out.println("Using plain hash map with balanced trees:");

		HashMap<String, String> stringMap = new HashMap<String, String>();

		for (int i = 0; i < 100; ++i) {
			stringMap.put("index_" + i, String.valueOf(i));
		}

		stringMap.values().forEach(System.out::println);

		/**
		 * Using LinkedHashMap
		 */
		System.out.println("Using LinkedHashMap:");

		LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<String,String>();

		for (int i = 0; i < 100; ++i) {
			linkedHashMap.put("index_" + i, String.valueOf(i));
		}

		linkedHashMap.values().forEach(System.out::println);
	}
}
