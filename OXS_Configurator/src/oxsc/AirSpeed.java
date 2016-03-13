package oxsc;

import gui.TabData;
import gui.TabVario;

public class AirSpeed extends Sensor {

	// private int parameters;
	
	public AirSpeed(String name) {
		super(name);
		VarAspeed.createSensor();
	}

	public void addOXSdata() {
		new OXSdata("AIR_SPEED", "Air Speed", this.getName(), "Air Speed (S.Port only)");
		if (MainP.vario != null) {
			TabVario.addToVspeedDdls(" V1 + A.Speed", 2);
			TabVario.getvSpeed2Ddl().setValue(2);
		}
	}

	public void removeSensor() { // TODO z better vSpeed choice
		TabVario.removeFromVspeedDdls(" V1 + A.Speed"); 
		TabVario.resetVspeedDdls();

		OXSdata.removeFromList(this);
		//OXSdata.removeFromList("varAspeed");
		TabData.resetSentDataFields();
		//TabData.resetSentDataFields("varAspeed");
		//updateUIoXSdataList();
		Sensor.getSensorList().remove(this);
		VarAspeed.deleteSensor();
	}

}