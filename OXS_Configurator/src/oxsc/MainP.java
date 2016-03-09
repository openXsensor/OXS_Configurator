/****************************************************************************
 **                                                                         **
 **  OpenXsensor Configurator: GUI for generating oXs_config.h file         **
 **  Copyright (C) 2015   David LABURTHE                                    **
 **                                                                         **
 **  This program is free software: you can redistribute it and/or modify   **
 **  it under the terms of the GNU General Public License as published by   **
 **  the Free Software Foundation, either version 3 of the License, or      **
 **  (at your option) any later version.                                    **
 **                                                                         **
 **  This program is distributed in the hope that it will be useful,        **
 **  but WITHOUT ANY WARRANTY; without even the implied warranty of         **
 **  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the          **
 **  GNU General Public License for more details.                           **
 **                                                                         **
 **  You should have received a copy of the GNU General Public License      **
 **  along with this program.  If not, see <http://www.gnu.org/licenses/>.  **
 **                                                                         **
 *****************************************************************************
 **                        Author: David LABURTHE                           **
 **                      Contact: dlaburthe@free.fr                         **
 **                           Date: 04.01.2015                              **
 ****************************************************************************/

package oxsc;

import gui.FileManagement;
import gui.MessageBox;
import gui.TabAirSpeed;
import gui.TabCurrent;
import gui.TabData;
import gui.TabGeneralSettings;
import gui.TabPPM;
import gui.TabVario;
import gui.TabVoltage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PGraphics;
import processing.core.PShape;
import controlP5.ControlEvent;
import controlP5.ControlP5;
import controlP5.Controller;

public class MainP extends PApplet {

	/**
	 * ??
	 */
	private static final long serialVersionUID = 1L;

	public static boolean tempActive = false; // Defines temperature sensor availability

	public static String day = (day() < 10) ? "0" + day() : "" + day();
	public static String month = (month() < 10) ? "0" + month() : "" + month();
	public static String date = day + "/" + month + "/" + year();

	public static final int tabGray = 0xFFC8C8C8; // gray 200
	public static final int backDdlGray = 0xFFFFFFFF; // gray 190
	public static final int grayedColor = 0xFF9B9B9B; // gray 155
	public static final int topBottomGray = 0xFF969696; // gray 150
	public static final int lightBackGray = 0xFF646464; // gray 100
	public static final int darkBackGray = 0xFF464646; // gray 70
	public static final int blueAct = 0xFF0FA5FF; // color(15, 165, 255);
	public static final int lightBlue = 0xFFB5CFDB; // color(181, 207, 219);
	public static final int orangeAct = 0xFFFF8000; // color(255, 128, 0);
	public static final int lightOrange = 0xFFDBBFB5; // color(219, 191, 181);
	public static final int okColor = 0xFF18D018; // color(24, 208, 24);
	public static final int warnColor = 0xFFFFB700; // color(255, 183, 0);
	public static final int errorColor = 0xFFFF2323; // color(255, 35, 35);
	public static final int white = 0xFFFFFFFF;

	PShape oxsI;
	PShape oxsL;

	public static PFont fontLabel; // = createFont("arial.ttf", 12, false) ;
	public static PFont fontItalic; // = createFont("arial italic", 12, false) ;
	public static PFont fontCells; // = createFont("arial bold", 12, false) ;
	public static PFont font16; // = createFont("arial", 16, false) ;
	public static PFont font20; // = createFont("arial", 20, false) ;

	public static ControlP5 cp5;

	// Tabs declaration
	public static TabGeneralSettings tabGenSet;
	public static TabPPM tabPPM;
	public static TabVario tabVario;
	public static TabAirSpeed tabAirSpeed;
	public static TabVoltage tabVoltage;
	public static TabCurrent tabCurrent;
//	TabTemperature tab5;
	public static TabData tabData;
	public static FileManagement fileManagement;

	public static String[] analogPins = new String[8]; // Analog pins array

	// Variables to set the controllers data type
	int ppmRngSensMinMaxRng;
	int ppmSensMinMaxRng;
	int ppmVspeedSwMinNBox;
	int ppmVspeedSwMaxNBox;
	int sensMinMaxRng;
	int vSpeedMinNBox;
	int vSpeedMaxNBox;
	int varioHysteresisSld;
	int outClimbRateMinMaxRng;
	
	int ppmRngMinNBox;
	int ppmRngMaxNBox;
	
	int aSpeedResetNBox;
	int ppmRngCompMinMaxRng;
	int ppmCompMinMaxRng;

	public static Protocol protocol;

	public static Sensor vario;
	public static Sensor vario2;
	public static Sensor airSpeed;
	public static Sensor[] aVolt = new Sensor[TabVoltage.getVoltnbr() + 1];
	public static Sensor current;
	public static Sensor rpm;
	public static Sensor ppm;

	public static PresetManagement presetMan;

	public static MessageBox messageBox;
	
