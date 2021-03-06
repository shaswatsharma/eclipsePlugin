package com.plugin.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Filereader {

	static String result;
	private File file;
	private StringBuilder filecontents;
	private String currline;
	private static Filereader fileobj = null;
	private String filepath;
	BufferedReader br = null;

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

	public static void main(String args[]) {
		fileobj = new Filereader("C:\\Users\\I320234\\Desktop\\mainactivity.txt");
	}

}
