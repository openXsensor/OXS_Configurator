package oxsc;

import java.util.ArrayList;
import java.util.List;

import gui.TabData;

public abstract class Sensor implements OXSdataController {
	
	private static final boolean DEBUG = false;
	
	private String name = "";
	
	private static List<Sensor> sensorList = new ArrayList<>();

	protected Sensor(String name) {

		this.name = name;
		sensorList.add(this);
		addOXSdata();
		updateUIoXSdataList();
		if (DEBUG) {
			System.out.println("Creation d'un objet " + this.getName());
			System.out.println(sensorList);
		}
	}

	public String getName() {
		return name;
	}
	
	public static List<Sensor> getSensorList() {
		return sensorList;
	}

	public static void removeFromList(Sensor sensor) {
		sensorList.remove(sensor);
	}

	public void updateUIoXSdataList() {
		TabData.populateSentDataFields();
	}

	public abstract void addOXSdata();

	public abstract void removeSensor();

}
