package oxsc;

import gui.TabGeneralSettings;
import gui.TabVoltage;

public class ProtFrSkyAuto extends Protocol {

	public ProtFrSkyAuto(String name) {
		super(name);

		// ----------- AUTO (SMART PORT) protocol data list array -----------
		setDataList(new String[][] {
			{ "----------", "----------" },
			{ "DEFAULTFIELD", "DEFAULT" },        // 1
			{ "Alt", "Altitude" },                // 2 ALT_FIRST_ID
			{ "VSpd", "Vertical Speed" },         // 3 VARIO_FIRST_ID
			{ "Curr", "Current" },                // 4 CURR_FIRST_ID
			{ "Vfas", "Vfas" },                   // 5 VFAS_FIRST_ID
			{ "T1", "Temperature 1" },            // 6 T1_FIRST_ID
			{ "T2", "Temperature 2" },            // 7 T2_FIRST_ID
			{ "Rpm", "RPM" },                     // 8 RPM_FIRST_ID
			{ "Fuel", "Fuel" },                   // 9 FUEL_FIRST_ID
			{ "AccX", "Acceleration X" },         // 10 ACCX_FIRST_ID
			{ "AccY", "Acceleration Y" },         // 11 ACCY_FIRST_ID
			{ "AccZ", "Acceleration Z" },         // 12 ACCZ_FIRST_ID
			{ "A3", "A3 (S.Port only)" },         // 13 A3_FIRST_ID
			{ "A4", "A4 (S.Port only)" },         // 14 A4_FIRST_ID
			{ "ASpd", "Air Speed (S.Port only)" } // 15 AIR_SPEED_FIRST_ID
		});
	}

	@Override
	public	void doExtraThings() {
		TabVoltage.getCellsTgl().setValue(0);
	}

	@Override
	public String writeType() {
		StringBuilder conf = new StringBuilder();
		conf.append("// ***** 1.2 - FrSky device ID (required when Sport protocol is used)  *****\n");
		conf.append("#define SENSOR_ID    " + TabGeneralSettings.getSensorIDDdl().getCaptionLabel().getText() + "\n");
		return conf.toString();
	}

	@Override
	public String writeData() {
		StringBuilder conf = new StringBuilder();
		conf.append("// ***** 9.1 - FrSky data *****\n");
		conf.append("#define SETUP_FRSKY_DATA_TO_SEND    \\");
		return conf.toString();
	}
}