	public void setup() {
		
		size(450, 460) ;
		noStroke() ;

		cp5 = new ControlP5(this) ;

		//Alt+mouseDragged to move controllers on the screen
		//Alt+Shift+h to show/hide controllers
		//Alt+Shift+s to save properties (what are properties? have a look at the properties examples)
		//Alt+Shift+l to load properties
		//cp5.enableShortcuts() ;

		oxsI = loadShape("OXSc_Icon.svg") ;
		oxsL = loadShape("OXSc_Logo.svg") ;

		PGraphics icon = createGraphics(64, 64, JAVA2D) ;
		icon.beginDraw() ;
		icon.shape(oxsI, 0, 0, 64, 64) ;
		icon.endDraw() ;
		frame.setIconImage(icon.image) ;
		frame.setTitle("oXs Configurator");

		fontLabel = createFont("arial.ttf", 12, false) ;
		fontItalic = createFont("ariali.ttf", 12, false) ;
		fontCells = createFont("arialbd.ttf", 12, false) ;
		font16 = createFont("arial.ttf", 16, false) ;
		font20 = createFont("arial.ttf", 20, false) ;

		for ( int i = 0; i < analogPins.length; i++ ) {
			analogPins[i] = ("A" + i ) ;
		}

		cp5.setFont(fontLabel, 12) ;


		// ------------------------ TABS definition ------------------------
		// By default all controllers are stored inside Tab 'default'
		cp5.getWindow().setPositionOfTabs(0, 80) ;

		// About
		cp5.addButton("about")
		   .setCaptionLabel("About")
		   .setPosition(380, 14)
		   .setSize(40, 15)
		   .setColorCaptionLabel(0x000000)
		   .setColorBackground(topBottomGray)
		   .setColorForeground(blueAct)
		   .setColorActive(orangeAct)
		   .setTab("global")
		   ;
		cp5.getController("about").getCaptionLabel().toUpperCase(false) ;

		// ----------------------- Tab 0 : GENERAL SETTINGS ----------------------
		tabGenSet = new TabGeneralSettings(cp5) ;

		// ---------------- PPM settings : Tabs Vario + Air Speed ----------------
		tabPPM = new TabPPM(cp5) ;

		// ---------------------------- Tab 1 : Vario settings ------------------------------
		tabVario = new TabVario(cp5) ;

		// ---------------------------- Tab 2 : Air Speed settings ------------------------------
		tabAirSpeed = new TabAirSpeed(cp5) ;

		// ------------------------------ Tab 3 : Voltage settings ------------------------------
		tabVoltage = new TabVoltage(cp5) ;

		// ------------------------------ Tab 4 : Current settings ------------------------------
		tabCurrent = new TabCurrent(cp5) ;

		// ------------------------------ Tab 5 : Temperature settings ------------------------------
		//tab5 = new TabTemperature(cp5) ;

		// ------------------------------ Tab 6 : RPM settings ------------------------------   needed ?
		cp5.getTab("rpm")
		   .setHeight(20)
		   .setColorForeground(tabGray)
		   .setColorBackground(darkBackGray)
		   .setColorActive(blueAct)
		   .setLabel("RPM")
		   .setId(6)
		   .hide()
		   ;
		cp5.getTab("rpm").getCaptionLabel().toUpperCase(false) ;

		// ------------------------------ Tab 7 : DATA to send ------------------------------
		tabData = new TabData(cp5) ;

		// ------------------------------ File dialog ------------------------------
		fileManagement = new FileManagement(cp5);

		// --------------------------- Preset Management ---------------------------
		presetMan = new PresetManagement(cp5);
		
		// ------------------------------ Message Box ------------------------------
		messageBox = new MessageBox(cp5, this);
		
		// dropdownlist overlap
		//cp5.getGroup("tempPin").bringToFront() ;

		// Tooltips general settings
		cp5.getTooltip().setDelay(1000) ;
		cp5.getTooltip().getLabel().toUpperCase(false) ;

		
		TabGeneralSettings.getProtocolDdl().setValue(1); // Set the protocol ddl value after telemetry fields creation
		new OXSdata("----------", "----------", "noSensor") ;

	}

