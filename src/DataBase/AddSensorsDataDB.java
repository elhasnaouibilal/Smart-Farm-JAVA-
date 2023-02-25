package DataBase;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import Interfaces.DataBase;
public class AddSensorsDataDB implements DataBase{
	final String DB_URL;
	private String USERNAME;
	private String PASSWORD;
	
	// Defining the constructor to Connect to Localhost DataBase.
    public AddSensorsDataDB() {
    	this.DB_URL = "jdbc:mysql://localhost/java_project?serverTimezone=UTC";
        this.USERNAME = "root";
        this.PASSWORD = "";
    }
    
    // Generatting Setter & Getters for the attributs.
	public String getDB_URL() {
		return DB_URL;
	}
	
	public String getUSERNAME() {
		return USERNAME;
	}
	public String getPASSWORD() {
		return PASSWORD;
	}
	
	public void setUSERNAME(String uSERNAME) {
		USERNAME = uSERNAME;
	}
	public void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}
	// Méthos AddTHTerrainsSensorsData pour ajouter les donneées du terrains agricoles au tableau du base donneés.

	public boolean addUserToDataBase(String UserName, String Password, String Operation) {
        try {
        	Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/java_project?serverTimezone=UTC","root","");
        	System.out.println("Connected Succefully");
            String sql = "INSERT INTO users_logins (USERNAME , PASSWORD, TIME, OPERATION)" + "VALUES (?,?,?,?)";
            // To generate the exact Time while logging into the application.
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            PreparedStatement Stmt = conn.prepareStatement(sql);
            Stmt.setString(1,UserName);
            Stmt.setString(2,Password);
            Stmt.setString(3,dtf.format(now));
            Stmt.setString(4,Operation);
            // Execute the Query.
            try {
                Stmt.execute();
                conn.close();
                return true;
            }catch(SQLException ex) {
            	System.out.println("Couldn't Insert data into the DataBase, Please verifie your Server !");
            }
            
        }catch (Exception e){
            System.out.println("An Error Occured while trying to Connect to DataBase, Please try Again !");
            e.printStackTrace();
        }
		return false;
	}
	@Override
	public boolean TerrainesData(String Temperature, String Humidity, String Location, String Time,
			String Temperature_Situation, String Hudmidity_Situation, String Humidity_Sol, String SH_Situation) {
        try {
        	Connection conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
        	//System.out.println("Connected Succefully");
            String sql = "INSERT INTO terrains_data (Temperature , Humidity, Time, Location, TSituation, HSituation,Humidity_Sol, SH_Situation)" + "VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement Stmt = conn.prepareStatement(sql);
            Stmt.setString(1,Temperature);
            Stmt.setString(2,Humidity);
            Stmt.setString(3,Location);
            Stmt.setString(4,Time);
            Stmt.setString(5,Temperature_Situation);
            Stmt.setString(6,Hudmidity_Situation);
            Stmt.setString(7,Humidity_Sol);
            Stmt.setString(8,SH_Situation);
            try {
                Stmt.execute();
                conn.close();
                return true;
            }catch(Exception e) {
                System.out.println("An Error Occured while trying to Connect to DataBase, Please try Again !");
                e.printStackTrace();
            }
        }catch (Exception e){
            System.out.println("An Error Occured while trying to insert data to DataBase, Please try Again !");
            e.printStackTrace();
        }
        return false;
	}
	@Override
	public boolean AnimauxData(String Temperature, String Humidity, String Location, String Time,String TSituation, String HSituation) {

        try {
        	Connection conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
        	System.out.println("Connected Succefully");
            String sql = "INSERT INTO animaux_data (Temperature , Humidity, Time, Location, TSituation, HSituation)" + "VALUES (?,?,?,?,?,?)";
            PreparedStatement Stmt = conn.prepareStatement(sql);
            Stmt.setString(1,Temperature);
            Stmt.setString(2,Humidity);
            Stmt.setString(3,Location);
            Stmt.setString(4,Time);
            Stmt.setString(5,TSituation);
            Stmt.setString(6,HSituation);
            Stmt.execute();
            conn.close();
            return true;
        }catch (Exception e){
            System.out.println("An Error Occured while trying to insert data to DataBase, Please try Again !");
            e.printStackTrace();
        }
        return false;
	}
}