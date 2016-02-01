package oxsc;

import java.util.ArrayList;

import gui.TabData;

public abstract class Sensor implements OXSdataController {
	
	private static ArrayList<Sensor> sensorList = new ArrayList<Sensor>();

	private String name = "";

	protected Sensor(String name) {

		this.name = name;
		addOXSdata();
		updateUIoXSdataList();
		sensorList.add(this);
		System.out.println("Creation d'un objet " + this.getName());
		System.out.println(sensorList);
	}

	public String getName() {
		return name;
	}
	
	public static ArrayList<Sensor> getSensorList() {
		return sensorList;
	}

	public void updateUIoXSdataList() {
		TabData.populateSentDataFields();
	}

	public abstract void addOXSdata();

	public abstract void removeSensor();

}