	public void draw() {

		background(topBottomGray) ;
		// Main screen background
		fill(tabGray) ;
		rect(0, 100, width, 300) ;
		fill(darkBackGray) ;
		rect(0, 97, width, 3) ;

		// Compatibility subtitle
		fill(0) ;
		textFont(fontLabel) ;
		text("For OXS " + Validation.getOxsVersion(), 75, 65) ;

		// OXS Configurator version display
		textFont(fontItalic) ;
		text(Validation.getOxsCversion(), 377, 68) ;

		// Logo display
		shapeMode(CENTER) ;
		shape(oxsL, width/2, 38, 300, 300) ;

		// File dialog Zone
		fill(topBottomGray) ;
		rect(0, height, width, -60) ;
		fill(darkBackGray) ;
		rect(0, height-60, width, 3) ;

		// Show preset buttons
		FileManagement.getLoadPresetBtn().show() ;
		FileManagement.getSavePresetBtn().show() ;

		// ------------ Tabs specific display ------------
		String currentTabName = cp5.getWindow(this).getCurrentTab().getName() ;
		
		switch (currentTabName) {

		case "default" :                                      // TAB GENERAL Settings
			TabGeneralSettings.draw(this);
			break ;

		case "vario" :                                        // TAB Vario
			TabVario.draw(this);
			break ;

		case "airSpeed" :                                     // TAB Air Speed sensor
			TabAirSpeed.draw(this);
			break ;

		case "voltage" :                                      // TAB Voltage / Other
			TabVoltage.draw(this);
			break ;

			/*
		case "current" :                                      // TAB Current sensor
		    if ( cp5.getController("currentDir").value() == 0 ) {             // Current grayed switch
		      cp5.getController("currentDirL").setColorValue(grayedColor) ;
		      cp5.getController("currentDir").setColorCaptionLabel(color(0)) ;
		    } else {
		      cp5.getController("currentDirL").setColorValue(color(0)) ;
		      cp5.getController("currentDir").setColorCaptionLabel(grayedColor) ;
		    }
			break ;
			 */

		case "data" :                                         // TAB DATA sent  dataSentDdlOpen
			TabData.draw(this);
			break ;
		}

		// ---------------- End TAB specific display ---------------

		// Load and Save preset buttons deco
		if ( FileManagement.getLoadPresetBtn().isVisible() ) {
			fill(blueAct) ;
			rect(19, 418, 102, 27) ;
		}

		if ( FileManagement.getSavePresetBtn().isVisible() ) {
			fill(orangeAct) ;
			rect(139, 418, 102, 27) ;
		}

		// ----------------- Texfield and Numberbox mouse-over -----------------

		if (cp5.isMouseOver(TabGeneralSettings.getOxsDir())) {
			TabGeneralSettings.getOxsDir().setColorForeground(blueAct);
		} else {
			TabGeneralSettings.getOxsDir().setColorForeground(tabGray);
		}

		if (cp5.isMouseOver(TabGeneralSettings.getArduinoVccNBox())) {
			TabGeneralSettings.getArduinoVccNBox().setColorForeground(blueAct);
		} else {
			TabGeneralSettings.getArduinoVccNBox().setColorForeground(grayedColor);
		}

		if ( cp5.isMouseOver ( TabPPM.getPpmRngMinNBox()) ) {
			TabPPM.getPpmRngMinNBox().setColorForeground(orangeAct) ;
		} else {
			TabPPM.getPpmRngMinNBox().setColorForeground(grayedColor) ;
		}

		if ( cp5.isMouseOver ( TabPPM.getPpmRngMaxNBox() ) ) {
			TabPPM.getPpmRngMaxNBox().setColorForeground(orangeAct) ;
		} else {
			TabPPM.getPpmRngMaxNBox().setColorForeground(grayedColor) ;
		}

		if ( cp5.isMouseOver ( TabVario.getvSpeedMinNBox()) ) {
			TabVario.getvSpeedMinNBox().setColorForeground(orangeAct) ;
		} else {
			TabVario.getvSpeedMinNBox().setColorForeground(grayedColor) ;
		}

		if ( cp5.isMouseOver ( TabVario.getvSpeedMaxNBox() ) ) {
			TabVario.getvSpeedMaxNBox().setColorForeground(orangeAct) ;
		} else {
			TabVario.getvSpeedMaxNBox().setColorForeground(grayedColor) ;
		}

		if ( cp5.isMouseOver ( TabVario.getPpmVspeedSwMinNBox()) ) {
			TabVario.getPpmVspeedSwMinNBox().setColorForeground(orangeAct) ;
		} else {
			TabVario.getPpmVspeedSwMinNBox().setColorForeground(grayedColor) ;
		}

		if ( cp5.isMouseOver ( TabVario.getPpmVspeedSwMaxNBox() ) ) {
			TabVario.getPpmVspeedSwMaxNBox().setColorForeground(orangeAct) ;
		} else {
			TabVario.getPpmVspeedSwMaxNBox().setColorForeground(grayedColor) ;
		}

		if ( cp5.isMouseOver ( TabAirSpeed.getaSpeedResetNBox() ) ) {
			TabAirSpeed.getaSpeedResetNBox().setColorForeground(orangeAct) ;
		} else {
			TabAirSpeed.getaSpeedResetNBox().setColorForeground(grayedColor) ;
		}

		for ( int i = 1; i <= TabVoltage.getVoltnbr(); i++ ) {
			if ( cp5.isMouseOver ( TabVoltage.getDividerVoltNBox()[i] ) ) {
				TabVoltage.getDividerVoltNBox()[i].setColorForeground(orangeAct) ;
			} else {
				TabVoltage.getDividerVoltNBox()[i].setColorForeground(color(170)) ;
			}
		}

		for ( int i = 1; i <= TabVoltage.getVoltnbr(); i++ ) {
			if ( cp5.isMouseOver ( TabVoltage.getOffsetVoltNBox()[i] ) ) {
				TabVoltage.getOffsetVoltNBox()[i].setColorForeground(orangeAct) ;
			} else {
				TabVoltage.getOffsetVoltNBox()[i].setColorForeground(color(170)) ;
			}
		}
		/*
		  if ( cp5.isMouseOver ( cp5.getController( "currentVccNb" ) ) ) {
		    cp5.getController( "currentVccNb" ).setColorForeground(orangeAct) ;
		  } else {
		    cp5.getController( "currentVccNb" ).setColorForeground(color(170)) ;
		  }
		 */

		if ( cp5.isMouseOver ( TabCurrent.getCurrentOutSensNBox() ) ) {
			TabCurrent.getCurrentOutSensNBox().setColorForeground(orangeAct) ;
		} else {
			TabCurrent.getCurrentOutSensNBox().setColorForeground(color(170)) ;
		}

		if ( cp5.isMouseOver ( TabCurrent.getCurrentOutOffsetNBox() ) ) {
			TabCurrent.getCurrentOutOffsetNBox().setColorForeground(orangeAct) ;
		} else {
			TabCurrent.getCurrentOutOffsetNBox().setColorForeground(color(170)) ;
		}

		if ( cp5.isMouseOver ( TabCurrent.getCurrentOutOffsetMaNBox() ) ) {
			TabCurrent.getCurrentOutOffsetMaNBox().setColorForeground(orangeAct) ;
		} else {
			TabCurrent.getCurrentOutOffsetMaNBox().setColorForeground(color(170)) ;
		}

		if ( cp5.isMouseOver ( TabCurrent.getCurrentDivNBox() ) ) {
			TabCurrent.getCurrentDivNBox().setColorForeground(orangeAct) ;
		} else {
			TabCurrent.getCurrentDivNBox().setColorForeground(color(170)) ;
		}
		/*
		  if ( cp5.isMouseOver ( cp5.getController( "tempOffset" ) ) ) {
		    cp5.getController( "tempOffset" ).setColorForeground(orangeAct) ;
		  } else {
		    cp5.getController( "tempOffset" ).setColorForeground(OXSConfigurator.tabGray) ;
		  }
		 */

		for ( int i = 1; i <= TabData.getFieldNbr(); i++ ) {
			if ( cp5.isMouseOver ( TabData.getDataMultiplierNBox()[i] ) ) {
				TabData.getDataMultiplierNBox()[i].setColorForeground(orangeAct) ;
			} else {
				TabData.getDataMultiplierNBox()[i].setColorForeground(grayedColor) ;
			}
			if ( cp5.isMouseOver ( TabData.getDataDividerNBox()[i] ) ) {
				TabData.getDataDividerNBox()[i].setColorForeground(orangeAct) ;
			} else {
				TabData.getDataDividerNBox()[i].setColorForeground(grayedColor) ;
			}
			if ( cp5.isMouseOver ( TabData.getDataOffsetNBox()[i] ) ) {
				TabData.getDataOffsetNBox()[i].setColorForeground(orangeAct) ;
			} else {
				TabData.getDataOffsetNBox()[i].setColorForeground(grayedColor) ;
			}
		}

    	// ----------------- Dropdownlist: mouse pressed elsewhere closes list -----------------

		if ( !cp5.isMouseOver ( TabGeneralSettings.getProtocolDdl() ) ) {
			if (mousePressed == true) {
				TabGeneralSettings.getProtocolDdl().close() ;
			}
		}

		if ( !cp5.isMouseOver ( TabGeneralSettings.getSerialPinDdl() ) ) {
			if (mousePressed == true) {
				TabGeneralSettings.getSerialPinDdl().close() ;
			}
		}

		if ( !cp5.isMouseOver ( TabGeneralSettings.getSensorIDDdl() ) ) {
			if (mousePressed == true) {
				TabGeneralSettings.getSensorIDDdl().close() ;
			}
		}

		if ( !cp5.isMouseOver ( TabGeneralSettings.getVoltRefChoiceDdl() ) ) {
			if (mousePressed == true) {
				TabGeneralSettings.getVoltRefChoiceDdl().close() ;
			}
		}

		if ( !cp5.isMouseOver ( TabGeneralSettings.getResetBtnPinDdl() ) ) {
			if (mousePressed == true) {
				TabGeneralSettings.getResetBtnPinDdl().close() ;
			}
		}

		if ( !cp5.isMouseOver ( TabPPM.getPpmPinDdl() ) ) {
			if (mousePressed == true) {
				TabPPM.getPpmPinDdl().close() ;
			}
		}

		if ( !cp5.isMouseOver ( TabVario.getvSpeed1Ddl() ) ) {
			if (mousePressed == true) {
				TabVario.getvSpeed1Ddl().close() ;
			}
		}

		if ( !cp5.isMouseOver ( TabVario.getvSpeed2Ddl() ) ) {
			if (mousePressed == true) {
				TabVario.getvSpeed2Ddl().close() ;
			}
		}

		if ( !cp5.isMouseOver ( TabVario.getClimbPinDdl() ) ) {
			if (mousePressed == true) {
				TabVario.getClimbPinDdl().close() ;
			}
		}

		for ( int i = 1; i <= TabVoltage.getVoltnbr(); i++ ) {
			if ( !cp5.isMouseOver ( TabVoltage.getDdlVolt()[i] ) ) {
				if (mousePressed == true) {
				TabVoltage.getDdlVolt()[i].close() ;
				}
			}
		}

		if (!cp5.isMouseOver(TabVoltage.getDdlNbrCells())) {
			if (mousePressed == true) {
				TabVoltage.getDdlNbrCells().close();
			}
		}

		if ( !cp5.isMouseOver ( TabCurrent.getCurrentPinDdl() ) ) {
			if (mousePressed == true) {
				TabCurrent.getCurrentPinDdl().close() ;
			}
		}
		/*
		  if ( !cp5.isMouseOver ( cp5.getGroup( "tempPin") ) ) {
		    if (mousePressed == true) {
		      cp5.getGroup( "tempPin" ).close() ;
		    }
		  }
		 */
		for ( int i = 1; i <= TabData.getFieldNbr(); i++ ) {
			if ( !cp5.isMouseOver ( TabData.getSentDataField(i) ) ) {
				if (mousePressed == true) {
					TabData.getSentDataField(i).close() ;
				}
			}
		}

		/*
		  for ( int i = 1; i <= dataSentFieldNbr; i++ ) {
		    if ( !cp5.isMouseOver ( cp5.getGroup( "hubDataField" + i ) ) ) {
		      if (mousePressed == true) {
		        cp5.getGroup( "hubDataField" + i ).close() ;
		      }
		    }
		  }
		 */

		for ( int i = 1; i <= TabData.getFieldNbr(); i++ ) {
			if ( !cp5.isMouseOver ( TabData.getTargetDataField(i) ) ) {
				if (mousePressed == true) {
					TabData.getTargetDataField(i).close() ;
				}
			}
		}

		// ----------------- TAB DATA sent display -----------------

		if (vario != null || airSpeed != null
				|| TabGeneralSettings.getVoltageTgl().getValue() == 1.0
				|| current != null /* || temperature != null */|| rpm != null) {
			cp5.getTab("data").show();
		} else {
			cp5.getTab("data").hide();
		}

	}

