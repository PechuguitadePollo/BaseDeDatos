package net.roseindia.jtableExample;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.sql.*;
import java.awt.event.*;

public class BaseDeDatos implements ActionListener{
	JFrame frame, frame1;
	JTextField textbox;
	JLabel label;
	JButton button;
	JPanel panel;
	static JTable table;
	
	String driverName = "oracle.jdbc.driver.OracleDriver";
	String url="jdbc:oracle:thin:@dbinf.inf.pucp.edu.pe:1521/DBINF";
	String userName = "A20150087";
	String password = "w86j19eh";
	String[] columnNames = {"COUNTRY_ID", "COUNTRY_NAME", "REGION_ID"};
	
	public void createUI(){
		frame = new JFrame("Database Search Result");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		textbox = new JTextField();
		textbox.setBounds(250,30,150,20); 
		label = new JLabel("Enter Country Region");
		label.setBounds(10, 30, 215, 20);
		button = new JButton("search");
		button.setBounds(153,331,150,20);
		button.addActionListener(this);
		
		frame.getContentPane().add(textbox);
		frame.getContentPane().add(label);
		frame.getContentPane().add(button);
		frame.setVisible(true);
		frame.setSize(500, 400); 
	} 
	
	public void actionPerformed(ActionEvent ae){
		button = (JButton)ae.getSource();
		System.out.println("Showing Table Data.......");
		showTableData(); 
	} 
	
	public void showTableData(){
		frame1 = new JFrame("Database Search Result");
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame1.getContentPane().setLayout(new BorderLayout()); 
		//TableModel tm = new TableModel();
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(columnNames);
		//DefaultTableModel model = new DefaultTableModel(tm.getData1(), tm.getColumnNames()); 
		//table = new JTable(model);
		table = new JTable();
		table.setModel(model); 
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setFillsViewportHeight(true);
		JScrollPane scroll = new JScrollPane(table);
		scroll.setHorizontalScrollBarPolicy(
		JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setVerticalScrollBarPolicy(
		JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); 
		String textvalue = textbox.getText();
		String countryID = "";
		String countryName = "";
		String regionID = "";
		try{ 
			Class.forName(driverName); 
			Connection con = DriverManager.getConnection(url, userName, password);
			String sql = "select * from COUNTRIES where REGION_ID = " +textvalue;
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			int i =0;
			while(rs.next()){
				countryID = rs.getString("COUNTRY_ID");
				countryName = rs.getString("COUNTRY_NAME");
				regionID = rs.getString("REGION_ID"); 
				model.addRow(new Object[]{countryID, countryName, regionID});
				i++; 
			}
			if(i <1){
				JOptionPane.showMessageDialog(null, "No Record Found","Error",
				JOptionPane.ERROR_MESSAGE);
			}
			if(i ==1){
				System.out.println(i+" Record Found");
			}
			else{
				System.out.println(i+" Records Found");
			}
		}
		catch(Exception ex){
			JOptionPane.showMessageDialog(null, ex.getMessage(),"Error",
			JOptionPane.ERROR_MESSAGE);
		}
		frame1.getContentPane().add(scroll);
		frame1.setVisible(true);
		frame1.setSize(400,300);
	}
	
	public static void main(String args[]){
		BaseDeDatos sr = new BaseDeDatos();
		sr.createUI(); 
	}
}
