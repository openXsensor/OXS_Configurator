package oxsc;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import gui.MessageBox;
import gui.TabCurrent;
import gui.TabData;
import static gui.TabGeneralSettings.*;
import gui.TabPPM;
import gui.TabVario;
import gui.TabVoltage;

public class Validation {

	private static final boolean DEBUG = false;
	private static final String OXS_CONFIG_FILE_NAME = System.getProperty("file.separator") + "oXs_config.h";
	private static final String oxsVersion = "v3.0";
	private static final String oxsCversion = "v3.0";
	private static String oxsDirectory = "";
	private static String outputConfigDir = "";
	
	private static StringBuilder message = new StringBuilder();
	private static boolean numPinsValid;
	private static boolean analogPinsValid;
	private static int vSpeedValid; // 0 -> not valid, 1 -> warning, 2 -> valid
	private static boolean cellsValid;
	private static boolean sentDataValid;
	private static int versionValid; // 0 -> not valid, 1 -> warning, 2 -> valid	
	private static int allValid; // 0 -> not valid, 1 -> warning, 2 -> valid

	public Validation() {	}

	public static String getOxsVersion() {
		return oxsVersion;
	}

	public static String getOxsCversion() {
		return oxsCversion;
	}

	public static String getOutputConfigDir() {
		return outputConfigDir;
	}

	public static int getAllValid() {
		return allValid;
	}

	public static void validationProcess(String option) {
		message.setLength(0);
		message.append("\n");
		
		// Config. file writing destination
		oxsDirectory = getOxsDir().getText().trim();
		if (oxsDirectory.equals("")) {
			outputConfigDir = System.getProperty("user.dir") + OXS_CONFIG_FILE_NAME;
		} else {
			outputConfigDir = oxsDirectory + OXS_CONFIG_FILE_NAME;
		}

		numPinsValid = true;
		analogPinsValid = true;
		vSpeedValid = 2;        // 0 -> not valid, 1 -> warning, 2 -> valid
		cellsValid = true;
		sentDataValid = true;
		versionValid = 2;       // 0 -> not valid, 1 -> warning, 2 -> valid

		validateNumPins();
		validateAnalogPins();
		validateVspeed();

		if (option.equals("Config")) {
			validateSentData();
			if (numPinsValid && analogPinsValid && vSpeedValid != 0 && cellsValid && sentDataValid)
				validateVersion();
		}

		if (!numPinsValid || !analogPinsValid || vSpeedValid == 0 || !cellsValid || !sentDataValid || versionValid == 0) {

			message.insert(0, "                                              --- ERROR ---\n");
			message.append("\n");
			message.append("                                             ----------------------\n");
			message.append("\n") ;
			if (option.equals("preset")) {
				message.append("Preset file can't be saved !\n");
			} else {
				message.append("Config file can't be written !\n");
			}

			allValid = 0 ;
			
			MessageBox.error(message);

		} else if (vSpeedValid == 1 || versionValid == 1) {

			message.insert(0, "                                           ----  WARNING  ----\n");
			message.append("\n");
			message.append("                                             ----------------------\n");
			message.append("\n");
			if (option.equals("Config")) {
				message.append("Configuration file will be written to:\n");
				message.append(outputConfigDir + "\n");
				message.append("\n");
				message.append("                       ! If the file already exists, it will be replaced !\n");
			}
			
			allValid = 1 ;
			
			MessageBox.warning(message);

		} else {

			message.insert(0, "                                         --- ALL IS GOOD ! ---\n");
			if (option.equals("preset")) {
				message.append("Preset file can be saved !\n");
			}
			message.append("\n");
			message.append("                                             ----------------------\n");

			allValid = 2 ;
			
			MessageBox.good(message);
		}
	}
	
