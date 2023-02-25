package Windows;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import com.fazecast.jSerialComm.SerialPort;

import Charts.GaugeChart;
import DataBase.AddSensorsDataDB;

public class AnimauxFrame extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private SerialPort ChoosenPort;
    private int x = 0;
    private int lastTemp = 1;
    private int lastRH = 1;
    private String currentRH = "Current Humidity : ";
    private String currentTemp = "Current Temperature : ";
    private JButton connectButton;
    private JLabel label1 = new JLabel();
    private JLabel label2 = new JLabel();
    private JLabel label3 = new JLabel();
    private JButton BackButton;
    private JComboBox<String> portList;
    private JPanel topPanel;
    private XYSeries series;
    private XYSeries series1;
    private JPanel panel = new JPanel();
	GaugeChart gaugeChart2 = new GaugeChart();
	GaugeChart gaugeChart1 = new GaugeChart();
	
	// Defining the Constructor.
	public AnimauxFrame() {
        XYSeries series = new XYSeries("Temperature Readings °C");
        // Series1 used to graph the Humidity Readings from the Sensors.
        XYSeries series1 = new XYSeries("Humidity Readings %");
		DefineComponent();
		setSizeLocation();
		setFontIcon();
		setUpPort();
		AddComponent();
		addActionEvent();
	}
	// Defining the Size, Location and the layout of the Frame.
	public void setSizeLocation() {
        setTitle("Sensor Graph GUI ");
        setSize(900,700);
        // Setting the Size to be fix.
        setResizable(false);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	// Defining the Fonts and all the customizable Fonts,Icons... on the frame.
	public void setFontIcon() {
        connectButton.setBackground(new Color(192,192,192));
        gaugeChart1.setColor1(new Color(128,128,182));
        gaugeChart2.setColor1(new Color(0,0,0));
        portList.setBackground(new Color(192,192,192));
        connectButton.setBackground(new Color(192,192,192));
        BackButton.setBackground(new Color(120,192,192));
        topPanel.setBackground(new Color(192,180,160));
        label1.setFont(new Font("Verdana",Font.ITALIC,13));
        label2.setFont(new Font("Verdana",Font.ITALIC,13));
	}
	// Defining all the Component of the Frame.
	public void DefineComponent() {
		// Defining all the label in the Frame.
        label1 = new JLabel(String.valueOf(currentTemp));
        label2 = new JLabel(String.valueOf(currentRH));
        label3 = new JLabel("       ");
        // Defining all the Buttons on the Frame
        connectButton = new JButton("Connect");
        BackButton = new JButton("BACK");
        // Defining the XYSeries used in the Frame.
        series = new XYSeries("DHT11 Readings °C ");
        series1 = new XYSeries("Humidity Readings % ");
        
        // Defining the Panels used in the Frame.
        topPanel = new JPanel();
        // Defining the PortList which contains all the Serial ports available. 
        portList = new JComboBox<>();
        
	}
	// Setting up the ports to read data from.
	public void setUpPort() {
		// This Function allows us to get all the available ports.
		SerialPort[] portNames = SerialPort.getCommPorts();
		// A for loop to add all the available ports on a List to chose from.
        for(SerialPort portName : portNames)
            portList.addItem(portName.getSystemPortName());
	}
	// Adding all the components to the Frame.
	public void AddComponent() {
		// Adding all the Compnents of the topPanel.
        topPanel.add(BackButton);
        topPanel.add(portList);
        topPanel.add(connectButton);
        topPanel.add(label3);
        topPanel.add(label1);
        topPanel.add(label2);
        // Setting up the topPanel on the NORTH of the frame wich means on the top.
        add(topPanel,BorderLayout.NORTH);
        // Defining the two Series used in the Graph.
        // Series used to graph the Temperature Readings from the Sensors.
        // Adding the Temperature and Humidity series to the graph.
        XYSeriesCollection dataset = new XYSeriesCollection(series);
        dataset.addSeries(series1);
        // Adding the two Gauge Charts to the Panel in the SOUTH (en bass).
        panel.add(gaugeChart2);
        panel.add(gaugeChart1);
        add(panel,BorderLayout.SOUTH);
        // Defining the Graph to plot the Temperature and the Humidity , with time in seconds.
        JFreeChart chart = ChartFactory.createXYLineChart("Temperature Readings", "Time (seconds)","DHT11_Readings",dataset);
        // Adding the chart in the CENTER.
        add(new ChartPanel(chart),BorderLayout.CENTER);
	}
	// Defining function AddActionEvent to setUp all the Buttons.
    public void addActionEvent() {
    	// Because we have implements the interface AddActionListener we give the argument this only.
    	connectButton.addActionListener(this);
    	BackButton.addActionListener(this);
    }
    // Overriding the actionPerformed method and adding all the actions Events.
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==connectButton) {
			if(connectButton.getText().equals("Connect")) {
	        	try {
	        		 ChoosenPort = SerialPort.getCommPort(portList.getSelectedItem().toString());
	        		 ChoosenPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER,0,0);
	        		 //ChoosenPort.setComPortParameters(9600, 8, 1, 0); 
	        		 //ChoosenPort.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0); 
	                 if(ChoosenPort.openPort()){
	                         connectButton.setText("Disconnect");
	                         portList.setEnabled(false);
	                 }
	                 Thread thread = new Thread(){
	                     @Override
	                     public void run() {
	                         Scanner scanner = new Scanner(ChoosenPort.getInputStream());
	                         while(scanner.hasNextLine()){
	                             try {
	                                 // Scanning the temperature DATA
	                                 String Temperature_Situation = "Normal Temperature";
	                                 String Humidity_Situation = "Normal Humidity";
	                                 
	                                 String line = scanner.nextLine();
	                                 int number = Integer.parseInt(line);
	                                 series.add(x,number);
	                         		 gaugeChart2.setValueWithAnimation(number);
	                                 repaint();
	                                 
	                                 String line2 = scanner.nextLine();
	                                 int number1 = Integer.parseInt(line2);
	                                 series1.add(x++,number1);
	                                 
	                                 String line3 = scanner.nextLine();
	                                 repaint();
	                                 lastTemp = number;
	                                 if(lastTemp>37) {
	                                	 Temperature_Situation = "High Temperature"; 
	                                 }
	                                 label1.setText((currentTemp)+String.valueOf(Integer.parseInt(String.valueOf(lastTemp)))+" °C");
	                                 // Scanning the Humidity DATA
	                                 gaugeChart1.setValueWithAnimation(number1);
	                                 
	                                 lastRH = number1;
	                                 //String line3 = scanner.nextLine();
	                                 if(lastRH>70) { 
	                                	 Humidity_Situation = "High Humidity"; 
	                                 }
	                                 label2.setText((currentRH) + String.valueOf(Integer.parseInt(String.valueOf(lastRH)))+" %");
	                                 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
	                                 LocalDateTime now = LocalDateTime.now();
	                                 var connectDB = new AddSensorsDataDB();
	                                 //connectDB.(Integer.toString(lastTemp), Integer.toString(lastRH), dtf.format(now), "Terrain Agricole",Temperature_Situation, Humidity_Situation);
	                                 connectDB.AnimauxData("Temperature = "+Integer.toString(lastTemp)+" °C","Humidity = "+ Integer.toString(lastRH)+" %","Time = "+ dtf.format(now)+" GMT", 
	                                		 "Terrain Agricole 1", "The temperature is a : "+Temperature_Situation,"The humidity is a : "+ Humidity_Situation);
	                             }catch (Exception e){
	                             	System.out.println("An Error Occured While trying to Read Data from Sensors, Please verifie your sensors Connection");
	                             }
	                         }
	                         scanner.close();
	                     }
	                 };
	                 thread.start();
	        	}catch (Exception e1){
	        		System.out.println("An Error Occured while trying to Read data from Sensors, Please verifie your Sensors Connection");
	        	}
			}else {
	            ChoosenPort.closePort();
	            portList.setEnabled(true);
	            connectButton.setText("Connect");
			}
        }
		if(e.getSource()==BackButton) {
			var frame = new ChoixFrame();
			frame.setVisible(true);
			dispose();
		}
	}	
}