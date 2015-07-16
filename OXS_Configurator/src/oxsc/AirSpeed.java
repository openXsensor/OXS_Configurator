package oxsc;

import gui.TabData;
import gui.TabVario;
import processing.core.PApplet;
import controlP5.ControlP5;

public class AirSpeed extends Sensor {

	// private int parameters;

	public void addOXSdata() {
		new OXSdata("AIR_SPEED", "Air Speed", this.getName(), null);
		if (MainP.vario != null) { // TODO better
			new OXSdata("PRANDTL_DTE", "Prandtl dTE", "varAspeed", null);
			new OXSdata("PRANDTL_COMPENSATION", "Prandtl Compensation",
					"varAspeed", null);

			TabVario.getvSpeed1Ddl().addItem(" V1 + A.Speed", 2);
			TabVario.getvSpeed2Ddl().addItem(" V1 + A.Speed", 2);
		}
	}

	public void removeSensor() {
		// if ( vario == null ) { // TODO better
		TabVario.getvSpeed1Ddl().removeItem(" V1 + A.Speed");
		TabVario.getvSpeed2Ddl().removeItem(" V1 + A.Speed");

		OXSdata.removeFromList(this);
		OXSdata.removeFromList("varAspeed");
		TabData.resetSentDataFields();
		//TabData.resetSentDataFields("varAspeed");
		//updateUIoXSdataList();
		Sensor.getSensorList().remove(this);
	}

	public AirSpeed(PApplet p, ControlP5 cp5, String name) {
		super(p, cp5, name);
	}
}