package com.plugin.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.plugin.log.Log;
import com.plugin.utils.FileAppend;
import com.plugin.utils.FileInfo;
import com.plugin.validate.Validate;

public class MethodAdderJFrame extends JFrame {

	private JPanel contentPane;
	private JTextField filePathText;
	private JTextField methodNameText;
	private JTextField param1Text, param2Text, param3Text, param4Text, param5Text;
	private JTextArea methodDescriptionText;
	private JComboBox param1ComboBox, param2ComboBox, param3ComboBox, param4ComboBox, param5ComboBox;
	private static MethodAdderJFrame frame;
	private JButton btnAdd;

	private String[] type = { "void", "int", "boolean", "long", "String" };
	private String[] accessModifiers = { "default", "private", "public", "protected" };
	private String[] accessSpecifiers = { "default", "static", "final" };
	private final String KEY_NAME = "name";
	private final String KEY_TYPE = "type";
	private FileInfo fileInfo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		frame = new MethodAdderJFrame();
		frame.setVisible(true);
	}

	/**
	 * Create the frame.
	 */
	public MethodAdderJFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 480, 622);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		btnAdd = new JButton("Add Method");
		btnAdd.setEnabled(false);

		JLabel lblFilePath = new JLabel("File Path");

		filePathText = new JTextField();
		filePathText.setColumns(10);

		JLabel lblAddMethodDetails = new JLabel("Add Method Details");

		JLabel lblName = new JLabel("Name");

		methodDescriptionText = new JTextArea();

		methodNameText = new JTextField();
		methodNameText.setColumns(10);

		JLabel lblReturnType = new JLabel("Return Type");

		JComboBox returnTypeComboBox = new JComboBox(type);

		JLabel lblAccessModifier = new JLabel("Access Modifier");

		JComboBox accessModifierComboBox = new JComboBox(accessModifiers);

		JLabel lblAccessSpecifier = new JLabel("Access Specifier");

		JComboBox accessSpecifierComboBox = new JComboBox(accessSpecifiers);

		JLabel lblParameterName = new JLabel("Parameter Name");

		JLabel lblParameterType = new JLabel("Parameter Type");

		param1ComboBox = new JComboBox(type);
		param1ComboBox.setEnabled(false);
		param2ComboBox = new JComboBox(type);
		param2ComboBox.setEnabled(false);
		param3ComboBox = new JComboBox(type);
		param3ComboBox.setEnabled(false);
		param4ComboBox = new JComboBox(type);
		param4ComboBox.setEnabled(false);
		param5ComboBox = new JComboBox(type);
		param5ComboBox.setEnabled(false);

		param1Text = new JTextField();
		param1Text.setColumns(10);
		param2Text = new JTextField();
		param2Text.setColumns(10);
		param2Text.setEnabled(false);
		param3Text = new JTextField();
		param3Text.setColumns(10);
		param3Text.setEnabled(false);
		param4Text = new JTextField();
		param4Text.setColumns(10);
		param4Text.setEnabled(false);
		param5Text = new JTextField();
		param5Text.setEnabled(false);
		param5Text.setColumns(10);

		param1Text.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				modifyParamComboBox();
			}

			private void modifyParamComboBox() {
				if (!param1Text.getText().isEmpty()) {
					param1ComboBox.setEnabled(true);
					param2Text.setEnabled(true);

				} else {
					param1ComboBox.setEnabled(false);
					param2Text.setEnabled(false);
					param2ComboBox.setEnabled(false);
					param3Text.setEnabled(false);
					param3ComboBox.setEnabled(false);
					param4Text.setEnabled(false);
					param4ComboBox.setEnabled(false);
					param5Text.setEnabled(false);
					param5ComboBox.setEnabled(false);
				}
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				modifyParamComboBox();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				modifyParamComboBox();
			}
		});

		param2Text.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				modifyParamComboBox();
			}

			private void modifyParamComboBox() {
				if (!param2Text.getText().isEmpty()) {
					param2ComboBox.setEnabled(true);
					param3Text.setEnabled(true);

				} else {
					param2ComboBox.setEnabled(false);
					param3Text.setEnabled(false);
					param4Text.setEnabled(false);
					param5Text.setEnabled(false);
					param3ComboBox.setEnabled(false);
					param4ComboBox.setEnabled(false);
					param5ComboBox.setEnabled(false);

				}

			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				modifyParamComboBox();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				modifyParamComboBox();
			}
		});

		param3Text.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				modifyParamComboBox();
			}

			private void modifyParamComboBox() {
				if (!param3Text.getText().isEmpty()) {
					param3ComboBox.setEnabled(true);
					param4Text.setEnabled(true);

				} else {
					param3ComboBox.setEnabled(false);
					param4Text.setEnabled(false);
					param5Text.setEnabled(false);
					param4ComboBox.setEnabled(false);
					param5ComboBox.setEnabled(false);

				}

			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				modifyParamComboBox();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				modifyParamComboBox();
			}
		});

		param4Text.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				modifyParamComboBox();
			}

			private void modifyParamComboBox() {
				if (!param4Text.getText().isEmpty()) {
					param4ComboBox.setEnabled(true);
					param5Text.setEnabled(true);

				} else {
					param4ComboBox.setEnabled(false);
					param5Text.setEnabled(false);
					param5ComboBox.setEnabled(false);

				}

			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				modifyParamComboBox();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				modifyParamComboBox();
			}
		});

		param5Text.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				modifyParamComboBox();
			}

			private void modifyParamComboBox() {
				if (!param5Text.getText().isEmpty()) {
					param5ComboBox.setEnabled(true);
				} else
					param5ComboBox.setEnabled(false);

			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				modifyParamComboBox();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				modifyParamComboBox();
			}
		});

		JLabel lblMethodDescription = new JLabel("Method Description");

		btnAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				FileAppend fileAppend = new FileAppend(fileInfo.createMethod(), fileInfo.getFilePath());
				JOptionPane.showMessageDialog(frame, "Method addedd successfully");
				btnAdd.setEnabled(false);
			}
		});

		JButton btnValidate = new JButton("Validate");
		btnValidate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String filePath = filePathText.getText().trim();
				String methodName = methodNameText.getText().trim();
				String returnType = (String) returnTypeComboBox.getSelectedItem();
				String param1 = param1Text.getText().trim();
				String param1Type = (String) param1ComboBox.getSelectedItem();
				String param2 = null;
				String param2Type = null;
				String param3Type = null;
				String param4Type = null;
				String param5Type = null;
				String param3 = null;
				String param4 = null;
				String param5 = null;
				String methodDescription = methodDescriptionText.getText().trim();

				String accessModifier = (String) accessModifierComboBox.getSelectedItem();
				String accessSpecifier = (String) accessSpecifierComboBox.getSelectedItem();

				if (param2Text.isEnabled()) {
					param2 = param2Text.getText().trim();
					param2Type = (String) param2ComboBox.getSelectedItem();
				}
				if (param3Text.isEnabled()) {
					param3 = param3Text.getText().trim();
					param3Type = (String) param3ComboBox.getSelectedItem();
				}
				if (param4Text.isEnabled()) {
					param4 = param4Text.getText().trim();
					param4Type = (String) param4ComboBox.getSelectedItem();
				}
				if (param5Text.isEnabled()) {
					param5 = param5Text.getText().trim();
					param5Type = (String) param5ComboBox.getSelectedItem();
				}

				String errorLog = "";
				boolean isError = false;

				if (filePath.isEmpty()) {
					isError = true;
					errorLog += "Please Enter file path\n";
				}
				if (methodName.isEmpty()) {
					isError = true;
					errorLog += "Please Enter method name\n";
				}
				if (methodDescription.isEmpty()) {
					isError = true;
					errorLog += "Please Enter method description\n";
				}

				if (isError) {
					JOptionPane.showMessageDialog(frame, errorLog);
				} else {
					fileInfo = new FileInfo();
					fileInfo.setFilePath(filePath.replace("\\", "\\\\"));
					fileInfo.setAccessModifier(accessModifier);
					fileInfo.setAccessSpecifier(accessSpecifier);
					fileInfo.setComments(methodDescription);
					fileInfo.setName(methodName);
					fileInfo.setReturnType(returnType);

					Validate validate = new Validate();

					ArrayList<HashMap<String, String>> argumentList = new ArrayList<HashMap<String, String>>();
					if (param1.equals("") || param1.isEmpty()) {
						Log log = validate.validateAll(fileInfo);
						if (log.getResult()) {
							JOptionPane.showMessageDialog(frame,
									"Your Input is Validated. Please Click Add to add your inputs.");
							btnAdd.setEnabled(true);
						} else {
							JOptionPane.showMessageDialog(frame, "Invalid Input!\n"+log.getError());
						}

					} else {
						if (param1 != "" && param1 != null && !param1.isEmpty()) {
							HashMap<String, String> map = new HashMap<String, String>();
							map.put(KEY_NAME, param1);
							map.put(KEY_TYPE, param1Type);
							argumentList.add(map);

							if (param2 != "" && param2 != null && !param2.isEmpty()) {
								map = new HashMap<String, String>();
								map.put(KEY_NAME, param2);
								map.put(KEY_TYPE, param2Type);
								argumentList.add(map);

								if (param3 != "" && param3 != null && !param3.isEmpty()) {
									map = new HashMap<String, String>();
									map.put(KEY_NAME, param3);
									map.put(KEY_TYPE, param3Type);
									argumentList.add(map);

									if (param4 != "" && param4 != null && !param4.isEmpty()) {
										map = new HashMap<String, String>();
										map.put(KEY_NAME, param4);
										map.put(KEY_TYPE, param4Type);
										argumentList.add(map);

										if (param5 != "" && param5 != null && !param5.isEmpty()) {
											map = new HashMap<String, String>();
											map.put(KEY_NAME, param5);
											map.put(KEY_TYPE, param5Type);
											argumentList.add(map);
										}
									}
								}
							}
						}
						fileInfo.setList(argumentList);
						Log log = validate.validateAll(fileInfo);
						if (log.getResult()) {
							JOptionPane.showMessageDialog(frame,
									"Your Input is Validated. Please Click Add to add your inputs.");
							btnAdd.setEnabled(true);
						} else {
							JOptionPane.showMessageDialog(frame, "Invalid Input!\n"+log.getError());
						}
					}
				}

			}
		});

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblFilePath, GroupLayout.PREFERRED_SIZE, 151, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(lblName, GroupLayout.PREFERRED_SIZE, 151,
												GroupLayout.PREFERRED_SIZE)
										.addGap(151).addComponent(methodNameText, GroupLayout.PREFERRED_SIZE, 151,
												GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(lblReturnType, GroupLayout.PREFERRED_SIZE, 151,
										GroupLayout.PREFERRED_SIZE)
								.addGap(151).addComponent(returnTypeComboBox, GroupLayout.PREFERRED_SIZE, 151,
										GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(lblAccessModifier, GroupLayout.PREFERRED_SIZE, 151,
										GroupLayout.PREFERRED_SIZE)
								.addGap(151).addComponent(accessModifierComboBox, GroupLayout.PREFERRED_SIZE, 151,
										GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(lblAccessSpecifier, GroupLayout.PREFERRED_SIZE, 151,
										GroupLayout.PREFERRED_SIZE)
								.addGap(151).addComponent(accessSpecifierComboBox, GroupLayout.PREFERRED_SIZE, 151,
										GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(lblParameterName, GroupLayout.PREFERRED_SIZE, 151,
										GroupLayout.PREFERRED_SIZE)
								.addGap(151).addComponent(lblParameterType, GroupLayout.PREFERRED_SIZE, 151,
										GroupLayout.PREFERRED_SIZE))
						.addGroup(
								gl_contentPane.createSequentialGroup()
										.addComponent(param1Text, GroupLayout.PREFERRED_SIZE, 151,
												GroupLayout.PREFERRED_SIZE)
										.addGap(151).addComponent(param1ComboBox, GroupLayout.PREFERRED_SIZE, 151,
												GroupLayout.PREFERRED_SIZE))
						.addGroup(
								gl_contentPane.createSequentialGroup()
										.addComponent(param2Text, GroupLayout.PREFERRED_SIZE, 151,
												GroupLayout.PREFERRED_SIZE)
										.addGap(151).addComponent(param2ComboBox, GroupLayout.PREFERRED_SIZE, 151,
												GroupLayout.PREFERRED_SIZE))
						.addGroup(
								gl_contentPane.createSequentialGroup()
										.addComponent(param3Text, GroupLayout.PREFERRED_SIZE, 151,
												GroupLayout.PREFERRED_SIZE)
										.addGap(151).addComponent(param3ComboBox, GroupLayout.PREFERRED_SIZE, 151,
												GroupLayout.PREFERRED_SIZE))
						.addGroup(
								gl_contentPane.createSequentialGroup()
										.addComponent(param4Text, GroupLayout.PREFERRED_SIZE, 151,
												GroupLayout.PREFERRED_SIZE)
										.addGap(151).addComponent(param4ComboBox, GroupLayout.PREFERRED_SIZE, 151,
												GroupLayout.PREFERRED_SIZE))
						.addComponent(lblMethodDescription, GroupLayout.PREFERRED_SIZE, 151, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup().addGap(155).addComponent(btnValidate,
								GroupLayout.PREFERRED_SIZE, 151, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup().addGap(155).addComponent(btnAdd,
								GroupLayout.PREFERRED_SIZE, 151, GroupLayout.PREFERRED_SIZE))
						.addGroup(
								gl_contentPane.createParallelGroup(Alignment.TRAILING, false).addComponent(filePathText)
										.addGroup(gl_contentPane.createSequentialGroup().addGap(161)
												.addComponent(lblAddMethodDetails, GroupLayout.PREFERRED_SIZE, 151,
														GroupLayout.PREFERRED_SIZE)
												.addGap(314)))
						.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
								.addGroup(gl_contentPane.createSequentialGroup().addGap(10)
										.addComponent(methodDescriptionText))
								.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
										.addComponent(param5Text, GroupLayout.PREFERRED_SIZE, 151,
												GroupLayout.PREFERRED_SIZE)
										.addGap(151).addComponent(param5ComboBox, GroupLayout.PREFERRED_SIZE, 151,
												GroupLayout.PREFERRED_SIZE))))
				.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		gl_contentPane
				.setVerticalGroup(
						gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup().addGap(1)
										.addComponent(lblFilePath, GroupLayout.PREFERRED_SIZE, 25,
												GroupLayout.PREFERRED_SIZE)
								.addComponent(filePathText, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(lblAddMethodDetails, GroupLayout.PREFERRED_SIZE, 25,
										GroupLayout.PREFERRED_SIZE)
				.addGap(31)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblName, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(methodNameText, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblReturnType, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(returnTypeComboBox, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
				.addGroup(
						gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblAccessModifier, GroupLayout.PREFERRED_SIZE, 25,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(accessModifierComboBox, GroupLayout.PREFERRED_SIZE, 25,
										GroupLayout.PREFERRED_SIZE))
				.addGroup(
						gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblAccessSpecifier, GroupLayout.PREFERRED_SIZE, 25,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(accessSpecifierComboBox, GroupLayout.PREFERRED_SIZE, 25,
										GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblParameterName, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblParameterType, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(param1Text, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(param1ComboBox, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(param2Text, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(param2ComboBox, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(param3Text, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(param3ComboBox, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(param4Text, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(param4ComboBox, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(param5Text, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(param5ComboBox, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
				.addGap(25)
				.addComponent(lblMethodDescription, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(methodDescriptionText, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
				.addGap(18).addComponent(btnValidate, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
				.addGap(18).addComponent(btnAdd, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
				.addGap(88)));
		contentPane.setLayout(gl_contentPane);
	}

}
