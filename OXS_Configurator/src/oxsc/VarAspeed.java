package oxsc;

import gui.TabData;

public class VarAspeed extends Sensor {

	public static VarAspeed varAspeed;
	
	public VarAspeed(String name) {
		super(name);
	}

	@Override
	public void addOXSdata() {
		new OXSdata("PRANDTL_DTE", "Prandtl dTE", this.getName());
		new OXSdata("PRANDTL_COMPENSATION", "Prandtl Compensation", this.getName());
	}

	@Override
	public void removeSensor() {
		OXSdata.removeFromList(this);
		TabData.resetSentDataFields();
		Sensor.getSensorList().remove(this);
	}
	
	public static void createSensor() {
		if (MainP.vario != null || MainP.airSpeed != null) {			
			varAspeed = new VarAspeed("varAspeed");
		}
	}

	public static void deleteSensor() {
		if (varAspeed != null) {
			varAspeed.removeSensor();
			varAspeed = null;			
		}
	}
}
