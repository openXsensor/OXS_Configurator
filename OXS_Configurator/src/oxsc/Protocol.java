package oxsc;

import java.util.ArrayList;
import java.util.List;

import gui.TabData;
import gui.TabGeneralSettings;

public abstract class Protocol {

	private static final boolean DEBUG = true;

	private static List<Protocol> protocolList = new ArrayList<>();

	private String name;
	private String[][] dataList;

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

	public Protocol(String name) {

		this.name = name;

		if (DEBUG) {
			System.out.println();
			System.out.println("Création d'un protocole " + this.getName() + ":");
		}
	}

	public static void createProtocols() {
		protocolList.add(new ProtFrSky("FrSky"));
		protocolList.add(new ProtMultiplex("Multiplex"));

		TabGeneralSettings.populateProtocolDdl(protocolList.stream().map(Protocol::getName).toArray(String[]::new));
	}

	public static Protocol getProtocol(String name) {
		for (Protocol p : protocolList) {
			if (name.equals(p.getName())) {
				p.doExtraThings();
				return p;
			}
		}
		return null;
	}

	abstract void doExtraThings();

	public String getName() {
		return name;
	}

	public String[][] getDataList() {
		return dataList;
	}

	public void setDataList(String[][] dataList) {
		this.dataList = dataList;
	}

	public String getDataCode(String dataName) {
		for (String[] s : dataList) {
			if (s[1].equals(dataName)) {
				return s[0];
			}
		}
		return null;
	}

	public static void updateUItargetDataList() { // TODO later
//		for (int i = 0; i < targetDataList.length; i++) {
//			if (DEBUG) {
//				System.out.print(targetDataList[i][0] + " - ");
//			}
//		}

		if (DEBUG) {
			System.out.println();
		}
		TabData.populateTargetDataFields();
		TabData.resetTargetDataFields();
	}

}
