package oxsc;

import gui.TabData;

public class Volt extends Sensor {

	// private int parameters ;

	public Volt(String name) {
		super(name);
	}

	public void addOXSdata() {
		String name = this.getName();
		new OXSdata(name.toUpperCase(), name.toUpperCase().charAt(0)
				+ name.substring(1, 4) + " " + name.substring(4), name);
		if (MainP.protocol instanceof ProtMultiplex) {
			new OXSdata("CELL_" + name.substring(4),
					"Cell " + name.substring(4), name);

			if (Sensor.getSensorList().stream().map(Sensor::getName)
					.filter(str -> str.startsWith("volt")).count() > 1) {
				if (!OXSdata.isInList("Cell min."))
					new OXSdata("CELL_MIN", "Cell min.", "volts");
				if (!OXSdata.isInList("Cells total"))
					new OXSdata("CELL_TOT", "Cells total", "volts");
			}
		}
	}

	public void removeSensor() {
		Sensor.removeFromList(this);
		OXSdata.removeFromList(this);
		TabData.resetSentDataFields();
		// updateUIoXSdataList();
	}

}