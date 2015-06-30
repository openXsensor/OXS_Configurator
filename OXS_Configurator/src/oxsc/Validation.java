package oxsc;

import java.io.File;
import java.util.Scanner;

import processing.core.PApplet;
import controlP5.ControlP5;
import gui.TabCurrent;
import gui.TabData;
import static gui.TabGeneralSettings.*;
import gui.TabPPM;
import gui.TabVario;
import gui.TabVoltage;

public class Validation {
	
	@SuppressWarnings("unused")
	private static ControlP5 cp5 ;
	private static MainP mainP;

	private static String version;
	private static String oxsDirectory = "";
	private static String outputConfigDir = "";
	private static final String oxsVersion = "v2.x";
	private static final String oxsCversion = "v2.3";
	private static boolean numPinsValid;
	private static boolean analogPinsValid;
	private static int vSpeedValid; // 0 -> not valid 1 -> warning 2 -> valid
	private static boolean cellsValid;
	private static boolean sentDataValid;
	private static int versionValid; // 0 -> not valid 1 -> warning 2 -> valid	
	private static int allValid; // 0 -> not valid 1 -> warning 2 -> valid
	


	public Validation(ControlP5 cp5) {  // Dummy constructor
		Validation.cp5 = cp5;
	}

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

	public static void validationProcess(MainP mainPori, String theString) {
        mainP = mainPori;
		// Config file writing destination
		oxsDirectory = MainP.trim( getOxsDir().getText() ) ;
		if ( oxsDirectory.equals("") ) {
			outputConfigDir = mainP.sketchPath("oXs_config.h");
		} else {
			outputConfigDir = oxsDirectory + "/oXs_config.h" ;
		}

		MainP.messageList.clear() ;
		MainP.messageList.set(0, "") ;
		MainP.messageList.append("") ;

		numPinsValid = true ;
		analogPinsValid = true ;
		vSpeedValid = 2 ;           // 0 -> not valid    1 -> warning   2 -> valid
		cellsValid = true ;
		sentDataValid = true ;
		versionValid = 2 ;          // 0 -> not valid    1 -> warning   2 -> valid

		validateNumPins() ;
		validateAnalogPins() ;
		validateVspeed() ;
		validateCells() ;

		if (theString.equals("Config")) {
			validateSentData();
			if (numPinsValid && analogPinsValid && vSpeedValid != 0
					&& cellsValid && sentDataValid)
				validateVersion();
		}

		if ( !numPinsValid || !analogPinsValid || vSpeedValid == 0 || !cellsValid || !sentDataValid || versionValid == 0 ) {

			MainP.messageBox.setBackgroundColor(MainP.errorColor) ;

			MainP.messageList.set(0, "                                              --- ERROR ---") ;
			MainP.messageList.append("") ;
			MainP.messageList.append("                                             ----------------------") ;
			MainP.messageList.append("") ;
			if ( theString.equals("preset") ) {
				MainP.messageList.append("Preset file can't be saved !") ;
			} else {
				MainP.messageList.append("Config file can't be written !") ;
			}
			//cp5.get(Textarea.class, "messageBoxLabel").setColor(color(255,0,0)) ;
			allValid = 0 ;

		} else if ( vSpeedValid == 1 || versionValid == 1 ) {

			MainP.messageBox.setBackgroundColor(MainP.warnColor) ;

			MainP.messageList.set(0, "                                           ----  WARNING  ----") ;
			MainP.messageList.append("") ;
			MainP.messageList.append("                                             ----------------------") ;
			MainP.messageList.append("") ;
			if ( theString.equals("Config") ) {
				MainP.messageList.append("Configuration file will be written to:") ;
				MainP.messageList.append(outputConfigDir) ;
				MainP.messageList.append("") ;
				MainP.messageList.append("                       ! If the file already exists, it will be replaced !") ;
			}

			allValid = 1 ;

		} else {

			MainP.messageBox.setBackgroundColor(MainP.okColor) ;

			MainP.messageList.set(0, "                                         --- ALL IS GOOD ! ---") ;
			if ( theString.equals("preset") ) {
				MainP.messageList.append("Preset file can be saved !") ;
			}
			MainP.messageList.append("") ;
			MainP.messageList.append("                                             ----------------------") ;

			allValid = 2 ;
		}

		String[] messageListArray = MainP.messageList.array() ;

		String joinedMessageList = MainP.join(messageListArray, "\n") ;

		MainP.messageBoxTextarea.setText(joinedMessageList) ;
		//println(messageList) ;

		//messageBox.setBackgroundColor(color(240)) ;
		MainP.buttonOKBtn.setColorForeground(MainP.blueAct) ;
		MainP.buttonOKBtn.setColorActive(MainP.orangeAct) ;
		MainP.messageBox.show() ;

	}
	
