package oxsc;

import gui.TabData;
import gui.TabVoltage;

public class Protocol {
	
	private static final boolean DEBUG = false;

	private String name;
	
	private static String[][] targetDataList;
	
	// ------------------------- HUB protocol data list array --------------------------
		/*private static String hubDataList[][] = new String[][] {
			{ "----------", "----------" },
			{ "DEFAULTFIELD", "DEFAULT" },
			{ "Vspd", "Vertical Speed" },            // FRSKY_USERDATA_VERT_SPEED
			{ "Curr", "Current" },                   // FRSKY_USERDATA_CURRENT
			{ "Vfas", "Vfas" },                      // FRSKY_USERDATA_VFAS_NEW
			{ "T1", "Temperature 1" },               // FRSKY_USERDATA_TEMP1
			{ "T2", "Temperature 2" },               // FRSKY_USERDATA_TEMP2
			{ "Rpm", "RPM" },                        // FRSKY_USERDATA_RPM
			{ "Fuel", "Fuel" },                      // FRSKY_USERDATA_FUEL
			{ "AccX", "Acceleration X" },            // FRSKY_USERDATA_ACC_X
			{ "AccY", "Acceleration Y" },            // FRSKY_USERDATA_ACC_Y
			{ "AccZ", "Acceleration Z" }             // FRSKY_USERDATA_ACC_Z
		} ;*/

	// -------------------- SMART PORT protocol data list array --------------------
	private static String[][] frSkyDataList = new String[][] {
			{ "----------", "----------" },
			{ "DEFAULTFIELD", "DEFAULT" },          // 1
			{ "Alt", "Altitude" },                  // 2  ALT_FIRST_ID
			{ "VSpd", "Vertical Speed" },           // 3  VARIO_FIRST_ID
			{ "Curr", "Current" },                  // 4  CURR_FIRST_ID
			{ "Vfas", "Vfas" },                     // 5  VFAS_FIRST_ID
			{ "T1", "Temperature 1" },              // 6  T1_FIRST_ID
			{ "T2", "Temperature 2" },              // 7  T2_FIRST_ID
			{ "Rpm", "RPM" },                       // 8  RPM_FIRST_ID
			{ "Fuel", "Fuel" },                     // 9  FUEL_FIRST_ID
			{ "AccX", "Acceleration X" },           // 10 ACCX_FIRST_ID
			{ "AccY", "Acceleration Y" },           // 11 ACCY_FIRST_ID
			{ "AccZ", "Acceleration Z" },           // 12 ACCZ_FIRST_ID
			{ "A3", "A3 (S.Port only)" },           // 13 A3_FIRST_ID
			{ "A4", "A4 (S.Port only)" },           // 14 A4_FIRST_ID
			{ "ASpd", "Air Speed (S.Port only)" }   // 15 AIR_SPEED_FIRST_ID
	};

	// -------------------- Multiplex protocol data list array --------------------
	private static String[][] multiplexDataList = new String[][] {
			{ "----------", "----------" },
			{ "2", "2" },
			{ "3", "3" },
			{ "4", "4" },
			{ "5", "5" },
			{ "6", "6" },
			{ "7", "7" },
			{ "8", "8" },
			{ "9", "9" },
			{ "10", "10" },
			{ "11", "11" },
			{ "12", "12" },
			{ "13", "13" },
			{ "14", "14" },
			{ "15", "15" }
	};

	public static Protocol createProtocol(String name) {
		if (MainP.protocol == null || !MainP.protocol.getName().equals(name)) {
			Protocol tempProt = new Protocol(name);
			return tempProt;
		}
		return MainP.protocol;
	}

	private Protocol(String name) {

		this.name = name;

		if (this.name.equals("FrSky")) {
			targetDataList = frSkyDataList.clone();
		} else if (this.name.equals("Multiplex")) {
			targetDataList = multiplexDataList.clone();
			TabVoltage.getCellsTgl().setValue(0);
		}

		if (DEBUG) {
			System.out.println();
			System.out.println("Création d'un protocole " + this.getName() + ":");
		}
		updateUItargetDataList();
	}
	  
	public String getName() {
		return name;
	}

	// public int getTargetDataListLength() { return targetDataList.length ; }
	
	public static String[][] getDataList() {
		return targetDataList;
	}

	public static String getDataCode(String dataName) {
		for (int i = 0; i < targetDataList.length; i++) {
			if (targetDataList[i][1].equals(dataName)) {
				return targetDataList[i][0];
			}
		}
		return null;
	}

	public static void updateUItargetDataList() { // TODO later
		for (int i = 0; i < targetDataList.length; i++) {
			if (DEBUG) {
				System.out.print(targetDataList[i][0] + " - ");
			}
		}

		if (DEBUG) {
			System.out.println();
		}
		TabData.populateTargetDataFields();
		TabData.resetTargetDataFields();
	}

}
