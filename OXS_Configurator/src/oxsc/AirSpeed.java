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
		/*if (MainP.vario != null) { // TODO better
			new OXSdata("PRANDTL_DTE", "Prandtl dTE", "varAspeed");
			new OXSdata("PRANDTL_COMPENSATION", "Prandtl Compensation",
					"varAspeed");

			TabVario.getvSpeed1Ddl().addItem(" V1 + A.Speed", 2);
			TabVario.getvSpeed2Ddl().addItem(" V1 + A.Speed", 2);
		}*/
		
	}

	public void removeSensor() {
		// if ( vario == null ) { // TODO better
		TabVario.getvSpeed1Ddl().removeItem(" V1 + A.Speed");
		TabVario.getvSpeed2Ddl().removeItem(" V1 + A.Speed");

		OXSdata.removeFromList(this);
		//OXSdata.removeFromList("varAspeed");
		TabData.resetSentDataFields();
		//TabData.resetSentDataFields("varAspeed");
		//updateUIoXSdataList();
		Sensor.getSensorList().remove(this);
		VarAspeed.deleteSensor();
	}

}