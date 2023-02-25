package Windows;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import com.fazecast.jSerialComm.SerialPort;
import Charts.GaugeChart;
import DataBase.AddSensorsDataDB;
import Interfaces.DataBase;
import Windows.ChoixFrame;

public class TerrainsFrame extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private String TSituation = "NORMAL TEMPERATURE";
	private String HSituation = "NORMAL HUMIDITY";
	private String HSOLSituation = "NORMAL SOIL HUMIDITY";
	static  SerialPort ChoosenPort;
	private JComboBox<String> portList;
	private JPanel topPanel;
	static SerialPort serial_port;
	private String CurrentSol = "Current Sol Humidity : ";
    JButton Irrigate1h = new JButton();
    JButton Irrigate2h = new JButton();
    JButton Back = new JButton();
    JButton ConnectButton = new JButton();
	GaugeChart gaugeChart1 = new GaugeChart();
	GaugeChart gaugeChart2 = new GaugeChart();
	GaugeChart gaugeChart3 = new GaugeChart();
	JPanel contentPane1 = new JPanel();
	JPanel contentPane2 = new JPanel();
	JLabel label = new JLabel((CurrentSol));
	AddSensorsDataDB DB = new AddSensorsDataDB();
	
	// Defining the Constructor.
	public TerrainsFrame(){
		SizeLocation();
		DefineComponent();
		setUpPort();
		Addcomponent();
		setupFontAndBackGround();
		AddActionEvent();
	}
	// Defining all the Buttons,Labels...
	public void DefineComponent() {
		Irrigate1h.setText(" Set Humidity to 90");
		Irrigate2h.setText(" Set Humidity to 50");
		ConnectButton.setText("Connect");
		Back.setText("Back");
		topPanel = new JPanel();
		portList = new JComboBox<>();
	}
	// Defining the Size and location of the frame.
	public void SizeLocation() {
		setTitle("Smart - Ferme : Humidité du Sol des Terrains Agricoles");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(850,400);
		setLocationRelativeTo(null);
	}
	// Setting up the port to read data from.
	public void setUpPort() {
		SerialPort[] portNames = SerialPort.getCommPorts();
        for(SerialPort portName : portNames) {
        	portList.addItem(portName.getSystemPortName());
        }
	}
	// Adding data to the Frame.
	public void Addcomponent() {
		topPanel.add(label);
		contentPane1.add(gaugeChart1);
		contentPane1.add(gaugeChart2);
		contentPane1.add(gaugeChart3);
		contentPane2.add(portList);
		contentPane2.add(ConnectButton);
		contentPane2.add(Irrigate1h);
		contentPane2.add(Irrigate2h);
		contentPane2.add(Back);
		contentPane2.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane1.setBorder(new EmptyBorder(5, 5, 5, 5));
		add(topPanel,BorderLayout.NORTH);
		add(contentPane1, BorderLayout.CENTER);
		add(contentPane2,BorderLayout.SOUTH);
	}
	// Adding the action listener to the different Buttons.
	public void AddActionEvent() {
		Irrigate1h.addActionListener(this);
		Irrigate2h.addActionListener(this);
		Back.addActionListener(this);
		ConnectButton.addActionListener(this);
	}
	// Setting up the font and BackGround color of the Buttons,Labels...
	public void setupFontAndBackGround() {
		portList.setBackground(new Color(192,192,192));
		label.setFont(new Font("Times new Roman", Font.BOLD,16));
		topPanel.setBackground(new Color(0,169,120));
		contentPane2.setBackground(new Color(192,192,192));
		Irrigate1h.setFont(new Font("Times New Roman",Font.BOLD,15));
		Irrigate1h.setBackground(new Color(192,192,192));
		Irrigate2h.setFont(new Font("Times New Roman",Font.BOLD,15));
		Irrigate2h.setBackground(new Color(192,192,192));
		Back.setFont(new Font("Times New Roman",Font.BOLD,15));
		Back.setBackground(new Color(192,192,192));
		Back.setFont(new Font("Times New Roman",Font.BOLD,15));
		ConnectButton.setBackground(new Color(192,192,192));
		gaugeChart1.setColor1(new Color(50, 192, 98));
		gaugeChart2.setColor1(new Color(128, 128, 128));
		gaugeChart3.setColor1(new Color(128, 128, 128));
		portList.setFont(new Font("Times New Roman",Font.BOLD,15));
		
	}
	// Overriding the actionPerformed method we implement from the ActionListner Interface.
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==Irrigate2h) {
			SerialPort[] getPort = SerialPort.getCommPorts();
			for(SerialPort port : getPort) {
				serial_port = SerialPort.getCommPort(port.getSystemPortName());
				if(serial_port.openPort()) {
					System.out.println("Succefully Connected to port");
				}
			}
			// Setting the BaurderRate of the Port.
			serial_port.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER,0,0);			
			OutputStream output_stream = serial_port.getOutputStream();
			DataOutputStream data = new DataOutputStream(output_stream);
			// Sending a command on the port to start the irrigation two one Hour.
			try {
				data.write(1);
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			} catch (IOException e1) {
				System.out.println("Couldn't connect to port to send command, Please try your connectivity ! ");
				// TODO Auto-generated catch block
				//e1.printStackTrace();
			}
        	label.setText((CurrentSol)+ "90 %");
    		gaugeChart1.setValueWithAnimation(90);
    		gaugeChart2.setValueWithAnimation(90);
    		gaugeChart3.setValueWithAnimation(90);
		}
		if(e.getSource()==Irrigate1h) {
			SerialPort[] getPort = SerialPort.getCommPorts();
			for(SerialPort port : getPort) {
				serial_port = SerialPort.getCommPort(port.getSystemPortName());
				if(serial_port.openPort()) {
					System.out.println("Succefully Connected to port");
				}
			}
			// Setting the BaurderRate of the Port.
			serial_port.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER,0,0);			
			OutputStream output_stream = serial_port.getOutputStream();
			DataOutputStream data = new DataOutputStream(output_stream);
			// Sending a command on the port to start the irrigation two one Hour.
			try {
				data.write(1);
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			} catch (IOException e1) {
				System.out.println("Couldn't connect to port to send command, Please try your connectivity ! ");
			}
        	label.setText((CurrentSol)+ "90 %");
    		gaugeChart1.setValueWithAnimation(90);
    		gaugeChart2.setValueWithAnimation(90);
    		gaugeChart3.setValueWithAnimation(90);
		}
		if(e.getSource()==Back) {
	        ChoixFrame window = new ChoixFrame();	
	        window.setVisible(true);
	        setVisible(false);
		}
		if(e.getSource()==ConnectButton) {
            if(ConnectButton.getText().equals("Connect")){
            	try {
                    // Attempt to connect to the serial port
                    ChoosenPort = SerialPort.getCommPort(portList.getSelectedItem().toString());
                    ChoosenPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER,0,0);
                    //ChoosenPort.setComPortTimeouts(SerialPort.LISTENING_EVENT_DATA_AVAILABLE,0,0);
                    if(ChoosenPort.openPort()){
                    	ConnectButton.setText("Disconnect");
                        portList.setEnabled(false);
                    }
                    // Create a new thread that listens for incoming text and populates the graph
                    Thread thread = new Thread(){
                        @Override
                        public void run() {
                        	Scanner scanner = new Scanner(ChoosenPort.getInputStream());
                        	while(scanner.hasNextLine()) {
                                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                                LocalDateTime now = LocalDateTime.now();
                        		String Temperature = scanner.next();
                        		gaugeChart1.setValueWithAnimation(Integer.parseInt(Temperature));
                        		String Humidity = scanner.next();
                        		gaugeChart2.setValueWithAnimation(Integer.parseInt(Humidity));
                        		String Sol_Humidity = scanner.next();
                        		gaugeChart3.setValueWithAnimation(Integer.parseInt(Sol_Humidity));
                        		repaint();
                        		if(Integer.parseInt(Temperature)>37) {
                        			TSituation = "HIGH TEMPERATURE ";
                        		}
                        		if(Integer.parseInt(Humidity)>76) {
                        			HSituation = "HIGH HUMIDITY ";
                        		}
                        		if(Integer.parseInt(Temperature)>75) {
                        			HSOLSituation = "HIGH SOIL HUMIDITY ";
                        		}
                        		label.setText("   "+"CURRENT TEMPERATURE = "+ Temperature +" °C " +"       "+"CURRENT HUMIDITY = "+ Humidity + " % "+"    " + "CURRENT SOIL HUMIDITY = " + Sol_Humidity + " % ");
                        		DB.TerrainesData("Temperature = " + Temperature + " °C ","HUMIDITY = " + Humidity + " % ", "TIME = " + dtf.format(now) + " GMT", "Terraine 1", TSituation, HSituation,"SOIL HUMIDITY = " + Sol_Humidity + " % ", HSOLSituation);
                        	}
                        	scanner.close();
                        }
                    };
                    thread.start();
            	}catch(NoSuchElementException Ex) {
            		System.out.println("Can't Connect to MicroControler, Please verifie the connectivity of your Arduino");
            	}
            }else{
                // Disconnect from the serial port
                ChoosenPort.closePort();
                portList.setEnabled(true);
                ConnectButton.setText("Connect");
            }
		}
	}
}