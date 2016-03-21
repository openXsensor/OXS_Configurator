package oxsc;

import java.util.ArrayList;
import java.util.List;

import gui.TabData;

public abstract class Sensor implements OXSdataController {
	
	private static final boolean DEBUG = true;
	
	private String name = "";
	
	private static List<Sensor> sensorList = new ArrayList<>();

	protected Sensor(String name) {

		this.name = name;
		addOXSdata();
		updateUIoXSdataList();
		sensorList.add(this);
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

	public void updateUIoXSdataList() {
		TabData.populateSentDataFields();
	}

	public abstract void addOXSdata();

	public abstract void removeSensor();

}
