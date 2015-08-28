package oxsc;

import java.util.ArrayList;

import gui.TabData;
import processing.core.PApplet;

public abstract class Sensor implements OXSdataController {
	
	private static ArrayList<Sensor> sensorList = new ArrayList<Sensor>();

	private String name = "";

	protected Sensor(String name) {

		this.name = name;
		addOXSdata();
		updateUIoXSdataList();
		sensorList.add(this);
		PApplet.println("Creation d'un objet " + this.getName());
		PApplet.println(sensorList);
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
