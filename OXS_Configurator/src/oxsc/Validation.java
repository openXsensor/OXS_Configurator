package oxsc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

import gui.MessageBox;
import gui.TabCurrent;
import gui.TabData;
import gui.TabGeneralSettings;
import gui.TabPPM;
import gui.TabVario;
import gui.TabVoltage;

public class Validation {

	private static final boolean DEBUG = false;
	private static final String OXS_CONFIG_FILE_NAME = System.getProperty("file.separator") + "oXs_config.h";  // TODO 1 suppress file.separator
	private static final String oxsVersion = "v3.0";
	private static final String oxsCversion = "v3.0";
	private static final String OXS_VERSION_FILE = "version.oxs";  // TODO 1 use path
	private static String oxsDirectory = "";
	private static String outputConfigDir = "";
	
	private static StringBuilder message = new StringBuilder();
	private static boolean numPinsValid;
	private static boolean analogPinsValid;
	private static int vSpeedValid; // 0 -> not valid, 1 -> warning, 2 -> valid
	private static boolean sentDataValid;
	private static int versionValid; // 0 -> not valid, 1 -> warning, 2 -> valid	
	private static int allValid; // 0 -> not valid, 1 -> warning, 2 -> valid
	private static boolean validationMbox;

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
	
	public static void checkUpdate() {
		message.setLength(0);
		message.append("                            OXS Configurator " + Validation.getOxsCversion() + " for OXS "
				+ Validation.getOxsVersion() + "\n");
		message.append("                                                       ---\n");
		message.append("                         -- OpenXsensor configuration file GUI --\n");
		message.append("\n");
		(new Thread(new Runnable() {
			public void run() {
				String line = null;
				try {
					URL url = new URL("https://github.com/davxlw/testda/raw/testEclipse/HelloWorld/oXs-C_Readme.txt");
					// https://github.com/openXsensor/OXS_Configurator/raw/master/OXS_Configurator/oXs-C_Readme.txt
					HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
					try (BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
						// boolean changeLog = false;
						while ((line = reader.readLine()) != null) {
							if (Pattern.matches("v\\d\\.\\d", line)) {
								System.out.println(line);
								break;
							}
						}
					} catch (IOException x) {
						System.err.format("IOException: %s%n", x);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (Float.parseFloat(line.substring(1)) > Float.parseFloat(oxsCversion.substring(1))) {
					System.out.println("A new version is available...");
					message.append("- A new version is available: " + line + "\n");
				} else {
					System.out.println("You're up to date !");
				}
				MessageBox.infos(message);
			}
		})).start();
	}
	
	public static void validationProcess(String option) {
		message.setLength(0);
		message.append("\n");
		setValidationMbox(true);
		
		// Config. file writing destination
		oxsDirectory = TabGeneralSettings.getOxsDir().getText().trim();
		if (oxsDirectory.equals("")) {
			outputConfigDir = MainP.execPath.getParent() + OXS_CONFIG_FILE_NAME;
		} else {
			outputConfigDir = oxsDirectory + OXS_CONFIG_FILE_NAME;
		}

		numPinsValid = true;
		analogPinsValid = true;
		vSpeedValid = 2;        // 0 -> not valid, 1 -> warning, 2 -> valid
		sentDataValid = true;
		versionValid = 2;       // 0 -> not valid, 1 -> warning, 2 -> valid

		validateNumPins();
		validateAnalogPins();
		validateVspeed();

		if (option.equals("Config")) {
			validateSentData();
			if (numPinsValid && analogPinsValid && vSpeedValid != 0 && sentDataValid)
				validateVersion();
		}

		if (!numPinsValid || !analogPinsValid || vSpeedValid == 0 || !sentDataValid || versionValid == 0) {

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
				&& (TabGeneralSettings.getVarioTgl().getValue() == 1.0 || TabGeneralSettings.getAirSpeedTgl().getValue() == 1.0)) {
			ppmInputIsActive = 1;
		}

		int analogClimbOutputIsActive = 0;
		if (TabVario.getAnalogClimbTgl().getValue() == 1.0 && TabGeneralSettings.getVarioTgl().getValue() == 1.0) {
			analogClimbOutputIsActive = 1;
		}
		
		String numPinsValidation[][] = new String[][] {        // array { pin name, pin value, isActive }
				{ "Serial output", "" + (int)TabGeneralSettings.getSerialPinDdl().getValue(), "1" },
				{ "Reset button", "" + (int)TabGeneralSettings.getResetBtnPinDdl().getValue(), "" + (int) TabGeneralSettings.getSaveEpromTgl().getValue() },
				{ "PPM input", "" + (int)TabPPM.getPpmPinDdl().getValue(), "" + ppmInputIsActive },
				{ "Analog climb output", "" + (int)TabVario.getClimbPinDdl().getValue(), "" + analogClimbOutputIsActive },
				{ "RPM input", "" + 8, "" + (int) TabGeneralSettings.getRpmTgl().getValue() }
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
			if (TabGeneralSettings.getVoltageTgl().getValue() == 1.0 && TabVoltage.getVoltTgl()[i].getValue() == 1.0) {
				voltActive[i] = "1";
				voltActiveCount++;
			} else {
				voltActive[i] = "0";
			}
		}

		if (TabGeneralSettings.getVoltageTgl().getValue() == 1.0 && voltActiveCount == 0) {
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
				{ "Current Sensor", TabCurrent.getCurrentPinDdl().getCaptionLabel().getText(), "" + (int)TabGeneralSettings.getCurrentTgl().getValue() },
				//{ "Temperature Sensor", "" + (int)cp5.getGroup("tempPin").getValue(), "" + (int)cp5.getController("temperature").getValue() },
				{ "Vario/Air Speed (A4-A5)", "A4", "" + (int) (TabGeneralSettings.getVarioTgl().getValue() + TabGeneralSettings.getAirSpeedTgl().getValue()) },
				{ "Vario/Air Speed (A4-A5)", "A5", "" + (int) (TabGeneralSettings.getVarioTgl().getValue() + TabGeneralSettings.getAirSpeedTgl().getValue()) }
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

		if (TabGeneralSettings.getVarioTgl().getValue() == 1) {                                                     // test V.Speed types with sensors
				if ( TabPPM.getPpmTgl().getValue() == 1 && ( TabGeneralSettings.getVario2Tgl().getValue() == 1 || TabGeneralSettings.getAirSpeedTgl().getValue() == 1 ) ) {
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
					// Only one Altitude: DEFAULT or "Altitude"
					} else if ((sentDataFieldName.equals("Altitude") || sentDataFieldName.equals("Altitude 2"))
							&& (targetDataFieldName.equals("DEFAULT") || targetDataFieldName.equals("Altitude"))) {
						for (int j = i + 1; j <= TabData.getFieldNbr(); j++) {
							if ((TabData.getSentDataField(j).getCaptionLabel().getText().equals("Altitude") 
									|| TabData.getSentDataField(j).getCaptionLabel().getText().equals("Altitude 2"))
									&& (TabData.getTargetDataField(j).getCaptionLabel().getText().equals("DEFAULT"))
									|| TabData.getTargetDataField(j).getCaptionLabel().getText().equals("Altitude")){
								sentDataValid = false;
								message.append("- " + TabData.getSentDataField(j).getCaptionLabel().getText() + " Telemetry data field can't be set to " 
										+ TabData.getTargetDataField(j).getCaptionLabel().getText() + " as it's\n");
								message.append("  already used by " + sentDataFieldName + " measurement !\n");
							}
						}
					// Only one V.Speed: DEFAULT or "Vertical Speed"
					} else if ((sentDataFieldName.equals("Vertical Speed") || sentDataFieldName.equals("Vertical Speed 2")
							|| sentDataFieldName.equals("Prandtl dTE") || sentDataFieldName.equals("PPM V.Speed"))
							&& (targetDataFieldName.equals("DEFAULT") || targetDataFieldName.equals("Vertical Speed"))) {
						for (int j = i + 1; j <= TabData.getFieldNbr(); j++) {
							if ((TabData.getSentDataField(j).getCaptionLabel().getText().equals("Vertical Speed")
									|| TabData.getSentDataField(j).getCaptionLabel().getText().equals("Vertical Speed 2")
									|| TabData.getSentDataField(j).getCaptionLabel().getText().equals("Prandtl dTE")
									|| TabData.getSentDataField(j).getCaptionLabel().getText().equals("PPM V.Speed"))
									&& (TabData.getTargetDataField(j).getCaptionLabel().getText().equals("DEFAULT")
									|| TabData.getTargetDataField(j).getCaptionLabel().getText().equals("Vertical Speed"))) {
								sentDataValid = false;
								message.append("- " + TabData.getSentDataField(j).getCaptionLabel().getText() + " Telemetry data field can't be set to " 
										+ TabData.getTargetDataField(j).getCaptionLabel().getText() + "\n");
								message.append("  as it's already used by " + sentDataFieldName + " measurement !\n");
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
					if (!oXsTabDataFields[j][0].equals("----------")
							&& oXsTabDataFields[i][1].equals(oXsTabDataFields[j][1])) {
						// Don't report as duplicates if OXS data are different but both set to "DEFAULT"
						if (!oXsTabDataFields[i][0].equals(oXsTabDataFields[j][0])
								&& oXsTabDataFields[i][1].equals("DEFAULT"))
							continue;

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

	public static void validateVersion() {  // TODO 2 better validate version

		Charset charset = Charset.forName("UTF-8");
		Path versionFile = Paths.get(oxsDirectory, OXS_VERSION_FILE);
		String version = null;
		
		try (BufferedReader reader = Files.newBufferedReader(versionFile, charset)) {
			while (reader.ready()) {
				version = reader.readLine();
				if (DEBUG) {
					System.out.println(version);
				}
			}
		} catch (Exception e) {
			System.out.println("File " + OXS_VERSION_FILE + " not found...");
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
			message.append("         You may go to \"" + MainP.oXsURL + "\" and\n");
			message.append("       download the latest version of both OXS and OXS Configurator.\n");
		}
	}

	public static boolean isValidationMbox() {
		return validationMbox;
	}

	public static void setValidationMbox(boolean validationMbox) {
		Validation.validationMbox = validationMbox;
	}
	
}