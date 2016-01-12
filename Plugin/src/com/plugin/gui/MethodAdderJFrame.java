package com.plugin.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import com.plugin.log.Log;
import com.plugin.utils.FileAppend;
import com.plugin.utils.FileInfo;
import com.plugin.validate.Validate;
import java.awt.Color;

public class MethodAdderJFrame extends JFrame {

	private JPanel contentPane;
	private JTextField filePathText;
	private JTextField methodNameText;
	private JTextField param1Text, param2Text, param3Text, param4Text, param5Text, customReturnText;
	private JTextArea methodDescriptionText;
	private JComboBox param1ComboBox, param2ComboBox, param3ComboBox, param4ComboBox, param5ComboBox,
			annotationComboBox;
	private static MethodAdderJFrame frame;
	private JButton btnAdd;

	private String[] annotation = { "none", "Deprecated", "Override", "SupperssWarnings", "Entity" };
	private String[] returnType = { "int", "boolean", "long", "String" };
	private String[] type = { "void", "int", "boolean", "long", "String", "Custom" };
	private String[] accessModifiers = { "default", "private", "public", "protected" };
	private String[] accessSpecifiers = { "default", "static", "final" };
	private final String KEY_NAME = "name";
	private final String KEY_TYPE = "type";
	private FileInfo fileInfo;

