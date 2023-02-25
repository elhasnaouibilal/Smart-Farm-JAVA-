package Windows;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import java.sql.*;
public class StaistiquesFrame extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton GetStatistiques;
	private JButton BackButton;
	Object[][] donnees = {
			{"", "", "", "", "", "", "", ""},
			{"", "", "", "", "", "", "", ""},
			{"", "", "", "", "", "", "", ""},
			{"", "", "", "", "", "", "", ""},
			{"", "", "", "", "", "", "", ""},
			{"", "", "", "", "", "", "", ""},
			{"", "", "", "", "", "", "", ""},
			{"", "", "", "", "", "", "", ""},
			{"", "", "", "", "", "", "", ""},
			{"", "", "", "", "", "", "", ""},
			{"", "", "", "", "", "", "", ""},
			{"", "", "", "", "", "", "", ""},
			{"", "", "", "", "", "", "", ""},
			{"", "", "", "", "", "", "", ""},
			{"", "", "", "", "", "", "", ""},
			{"", "", "", "", "", "", "", ""},
			{"", "", "", "", "", "", "", ""},
			
	};
	String[] entetes = {"Temperature","Humidity","Time","Location","Temperature Situation","HumiditySituation"," Sol Humidity","Sol Humidity Situation"};
	JTable tableau = new JTable(donnees,entetes);
	public StaistiquesFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Show me Staistiques!");
		setSize(900,400);
		DefineComponent();
		AddComponents();
		SetFontBackground();
		ActionEvent();
	}
	public void DefineComponent() {
		GetStatistiques = new JButton("Get Statistiques !");
		contentPane = new JPanel();
		BackButton = new JButton("Go Back");
	}
	public void AddComponents() {
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		add(tableau.getTableHeader(),BorderLayout.NORTH);
		add(tableau,BorderLayout.CENTER);
		contentPane.add(BackButton,BorderLayout.EAST);
		contentPane.add(GetStatistiques);
		add(contentPane,BorderLayout.SOUTH);
	}
	public void SetFontBackground() {
		BackButton.setFont(new Font("Times New Roman",Font.BOLD,17));
		GetStatistiques.setFont(new Font("Times New Roman",Font.BOLD,17));
		
		GetStatistiques.setBackground(new Color(192,192,192));
		BackButton.setBackground(new Color(192,192,192));
		contentPane.setBackground(new Color(210,192,192));
		tableau.setGridColor(new Color(200,200,190));
		tableau.setSelectionBackground(new Color(190,192,192));
	}
	public void ActionEvent() {
		GetStatistiques.addActionListener(this);
		BackButton.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==GetStatistiques) {
        	try {
        		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/java_project?serverTimezone=UTC","root","");
        		//System.out.println("Connected Succefully");
        		// Make the scrolling of the DataBase INSENSITIVE, and Readable only.
        		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        		ResultSet r = stmt.executeQuery("SELECT * FROM terrains_data");
        		// Because the ResultSet r starts from the index 0, we have to implement r.next().
        		r.next();
        		int i=0;
        		while(r.next()) {
            		String Tmp = r.getString("Temperature");
            		donnees[i][0] = Tmp + " °C"; 
            		String Hum = r.getString("Humidity") + " %" ;
            		donnees[i][1] = Hum ; 
            		//String Location = r.getString("Time");
            		donnees[i][2] = r.getString("Time"); 
            		String Time = r.getString("Location");
            		donnees[i][3] =Time; 
            		String TS = r.getString("TSituation");
            		donnees[i][4] = TS;
            		String HS = r.getString("HSituation");
            		donnees[i][5]= HS;
            		String HSol = r.getString("Humidity_Sol");
            		donnees[i][6]= HSol;
            		String HSolSituation = r.getString("SH_Situation");
            		donnees[i][7]= HSolSituation;
            		repaint();
            		i++;
        		}
        	}catch(Exception e1) {
        		System.out.println("Couldn't Connect to DataBase, Please Verifie your Connectivity and try again");
        	}
		}
		if(e.getSource()==BackButton) {
        	var frame = new ChoixFrame();
        	frame.setVisible(true);
        	setVisible(false);
		}
	}
}