	public void controlEvent(ControlEvent theEvent) {
		// DropdownList is of type ControlGroup.
		// A controlEvent will be triggered from inside the ControlGroup class.
		// therefore you need to check the originator of the Event with if (theEvent.isGroup())
		// to avoid an error message thrown by controlP5.

		// Tab vario: display PPM parameters
		if ( theEvent.isFrom(cp5.getTab("vario")) ) {
			TabPPM.getPpmTgl().setTab("vario")
			                  .show();
			TabPPM.getPpmPinL().setTab("vario")
			                   .show();
			TabPPM.getPpmPinDdl().setTab("vario");
			TabPPM.getPpmRngL().setTab("vario")
			                   .show();
			TabPPM.getPpmRngMinNBox().setTab("vario")
			                         .show();
			TabPPM.getPpmRngMaxNBox().setTab("vario")
			                         .show();
			
		// Tab Air Speed: display PPM parameters
		} else if ( theEvent.isFrom(cp5.getTab("airSpeed")) ) {
			TabPPM.getPpmTgl().setTab("airSpeed")
			                  .show();
			TabPPM.getPpmPinL().setTab("airSpeed")
			                   .show();
			TabPPM.getPpmPinDdl().setTab("airSpeed") ;
			TabPPM.getPpmRngL().setTab("airSpeed")
			                   .show();
			TabPPM.getPpmRngMinNBox().setTab("airSpeed")
			                         .show();
			TabPPM.getPpmRngMaxNBox().setTab("airSpeed")
			                         .show();
		}

		// V speed sensitivity range interaction
		if ( theEvent.isFrom(TabVario.getvSpeedMaxNBox()) || theEvent.isFrom(TabVario.getvSpeedMinNBox()) ) {
			TabVario.getvSpeedMaxNBox().setBroadcast(false) ;
			TabVario.getvSpeedMinNBox().setBroadcast(false) ;
			TabVario.getvSpeedMaxNBox().setRange( TabVario.getvSpeedMinNBox().getValue(), 1000 ) ;
			TabVario.getvSpeedMinNBox().setRange( 0, TabVario.getvSpeedMaxNBox().getValue() ) ;
			TabVario.getvSpeedMinNBox().setBroadcast(true) ;
			TabVario.getvSpeedMaxNBox().setBroadcast(true) ;
		}

		// Voltages sensor activation/deactivation
		for (int i = 1; i <= TabVoltage.getVoltnbr(); i++) {
			if (theEvent.isFrom(TabVoltage.getVoltTgl()[i])) {
				switch ((int) theEvent.getController().getValue()) {
				case 1:
					if (aVolt[i] == null) {
						aVolt[i] = new Volt("volt" + i);
						TabVoltage.populateNbrCells();
					}
					break;
				case 0:
					if (aVolt[i] != null) {
						aVolt[i].removeSensor();
						aVolt[i] = null;
						TabVoltage.populateNbrCells();
						if ( i == 1 ) {
							TabVoltage.getCellsTgl().setValue(0);
						}
					}
					break;
				}
			}
		}

		//  Current sensor output offset interaction // TODO current - if output sens = 0
		if ( theEvent.isFrom(TabCurrent.getCurrentOutOffsetNBox() ) /*|| theEvent.isFrom(cp5.getController("currentOutSensNb"))*/ ) {
			TabCurrent.getCurrentOutOffsetMaNBox().setBroadcast(false) ;
			float currentOutSens = TabCurrent.getCurrentOutSensNBox().getValue() ;
			float currentOutOffsetMV = TabCurrent.getCurrentOutOffsetNBox().getValue() ;
			TabCurrent.getCurrentOutOffsetMaNBox().setValue((float) ((currentOutOffsetMV / currentOutSens) * 1000.0));
			TabCurrent.getCurrentOutOffsetMaNBox().setBroadcast(true);
		}
		if ( theEvent.isFrom(TabCurrent.getCurrentOutOffsetMaNBox()) ) {
			TabCurrent.getCurrentOutOffsetNBox().setBroadcast(false) ;
			float currentOutSens = TabCurrent.getCurrentOutSensNBox().getValue() ;
			float currentOutOffsetMA = TabCurrent.getCurrentOutOffsetMaNBox().getValue() ;
			TabCurrent.getCurrentOutOffsetNBox().setValue((float) ((currentOutOffsetMA / 1000.0 ) * currentOutSens));
			TabCurrent.getCurrentOutOffsetNBox().setBroadcast(true);
		}

		// Protocol selection - Showing right Telemetry data list in fields
		if (theEvent.isFrom(TabGeneralSettings.getProtocolDdl())) {
			switch (theEvent.getGroup().getCaptionLabel().getText()) {
			case "FrSky":
					protocol = Protocol.createProtocol("FrSky");
				break;
			case "Multiplex":
					protocol = Protocol.createProtocol("Multiplex");
				break;
			}
			// TODO z better: updating OXSdata according to the protocol
			for (Sensor sensor : Sensor.getSensorList()) {
				OXSdata.removeFromList(sensor);
				sensor.addOXSdata();				
			}
			TabData.resetSentDataFields();
			TabData.populateSentDataFields();
		}

		// Selecting DEFAULT automatically in Telemetry data fields
		// TODO group controllers ?
		for (int i = 1; i <= TabData.getFieldNbr(); i++) {

			if (theEvent.isFrom(TabData.getSentDataField(i))) {
				String dataFieldDisplayName = TabData.getSentDataField(i).getCaptionLabel().getText();
				String defaultValue = OXSdata.getOXSdata(dataFieldDisplayName).getDefaultValue();
				if (defaultValue != null) {
					for (String[] stringArray : TabData.getTargetDataField(i).getListBoxItems()) {
						if (stringArray[1].equals(defaultValue)) {
							 TabData.getTargetDataField(i).setValue(Float.parseFloat(stringArray[2]));
						}
					}
				}
				/*switch (TabData.getSentDataField(i).getCaptionLabel().getText()) {
				case "Altitude":
				case "Vertical Speed":
				case "Altitude 2":
				case "Vertical Speed 2":
				case "Air Speed":
				case "Prandtl dTE":
				case "PPM V.Speed":
				case "Current (mA)":
				case "Consumption (mAh)":
				case "Cells monitoring":
				case "RPM":
					TabData.getTargetDataField(i).setValue(1);
					break;
				}*/

				/*
				 * System.out.println("oxsdataList : " +
				 * TabData.getSentDataField(i).getCaptionLabel() .getText());
				 */
			}
		}
		/*
		  if (theEvent.isGroup()) {
		    // check if the Event was triggered from a ControlGroup
		    System.out.println("event from group : "+theEvent.getGroup().getValue()+" from "+theEvent.getGroup()) ;
		  } else if (theEvent.isController()) {
		    System.out.println("event from controller : "+theEvent.getController().getValue()+" from "+theEvent.getController()) ;
		  }

		  if (theEvent.isTab()) {
		    // check if the Event was triggered from a Tab
		    System.out.println("event from tab : " + theEvent.getTab().getName() ) ;
		  }
		 */
	}

