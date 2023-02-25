package Windows;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ChoixFrame extends JFrame implements ActionListener,MouseListener {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel PanelOptions;
	private JButton button1;
	private JButton button2;
	private JButton button3;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JLabel lblNewLabel_4;
	private JPanel TopPanel;
	private JLabel label2;
	public ChoixFrame() {
		DefineComponent();
		setSizeAndLocation();
		setFontAndImage();
		AddComponentToContainer();
		addActionEvent();
	}
	public void setSizeAndLocation() {
		setBounds(100, 100, 450, 300);
		setSize(800,340);
		setLocationRelativeTo(null);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	}
	public void setFontAndImage() {
		
		button1.setBackground(new Color(192,192,192));
		button2.setBackground(new Color(192,192,192));
		button3.setBackground(new Color(192,192,192));
		lblNewLabel_1.setFont(new Font("New Times Rpman",Font.BOLD,15));
		lblNewLabel_2.setFont(new Font("New Times Rpman",Font.BOLD,15));
		lblNewLabel_3.setFont(new Font("New Times Rpman",Font.BOLD,15));
		button1.setFont(new Font("New Times Rpman",Font.BOLD,16));
		button2.setFont(new Font("New Times Rpman",Font.BOLD,16));
		button3.setFont(new Font("New Times Rpman",Font.BOLD,16));
		lblNewLabel_1.setIcon(new ImageIcon("src/Icons/sprout.png"));
		lblNewLabel_2.setIcon(new ImageIcon("src/Icons/cow.png"));
		lblNewLabel_3.setIcon(new ImageIcon("src/Icons/analysis.png"));	
	}
	public void DefineComponent() {
		TopPanel = new JPanel();
		contentPane = new JPanel();
		PanelOptions = new JPanel();
		lblNewLabel_1 = new JLabel("Terrains Agricoles");
		lblNewLabel_2 = new JLabel("Fermes");
		lblNewLabel_3 = new JLabel("Statistiques");
		lblNewLabel_4 = new JLabel("            " );
		button1 = new JButton("TERRAINS");
		button2 = new JButton("ANIMAUX");
		button3 = new JButton("STATISQUES");
	}
	public void AddComponentToContainer() {
		add(PanelOptions);
		add(PanelOptions);
		add(contentPane);
		setTitle("Smart - Ferme");
		contentPane.add(lblNewLabel_1,BorderLayout.WEST);
		contentPane.add(button1,BorderLayout.WEST);
		contentPane.add(lblNewLabel_4,BorderLayout.CENTER);
		contentPane.add(lblNewLabel_2,BorderLayout.SOUTH);
		contentPane.add(button2,BorderLayout.SOUTH);
		contentPane.add(lblNewLabel_3,BorderLayout.NORTH);
		contentPane.add(button3,BorderLayout.NORTH);
	}
    public void addActionEvent() {
        button1.addActionListener(this);
        button1.addMouseListener(this);
        button2.addActionListener(this);
        button3.addActionListener(this);
    }
	public void setUpFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		 if (e.getSource() == button1) {
	        TerrainsFrame window = new TerrainsFrame();
	        window.setVisible(true);
            dispose();
            //setVisible(false);
		 }else if(e.getSource()==button2) {
	        var window = new AnimauxFrame();
	        window.setVisible(true);
            dispose();
            //setVisible(false);
		 }else if(e.getSource()==button3) {
    		 StaistiquesFrame frame = new StaistiquesFrame();
    		 frame.setVisible(true);
    		 //setVisible(false);
    		 dispose();
		 }	
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
		
	}
	// Affiche un message si le mouseest au dessous duButton.
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}