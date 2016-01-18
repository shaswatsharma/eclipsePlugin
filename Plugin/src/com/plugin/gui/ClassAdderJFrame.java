package com.plugin.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JTextArea;

public class ClassAdderJFrame extends JFrame {

	private JPanel contentPane;
	private JTextField filePathText;
	private JTextField nameText;
	private enum classType{
		CLASS,INTERFACE
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClassAdderJFrame frame = new ClassAdderJFrame();
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
	public ClassAdderJFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 732, 528);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblFilePath = new JLabel("File Path");
		lblFilePath.setBounds(10, 45, 46, 14);
		contentPane.add(lblFilePath);
		
		filePathText = new JTextField();
		filePathText.setBounds(97, 42, 498, 20);
		contentPane.add(filePathText);
		filePathText.setColumns(10);
		
		JButton btnBrowse = new JButton("Browse");
		btnBrowse.setBounds(617, 41, 89, 23);
		contentPane.add(btnBrowse);
		
		JLabel lblAddClassDetails = new JLabel("Add Class Details");
		lblAddClassDetails.setBounds(308, 109, 101, 14);
		contentPane.add(lblAddClassDetails);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(31, 164, 46, 14);
		contentPane.add(lblName);
		
		nameText = new JTextField();
		nameText.setBounds(142, 161, 175, 20);
		contentPane.add(nameText);
		nameText.setColumns(10);
		
		JLabel lblType = new JLabel("Type");
		lblType.setBounds(397, 164, 46, 14);
		contentPane.add(lblType);
		
		JComboBox fileTypeComboBox = new JComboBox();
		fileTypeComboBox.setBounds(485, 161, 28, 20);
		contentPane.add(fileTypeComboBox);
		
		JLabel lblAccessModifier = new JLabel("Access Modifier");
		lblAccessModifier.setBounds(31, 223, 101, 14);
		contentPane.add(lblAccessModifier);
		
		JComboBox accessModifierComboBox = new JComboBox();
		accessModifierComboBox.setBounds(142, 220, 28, 20);
		contentPane.add(accessModifierComboBox);
		
		JCheckBox chckbxStatic = new JCheckBox("Static");
		chckbxStatic.setBounds(389, 219, 97, 23);
		contentPane.add(chckbxStatic);
		
		JCheckBox chckbxFinal = new JCheckBox("final");
		chckbxFinal.setBounds(498, 219, 97, 23);
		contentPane.add(chckbxFinal);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(84, 350, 553, 75);
		contentPane.add(textArea);
		
		JLabel lblClassDescription = new JLabel("Class Description");
		lblClassDescription.setBounds(84, 325, 129, 14);
		contentPane.add(lblClassDescription);
	}
}
