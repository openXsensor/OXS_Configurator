package oxsc;

import processing.core.PApplet;
import controlP5.ControlP5;

public class Current extends Sensor {
	
	//private int parameters;

	public void addOXSdata() {
		new OXSdata("CURRENTMA", "Current (mA)", this.getName());
		new OXSdata("MILLIAH", "Consumption (mAh)", this.getName());
	}

	public void removeSensor() {
		MainP.tabData.resetSentDataFields(this.getName());
		OXSdata.removeFromList(this);
		updateUIoXSdataList();
	}

	public Current(PApplet p, ControlP5 cp5, String name) {
		super(p, cp5, name);
	}
}
