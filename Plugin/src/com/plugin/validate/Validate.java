package com.plugin.validate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * This class validates the different parameters of a class entered by the user
 */
public class Validate {
	/*
	 * This method validates the name of the method
	 */
	private boolean isValidMethodName() {
		String abc = "hello";
		String str = "abc";

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
	private boolean isValidParameterName() {
		String abc = "hello";
		
		//This condition returns false if parameter name starts with digit
		if (abc.charAt(0) > 47 && abc.charAt(0) < 57)
			return false;

		// Here we are checking either method name is violated or not using
		// regular expression
		Pattern p = Pattern.compile("^[a-z0-9$_]++$", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(abc);
		boolean b = m.find();
		return b;
	}

	/*
	 * Checks whether the no of arguments doesn't exceed the limit
	 */
	private boolean isValidNumberOfArguments() {
		return true;
	}

	/*
	 * Check whether comments are available or not
	 */
	private boolean isCommentAvailable() {
		return true;
	}

	/*
	 * It validates all the method and returns a final boolean value
	 */
	public boolean validateAll() {
		return isValidMethodName() && isValidParameterName() && isCommentAvailable() && isValidNumberOfArguments();
	}

}
