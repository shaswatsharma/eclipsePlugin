package com.plugin.utils;

import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import com.plugin.data.ClassDetails;
import com.plugin.data.ClassVariableDetails;
import com.plugin.data.MethodDetails;
import com.plugin.data.ParameterDetails;

import java.io.*;

public class ClassParser {

	private int count;
	private LineNumberReader lr;
	private static ClassParser methodfile;
	private String filename, line, className;
	private ClassDetails classDetails;

	private ArrayList<MethodDetails> methodList;
	private ArrayList<ClassVariableDetails> variableList;

	public ClassParser(String path) {
		filename = path;
		readLines();
		printlinenumbers();
	}

	private void readLines() {
		try {
			String body = new String();
			classDetails = new ClassDetails();
			methodList = new ArrayList<MethodDetails>();
			variableList = new ArrayList<ClassVariableDetails>();
			MethodDetails methodDetails = new MethodDetails();
			lr = new LineNumberReader(new FileReader(filename));
			boolean flag = true;
			boolean bodyflag = false;
			boolean insideClass = false;

			// traverse until the end of the file
			while ((line = lr.readLine()) != null) {

				// initialize count=0 wen u find " class " for the first
				// time..Indicates beginning of class
				if (line.contains(" class ") && !line.contains("//") && flag && !line.contains("\" class \"")) {
					StringTokenizer str = new StringTokenizer(line);
					while (str.hasMoreTokens()) {
						if ((str.nextToken()).equals("class")) {
							break;
						}
					}
					this.className = str.nextToken();

					System.out.println("In line no" + lr.getLineNumber() + " class begins");
					count = 0;
					insideClass = true;
					continue;
				}

				// if (line.contains(" final ") && line.contains(" = ") &&
				// !line.contains("//") && flag) {
				// StringTokenizer str = new StringTokenizer(line);
				// String prev = null;
				// String current = null;
				// while (str.hasMoreTokens()) {
				// prev = current;
				// current = str.nextToken();
				//
				//
				// if (current.equals("=")) {
				// break;
				// }
				// }
				//
				// }

				if (bodyflag && insideClass) {
					body = body + "\n";
					body = body + line;
				}

				if (line.contains("/*")) {
					flag = false;
				}

				if (line.contains("*/")) {
					flag = true;
					continue;
				}

				if (line.contains("{") && count != 0 && !line.contains("//") && flag && insideClass) {
					count++;
				}
				if (line.contains("}") && !line.contains("//") && flag && insideClass) {
					count--;
					// if count becomes "0" then that will mark the end of the
					// method, so add the current_line_number,and the body of
					// the method to the map
					if (count == 0) {
						methodDetails.setEndingIndex(String.valueOf(lr.getLineNumber()));
						// next line will help to remove the final
						// closingbrace"}" from body section and includes
						body = body.substring(0, body.length() - 1);
						methodDetails.setBody(body);
						body = null;
						bodyflag = false;
						methodList.add(methodDetails);
					}
                    continue;
				}

				/*
				 * For first "{" inside class
				 */
				if (line.contains("{") && (!line.contains(" class ")) && count == 0 && flag && !line.contains("//")
						&& flag && insideClass) {

					MethodDetails md = new MethodDetails();
					md.setStartingIndex(String.valueOf(lr.getLineNumber()));
					methodDetails = md;
					count++;
					if (count == 1) {
						bodyflag = true;
					}
					// String[] parts;
					String[] parts = splitByThrows(line, md);

					parseLeftpart(parts[0], md);
					parseRightPart(parts[1], md);

				}

				if ((count == 0) && insideClass && flag && !line.contains("//") && !line.trim().isEmpty()
						&& (!line.contains("(") || line.contains("="))) {
					String resultValue, temp;
					boolean val;
					ClassVariableDetails varDetails = new ClassVariableDetails();
					// check for "=" in the currentline
					if (line.contains("=")) {
						String[] varArray = line.split("=");
						varDetails.setValue(varArray[1]);
						resultValue = varArray[0];
					} else {
						varDetails.setValue("");
						resultValue = line;
					}

					// check for accessSpecifier in the currentLine
					if ((resultValue.contains("private") ? true : false)) {
						varDetails.setSpecifier("private");
					} else if ((resultValue.contains("public") ? true : false)) {
						varDetails.setSpecifier("public");
					} else
						varDetails.setSpecifier("");

					// check for accessModifier in the currentline
					if (resultValue.contains("final") ? true : false) {
						varDetails.setModifier("final");
					} else if (resultValue.contains("static") ? true : false) {
						varDetails.setModifier("static");
					} else
						varDetails.setModifier("");

					StringTokenizer str = new StringTokenizer(resultValue);
					while (str.hasMoreTokens()) {
						temp = str.nextToken();
						if (!temp.equals("private") && !temp.equals("public") && !temp.equals("protected")
								&& !temp.equals("final") && !temp.equals("static")) {
							if (varDetails.getType() == null) {
								varDetails.setType(temp);
								continue;
							}
							if (varDetails.getName() == null) {
								varDetails.setName(temp);
							}

						}
					}
					variableList.add(varDetails);
				}
			}
			classDetails.setMethods(methodList);
			classDetails.setClassName(className);
			classDetails.setVariables(variableList);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// private void parserForEquals(String line){
	// if(line.contains("=")){
	// String[] varArray=line.split("=");
	// }
	// else{
	//
	// }
	// }

	// method used to obtain all the details stored in the map.
	private void printlinenumbers() {
		int i = 0;
		// List<MethodDetails> methodList = classDetails.getMethods();
		MethodDetails methodDetails;
		System.out.println(methodList.size());
		while (i < methodList.size()) {
			methodDetails = methodList.get(i);
			System.out.println("Starting Index : " + methodDetails.getStartingIndex() + "\nEnd Index : "
					+ methodDetails.getEndingIndex() + "\nAccess Specifier : " + methodDetails.getSpecifier()
					+ "\nAcess Modifier : " + methodDetails.getModifier() + "\nReturn Type : "
					+ methodDetails.getReturnType() + "\nMethod Name : " + methodDetails.getName()
					+ "\nMethod Exception : " + methodDetails.getException() + "\nMethod Body : "
					+ methodDetails.getBody());
			ParameterDetails parameterDetails = methodDetails.getParameters();
			if (parameterDetails != null) {
				System.out.println("\n Parameter Type 1 : " + parameterDetails.getParameterType1()
						+ "\n Parameter Name 1 : " + parameterDetails.getParameterName1() + "\n Parameter Type 2 : "
						+ parameterDetails.getParameterType2() + "\n Parameter Name 2 : "
						+ parameterDetails.getParameterName2() + "\n Parameter Type 3 : "
						+ parameterDetails.getParameterType3() + "\n Parameter Name 3 : "
						+ parameterDetails.getParameterName3() + "\n Parameter Type 4 : "
						+ parameterDetails.getParameterType4() + "\n Parameter Name 4 : "
						+ parameterDetails.getParameterName4() + "\n Parameter Type 5 : "
						+ parameterDetails.getParameterType5() + "\n Parameter Name 5 : "
						+ parameterDetails.getParameterName5());
			}
			i++;
		}
		System.out.println(classDetails.getClassName() + "  Hello");
		List<ClassVariableDetails> varList = classDetails.getVariables();
		if (varList != null) {
			for (i = 0; i < varList.size(); i++) {
				ClassVariableDetails cvd = varList.get(i);
				System.out.println("Access Specifier : " + cvd.getSpecifier() + "\nAccess Modifier : "
						+ cvd.getModifier() + "\nDataType : " + cvd.getType() + "\nVariable Name : " + cvd.getName()
						+ "\nValue : " + cvd.getValue());
				System.out.println(" ");
			}
		}
	}

	// checks if their is any "throws" keyword in the currrent line
	private String[] splitByThrows(String line, MethodDetails methodDetails) {
		String[] parts;
		// if their is any "throws" keyword then split the current line using it
		// and use the left_String to obtain the method details
		// and right_String to obtain the "type of Exception" thrown by that
		// method

		if (line.contains("throws")) {
			String[] throwsSplit = line.split("throws");
			parts = throwsSplit[0].split("\\(");
			methodDetails.setException(throwsSplit[1].replaceAll("[^a-zA-Z0-9]", ""));
		}
		// And if their is no "throws" keyword, then split the line using "(" to
		// separate the paremeter_section and method_name section
		else {
			parts = line.split("\\(");
		}
		return parts;
	}

	private void parseLeftpart(String leftSubString, MethodDetails methodDetails) {
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
					methodDetails.setSpecifier(temp);
					value = true;
					break;

				case "static":
				case "final":
				case "synchronized":
					methodDetails.setModifier(temp);
					value = true;
					break;

				default:
					break;

				}
				if (value == true)
					continue;

				if (methodDetails.getReturnType() == null && !leftSubString.contains(className)) {
					methodDetails.setReturnType(temp);
					continue;
				}

				if (methodDetails.getName() == null) {
					methodDetails.setName(temp);
					;
					continue;
				}

			}
		}
	}

	private void parseRightPart(String rightSubString, MethodDetails methodDetails) {
		StringTokenizer str;
		// parsing the second part of the string
		ParameterDetails parameterDetails = new ParameterDetails();
		if (rightSubString != null) {
			// if this method has only one parameter, then retrive the
			// parameterType and parameterName and store it in map
			if (!rightSubString.contains(",")) {
				str = new StringTokenizer(rightSubString);
				String temp = null;
				while (str.hasMoreTokens()) {
					temp = str.nextToken();
					// retrieving the one and only parameterType
					if (parameterDetails.getParameterType1() == null) {
						parameterDetails.setParameterType1(temp.replaceAll("[^a-zA-Z0-9]", ""));
						continue;
					}
					// retrieving the one and only parameter
					if (parameterDetails.getParameterName1() == null) {
						parameterDetails.setParameterName1(temp.replaceAll("[^a-zA-Z0-9]", ""));
						continue;
					}
				}
				methodDetails.setTotalParameters(1);
			}
			// if their are more than one parameters
			else {
				// split it using "," and get Strings of the form "parameterType
				// parameterValue"
				String[] secondHalf = rightSubString.split("\\,");

				for (String param : secondHalf) {
					// Now split these strings using " " and obtain
					// parameterType and parameterValue separately
					str = new StringTokenizer(param);
					String temp = null;
					while (str.hasMoreTokens()) {
						temp = str.nextToken();
						// for parameter1
						if (parameterDetails.getParameterType1() == null) {
							parameterDetails.setParameterType1(temp.replaceAll("[^a-zA-Z0-9]", ""));
							continue;
						}

						if (parameterDetails.getParameterName1() == null) {
							parameterDetails.setParameterName1(temp.replaceAll("[^a-zA-Z0-9]", ""));
							continue;
						}
						// for parameter2
						if (parameterDetails.getParameterType2() == null) {
							parameterDetails.setParameterType2(temp.replaceAll("[^a-zA-Z0-9]", ""));
							continue;
						}

						if (parameterDetails.getParameterName2() == null) {
							parameterDetails.setParameterName2(temp.replaceAll("[^a-zA-Z0-9]", ""));
							continue;
						}
						// for parameter3
						if (parameterDetails.getParameterType3() == null) {
							parameterDetails.setParameterType3(temp.replaceAll("[^a-zA-Z0-9]", ""));
							continue;
						}

						if (parameterDetails.getParameterName3() == null) {
							parameterDetails.setParameterName3(temp.replaceAll("[^a-zA-Z0-9]", ""));
							continue;
						}
						// for parameter4
						if (parameterDetails.getParameterType4() == null) {
							parameterDetails.setParameterType4(temp.replaceAll("[^a-zA-Z0-9]", ""));
							continue;
						}

						if (parameterDetails.getParameterName4() == null) {
							parameterDetails.setParameterName4(temp.replaceAll("[^a-zA-Z0-9]", ""));
							continue;
						}
						// for parameter5
						if (parameterDetails.getParameterType5() == null) {
							parameterDetails.setParameterType5(temp.replaceAll("[^a-zA-Z0-9]", ""));
							continue;
						}

						if (parameterDetails.getParameterName5() == null) {
							parameterDetails.setParameterName5(temp.replaceAll("[^a-zA-Z0-9]", ""));
							continue;
						}
					}
				}
				methodDetails.setTotalParameters(secondHalf.length);
			}
			methodDetails.setParameters(parameterDetails);
		}
	}

	public static void main(String[] args) {
		methodfile = new ClassParser("C:\\Users\\I323334\\Desktop\\Validate.java");
	}

	public ArrayList<MethodDetails> getAllMethodDetails() {
		return methodList;
	}

	public ClassDetails getClassDetails() {
		return classDetails;
	}
}