	public static void validateNumPins() {

		String numPinsValidation[][] = new String[][] {                      // array { pin name, pin value, isActive }
				{ "Serial output", "" + (int)getSerialPinDdl().getValue(), "2" },
				{ "Reset button", "" + (int)getResetBtnPinDdl().getValue(), "" + (int) getSaveEpromTgl().getValue() + 1  },
				{ "PPM input", "" + (int)TabPPM.getPpmPin().getValue(), "" + (int) TabPPM.getPpmTgl().getValue() + (int)getVarioTgl().getValue() + (int)getAirSpeedTgl().getValue() },
				{ "Analog climb output", "" + (int)TabVario.getClimbPin().getValue(), "" + (int) TabVario.getAnalogClimbTgl().getValue() + (int)getVarioTgl().getValue() },
				{ "RPM input", "" + 8, "" + (int) getRpmTgl().getValue() + 1 }
		} ;

		for ( int i = 0; i < numPinsValidation.length; i++ ) {
			for ( int j = i+1; j < numPinsValidation.length; j++ ) {
				if ( Integer.parseInt(numPinsValidation[i][1]) != -1 && Integer.parseInt(numPinsValidation[j][1]) != -1 && Integer.parseInt(numPinsValidation[i][2]) > 1 && Integer.parseInt(numPinsValidation[j][2]) > 1 ) {
					if ( numPinsValidation[i][1].equals(numPinsValidation[j][1]) ) {
						//println("Attention !!  " + numPinsValidation[i][0] + " is using the same pin n°" + numPinsValidation[i][1] + " as " + numPinsValidation[j][0] + " !") ;
						numPinsValid = false ;
						MainP.messageList.append("- " + numPinsValidation[i][0] + " is using the same pin n°" + numPinsValidation[i][1] + " as " + numPinsValidation[j][0] + " !") ;
					}
				}
			}
		}
		/*
			  if ( numPinsValid ) {
			    println("No problem found with numeric pins ;)") ;
			    messageList.append("No problem found with numeric pins ;)") ;
			  }
		 */
	}

	public static void validateAnalogPins() {

		String voltActive[] = new String[7] ;
		int voltActiveCount = 0 ;

		for ( int i = 1 ; i <= TabVoltage.getVoltnbr() ; i++ ) {
			if ( (int)getVoltageTgl().getValue() == 1 && (int)TabVoltage.getVoltTgl()[i].getValue() == 1 ) {
				voltActive[i] = "1" ;
				voltActiveCount ++ ;
			} else {
				voltActive[i] = "0" ;
			}
		}

		if ( (int)getVoltageTgl().getValue() == 1 && voltActiveCount == 0 ) {
			analogPinsValid = false ;
			MainP.messageList.append("- Voltage sensor is active but there is no voltage to measure !") ;
		}

		String analogPinsValidation[][] = new String[][] {              // array { pin name, pin value, isActive }
				{ "Voltage n°1", "" + (int)TabVoltage.getDdlVolt()[1].getValue(), "" + voltActive[1] },
				{ "Voltage n°2", "" + (int)TabVoltage.getDdlVolt()[2].getValue(), "" + voltActive[2] },
				{ "Voltage n°3", "" + (int)TabVoltage.getDdlVolt()[3].getValue(), "" + voltActive[3] },
				{ "Voltage n°4", "" + (int)TabVoltage.getDdlVolt()[4].getValue(), "" + voltActive[4] },
				{ "Voltage n°5", "" + (int)TabVoltage.getDdlVolt()[5].getValue(), "" + voltActive[5] },
				{ "Voltage n°6", "" + (int)TabVoltage.getDdlVolt()[6].getValue(), "" + voltActive[6] },
				{ "Current Sensor", "" + (int)TabCurrent.getCurrentPinDdl().getValue(), "" + (int)getCurrentTgl().getValue() },
				//{ "Temperature Sensor", "" + (int)cp5.getGroup("tempPin").getValue(), "" + (int)cp5.getController("temperature").getValue() },
				{ "Vario/Air Speed (A4-A5)", "4", "" + (int) getVarioTgl().getValue() + (int)getAirSpeedTgl().getValue() },
				{ "Vario/Air Speed (A4-A5)", "5", "" + (int) getVarioTgl().getValue() + (int)getAirSpeedTgl().getValue() }
		} ;

		for ( int i = 0; i < analogPinsValidation.length; i++ ) {
			if ( Integer.parseInt(analogPinsValidation[i][1]) == -1 && Integer.parseInt(analogPinsValidation[i][2]) == 1 ) {
				analogPinsValid = false ;
				MainP.messageList.append("- " + analogPinsValidation[i][0] + " has no pin assigned !") ;
			}
			for ( int j = i+1; j < analogPinsValidation.length; j++ ) {

				if ( Integer.parseInt(analogPinsValidation[i][1]) != -1 && Integer.parseInt(analogPinsValidation[j][1]) != -1 && Integer.parseInt(analogPinsValidation[i][2]) >= 1 && Integer.parseInt(analogPinsValidation[j][2]) >= 1 ) {
					if ( analogPinsValidation[i][1].equals(analogPinsValidation[j][1]) ) {
						//println("Attention !!  " + analogPinsValidation[i][0] + " is using the same pin n°A" + analogPinsValidation[i][1] + " as " + analogPinsValidation[j][0] + " !") ;
						analogPinsValid = false ;
						MainP.messageList.append("- " + analogPinsValidation[i][0] + " is using the same pin n°A" + analogPinsValidation[i][1] + " as " + analogPinsValidation[j][0] + " !") ;
					}
				}

			}
		}
		/*
			  if ( analogPinsValid ) {
			    println("No problem found with analog pins ;)") ;
			    messageList.append("No problem found with analog pins ;)") ;
			  }
		 */
	}