	// Mouse wheel support for scroll bars !!
	public void mouseWheelMoved(java.awt.event.MouseWheelEvent e) {
		super.mouseWheelMoved(e) ;
		cp5.setMouseWheelRotation(e.getWheelRotation()) ;
	}

	public void oxsDirButton(int theValue) {
		//System.out.println("oxsDir button: "+theValue) ;
		selectFolder("Select OXS source folder:", "folderSelected") ;
	}

	public void about(boolean theFlag) {
		MessageBox.about();
	}
	
	public void varioTgl(boolean theFlag) {
		if (theFlag == true && vario == null) {
			vario = new Vario("vario");
			cp5.getTab("vario").show();
		} else if (theFlag == false && vario != null) {
			if (vario2 != null)
				TabGeneralSettings.getVario2Tgl().setValue(0);
			if (ppm != null && airSpeed == null) {
				TabPPM.getPpmTgl().setValue(0);
			}
			vario.removeSensor();
			vario = null;
			cp5.getTab("vario").hide();
		}
	}

	public void vario2Tgl(boolean theFlag) {
		if (theFlag == true && vario != null && vario2 == null) {
			vario2 = new Vario("vario2");
		} else if (theFlag == false && vario2 != null) {
			vario2.removeSensor();
			vario2 = null;
		}
	}

