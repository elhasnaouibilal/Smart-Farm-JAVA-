package JUnitsTests;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import DataBase.AddSensorsDataDB;
class AddToDBTest {
	AddSensorsDataDB obj = new AddSensorsDataDB();
	@Test
	void test() {
		// Testing to add Data to dataBase.
		// The First Test Passed without any Issues.
		assertEquals(obj.AnimauxData("29 °C ", "87%", "19/10/2023", "KENITRA", "NORMAL Temperature", "HIGH Humidity"),true);
		// The data Were added succefully to the DataBase.
		
		// In this Case we are going to make sure that we can't add the data to the dataBase/
		// By giving a null Column to the method AnimauxData it can't add the data to the database.
		// Inserting null in the arguments of the method.
		
		// The Test Passed without any Issues.
		assertTrue(obj.addUserToDataBase("ELHASNAOUI", "GA234405", "Operation 2"));
		// In this Case all the tests Failed.
		assertEquals(obj.addUserToDataBase("ELHASNAOUI", null, "Operation 6"),false);
		assertEquals(obj.addUserToDataBase(null, "GA234405", "Operation 2"),false);
		assertEquals(obj.addUserToDataBase("ELHASNAOUI", null, null),false);
		assertEquals(obj.addUserToDataBase(null, null, null),false);
		
		// Testing the method TerrainsData.
		// Trying to insert a null Column to the DtaBase.
		// The test Passes Succefully and we can't insert data to the DataBase.
		assertEquals(obj.TerrainesData(null, null, null, null, null, null, null, null),false);
		assertEquals(obj.TerrainesData("Temperature = 32 °C", null, null, null, null, null, null, null),false);
		assertEquals(obj.TerrainesData("Temperature = 32 °C", " Humidity = 98%", null, null, null, null, null, null),false);
		assertEquals(obj.TerrainesData("Temperature = 32 °C", " Humidity = 98%", "19/12/2022 10:10:08", "Terrain 1", null, null, null, null),false);
		assertEquals(obj.TerrainesData("Temperature = 32 °C", " Humidity = 98%", "19/12/2022 10:10:08", "Terrain 1", "HIGH TEMPERATURE", null, null, null),false);
		assertEquals(obj.TerrainesData("Temperature = 32 °C", " Humidity = 98%", "19/12/2022 10:10:08", "Terrain 1", "HIGH TEMPERATURE","HIGH HUMIDITY" , null, null),false);
		assertEquals(obj.TerrainesData("Temperature = 32 °C", " Humidity = 98%", "19/12/2022 10:10:08", "Terrain 1", "HIGH TEMPERATURE", "HIGH HUMIDITY", "SOIL HUMIDITY = 98 %", null),false);


		// By giving all the Data Correctly we can add data to the DataBase.
		assertEquals(obj.TerrainesData("Temperature = 32 °C", " Humidity = 98%", "19/12/2022 10:10:08", "Terrain 1", "HIGH TEMPERATURE", "HIGH HUMIDITY", " Sol Humidity = 67%", "LOW SOL HUMIDITY"),true);
		
	}
}