	public static void validateVspeed() {

		if ( getVarioTgl().getValue() == 1 ) {                                                     // test V.Speed types with sensors

			for (;;) {
				if ( (int)TabVario.getvSpeed1Ddl().getValue() == 1 && getVario2Tgl().getValue() == 0 ) {
					vSpeedValid = 0 ;
					MainP.messageList.append( "- You can't use Vario 2 V.Speed as Vario 2 is not activated !" ) ;
					break ;
				} else if ( (int)TabVario.getvSpeed1Ddl().getValue() == 2 && getAirSpeedTgl().getValue() == 0 ) {
					vSpeedValid = 0 ;
					MainP.messageList.append( "- You can't use Vario 1 + A.Speed compensated V.Speed as Air Speed" ) ;
					MainP.messageList.append( "  sensor is not activated !" ) ;
					break ;
				}

				if ( TabPPM.getPpmTgl().getValue() == 1 && ( getVario2Tgl().getValue() == 1 || getAirSpeedTgl().getValue() == 1 ) ) {
					if ( (int)TabVario.getvSpeed2Ddl().getValue() == 1 && getVario2Tgl().getValue() == 0 ) {
						vSpeedValid = 0 ;
						MainP.messageList.append( "- You can't use Vario 2 V.Speed as Vario 2 is not activated !" ) ;
					} else if ( (int)TabVario.getvSpeed2Ddl().getValue() == 2 &&  getAirSpeedTgl().getValue() == 0 ) {
						vSpeedValid = 0 ;
						MainP.messageList.append( "- You can't use Vario 1 + A.Speed compensated V.Speed as Air Speed" ) ;
						MainP.messageList.append( "  sensor is not activated !" ) ;
					}

					if ( (int)TabVario.getvSpeed1Ddl().getValue() == (int)TabVario.getvSpeed2Ddl().getValue() ) {
						vSpeedValid = ( vSpeedValid == 0 ) ? 0 : 1 ;
						MainP.messageList.append( "- You have set the same V.Speed types for switching in Vario TAB !" ) ;
					}
				}
				break ;
			}

		}

	}

	public static void validateCells() {  // TODO remove validateCells

		int cellsNbr = 0 ;

		if ( TabVoltage.getCellsTgl().getValue() == 1 ) {
			//println("Cells active") ;
			for ( int i = 1 ; i <= TabVoltage.getVoltnbr() ; i++ ) {
				if ( TabVoltage.getVoltTgl()[i].getValue() == 1 ) {
					cellsNbr ++ ;
				} else {
					break ;
				}
			}
			if ( TabVoltage.getDdlNbrCells().getValue() > cellsNbr ) {
				cellsValid = false ;
				MainP.messageList.append( "- You can't monitor more than " + cellsNbr + " cell(s)" ) ;
			}
			//println(cellsNbr ) ;
		}

	}