	public void airSpeedTgl(boolean theFlag) {
		if (theFlag == true && airSpeed == null) {
			airSpeed = new AirSpeed("airSpeed");
			cp5.getTab("airSpeed").show();
		} else if (theFlag == false && airSpeed != null) {
			airSpeed.removeSensor();
			airSpeed = null;
			cp5.getTab("airSpeed").hide();
			if (ppm != null && vario == null)
				TabPPM.getPpmTgl().setValue(0.0f);
		}
	}

	public void voltageTgl(boolean theFlag) {
		if (theFlag == true) {
			cp5.getTab("voltage").show();
		} else {
			for (int i = 1; i <= TabVoltage.getVoltnbr(); i++) {
				TabVoltage.getVoltTgl()[i].setValue(0.0f);
			}
			cp5.getTab("voltage").hide();
		}
	}

	public void cellsTgl(boolean theFlag) { // TODO z better
		if (theFlag == true && aVolt[1] != null) {
			new OXSdata("CELLS", "Cells monitoring", "voltCells", null);
			TabData.populateSentDataFields();
		} else {
			OXSdata.removeFromList("voltCells");
			TabData.resetSentDataFields();
			// TabData.populateSentDataFields();
		}
	}

	public void currentTgl(boolean theFlag) {
		if (theFlag == true && current == null) {
			current = new Current("current");
			cp5.getTab("current").show();
		} else if (theFlag == false && current != null) {
			current.removeSensor();
			current = null;
			cp5.getTab("current").hide();
		}
	}

