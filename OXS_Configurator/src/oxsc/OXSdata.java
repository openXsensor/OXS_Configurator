package oxsc;

import gui.TabData;

import java.util.ArrayList;
import java.util.List;

public class OXSdata {

	private static final boolean DEBUG = false;

	private String name;
	private String displayName;
	private String sensorType;
	private String defaultValue;
	
	private static List<OXSdata> OXSdataList = new ArrayList<>();

	public OXSdata(String name, String displayName, String sensorType, String defaultValue) {
		this.name = name;
		this.displayName = displayName;
		this.sensorType = sensorType;
		this.defaultValue = defaultValue;

		if (DEBUG) {
			System.out.println("Creation d'un objet OXSdata: " + this.name + " - " + this.displayName + " - "
					+ this.defaultValue);
		}
		OXSdataList.add(this);
	}
	
	public OXSdata(String name, String displayName, String sensorType) {
		this(name, displayName, sensorType, null);
	}

	public String getName() {
		return name;
	}
	
	public static String getName(String displayName) {
		return OXSdataList.stream().filter(d -> d.displayName.equals(displayName)).findFirst().get().getName();
	}
	
	public String getDisplayName() {
		return displayName;
	}

	public String getSensorType() {
		return sensorType;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public static List<OXSdata> getList() {
		return OXSdataList;
	}
	
	public static OXSdata getItem(int i) {
		return OXSdataList.get(i);
	}

	public static OXSdata getOXSdata(String displayName) {
		return OXSdataList.stream().filter(d -> d.displayName.equals(displayName)).findFirst().get();
	}
	
	public static void addToList(OXSdata newData) {
		OXSdataList.add(newData);
	}

	public static boolean isInList(String sensorDisplayName) {
		for (OXSdata o : OXSdataList) {
			if (o.displayName.equals(sensorDisplayName)) 
				return true;
		}
		return false;
	}

	public static void removeFromList(Sensor sensor) { // TODO needed ?
		for (int i = OXSdataList.size() - 1; i >= 0; i--) {
			if (OXSdataList.get(i).sensorType.equals(sensor.getName()))
				OXSdataList.remove(OXSdataList.get(i));
		}
		TabData.populateSentDataFields();
	}

	public static void removeFromList(String sensorType) {
		for (int i = OXSdataList.size() - 1; i >= 0; i--) {
			if (OXSdataList.get(i).sensorType.equals(sensorType))
				OXSdataList.remove(OXSdataList.get(i));
		}
		TabData.populateSentDataFields();
	}
	
	public static void updateList(){
		// TODO z
	}

}