	public static void validateNumPins() {

		int ppmInputIsActive = 0;
		if (TabPPM.getPpmTgl().getValue() == 1.0
				&& (getVarioTgl().getValue() == 1.0 || getAirSpeedTgl().getValue() == 1.0)) {
			ppmInputIsActive = 1;
		}

		int analogClimbOutputIsActive = 0;
		if (TabVario.getAnalogClimbTgl().getValue() == 1.0 && getVarioTgl().getValue() == 1.0) {
			analogClimbOutputIsActive = 1;
		}
		
		String numPinsValidation[][] = new String[][] {        // array { pin name, pin value, isActive }
				{ "Serial output", "" + (int)getSerialPinDdl().getValue(), "1" },
				{ "Reset button", "" + (int)getResetBtnPinDdl().getValue(), "" + (int) getSaveEpromTgl().getValue() },
				{ "PPM input", "" + (int)TabPPM.getPpmPinDdl().getValue(), "" + ppmInputIsActive },
				{ "Analog climb output", "" + (int)TabVario.getClimbPinDdl().getValue(), "" + analogClimbOutputIsActive },
				{ "RPM input", "" + 8, "" + (int) getRpmTgl().getValue() }
		} ;

		for ( int i = 0; i < numPinsValidation.length; i++ ) {
			for ( int j = i+1; j < numPinsValidation.length; j++ ) {
				if ( Integer.parseInt(numPinsValidation[i][1]) != -1 
						&& Integer.parseInt(numPinsValidation[j][1]) != -1 
						&& Integer.parseInt(numPinsValidation[i][2]) == 1 
						&& Integer.parseInt(numPinsValidation[j][2]) == 1 ) {
					if ( numPinsValidation[i][1].equals(numPinsValidation[j][1]) ) {
						numPinsValid = false ;
						message.append("- " + numPinsValidation[i][0] + " is using the same pin n°" + numPinsValidation[i][1] + " as " + numPinsValidation[j][0] + " !\n");
					}
				}
			}
		}
		
	}

	public static void validateAnalogPins() {

		String voltActive[] = new String[TabVoltage.getVoltnbr() + 1];
		int voltActiveCount = 0;

		for (int i = 1; i <= TabVoltage.getVoltnbr(); i++) {
			if (getVoltageTgl().getValue() == 1.0 && TabVoltage.getVoltTgl()[i].getValue() == 1.0) {
				voltActive[i] = "1";
				voltActiveCount++;
			} else {
				voltActive[i] = "0";
			}
		}

		if (getVoltageTgl().getValue() == 1.0 && voltActiveCount == 0) {
			analogPinsValid = false;
			message.append("- Voltage sensor is active but there is no voltage to measure !\n");
		}

		String analogPinsValidation[][] = new String[][] {              // array { pin name, pin value, isActive }
				{ "Voltage n°1", TabVoltage.getDdlVolt()[1].getCaptionLabel().getText(), "" + voltActive[1] },
				{ "Voltage n°2", TabVoltage.getDdlVolt()[2].getCaptionLabel().getText(), "" + voltActive[2] },
				{ "Voltage n°3", TabVoltage.getDdlVolt()[3].getCaptionLabel().getText(), "" + voltActive[3] },
				{ "Voltage n°4", TabVoltage.getDdlVolt()[4].getCaptionLabel().getText(), "" + voltActive[4] },
				{ "Voltage n°5", TabVoltage.getDdlVolt()[5].getCaptionLabel().getText(), "" + voltActive[5] },
				{ "Voltage n°6", TabVoltage.getDdlVolt()[6].getCaptionLabel().getText(), "" + voltActive[6] },
				{ "Current Sensor", TabCurrent.getCurrentPinDdl().getCaptionLabel().getText(), "" + (int)getCurrentTgl().getValue() },
				//{ "Temperature Sensor", "" + (int)cp5.getGroup("tempPin").getValue(), "" + (int)cp5.getController("temperature").getValue() },
				{ "Vario/Air Speed (A4-A5)", "A4", "" + (int) (getVarioTgl().getValue() + getAirSpeedTgl().getValue()) },
				{ "Vario/Air Speed (A4-A5)", "A5", "" + (int) (getVarioTgl().getValue() + getAirSpeedTgl().getValue()) }
		};

		for (int i = 0; i < analogPinsValidation.length; i++) {
			if (analogPinsValidation[i][1].equals(" --") && analogPinsValidation[i][2].equals("1")) {
				analogPinsValid = false;
				message.append("- " + analogPinsValidation[i][0] + " has no pin assigned !\n");
			}
			for (int j = i + 1; j < analogPinsValidation.length; j++) {
				if (!analogPinsValidation[i][1].equals(" --") && !analogPinsValidation[j][1].equals(" --")
						&& Integer.parseInt(analogPinsValidation[i][2]) >= 1 && Integer.parseInt(analogPinsValidation[j][2]) >= 1) {
					if (analogPinsValidation[i][1].equals(analogPinsValidation[j][1])) {
						analogPinsValid = false;
						message.append("- " + analogPinsValidation[i][0] + " is using the same pin n°" + analogPinsValidation[i][1] + " as " + analogPinsValidation[j][0] + " !\n");
					}
				}
			}
		}
	}

