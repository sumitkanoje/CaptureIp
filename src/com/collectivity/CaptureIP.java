package com.collectivity;

import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.filechooser.FileNameExtensionFilter;

import com.utilities.Utilities;

public class CaptureIP {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		System.out.println("Please Enter the file name");
		//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		//String file = br.readLine();
		String file = "C:\\Users\\532789\\workspace\\HelloWorld";
		//File file = new File("C:\\Users\\532789\\workspace\\HelloWorld");
		final FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"Compressed files", "txt", "text", "java", "cs", "cpp",
				"py", "html");
		
		//java.util.List<File> files = Utilities.listDir(file,filter);
		
		//Utilities.listDirRecursive(file);
		
		//System.out.print(Utilities.listDirRecursive(file).toString());
		
		//System.out.println(files.toString());
		System.out.println(Utilities.scanDir(file,filter).toString());
		//System.out.println(Utilities.scanFile("file2.txt").toString());
	}
	
}
