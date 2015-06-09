package gui;

import oxsc.MainP;
import oxsc.OXSdata;
import controlP5.ControlP5;
import controlP5.Controller;
import controlP5.DropdownList;

public class TabData {

	private final ControlP5 cp5;

	private static final int tabDataFieldNbr = 10;
	
	@SuppressWarnings("unused")
	private DropdownList oXSdataField; // TODO later
	@SuppressWarnings("unused")
	private DropdownList targetDataField; // TODO
	private String[] oXSdataFieldDisplayList = new String[tabDataFieldNbr + 1];
	@SuppressWarnings("unused")
	private String[] dataDestFieldDisplayList = new String[tabDataFieldNbr + 1]; // TODO
	
	// ------------------------- HUB protocol data list array --------------------------
	private static String hubDataList[][] = new String[][] {
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
	} ;
	/*
	 FRSKY_USERDATA_GPS_ALT_B
	 FRSKY_USERDATA_TEMP1
	 FRSKY_USERDATA_RPM
	 FRSKY_USERDATA_FUEL
	 FRSKY_USERDATA_TEMP2
	 FRSKY_USERDATA_CELL_VOLT
	 FRSKY_USERDATA_GPS_ALT_A
	 FRSKY_USERDATA_BARO_ALT_B
	 FRSKY_USERDATA_GPS_SPEED_B
	 FRSKY_USERDATA_GPS_LONG_B
	 FRSKY_USERDATA_GPS_LAT_B
	 FRSKY_USERDATA_GPS_CURSE_B
	 FRSKY_USERDATA_GPS_DM
	 FRSKY_USERDATA_GPS_YEAR
	 FRSKY_USERDATA_GPS_HM
	 FRSKY_USERDATA_GPS_SEC
	 FRSKY_USERDATA_GPS_SPEED_A
	 FRSKY_USERDATA_GPS_LONG_A
	 FRSKY_USERDATA_GPS_LAT_A
	 FRSKY_USERDATA_GPS_CURSE_A
	 FRSKY_USERDATA_BARO_ALT_A
	 FRSKY_USERDATA_GPS_LONG_EW
	 FRSKY_USERDATA_GPS_LAT_EW
	 FRSKY_USERDATA_ACC_X
	 FRSKY_USERDATA_ACC_Y
	 FRSKY_USERDATA_ACC_Z
	 FRSKY_USERDATA_CURRENT
	 FRSKY_USERDATA_VERT_SPEED
	 FRSKY_USERDATA_ALT_MIN
	 FRSKY_USERDATA_ALT_MAX
	 FRSKY_USERDATA_RPM_MAX
	 FRSKY_USERDATA_T1_MAX
	 FRSKY_USERDATA_T2_MAX
	 FRSKY_USERDATA_GPS_SPEED_MAX
	 FRSKY_USERDATA_GPS_DIS_MAX
	 FRSKY_USERDATA_VFAS_NEW
	 FRSKY_USERDATA_VOLTAGE_B
	 FRSKY_USERDATA_VOLTAGE_A
	 FRSKY_USERDATA_GPS_DIST
	 FRSKY_USERDATA_FUELPERCENT
	 */