	public static void validateVspeed() {  // TODO validate vspeed better

		if (getVarioTgl().getValue() == 1) {                                                     // test V.Speed types with sensors
				if ( TabPPM.getPpmTgl().getValue() == 1 && ( getVario2Tgl().getValue() == 1 || getAirSpeedTgl().getValue() == 1 ) ) {
					if ((int)TabVario.getvSpeed1Ddl().getValue() == (int)TabVario.getvSpeed2Ddl().getValue()) {
						vSpeedValid = (vSpeedValid == 0) ? 0 : 1;
						message.append("- You have set the same V.Speed types for switching in Vario TAB !\n\n");
					}
				}
		}

	}

	public static void validateSentData() {   // TODO later better redundancy tests validateSentData()
		
		int oxsMeasureCount = 0;

		String[][] oXsTabDataFields = new String[TabData.getFieldNbr() + 1][2];
	
		for (int i = 1; i <= TabData.getFieldNbr(); i++) {
			String sentDataFieldName = TabData.getSentDataField(i).getCaptionLabel().getText();
			String targetDataFieldName = TabData.getTargetDataField(i).getCaptionLabel().getText();
	
			if (!sentDataFieldName.equals("----------")) {   // if OXS measurement field is not empty
				oxsMeasureCount ++;
				if (targetDataFieldName.equals("----------")) {         // if telemetry field is empty
					sentDataValid = false;
					message.append("- The " + sentDataFieldName + " measure is not sent !\n");
				// if FrSky protocol
				} else if (MainP.protocol.getName().equals("FrSky")) {
				// OXS measurement must be default
                if ((sentDataFieldName.equals("Cells monitoring") || sentDataFieldName.equals("RPM"))
							&& !targetDataFieldName.equals("DEFAULT")) {
						sentDataValid = false;
						message.append("- " + sentDataFieldName + " must be set to DEFAULT !\n");
					// OXS measurement can't be default
					} else if ((sentDataFieldName.equals("Alt. over 10 seconds") || sentDataFieldName.equals("Alt. over 10 seconds 2")
							|| sentDataFieldName.equals("Vario sensitivity") || sentDataFieldName.equals("Prandtl Compensation")
							|| sentDataFieldName.equals("Volt 1") || sentDataFieldName.equals("Volt 2")
							|| sentDataFieldName.equals("Volt 3") || sentDataFieldName.equals("Volt 4")
							|| sentDataFieldName.equals("Volt 5") || sentDataFieldName.equals("Volt 6")
							|| sentDataFieldName.equals("PPM value")) && targetDataFieldName.equals("DEFAULT")) {
						sentDataValid = false;
						message.append("- " + sentDataFieldName + " can't be set to DEFAULT !\n");
					// Only one Altitude DEFAULT TODO remove
					} else if ((sentDataFieldName.equals("Altitude") || sentDataFieldName.equals("Altitude 2"))
							&& targetDataFieldName.equals("DEFAULT")) {
						for (int j = i + 1; j <= TabData.getFieldNbr(); j++) {
							if ((TabData.getSentDataField(j).getCaptionLabel().getText().equals("Altitude") 
									|| TabData.getSentDataField(j).getCaptionLabel().getText().equals("Altitude 2"))
									&& TabData.getTargetDataField(j).getCaptionLabel().getText().equals("DEFAULT")) {
								sentDataValid = false;
								message.append("- " + TabData.getSentDataField(j).getCaptionLabel().getText() + " Telemetry data field can't be set to DEFAULT as it's\n");
								message.append("  already used by " + sentDataFieldName + " measurement !\n");
							}
						}
					// Only one V.Speed DEFAULT TODO remove
					} else if ((sentDataFieldName.equals("Vertical Speed") || sentDataFieldName.equals("Vertical Speed 2")
							|| sentDataFieldName.equals("Prandtl dTE") || sentDataFieldName.equals("PPM_VSPEED"))
							&& targetDataFieldName.equals("DEFAULT")) {
						for (int j = i + 1; j <= TabData.getFieldNbr(); j++) {
							if ((TabData.getSentDataField(j).getCaptionLabel().getText().equals("Vertical Speed")
									|| TabData.getSentDataField(j).getCaptionLabel().getText().equals("Vertical Speed 2")
									|| TabData.getSentDataField(j).getCaptionLabel().getText().equals("Prandtl dTE")
									|| TabData.getSentDataField(j).getCaptionLabel().getText().equals("PPM_VSPEED"))
									&& TabData.getTargetDataField(j).getCaptionLabel().getText().equals("DEFAULT")) {
								sentDataValid = false;
								message.append("- " + TabData.getSentDataField(j).getCaptionLabel().getText() + " Telemetry data field can't be set to DEFAULT as it's\n");
								message.append("  already used by " + sentDataFieldName + " measurement !\n");
							}
						}
					}
				}
			}
			oXsTabDataFields[i][0] = sentDataFieldName;
			oXsTabDataFields[i][1] = targetDataFieldName;
		}
	
		// ***  Duplicate tests  ***
		for (int i = 1; i <= TabData.getFieldNbr(); i++) {
			boolean duplicate = false;
			List<String> tempStr = new ArrayList<>();
			String messageString = "";
			if (!oXsTabDataFields[i][0].equals("----------") && !oXsTabDataFields[i][1].equals("----------")) {
				tempStr.add(oXsTabDataFields[i][1]);
				tempStr.add(oXsTabDataFields[i][0]);
				for (int j = i + 1; j <= TabData.getFieldNbr(); j++) {
					if (!oXsTabDataFields[j][0].equals("----------") && oXsTabDataFields[i][1].equals(oXsTabDataFields[j][1])) {
						duplicate = true;
						sentDataValid = false;
						tempStr.add(oXsTabDataFields[j][0]);
						oXsTabDataFields[j][0] = "----------";
					}
				}
				if (duplicate) {
					message.append("- " + tempStr.get(0) + " can't be used at the same time by:\n");
					for (int j = 1; j < tempStr.size() - 1; j++) {
							if (j < tempStr.size() - 2) {
								messageString += tempStr.get(j) + ", ";
							} else {
								messageString += tempStr.get(j) + " and " + tempStr.get(j + 1);
							}
					}
					message.append("  " + messageString + " !\n");
					duplicate = false;
				}
			}
		}

		if (oxsMeasureCount == 0) {
			sentDataValid = false;
			message.append("- You don't have any OXS measurement set !\n");
		}

	}

