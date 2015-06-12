package oxsc;

import gui.TabData;
import processing.core.PApplet;
import controlP5.ControlP5;

public class Volt extends Sensor {

	// private int parameters ;

	public void addOXSdata() {
		String name = this.getName();
		new OXSdata(name.toUpperCase(), name.toUpperCase().charAt(0)
				+ name.substring(1, 4) + " " + name.substring(4), name, null);
	}

	public void removeSensor() {
		TabData.resetSentDataFields(this.getName());
		OXSdata.removeFromList(this);
		updateUIoXSdataList();
		Sensor.getSensorList().remove(this);
	}

	public Volt(PApplet p, ControlP5 cp5, String name) {
		super(p, cp5, name);
	}
}