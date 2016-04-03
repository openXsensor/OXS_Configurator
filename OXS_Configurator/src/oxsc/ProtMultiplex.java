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
	public	void doExtraThings() {
		TabVoltage.getCellsTgl().setValue(0);
	}

	@Override
	public String writeType() {
		StringBuilder conf = new StringBuilder();
		conf.append("// ***** 1.1 - Multiplex protocol (if line commented oXs assumes it is Frsky protocol) *****\n");
		conf.append("#define MULTIPLEX\n");
		return conf.toString();
	}

	@Override
	public String writeData() {
		StringBuilder conf = new StringBuilder();
		conf.append("// ***** 9.2 - Multiplex data *****\n");
		conf.append("#define SETUP_MULTIPLEX_DATA_TO_SEND    \\");
		return conf.toString();
	}
}
