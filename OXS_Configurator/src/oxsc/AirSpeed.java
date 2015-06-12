package oxsc;

import gui.TabData;
import processing.core.PApplet;
import controlP5.ControlP5;
import controlP5.DropdownList;

public class AirSpeed extends Sensor {

	// private int parameters;

	public void addOXSdata() {
		new OXSdata("AIR_SPEED", "Air Speed", this.getName(), null);
		if (MainP.vario != null) { // TODO better
			new OXSdata("PRANDTL_DTE", "Prandtl dTE", "varAspeed", null);
			new OXSdata("PRANDTL_COMPENSATION", "Prandtl Compensation",
					"varAspeed", null);

			cp5.get(DropdownList.class, "vSpeed1").addItem(" V1 + A.Speed", 2);
			cp5.get(DropdownList.class, "vSpeed2").addItem(" V1 + A.Speed", 2);
		}
	}

	public void removeSensor() {
		// if ( vario == null ) { // TODO better
		cp5.get(DropdownList.class, "vSpeed1").removeItem(" V1 + A.Speed");
		cp5.get(DropdownList.class, "vSpeed2").removeItem(" V1 + A.Speed");
		// }

		TabData.resetSentDataFields(this.getName());
		TabData.resetSentDataFields("varAspeed");
		OXSdata.removeFromList(this);
		OXSdata.removeFromList("varAspeed");
		updateUIoXSdataList();
		Sensor.getSensorList().remove(this);
	}

	public AirSpeed(PApplet p, ControlP5 cp5, String name) {
		super(p, cp5, name);
	}
}