package com.plugin.validate;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.plugin.utils.FileInfo;
import com.plugin.utils.Filereader;

/*
 * This class validates the different parameters of a class entered by the user
 */
public class Validate {
	/*
	 * This method validates the name of the method
	 */
	private boolean isValidMethodName(FileInfo fileInfo) {
		String abc = fileInfo.getName();
		Filereader fileReader = new Filereader(fileInfo.getFilePath());
		String str = fileReader.readContents();

		// This condition returns false if method name starts from the digit
		if (abc.charAt(0) > 47 && abc.charAt(0) < 57)
			return false;

		// This condition returns false if method is already present except in
		// case of overloading
		if (str.contains(" " + abc + "()"))
			return false;

		// Here we are checking either method name is violated or not using
		// regular expression
		Pattern p = Pattern.compile("^[a-z0-9$_]++$", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(abc);
		boolean b = m.find();
		return b;

	}

	/*
	 * This method validates the name of the arguments of a method
	 */
	private boolean isValidParameterName(FileInfo fileInfo) {
		List<HashMap<String, String>> list = fileInfo.getList();
		for (int i = 0; i < list.size(); i++) {
			HashMap<String, String> map = (HashMap<String, String>) list.get(i);
			String abc = map.get("name");

			// This condition returns false if parameter name starts with digit
			if (abc.charAt(0) > 47 && abc.charAt(0) < 57)
				return false;

			// Here we are checking either method name is violated or not using
			// regular expression
			Pattern p = Pattern.compile("^[a-z0-9$_]++$", Pattern.CASE_INSENSITIVE);
			Matcher m = p.matcher(abc);
			boolean b = m.find();
			if(b==false)
				return b;
		}
		return true;
	}

	/*
	 * Checks whether the no of arguments doesn't exceed the limit
	 */
	private boolean isValidNumberOfArguments(FileInfo fileInfo) {
		if (fileInfo.getList().size() > 4)
			return false;
		return true;
	}

	/*
	 * Check whether comments are available or not
	 */
	private boolean isCommentAvailable(FileInfo fileInfo) {
		if (fileInfo.getComments() == null)
			return false;
		return true;
	}

	/*
	 * It validates all the method and returns a final boolean value
	 */
	public boolean validateAll(FileInfo fileInfo) {
		return isValidMethodName(fileInfo) && isValidParameterName(fileInfo) && isCommentAvailable(fileInfo) && isValidNumberOfArguments(fileInfo);
	}

}
