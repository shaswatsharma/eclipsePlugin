package com.plugin.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class FileInfo {

	private String name, accessSpecifier, accessmodifier, returntytpe, resultMethod;
	private ArrayList<HashMap<String, String>> list;
	private final String KEY_NAME = "name";
	private final String KEY_TYPE = "type";

	public String getName() {
		return name;
	}

	public void setName(String name) {// name of the method to be appended
		this.name = name;
	}

	public String getReturntytpe() {
		return returntytpe;
	}

	public void setReturntytpe(String returntytpe) {// return type of method
		this.returntytpe = returntytpe;
	}

	public String getAccessSpecifier() {
		return accessSpecifier;
	}

	public void setAccessSpecifier(String accessSpecifier) {// access Specifier
															// of the method to
															// be appended
		this.accessSpecifier = accessSpecifier;
	}

	public String getAccessmodifier() {
		return accessmodifier;
	}

	public void setAccessmodifier(String accessmodifier) {// set the access
															// specifier of the
															// resulting method
		this.accessmodifier = accessmodifier;
	}

	public ArrayList<HashMap<String, String>> getList() {
		return list;
	}

	public void setList(ArrayList<HashMap<String, String>> list) {
		this.list = list;
	}

	public void createMethod() {// to create the result method whicjh has to be
								// appended //public final int count() {
		resultMethod = accessSpecifier + " " + accessmodifier + " " + returntytpe + " " + name + "(";
		String val = null;
		String parName=null;
		String parType=null;
		if(list.size()!=0) {
			HashMap<String, String> map = (HashMap<String, String>) list.get(0);
			 parName=map.get(KEY_NAME);
			 parType=map.get(KEY_TYPE);	
			 if (parName != null && parType != null) {
					resultMethod = resultMethod + parType + " " + parName;
			   }
		}
		for (int i = 1; i < list.size(); i++) {
			HashMap<String, String> map = (HashMap<String, String>) list.get(i);

			
				 parName = (String) map.get(KEY_NAME);
				 parType = (String) map.get(KEY_TYPE);
				if (parName != null && parType != null) {
					val = ","+parType + " " + parName;
					resultMethod=resultMethod + val;
				}
			
			
		}
		resultMethod=resultMethod+"){}";
		System.out.println(resultMethod);
	}
	
	public static void main(String args[]){
		
		HashMap<String,String> map1=new HashMap<String,String>();
		map1.put("name", "par1");
		map1.put("type","int");
		
		HashMap<String,String> map2=new HashMap<String,String>();
		map2.put("name", "par2");
		map2.put("type","float");
		
		HashMap<String,String> map3=new HashMap<String,String>();
		map3.put("name", "par3");
		map3.put("type","Long");
		
		HashMap<String,String> map4=new HashMap<String,String>();
		map4.put("name","par4");
		map4.put("type","String");
		
		ArrayList<HashMap<String,String>> testlist=new ArrayList<HashMap<String,String>>();
		testlist.add(map1);
		testlist.add(map2);
		testlist.add(map3);
		testlist.add(map4);
		
		FileInfo info=new FileInfo();
		info.setAccessmodifier("final");
		info.setAccessSpecifier("public");
		info.setName("testmethod");
		info.setReturntytpe("void");
		info.setList(testlist);
		
		info.createMethod();
	}
	

}