	public static void validateSentData() {                // TODO later better redundancy tests validateSentData()
		
		int oxsMeasureCount = 0 ;
	
		String oxsMeasureValidationList[][] = new String[TabData.getTabDataFieldNbr() + 1][5] ;
		for ( int i = 1 ; i < oxsMeasureValidationList.length ; i++ ) {
			oxsMeasureValidationList[i][0] = "" + TabData.getSentDataList()[ (int) TabData.getSentDataField(i).getValue() ][0] ; // Data sent - ( OXS measurement )
			oxsMeasureValidationList[i][1] = "" + TabData.getSentDataField(i).getCaptionLabel().getText() ;          // OXS measurement - ( Data sent )
			oxsMeasureValidationList[i][2] = "" + TabData.getHubDataField(i).getCaptionLabel().getText() ;           // HUB data field
			oxsMeasureValidationList[i][3] = "" + TabData.getTargetDataField(i).getCaptionLabel().getText() ;         // target data field
			oxsMeasureValidationList[i][4] = "0" ;                                                                          // is measurement active
		}
		for ( int i = 1 ; i <= TabData.getTabDataFieldNbr() ; i++ ) {
			switch ( TabData.getSentDataField(i).getCaptionLabel().getText() ) {
			case "Altitude" :
			case "Vertical Speed" :
			case "Alt. over 10 seconds" :
			case "Vario sensitivity" :
				if ( getVarioTgl().getValue() == 1 ) { oxsMeasureValidationList[i][4] = "1" ; }
				break ;
			case "Altitude 2" :            
			case "Vertical Speed 2" :      
			case "Alt. over 10 seconds 2" :
				if ( getVario2Tgl().getValue() == 1 ) { oxsMeasureValidationList[i][4] = "1" ; }
				break ;
			case "Air Speed" :
				if ( getAirSpeedTgl().getValue() == 1 ) { oxsMeasureValidationList[i][4] = "1" ; }
				break ;
			case "Prandtl dTE" :
			case "Prandtl Compensation" :
				if ( getVarioTgl().getValue() == 1 && getAirSpeedTgl().getValue() == 1 ) { oxsMeasureValidationList[i][4] = "1" ; }
				break ;
			//case 11 :                // TODO "PPM_VSPEED"
			//	if ( TabGeneralSettings.getVarioTgl().getValue() == 1 && TabPPM.getPpmTgl().getValue() == 1 ) { oxsMeasureValidationList[i][4] = "1" ; }
			//	break ;
			case "Current (mA)" :
			case "Consumption (mAh)" :
				if ( getCurrentTgl().getValue() == 1 ) { oxsMeasureValidationList[i][4] = "1" ; }
				break ;
			case "Cells monitoring" :
				if ( getVoltageTgl().getValue() == 1 && TabVoltage.getCellsTgl().getValue() == 1 ) { oxsMeasureValidationList[i][4] = "1" ; }
				break ;
			case "Volt 1" :
				if ( getVoltageTgl().getValue() == 1 && TabVoltage.getVoltTgl()[1].getValue() == 1 ) { oxsMeasureValidationList[i][4] = "1" ; }
				break ;
			case "Volt 2" :
				if ( getVoltageTgl().getValue() == 1 && TabVoltage.getVoltTgl()[2].getValue() == 1 ) { oxsMeasureValidationList[i][4] = "1" ; }
				break ;
			case "Volt 3" :
				if ( getVoltageTgl().getValue() == 1 && TabVoltage.getVoltTgl()[3].getValue() == 1 ) { oxsMeasureValidationList[i][4] = "1" ; }
				break ;
			case "Volt 4" :
				if ( getVoltageTgl().getValue() == 1 && TabVoltage.getVoltTgl()[4].getValue() == 1 ) { oxsMeasureValidationList[i][4] = "1" ; }
				break ;
			case "Volt 5" :
				if ( getVoltageTgl().getValue() == 1 && TabVoltage.getVoltTgl()[5].getValue() == 1 ) { oxsMeasureValidationList[i][4] = "1" ; }
				break ;
			case "Volt 6" :
				if ( getVoltageTgl().getValue() == 1 && TabVoltage.getVoltTgl()[6].getValue() == 1 ) { oxsMeasureValidationList[i][4] = "1" ; }
				break ;
			case "RPM" :
				if ( getRpmTgl().getValue() == 1 ) { oxsMeasureValidationList[i][4] = "1" ; }
				break ;
			case "PPM value" :
				if ( TabPPM.getPpmTgl().getValue() == 1 ) { oxsMeasureValidationList[i][4] = "1" ; }
				break ;
			}
		}
	
		//int oxsMeasureCountHUB[][] = new int[TabData.getSentDataList().length + 1][TabData.getHubDataList().length] ;
		int oxsMeasureCountSPORT[][] = new int[TabData.getSentDataList().length + 1][TabData.getsPortDataList().length] ;               // array { sentData, sPortData }
		String[][] oXsTabDataFields = new String[TabData.getTabDataFieldNbr() + 1][2];
	
		for ( int i = 1 ; i <= TabData.getTabDataFieldNbr() ; i++ ) {
			int sentDataFieldNb = (int) TabData.getSentDataField(i).getValue() ;
			String sentDataFieldName = TabData.getSentDataField(i).getCaptionLabel().getText() ; // TODO ori: tab7.getDdlFieldDisplay("sentDataField" + i)
	
			int targetDataFieldNb = (int) TabData.getTargetDataField(i).getValue() ;
			String targetDataFieldName = TabData.getTargetDataField(i).getCaptionLabel().getText() ; // TODO ori: tab7.getDdlFieldDisplay("sPortDataField" + i)
			PApplet.println("(for) nom dest. = " + targetDataFieldName) ;
	
			if ( sentDataFieldNb > 0 ) {   // if OXS measurement field is not empty
				oxsMeasureCount ++ ;
				//if ( OXSdata.isInList( sentDataFieldName ) ) {                // if OXS measurement is valid (sensor available) TODO remove
				/*if ( cp5.getGroup("protocolChoice").getValue() == 1 ) {          // if HUB protocol => maybe not up to date since OXSC v2.0
			            if ( cp5.getGroup("hubDataField" + i).getValue() == 0 ) {    // if telemetry field is empty
			              sentDataValid = false ;
			              messageList.append( "- The " + oxsMeasureValidationList[i][1] + " measure is not sent !" ) ;
			            } else if ( ( oxsMeasureValidationList[i][0].equals("ALTIMETER") || oxsMeasureValidationList[i][0].equals("CELLS")          // OXS measurement must be default
			                          || oxsMeasureValidationList[i][0].equals("RPM") ) && !oxsMeasureValidationList[i][2].equals("DEFAULT") ) {
			                sentDataValid = false ;
			                messageList.append( "- " + oxsMeasureValidationList[i][1] + " must be set to DEFAULT !" ) ;
			            } else if ( ( oxsMeasureValidationList[i][0].equals("SENSITIVITY") || oxsMeasureValidationList[i][0].equals("ALT_OVER_10_SEC")   // OXS measurement can't be default
			                          || oxsMeasureValidationList[i][0].equals("VOLT1") || oxsMeasureValidationList[i][0].equals("VOLT2")
			                          || oxsMeasureValidationList[i][0].equals("VOLT3") || oxsMeasureValidationList[i][0].equals("VOLT4")
			                          || oxsMeasureValidationList[i][0].equals("VOLT5") || oxsMeasureValidationList[i][0].equals("VOLT6")
			                          || oxsMeasureValidationList[i][0].equals("MILLIAH") ) && oxsMeasureValidationList[i][2].equals("DEFAULT") ) {
			                sentDataValid = false ;
			                messageList.append( "- " + oxsMeasureValidationList[i][1] + " can't be set to DEFAULT !" ) ;
			            }
			            oxsMeasureCountHUB[int( sentDataFieldNb )][int( cp5.getGroup("hubDataField" + i).getValue() )] ++ ;
			            oxsMeasureCountHUB[sentDataList.length][int( cp5.getGroup("hubDataField" + i).getValue() )] ++ ;
	
			        } else*/ 
				if ( targetDataFieldNb == 0 ) {         // if telemetry field is empty
					sentDataValid = false ;
					MainP.messageList.append( "- The " + sentDataFieldName + " measure is not sent !" ) ;
				} else if ( MainP.protocol.getName() == "FrSky" ) {          // if FrSky protocol
					/*} else*/ if ( ( sentDataFieldName.equals("Cells monitoring") || sentDataFieldName.equals("RPM") )   // OXS measurement must be default
							&& !targetDataFieldName.equals("DEFAULT") ) {
						sentDataValid = false ;
						MainP.messageList.append( "- " + sentDataFieldName + " must be set to DEFAULT !" ) ;
					// OXS measurement can't be default
					} else if ( ( sentDataFieldName.equals("Alt. over 10 seconds") || sentDataFieldName.equals("Alt. over 10 seconds 2")
							|| sentDataFieldName.equals("Vario sensitivity") || sentDataFieldName.equals("Prandtl Compensation")
							|| sentDataFieldName.equals("Volt 1") || sentDataFieldName.equals("Volt 2")
							|| sentDataFieldName.equals("Volt 3") || sentDataFieldName.equals("Volt 4")
							|| sentDataFieldName.equals("Volt 5") || sentDataFieldName.equals("Volt 6")
							|| sentDataFieldName.equals("PPM value") ) && targetDataFieldName.equals("DEFAULT") ) {
						sentDataValid = false ;
						MainP.messageList.append( "- " + sentDataFieldName + " can't be set to DEFAULT !" ) ;
					// Only one Altitude DEFAULT
					} else if ( ( sentDataFieldName.equals("Altitude") || sentDataFieldName.equals("Altitude 2") )
							&& targetDataFieldName.equals("DEFAULT") ) {
						for ( int j = i+1 ; j <= TabData.getTabDataFieldNbr() ; j++ ) {
							if ( ( TabData.getSentDataField(j).getCaptionLabel().getText().equals("Altitude") 
									|| TabData.getSentDataField(j).getCaptionLabel().getText().equals("Altitude 2") )
									&& TabData.getTargetDataField(j).getCaptionLabel().getText().equals("DEFAULT") ) {
								sentDataValid = false ;
								MainP.messageList.append( "- " + TabData.getSentDataField(j).getCaptionLabel().getText() + " Telemetry data field can't be set to DEFAULT as it's" ) ;
								MainP.messageList.append( "  already used by " + sentDataFieldName + " measurement !" ) ;
							}
						}
					// Only one V.Speed DEFAULT
					} else if ( ( sentDataFieldName.equals("Vertical Speed") || sentDataFieldName.equals("Vertical Speed 2")
							|| sentDataFieldName.equals("Prandtl dTE") || sentDataFieldName.equals("PPM_VSPEED") )
							&& targetDataFieldName.equals("DEFAULT") ) {
						for ( int j = i+1 ; j <= TabData.getTabDataFieldNbr() ; j++ ) {
							if ( ( TabData.getSentDataField(j).getCaptionLabel().getText().equals("Vertical Speed")
									|| TabData.getSentDataField(j).getCaptionLabel().getText().equals("Vertical Speed 2")
									|| TabData.getSentDataField(j).getCaptionLabel().getText().equals("Prandtl dTE")
									|| TabData.getSentDataField(j).getCaptionLabel().getText().equals("PPM_VSPEED") )
									&& TabData.getTargetDataField(j).getCaptionLabel().getText().equals("DEFAULT") ) {
								sentDataValid = false ;
								MainP.messageList.append( "- " + TabData.getSentDataField(j).getCaptionLabel().getText() + " Telemetry data field can't be set to DEFAULT as it's" ) ;
								MainP.messageList.append( "  already used by " + sentDataFieldName + " measurement !" ) ;
							}
						}
					}
	
					oxsMeasureCountSPORT[sentDataFieldNb][targetDataFieldNb] ++ ;
					oxsMeasureCountSPORT[TabData.getSentDataList().length][targetDataFieldNb] ++ ;
				}
				/*}*/ /*else if ( oxsMeasureValidationList[i][0].equals("CELLS") ) {
			          sentDataValid = false ;
			          messageList.append( "- " + oxsMeasureValidationList[i][1] + " is not activated in voltage tab. !" ) ;
			      } else if ( oxsMeasureValidationList[i][0].equals("PPM_VSPEED") ) {
			          sentDataValid = false ;
			          messageList.append( "- PPM option is not activated in Vario tab. !" ) ;
			      } else if ( oxsMeasureValidationList[i][0].equals("PRANDTL_DTE") || oxsMeasureValidationList[i][0].equals("PRANDTL_COMPENSATION") ) {
			          sentDataValid = false ;
			          messageList.append( "- " + oxsMeasureValidationList[i][1] + " needs Vario AND Air Speed sensor !" ) ;
			      } else {
			          sentDataValid = false ;
			          messageList.append( "- " + oxsMeasureValidationList[i][1] + " needs the sensor to be active !" ) ;
			          println("nom = " + sentDataFieldName) ;
			          println("result = " + OXSdata.isInList( sentDataFieldName )) ;
			      }*/
			}
			oXsTabDataFields[i][0] = sentDataFieldName;
			oXsTabDataFields[i][1] = targetDataFieldName;
		}
	
		// ***  Duplicate tests  ***
		/*if ( getProtocolDdl().getValue() == 1 ) {          //  HUB protocol => maybe not up to date since OXSC v2.0
			for ( int k = 1 ; k <= TabData.getSentDataList().length ; k++ ) {

				for ( int l = 1 ; l < TabData.getHubDataList().length ; l++ ) {
					//print( oxsMeasureCountHUB[k][l] + " " ) ;
					if ( oxsMeasureCountHUB[k][l] > 1 && k < TabData.getSentDataList().length ) {
						sentDataValid = false ;
						MainP.messageList.append( "- " + TabData.getSentDataList()[k][1] + " can't be sent " + oxsMeasureCountHUB[k][l] + "X to the same " +  TabData.getHubDataList()[l][1] + " field !" ) ;
					}
					if ( k == TabData.getSentDataList().length && l > 1 && oxsMeasureCountHUB[TabData.getSentDataList().length][l] > 1 ) {
						sentDataValid = false ;
						MainP.messageList.append( "- Different measurements can't be sent to the same " +  TabData.getHubDataList()[l][1] + " field !" ) ;
					}
				}
				//println("");
			}
			if ( oxsMeasureCountHUB[2][1] == 1 && oxsMeasureCountHUB[TabData.getSentDataList().length][2] >= 1 ) {  // Test VERTICAL_SPEED  default/VSpd
				sentDataValid = false ;
				MainP.messageList.append( "- Vertical Speed not available as it's already used by VERTICAL_SPEED !" ) ;
			}
			if ( oxsMeasureCountHUB[5][1] == 1 && oxsMeasureCountHUB[TabData.getSentDataList().length][3] >= 1 ) {  // Test CURRENTMA  default/Curr
				sentDataValid = false ;
				MainP.messageList.append( "- Current not available as it's already used by CURRENTMA !" ) ;
			}
			if ( oxsMeasureCountHUB[14][1] == 1 && oxsMeasureCountHUB[TabData.getSentDataList().length][7] >= 1 ) {  // Test RPM default/RPM
				sentDataValid = false ;
				MainP.messageList.append( "- RPM not available as it's already used by RPM !" ) ;
			}
		} else*/ 
		if ( MainP.protocol.getName().equals("FrSky") ) {         // TODO FrSky protocol
			/*for ( int k = 1 ; k <= Protocol.getDataList().length ; k++ ) {
				
			      if ( k == sentDataList.length ) {
			        print( "TOTAL: " ) ;
			      } else {
			        print( sentDataList[k][1] + ": " ) ;
			      }
				 
				for ( int l = 1 ; l < TabData.getsPortDataList().length ; l++ ) {
					//print( oxsMeasureCountSPORT[k][l] + " " ) ;
					if ( oxsMeasureCountSPORT[k][l] > 1 && k < TabData.getSentDataList().length ) {
						sentDataValid = false ;
						MainP.messageList.append( "- " + TabData.getSentDataList()[k][1] + " can't be sent " + oxsMeasureCountSPORT[k][l] + "X to the same " +  TabData.getsPortDataList()[l][1] + " field !" ) ;
					}
					if ( k == TabData.getSentDataList().length && l > 1 && oxsMeasureCountSPORT[TabData.getSentDataList().length][l] > 1 ) {
						sentDataValid = false ;
						MainP.messageList.append( "- Different measurements can't be sent to the same " +  TabData.getsPortDataList()[l][1] + " field !" ) ;
					}
				}
				//println("");
			}
			if ( (oxsMeasureCountSPORT[1][1] == 1 || oxsMeasureCountSPORT[4][1] == 1) && oxsMeasureCountSPORT[TabData.getSentDataList().length][2] >= 1 ) {  // Test ALTITUDEs  default/Alt
				sentDataValid = false ;
				MainP.messageList.append( "- Altitude Telemetry data field is not available as it's already used by" ) ;
				MainP.messageList.append( "  Altitude 1 or 2 measurement !" ) ;
			}
			if ( (oxsMeasureCountSPORT[2][1] == 1 || oxsMeasureCountSPORT[5][1] == 1 || oxsMeasureCountSPORT[9][1] == 1
					|| oxsMeasureCountSPORT[11][1] == 1) && oxsMeasureCountSPORT[TabData.getSentDataList().length][3] >= 1 ) {  // Test VERTICAL_SPEEDs  default/VSpd
				sentDataValid = false ;
				MainP.messageList.append( "- Vertical Speed Telemetry data field is not available as it's already" ) ;
				MainP.messageList.append( "  used by Vertical Speed measurement !" ) ;
			}
			if ( oxsMeasureCountSPORT[12][1] == 1 && oxsMeasureCountSPORT[TabData.getSentDataList().length][4] >= 1 ) {  // Test CURRENTMA  default/Curr
				sentDataValid = false ;
				MainP.messageList.append( "- Current Telemetry data field is not available as it's already used by" ) ;
				MainP.messageList.append( "  Current (mA) measurement !" ) ;
			}
			if ( oxsMeasureCountSPORT[21][1] == 1 && oxsMeasureCountSPORT[TabData.getSentDataList().length][8] >= 1 ) {  // Test RPM default/RPM
				sentDataValid = false ;
				MainP.messageList.append( "- RPM Telemetry data field is not available as it's already used by" ) ;
				MainP.messageList.append( "  RPM measurement !" ) ;
			}*/
	
			for (int i = 1; i <= TabData.getTabDataFieldNbr(); i++) { // TODO first continue duplicate tests
				PApplet.println(oXsTabDataFields[i][0]);
				System.out.println("premier i = " + i);
				//if ( !oXsTabDataFields[i][0].equals("----------")) {
					for (int j = i + 1; j <= TabData.getTabDataFieldNbr(); j++) {
						if (oXsTabDataFields[i][1].equals(oXsTabDataFields[j][1])){
							sentDataValid = false ;
							MainP.messageList.append( "- " + oXsTabDataFields[i][0]  );
							System.out.println("i = " + i);
							i = j - 1;
							System.out.println("j = " + j);
							break;
						}
						MainP.messageList.append( " can't be used multiple times !" );
						
					}
					//MainP.messageList.append( "- RPM Telemetry data field is not available as it's already used by" );
				//}
			}

			
		}
	
		if ( oxsMeasureCount == 0 ) {
			sentDataValid = false ;
			MainP.messageList.append( "- You don't have any OXS measurement set !" ) ;
		}
	
	}

