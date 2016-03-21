// not used yet TODO z
package oxsc;

import java.util.ArrayList;

public class TargetData {

	private String name;
	private String displayName;
	// private String sensorType ;
	private static ArrayList<TargetData> targetDataList = new ArrayList<TargetData>();

	public TargetData(String name, String displayName/* , String sensorType */) {
		this.name = name;
		this.displayName = displayName;
		// this.sensorType = sensorType ;
		// println("Creation d'un objet TargetData: " + this.name + " - " +
		// this.displayName) ;
	}

	public String getName() {
		return name;
	}

	public String getDisplayName() {
		return displayName;
	}

	// public String getSensorType() { return sensorType ; }

	public static ArrayList<TargetData> getList() {
		return targetDataList;
	}

	public static void addToList(TargetData newData) {
		targetDataList.add(newData);
	}

	/*
	 * public static void removeFromList(Sensor sensor) { //  for (int i =
	 * OXSdataList.size()-1 ; i >= 0 ; i-- ) { if (
	 * OXSdataList.get(i).sensorType == sensor.getName() ) OXSdataList.remove(
	 * OXSdataList.get(i) ) ; } }
	 */

	public static void clearList() {
		targetDataList.clear();
	}

}
