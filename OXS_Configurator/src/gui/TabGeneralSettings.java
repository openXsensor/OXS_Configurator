package gui;

import oxsc.MainP;
import processing.core.PApplet;
import controlP5.ControlP5;
import controlP5.Controller;
import controlP5.DropdownList;
import controlP5.Numberbox;
import controlP5.Tab;
import controlP5.Textfield;
import controlP5.Textlabel;
import controlP5.Toggle;

public class TabGeneralSettings {
	
	@SuppressWarnings("unused")
	private final ControlP5 cp5 ;
	@SuppressWarnings("unused")
	private final PApplet p ; // TODO check if needed

	private static Tab general;
	private static Textfield oxsDir;
	private static DropdownList serialPinDdl;
	private static DropdownList protocolDdl;
	private static Textlabel sensorIDTextlabel;
	private static DropdownList sensorIDDdl;
	private static DropdownList voltRefChoiceDdl;
	private static Numberbox arduinoVccNBox;
	private static Toggle saveEpromTgl;
	private static Textlabel resetButtonPinTextLabel;
	private static DropdownList resetBtnPinDdl;
	private static Toggle varioTgl;
	private static Toggle vario2Tgl;
	private static Toggle airSpeedTgl;
	private static Toggle voltageTgl;
	private static Toggle currentTgl;
	private static Toggle temperatureTgl;
	private static Toggle rpmTgl;
	
	private static final String[] sensorIDs = new String[] { "0x1B", "0xBA",
			"0x39", "0x16", "0x95", "0x34" }; // Sensor ID array
	
