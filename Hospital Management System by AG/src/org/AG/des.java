package org.AG;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import java.awt.GridLayout;
import java.awt.Panel;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class des {

	private JTextField patientnameField;
	private JTextField patientIDfield;
	private JTextField patientNumField;
	private JFrame frame;
	private JTextField patientAgefield;
	private JTextField patientAddressField;
	private JTextField appointmentIDField;
	private JTextField appointmentDateField;
	private JTextField appointmentTimeField;
	private JTextField docNameField;
	private JTextField docIDField;
	private JTextField docNumField;
	private JTextField docSpecialfield;
	private JTextField prescriptionIDfield;
	private JTextField startDateField;
	private JTextField endDateField;
	private JTable docTable;
	private JTable patTable;
	private JTable prescTable;
	private JTextField medIDfield;
	private JTextField medsNameField;
	private JTextField medsDoseField;
	private JTable medTable;
	private JTable aptmtTable;

	/*
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					des window = new des();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public des() {
		Database.dbInit();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(0, 0, 1920, 1080);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setTitle("Hospital Management System by AG");
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
		
		Color bgColor = Color.decode("#b1e8fe");
		
//		Creating a tabbed pane to hold different panels
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane);
		tabbedPane.setBackground(bgColor);

		/*---------------------------------------------Appointment Panel-----------------------------------------------------------------------*/
		JPanel appointmentsPanel = new JPanel();
		tabbedPane.addTab("Appointments", null, appointmentsPanel, null);
		appointmentsPanel.setBackground(bgColor);
		appointmentsPanel.setLayout(null);
		
		
		
		JPanel aptmtInputPanel = new JPanel();
		aptmtInputPanel.setBorder(new TitledBorder(new LineBorder(Color.decode("#000000"), 5), "Add Appointment", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		aptmtInputPanel.setBackground(Color.decode("#daf4fe"));
		aptmtInputPanel.setBounds(340, 67, 480, 540);
		appointmentsPanel.add(aptmtInputPanel);
		aptmtInputPanel.setLayout(new GridLayout(0, 2, 0, 10));
		
		JLabel lblID = new JLabel("ID");
		aptmtInputPanel.add(lblID);
		
		appointmentIDField = new JTextField();
		aptmtInputPanel.add(appointmentIDField);
		appointmentIDField.setColumns(10);
		
		JLabel lblDate = new JLabel("Date");
		aptmtInputPanel.add(lblDate);
		
		appointmentDateField = new JTextField();
		aptmtInputPanel.add(appointmentDateField);
		appointmentDateField.setColumns(10);
		
		JLabel lblTime = new JLabel("Time");
		aptmtInputPanel.add(lblTime);
		
		appointmentTimeField = new JTextField();
		aptmtInputPanel.add(appointmentTimeField);
		appointmentTimeField.setColumns(10);
		
		JLabel lblPatient = new JLabel("Patient");
		aptmtInputPanel.add(lblPatient);
		
		JComboBox<String> patientsComboBox = new JComboBox<String>();
		aptmtInputPanel.add(patientsComboBox);
		
		JLabel lblDoctor = new JLabel("Doctor");
		aptmtInputPanel.add(lblDoctor);
		
		JComboBox<String> docComboBox = new JComboBox<String>();
		aptmtInputPanel.add(docComboBox);
		
		
		
		JButton btnaddAppointment = new JButton("Add");
		btnaddAppointment.setForeground(Color.WHITE);
		btnaddAppointment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Database.addAptmt(Integer.valueOf(appointmentIDField.getText()),
													  appointmentDateField.getText(),
													  appointmentTimeField.getText(),
													  patientsComboBox.getSelectedItem().toString(),
													  docComboBox.getSelectedItem().toString()
							);
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(aptmtInputPanel, "Please enter an Valid ID", "Invalid ID", JOptionPane.ERROR_MESSAGE);

					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		aptmtInputPanel.add(btnaddAppointment);
		btnaddAppointment.setBackground(Color.decode("#003951"));
		
		JButton btnRemoveAppointment = new JButton("Remove");
		btnRemoveAppointment.setForeground(Color.WHITE);
		btnRemoveAppointment.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Database.delete(appointmentIDField.getText(),"appointment");
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(aptmtInputPanel, "Please enter an Valid ID", "Invalid ID", JOptionPane.ERROR_MESSAGE);

					e1.printStackTrace();
				}
				
			}
		});
		aptmtInputPanel.add(btnRemoveAppointment);
		btnRemoveAppointment.setBackground(Color.decode("#003951"));
		
		JScrollPane aptmtScrollpane = new JScrollPane();
		aptmtScrollpane.setBounds(830, 65, 480, 540);
		appointmentsPanel.add(aptmtScrollpane);
		
		aptmtTable = new JTable();
		String[] aptmtColumns = {"ID","Date","Time","Patient","Doctor"};
		DefaultTableModel aptmtModel =new DefaultTableModel(aptmtColumns,0);
		aptmtTable.setModel(aptmtModel);
		aptmtScrollpane.setViewportView(aptmtTable);
		

		JButton btnloadAptmtData = new JButton("Show All");
		btnloadAptmtData.setForeground(Color.WHITE);
		btnloadAptmtData.setBackground(Color.decode("#003951"));
		btnloadAptmtData.setBounds(780, 29, 100, 30);
		btnloadAptmtData.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Database.loadAptmtData(aptmtModel);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		appointmentsPanel.add(btnloadAptmtData);
		
		
		/*---------------------------------------------Prescription Panel-----------------------------------------------------------------------*/
		JPanel prescriptionsPanel = new JPanel();
		tabbedPane.addTab("Prescriptions", null, prescriptionsPanel, null);
		prescriptionsPanel.setBackground(bgColor);
		prescriptionsPanel.setLayout(null);
		
		JPanel prescriptionInputPanel = new JPanel();
		prescriptionInputPanel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 5), "Add Prescription", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		prescriptionInputPanel.setBackground(Color.decode("#daf4fe"));
		prescriptionInputPanel.setBounds(37, 0, 1490, 165);
		prescriptionsPanel.add(prescriptionInputPanel);
		prescriptionInputPanel.setLayout(new GridLayout(0, 4, 10, 10));
		
		JLabel lblIDpres = new JLabel("ID");
		prescriptionInputPanel.add(lblIDpres);
		
		prescriptionIDfield = new JTextField();
		prescriptionIDfield.setColumns(10);
		prescriptionInputPanel.add(prescriptionIDfield);
		
		JLabel lblMedication = new JLabel("Medication");
		prescriptionInputPanel.add(lblMedication);
		
		JComboBox<String> medicationComboBox = new JComboBox<String>();
		prescriptionInputPanel.add(medicationComboBox);
		
		JLabel lblstrtDate = new JLabel("Start Date");
		prescriptionInputPanel.add(lblstrtDate);
		
		startDateField = new JTextField();
		startDateField.setColumns(10);
		prescriptionInputPanel.add(startDateField);
		
		JLabel lblEndDate = new JLabel("End Date");
		prescriptionInputPanel.add(lblEndDate);
		
		endDateField = new JTextField();
		endDateField.setColumns(10);
		prescriptionInputPanel.add(endDateField);
		
		JLabel lblPatient_1 = new JLabel("Patient");
		prescriptionInputPanel.add(lblPatient_1);
		
		JComboBox<String> prescPatientsComboBox = new JComboBox<String>();
		prescriptionInputPanel.add(prescPatientsComboBox);
		
		JLabel lblDoctor_1 = new JLabel("Doctor");
		prescriptionInputPanel.add(lblDoctor_1);
		
		JComboBox<String> prescDocComboBox = new JComboBox<String>();
		prescriptionInputPanel.add(prescDocComboBox);
		
		JScrollPane prescScrollPane = new JScrollPane();
		prescScrollPane.setBounds(36, 210, 1490, 500);
		prescriptionsPanel.add(prescScrollPane);
		
		prescTable = new JTable();
		String[] prescColumns = {"ID","Patient","Doctor","Medication","Dosage","Start Date","End Date"};
		DefaultTableModel prescModel = new DefaultTableModel(prescColumns,0);
		prescTable.setModel(prescModel);
		
				prescScrollPane.setViewportView(prescTable);
				
				JButton btnaddPresc = new JButton("Add");
				btnaddPresc.setForeground(Color.WHITE);
				btnaddPresc.setBounds(36, 170, 179, 27);
				prescriptionsPanel.add(btnaddPresc);
				btnaddPresc.setBackground(Color.decode("#003951"));
				btnaddPresc.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							Database.addPresc(
									Integer.valueOf(prescriptionIDfield.getText()),
									medicationComboBox.getSelectedItem().toString(),
									startDateField.getText(),
									endDateField.getText(),
									prescPatientsComboBox.getSelectedItem().toString(),
									prescDocComboBox.getSelectedItem().toString()
									);
						} catch (NumberFormatException e1) {
							e1.printStackTrace();
							JOptionPane.showMessageDialog(prescriptionInputPanel, "Please enter an Valid ID", "Invalid ID", JOptionPane.ERROR_MESSAGE);

						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						
					}
				});
				
				JButton btnRemovePresc = new JButton("Remove");
				btnRemovePresc.setForeground(Color.WHITE);
				btnRemovePresc.setBounds(227, 170, 179, 27);
				btnRemovePresc.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							Database.delete(prescriptionIDfield.getText(),"prescription");
						} catch (SQLException e1) {
							JOptionPane.showMessageDialog(aptmtInputPanel, "Please enter an Valid ID", "Invalid ID", JOptionPane.ERROR_MESSAGE);

							e1.printStackTrace();
						}
						
					}
				});
				prescriptionsPanel.add(btnRemovePresc);
				btnRemovePresc.setBackground(Color.decode("#003951"));
				
				JButton btnLoadPrescData = new JButton("Show All");
				btnLoadPrescData.setForeground(Color.WHITE);
				btnLoadPrescData.setBackground(Color.decode("#003951"));
				btnLoadPrescData.setBounds(418, 170, 179, 27);
				btnLoadPrescData.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							Database.loadPrescData(prescModel);
						} catch (SQLException e1) {
							
							e1.printStackTrace();
						}
						
					}
				});
				prescriptionsPanel.add(btnLoadPrescData);
		
