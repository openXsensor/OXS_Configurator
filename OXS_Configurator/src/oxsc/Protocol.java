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

	public Protocol(String name) {

		this.name = name;

		if (DEBUG) {
			System.out.println("Création d'un protocole " + this.getName() + ":");
		}
	}

	public static void createProtocols() {
		protocolList.add(new ProtFrSkyAuto("FrSky Auto"));
		protocolList.add(new ProtFrSkySPort("FrSky S.Port"));
		protocolList.add(new ProtFrSkyHub("FrSky Hub"));
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

	public abstract void doExtraThings();

	public abstract String writeType();

	public abstract String writeData();

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
