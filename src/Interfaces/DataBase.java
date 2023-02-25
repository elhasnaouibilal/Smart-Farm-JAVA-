package Interfaces;

import java.sql.PreparedStatement;

public interface DataBase {
	public boolean addUserToDataBase(String UserName,String Password,String Operation);
	public boolean TerrainesData(String Temperature,String Hudimity, String Location, String Time,  String Temperature_Situation,String Hudmidity_Situation,String Humidity_Sol, String SH_Situation);
	public boolean AnimauxData(String Temperature, String Humidity, String Location, String Time,String TSituation, String HSituation);
}