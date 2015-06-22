package oxsc;

import gui.TabData;
import processing.core.PApplet;

public class Protocol {
	
	private String name ;
	
	private static String targetDataList[][] ;

	private static String frSkyDataList[][] = new String[][] {
			{ "----------", "----------" },
			{ "DEFAULTFIELD", "DEFAULT" },          // 1
			{ "Alt", "Altitude" },                  // 2  ALT_FIRST_ID
			{ "Vspd", "Vertical Speed" },           // 3  VARIO_FIRST_ID
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

	private static String multiplexDataList[][] = new String[][] {
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
			
			updateUItargetDataList();
			
			// TODO OXSdata.updateList(); 
			
			return tempProt;
		}
		return MainP.protocol ;

	}

	private Protocol(String name) {

		this.name = name;

		if (this.name == "FrSky") {
			targetDataList = frSkyDataList.clone();
		} else if (this.name == "Multiplex") {
			targetDataList = multiplexDataList.clone();
		}

		
		PApplet.println();
		PApplet.println("Création d'un protocole " + this.getName() + ":");
		//updateUItargetDataList() ; // TODO why not working ?
	}
	  
	public String getName() {
		return name;
	}

	// public int getTargetDataListLength() { return targetDataList.length ; }
	
	public static String[][] getDataList() {
		return targetDataList;
	}

	public static void updateUItargetDataList() { // TODO
		for (int i = 0; i < targetDataList.length; i++) {
			PApplet.print(targetDataList[i][0] + " - ");
		}

		PApplet.println();

		TabData.populateTargetDataFields();
	}

}
