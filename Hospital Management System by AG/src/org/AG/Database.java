package org.AG;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;

import org.sqlite.SQLiteDataSource;

public class Database {
//	declaring connection and datasource variables
	static Connection conn;
	static SQLiteDataSource ds;
	
//	initialize method to initialize the database with all the tables

	public static void dbInit() {
		ds = new SQLiteDataSource();
		
		try {
            ds = new SQLiteDataSource();
            ds.setUrl("jdbc:sqlite:HospitalDB.db");
        } catch ( Exception e ) {
            e.printStackTrace();
            
            System.exit(0);
        }
        try {
        	 conn = ds.getConnection();
        	 
        	 Statement statement = conn.createStatement();
        	 // Create the Patients table
             statement.executeUpdate("CREATE TABLE IF NOT EXISTS patients (\n"
             		+ "  patient_id INTEGER PRIMARY KEY,\n"
             		+ "  name TEXT NOT NULL,\n"
             		+ "  gender TEXT NOT NULL,\n"
             		+ "  age INTEGER NOT NULL,\n"
             		+ "  address TEXT NOT NULL,\n"
             		+ "  phone_number TEXT NOT NULL\n"
             		+ ");");
             
            
             
             // Create the Doctors table
             statement.executeUpdate("CREATE TABLE IF NOT EXISTS doctors (\n"
             		+ "  doctor_id INTEGER PRIMARY KEY,\n"
             		+ "  name TEXT NOT NULL,\n"
             		+ "  gender TEXT NOT NULL,\n"
             		+ "  specialization TEXT NOT NULL,\n"
             		+ "  phone_number TEXT NOT NULL\n"
             		+ ");");
             
           
             // Create the Appointments table
             statement.executeUpdate("CREATE TABLE IF NOT EXISTS appointments (\n"
             		+ "  appointment_id INTEGER PRIMARY KEY,\n"
             		+ "  patient TEXT NOT NULL,\n"
             		+ "  doctor TEXT INTEGER NOT NULL,\n"
             		+ "  appointment_date TEXT NOT NULL,\n"
             		+ "  appointment_time TEXT NOT NULL\n"
             		+ ");"
             		);
             
             // Create the Medications table
             statement.executeUpdate("CREATE TABLE IF NOT EXISTS medications (\n"
             		+ "  medication_id INTEGER PRIMARY KEY,\n"
             		+ "  name TEXT NOT NULL,\n"
             		+ "  dosage TEXT NOT NULL\n"
             		+ ");");
             
             //Create the Prescriptions table
             statement.executeUpdate("CREATE TABLE IF NOT EXISTS prescriptions (\n"
             		+ "  prescription_id INTEGER PRIMARY KEY,\n"
             		+ "  patient_id INTEGER NOT NULL,\n"
             		+ "  doctor_id INTEGER NOT NULL,\n"
             		+ "  medication_id INTEGER NOT NULL,\n"
             		+ "  start_date TEXT NOT NULL,\n"
             		+ "  end_date TEXT NOT NULL,\n"
             		+ "  FOREIGN KEY (patient_id) REFERENCES patients (patient_id),\n"
             		+ "  FOREIGN KEY (doctor_id) REFERENCES doctors (doctor_id),\n"
             		+ "  FOREIGN KEY (medication_id) REFERENCES medications (medication_id)\n"
             		+ ");");

//           Closing statement and connection  
             statement.close();
        	 conn.close();
        	 
        }catch ( SQLException e ) {
            e.printStackTrace();
            System.exit( 0 );
        }
        finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            }catch (SQLException e) {
                System.err.println(e);
              }
        
        }
	

	}
	
//	Method to add new Doctors into the database
	protected static void addDoctor(int id,String name,String gender,String specialization,String phone_number) throws SQLException{
		conn = ds.getConnection();
		PreparedStatement ps =conn.prepareStatement("INSERT INTO "
													+ "doctors(doctor_id,name,gender,specialization,phone_number) "
													+ "VALUES(?,?,?,?,?)");
		ps.setInt(1, id);
		ps.setString(2, name);
		ps.setString(3, gender);
		ps.setString(4, specialization);
		ps.setString(5, phone_number);
		ps.executeUpdate();
		ps.close();
		conn.close();
	}
