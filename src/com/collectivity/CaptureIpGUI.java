package com.collectivity;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.utilities.Utilities;

import java.awt.event.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

public class CaptureIpGUI extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel chooserLabel;
	JLabel resultsLabel;
	JFileChooser chooser;
	JButton go;
	FileNameExtensionFilter filter = new FileNameExtensionFilter("Souce Files (txt,java,log)", "txt", "log", "java");
	
	
	public CaptureIpGUI() {
		chooserLabel = new JLabel("Select file/Directory", JLabel.CENTER);
		resultsLabel = new JLabel();
		
		go = new JButton("Browse");
		go.addActionListener(this);
		add(chooserLabel);
		add(go);
		add(resultsLabel);
		
	}

	public void actionPerformed(ActionEvent e) {
		String results = "<html><br/><h3>Results:</h3><br/>";
		
		UIManager.put("FileChooser.readOnly", Boolean.TRUE);
		chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle("Select File or Diretory");
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		chooser.setFileFilter(filter);
		chooser.setAcceptAllFileFilterUsed(false);
		
		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			System.out.println(chooser.getSelectedFile().toString());
			String query = chooser.getSelectedFile().toString();
			try {
				File temp = new File(query);
				if(temp.isFile()){
					calculate(Utilities.scanFile(query));
				}else if (temp.isDirectory()){
					calculate(Utilities.scanDir(query,filter));
				}else{
					results += "Something went Wrong.";
				}
				
			} catch (FileNotFoundException ev) {
				ev.printStackTrace();
			} catch (IOException ev) {
				ev.printStackTrace();
			} 
		} else {
			System.out.println("No Selection ");
			results += "No Selection<br/>Please select a file or Directory";
			results += "</html>";
			this.resultsLabel.setText(results);
		}
		UIManager.put("FileChooser.readOnly", Boolean.FALSE);
	}

	public Dimension getPreferredSize() {
		return new Dimension(300, 200);
	}

	public static void main(String s[]) {
		JFrame frame = new JFrame("Capture IP Address By Sumit (532789)");
		CaptureIpGUI panel = new CaptureIpGUI();
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		frame.getContentPane().add(panel, "Center");
		frame.setSize(panel.getPreferredSize());
		frame.pack();
		frame.setVisible(true);
	}
	
	public void calculate(HashMap<String, Object> query) {
		String results = "<html><br/><h3>Results:</h3><br/>";
		results += "<ul>";
		for (Entry<String, Object> entry : query.entrySet()) {
			results += "<ul>"+entry.getKey();
			
			//for (Entry<String, Object> entry2 : (Map<String, Object>) entry.getValue()) {
				results += "<li>"+entry.getValue().toString()+"</li>";
			//}
			results += "</ul>";
		    // ...
		}
		results += "</ul>";
		results += "</html>";
		this.resultsLabel.setText(results);
	}
}