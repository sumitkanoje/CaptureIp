package com.utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * This class provides a variety of basic utility methods that are not dependent
 * on any other classes within the org.jamwiki package structure.
 */
public class Utilities {
	private static String IPADDRESS_PATTERN = "(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
	private static String URL_PATTERN = "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
	
	//public static HashMap<String, Object> scanLine(String ipString) {
	public static List<String> scanLine(String ipString) {
		Pattern pattern = Pattern.compile(URL_PATTERN+"|"+IPADDRESS_PATTERN);
		Matcher matcher = pattern.matcher(ipString);

		List<String> results = new ArrayList<String>();
		//HashMap<String, Object> results = new HashMap<String, Object>();
		while (matcher.find()) {
			results.add(matcher.group());
			
		}
		return results;
	}

	public static HashMap<String, Object> scanFile(String file)
			throws FileNotFoundException, IOException {
		HashMap<String, Object> matched = new HashMap<String, Object>();

		try (BufferedReader br2 = new BufferedReader(new FileReader(file))) {
			String line;
			HashMap<String, Object> results = new HashMap<String, Object>();
			int count = 0;
			while ((line = br2.readLine()) != null) {
				count++;
				if (Utilities.scanLine(line).isEmpty())
					continue;
				else
					results.put("Line "+count, Utilities.scanLine(line));
				
			}
			matched.put(file,results);
		}
		return matched;
	}

	public static HashMap<String, Object> scanDir(String dirURI,final FileNameExtensionFilter filter) {
		HashMap<String, Object> matched = new HashMap<String, Object>();
		
		Utilities.listDir(dirURI, filter);
		List<File> files = DirList;
		
		for (File file : files) {
			if(file.isDirectory()){
				List<File> extra = Utilities.listDir(file.getAbsolutePath(), filter);
				files.addAll(extra);
			}
		}
		
		System.out.println(files.size());
		
		for (File file : files) {
			if (file.isFile()) {
				try {
					List<Object> values = new ArrayList<Object>(Utilities.scanFile(file.getAbsolutePath()).values());
					@SuppressWarnings("unchecked")
					HashMap<String, Object> value = (HashMap<String, Object>) values.get(0);
					//System.out.println(file.getName()+" "+value.isEmpty());
					if(!value.isEmpty())
						matched.put(file.getPath(), values.get(0));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		//matched.put(dirURI, results);
		return matched;
	}
	
	public static List<File> DirList = new ArrayList<File>();
	public static List<File> listDir(String dirURI,final FileNameExtensionFilter filter){
		File rootDir = new File(dirURI);
		List<File> res = new ArrayList<File>();
		
		File[] files = rootDir.listFiles(new FileFilter() {
			public boolean accept(File file) {
				return filter.accept(file);
			};
		});
		
		for (File filez : files) {
			if(filez.isDirectory()) {
				List<File> stage = Utilities.listDir(filez.getAbsolutePath(), filter);
				//System.out.println("Now: "+stage.toString());
				Utilities.DirList.addAll(stage);
			}
			else if(filez.isFile()){
				DirList.add(filez);
			}
		}
		//System.out.println("Then: "+DirList.toString());
		return res;
		
	}
	
	

}