package Windows;
import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import DataBase.AddSensorsDataDB;
import Windows.LoginFrame;

public class ForgetPassword extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	Container container = getContentPane();
	AddSensorsDataDB DB = new AddSensorsDataDB();
	JLabel FirstName = new JLabel("FIRST NAME :  ");
	JLabel LastName = new JLabel("LAST NAME  :  ");
	JLabel NumberCarte = new JLabel("IDENTITY NUMBER  :  ");
	JLabel Email = new JLabel("YOUR EMAIL  :  ");
	JLabel ConfirmEmail = new JLabel("CONFIRM YOUR EMAIL :  ");
	JLabel Password = new JLabel("YOUR PASSWORD : ");
	
	JTextField FirstNameText = new JTextField();
	JTextField LastNameText = new JTextField();
	JTextField NumberCarteText = new JTextField();
	JTextField EmailText = new JTextField();
	JTextField ConfirmEmailText = new JTextField();
	JLabel PasswordText = new JLabel();
	
    JButton GetPasswordButton = new JButton("GET PASSWORD ");
    JButton BackButton = new JButton("GET BACK ");
    JButton resetButton = new JButton("RESET");
    
	public ForgetPassword(){
		setUpFrame();
	    setLayoutManager();
	    setLocationAndSize();
	    addComponentsToContainer();
	    setFonts();
        addActionEvent();
	}
	public void setUpFrame(){
		setTitle("Forget My Password ");
	    setVisible(true);
	    setSize(460,520);
	    setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
	}
	public void setLayoutManager() {
	    container.setLayout(null);
	}
	public void setFonts() {
		FirstName.setFont(new Font("Times New Roman",Font.BOLD,15));
		PasswordText.setFont(new Font("Times New Roman",Font.ITALIC,16));
	    LastName.setFont(new Font("Times New Roman",Font.BOLD,15));
	   	NumberCarte.setFont(new Font("Times New Roman",Font.BOLD,15));
	    Email.setFont(new Font("Times New Roman",Font.BOLD,15));
	   	ConfirmEmail.setFont(new Font("Times New Roman",Font.BOLD,15));
	   	Password.setFont(new Font("Times New Roman",Font.BOLD,15));
	   	GetPasswordButton.setFont(new Font("Times New Roman",Font.BOLD,15));
	   	resetButton.setFont(new Font("Times New Roman",Font.BOLD,15));
	   	GetPasswordButton.setBackground(new Color(192,192,192));
	    resetButton.setBackground(new Color(192,192,192));
	   	BackButton.setBackground(new Color(192,192,192));
	}
	public void setLocationAndSize() {
    	FirstName.setBounds(50, 60, 200, 30);
    	LastName.setBounds(50, 110, 200, 30);
    	NumberCarte.setBounds(50, 160, 200, 30);
    	Email.setBounds(50,210,200,30);
    	ConfirmEmail.setBounds(50,260,200,30);
    	Password.setBounds(50,310,150,30);
    	
    	FirstNameText.setBounds(250, 60, 150, 30);
    	LastNameText.setBounds(250, 110, 150, 30);
    	NumberCarteText.setBounds(250, 160, 150, 30);
    	EmailText.setBounds(250,210,150,30);
    	ConfirmEmailText.setBounds(250,260,150,30);
    	PasswordText.setBounds(250,310,150,30);
	    	
    	GetPasswordButton.setBounds(52,380 ,180, 30);
        resetButton.setBounds(250, 380, 148, 30);
        BackButton.setBounds(50,430,350,30);
    }
	public void addComponentsToContainer() {
        container.add(FirstName);
	    container.add(LastName);
        container.add(FirstNameText);
        container.add(LastNameText);
	    container.add(NumberCarte);
        container.add(NumberCarteText);
        container.add(Email);
	    container.add(EmailText);
	    container.add(ConfirmEmail);
        container.add(ConfirmEmailText);
        container.add(Password);
	    container.add(PasswordText);
	    container.add(GetPasswordButton); 
	    container.add(resetButton);
	    container.add(BackButton);
	}
	public void addActionEvent() {
	    GetPasswordButton.addActionListener(this);
	    resetButton.addActionListener(this);
	    BackButton.addActionListener(this);
	}   
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==BackButton) {
			var Frame = new LoginFrame();
			Frame.setVisible(true);
			dispose();
		}
		if(e.getSource()==GetPasswordButton) {
			  String FName = FirstNameText.getText();
			  String LName = LastNameText.getText();
			  String NCarte = NumberCarteText.getText();
			  String NEmail = EmailText.getText();
			  String ConfirmEmail = ConfirmEmailText.getText();
			  if(FName.isEmpty()||LName.isEmpty()||NCarte.isEmpty()||NEmail.isEmpty()||ConfirmEmail.isEmpty()) {
				  JOptionPane.showMessageDialog(this, "Please Fill in all the blanks");
			  }else {
				  if(NEmail.equals(ConfirmEmail)) {
					  // We will Verifie the user Data with the DataBase if it matches with some accounts.
					  try {
						  Connection conn = DriverManager.getConnection(DB.getDB_URL(),DB.getUSERNAME(),DB.getPASSWORD());
						  Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
						  ResultSet r = stmt.executeQuery("SELECT * FROM users");
						  //r.next();
						  while(r.next()) {
							  String First_Name = r.getString("First_Name");
							  //System.out.println(First_Name);
							  String Last_Name = r.getString("Last_Name");
							  //System.out.println(Last_Name);
							  String NumberCarte = r.getString("NumberCarte");
							  //System.out.println(NumberCarte);
							  String Email = r.getString("Email");
							  String Password = r.getString("Password");
							  if(LName.equalsIgnoreCase(Last_Name)&&NCarte.equalsIgnoreCase(NumberCarte)&&NEmail.equalsIgnoreCase(Email)){
								  PasswordText.setText("     "+Password);
							  } 
						  }
						  
					  }catch(Exception e2) {
						  
					  }
					  
				  }else {
					  JOptionPane.showMessageDialog(this, "Please Verifie your Email Confirmation");
				  }
			  }
		}
		if(e.getSource()==resetButton) {
			FirstNameText.setText("");
			LastNameText.setText("");
			FirstNameText.setText("");
			NumberCarteText.setText("");
			EmailText.setText("");
			ConfirmEmailText.setText("");
			PasswordText.setText("");
		}
	}
}