//	Method to load doctor's data to the table
	public static void loadDocData(DefaultTableModel model) throws SQLException {
		model.setRowCount(0);
		conn = ds.getConnection();
		String query = "SELECT * From doctors";
		PreparedStatement ps = conn.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
 		// Add data to the table model
		while (rs.next()) {
		    int id = rs.getInt("doctor_id");
		    String name = rs.getString("name");
		    String gender = rs.getString("gender");
		    String specialization = rs.getString("specialization");
		    String phnum = rs.getString("phone_number");

		    Object[] row = {id, name, gender,specialization,phnum};
		    model.addRow(row);
		}
		rs.close();
		ps.close();
		conn.close();
		
	}

//  Method to add new Patient in the database
	public static void addPatient(int id,String name,String gender,int age,String address ,String phone_number) throws SQLException {
		conn = ds.getConnection();
		PreparedStatement ps =conn.prepareStatement("INSERT INTO "
													+ "patients(patient_id,name,gender,age,address,phone_number) "
													+ "VALUES(?,?,?,?,?,?)");
		ps.setInt(1, id);
		ps.setString(2, name);
		ps.setString(3, gender);
		ps.setInt(4, age);
		ps.setString(5, address);
		ps.setString(6, phone_number);
		ps.executeUpdate();
		ps.close();
		conn.close();
		
		
	}
	
//	Method to load Patients data to the table
	public static void loadPatData(DefaultTableModel model) throws SQLException {
		model.setRowCount(0);
		conn = ds.getConnection();
		String query = "SELECT * From patients";
		PreparedStatement ps = conn.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
 		// Add data to the table model
		while (rs.next()) {
		    int id = rs.getInt("patient_id");
		    String name = rs.getString("name");
		    String gender = rs.getString("gender");
		    int age= rs.getInt("age");
		    String address = rs.getString("address");
		    String phnum = rs.getString("phone_number");

		    Object[] row = {id, name, gender,age,address,phnum};
		    model.addRow(row);
		}
		rs.close();
		ps.close();
		conn.close();
		
	}
//	Method to update the combo box with either the patient or doctors name
	public static void updateComboBox(JComboBox<String> cbx,String tableName) throws SQLException {
		cbx.removeAllItems();
		conn = ds.getConnection();
		String query = "SELECT "+tableName+"_id,name FROM "+tableName+"s;"; 
		PreparedStatement ps = conn.prepareStatement(query);
		ResultSet rs =  ps.executeQuery();
		
		while (rs.next()) {
			cbx.addItem(rs.getString(tableName+"_id")+ "|" + rs.getString("name"));
			
		}
		ps.close();
		conn.close();
		
	}
	
//Method to add new Medication to the medications table
	public static void addMedication(int id,String name,String dosage) throws SQLException {
		conn = ds.getConnection();
		String query = "INSERT INTO medications(medication_id,name,dosage) VALUES(?,?,?)";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setInt(1, id);
		ps.setString(2, name);
		ps.setString(3, dosage);
		ps.executeUpdate();
		ps.close();
		conn.close();
	}
// Method to load Medication data into the model
	public static void loadMedData(DefaultTableModel model) throws SQLException {
		model.setRowCount(0);
		conn = ds.getConnection();
		String query = "SELECT * From medications";
		PreparedStatement ps = conn.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
 		// Add data to the table model
		while (rs.next()) {
		    int id = rs.getInt("medication_id");
		    String name = rs.getString("name");
		    String dosage = rs.getString("dosage");

		    Object[] row = {id, name, dosage};
		    model.addRow(row);
		}
		rs.close();
		ps.close();
		conn.close();
	}

