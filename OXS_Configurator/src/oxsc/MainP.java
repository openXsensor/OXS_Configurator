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
import gui.TabAirSpeed;
import gui.TabCurrent;
import gui.TabData;
import gui.TabGeneralSettings;
import gui.TabPPM;
import gui.TabVario;
import gui.TabVoltage;

import java.io.File;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PGraphics;
import processing.core.PShape;
import processing.data.StringList;
import controlP5.Button;
import controlP5.ControlEvent;
import controlP5.ControlP5;
import controlP5.Controller;
import controlP5.Group;
import controlP5.Numberbox;
import controlP5.Slider;
import controlP5.Textarea;
import controlP5.Textfield;

public class MainP extends PApplet {

	/**
	 * ??
	 */
	private static final long serialVersionUID = 1L;

	public static boolean tempActive = false; // Defines temperature sensor availability

	public static String day = (day() < 10) ? "0" + day() : "" + day();
	public static String month = (month() < 10) ? "0" + month() : "" + month();

	public static final int tabGray = 0xFFC8C8C8; // gray 200
	public static final int backDdlGray = 0xFFFFFFFF; // gray 190
	public static final int topBottomGray = 0xFF969696; // gray 150
	public static final int darkBackGray = 0xFF464646; // gray 70
	public static final int blueAct = 0xFF0FA5FF; // color(15, 165, 255);
	public static final int lightBlue = 0xFFB5CFDB; // color(181, 207, 219);
	public static final int orangeAct = 0xFFFF8000; // color(255, 128, 0);
	public static final int lightOrange = 0xFFDBBFB5; // color(219, 191, 181);
	public static final int grayedColor = 0xFF9B9B9B; // color(155);
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

	public ControlP5 cp5;

	public static Group messageBox; // TODO later
	public static Textarea messageBoxTextarea;
	public static Button buttonOKBtn;
	// Popup message; // TODO later

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

	public static StringList messageList = new StringList(); // TODO later

	// Variables to set the controllers data type
	int vSpeedMin;
	int vSpeedMax;
	int sensMinMax;
	int vSpeedMinMax;
	int ppmRngMinMax;
	int ppmRngMin;
	int ppmRngMax;
	int varioHysteresis;
	int ppmRngSensMinMax;
	int ppmSensMinMax;
	int ppmVspeedSwMin;
	int ppmVspeedSwMax;
	int outClimbRateMinMax;
	int aSpeedReset;
	int ppmRngCompMinMax;
	int ppmCompMinMax;

	int mBoxWidth = 400; // TODO later
	int mBoxHeight = 320; // TODO later

	public static Protocol protocol;

	public static Sensor vario;
	public static Sensor vario2;
	public static Sensor airSpeed;
	public static Sensor[] aVolt = new Sensor[TabVoltage.getVoltnbr() + 1];
	public static Sensor current;
	public static Sensor rpm;
	public static Sensor ppm;


