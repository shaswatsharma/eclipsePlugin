package com.plugin.gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.plugin.utils.Filereader;
import javax.swing.JTextArea;
import javax.swing.UIManager;

public class ClassValidatorJFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private final String KEY_FOR_LOC = "LOC";
	private final String KEY_FOR_BLANK_LINES = "BL";
	private final String KEY_FOR_COMMENTED_LINES = "CL";


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClassValidatorJFrame frame = new ClassValidatorJFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ClassValidatorJFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 756, 571);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(131, 151, 476, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblFilePath = new JLabel("File Path");
		lblFilePath.setBounds(31, 154, 65, 14);
		contentPane.add(lblFilePath);
		
		JButton btnBrowse = new JButton("Browse");
		btnBrowse.setBounds(641, 150, 89, 23);
		contentPane.add(btnBrowse);

		JTextArea textArea = new JTextArea();
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 15));
		textArea.setLineWrap(true);
		textArea.setBackground(UIManager.getColor("Panel.background"));
		textArea.setBounds(212, 324, 339, 151);
		textArea.setEditable(false);

		JButton btnValidate = new JButton("Validate");
		btnValidate.setBounds(323, 182, 89, 23);
		
		btnValidate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Filereader fileReader = new Filereader(textField.getText().trim());
				Map<String, Integer> result = fileReader.countLines();
				String output = " The file has the following details \r\n" + " Total Number of Lines of Code : " +result.get(KEY_FOR_LOC);
				output += "\r\n Total Number of Blank lines : "+result.get(KEY_FOR_BLANK_LINES);
				output += "\r\n Total Number of Comment Lines : " + result.get(KEY_FOR_COMMENTED_LINES);
				textArea.setText(output);
			}
		});
		
		btnBrowse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				int returnVal = fc.showOpenDialog(ClassValidatorJFrame.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					textField.setText(file.getAbsolutePath());
				}
			}
		});
		contentPane.add(btnValidate);
		
		contentPane.add(textArea);
	}
}