	public void temperatureTgl(boolean theFlag) {
		if (theFlag == true) {
			cp5.getTab("temperature").show();
		} else {
			cp5.getTab("temperature").hide();
		}
		// System.out.println("a toggle event.") ;
	}

	// RPM TAB display
	public void rpmTgl(boolean theFlag) {
		if (theFlag == true && rpm == null) {
			rpm = new Rpm("rpm");
			// cp5.getTab("rpm").show() ;
		} else if (theFlag == false && rpm != null) {
			rpm.removeSensor();
			rpm = null;
			// cp5.getTab("rpm").hide() ;
		}
		// System.out.println("a toggle event.") ;
	}

	public void ppmTgl(boolean theFlag) {
		if (theFlag == true && ppm == null) {
			ppm = new PPM("ppm");
		} else if (theFlag == false && ppm != null) {
			ppm.removeSensor();
			ppm = null;
		}
	}

	// Load preset button
	public void loadButton(int theValue) {
		File presetDir = new File( sketchPath("src/Preset/...") ) ;
		selectInput("Select a preset file to load:", "presetLoad", presetDir) ;
	}

	// Save preset button
	public void saveButton(int theValue) {
		MessageBox.mbClose() ;
		Validation.validationProcess(this, "preset") ;
		if ( Validation.getAllValid() == 2 ) {
			MessageBox.getGroup().hide() ;
		}
		if ( Validation.getAllValid() != 0 ) {
			File presetDir = new File( ("src/Preset/type name") ) ;  // sketchPath("Preset/type name")
			selectOutput("Type preset name to save:", "presetSave", presetDir) ;
		}
	}

	public void presetLoad(File selection) throws FileNotFoundException, IOException {
		if (selection == null) {
			// System.out.println("Window was closed or the user hit cancel.") ;
		} else {
			// System.out.println("User selected " + selection.getAbsolutePath()) ;
			PresetManagement.presetLoad(selection);
		}
	}
	
	public void presetSave(File selection) throws FileNotFoundException {
		if (selection == null) {
			// System.out.println("Window was closed or the user hit cancel.") ;
		} else {
			PresetManagement.presetSave(selection);
		}
	}

	public void writeConfButton(int theValue) {
		MessageBox.mbOkCancel() ;
		Validation.validationProcess(this, "Config") ;
		if ( Validation.getAllValid() == 0) {
			MessageBox.mbClose() ;
		}
	}

	public void folderSelected(File selection) {
		if (selection == null) {
			//System.out.println("Window was closed or the user hit cancel.") ;
		} else {
			//System.out.println("User selected " + selection.getAbsolutePath()) ;
			TabGeneralSettings.getOxsDir().setText(selection.getAbsolutePath()) ;
		}
	}

	// =========================================== Shortcuts ===========================================