	private enum Variables {
		none, Override,Deprecated, SupperssWarnings, Entity 

	}

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
		fileInfo = new FileInfo();
		setTitle("Method Adder and Verifier");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 820, 772);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		btnAdd = new JButton("Add Method");
		btnAdd.setEnabled(false);

		JFileChooser fc = new JFileChooser();

		JLabel lblFilePath = new JLabel("File Path");

		filePathText = new JTextField();
		filePathText.setColumns(10);

		customReturnText = new JTextField();
		customReturnText.setColumns(10);
		customReturnText.setEnabled(false);

		// JList<String> list = new JList<>(type);
		// AutoCompleteDecorator.decorate(list, customReturnText);

		JLabel lblAddMethodDetails = new JLabel("Add Method Details");

		JLabel lblName = new JLabel("Name");

		JLabel numOfChars = new JLabel("-100");
		numOfChars.setForeground(Color.RED);

		methodDescriptionText = new JTextArea();
		methodDescriptionText.setLineWrap(true);
		methodDescriptionText.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				validateNumberOfChars();
			}

			private void validateNumberOfChars() {
				int length = methodDescriptionText.getText().length();
				if (length < 100) {
					numOfChars.setText("-" + (100 - length));
					numOfChars.setForeground(Color.RED);
				} else {
					numOfChars.setText("" + length);
					numOfChars.setForeground(Color.BLACK);
				}
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				validateNumberOfChars();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				validateNumberOfChars();
			}
		});

		methodNameText = new JTextField();
		methodNameText.setColumns(10);

		JLabel lblReturnType = new JLabel("Return Type");

		String[] customTypes = fileInfo.getCustomReturnType();

		JComboBox returnTypeComboBox = new JComboBox(type);
		AutoCompleteDecorator.decorate(returnTypeComboBox);

		for (int i = 0; i < customTypes.length; i++)
			returnTypeComboBox.addItem(customTypes[i]);

		returnTypeComboBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					String selectedValue = (String) returnTypeComboBox.getSelectedItem();
					if (selectedValue.equals("Custom")) {
						customReturnText.setEnabled(true);
						fileInfo.setReturnTypeCustom(true);
					} else {
						customReturnText.setEnabled(false);
						fileInfo.setReturnTypeCustom(false);
					}
				}
			}
		});

		JLabel lblAccessModifier = new JLabel("Access Modifier");

		JComboBox accessModifierComboBox = new JComboBox(accessModifiers);

		JLabel lblAccessSpecifier = new JLabel("Access Specifier");

		JComboBox accessSpecifierComboBox = new JComboBox(accessSpecifiers);

		JLabel lblParameterName = new JLabel("Parameter Name");

		JLabel lblParameterType = new JLabel("Parameter Type");

		param1ComboBox = new JComboBox(returnType);
		param1ComboBox.setEnabled(false);
		AutoCompleteDecorator.decorate(param1ComboBox);
		for (int i = 0; i < customTypes.length; i++)
			param1ComboBox.addItem(customTypes[i]);

		param2ComboBox = new JComboBox(returnType);
		param2ComboBox.setEnabled(false);
		AutoCompleteDecorator.decorate(param2ComboBox);
		for (int i = 0; i < customTypes.length; i++)
			param2ComboBox.addItem(customTypes[i]);

		param3ComboBox = new JComboBox(returnType);
		param3ComboBox.setEnabled(false);
		AutoCompleteDecorator.decorate(param3ComboBox);
		for (int i = 0; i < customTypes.length; i++)
			param3ComboBox.addItem(customTypes[i]);

		param4ComboBox = new JComboBox(returnType);
		param4ComboBox.setEnabled(false);
		AutoCompleteDecorator.decorate(param4ComboBox);
		for (int i = 0; i < customTypes.length; i++)
			param4ComboBox.addItem(customTypes[i]);

		param5ComboBox = new JComboBox(returnType);
		param5ComboBox.setEnabled(false);
		AutoCompleteDecorator.decorate(param5ComboBox);
		for (int i = 0; i < customTypes.length; i++)
			param5ComboBox.addItem(customTypes[i]);


		annotationComboBox = new JComboBox(Variables.values());

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
				if (methodDescription.length() < 100) {
					isError = true;
					errorLog += "Please Enter method description of atleast 100 characters\n";
				}

				if (isError) {
					JOptionPane.showMessageDialog(frame, errorLog);
				} else {
					fileInfo.setFilePath(filePath.replace("\\", "\\\\"));
					fileInfo.setAccessModifier(accessModifier);
					fileInfo.setAccessSpecifier(accessSpecifier);
					fileInfo.setComments(methodDescription);
					fileInfo.setName(methodName);
					fileInfo.setAnnotation(annotationComboBox.getSelectedItem().toString());

					if (!fileInfo.isReturnTypeCustom()) {
						fileInfo.setReturnType(returnType);
					} else {
						fileInfo.setReturnType(customReturnText.getText().trim());
					}

					Validate validate = new Validate();

					ArrayList<HashMap<String, String>> argumentList = new ArrayList<HashMap<String, String>>();
					if (param1.equals("") || param1.isEmpty()) {
						Log log = validate.validateAll(fileInfo);
						if (log.getResult()) {
							JOptionPane.showMessageDialog(frame,
									"Your Input is Validated. Please Click Add to add your inputs.");
							btnAdd.setEnabled(true);
						} else {
							btnAdd.setEnabled(false);
							JOptionPane.showMessageDialog(frame, "Invalid Input!\n" + log.getError());
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
							btnAdd.setEnabled(false);
							JOptionPane.showMessageDialog(frame, "Invalid Input!\n" + log.getError());
						}
					}
				}

			}
		});

		JButton btnBrowse = new JButton("Browse");
		btnBrowse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int returnVal = fc.showOpenDialog(MethodAdderJFrame.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					filePathText.setText(file.getAbsolutePath());
				}
			}
		});

		JLabel lblAnnotation = new JLabel("Annotation");

		JLabel lblCustomReturnType = new JLabel("Custom Return Type");

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane
				.createParallelGroup(
						Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addComponent(lblFilePath, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
						.addGap(18)
						.addComponent(filePathText, GroupLayout.PREFERRED_SIZE, 556, GroupLayout.PREFERRED_SIZE)
						.addGap(29).addComponent(btnBrowse).addContainerGap(77, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup().addContainerGap()
						.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(lblName, GroupLayout.PREFERRED_SIZE, 151,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED).addComponent(methodNameText,
												GroupLayout.PREFERRED_SIZE, 151, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(lblAccessModifier, GroupLayout.PREFERRED_SIZE, 151,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(lblAnnotation))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addComponent(accessModifierComboBox, GroupLayout.PREFERRED_SIZE, 151,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(annotationComboBox, 0, 161, Short.MAX_VALUE))))
						.addGap(87)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblCustomReturnType)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addComponent(lblReturnType, GroupLayout.PREFERRED_SIZE, 151,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(lblAccessSpecifier, GroupLayout.PREFERRED_SIZE, 151,
												GroupLayout.PREFERRED_SIZE)))
						.addGap(33)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(returnTypeComboBox, GroupLayout.PREFERRED_SIZE, 151,
										GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(accessSpecifierComboBox, GroupLayout.PREFERRED_SIZE, 152,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(customReturnText, GroupLayout.PREFERRED_SIZE, 152,
												GroupLayout.PREFERRED_SIZE)))
						.addGap(55))
				.addGroup(
						gl_contentPane.createSequentialGroup().addGap(122)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addComponent(param5Text, GroupLayout.PREFERRED_SIZE, 151,
												GroupLayout.PREFERRED_SIZE)
								.addComponent(param4Text, GroupLayout.PREFERRED_SIZE, 151, GroupLayout.PREFERRED_SIZE)
								.addComponent(param3Text, GroupLayout.PREFERRED_SIZE, 151, GroupLayout.PREFERRED_SIZE)
								.addComponent(param2Text, GroupLayout.PREFERRED_SIZE, 151, GroupLayout.PREFERRED_SIZE)
								.addComponent(param1Text, GroupLayout.PREFERRED_SIZE, 151, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblParameterName, GroupLayout.PREFERRED_SIZE, 151,
										GroupLayout.PREFERRED_SIZE))
						.addGap(208)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblParameterType, GroupLayout.PREFERRED_SIZE, 151,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(param1ComboBox, 0, 161, Short.MAX_VALUE)
								.addComponent(param2ComboBox, 0, 161, Short.MAX_VALUE)
								.addComponent(param3ComboBox, 0, 161, Short.MAX_VALUE)
								.addComponent(param4ComboBox, 0, 161, Short.MAX_VALUE)
								.addComponent(param5ComboBox, 0, 161, Short.MAX_VALUE)).addGap(162))
				.addGroup(gl_contentPane.createSequentialGroup().addGap(33).addGroup(gl_contentPane
						.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(lblMethodDescription, GroupLayout.PREFERRED_SIZE, 151,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(numOfChars).addGap(564))
						.addGroup(gl_contentPane.createSequentialGroup()
								.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addComponent(methodDescriptionText, Alignment.LEADING,
												GroupLayout.DEFAULT_SIZE, 733, Short.MAX_VALUE)
								.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(btnValidate, GroupLayout.PREFERRED_SIZE, 151,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED, 431, Short.MAX_VALUE).addComponent(
												btnAdd, GroupLayout.PREFERRED_SIZE, 151, GroupLayout.PREFERRED_SIZE)))
								.addGap(38))))
				.addGroup(gl_contentPane.createSequentialGroup().addGap(316)
						.addComponent(lblAddMethodDetails, GroupLayout.PREFERRED_SIZE, 151, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(337, Short.MAX_VALUE)));
		gl_contentPane
				.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup().addGap(32)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(btnBrowse)
										.addComponent(filePathText, GroupLayout.PREFERRED_SIZE, 25,
												GroupLayout.PREFERRED_SIZE)
						.addComponent(lblFilePath, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
				.addGap(18)
				.addComponent(lblAddMethodDetails, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
				.addGap(19)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblName, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(methodNameText, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(returnTypeComboBox, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblReturnType, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
				.addGap(25)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblAccessModifier, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(accessModifierComboBox, GroupLayout.PREFERRED_SIZE, 25,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(customReturnText, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblCustomReturnType))
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
						.createSequentialGroup().addGap(81)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblParameterType, GroupLayout.PREFERRED_SIZE, 25,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(lblParameterName, GroupLayout.PREFERRED_SIZE, 25,
										GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(param1ComboBox, GroupLayout.PREFERRED_SIZE, 25,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(param1Text, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
						.addGap(18)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(param2ComboBox, GroupLayout.PREFERRED_SIZE, 25,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(param2Text, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
						.addGap(18)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(param3ComboBox, GroupLayout.PREFERRED_SIZE, 25,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(param3Text, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
						.addGap(18)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(param4ComboBox, GroupLayout.PREFERRED_SIZE, 25,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(param4Text, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
						.addGap(18)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(param5ComboBox, GroupLayout.PREFERRED_SIZE, 25,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(param5Text, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_contentPane.createSequentialGroup().addGap(33)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblAnnotation)
										.addComponent(annotationComboBox, GroupLayout.PREFERRED_SIZE, 24,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(lblAccessSpecifier, GroupLayout.PREFERRED_SIZE, 25,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(accessSpecifierComboBox, GroupLayout.PREFERRED_SIZE, 25,
												GroupLayout.PREFERRED_SIZE))))
				.addGap(51)
				.addGroup(
						gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblMethodDescription, GroupLayout.PREFERRED_SIZE, 25,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(numOfChars)).addPreferredGap(ComponentPlacement.UNRELATED)
				.addComponent(methodDescriptionText, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
				.addGap(18)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnValidate, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnAdd, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)).addGap(36)));
		contentPane.setLayout(gl_contentPane);
	}
}
