package oxsc;

public class ProtFrSkyHub extends ProtFrSkyAuto {

	public ProtFrSkyHub(String name) {
		super(name);

		// ----------- HUB protocol data list array -----------
		setDataList(new String[][] {
			{ "----------", "----------" },
			{ "DEFAULTFIELD", "DEFAULT" },
			{ "Vspd", "Vertical Speed" },            // FRSKY_USERDATA_VERT_SPEED
			{ "Curr", "Current" },                   // FRSKY_USERDATA_CURRENT
			{ "Vfas", "Vfas" },                      // FRSKY_USERDATA_VFAS_NEW
			{ "T1", "Temperature 1" },               // FRSKY_USERDATA_TEMP1
			{ "T2", "Temperature 2" },               // FRSKY_USERDATA_TEMP2
			{ "Rpm", "RPM" },                        // FRSKY_USERDATA_RPM
			{ "Fuel", "Fuel" },                      // FRSKY_USERDATA_FUEL
			{ "AccX", "Acceleration X" },            // FRSKY_USERDATA_ACC_X
			{ "AccY", "Acceleration Y" },            // FRSKY_USERDATA_ACC_Y
			{ "AccZ", "Acceleration Z" }             // FRSKY_USERDATA_ACC_Z
		});
	}

	@Override
	public String writeType() {
		StringBuilder conf = new StringBuilder();
		conf.append("// ***** 1.2 - FrSky device ID (required when Sport protocol is used)  *****\n");
		conf.append("#define FRSKY_TYPE_HUB       //To force HUB protocol (D series receiver)\n");
		return conf.toString();
	}

}
