package oxsc;

import gui.TabData;

public class Volt extends Sensor {

	// private int parameters ;

	public Volt(String name) {
		super(name);
	}

	public void addOXSdata() {
		String name = this.getName();
		new OXSdata(name.toUpperCase(), name.toUpperCase().charAt(0) + name.substring(1, 4) + " " + name.substring(4),
				name);
		if (MainP.protocol.getName().equals("Multiplex")) {
			new OXSdata("CELL_" + name.substring(4), "Cell " + name.substring(4), name);
			if (!OXSdata.isInList("Cell min.")) new OXSdata("CELL_MIN", "Cell min.", name);  // TODO 2 define conditions with multiplex protocol
			if (!OXSdata.isInList("Cells total")) new OXSdata("CELL_TOT", "Cells total", name);
		}
	}

	public void removeSensor() {
		OXSdata.removeFromList(this);
		TabData.resetSentDataFields();
		// updateUIoXSdataList();
		Sensor.getSensorList().remove(this);
	}

}