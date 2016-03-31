package oxsc;

import gui.TabVoltage;

public class ProtMultiplex extends Protocol {

	public ProtMultiplex(String name) {
		super(name);

		// ----------- Multiplex protocol data list array -----------
		setDataList(new String[][] {
			{ "----------", "----------" },
			{ "2", "2" },
			{ "3", "3" },
			{ "4", "4" },
			{ "5", "5" },
			{ "6", "6" },
			{ "7", "7" },
			{ "8", "8" },
			{ "9", "9" },
			{ "10", "10" },
			{ "11", "11" },
			{ "12", "12" },
			{ "13", "13" },
			{ "14", "14" },
			{ "15", "15" }
		});
	}

	@Override
	void doExtraThings() {
		TabVoltage.getCellsTgl().setValue(0);
	}
}
