package oxsc;

import processing.core.PApplet;
import controlP5.ControlP5;

public class PPM extends Sensor {

	// private int parameters ;

	public void addOXSdata() {
		new OXSdata("PPM", "PPM value", this.getName());
	}

	public void removeSensor() {
		MainP.tabData.resetSentDataFields(this.getName());
		OXSdata.removeFromList(this);
		updateUIoXSdataList();
	}

	public PPM(PApplet p, ControlP5 cp5, String name) {
		super(p, cp5, name);
	}
}