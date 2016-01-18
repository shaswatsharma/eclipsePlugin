package com.plugin.utils;

import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.io.*;

public class FindMethodsFile {

	private int count;
	private LineNumberReader lr;
	private static FindMethodsFile methodfile;
	private String filename, line;
	private final String StartIndex = "start";
	private final String EndIndex = "end";
	private final String accessSpecifier = "specifier";
	private final String accessmodifier = "modifier";
	private final String returnType = "returnType";
	private final String methodName = "name";
	private final String parameter1 = "param1";
	private final String parameter2 = "param2";
	private final String parameter3 = "param3";
	private final String parameter4 = "param4";
	private final String parameter5 = "param5";
	private final String parameterType1 = "type1";
	private final String parameterType2 = "type2";
	private final String parameterType3 = "type3";
	private final String parameterType4 = "type4";
	private final String parameterType5 = "type5";
	private final String exceptionThrown = "exception";
	private final String methodBody = "methodbody";
	// private String body;

	private ArrayList<HashMap<String, String>> list;

	public FindMethodsFile(String path) {
		filename = path;
		readLines();
		printlinenumbers();
	}

	private void readLines() {
		try {
			String body = new String();
			list = new ArrayList<HashMap<String, String>>();
			HashMap<String, String> prevmap = new HashMap<String, String>();
			lr = new LineNumberReader(new FileReader(filename));
			boolean flag = true;
			boolean bodyflag = false;

			// traverse until the end of the file
			while ((line = lr.readLine()) != null) {
				
				// initialize count=0 wen u find " class " for the first
				// time..Indicates beginning of class
				if (line.contains(" class ") && !line.contains("//") && flag && !line.contains("\" class \"")) {
					System.out.println("in line no" + lr.getLineNumber() + "class begins");
					count = 0;
				}

				if (bodyflag) {
					if (body == null)
						body = line;
					else{
						body=body + "\n";
						body =  body + line;
					}
				}

				if (line.contains("/*")) {
					flag = false;
				}

				if (line.contains("*/")) {
					flag = true;
				}
				

				if (line.contains("{") && count != 0 && !line.contains("//") && flag) {
					count++;
				}
				if (line.contains("}") && count != 0 && !line.contains("//") && flag) {
					count--;
					// if count becomes "0" then that will mark the end of the
					// method, so add the current_line_number,and the body of
					// the method to the map
					if (count == 0) {
						prevmap.put(EndIndex, String.valueOf(lr.getLineNumber()));
						// next line will help to remove the final
						// closingbrace"}" from body section and includes
						body = body.substring(0, body.length() - 1);
						prevmap.put(methodBody, body);
						body = null;
						bodyflag = false;
						list.add(prevmap);
					}
				}
				if (line.contains("{") && (!line.contains(" class ")) && count == 0 && !line.contains("//") && flag) {// for
																														// first
																														// occurence
																														// of
																														// opening
																														// brace

					HashMap<String, String> map = new HashMap<String, String>();
					map.put(StartIndex, String.valueOf(lr.getLineNumber()));
					prevmap = map;
					count++;
					if (count == 1) {
						bodyflag = true;
					}
					String[] parts;
					parts = splitByThrows(line, map);
					StringTokenizer str;

					parseLeftpart(parts[0], map);
					parseRightPart(parts[1], map);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// method used to obtain all the details stored in the map.
	private void printlinenumbers() {
		int i = 0;
		HashMap<String, String> map;
		System.out.println(list.size());
		while (i < list.size()) {
			map = (HashMap<String, String>) list.get(i);
			System.out.println("starting index: " + Integer.valueOf(map.get(StartIndex)) + " ending index :"
					+ Integer.valueOf(map.get(EndIndex)) + " accessSpecifier=" + map.get(accessSpecifier)
					+ " accessmodifier=" + map.get(accessmodifier) + " returnType=" + map.get(returnType)
					+ " methodName=" + map.get(methodName) + "\n firstParameterType=" + map.get(parameterType1)
					+ " firstParameter=" + map.get(parameter1) + "\n secondParameterType=" + map.get(parameterType2)
					+ " secondParameter=" + map.get(parameter2) + "\n thirdParameterType=" + map.get(parameterType3)
					+ " thirdParameter=" + map.get(parameter3) + "\n fourthParameterType=" + map.get(parameterType4)
					+ " fourthParameter=" + map.get(parameter4) + "\n fifthParameterType=" + map.get(parameterType5)
					+ " fifthParameter=" + map.get(parameter5) + "\n ExceptionThrown=" + map.get(exceptionThrown)
					+ "\n methodBody=" + map.get(methodBody));
			i++;
		}
	}

	// checks if their is any "throws" keyword in the currrent line
	private String[] splitByThrows(String line, HashMap<String, String> map) {
		String[] parts;
		// if their is any "throws" keyword then split the current line using it
		// and use the left_String to obtain the method details
		// and right_String to obtain the "type of Exception" thrown by that
		// method

		if (line.contains("throws")) {
			String[] throwsSplit = line.split("throws");
			parts = throwsSplit[0].split("\\(");
			map.put(exceptionThrown, throwsSplit[1].replaceAll("[^a-zA-Z0-9]", ""));
		}
		// And if their is no "throws" keyword, then split the line using "(" to
		// seperate the paremeter_section and method_name section
		else {
			parts = line.split("\\(");
		}
		return parts;
	}

	//
	private void parseLeftpart(String leftSubString, HashMap<String, String> map) {
		StringTokenizer str;
		if (leftSubString != null) {
			str = new StringTokenizer(leftSubString);

			while (str.hasMoreTokens()) {
				boolean value = false;
				String temp = null;
				switch (temp = str.nextToken()) {
				case "public":
				case "private":
				case "protected":
					map.put(accessSpecifier, temp);
					value = true;
					break;

				case "static":
				case "final":
				case "synchronized":
					map.put(accessmodifier, temp);
					value = true;
					break;

				default:
					break;

				}
				if (value == true)
					continue;

				if (!map.containsKey(returnType)) {
					map.put(returnType, temp);
					continue;
				}

				if (!map.containsKey(methodName)) {
					map.put(methodName, temp);
					continue;
				}

			}
		}
	}

	private void parseRightPart(String rightSubString, HashMap<String, String> map) {
		StringTokenizer str;
		// parsing the second part of the string
		if (rightSubString != null) {
			// if this method has only one parameter, then retrive the
			// parameterType and parameterName and store it in map
			if (!rightSubString.contains(",")) {
				str = new StringTokenizer(rightSubString);
				String temp = null;
				while (str.hasMoreTokens()) {
					temp = str.nextToken();
					// retriving the one and only parameterType
					if (!map.containsKey(parameterType1)) {
						map.put(parameterType1, temp.replaceAll("[^a-zA-Z0-9]", ""));
						continue;
					}
					// retriving the one and only parameter
					if (!map.containsKey(parameter1)) {
						map.put(parameter1, temp.replaceAll("[^a-zA-Z0-9]", ""));
						continue;
					}
				}
			}
			// if their are more than one parameters
			else {
				// split it using "," and get Strings of the form "parameterType
				// parameterValue"
				String[] secondHalf = rightSubString.split("\\,");
				// System.out.println("\n");
				for (String param : secondHalf) {
					// Now split these strings using " " and obtain
					// parameterType and parameterValue separately
					str = new StringTokenizer(param);
					String temp = null;
					while (str.hasMoreTokens()) {
						temp = str.nextToken();
						// for parameter1
						if (!map.containsKey(parameterType1)) {
							map.put(parameterType1, temp.replaceAll("[^a-zA-Z0-9]", ""));
							continue;
						}

						if (!map.containsKey(parameter1)) {
							map.put(parameter1, temp.replaceAll("[^a-zA-Z0-9]", ""));
							continue;
						}
						// for parameter2
						if (!map.containsKey(parameterType2)) {
							map.put(parameterType2, temp.replaceAll("[^a-zA-Z0-9]", ""));
							continue;
						}

						if (!map.containsKey(parameter2)) {
							map.put(parameter2, temp.replaceAll("[^a-zA-Z0-9]", ""));
							continue;
						}
						// for parameter3
						if (!map.containsKey(parameterType3)) {
							map.put(parameterType3, temp.replaceAll("[^a-zA-Z0-9]", ""));
							continue;
						}

						if (!map.containsKey(parameter3)) {
							map.put(parameter3, temp.replaceAll("[^a-zA-Z0-9]", ""));
							continue;
						}
						// for parameter4
						if (!map.containsKey(parameterType4)) {
							map.put(parameterType4, temp.replaceAll("[^a-zA-Z0-9]", ""));
							continue;
						}

						if (!map.containsKey(parameter4)) {
							map.put(parameter4, temp.replaceAll("[^a-zA-Z0-9]", ""));
							continue;
						}
						// for parameter5
						if (!map.containsKey(parameterType5)) {
							map.put(parameterType5, temp.replaceAll("[^a-zA-Z0-9]", ""));
							continue;
						}

						if (!map.containsKey(parameter5)) {
							map.put(parameter5, temp.replaceAll("[^a-zA-Z0-9]", ""));
							continue;
						}
					}
				}
			}
		}
	}

	public static void main(String[] args) {
		methodfile = new FindMethodsFile("C:\\Users\\I320234\\Desktop\\FileInfo.java");
	}

	public ArrayList<HashMap<String, String>> getAllMethodDetails() {
		return list;
	}
}
