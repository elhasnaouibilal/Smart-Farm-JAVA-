package JUnitsTests;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import DataBase.AddSensorsDataDB;

class InsertingNullData {
	AddSensorsDataDB obj = new AddSensorsDataDB();
	@Test
	void test() {
		assertEquals(obj.AnimauxData(null, "87%", "19/10/2023", "KENITRA", "NORMAL Temperature", "HIGH Humidity"),false);
		
		assertFalse(obj.AnimauxData(null, "87%", "19/10/2023", "KENITRA", "NORMAL Temperature", "HIGH Humidity"));
		assertFalse(obj.AnimauxData(null, null, "19/10/2023", "KENITRA", "NORMAL Temperature", "HIGH Humidity"));
		assertFalse(obj.AnimauxData(null, "87%",null, "KENITRA", "NORMAL Temperature", "HIGH Humidity"));
		assertFalse(obj.AnimauxData(null, "87%", "19/10/2023", null, "NORMAL Temperature", "HIGH Humidity"));
		assertFalse(obj.AnimauxData(null, "87%", "19/10/2023", "KENITRA", null, "HIGH Humidity"));
	}

}