	public static void validateVersion() {

		File versionFile = new File(oxsDirectory + "/version.oxs");

		try {
			Scanner scanner = new Scanner(versionFile);

			while (scanner.hasNextLine()) {
				version = scanner.nextLine();
				System.out.println(version);
			}
			scanner.close();
		} catch (Exception e) {
			PApplet.println("File version.oxs not found...");
			// e.printStackTrace();
			version = null;
		}

		if (version == null) {
			versionValid = 1;

			MainP.messageList.append("");
			MainP.messageList.append("                ** The Configurator can't find OXS version number **");
			MainP.messageList.append("                **      Configuration file may not be compatible...    **");

		} else if (version.charAt(1) == oxsCversion.charAt(1)) {
			MainP.messageList.append("Configuration file will be written to:");
			MainP.messageList.append(outputConfigDir);
			MainP.messageList.append("");
			MainP.messageList.append("                       ! If the file already exists, it will be replaced !");

		} else if (version.charAt(1) > oxsCversion.charAt(1)) {
			versionValid = 1;
			MainP.messageList.append("");
			MainP.messageList.append("        **  The Configurator " + oxsCversion + " can't set OXS " + version + " new features,  **");
			MainP.messageList.append("        **    if you need them, you can edit the config file by hand    **");
			MainP.messageList.append("");
		} else {
			versionValid = 0;
			MainP.messageList.append("            ** The Configurator "	+ oxsCversion + " isn't compatible with OXS " + version	+ " **");
			MainP.messageList.append("");
			MainP.messageList.append("         You may go to \"https://code.google.com/p/openxsensor/\" and");
			MainP.messageList.append("       download the latest version of both OXS and OXS Configurator.");
		}
	}
	
}


	
	
	
	
	
	
	
	