/*---------------------------------------------Medications Panel-----------------------------------------------------------------------*/
		JPanel medicationsPanel = new JPanel();
		medicationsPanel.setLayout(null);
		medicationsPanel.setBackground(bgColor);
		tabbedPane.addTab("Medications", null, medicationsPanel, null);
		
		JPanel medicInputPanel = new JPanel();
		medicInputPanel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 5), "Add Medications", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		medicInputPanel.setBackground(Color.decode("#daf4fe"));
		medicInputPanel.setBounds(340, 67, 480, 540);
		medicationsPanel.add(medicInputPanel);
		medicInputPanel.setLayout(new GridLayout(0, 2, 0, 10));
		
		JLabel lblID_1 = new JLabel("ID");
		medicInputPanel.add(lblID_1);
		
		medIDfield = new JTextField();
		medIDfield.setColumns(10);
		medicInputPanel.add(medIDfield);
		
		JLabel namelabel = new JLabel("Name");
		medicInputPanel.add(namelabel);
		
		medsNameField = new JTextField();
		medsNameField.setColumns(10);
		medicInputPanel.add(medsNameField);
		
		JLabel lblTime_1 = new JLabel("Dosage");
		medicInputPanel.add(lblTime_1);
		
		medsDoseField = new JTextField();
		medsDoseField.setColumns(10);
		medicInputPanel.add(medsDoseField);
		
		JButton btnaddMeds = new JButton("Add");
		btnaddMeds.setForeground(Color.WHITE);
		btnaddMeds.setBackground(Color.decode("#003951"));
		btnaddMeds.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				try {
					Database.addMedication(Integer.valueOf(medIDfield.getText()), medsNameField.getText(), medsDoseField.getText());
					Database.updateComboBox(medicationComboBox, "medication");
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(medicInputPanel, "Please enter an Valid ID", "Invalid ID", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		medicInputPanel.add(btnaddMeds);
		
		JButton btnRemoveMeds = new JButton("Remove");
		btnRemoveMeds.setForeground(Color.WHITE);

		btnRemoveMeds.setBackground(Color.decode("#003951"));
		btnRemoveMeds.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Database.delete(medIDfield.getText(),"medication");
					Database.updateComboBox(medicationComboBox, "medication");

				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(aptmtInputPanel, "Please enter an Valid ID", "Invalid ID", JOptionPane.ERROR_MESSAGE);

					e1.printStackTrace();
				}
				
			}
		});
		medicInputPanel.add(btnRemoveMeds);
		
		JScrollPane medScrollpane = new JScrollPane();
		medScrollpane.setBounds(830, 65, 480, 540);
		medicationsPanel.add(medScrollpane);
		
		medTable = new JTable();
		String[] medcolumn = {"ID","Name","Dosage"};
		DefaultTableModel medModel = new DefaultTableModel(medcolumn,0); 
		medTable.setModel(medModel);
		medScrollpane.setViewportView(medTable);
		
		JButton btnloadMedData = new JButton("Show All");
		btnloadMedData.setForeground(Color.WHITE);

		btnloadMedData.setBackground(Color.decode("#003951"));
		btnloadMedData.setBounds(780, 29, 100, 30);
		btnloadMedData.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Database.loadMedData(medModel);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		medicationsPanel.add(btnloadMedData);

		/*---------------------------------------------Patients Panel-----------------------------------------------------------------------*/
		JPanel patientsPanel = new JPanel();
		tabbedPane.addTab("Patients", null, patientsPanel, null);
		patientsPanel.setBackground(bgColor);
		patientsPanel.setLayout(null);
		
		JPanel patInputPanel = new JPanel();
		patInputPanel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 5), "Add Patient", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		patInputPanel.setBounds(340, 67, 480, 540);
		patInputPanel.setBackground(Color.decode("#daf4fe"));
		patientsPanel.add(patInputPanel);
		patInputPanel.setLayout(new GridLayout(0, 2, 0, 10));
		
		JLabel lblName_1 = new JLabel("Name");
		patInputPanel.add(lblName_1);
		
		patientnameField = new JTextField();
		patInputPanel.add(patientnameField);
		patientnameField.setColumns(10);
		
		JLabel lblId_1 = new JLabel("ID");
		patInputPanel.add(lblId_1);
		
		patientIDfield = new JTextField();
		patInputPanel.add(patientIDfield);
		patientIDfield.setColumns(10);
		
		JLabel lblPhoneNo_1 = new JLabel("Phone No.");
		patInputPanel.add(lblPhoneNo_1);
		
		patientNumField = new JTextField();
		patInputPanel.add(patientNumField);
		patientNumField.setColumns(10);
		
		JLabel lblGender_1 = new JLabel("Gender");
		patInputPanel.add(lblGender_1);
		
		JComboBox<String> patientGenderComboBox = new JComboBox<String>();
		patientGenderComboBox.addItem("Male");
		patientGenderComboBox.addItem("Female");
		patientGenderComboBox.addItem("Other");
		patInputPanel.add(patientGenderComboBox);
		
		JLabel lblAge = new JLabel("Age");
		patInputPanel.add(lblAge);
		
		patientAgefield = new JTextField();
		patInputPanel.add(patientAgefield);
		patientAgefield.setColumns(10);
		
		JLabel lblAddress = new JLabel("Address");
		patInputPanel.add(lblAddress);
		
		patientAddressField = new JTextField();
		patInputPanel.add(patientAddressField);
		patientAddressField.setColumns(10);
		
		JButton btnaddPatient = new JButton("Add");
		btnaddPatient.setForeground(Color.WHITE);
		patInputPanel.add(btnaddPatient);
		btnaddPatient.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Database.addPatient(Integer.valueOf(patientIDfield.getText()),
										patientnameField.getText(),
										patientGenderComboBox.getSelectedItem().toString(),
										Integer.valueOf(patientAgefield.getText()),
										patientAddressField.getText(),
										patientNumField.getText()
										);
					Database.updateComboBox(patientsComboBox, "patient");
					Database.updateComboBox(prescPatientsComboBox, "patient");
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(patInputPanel, "Please enter an Valid ID/Age", "Invalid ID/Age", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		btnaddPatient.setBackground(Color.decode("#003951"));
		
		JButton btnRemovePatient = new JButton("Remove");
		btnRemovePatient.setForeground(Color.WHITE);
		patInputPanel.add(btnRemovePatient);
		btnRemovePatient.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Database.delete(patientIDfield.getText(),"patient");
					Database.updateComboBox(patientsComboBox, "patient");
					Database.updateComboBox(prescPatientsComboBox, "patient");
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(aptmtInputPanel, "Please enter an Valid ID", "Invalid ID", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
				
			}
		});
		btnRemovePatient.setBackground(Color.decode("#003951"));
		
		JScrollPane patScrollpane = new JScrollPane();
		patScrollpane.setBounds(830, 65, 480, 540);
		patientsPanel.add(patScrollpane);
		
		patTable = new JTable();
		String[] patColumns = {"ID","Name","Gender","Age","Address","Ph. Num"};
		DefaultTableModel patModel = new DefaultTableModel(patColumns,0);
		patTable.setModel(patModel);
		patScrollpane.setViewportView(patTable);
		
		JButton btnloadPatData = new JButton("Show All");
		btnloadPatData.setForeground(Color.WHITE);
		btnloadPatData.setBackground(Color.decode("#003951"));
		btnloadPatData.setBounds(780, 29, 100, 30);
		btnloadPatData.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Database.loadPatData(patModel);
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
				
			}
		});
		patientsPanel.add(btnloadPatData);
		
		/*---------------------------------------------Doctors Panel-----------------------------------------------------------------------*/
				JPanel doctorsPanel = new JPanel();
				tabbedPane.addTab("Doctors", null, doctorsPanel, null);
				doctorsPanel.setLayout(null);
				doctorsPanel.setBackground(bgColor);
				
				JPanel docInputPanel = new JPanel();
				docInputPanel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 5), "Add Doctor", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
				docInputPanel.setBackground(Color.decode("#daf4fe"));
				docInputPanel.setBounds(340, 67, 480, 540);
				doctorsPanel.add(docInputPanel);
				docInputPanel.setLayout(new GridLayout(0, 2, 0, 10));
				
				JLabel lblName_1_1 = new JLabel("Name");
				docInputPanel.add(lblName_1_1);
				
				docNameField = new JTextField();
				docNameField.setColumns(10);
				docInputPanel.add(docNameField);
				
				JLabel lblId_1_1 = new JLabel("ID");
				docInputPanel.add(lblId_1_1);
				
				docIDField = new JTextField();
				docIDField.setColumns(10);
				docInputPanel.add(docIDField);
				
				JLabel lblPhoneNo_1_1 = new JLabel("Phone No.");
				docInputPanel.add(lblPhoneNo_1_1);
				
				docNumField = new JTextField();
				docNumField.setColumns(10);
				docInputPanel.add(docNumField);
				
				JLabel lblGender_1_1 = new JLabel("Gender");
				docInputPanel.add(lblGender_1_1);
				
				JComboBox<String> docGenderComboBox = new JComboBox<String>();
				docGenderComboBox.addItem("Male");
				docGenderComboBox.addItem("Female");
				docGenderComboBox.addItem("Other");
				
				docInputPanel.add(docGenderComboBox);
				
				JLabel lblAddress_1 = new JLabel("Specialization");
				docInputPanel.add(lblAddress_1);
				
				docSpecialfield = new JTextField();
				docSpecialfield.setColumns(10);
				docInputPanel.add(docSpecialfield);
				
				JButton btnaddDoctor = new JButton("Add");
				btnaddDoctor.setForeground(Color.WHITE);
				btnaddDoctor.setBackground(Color.decode("#003951"));
				btnaddDoctor.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							Database.addDoctor(Integer.valueOf(docIDField.getText()),
												docNameField.getText(),	
												docGenderComboBox.getSelectedItem().toString(),
												docSpecialfield.getText(), 
												docNumField.getText() );
							Database.updateComboBox(docComboBox, "doctor");
							Database.updateComboBox(prescDocComboBox, "doctor");

						} catch (NumberFormatException e1) {
							JOptionPane.showMessageDialog(docInputPanel, "Please enter an Valid ID", "Invalid ID", JOptionPane.ERROR_MESSAGE);
							e1.printStackTrace();
						} catch (SQLException e1) {
							
							e1.printStackTrace();
						}
						
					}
				});
				docInputPanel.add(btnaddDoctor);
				
				JButton btnRemoveDoctor = new JButton("Remove");
				btnRemoveDoctor.setForeground(Color.WHITE);
				btnRemoveDoctor.setBackground(Color.decode("#003951"));
				btnRemoveDoctor.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							Database.delete(docIDField.getText(),"doctor");
							Database.updateComboBox(docComboBox, "doctor");
							Database.updateComboBox(prescDocComboBox, "doctor");
						} catch (SQLException e1) {
							JOptionPane.showMessageDialog(aptmtInputPanel, "Please enter an Valid ID", "Invalid ID", JOptionPane.ERROR_MESSAGE);
							e1.printStackTrace();
						}
						
					}
				});
				docInputPanel.add(btnRemoveDoctor);
				
				JScrollPane docScrollpane = new JScrollPane();
				docScrollpane.setBounds(830, 65, 480, 540);
				doctorsPanel.add(docScrollpane);
				
				docTable = new JTable();
				String[] docColumns = {"ID","Name","Gender","Specialization","Ph. Num"};
				DefaultTableModel docModel = new DefaultTableModel(docColumns,0);
				docTable.setModel(docModel);
				
				docScrollpane.setViewportView(docTable);
				
				JButton btnloadDocData = new JButton("Show All");
				btnloadDocData.setForeground(Color.WHITE);
				btnloadDocData.setBackground(Color.decode("#003951"));
				btnloadDocData.setBounds(780, 29, 100, 30);
				btnloadDocData.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							Database.loadDocData(docModel);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						
					}
				});
				doctorsPanel.add(btnloadDocData);
				
								
//				updating all the combo box with table values
				  try { Database.updateComboBox(docComboBox, "doctor");
				  Database.updateComboBox(patientsComboBox, "patient");
				  Database.updateComboBox(prescDocComboBox, "doctor");
				  Database.updateComboBox(prescPatientsComboBox, "patient");
				  Database.updateComboBox(medicationComboBox, "medication"); }
				  catch
				  (SQLException e1) {
				  
				  e1.printStackTrace(); }
				 
				  
	}
}
