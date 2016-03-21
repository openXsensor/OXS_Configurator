package oxsc;

import gui.TabData;

public class Current extends Sensor {
	
	//private int parameters;
	
	public Current(String name) {
		super(name);
	}

	public void addOXSdata() {
		new OXSdata("CURRENTMA", "Current (mA)", this.getName(), "Current");
		new OXSdata("MILLIAH", "Consumption (mAh)", this.getName());
	}

	public void removeSensor() {
		OXSdata.removeFromList(this);
		TabData.resetSentDataFields();
		//updateUIoXSdataList();
		Sensor.getSensorList().remove(this);
	}

}
