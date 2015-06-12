package oxsc;

import gui.TabData;

import java.util.ArrayList;

import processing.core.PApplet;
import controlP5.ControlP5;
import controlP5.DropdownList;

public class Vario extends Sensor {
	
	// private int parameters ;
	
	private ArrayList<OXSdata> dataList = new ArrayList<OXSdata>(); // or string array ?

	public Vario(PApplet p, ControlP5 cp5, String name) {
		super(p, cp5, name);
	}

	public void addOXSdata() {
		String varioName = this.getName();

		if (varioName == "vario") {
			new OXSdata("ALTIMETER", "Altitude", varioName);
			new OXSdata("VERTICAL_SPEED", "Vertical Speed", varioName);
			new OXSdata("ALT_OVER_10_SEC", "Alt. over 10 seconds", varioName);
			new OXSdata("SENSITIVITY", "Vario sensitivity", varioName);
			
			if (MainP.protocol.getName() == "multiplex") {
				new OXSdata("REL_ALTIMETER", "Relative Altitude", varioName);
				new OXSdata("ALTIMETER_MAX", "Max Relative Altitude", varioName);
			}
			
			cp5.get(DropdownList.class, "vSpeed1").addItem("       Vario 1", 0);
			cp5.get(DropdownList.class, "vSpeed2").addItem("       Vario 1", 0);
			cp5.get(DropdownList.class, "vSpeed1").setValue(0);

			if (MainP.airSpeed != null) { // TODO better
				new OXSdata("PRANDTL_DTE", "Prandtl dTE", "varAspeed");
				new OXSdata("PRANDTL_COMPENSATION", "Prandtl Compensation",
						"varAspeed");

				cp5.get(DropdownList.class, "vSpeed1").addItem(" V1 + A.Speed",
						2);
				cp5.get(DropdownList.class, "vSpeed2").addItem(" V1 + A.Speed",
						2);
			}
		} else {
			new OXSdata("ALTIMETER_" + varioName.substring(5), "Altitude "
					+ varioName.substring(5), varioName);
			new OXSdata("VERTICAL_SPEED_" + varioName.substring(5),
					"Vertical Speed " + varioName.substring(5), varioName);
			new OXSdata("ALT_OVER_10_SEC_" + varioName.substring(5),
					"Alt. over 10 seconds " + varioName.substring(5), varioName);

			cp5.get(DropdownList.class, "vSpeed1").addItem("       Vario 2", 1);
			cp5.get(DropdownList.class, "vSpeed2").addItem("       Vario 2", 1);
			cp5.get(DropdownList.class, "vSpeed2").setValue(1);

		}
	}

	public void removeSensor() {

		if (this.getName() == "vario") {
			cp5.get(DropdownList.class, "vSpeed1").removeItem("       Vario 1");
			cp5.get(DropdownList.class, "vSpeed2").removeItem("       Vario 1");

			// if ( airSpeed == null ) { // TODO better
			cp5.get(DropdownList.class, "vSpeed1").removeItem(" V1 + A.Speed");
			cp5.get(DropdownList.class, "vSpeed2").removeItem(" V1 + A.Speed");
			// }
			TabData.resetSentDataFields("varAspeed");
			OXSdata.removeFromList("varAspeed");
			PApplet.println("remove varAspeed");
		} else {
			cp5.get(DropdownList.class, "vSpeed1").removeItem("       Vario 2");
			cp5.get(DropdownList.class, "vSpeed2").removeItem("       Vario 2");
		}

		TabData.resetSentDataFields(this.getName());
		OXSdata.removeFromList(this);
		PApplet.println("remove " + this.getName());
		updateUIoXSdataList();
		Sensor.getSensorList().remove(this);

	}

}
