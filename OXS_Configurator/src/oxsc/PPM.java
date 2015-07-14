package oxsc;

import gui.TabData;
import processing.core.PApplet;
import controlP5.ControlP5;

public class PPM extends Sensor {

	// private int parameters ;

	public void addOXSdata() {
		new OXSdata("PPM", "PPM value", this.getName(), null);
	}

	public void removeSensor() {
		OXSdata.removeFromList(this);
		TabData.resetSentDataFields();
		//updateUIoXSdataList();
		Sensor.getSensorList().remove(this);
	}

	public PPM(PApplet p, ControlP5 cp5, String name) {
		super(p, cp5, name);
	}
}