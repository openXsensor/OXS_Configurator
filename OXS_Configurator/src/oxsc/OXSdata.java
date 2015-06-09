package oxsc;

import java.util.ArrayList;

import processing.core.PApplet;

public class OXSdata {

	private String name;
	private String displayName;
	private String sensorType;
	private static ArrayList<OXSdata> OXSdataList = new ArrayList<OXSdata>();

	public OXSdata(String name, String displayName, String sensorType) {
		this.name = name;
		this.displayName = displayName;
		this.sensorType = sensorType;
		PApplet.println("Creation d'un objet OXSdata: " + this.name + " - "
				+ this.displayName);
		OXSdataList.add(this);
	}

	public String getName() {
		return name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getSensorType() {
		return sensorType;
	}

	public static ArrayList<OXSdata> getList() {
		return OXSdataList;
	}

	public static void addToList(OXSdata newData) {
		OXSdataList.add(newData);
	}

	public static boolean isInList(String displayNameTest) {
		PApplet.println("(isInList) displayName � tester: " + displayNameTest);
		for (int i = OXSdataList.size() - 1; i >= 0; i--) {
			if (OXSdataList.get(i).displayName == displayNameTest)
				return true;
		}
		return false;
	}

	public static void removeFromList(Sensor sensor) { // TODO needed ?
														// ??
		for (int i = OXSdataList.size() - 1; i >= 0; i--) {
			// println( sensor.getName() + " for n°: " + i ) ;
			if (OXSdataList.get(i).sensorType == sensor.getName())
				OXSdataList.remove(OXSdataList.get(i));
		}
	}

	public static void removeFromList(String sensorType) {
		for (int i = OXSdataList.size() - 1; i >= 0; i--) {
			// println( sensorType + " for n°: " + i ) ;
			if (OXSdataList.get(i).sensorType == sensorType)
				OXSdataList.remove(OXSdataList.get(i));
		}
	}

}