	public static void validateVersion() {  // TODO 1 better validate version

		File versionFile = new File(oxsDirectory + "/version.oxs");
		String version = null;
		
		try { // with resources
			Scanner scanner = new Scanner(versionFile);

			while (scanner.hasNextLine()) {
				version = scanner.nextLine();
				if (DEBUG) {
					System.out.println(version);
				}
			}
			scanner.close();
		} catch (Exception e) {
			System.out.println("File version.oxs not found...");
			// e.printStackTrace();
			version = null;
		}

		if (version == null) {
			versionValid = 1;

			message.append("                **   The Configurator can't find OXS version number   **\n");
			message.append("                **         Configuration file may not be compatible...      **\n");

		} else if (version.charAt(1) == oxsCversion.charAt(1)) {
			message.append("Configuration file will be written to:\n");
			message.append(outputConfigDir + "\n");
			message.append("\n");
			message.append("                       ! If the file already exists, it will be replaced !\n");

		} else if (version.charAt(1) > oxsCversion.charAt(1)) {
			versionValid = 1;
			
			message.append("        **  The Configurator " + oxsCversion + " can't set OXS " + version + " new features,  **\n");
			message.append("        **    if you need them, you can edit the config file by hand    **\n");
			message.append("\n");
		} else {
			versionValid = 0;
			
			message.append("            ** The Configurator "	+ oxsCversion + " isn't compatible with OXS " + version	+ " **\n");
			message.append("\n");
			message.append("         You may go to \"https://code.google.com/p/openxsensor/\" and\n");  // TODO 1 change URL
			message.append("       download the latest version of both OXS and OXS Configurator.\n");
		}
	}
	
}