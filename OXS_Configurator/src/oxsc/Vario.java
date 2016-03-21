package oxsc;

import gui.TabData;
import gui.TabVario;

import java.util.ArrayList;

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
			new OXSdata("PPM_VSPEED", "PPM V.Speed", varioName, "Vertical Speed");
			
			if (MainP.protocol.getName().equals("Multiplex")) {
				new OXSdata("REL_ALTIMETER", "Relative Altitude", varioName);
				new OXSdata("ALTIMETER_MAX", "Max Relative Altitude", varioName);
			}
			
			TabVario.addToVspeedDdls("       Vario 1", 0);
			TabVario.getvSpeed1Ddl().setValue(0);

			if (MainP.airSpeed != null) {
				TabVario.addToVspeedDdls(" V1 + A.Speed", 2);
				TabVario.getvSpeed2Ddl().setValue(2);
			}
		} else {
			new OXSdata("ALTIMETER_" + varioName.substring(5), "Altitude "
					+ varioName.substring(5), varioName, "Altitude");
			new OXSdata("VERTICAL_SPEED_" + varioName.substring(5),
					"Vertical Speed " + varioName.substring(5), varioName, "Vertical Speed");
			new OXSdata("ALT_OVER_10_SEC_" + varioName.substring(5),
					"Alt. over 10 seconds " + varioName.substring(5), varioName);

			if (MainP.protocol.getName().equals("Multiplex")) {
				new OXSdata("REL_ALTIMETER_" + varioName.substring(5),
						"Relative Altitude " + varioName.substring(5), varioName);
			}
			
			TabVario.addToVspeedDdls("       Vario 2", 1);
			TabVario.addToVspeedDdls("       Average", 3);
			TabVario.getvSpeed2Ddl().setValue(1);
		}
		
	}

	public void removeSensor() {

		if (this.getName().equals("vario")) {  // TODO z better vSpeed choice
			TabVario.removeFromVspeedDdls("       Vario 1");
			TabVario.removeFromVspeedDdls(" V1 + A.Speed");
			TabVario.resetVspeedDdls();

			OXSdata.removeFromList("varAspeed");
			System.out.println("remove varAspeed");
			//TabData.resetSentDataFields("varAspeed");
		} else {
			TabVario.removeFromVspeedDdls("       Vario 2");
			TabVario.removeFromVspeedDdls("       Average");
			TabVario.resetVspeedDdls();
		}

		OXSdata.removeFromList(this);
		System.out.println(OXSdata.getList());
		System.out.println("remove " + this.getName());
		TabData.resetSentDataFields();
		//updateUIoXSdataList();
		Sensor.getSensorList().remove(this);
		VarAspeed.deleteSensor();

	}

}
