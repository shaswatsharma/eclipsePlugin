package com.plugin.utils;

import java.util.ArrayList;
import java.util.HashMap;

public class FileInfo {

	private String name, accessSpecifier, accessModifier, returnType, resultMethod, comments, filePath, addComment;
	private ArrayList<HashMap<String, String>> list;
	private final String KEY_NAME = "name";
	private final String KEY_TYPE = "type";

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {// name of the method to be appended
		this.name = name;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {// return type of method
		this.returnType = returnType;
	}

	public String getAccessSpecifier() {
		return accessSpecifier;
	}

	public void setAccessSpecifier(String accessSpecifier) {// access Specifier
															// of the method to
															// be appended
		this.accessSpecifier = accessSpecifier;
	}

	public String getAccessModifier() {
		return accessModifier;
	}

	public void setAccessModifier(String accessModifier) {// set the access
															// specifier of the
															// resulting method
		this.accessModifier = accessModifier;
	}

	public ArrayList<HashMap<String, String>> getList() {
		return list;
	}

	public void setList(ArrayList<HashMap<String, String>> list) {
		this.list = list;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getComments() {
		return comments;
	}

	public String createMethod() {// to create the result method which has to be
								// appended //public final int count() {
		
		addComment = "/*\n" + " *" + comments + "\n */\n"; 
		
		resultMethod = addComment+ accessSpecifier + " " + accessModifier + " " + returnType + " " + name + "(";
		String val = null;
		String parName = null;
		String parType = null;
		if (list!=null && list.size() != 0) {
			HashMap<String, String> map = (HashMap<String, String>) list.get(0);
			parName = map.get(KEY_NAME);
			parType = map.get(KEY_TYPE);
			if (parName != null && parType != null) {
				resultMethod = resultMethod + parType + " " + parName;
			}
		}
		for (int i = 1; i < list.size(); i++) {
			HashMap<String, String> map = (HashMap<String, String>) list.get(i);

			parName = (String) map.get(KEY_NAME);
			parType = (String) map.get(KEY_TYPE);
			if (parName != null && parType != null) {
				val = "," + parType + " " + parName;
				resultMethod = resultMethod + val;
			}

		}
		resultMethod = resultMethod + "){}";
		return resultMethod;
	}

/*	public static void main(String args[]) {

		HashMap<String, String> map1 = new HashMap<String, String>();
		map1.put("name", "par1");
		map1.put("type", "int");

		HashMap<String, String> map2 = new HashMap<String, String>();
		map2.put("name", "par2");
		map2.put("type", "float");

		HashMap<String, String> map3 = new HashMap<String, String>();
		map3.put("name", "par3");
		map3.put("type", "Long");

		HashMap<String, String> map4 = new HashMap<String, String>();
		map4.put("name", "par4");
		map4.put("type", "String");

		ArrayList<HashMap<String, String>> testlist = new ArrayList<HashMap<String, String>>();
		testlist.add(map1);
		testlist.add(map2);
		testlist.add(map3);
		testlist.add(map4);

		FileInfo info = new FileInfo();
		info.setAccessModifier("final");
		info.setAccessSpecifier("public");
		info.setName("testmethod");
		info.setReturnType("void");
		info.setList(testlist);

		info.createMethod();
	}
*/
}
