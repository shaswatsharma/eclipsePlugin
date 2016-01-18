package com.plugin.main;

import java.util.ArrayList;
import java.util.HashMap;

import com.plugin.utils.FileCreation;
import com.plugin.utils.FindMethodsFile;
import com.plugin.utils.MethodParser;

public class ClassValidator {

	public static void main(String[] args) {
		String StartIndex = "start";
		String EndIndex = "end";
		String accessSpecifier = "specifier";
		String accessmodifier = "modifier";
		String returnType = "returnType";
		String methodName = "name";
		String parameter1 = "param1";
		String parameter2 = "param2";
		String parameter3 = "param3";
		String parameter4 = "param4";
		String parameter5 = "param5";
		String parameterType1 = "type1";
		String parameterType2 = "type2";
		String parameterType3 = "type3";
		String parameterType4 = "type4";
		String parameterType5 = "type5";
		String exceptionThrown = "exception";
		String methodBody = "methodbody";

		FindMethodsFile findMethodsFile = new FindMethodsFile("C:\\Users\\I323305\\Desktop\\FileInfo.java");
		ArrayList<HashMap<String, String>> list = findMethodsFile.getAllMethodDetails();

		System.out.println("Number of methods are " + list.size());

		// Creating JUnit Class
		FileCreation fileCreation = new FileCreation();
		fileCreation.setAccessSpecifier("public");
		fileCreation.setFilename("FileInfoTest");
		fileCreation.setPath("C:\\Users\\I323305\\Desktop\\");
		fileCreation.createFile();

		// Creating JUnitMethods
		int i = 0;
		while (i < list.size()) {
			HashMap<String, String> map = list.get(i);

			MethodParser methodParser = new MethodParser();
			methodParser.generateJUnitMethod(map, "C:\\Users\\I323305\\Desktop\\FileInfoTest.java");

			i++;
			/*
			 * System.out.println("starting index: " +
			 * Integer.valueOf(map.get(StartIndex)) + " ending index :" +
			 * Integer.valueOf(map.get(EndIndex)) + " accessSpecifier=" +
			 * map.get(accessSpecifier) + " accessmodifier=" +
			 * map.get(accessmodifier) + " returnType=" + map.get(returnType) +
			 * " methodName=" + map.get(methodName) + "\n firstParameterType=" +
			 * map.get(parameterType1) + " firstParameter=" +
			 * map.get(parameter1) + "\n secondParameterType=" +
			 * map.get(parameterType2) + " secondParameter=" +
			 * map.get(parameter2) + "\n thirdParameterType=" +
			 * map.get(parameterType3) + " thirdParameter=" +
			 * map.get(parameter3) + "\n fourthParameterType=" +
			 * map.get(parameterType4) + " fourthParameter=" +
			 * map.get(parameter4) + "\n fifthParameterType=" +
			 * map.get(parameterType5) + " fifthParameter=" +
			 * map.get(parameter5) + "\n ExceptionThrown=" +
			 * map.get(exceptionThrown) + "\n methodBody=" +
			 * map.get(methodBody)); i++;
			 */ }

	}

}
