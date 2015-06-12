package oxsc;

import java.util.ArrayList;

import gui.TabData;
import processing.core.PApplet;
import controlP5.ControlP5;

public abstract class Sensor implements OXSdataController {

	protected final ControlP5 cp5;
	protected final PApplet p;
	
	private static ArrayList<Sensor> sensorList = new ArrayList<Sensor>();

	private String name = "";

	protected Sensor(PApplet p, ControlP5 cp5, String name) {

		this.cp5 = cp5;
		this.p = p;

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
