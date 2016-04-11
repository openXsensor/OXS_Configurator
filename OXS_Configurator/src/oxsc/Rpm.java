package oxsc;

import gui.TabData;

public class Rpm extends Sensor {

	// private int parameters ;
	
	public Rpm(String name) {
		super(name);
	}

	public void addOXSdata() {
		new OXSdata("RPM", "RPM", this.getName(), "DEFAULT");
	}

	public void removeSensor() {
		OXSdata.removeFromList(this);
		TabData.resetSentDataFields();
		// updateUIoXSdataList() ;
		Sensor.removeFromList(this);
	}

}