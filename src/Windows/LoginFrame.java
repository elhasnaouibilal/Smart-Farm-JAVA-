package Windows;
import javax.swing.*;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;

import DataBase.AddSensorsDataDB;
import Interfaces.DataBase;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class LoginFrame extends JFrame implements ActionListener {
    /**
	 * 
	 */
	// Defining all the Buttons, Labels and TextFields used in the frame.
	private String Operation = "Consulting the Weather";
	private static final long serialVersionUID = 1L;
	private Container container = getContentPane();
	private JLabel userLabel = new JLabel("USERNAME :  ");
	private JLabel passwordLabel = new JLabel("PASSWORD  :  ");
	private JTextField userTextField = new JTextField();
	private JPasswordField passwordField = new JPasswordField();
	private JButton loginButton = new JButton("LOGIN");
	private JButton resetButton = new JButton("RESET");
	private JButton ForgetPassword = new JButton("Forget Your Password ? ");
	private JCheckBox showPassword = new JCheckBox("Show Password");
	AddSensorsDataDB DB = new AddSensorsDataDB();
    // Defining the constructor.
    public LoginFrame() {
    	setUpFrame();
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        setUpIconFontColor();
        addActionEvent();
    }
    // Setting up the Frame.
    public void setUpFrame(){
        setTitle("Smart - Ferme Login");
        setVisible(true);
        setSize(400,380);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }
    // Setting up the Layout to null.
    public void setLayoutManager() {
    	container.setLayout(null);
    }
    // Setting the location and size of the labels, TextFields.
    public void setLocationAndSize() {
        userLabel.setBounds(50, 60, 200, 30);
        passwordLabel.setBounds(50, 130, 200, 30);
        userTextField.setBounds(200, 60, 150, 30);
        passwordField.setBounds(200, 130, 150, 30);
        showPassword.setBounds(196, 160, 150, 30);
        loginButton.setBounds(65,220 , 120, 30);
        resetButton.setBounds(225, 220, 120, 30);
        ForgetPassword.setBounds(65,270,280,30);
    }
    // Setting up the Icons,Background color of the Buttons.
    public void setUpIconFontColor() {
    	userLabel.setIcon(new ImageIcon("src/Icons/user.png"));
    	passwordLabel.setIcon(new ImageIcon("src/Icons/lock.png"));
    	loginButton.setBackground(new Color(192,192,192));
    	resetButton.setBackground(new Color(192,192,192));
    	ForgetPassword.setBackground(new Color(205,255,255));
    }
    // Adding the component to the Frame.
    public void addComponentsToContainer() {
        container.add(userLabel);
        container.add(passwordLabel);
        container.add(userTextField);
        container.add(passwordField);
        container.add(showPassword);
        container.add(loginButton);
        container.add(resetButton);
        container.add(ForgetPassword);
    }
    // Implementing the Action Listener Event.
    public void addActionEvent() {
        loginButton.addActionListener(this);
        resetButton.addActionListener(this);
        showPassword.addActionListener(this);
        ForgetPassword.addActionListener(this);
    }
    // Association of the different action Listeners.
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String userText;
            String pwdText;
            userText = userTextField.getText();
            //The getText () method is deprecated in JPasswordField class because it returns password as a String object which might be vulnerable for security.
            pwdText = String.valueOf(passwordField.getPassword());
            if ((userText.equalsIgnoreCase("ELHASNAOUI") && pwdText.equalsIgnoreCase("GA234405")) || (userText.equalsIgnoreCase("ELANNABE") && pwdText.equalsIgnoreCase("40464046"))){
            	// Adding the user Login to the DataBase within the exact time.
            	try {
            		DB.addUserToDataBase(userText, pwdText, Operation);
            		System.out.println("Logged in Succefully ! " );
            		var frame = new ChoixFrame();
            		frame.setVisible(true);
            		dispose();
            	}catch(Exception e3) {
            		System.out.println("Couldn't add the User data to DataBase, Verifie your Connectivity");
            	}
            } 
            // Handling the case where the user is not filling all the Login Requirements.
            else if((userText.isEmpty() || pwdText.isEmpty())) {
            	JOptionPane.showMessageDialog(this, "Please fill in the UserName and Password");
            }
            else{
                JOptionPane.showMessageDialog(this, "Invalid Username or Password");
            }
        }
        if (e.getSource() == resetButton) {
            userTextField.setText("");
            passwordField.setText("");
        }
        if (e.getSource() == showPassword) {
            if (showPassword.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('*');
            }
        }
        if(e.getSource()== ForgetPassword) {
        	var frame2 = new ForgetPassword();
        	frame2.setVisible(true);
        	dispose();
        }
    }
    // Overriding the method add to DataBase from the Interface.
}