	public void keyPressed() {
		// default properties load/save key combinations are
		// alt+shift+l to load properties
		// alt+shift+s to save properties
		if (key == 's') {
			// cp5.saveProperties(("settings.oxs")) ;
		} else if (key == 'l') {
			// cp5.loadProperties(("settings.oxs")) ;
			// Hack to keep slider labels alignment
			// cp5.getController("varioHysteresis").getCaptionLabel().align(ControlP5.LEFT_OUTSIDE,
			// ControlP5.CENTER).setPaddingX(10) ;
			// cp5.getController("varioHysteresis").getValueLabel().align(ControlP5.RIGHT_OUTSIDE,
			// ControlP5.CENTER).setPaddingX(10) ;
		} else if (key == 'c') {
			System.out.println("mAmp / step " + mAmpStep());
			System.out.println("Current offset " + offsetCurrent());
		} else if (key == 'p') {
			// message.showOk("gxb");
		} else if (key == 'g') {
			cp5.getGroup("serialPinDdl").setValue(4);
//			TabGeneralSettings.getSerialPinDdl().setValue(4);
		} else if (key == 'd') {
			vario = null;
			// System.out.println(vario.getMeasurementName(0)) ;
		}
	}

	// =================================================================================================

	// Rounding function
	public static float round(float number, float decimal) {
		return round((number*pow(10, decimal)))/pow(10, decimal) ;
	}

	// Voltage measurements milliVolt per ADC step calculation
	public static float mVoltStep(int NbrVolt) {

		float mVoltStep ;
		float voltageDiv = TabVoltage.getDividerVoltNBox()[NbrVolt].getValue() ;
		float arduinoVcc = TabGeneralSettings.getArduinoVccNBox().getValue() ;

		if ( TabGeneralSettings.getVoltRefChoiceDdl().getValue() == 1 ) {
			mVoltStep = ( arduinoVcc * 1000.0f / 1024.0f ) * voltageDiv ;
		} else {
			mVoltStep = ( 1.1f * 1000.0f / 1024.0f ) * voltageDiv ;
		}
		return mVoltStep ;
	}
	
	// Current sensor milliAmp per ADC step calculation
	public static float mAmpStep() {

		float mAmpStep;
		float mAmpPmV;
		float arduinoVcc = TabGeneralSettings.getArduinoVccNBox().getValue();
		float currentDiv = TabCurrent.getCurrentDivNBox().getValue();
		float currentOutSens = TabCurrent.getCurrentOutSensNBox().getValue();

		mAmpPmV = (currentOutSens == 0) ? 0 : 1000.0f / currentOutSens;
		if (TabGeneralSettings.getVoltRefChoiceDdl().getValue() == 1) {
			mAmpStep = (arduinoVcc * 1000.0f / 1024.0f) * mAmpPmV * currentDiv;
		} else {
			mAmpStep = (1.1f * 1000.0f / 1024.0f) * mAmpPmV;
		}
		return mAmpStep;
	}

	// Current sensor offset calculation in ADC step
	public static int offsetCurrent() {

		int offsetCurrent ;
		//float currentVcc = cp5.getController("currentVccNb").getValue() ;
		float currentOutOffset = TabCurrent.getCurrentOutOffsetNBox().getValue() ;
		float currentDiv = TabCurrent.getCurrentDivNBox().getValue() ;
		float arduinoVcc = TabGeneralSettings.getArduinoVccNBox().getValue() ;

		//if ( cp5.getController( "currentDir" ).value() == 0 ) {
		//  offsetCurrent = int( ( currentVcc / 2.0 + currentOutOffset / 1000.0 ) / arduinoVcc  * 1024.0 * currentDiv ) ;
		//} else {
		offsetCurrent = (int) ( currentOutOffset / 1000.0 / arduinoVcc  * 1024.0 * currentDiv ) ;
		//}

		return offsetCurrent ;
	}
		
	// Customize functions
	public static void customizeToggle(Controller<?> tgl) {
		tgl.setColorForeground(orangeAct) ;
		tgl.setColorBackground(darkBackGray) ;
		tgl.setColorActive(blueAct) ;
		tgl.setColorCaptionLabel(0) ;
		tgl.setSize(15, 15) ;
		// reposition the Labels
		tgl.getCaptionLabel().align(ControlP5.LEFT_OUTSIDE, ControlP5.CENTER).setPaddingX(10) ;
		tgl.getCaptionLabel().toUpperCase(false) ;
	}

	public static void customizeRange(Controller<?> rng) {
		rng.setColorForeground(blueAct) ;
		rng.setColorBackground(darkBackGray) ;
		rng.setColorActive(orangeAct) ;
		//rng.setSize(200, 20) ;
		rng.setColorCaptionLabel(0) ;
		rng.setTab("vario") ;
		//rng.getCaptionLabel().align(ControlP5.LEFT_OUTSIDE, ControlP5.CENTER) ;
		rng.getCaptionLabel().toUpperCase(false) ;
	}

	public void buttonOK(int theValue) { // TODO First: problems with preset save btn and warning state validation
		if (Validation.getAllValid() != 0) {
			WriteConf.writeConf();
		}
		MessageBox.getGroup().hide();
	}

	public void buttonCancel(int theValue) {
		MessageBox.getGroup().hide();
	}
		
	public static void main(String _args[]) {
		PApplet.main(new String[] { oxsc.MainP.class
				.getName() });
	}
}