	// ----------------------- SMART PORT protocol data list array -----------------------------	
	private static String sPortDataList[][] = new String[][] {
		{ "----------", "----------" },
		{ "DEFAULTFIELD", "DEFAULT" },          // 1
		{ "Alt", "Altitude" },                  // 2  ALT_FIRST_ID
		{ "Vspd", "Vertical Speed" },           // 3  VARIO_FIRST_ID
		{ "Curr", "Current" },                  // 4  CURR_FIRST_ID
		{ "Vfas", "Vfas" },                     // 5  VFAS_FIRST_ID
		{ "T1", "Temperature 1" },              // 6  T1_FIRST_ID
		{ "T2", "Temperature 2" },              // 7  T2_FIRST_ID
		{ "Rpm", "RPM" },                       // 8  RPM_FIRST_ID
		{ "Fuel", "Fuel" },                     // 9  FUEL_FIRST_ID
		{ "AccX", "Acceleration X" },           // 10 ACCX_FIRST_ID
		{ "AccY", "Acceleration Y" },           // 11 ACCY_FIRST_ID
		{ "AccZ", "Acceleration Z" },           // 12 ACCZ_FIRST_ID
		{ "A3", "A3 (S.Port only)" },           // 13 A3_FIRST_ID
		{ "A4", "A4 (S.Port only)" },           // 14 A4_FIRST_ID
		{ "ASpd", "Air Speed (S.Port only)" }   // 15 AIR_SPEED_FIRST_ID
	} ;
	/*
		//#define RSSI_ID            0xf101  // please do not use this code because it is already used by the receiver
		//#define ADC1_ID            0xf102  // please do not use this code because it is already used by the receiver
		#define ADC2_ID            0xf103
		#define BATT_ID            0xf104
		//#define SWR_ID             0xf105   // please do not use this code because it is already used by the receiver
		#define T1_FIRST_ID        0x0400
		#define T1_LAST_ID         0x040f
		#define T2_FIRST_ID        0x0410
		#define T2_LAST_ID         0x041f
		#define RPM_FIRST_ID       0x0500
		#define RPM_LAST_ID        0x050f
		#define FUEL_FIRST_ID      0x0600
		#define FUEL_LAST_ID       0x060f
		#define ALT_FIRST_ID       0x0100
		#define ALT_LAST_ID        0x010f
		#define VARIO_FIRST_ID     0x0110
		#define VARIO_LAST_ID      0x011f
		#define ACCX_FIRST_ID      0x0700
		#define ACCX_LAST_ID       0x070f
		#define ACCY_FIRST_ID      0x0710
		#define ACCY_LAST_ID       0x071f
		#define ACCZ_FIRST_ID      0x0720
		#define ACCZ_LAST_ID       0x072f
		#define CURR_FIRST_ID      0x0200
		#define CURR_LAST_ID       0x020f
		#define VFAS_FIRST_ID      0x0210
		#define VFAS_LAST_ID       0x021f
		#define GPS_SPEED_FIRST_ID 0x0830
		#define GPS_SPEED_LAST_ID  0x083f
		#define CELLS_FIRST_ID     0x0300
		#define CELLS_SECOND_ID    0x0301
		#define CELLS_THIRD_ID     0x0302
		#define CELLS_LAST_ID      0x030f
		// End of list of all telemetry fields supported by SPORT  (defined by Frsky)
	 */

