package oxsc;

import gui.TabData;
import gui.TabVario;

import java.util.ArrayList;

import processing.core.PApplet;

public class Vario extends Sensor {
	
	// private int parameters ;
	@SuppressWarnings("unused")
	private ArrayList<OXSdata> dataList = new ArrayList<OXSdata>(); // or string array ?

	public Vario(String name) {
		super(name);
		VarAspeed.createSensor();
	}

	public void addOXSdata() {
		String varioName = this.getName();

		if (varioName.equals("vario")) {
			new OXSdata("ALTIMETER", "Altitude", varioName, "Altitude");
			new OXSdata("VERTICAL_SPEED", "Vertical Speed", varioName, "Vertical Speed");
			new OXSdata("ALT_OVER_10_SEC", "Alt. over 10 seconds", varioName);
			new OXSdata("SENSITIVITY", "Vario sensitivity", varioName);
			
			if (MainP.protocol.getName().equals("Multiplex")) {
				new OXSdata("REL_ALTIMETER", "Relative Altitude", varioName);
				new OXSdata("ALTIMETER_MAX", "Max Relative Altitude", varioName);
			}
			
			TabVario.getvSpeed1Ddl().addItem("       Vario 1", 0);
			TabVario.getvSpeed2Ddl().addItem("       Vario 1", 0);
			TabVario.getvSpeed1Ddl().setValue(0);

			/*if (MainP.airSpeed != null) { // TODO better
				new OXSdata("PRANDTL_DTE", "Prandtl dTE", "varAspeed");
				new OXSdata("PRANDTL_COMPENSATION", "Prandtl Compensation", "varAspeed");

				TabVario.getvSpeed1Ddl().addItem(" V1 + A.Speed",	2);
				TabVario.getvSpeed2Ddl().addItem(" V1 + A.Speed",	2);
			}*/
		} else {
			new OXSdata("ALTIMETER_" + varioName.substring(5), "Altitude "
					+ varioName.substring(5), varioName, "Altitude");
			new OXSdata("VERTICAL_SPEED_" + varioName.substring(5),
					"Vertical Speed " + varioName.substring(5), varioName, "Vertical Speed");
			new OXSdata("ALT_OVER_10_SEC_" + varioName.substring(5),
					"Alt. over 10 seconds " + varioName.substring(5), varioName);

			TabVario.getvSpeed1Ddl().addItem("       Vario 2", 1);
			TabVario.getvSpeed2Ddl().addItem("       Vario 2", 1);
			TabVario.getvSpeed2Ddl().setValue(1);

		}
		
	}

	public void removeSensor() {

		if (this.getName().equals("vario")) {
			TabVario.getvSpeed1Ddl().removeItem("       Vario 1");
			TabVario.getvSpeed2Ddl().removeItem("       Vario 1");

			// if ( airSpeed == null ) { // TODO better
			TabVario.getvSpeed1Ddl().removeItem(" V1 + A.Speed");
			TabVario.getvSpeed2Ddl().removeItem(" V1 + A.Speed");
			// }
			OXSdata.removeFromList("varAspeed");
			PApplet.println("remove varAspeed");
			//TabData.resetSentDataFields("varAspeed");
		} else {
			TabVario.getvSpeed1Ddl().removeItem("       Vario 2");
			TabVario.getvSpeed2Ddl().removeItem("       Vario 2");
		}

		OXSdata.removeFromList(this);
		PApplet.println(OXSdata.getList());
		PApplet.println("remove " + this.getName());
		TabData.resetSentDataFields();
		//updateUIoXSdataList();
		Sensor.getSensorList().remove(this);
		VarAspeed.deleteSensor();

	}

}
