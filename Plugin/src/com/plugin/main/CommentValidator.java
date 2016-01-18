package com.plugin.main;

import java.util.List;
import java.util.Map;

import com.plugin.utils.Filereader;

public class CommentValidator {
	private final static String KEY_START = "Start";
	private final static String KEY_END = "End";
	private final static String KEY_COUNT = "Count";

	public static void main(String[] args) {
		Filereader filereader = new Filereader("C:\\Users\\I323305\\Desktop\\MethodDetails.java");
		List<Map<String, Integer>> list = filereader.validateComments();
		for (int i = 0; i < list.size(); i++) {
			Map<String, Integer> map = list.get(i);
			if (map.get(KEY_COUNT) > 5) {
				System.out.println("You have not entered sufficient comments between Line Start : " + map.get(KEY_START)
						+ " and Line End : " + map.get(KEY_END));
			}
		}
	}

}