	public TabData(ControlP5 cp5) {

		this.cp5 = cp5;

		cp5.getTab("data").setHeight(20).setColorForeground(MainP.tabGray)
				.setColorBackground(0xFF285A28) // color(40, 90, 40)
				.setColorActive(0xFF3CBE3C) // color(60, 190, 60)
				.setLabel("DATA sent").setId(7).hide();
		cp5.getTab("data").getCaptionLabel().toUpperCase(false);

		// Text Labels
		cp5.addTextlabel("sentData").setText("OXS measurement")
				.setPosition(22, 104).setColorValueLabel(0).setTab("data");
		cp5.getProperties().remove(cp5.getController("sentData"));

		cp5.addTextlabel("DataFS").setText("Telemetry data field")
				.setPosition(162, 104).setColorValueLabel(0).setTab("data");
		cp5.getProperties().remove(cp5.getController("DataFS"));

		cp5.addTextlabel("multiplierL").setText("Multiplier")
				.setPosition(291, 104).setColorValueLabel(0).setTab("data");
		cp5.getProperties().remove(cp5.getController("multiplierL"));

		cp5.addTextlabel("dividerL").setText("Divider").setPosition(347, 104)
				.setColorValueLabel(0).setTab("data");
		cp5.getProperties().remove(cp5.getController("dividerL"));

		cp5.addTextlabel("offsetL").setText("Offset").setPosition(400, 104)
				.setColorValueLabel(0).setTab("data");
		cp5.getProperties().remove(cp5.getController("offsetL"));

		for (int i = 1; i <= tabDataFieldNbr; i++) {
			// Transmitted DATA field
			cp5.addDropdownList("sentDataField" + i)
					.setColorForeground(MainP.orangeAct)
					.setColorBackground(MainP.darkBackGray)
					.setColorActive(MainP.blueAct)
					.setPosition(10, 146 - 25 + i * 25)
					.setSize(135, 336 - 25 * i).setItemHeight(20)
					.setBarHeight(20).setTab("data");
			cp5.getGroup("sentDataField" + i).getCaptionLabel().getStyle().marginTop = 2;
			cp5.getGroup("sentDataField" + i).getCaptionLabel()
					.set("----------");
			cp5.get(DropdownList.class, "sentDataField" + i).toUpperCase(false);
			cp5.getProperties().remove(cp5.getGroup("sentDataField" + i),
					"ListBoxItems");

			// HUB DATA field
			cp5.addDropdownList("hubDataField" + i)
					.setColorForeground(MainP.orangeAct)
					.setColorBackground(MainP.darkBackGray)
					.setColorActive(MainP.blueAct)
					.setPosition(150, 146 - 25 + i * 25)
					.setSize(135, 336 - 25 * i).setItemHeight(20)
					.setBarHeight(20).setTab("data");
			for (int j = 0; j < TabData.hubDataList.length; j++) {
				cp5.get(DropdownList.class, "hubDataField" + i).addItem(
						"" + TabData.hubDataList[j][1], j);
			}
			cp5.getGroup("hubDataField" + i).getCaptionLabel().getStyle().marginTop = 2;
			cp5.get(DropdownList.class, "hubDataField" + i).toUpperCase(false);
			cp5.get(DropdownList.class, "hubDataField" + i).setValue(0);
			cp5.getGroup("hubDataField" + i).hide();
			cp5.getProperties().remove(cp5.getGroup("hubDataField" + i),
					"ListBoxItems");

			// SMART PORT DATA field
			cp5.addDropdownList("sPortDataField" + i)
					.setColorForeground(MainP.orangeAct)
					.setColorBackground(MainP.darkBackGray)
					.setColorActive(MainP.blueAct)
					.setPosition(150, 146 - 25 + i * 25)
					.setSize(135, 336 - 25 * i).setItemHeight(20)
					.setBarHeight(20).setTab("data");
			/*
			 * for (int j = 0; j < sPortDataList.length; j++ ) {
			 * cp5.get(DropdownList.class, "sPortDataField" + i).addItem("" +
			 * sPortDataList[j][1], j) ; }
			 */
			cp5.get(DropdownList.class, "sPortDataField" + i).addItem(
					"----------", 0);
			cp5.get(DropdownList.class, "sPortDataField" + i).setValue(0);
			cp5.getGroup("sPortDataField" + i).getCaptionLabel().getStyle().marginTop = 2;
			cp5.get(DropdownList.class, "sPortDataField" + i)
					.toUpperCase(false);
			// cp5.getGroup("sPortDataField" + i).hide() ;
			cp5.getProperties().remove(cp5.getGroup("sPortDataField" + i),
					"ListBoxItems");

			// Data sent multiplier
			cp5.addNumberbox("dataMultiplier" + i)
					.setColorActive(MainP.blueAct)
					.setPosition(300, 125 - 25 + i * 25).setSize(40, 20)
					.setRange(-1000, 1000).setMultiplier((float) 0.5) // set the
																		// sensitifity
																		// of
																		// the
																		// numberbox
					.setDecimalPrecision(0).setDirection(Controller.HORIZONTAL) // change
																				// the
																				// control
																				// direction
																				// to
																				// left/right
					.setValue(1).setCaptionLabel("").setTab("data");
			cp5.getTooltip().register("dataMultiplier" + i, "- Default: 1 -");

			// Data sent divider
			cp5.addNumberbox("dataDivider" + i).setColorActive(MainP.blueAct)
					.setPosition(350, 125 - 25 + i * 25).setSize(40, 20)
					.setRange(1, 1000).setMultiplier((float) 0.5) // set the
																	// sensitifity
																	// of the
																	// numberbox
					.setDecimalPrecision(0).setDirection(Controller.HORIZONTAL) // change
																				// the
																				// control
																				// direction
																				// to
																				// left/right
					.setValue(1).setCaptionLabel("").setTab("data");
			cp5.getTooltip().register("dataDivider" + i, "- Default: 1 -");

			// Data sent offset
			cp5.addNumberbox("dataOffset" + i).setColorActive(MainP.blueAct)
					.setPosition(400, 125 - 25 + i * 25).setSize(40, 20)
					.setRange(-999, 999).setMultiplier((float) 0.5) // set the
																	// sensitifity
																	// of the
																	// numberbox
					.setDecimalPrecision(0).setDirection(Controller.HORIZONTAL) // change
																				// the
																				// control
																				// direction
																				// to
																				// left/right
					.setValue(0).setCaptionLabel("").setTab("data");
			cp5.getTooltip().register("dataOffset" + i, "- Default: 0 -");
		}
		
		// dropdownlist overlap
		for ( int i = tabDataFieldNbr; i >= 1; i-- ) {
			cp5.getGroup("sentDataField" + i).bringToFront() ;
			//cp5.getGroup("hubDataField" + i).bringToFront() ;
			cp5.getGroup("sPortDataField" + i).bringToFront() ;
		}
	}