	public void setup() {
		
		size(450, 460) ;
		noStroke() ;

		cp5 = new ControlP5(this) ;

		cp5.getProperties().addSet("Preset");

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

		//message = new Popup(this, cp5);           // TODO later popup




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
		tabGenSet = new TabGeneralSettings(this, cp5) ;

		// ---------------- PPM settings : Tabs Vario + Air Speed ----------------
		tabPPM = new TabPPM(this, cp5) ;

		// ---------------------------- Tab 1 : Vario settings ------------------------------
		tabVario = new TabVario(this, cp5) ;

		// ---------------------------- Tab 2 : Air Speed settings ------------------------------
		tabAirSpeed = new TabAirSpeed(this, cp5) ;

		// ------------------------------ Tab 3 : Voltage settings ------------------------------
		tabVoltage = new TabVoltage(cp5) ;

		// ------------------------------ Tab 4 : Current settings ------------------------------
		tabCurrent = new TabCurrent(cp5) ;

		// ------------------------------ Tab 5 : Temperature settings ------------------------------
		//tab5 = new TabTemperature(this, cp5) ;

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

		// --------------------------------------------------------------------------

		// dropdownlist overlap
		//cp5.getGroup("tempPin").bringToFront() ;

		// Tooltips general settings
		cp5.getTooltip().setDelay(1000) ;
		cp5.getTooltip().getLabel().toUpperCase(false) ;

		createMessageBox() ;                     	     //  Message box creation
		TabGeneralSettings.getProtocolDdl().setValue(1); // Set the protocol ddl value after telemetry fields creation
		new OXSdata("----------", "----------", "noSensor", null) ;

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

		int currentTabId = cp5.getWindow(this).getCurrentTab().getId() ;

		switch( currentTabId ) {

		case 0 :                                        // TAB GENERAL Settings
			TabGeneralSettings.draw(this);
			break ;

		case 1 :                                        // TAB Vario

			TabVario.draw(this);
			break ;

		case 2 :                                        // TAB Air Speed sensor

			stroke(blueAct) ;     // blue border
			strokeWeight(3) ;
			noFill() ;
			rect(4, 106, 442, 148) ;
			line(4, 142, 446, 142) ;
			strokeWeight(1) ;
			noStroke() ;

			TabPPM.drawPPMzone(this) ;

			// separation lines
			stroke(MainP.darkBackGray) ;
			line(10, 184, 440, 184) ;
			noStroke() ;

			if ( cp5.getController("ppm").getValue() == 0 ) {
				cp5.getController("aSpeedReset").lock() ;
				cp5.getController("aSpeedReset").setColorBackground(grayedColor) ;
				cp5.getController("aSpeedReset").setColorForeground(grayedColor) ;
				cp5.getController("aSpeedReset").setColorValueLabel(grayedColor) ;
				cp5.getController("aSpeedReset").setColorCaptionLabel(grayedColor) ;

				cp5.getController("ppmRngCompL").setColorValueLabel(grayedColor) ;
				cp5.getController("ppmRngCompMinMax").lock() ;
				cp5.getController("ppmRngCompMinMax").setColorForeground(grayedColor) ;
				cp5.getController("ppmRngCompMinMax").setColorBackground(grayedColor) ;
				cp5.getController("ppmRngCompMinMax").setColorValueLabel(grayedColor) ;
				cp5.getController("ppmRngCompMinMax").setColorCaptionLabel(grayedColor) ;

				cp5.getController("ppmCompL").setColorValueLabel(grayedColor) ;
				cp5.getController("ppmCompMinMax").lock() ;
				cp5.getController("ppmCompMinMax").setColorForeground(grayedColor) ;
				cp5.getController("ppmCompMinMax").setColorBackground(grayedColor) ;
				cp5.getController("ppmCompMinMax").setColorValueLabel(grayedColor) ;
				cp5.getController("ppmCompMinMax").setColorCaptionLabel(grayedColor) ;

			} else {
				cp5.getController("aSpeedReset").unlock() ;
				cp5.getController("aSpeedReset").setColorBackground(MainP.darkBackGray) ;
				cp5.getController("aSpeedReset").setColorValueLabel(white) ;
				cp5.getController("aSpeedReset").setColorCaptionLabel(color(0)) ;

				cp5.getController("ppmRngCompL").setColorValueLabel(color(0)) ;
				cp5.getController("ppmRngCompMinMax").unlock() ;
				cp5.getController("ppmRngCompMinMax").setColorForeground(blueAct) ;
				cp5.getController("ppmRngCompMinMax").setColorBackground(MainP.darkBackGray) ;
				cp5.getController("ppmRngCompMinMax").setColorValueLabel(white) ;
				cp5.getController("ppmRngCompMinMax").setColorCaptionLabel(color(0)) ;

				cp5.getController("ppmCompL").setColorValueLabel(color(0)) ;
				cp5.getController("ppmCompMinMax").unlock() ;
				cp5.getController("ppmCompMinMax").setColorForeground(blueAct) ;
				cp5.getController("ppmCompMinMax").setColorBackground(MainP.darkBackGray) ;
				cp5.getController("ppmCompMinMax").setColorValueLabel(white) ;
				cp5.getController("ppmCompMinMax").setColorCaptionLabel(color(0)) ;
			}

			break ;

		case 3 :                                                 // TAB Voltage / Other

			TabVoltage.draw(this);
			break ;

		case 4 :                                                            // TAB Current sensor
			/*
		    if ( cp5.getController("currentDir").value() == 0 ) {             // Current grayed switch
		      cp5.getController("currentDirL").setColorValue(grayedColor) ;
		      cp5.getController("currentDir").setColorCaptionLabel(color(0)) ;
		    } else {
		      cp5.getController("currentDirL").setColorValue(color(0)) ;
		      cp5.getController("currentDir").setColorCaptionLabel(grayedColor) ;
		    }
			 */
			break ;

		case 7 :                                                            // TAB DATA sent  dataSentDdlOpen
			fill(10) ;
			rect(298, 414, 124, 34) ;

			for ( int i = 1 ; i <= TabData.getTabDataFieldNbr() ; i++ ) {            // Load and Save preset buttons hide
				if ( TabData.getSentDataField(i).isOpen() || TabData.getTargetDataField(i).isOpen() ) {
					FileManagement.getLoadPresetBtn().hide() ;
					FileManagement.getSavePresetBtn().hide() ;
					break ;
				} else {
					FileManagement.getLoadPresetBtn().show() ;
					FileManagement.getSavePresetBtn().show() ;
				}
			}
			/*
		    // Grayed multiplier + divider + offset if Telemetry data field == DEFAULT
		    for ( int i = 1 ; i <= dataSentFieldNbr ; i++ ) {
		      if ( cp5.getGroup("protocolChoice").value() == 1 ) {
		        if ( cp5.getGroup("hubDataField" + i).value() == 1 ) {
		          cp5.getController("dataMultiplier" + i).lock() ;
		          cp5.getController("dataMultiplier" + i).setColorBackground(grayedColor) ;
		          cp5.getController("dataMultiplier" + i).setColorValueLabel(grayedColor) ;
		          cp5.getController("dataDivider" + i).lock() ;
		          cp5.getController("dataDivider" + i).setColorBackground(grayedColor) ;
		          cp5.getController("dataDivider" + i).setColorValueLabel(grayedColor) ;
		          cp5.getController("dataOffset" + i).lock() ;
		          cp5.getController("dataOffset" + i).setColorBackground(grayedColor) ;
		          cp5.getController("dataOffset" + i).setColorValueLabel(grayedColor) ;
		        } else {
		          cp5.getController("dataMultiplier" + i).unlock() ;
		          cp5.getController("dataMultiplier" + i).setColorBackground(OXSConfigurator._Gray) ;
		          cp5.getController("dataMultiplier" + i).setColorValueLabel(white) ;
		          cp5.getController("dataDivider" + i).unlock() ;
		          cp5.getController("dataDivider" + i).setColorBackground(OXSConfigurator._Gray) ;
		          cp5.getController("dataDivider" + i).setColorValueLabel(white) ;
		          cp5.getController("dataOffset" + i).unlock() ;
		          cp5.getController("dataOffset" + i).setColorBackground(OXSConfigurator._Gray) ;
		          cp5.getController("dataOffset" + i).setColorValueLabel(white) ;
		        }
		      } else if ( cp5.getGroup("protocolChoice").value() == 2 ) {
		        if ( TabData.targetDataField[i].value() == 1 ) {
		          cp5.getController("dataMultiplier" + i).lock() ;
		          cp5.getController("dataMultiplier" + i).setColorBackground(grayedColor) ;
		          cp5.getController("dataMultiplier" + i).setColorValueLabel(grayedColor) ;
		          cp5.getController("dataDivider" + i).lock() ;
		          cp5.getController("dataDivider" + i).setColorBackground(grayedColor) ;
		          cp5.getController("dataDivider" + i).setColorValueLabel(grayedColor) ;
		          cp5.getController("dataOffset" + i).lock() ;
		          cp5.getController("dataOffset" + i).setColorBackground(grayedColor) ;
		          cp5.getController("dataOffset" + i).setColorValueLabel(grayedColor) ;
		        } else {
		          cp5.getController("dataMultiplier" + i).unlock() ;
		          cp5.getController("dataMultiplier" + i).setColorBackground(OXSConfigurator._Gray) ;
		          cp5.getController("dataMultiplier" + i).setColorValueLabel(white) ;
		          cp5.getController("dataDivider" + i).unlock() ;
		          cp5.getController("dataDivider" + i).setColorBackground(OXSConfigurator._Gray) ;
		          cp5.getController("dataDivider" + i).setColorValueLabel(white) ;
		          cp5.getController("dataOffset" + i).unlock() ;
		          cp5.getController("dataOffset" + i).setColorBackground(OXSConfigurator._Gray) ;
		          cp5.getController("dataOffset" + i).setColorValueLabel(white) ;
		        }
		      }
		    }
			 */
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
		// TODO in 1 loop ?? later
		for ( int i = 1; i <= TabData.getTabDataFieldNbr(); i++ ) {
			if ( cp5.isMouseOver ( TabData.getDataMultiplierNBox()[i] ) ) {
				TabData.getDataMultiplierNBox()[i].setColorForeground(orangeAct) ;
			} else {
				TabData.getDataMultiplierNBox()[i].setColorForeground(grayedColor) ;
			}
		}

		for ( int i = 1; i <= TabData.getTabDataFieldNbr(); i++ ) {
			if ( cp5.isMouseOver ( TabData.getDataDividerNBox()[i] ) ) {
				TabData.getDataDividerNBox()[i].setColorForeground(orangeAct) ;
			} else {
				TabData.getDataDividerNBox()[i].setColorForeground(grayedColor) ;
			}
		}

		for ( int i = 1; i <= TabData.getTabDataFieldNbr(); i++ ) {
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

		if ( !cp5.isMouseOver ( TabPPM.getPpmPin() ) ) {
			if (mousePressed == true) {
				TabPPM.getPpmPin().close() ;
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

		if ( !cp5.isMouseOver ( TabVario.getClimbPin() ) ) {
			if (mousePressed == true) {
				TabVario.getClimbPin().close() ;
			}
		}

		for ( int i = 1; i <= TabVoltage.getVoltnbr(); i++ ) {
			if ( !cp5.isMouseOver ( TabVoltage.getDdlVolt()[i] ) ) {
				TabVoltage.getDdlVolt()[i].close() ;
				if (mousePressed == true) {
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
		for ( int i = 1; i <= TabData.getTabDataFieldNbr(); i++ ) {
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

		for ( int i = 1; i <= TabData.getTabDataFieldNbr(); i++ ) {
			if ( !cp5.isMouseOver ( TabData.getTargetDataField(i) ) ) {
				if (mousePressed == true) {
					TabData.getTargetDataField(i).close() ;
				}
			}
		}

		// ----------------- TAB DATA sent display -----------------

		if (vario != null || airSpeed != null
				|| TabGeneralSettings.getVoltageTgl().getValue() == 1
				|| current != null /* || temperature != null */|| rpm != null) {
			cp5.getTab("data").show();
		} else {
			cp5.getTab("data").hide();
		}

	}

	public void controlEvent(ControlEvent theEvent) {
		// DropdownList is of type ControlGroup.
		// A controlEvent will be triggered from inside the ControlGroup class.
		// therefore you need to check the originator of the Event with if (theEvent.isGroup()) to avoid an error message thrown by controlP5.

		if ( theEvent.isFrom(cp5.getTab("vario")) ) {           //  Tab vario : display PPM parameters
			cp5.getController("ppm").setTab("vario") ;
			cp5.getController("ppm").show() ;
			cp5.getController("ppmPinL").setTab("vario") ;
			cp5.getController("ppmPinL").show() ;
			cp5.getController("ppmRngL").setTab("vario") ;
			cp5.getController("ppmRngL").show() ;
			cp5.getController("ppmRngMin").setTab("vario") ;
			cp5.getController("ppmRngMin").show() ;
			cp5.getController("ppmRngMax").setTab("vario") ;
			cp5.getController("ppmRngMax").show() ;
			cp5.getGroup("ppmPin").setTab("vario") ;
		} else if ( theEvent.isFrom(cp5.getTab("airSpeed")) ) {  //  Tab Air Speed : display PPM parameters
			cp5.getController("ppm").setTab("airSpeed") ;
			cp5.getController("ppm").show() ;
			cp5.getController("ppmPinL").setTab("airSpeed") ;
			cp5.getController("ppmPinL").show() ;
			cp5.getController("ppmRngL").setTab("airSpeed") ;
			cp5.getController("ppmRngL").show() ;
			cp5.getController("ppmRngMin").setTab("airSpeed") ;
			cp5.getController("ppmRngMin").show() ;
			cp5.getController("ppmRngMax").setTab("airSpeed") ;
			cp5.getController("ppmRngMax").show() ;
			cp5.getGroup("ppmPin").setTab("airSpeed") ;
		}

		if ( theEvent.isFrom(cp5.getController("vSpeedMax")) || theEvent.isFrom(cp5.getController("vSpeedMin")) ) {   //  V speed sensitivity range interaction
			cp5.getController("vSpeedMax").setBroadcast(false) ;
			cp5.getController("vSpeedMin").setBroadcast(false) ;
			cp5.get(Numberbox.class, "vSpeedMax").setRange( cp5.getController("vSpeedMin").getValue(), 1000 ) ;
			cp5.get(Numberbox.class, "vSpeedMin").setRange( 0, cp5.getController("vSpeedMax").getValue() ) ;
			cp5.getController("vSpeedMin").setBroadcast(true) ;
			cp5.getController("vSpeedMax").setBroadcast(true) ;
		}

		// Voltages sensor activation/desactivation
		for (int i = 1; i <= TabVoltage.getVoltnbr(); i++) {
			if (theEvent.isFrom(TabVoltage.getVoltTgl()[i])) {
				switch ((int) theEvent.getController().getValue()) {
				case 1:
					if (aVolt[i] == null) {
						aVolt[i] = new Volt(this, cp5, "volt" + i);
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

		//  Current sensor output offset interaction
		if ( theEvent.isFrom(cp5.getController("currentOutOffsetNb") ) /*|| theEvent.isFrom(cp5.getController("currentOutSensNb"))*/ ) {
			cp5.getController("currentOutOffsetMA").setBroadcast(false) ;
			float currentOutSens = cp5.getController("currentOutSensNb").getValue() ;
			float currentOutOffsetMV = cp5.getController("currentOutOffsetNb").getValue() ;
			cp5.getController("currentOutOffsetMA").setValue( ( currentOutOffsetMV / currentOutSens ) * 1000 ) ;
			cp5.getController("currentOutOffsetMA").setBroadcast(true) ;
		}
		if ( theEvent.isFrom(cp5.getController("currentOutOffsetMA")) ) {
			cp5.getController("currentOutOffsetNb").setBroadcast(false) ;
			float currentOutSens = cp5.getController("currentOutSensNb").getValue() ;
			float currentOutOffsetMA = cp5.getController("currentOutOffsetMA").getValue() ;
			cp5.getController("currentOutOffsetNb").setValue( ( currentOutOffsetMA / 1000 ) * currentOutSens ) ;
			cp5.getController("currentOutOffsetNb").setBroadcast(true) ;
		}

		// Protocol selection - Showing right Telemetry data list in fields
		if (theEvent.isFrom(TabGeneralSettings.getProtocolDdl())) {
			switch ((int) theEvent.getGroup().getValue()) {
			case 1:
					protocol = Protocol.createProtocol("FrSky");
				break;
			case 2:
					protocol = Protocol.createProtocol("Multiplex");
				break;
			}
		}

		// Selecting DEFAULT automatically in Telemetry data fields
		// TODO group controllers ?
		for (int i = 1; i <= TabData.getTabDataFieldNbr(); i++) {

			if (theEvent.isFrom(TabData.getSentDataField(i))) {

				switch (TabData.getSentDataField(i).getCaptionLabel().getText()) {
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
					TabData.getTargetDataField(i).setValue(1); //TODO don't like set method
					break;
				}

				/*
				 * println("oxsdataList : " +
				 * TabData.getSentDataField(i).getCaptionLabel() .getText());
				 */
			}
		}
		/*
		  if (theEvent.isGroup()) {
		    // check if the Event was triggered from a ControlGroup
		    println("event from group : "+theEvent.getGroup().getValue()+" from "+theEvent.getGroup()) ;
		  } else if (theEvent.isController()) {
		    println("event from controller : "+theEvent.getController().getValue()+" from "+theEvent.getController()) ;
		  }

		  if (theEvent.isTab()) {
		    // check if the Event was triggered from a Tab
		    println("event from tab : " + theEvent.getTab().getName() ) ;
		  }
		 */
	}

	public void mouseWheelMoved(java.awt.event.MouseWheelEvent e) {  // Mouse wheel support for scroll bars !!
		super.mouseWheelMoved(e) ;
		cp5.setMouseWheelRotation(e.getWheelRotation()) ;
	}

	void oxsDirButton(int theValue) {
		//println("oxsDir button: "+theValue) ;
		selectFolder("Select OXS source folder:", "folderSelected") ;
	}

	public void vario(boolean theFlag) {
		if (theFlag == true && vario == null) {
			vario = new Vario(this, cp5, "vario");
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

	void vario2(boolean theFlag) {
		if (theFlag == true && vario2 == null) {
			vario2 = new Vario(this, cp5, "vario2");
		} else if (theFlag == false && vario2 != null) {
			vario2.removeSensor();
			vario2 = null;
		}
	}

	void airSpeed(boolean theFlag) {
		if (theFlag == true && airSpeed == null) {
			airSpeed = new AirSpeed(this, cp5, "airSpeed");
			cp5.getTab("airSpeed").show();
		} else if (theFlag == false && airSpeed != null) {
			airSpeed.removeSensor();
			airSpeed = null;
			cp5.getTab("airSpeed").hide();
			if (ppm != null && vario == null)
				TabPPM.getPpmTgl().setValue(0);
		}
	}

	void voltage(boolean theFlag) {
		if (theFlag == true) {
			cp5.getTab("voltage").show();
		} else {
			for (int i = 1; i <= TabVoltage.getVoltnbr(); i++) {
				TabVoltage.getVoltTgl()[i].setValue(0);
			}
			cp5.getTab("voltage").hide();
		}
	}

	void cells(boolean theFlag) { // TODO clean
		if (theFlag == true && aVolt[1] != null) {
			new OXSdata("CELLS", "Cells monitoring", "voltCells", null);
			TabData.populateSentDataFields();
		} else {
			OXSdata.removeFromList("voltCells"); // TODO remove
													// "Cells Monotoring" from
													// ddl display
			TabData.resetSentDataFields();
			// TabData.populateSentDataFields() ;
		}
	}

	void current(boolean theFlag) {
		if (theFlag == true && current == null) {
			current = new Current(this, cp5, "current");
			cp5.getTab("current").show();
		} else if (theFlag == false && current != null) {
			current.removeSensor();
			current = null;
			cp5.getTab("current").hide();
		}
	}

	void temperature(boolean theFlag) {
		if (theFlag == true) {
			cp5.getTab("temperature").show();
		} else {
			cp5.getTab("temperature").hide();
		}
		// println("a toggle event.") ;
	}

	// RPM TAB display
	void rpm(boolean theFlag) {
		if (theFlag == true && rpm == null) {
			rpm = new Rpm(this, cp5, "rpm");
			// cp5.getTab("rpm").show() ;
		} else if (theFlag == false && rpm != null) {
			rpm.removeSensor();
			rpm = null;
			// cp5.getTab("rpm").hide() ;
		}
		// println("a toggle event.") ;
	}

	void ppm(boolean theFlag) {
		if (theFlag == true && ppm == null) {
			ppm = new PPM(this, cp5, "ppm");
		} else if (theFlag == false && ppm != null) {
			ppm.removeSensor();
			ppm = null;
		}
	}

	void about(boolean theFlag) {

		mbClose() ;

		messageList.clear() ;

		messageList.append( "                            OXS Configurator " + Validation.getOxsCversion() + " for OXS " + Validation.getOxsVersion() ) ;
		messageList.append( "                                                       ---" ) ;
		messageList.append( "                         -- OpenXsensor configuration file GUI --" ) ;
		messageList.append( "\n" ) ;
		messageList.append( "Contributors:" ) ;
		messageList.append( "" ) ;
		messageList.append( "- Rainer Schloßhan" ) ;
		messageList.append( "- Bertrand Songis" ) ;
		messageList.append( "- André Bernet" ) ;
		messageList.append( "- Michael Blandford" ) ;
		messageList.append( "- Michel Strens" ) ;
		messageList.append( "- David Laburthe" ) ;
		messageList.append( "" ) ;
		messageList.append( "" ) ;

		String[] messageListArray = messageList.array() ;

		String joinedMessageList = join(messageListArray, "\n") ;

		messageBoxTextarea.setText(joinedMessageList) ;

		buttonOKBtn.setColorForeground(orangeAct) ;
		buttonOKBtn.setColorBackground(color(100)) ;
		buttonOKBtn.setColorActive(blueAct) ;
		messageBox.setBackgroundColor(blueAct) ;
		messageBox.show() ;
	}

	public void loadButton(int theValue) {                                     // Load preset button
		File presetDir = new File( sketchPath("src/Preset/...") ) ;
		selectInput("Select a preset file to load:", "presetLoad", presetDir) ;
	}

	public void saveButton(int theValue) {                                     // Save preset button
		mbClose() ;
		Validation.validationProcess(this, "preset") ;
		if ( Validation.getAllValid() == 2 ) {
			messageBox.hide() ;
		}
		if ( Validation.getAllValid() != 0 ) {
			File presetDir = new File( ("src/Preset/type name") ) ;  // sketchPath("Preset/type name")
			selectOutput("Type preset name to save:", "presetSave", presetDir) ;
		}
	}

	public void presetLoad(File selection) { // TODO preset load
		// PresetManagement.presetLoad(selection);
		if (selection == null) {
			// println("Window was closed or the user hit cancel.") ;
		} else {
			/*
			 * //println("User selected " + selection.getAbsolutePath()) ;
			 * cp5.setBroadcast(false);
			 * cp5.loadProperties(selection.getAbsolutePath()) ;
			 * 
			 * // Hack to keep slider labels alignement
			 * cp5.getController("varioHysteresis").getCaptionLabel().align(
			 * ControlP5.LEFT_OUTSIDE, ControlP5.CENTER).setPaddingX(10) ;
			 * cp5.getController("varioHysteresis").getValueLabel().align(
			 * ControlP5.RIGHT_OUTSIDE, ControlP5.CENTER).setPaddingX(10) ;
			 * cp5.setBroadcast(true);
			 */

		}
	}

	public void presetSave(File selection) { // TODO preset save
		if (selection == null) {
			// println("Window was closed or the user hit cancel.") ;
		} else {
			// println("User selected " + selection.getAbsolutePath());
			// cp5.saveProperties(selection.getAbsolutePath());

			// TODO first only one should be created
			// object.getName - object.getValue  for ex => TabGeneralSettings.getOxsDir() - TabGeneralSettings.getOxsDir().getText()
			@SuppressWarnings("unused")
			PresetManagement newPreset = new PresetManagement();
			PresetManagement.getUiUnits().stream().forEach(uiU -> uiU.stream().forEach(c -> {
				System.out.println(c.getClass().getName() + " - " + c.getClass().toString());
				if (c instanceof controlP5.DropdownList) {
					controlP5.DropdownList co = (controlP5.DropdownList) c;
					System.out.println(co.getCaptionLabel().getText());
				}
				if (c instanceof controlP5.Toggle) {
					controlP5.Toggle co = (controlP5.Toggle) c;
					System.out.println(co.getValue());
				}
			}));
		}
	}

	public void writeConfButton(int theValue) {
		mbOkCancel() ;
		Validation.validationProcess(this, "Config") ;
		if ( Validation.getAllValid() == 0) {
			mbClose() ;
		}
	}

	public void folderSelected(File selection) {
		if (selection == null) {
			//println("Window was closed or the user hit cancel.") ;
		} else {
			//println("User selected " + selection.getAbsolutePath()) ;
			cp5.get(Textfield.class, "oxsDirectory").setText(selection.getAbsolutePath()) ;
		}
	}

	// =========================================== Shortcuts ===========================================

	public void keyPressed() {
		// default properties load/save key combinations are
		// alt+shift+l to load properties
		// alt+shift+s to save properties
		if (key == 's') {
			// cp5.saveProperties(("settings.oxs")) ;
			savePreset();
			cp5.getProperties().print();
		} else if (key == 'l') {
			// cp5.loadProperties(("settings.oxs")) ;
			loadPreset();
			// Hack to keep slider labels alignment
			// cp5.getController("varioHysteresis").getCaptionLabel().align(ControlP5.LEFT_OUTSIDE,
			// ControlP5.CENTER).setPaddingX(10) ;
			// cp5.getController("varioHysteresis").getValueLabel().align(ControlP5.RIGHT_OUTSIDE,
			// ControlP5.CENTER).setPaddingX(10) ;

			cp5.getProperties().print();
		} else if (key == 'c') {
			println("mAmp / step " + mAmpStep());
			println("Current offset " + offsetCurrent());
		} else if (key == 'p') {
			// message.showOk("gxb");
		} else if (key == 'g') {
			TabGeneralSettings.getVarioTgl().setValue(true);
			TabData.getSentDataField(2).setCaptionLabel("Altitude");
			TabData.getTargetDataField(2).setCaptionLabel("Altitude");
		} else if (key == 'd') {
			vario = null;
			// println(vario.getMeasurementName(0)) ;
		}
	}

	// =================================================================================================

	public static float round(float number, float decimal) {      // Rounding function
		return round((number*pow(10, decimal)))/pow(10, decimal) ;
	}

	public static float mVoltStep(int NbrVolt) {    // Voltage measurements milliVolt per ADC step calculation

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

	void customizeSlider(Controller<Slider> sld) {
		sld.setColorForeground(blueAct) ;
		sld.setColorCaptionLabel(0) ;
		sld.setColorValueLabel(0) ;
		sld.setSize(150, 15) ;
		sld.setValue(50) ;
		sld.setTab("vario") ;
		// reposition the Labels for controller 'slider'
		sld.getCaptionLabel().align(ControlP5.LEFT_OUTSIDE, ControlP5.CENTER).setPaddingX(10) ;
		sld.getValueLabel().align(ControlP5.RIGHT_OUTSIDE, ControlP5.CENTER).setPaddingX(10) ;

		sld.getCaptionLabel().toUpperCase(false) ;
	}

	public static void customizeToggle(Controller<?> tgl) {
		tgl.setColorForeground(orangeAct) ;
		tgl.setColorBackground(MainP.darkBackGray) ;
		tgl.setColorActive(blueAct) ;
		tgl.setColorCaptionLabel(0) ;
		tgl.setSize(15, 15) ;

		// reposition the Labels
		tgl.getCaptionLabel().align(ControlP5.LEFT_OUTSIDE, ControlP5.CENTER).setPaddingX(10) ;

		tgl.getCaptionLabel().toUpperCase(false) ;
	}

	public static void customizeRange(Controller<?> rng) {
		rng.setColorForeground(blueAct) ;
		rng.setColorBackground(MainP.darkBackGray) ;
		rng.setColorActive(orangeAct) ;
		//rng.setSize(200, 20) ;
		rng.setColorCaptionLabel(0) ;
		rng.setTab("vario") ;

		//rng.getCaptionLabel().align(ControlP5.LEFT_OUTSIDE, ControlP5.CENTER) ;
		rng.getCaptionLabel().toUpperCase(false) ;
	}

	public void createMessageBox() { // TODO second move to gui package

		// create a group to store the messageBox elements
		messageBox = cp5.addGroup("messageBox", width / 2 - mBoxWidth / 2, 76, mBoxWidth)
				        .setBackgroundHeight(mBoxHeight)
				        .setBackgroundColor(color(240))
				        .setTab("global")
				        .hideBar()
				        .hide()
				        ;

		// add a Textaera to the messageBox.
		messageBoxTextarea = cp5.addTextarea("messageBoxLabel")
		                        .setPosition(5,5)
		                        .setSize(mBoxWidth - 10, mBoxHeight - 48)
		                        .setLineHeight(14)
		                        .setColor(white)
		                        .setColorActive(orangeAct)
		                        //.setBorderColor(color(0))
		                        .setColorBackground(color(120))
		                        .setColorForeground(blueAct)
		                        .setScrollBackground(color(80))
		                        //.setTab("global")
		                        ;
		messageBoxTextarea.moveTo(messageBox) ;

		// OK button to the messageBox.
		buttonOKBtn = cp5.addButton(this, "btnOK", "buttonOK", 0, width / 2 - 60, 218, 80, 30) ;
		buttonOKBtn.moveTo(messageBox) ;
		buttonOKBtn.setColorForeground(color(blueAct)) ;
		buttonOKBtn.setColorBackground(color(100)) ;
		buttonOKBtn.setColorActive(color(orangeAct)) ;
		buttonOKBtn.getCaptionLabel().setFont(font20) ;
		buttonOKBtn.getCaptionLabel().toUpperCase(false) ;
		buttonOKBtn.getCaptionLabel().align(ControlP5.CENTER, ControlP5.CENTER).setPaddingX(10) ;

		// Cancel button to the messageBox.
		cp5.addButton(this, "btnCancel", "buttonCancel", 0, mBoxWidth / 2 + 5, mBoxHeight - 37, 80, 30)
		   .moveTo(messageBox)
		   .setCaptionLabel("Cancel")
		   .setColorForeground(blueAct)
		   .setColorBackground(color(100))
		   .setColorActive(orangeAct)
		   .hide()
		   ;
		cp5.getController("buttonCancel").getCaptionLabel().setFont(font20) ;
		cp5.getController("buttonCancel").getCaptionLabel().toUpperCase(false) ;
		cp5.getController("buttonCancel").getCaptionLabel().align(ControlP5.CENTER, ControlP5.CENTER).setPaddingX(10) ;

	}

	public void mbOkCancel() {
		buttonOKBtn.setPosition(mBoxWidth / 2 - 80 - 5, mBoxHeight - 37) ;
		buttonOKBtn.setSize(80, 30) ;
		buttonOKBtn.setCaptionLabel("OK") ;

		cp5.getController("buttonCancel").show() ;
	}

	public void mbClose() {
		buttonOKBtn.setPosition(mBoxWidth / 2 - 40 , mBoxHeight - 37) ;
		buttonOKBtn.setSize(80, 30) ;
		buttonOKBtn.setCaptionLabel("CLOSE") ;

		cp5.getController("buttonCancel").hide() ;
	}

	public void buttonOK(int theValue) {
		if (Validation.getAllValid() != 0) {
			WriteConf.writeConf();
		}
		messageBox.hide();
	}

	public void buttonCancel(int theValue) {
		messageBox.hide();
	}
		
	// Preset

	public void savePreset() {
		// Tab Gen.
		cp5.getProperties().copy(cp5.getController("oxsDirectory"), "Preset");
		cp5.getProperties().copy(cp5.getGroup("serialPin"), "Preset");
		cp5.getProperties().copy(cp5.getGroup("sensorID"), "Preset");
		cp5.getProperties().copy(cp5.getGroup("protocolChoice"), "Preset");
		cp5.getProperties().copy(cp5.getGroup("voltRefChoice"), "Preset");
		cp5.getProperties().copy(cp5.getController("arduinoVccNb"), "Preset");
		cp5.getProperties().copy(cp5.getController("saveEprom"), "Preset");
		cp5.getProperties().copy(cp5.getGroup("resetButtonPin"), "Preset");

		cp5.getProperties().copy(cp5.getController("vario"), "Preset");
		cp5.getProperties().copy(cp5.getController("vario2"), "Preset");
		cp5.getProperties().copy(cp5.getController("airSpeed"), "Preset");
		cp5.getProperties().copy(cp5.getController("voltage"), "Preset");
		cp5.getProperties().copy(cp5.getController("current"), "Preset");
		cp5.getProperties().copy(cp5.getController("rpm"), "Preset");

		// PPM
		cp5.getProperties().copy(cp5.getController("ppm"), "Preset");
		cp5.getProperties().copy(cp5.getGroup("ppmPin"), "Preset");
		cp5.getProperties().copy(cp5.getController("ppmRngMin"), "Preset");
		cp5.getProperties().copy(cp5.getController("ppmRngMax"), "Preset");

		// Tab Vario
		cp5.getProperties().copy(cp5.getController("ppmRngSensMinMax"), "Preset");
		cp5.getProperties().copy(cp5.getController("ppmSensMinMax"), "Preset");
		cp5.getProperties().copy(cp5.getGroup("vSpeed1"), "Preset");
		cp5.getProperties().copy(cp5.getGroup("vSpeed2"), "Preset");
		cp5.getProperties().copy(cp5.getController("ppmVspeedSwMin"), "Preset");
		cp5.getProperties().copy(cp5.getController("ppmVspeedSwMax"), "Preset");

		cp5.getProperties().copy(cp5.getController("sensMinMax"), "Preset");
		cp5.getProperties().copy(cp5.getController("vSpeedMin"), "Preset");
		cp5.getProperties().copy(cp5.getController("vSpeedMax"), "Preset");

		cp5.getProperties().copy(cp5.getController("varioHysteresis"), "Preset");

		cp5.getProperties().copy(cp5.getController("analogClimb"), "Preset");
		cp5.getProperties().copy(cp5.getGroup("climbPin"), "Preset");
		cp5.getProperties().copy(cp5.getController("outClimbRateMinMax"), "Preset");

		cp5.saveProperties("testPreset.ser", "Preset");

	}

	public void loadPreset() {
		cp5.loadProperties(("testPreset.ser"));

		// Hack to keep slider labels alignement
		cp5.getController("varioHysteresis").getCaptionLabel().align(ControlP5.LEFT_OUTSIDE, ControlP5.CENTER).setPaddingX(10) ;
		cp5.getController("varioHysteresis").getValueLabel().align(ControlP5.RIGHT_OUTSIDE, ControlP5.CENTER).setPaddingX(10) ;
	}

	public static void main(String _args[]) {
		PApplet.main(new String[] { oxsc.MainP.class
				.getName() });
	}
}