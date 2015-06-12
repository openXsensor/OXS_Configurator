package oxsc;

import gui.TabData;
import processing.core.PApplet;
import controlP5.ControlP5;

public class Current extends Sensor {
	
	//private int parameters;

	public void addOXSdata() {
		new OXSdata("CURRENTMA", "Current (mA)", this.getName(), null);
		new OXSdata("MILLIAH", "Consumption (mAh)", this.getName(), null);
	}

	public void removeSensor() {
		TabData.resetSentDataFields(this.getName());
		OXSdata.removeFromList(this);
		updateUIoXSdataList();
		Sensor.getSensorList().remove(this);
	}

	public Current(PApplet p, ControlP5 cp5, String name) {
		super(p, cp5, name);
	}
}
