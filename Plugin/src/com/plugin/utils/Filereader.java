package com.plugin.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Filereader {

	static String result;
	private File file;
	private int loc;
	private int blankLines;
	private int lineOfComments;
	private StringBuilder filecontents;
	private String currline;
	private static Filereader fileobj = null;
	private String filepath;
	private Map<String, Integer> map = new HashMap<String, Integer>();
	BufferedReader br = null;

	private final String KEY_FOR_LOC = "LOC";
	private final String KEY_FOR_BLANK_LINES = "BL";
	private final String KEY_FOR_COMMENTED_LINES = "CL";
	private final String KEY_START = "Start";
	private final String KEY_END = "End";
	private final String KEY_COUNT = "Count";

	public Filereader(String path) {// Constructor which takes File Path and
									// calls "ReadContents" to read its contents
		this.filepath = path;
	}

	public String readContents() {// read the contents of the file in a
									// StringBuilder and Return the contents in
									// the form of string
		file = new File(filepath);
		filecontents = new StringBuilder((int) file.length());

		String separator = System.getProperty("line.separator");
		try {
			br = new BufferedReader(new FileReader(filepath));

			while ((currline = br.readLine()) != null) {
				filecontents.append(currline + separator);

			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return filecontents.toString();
	}

	/*
	 * This method will return total no of lines of code, blank lines, commented
	 * lines
	 */
	public Map<String, Integer> countLines() {
		BufferedReader br = null;
		boolean flag = false;
		String line = null;
		try {
			br = new BufferedReader(new FileReader(filepath));
			while ((line = br.readLine()) != null) {

				// This will count commented lines
				if (line.contains("/*"))
					flag = true;

				if (flag)
					lineOfComments++;

				if (line.contains("*/"))
					flag = false;

				if (line.contains("//") && !flag)
					lineOfComments++;

				// This part will count the blank lines
				if (line.trim().isEmpty()) {
					blankLines++;
				}

				// This will count total no of lines
				loc++;
			}

			map.put(KEY_FOR_LOC, loc);
			map.put(KEY_FOR_BLANK_LINES, blankLines);
			map.put(KEY_FOR_COMMENTED_LINES, lineOfComments);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	public List<Map<String, Integer>> validateComments() {
		List<Map<String, Integer>> list = null;
		try {
			list = new ArrayList<Map<String, Integer>>();
			int i = 0;
			LineNumberReader lr = new LineNumberReader(new FileReader(filepath));
			String line = null;
			int count = 0;
			int start = 1;
			boolean value = false;
			while (true) {
				Map<String, Integer> map = new HashMap<String, Integer>();
				boolean flag = false;

				if (!value && (line = lr.readLine()) != null) {
					if (line.contains("class ")) {
						value = true;
						start = lr.getLineNumber();
					}
				}

				if (value) {
					while ((line = lr.readLine()) != null) {
						if (count != 0 && line.trim().isEmpty()) {
							count--;
						}
						if (line.contains("/*"))
							flag = true;
						
						if (line.contains("/*") || line.contains("//")) {
							break;
						}
						
						count++;
					}
					if (count != 0) {
						start++;
						map.put(KEY_START, start);
						map.put(KEY_END, lr.getLineNumber()-1);
						map.put(KEY_COUNT, count);
						list.add(map);
					}

					while (flag && (line = lr.readLine()) != null) {
						if (line.contains("*/"))
							break;
					}
					start = lr.getLineNumber();
					//System.out.println(start + 1000);
					count = 0;
				}
				if (line == null) {
					lr.close();
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// public static void main(String args[]) {
	// fileobj = new
	// Filereader("C:\\Users\\I320234\\Desktop\\mainactivity.txt");
	// }

}