	public static int getDataSentFieldNbr() {
		return tabDataFieldNbr;
	}

	public String[] getOXSdataFieldDisplayList() {
		return oXSdataFieldDisplayList;
	}

	public static String[][] getsPortDataList() {
		return sPortDataList;
	}

	public static String[][] getHubDataList() {
		return hubDataList;
	}

	public void populateSentDataFields() {
		for (int i = 1; i <= tabDataFieldNbr; i++) {
			cp5.get(DropdownList.class, "sentDataField" + i).clear();
			for (int j = 0; j < OXSdata.getList().size(); j++)
				cp5.get(DropdownList.class, "sentDataField" + i).addItem(
						OXSdata.getList().get(j).getDisplayName(), j);
		}
	}

	public void resetSentDataFields(String sensorType) {
		for (int i = 1; i <= tabDataFieldNbr; i++) {
			String ddlFieldDisplay = getDdlFieldDisplay(i);
			for (int j = 0; j < OXSdata.getList().size(); j++) {
				// println( "reset fnc dataSentField: " + i +
				// "  OXSdataList j = " + j + "  OXSdataListSize: " +
				// OXSdata.getList().size() ) ;
				if (ddlFieldDisplay == OXSdata.getList().get(j)
						.getDisplayName()
						&& sensorType == OXSdata.getList().get(j)
								.getSensorType()) {
					cp5.get(DropdownList.class, "sentDataField" + i)
							.setValue(0);
					// print("reset") ;
					break;
				}
			}
		}
	}

	public void populateSPortDataFields() {
		for (int i = 1; i <= tabDataFieldNbr; i++) {
			cp5.get(DropdownList.class, "sPortDataField" + i).clear();
			for (int j = 0; j < MainP.protocol.getDataList().length; j++)
				cp5.get(DropdownList.class, "sPortDataField" + i).addItem(
						MainP.protocol.getDataList()[j][1], j); // TODO j+1 => j
		}
	}

	public String getDdlFieldDisplay(int id) {
		return oXSdataFieldDisplayList[id];
	} // voir getOXSdataFieldDisplayList() -> necessaire ?

}
