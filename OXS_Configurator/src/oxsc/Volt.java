package oxsc;

import gui.TabData;

public class Volt extends Sensor {

	// private int parameters ;

	public Volt(String name) {
		super(name);
	}

	public void addOXSdata() {
		String name = this.getName();
		new OXSdata(name.toUpperCase(), name.toUpperCase().charAt(0)
				+ name.substring(1, 4) + " " + name.substring(4), name);
	}

	public void removeSensor() {
		Sensor.removeFromList(this);
		OXSdata.removeFromList(this);
		TabData.resetSentDataFields();
		// updateUIoXSdataList();
	}

}