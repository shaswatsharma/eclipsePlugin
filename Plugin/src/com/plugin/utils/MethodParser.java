package com.plugin.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class MethodParser {
	
	String filePath;
	HashMap<String, String> map;
	
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


/*	public static void main(String[] args) {
		String path = "C:\\Users\\I323305\\Desktop\\method.txt";
		Filereader reader = new Filereader(path);
		String content = reader.readContents();
		generateJUnitMethod(content,"path");
	}
*/
	/*
	 * 
	 */
	public void generateJUnitMethod(HashMap<String, String> map, String filePath) {
		this.filePath = filePath;
		this.map = map;
		ArrayList<String> condition = new ArrayList<>();
		Scanner scanner = new Scanner(map.get(methodBody));
		boolean comment = false;
		boolean switchCase = false;
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if (line.contains("/*") && !line.contains("\"/*\""))
				comment = true;

			if (line.contains("*/") && !line.contains("\"*/\""))
				comment = false;
			/*
			 * this is if else test
			 */
			if (!comment) {
				if (!line.trim().startsWith("//")) {
					String buffer[] = line.trim().split(" ");

					for (int i = 0; i < buffer.length; i++) {

						if (buffer[i].contains("//"))
							continue;
						if (buffer[i].equals("if") || buffer[i].equals("if("))
							condition.add("if");
						if (buffer[i].equals("else") || buffer[i].equals("else(") || buffer[i].equals("}else")
								|| buffer[i].equals("}else{"))
							condition.add("else");
						if (buffer[i].equals("while") || buffer[i].equals("while("))
							condition.add("while");
						if (buffer[i].equals("do {"))
							condition.add("doWhile");
						if (buffer[i].equals("for") || buffer[i].equals("for("))
							condition.add("for");
						if (buffer[i].equals("switch") || buffer[i].equals("switch("))
							condition.add("switch");
						if (buffer[i].contains("case") || buffer[i].contains("case:"))
							condition.add("case");
						if (buffer[i].contains("catch") || buffer[i].contains("catch("))
							condition.add("catch");
						if (buffer[i].equals("{"))
							condition.add("{");
						if (buffer[i].equals("}"))
							condition.add("}");

					}
				}
			}
		}

		System.out.println(condition.toString());
		scanner.close();

		generateTestMethods(condition);
	}

	private void generateTestMethods(ArrayList<String> condition) {
		boolean switchCase = false;
		int countIf = 0;
		int braces = 0;
		int testCases = 0;
		for (int i = 0; i < condition.size(); i++) {
			String keyword = condition.get(i);
			if (keyword.equals("catch") || keyword.equals("for") || keyword.equals("while")
					|| keyword.equals("doWhile")) {
				testCases++;
				createMethod(keyword, testCases);
			}

			if (keyword.equals("if") && countIf == 0) {
				countIf++;
				testCases++;
				createMethod(keyword, testCases);
			}
			if (keyword.equals("switch") && condition.get(i + 1).equals("{")) {
				switchCase = true;
				braces++;
			}

			if (switchCase && braces > 0) {
				// Traversing switch case
				if (keyword.equals("{"))
					braces++;
				else if (keyword.equals("}"))
					braces--;
				if (keyword.equals("case")) {
					testCases++;
					createMethod(keyword, testCases);
				}
			}
		}
	}

	private void createMethod(String condition, int i) {
		String comment = "This is a Auto Generated JUnit test case for \""+map.get(methodName)+"\" method for \"" + condition + "\" keyword used.";
		String name = map.get(methodName) + "Test" + i + condition;
		FileInfo fileInfo = new FileInfo();
		fileInfo.setAccessModifier("public");
		fileInfo.setAccessSpecifier("");
		fileInfo.setAnnotation("Test");
		fileInfo.setComments(comment);
		fileInfo.setFilePath(filePath);
		fileInfo.setList(null);
		fileInfo.setName(name);
		fileInfo.setReturnType("void");
		String method = fileInfo.createMethod();
		FileAppend fileAppend = new FileAppend(method,filePath);

	}
}
