package oxsc;

import gui.TabData;

public class PPM extends Sensor {

	// private int parameters ;

	public PPM(String name) {
		super(name);
	}
	
	public void addOXSdata() {
		new OXSdata("PPM", "PPM value", this.getName());
	}

	public void removeSensor() {
		OXSdata.removeFromList(this);
		TabData.resetSentDataFields();
		//updateUIoXSdataList();
		Sensor.getSensorList().remove(this);
	}

}