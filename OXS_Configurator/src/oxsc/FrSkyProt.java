package oxsc;

public class FrSkyProt extends Protocol {

	public FrSkyProt(String name) {
		super(name);

		setDataList(new String[][] {
				{ "----------", "----------" },
				{ "DEFAULTFIELD", "DEFAULT" }, // 1
				{ "Alt", "Altitude" }, // 2 ALT_FIRST_ID
				{ "VSpd", "Vertical Speed" }, // 3 VARIO_FIRST_ID
				{ "Curr", "Current" }, // 4 CURR_FIRST_ID
				{ "Vfas", "Vfas" }, // 5 VFAS_FIRST_ID
				{ "T1", "Temperature 1" }, // 6 T1_FIRST_ID
				{ "T2", "Temperature 2" }, // 7 T2_FIRST_ID
				{ "Rpm", "RPM" }, // 8 RPM_FIRST_ID
				{ "Fuel", "Fuel" }, // 9 FUEL_FIRST_ID
				{ "AccX", "Acceleration X" }, // 10 ACCX_FIRST_ID
				{ "AccY", "Acceleration Y" }, // 11 ACCY_FIRST_ID
				{ "AccZ", "Acceleration Z" }, // 12 ACCZ_FIRST_ID
				{ "A3", "A3 (S.Port only)" }, // 13 A3_FIRST_ID
				{ "A4", "A4 (S.Port only)" }, // 14 A4_FIRST_ID
				{ "ASpd", "Air Speed (S.Port only)" } // 15 AIR_SPEED_FIRST_ID
		});
	}
}