	public TabGeneralSettings(PApplet p, ControlP5 cp5) {
		
		this.cp5 = cp5;
		this.p = p;
		
		    general = cp5.getTab("default")
		                 .setHeight(20)
		                 .setColorLabel(MainP.white)
		                 .setColorForeground(MainP.tabGray)
		                 .setColorBackground(MainP.darkBackGray)
		                 .setColorActive(MainP.orangeAct)
		                 .setLabel("GENERAL Settings")
		                 .setId(0)
		                 ;
		    general.getCaptionLabel().toUpperCase(false) ;

		    // OXS directory
		    oxsDir = cp5.addTextfield("oxsDirectory")
		                .setCaptionLabel("OXS directory  ")
		                .setPosition(95, 109)
		                .setColorCaptionLabel(0)
		                .setSize(310, 22)
		                .setAutoClear(false)
		                ;
		    oxsDir.getCaptionLabel().align(ControlP5.LEFT_OUTSIDE, ControlP5.CENTER) ;
		    oxsDir.getCaptionLabel().toUpperCase(false) ;
		    cp5.getTooltip().register("oxsDirectory", "Choose OXS source directory") ;
		  
		    cp5.addButton("oxsDirButton")
		       .setColorForeground(MainP.blueAct)
		       .setCaptionLabel(". . .")
		       .setPosition(410, 110)
		       .setSize(25, 20)
		       ;
		    cp5.getTooltip().register("oxsDirButton", "Choose OXS source directory") ;

		    cp5.addTextlabel("serialPinlabel")
		       .setText("Serial output Pin number            ")
		       .setPosition(10, 142)
		       .setColorValueLabel(0)
		       ;
		    cp5.getProperties().remove(cp5.getController("serialPinlabel")) ;
		    cp5.getTooltip().register("serialPinlabel", "Choose the serial output Pin number - Default: 4 -") ;

		 // Serial output pin
		    serialPinDdl = cp5.addDropdownList("serialPin")
		                      .setPosition(160, 161)
		                      .setSize(25, 300)
		                      .setColorForeground(MainP.blueAct)
		                      .setBackgroundColor(MainP.backDdlGray)
		                      .setItemHeight(20)
		                      .setBarHeight(20)
		                      ;
		    serialPinDdl.getCaptionLabel().set(" ") ;
		    serialPinDdl.getCaptionLabel().getStyle().marginTop = 2 ;
		    serialPinDdl.addItem("2", 2) ;
		    serialPinDdl.addItem("4", 4) ;
		    serialPinDdl.setValue(4) ;
		    cp5.getProperties().remove(serialPinDdl, "ListBoxItems") ;

		 // Protocol choice
		    cp5.addTextlabel("protocol")
		       .setText("Protocol                                   ")
		       .setPosition(10, 172)
		       .setColorValueLabel(0)
		       .setTab("default")
		       ;
		    //protocol.captionLabel().toUpperCase(false) ;
		    cp5.getProperties().remove(cp5.getController("protocol")) ;
		    cp5.getTooltip().register("protocol", "Choose protocol") ;

		    protocolDdl = cp5.addDropdownList("protocolChoice")
		                     .setPosition(100, 191)
		                     .setSize(105, 300)
		                     .setColorForeground(MainP.blueAct)
		                     .setBackgroundColor(MainP.backDdlGray)
		                     .setItemHeight(20)
		                     .setBarHeight(20)
		                     ;
		    protocolDdl.getCaptionLabel().set("Choose") ;
		    protocolDdl.getCaptionLabel().getStyle().marginTop = 2 ;
		    protocolDdl.addItem("FrSky", 1) ;
		    protocolDdl.addItem("Multiplex", 2) ;
		    protocolDdl.toUpperCase(false) ;
		    cp5.getProperties().remove(protocolDdl, "ListBoxItems") ;

		    // Sensor ID choice
		    sensorIDTextlabel = cp5.addTextlabel("sensorIDlabel")
		                           .setText("Sensor ID                    ")
		                           .setPosition(290, 172)
		                           .setColorValueLabel(0x000000)
		                           .setTab("default")
		                           ;
		    cp5.getProperties().remove(sensorIDTextlabel) ;
		    cp5.getTooltip().register("sensorIDlabel", "Choose S.Port sensor ID - Default: 0x1B -") ;
		    
		    sensorIDDdl = cp5.addDropdownList("sensorID")
		    		         .setPosition(355, 191)
		    		         .setSize(50, 300)
		    		         .setColorForeground(MainP.blueAct)
		    		         .setBackgroundColor(MainP.backDdlGray)
		    		         .setItemHeight(20)
		    		         .setBarHeight(20)
		    		         ;
		    sensorIDDdl.getCaptionLabel().align(ControlP5.LEFT, ControlP5.CENTER).setPaddingX(8) ;
		    sensorIDDdl.addItems(sensorIDs) ;
		    sensorIDDdl.setValue(0) ;
		    sensorIDDdl.toUpperCase(false) ;
		    cp5.getProperties().remove(sensorIDDdl, "ListBoxItems") ;
		    
		    // Voltage reference  
		    cp5.addTextlabel("voltRef")
		       .setText("Voltage reference                                   ")
		       .setPosition(10, 214)
		       .setColorValueLabel(0)
		       .setTab("default")
		       ;
		    cp5.getProperties().remove(cp5.getController("voltRef")) ;
		    cp5.getTooltip().register("voltRef", "Choose voltage reference") ;
		  
		    voltRefChoiceDdl = cp5.addDropdownList("voltRefChoice")
						          .setPosition(160, 232)
						          .setSize(95, 100)
						          .setColorForeground(MainP.blueAct)
						          .setBackgroundColor(MainP.backDdlGray)
						          .setItemHeight(20)
						          .setBarHeight(20)
						          ;
		    voltRefChoiceDdl.getCaptionLabel().set("Choose") ;
		    voltRefChoiceDdl.getCaptionLabel().getStyle().marginTop = 2 ;
		    voltRefChoiceDdl.addItem("  Arduino VCC", 1) ;
		    voltRefChoiceDdl.addItem("Internal (1.1V)", 2) ;
		    voltRefChoiceDdl.setValue(1) ;
		    voltRefChoiceDdl.toUpperCase(false) ;
		    cp5.getProperties().remove(voltRefChoiceDdl, "ListBoxItems") ;
		    
		    arduinoVccNBox = cp5.addNumberbox("arduinoVccNb")
					            .setPosition(265, 211)
					            .setSize(37, 20)
					            .setRange(0, (float) 9.99)
					            .setMultiplier((float) 0.01)                    // set the sensitifity of the numberbox
					            .setDecimalPrecision(2)
					            .setDirection(Controller.HORIZONTAL)    // change the control direction to left/right
					            .setValue(5)
					            .setCaptionLabel("volts")
					            .setColorCaptionLabel(0)
					            ;
		    arduinoVccNBox.getCaptionLabel().align(ControlP5.RIGHT_OUTSIDE, ControlP5.CENTER).setPaddingX(5) ;
		    arduinoVccNBox.getCaptionLabel().toUpperCase(false) ;
		    cp5.getTooltip().register("arduinoVccNb", "Arduino alimentation voltage") ;
		  
		    // Save to EEPROM
		    saveEpromTgl = cp5.addToggle("saveEprom")
						      .setPosition(145, 261)
						      .setCaptionLabel("Save data to EEPROM")
						      ;
		    customizeToggleSensor(saveEpromTgl) ;
		    saveEpromTgl.getCaptionLabel().align(ControlP5.LEFT_OUTSIDE, ControlP5.CENTER).setPaddingX(8) ;
		  
		    // Reset button pin
		    resetButtonPinTextLabel = cp5.addTextlabel("resetButtonPinLabel")
		                                 .setText("Reset button Pin number           ")
		                                 .setPosition(210, 260)
		                                 .setColorValueLabel(0)
		                                 ;
		    cp5.getProperties().remove(resetButtonPinTextLabel) ;
		    cp5.getTooltip().register("resetButtonPinLabel", "- Default: 10 -") ;
		  
		    resetBtnPinDdl = cp5.addDropdownList("resetButtonPin")
						        .setPosition(355, 279)
						        .setSize(30, 180)
						        .setColorForeground(MainP.blueAct)
						        .setBackgroundColor(MainP.backDdlGray)
						        .setItemHeight(20)
						        .setBarHeight(20)
						        ;
		    resetBtnPinDdl.getCaptionLabel().set(" ") ;
		    resetBtnPinDdl.getCaptionLabel().getStyle().marginTop = 2 ;
		    for ( int i = 2; i <= 12; i++ ) {
		    	resetBtnPinDdl.addItem("" + i, i) ;
		    }
		    resetBtnPinDdl.setValue(10) ;
		    cp5.getProperties().remove(resetBtnPinDdl, "ListBoxItems") ;
		  
		    cp5.addTextlabel("sensors")
		       .setText("SENSORS")
		       .setPosition(191, 295)
		       .setColorValueLabel(MainP.white)
		       ;
		    cp5.getProperties().remove(cp5.getController("sensors")) ;
		    cp5.getTooltip().register("sensors", "Sensors settings") ;
		  
		    // Toggle buttons
		    varioTgl = cp5.addToggle("vario")
					      .setPosition(145, 336)
					      .setCaptionLabel("Vario 1        ")
					      ;
		    customizeToggleSensor(varioTgl) ;
		    
		    vario2Tgl = cp5.addToggle("vario2")
		                   .setPosition(265, 336)
		                   .setCaptionLabel("Vario 2        ")
		                   ;
		    customizeToggleSensor(vario2Tgl) ;
		    
		    airSpeedTgl = cp5.addToggle("airSpeed")
		                     .setPosition(385, 336)
		                     .setCaptionLabel("Air Speed       ")
		                     ;
		    customizeToggleSensor(airSpeedTgl) ;
		  
		    voltageTgl = cp5.addToggle("voltage")
		                    .setPosition(145, 361)
		                    .setCaptionLabel("Voltage / Other   ")
		                    ;
		    customizeToggleSensor(voltageTgl) ;
		  
		    currentTgl = cp5.addToggle("current")
		                    .setPosition(265, 361)
		                    .setCaptionLabel("Current        ")
		                    ;
		    customizeToggleSensor(currentTgl) ;
		  
		    temperatureTgl = cp5.addToggle("temperature")
		                        .setPosition(85, 315)
		                        .setCaptionLabel("Temperature  ")
		                        ;
		    customizeToggleSensor(temperatureTgl) ;
		   // if (!tempActive) {
		    temperatureTgl.hide() ;
		   // }

		    rpmTgl = cp5.addToggle("rpm")
		                .setPosition(385, 361)
		                .setCaptionLabel("RPM          ")
		                ;
		    customizeToggleSensor(rpmTgl) ;
		    
		    // dropdownlist overlap
		    resetBtnPinDdl.bringToFront() ;
		    voltRefChoiceDdl.bringToFront() ;
		    protocolDdl.bringToFront() ;
		    sensorIDDdl.bringToFront() ;
		    serialPinDdl.bringToFront() ;
	}

