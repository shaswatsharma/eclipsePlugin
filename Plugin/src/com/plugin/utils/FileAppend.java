package com.plugin.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileAppend {
	static String result;
	private File file;
	private StringBuilder filecontents;
	private String prevline, currline;
	BufferedReader br = null;

	public FileAppend(String content, String path) {
		appendContents(content, path);
	}

	private void appendContents(String content, String path) {
		try {
			result = readContents(content, path);
			System.out.println(result);
			writeContents(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String readContents(String content, String filepath) {// read the
																	// contents
																	// , until
																	// the last
																	// closing
																	// brace "}"
																	// is
																	// encountered
																	// .
		file = new File(filepath);
		filecontents = new StringBuilder((int) file.length());

		String separator = System.getProperty("line.separator");
		try {
			br = new BufferedReader(new FileReader(filepath));
			prevline = br.readLine();
			while ((currline = br.readLine()) != null) {
				filecontents.append(prevline + separator);
				prevline = currline;
			}
			filecontents.append(content + separator);
			filecontents.append("}");
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

	private void writeContents(String path) {// write the new contents at the
												// end of existing file and add
												// closing brace to class
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(path));
			out.write(result);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*@SuppressWarnings("unused")
	public static void main(String args[]) {
		String appendString = "public void append(){}";
		FileAppend file = new FileAppend(appendString, "C:\\Users\\I320234\\Desktop\\mainactivity.txt");
	}*/
}