//	Method to delete a specific value from a specific table
	public static void delete(String id, String table) throws SQLException {
		conn = ds.getConnection();
		String query = "DELETE FROM "+table+"s WHERE "+table+"_id = ?";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, id);
		ps.executeUpdate();
		ps.close();
		conn.close();
	}

//	Method to add the prescription data to the prescription table
	public static void addPresc(Integer id, String meds, String start, String end, String patient,
			String doctor) throws SQLException {
		conn = ds.getConnection();
		PreparedStatement ps =conn.prepareStatement("INSERT INTO "
													+ "prescriptions(prescription_id,patient_id,doctor_id,medication_id,start_date,end_date) "
													+ "VALUES(?,?,?,?,?,?)");
		
		meds = meds.substring(0, meds.indexOf("|"));
		patient = patient.substring(0, patient.indexOf("|"));
		doctor = doctor.substring(0, doctor.indexOf("|"));
		ps.setInt(1, id);
		ps.setInt(2, Integer.valueOf(patient));
		ps.setInt(3, Integer.valueOf(doctor));
		ps.setInt(4, Integer.valueOf(meds));
		ps.setString(5, start);
		ps.setString(6, end);
		ps.executeUpdate();
		ps.close();
		conn.close();
	}

	
	
//  Method to load the prescripton data into the table model
	public static void loadPrescData(DefaultTableModel model) throws SQLException {
		model.setRowCount(0);
		conn = ds.getConnection();
		String query = "SELECT *,"
						+ "patients.name AS pname,"
						+ "doctors.name AS dname,"
						+ "medications.name AS mname "
						+ "From prescriptions "
						+ "INNER JOIN patients ON patients.patient_id = prescriptions.patient_id "
						+ "INNER JOIN doctors ON doctors.doctor_id = prescriptions.doctor_id "
						+ "INNER JOIN medications ON medications.medication_id= prescriptions.medication_id ;";
		
		PreparedStatement ps = conn.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
 		// Add data to the table model
		while (rs.next()) {
		    int id = rs.getInt("prescription_id");
		    String patient = rs.getInt("patient_id")+"|"+rs.getString("pname");
		    String doctor = rs.getInt("doctor_id")+"|"+rs.getString("dname");
		    String med = rs.getInt("medication_id")+"|"+rs.getString("mname");
		    String dosage = rs.getString("dosage");
		    String start = rs.getString("start_date");
		    String end = rs.getString("end_date");
		    Object[] row = {id,patient,doctor,med,dosage,start,end};
		    model.addRow(row);
		}
		rs.close();
		ps.close();
		conn.close();
	}
	
//	Method to load the appointment data into the table model
	public static void loadAptmtData(DefaultTableModel model) throws SQLException {
		model.setRowCount(0);
		conn = ds.getConnection();
		String query = "SELECT * From appointments";
		PreparedStatement ps = conn.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
 		// Add data to the table model
		while (rs.next()) {
		    int id = rs.getInt("appointment_id");
		    String date = rs.getString("appointment_date");
		    String time = rs.getString("appointment_time");
		    String patient = rs.getString("patient");
		    String doctor = rs.getString("doctor");
		    Object[] row = {id,date,time,patient,doctor };
		    model.addRow(row);
		}
		rs.close();
		ps.close();
		conn.close();
	}

//	Method to add the appointment data into the database
	public static void addAptmt(Integer id, String date, String time, String patient, String doctor) throws SQLException {
		conn = ds.getConnection();
		PreparedStatement ps =conn.prepareStatement("INSERT INTO "
													+ "appointments(appointment_id,appointment_date,appointment_time,patient,doctor) "
													+ "VALUES(?,?,?,?,?)");
		
		
		ps.setInt(1, id);
		ps.setString(2, date);
		ps.setString(3,time);
		ps.setString(4,patient);
		ps.setString(5, doctor);
		
		ps.executeUpdate();
		ps.close();
		conn.close();
		
	}
	

}