	public static Textfield getOxsDir() { return oxsDir; }

	public static DropdownList getSerialPinDdl() {
		return serialPinDdl;
	}

	public static DropdownList getProtocolDdl() {
		return protocolDdl;
	}

	public static DropdownList getSensorIDDdl() {
		return sensorIDDdl;
	}

	public static DropdownList getVoltRefChoiceDdl() {
		return voltRefChoiceDdl;
	}

	public static Numberbox getArduinoVccNBox() {
		return arduinoVccNBox;
	}

	public static Toggle getSaveEpromTgl() {
		return saveEpromTgl;
	}

	public static DropdownList getResetBtnPinDdl() {
		return resetBtnPinDdl;
	}

	public static Toggle getVarioTgl() {
		return varioTgl;
	}

	public static Toggle getVario2Tgl() {
		return vario2Tgl;
	}

	public static Toggle getAirSpeedTgl() {
		return airSpeedTgl;
	}

	public static Toggle getVoltageTgl() {
		return voltageTgl;
	}

	public static Toggle getCurrentTgl() {
		return currentTgl;
	}

	public static Toggle getTemperatureTgl() {
		return temperatureTgl;
	}

	public static Toggle getRpmTgl() {
		return rpmTgl;
	}

	public static void draw(MainP mainP) {
		// separation lines
		mainP.stroke(MainP.darkBackGray) ;
		mainP.noSmooth() ;
		mainP.line(10, 200, 440, 200) ;
		mainP.line(10, 241, 440, 241) ;
		mainP.noStroke() ;
		mainP.smooth() ;
	
		// Sensor ID graying
		if ( protocolDdl.getValue() != 1 ) {
			sensorIDTextlabel.setColorValueLabel(MainP.grayedColor) ;
			sensorIDDdl.hide() ;
			mainP.fill(MainP.grayedColor) ;
			mainP.rect(355, 170, 50, 20) ;
		} else {
			sensorIDTextlabel.setColorValueLabel(0) ;
			sensorIDDdl.show() ;
		}
		
		// Voltage graying
		if ( voltRefChoiceDdl.getValue() == 2 ) {
			arduinoVccNBox.lock() 
						  .setColorBackground(MainP.grayedColor) 
						  .setColorValueLabel(MainP.grayedColor) 
						  .setColorCaptionLabel(MainP.grayedColor) ;
		} else {
			arduinoVccNBox.unlock()
						  .setColorBackground(MainP.darkBackGray)
						  .setColorValueLabel(MainP.white)
						  .setColorCaptionLabel(mainP.color(0)) ;
		}
	
		if ( saveEpromTgl.getValue() == 0 ) {
			resetButtonPinTextLabel.setColorValueLabel(MainP.grayedColor) ;
			resetBtnPinDdl.hide() ;
			mainP.fill(MainP.grayedColor) ;
			mainP.rect(355, 258, 30, 20) ;
		} else {
			resetButtonPinTextLabel.setColorValueLabel(mainP.color(0)) ;
			resetBtnPinDdl.show() ;
			mainP.fill(MainP.lightOrange) ;               // rectangle border filled
			mainP.rect(10, 258, 152, 20) ;
			mainP.noFill() ;
		}
		mainP.stroke(MainP.darkBackGray) ;                        // rectangle border
		mainP.noFill() ;
		mainP.rect(10, 258, 152, 20) ;
		mainP.noStroke() ;
	
		if ( !MainP.tempActive ) {
			temperatureTgl.setColorCaptionLabel(MainP.grayedColor)
						  .setColorBackground(MainP.grayedColor) ;
		}
	
		// SENSORS part
		mainP.fill(255, 128, 0) ;
		mainP.rect(mainP.width / 2 - 35, 295, 69, 18) ;
		mainP.rect(0, 310, mainP.width, 3) ;
		mainP.noFill() ;
	
		if ( varioTgl.getValue() == 1 ) {                 // Vario 2 enabled if vario 1
			vario2Tgl.unlock()
			         .setColorCaptionLabel(0)
			         .setColorBackground(MainP.darkBackGray) ;
		} else {
			vario2Tgl.lock()
					 .setColorCaptionLabel(MainP.grayedColor)
					 .setColorBackground(MainP.grayedColor) ;
		}
	
		// Sensors table
		if ( varioTgl.getValue() == 1 ) {
			mainP.fill(MainP.lightOrange) ;
			mainP.rect(45, 331, 120, 25) ;
			mainP.noFill() ;
		}
		if ( vario2Tgl.getValue() == 1 ) {
			mainP.fill(MainP.lightOrange) ;
			mainP.rect(165, 331, 120, 25) ;
			mainP.noFill() ;
		}
		if ( airSpeedTgl.getValue() == 1 ) {
			mainP.fill(MainP.lightOrange) ;
			mainP.rect(285, 331, 120, 25) ;
			mainP.noFill() ;
		}
		if ( voltageTgl.getValue() == 1 ) {
			mainP.fill(MainP.lightOrange) ;
			mainP.rect(45, 356, 120, 25) ;
			mainP.noFill() ;
		}
		if ( currentTgl.getValue() == 1 ) {
			mainP.fill(MainP.lightOrange) ;
			mainP.rect(165, 356, 120, 25) ;
			mainP.noFill() ;
		}
		if ( rpmTgl.getValue() == 1 ) {
			mainP.fill(MainP.lightOrange) ;
			mainP.rect(285, 356, 120, 25) ;
			mainP.noFill() ;
		}
	
		mainP.stroke(MainP.darkBackGray) ;
		mainP.rect(45, 331, 120, 25) ;
		mainP.rect(165, 331, 120, 25) ;
		mainP.rect(285, 331, 120, 25) ;
		mainP.rect(45, 356, 120, 25) ;
		mainP.rect(165, 356, 120, 25) ;
		mainP.rect(285, 356, 120, 25) ;
		mainP.noStroke() ;
	}

	public static void customizeToggleSensor(Controller<?> tglS) {
		tglS.setColorForeground(MainP.blueAct) ;
		tglS.setColorBackground(MainP.darkBackGray) ;
		tglS.setColorCaptionLabel(0) ;
		tglS.setSize(15, 15) ;
		tglS.setTab("default") ;
		// reposition the Labels
		tglS.getCaptionLabel().align(ControlP5.LEFT_OUTSIDE, ControlP5.CENTER) ;		
		tglS.getCaptionLabel().toUpperCase(false) ;
	}
}
