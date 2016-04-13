package gui;

import oxsc.MainP;

import java.util.ArrayList;
import java.util.List;

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
	private final ControlP5 cp5;

	private static Tab genTab;
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

	private static List<Object> controllers = new ArrayList<>();

	// Sensor ID array
	private static final String[] sensorIDs = new String[] { "0x1B", "0xBA", "0x39", "0x16", "0x95", "0x34" };

	public TabGeneralSettings(ControlP5 cp5) {

		this.cp5 = cp5;

		genTab = cp5.getTab("default")
		            .setHeight(20)
		            .setColorLabel(MainP.white)
		            .setColorForeground(MainP.tabGray)
		            .setColorBackground(MainP.darkBackGray)
		            .setColorActive(MainP.orangeAct)
		            .setLabel("GENERAL Settings")
		            .setId(0);
		genTab.getCaptionLabel().toUpperCase(false);

		// OXS directory
		oxsDir = cp5.addTextfield("oxsDir")
				    .setCaptionLabel("OXS directory  ")
				    .setPosition(95, 109)
				    .setColorCaptionLabel(0)
				    .setSize(310, 22)
				    .setAutoClear(false);
		oxsDir.getCaptionLabel().align(ControlP5.LEFT_OUTSIDE, ControlP5.CENTER);
		oxsDir.getCaptionLabel().toUpperCase(false);
		cp5.getTooltip().register(oxsDir, "Choose OXS source directory");
		controllers.add(oxsDir);

		cp5.addButton("oxsDirButton")
		   .setColorForeground(MainP.blueAct)
		   .setCaptionLabel(". . .")
		   .setPosition(410, 110)
		   .setSize(25, 20);
		cp5.getTooltip().register("oxsDirButton", "Choose OXS source directory");

		cp5.addTextlabel("serialPinlabel")
	       .setText("Serial output Pin number            ")
	       .setPosition(10, 142)
	       .setColorValueLabel(0);
		cp5.getTooltip().register("serialPinlabel", "Choose the serial output Pin number - Default: 4 -");

		// Serial output pin
		serialPinDdl = cp5.addDropdownList("serialPinDdl").setPosition(160, 161).setSize(25, 300);
		serialPinDdl.getCaptionLabel().set(" ");
		serialPinDdl.addItem("2", 2);
		serialPinDdl.addItem("4", 4);
		serialPinDdl.setValue(4);
		customizeDdl(serialPinDdl);

		// Protocol choice
		cp5.addTextlabel("protocol")
		   .setText("Protocol                                   ")
		   .setPosition(10, 172)
		   .setColorValueLabel(0).setTab("default");
		// protocol.captionLabel().toUpperCase(false) ;
		cp5.getTooltip().register("protocol", "Choose protocol");

		protocolDdl = cp5.addDropdownList("protocolDdl").setPosition(100, 191).setSize(105, 300);
		protocolDdl.getCaptionLabel().set("Choose");
		protocolDdl.addItem("FrSky", 1);
		protocolDdl.addItem("Multiplex", 2);
		customizeDdl(protocolDdl);

		// Sensor ID choice
		sensorIDTextlabel = cp5.addTextlabel("sensorIDTextlabel")
				               .setText("Sensor ID                    ")
				               .setPosition(290, 172)
				               .setColorValueLabel(0x000000)
				               .setTab("default");
		cp5.getTooltip().register(sensorIDTextlabel, "Choose S.Port sensor ID - Default: 0x1B -");

		sensorIDDdl = cp5.addDropdownList("sensorIDDdl").setPosition(355, 191).setSize(50, 300);
		sensorIDDdl.addItems(sensorIDs);
		sensorIDDdl.setValue(0);
		customizeDdl(sensorIDDdl);
		sensorIDDdl.getCaptionLabel().getStyle().marginTop = 0;
		sensorIDDdl.getCaptionLabel().align(ControlP5.LEFT, ControlP5.CENTER).setPaddingX(8);

		// Voltage reference
		cp5.addTextlabel("voltRef")
		   .setText("Voltage reference                                   ")
		   .setPosition(10, 214)
		   .setColorValueLabel(0)
		   .setTab("default");
		cp5.getTooltip().register("voltRef", "Choose voltage reference");

		voltRefChoiceDdl = cp5.addDropdownList("voltRefChoiceDdl").setPosition(160, 232).setSize(95, 100);
		voltRefChoiceDdl.getCaptionLabel().set("Choose");
		voltRefChoiceDdl.addItem("  Arduino VCC", 1);
		voltRefChoiceDdl.addItem("Internal (1.1V)", 2);
		voltRefChoiceDdl.setValue(1);
		customizeDdl(voltRefChoiceDdl);

		arduinoVccNBox = cp5.addNumberbox("arduinoVccNBox")
				            .setPosition(265, 211)
				            .setSize(37, 20)
				            .setRange(0, 9.99f)
				            .setMultiplier(0.01f) // set the sensitifity of the numberbox
				            .setDecimalPrecision(2)
				            .setDirection(Controller.HORIZONTAL) // change the control direction to left/right
				            .setValue(5)
				            .setCaptionLabel("volts")
				            .setColorCaptionLabel(0);
		arduinoVccNBox.getCaptionLabel().align(ControlP5.RIGHT_OUTSIDE, ControlP5.CENTER).setPaddingX(5);
		arduinoVccNBox.getCaptionLabel().toUpperCase(false);
		cp5.getTooltip().register(arduinoVccNBox, "Arduino alimentation voltage");
		controllers.add(arduinoVccNBox);

		// Save to EEPROM
		saveEpromTgl = cp5.addToggle("saveEpromTgl").setPosition(145, 261).setCaptionLabel("Save data to EEPROM");
		customizeToggleSensor(saveEpromTgl);
		saveEpromTgl.getCaptionLabel().align(ControlP5.LEFT_OUTSIDE, ControlP5.CENTER).setPaddingX(8);

		// Reset button pin
		resetButtonPinTextLabel = cp5.addTextlabel("resetButtonPinTextLabel")
				                     .setText("Reset button Pin number           ")
				                     .setPosition(210, 260)
				                     .setColorValueLabel(0);
		cp5.getTooltip().register(resetButtonPinTextLabel, "- Default: 10 -");

		resetBtnPinDdl = cp5.addDropdownList("resetBtnPinDdl").setPosition(355, 279).setSize(30, 180);
		resetBtnPinDdl.getCaptionLabel().set(" ");
		for (int i = 2; i <= 12; i++) {
			resetBtnPinDdl.addItem("" + i, i);
		}
		resetBtnPinDdl.setValue(10);
		customizeDdl(resetBtnPinDdl);

		// "SENSORS" title
		cp5.addTextlabel("sensors").setText("SENSORS").setPosition(191, 295).setColorValueLabel(MainP.white);
		cp5.getTooltip().register("sensors", "Sensors settings");

		// Toggle buttons
		varioTgl = cp5.addToggle("varioTgl").setPosition(145, 336).setCaptionLabel("Vario 1        ");
		customizeToggleSensor(varioTgl);

		vario2Tgl = cp5.addToggle("vario2Tgl").setPosition(265, 336).setCaptionLabel("Vario 2        ");
		customizeToggleSensor(vario2Tgl);

		airSpeedTgl = cp5.addToggle("airSpeedTgl").setPosition(385, 336).setCaptionLabel("Air Speed       ");
		customizeToggleSensor(airSpeedTgl);

		voltageTgl = cp5.addToggle("voltageTgl").setPosition(145, 361).setCaptionLabel("Voltage / Other   ");
		customizeToggleSensor(voltageTgl);

		currentTgl = cp5.addToggle("currentTgl").setPosition(265, 361).setCaptionLabel("Current        ");
		customizeToggleSensor(currentTgl);

		temperatureTgl = cp5.addToggle("temperatureTgl").setPosition(85, 315).setCaptionLabel("Temperature  ");
		// customizeToggleSensor(temperatureTgl) ;
		// if (!tempActive) {
		temperatureTgl.hide();
		// }

		rpmTgl = cp5.addToggle("rpmTgl").setPosition(385, 361).setCaptionLabel("RPM          ");
		customizeToggleSensor(rpmTgl);

		// dropdownlist overlap
		resetBtnPinDdl.bringToFront();
		voltRefChoiceDdl.bringToFront();
		protocolDdl.bringToFront();
		sensorIDDdl.bringToFront();
		serialPinDdl.bringToFront();
	}

	public static void draw(MainP mainP, ControlP5 cp5) {
		// separation lines
		mainP.stroke(MainP.darkBackGray);
		mainP.noSmooth();
		mainP.line(10, 200, 440, 200);
		mainP.line(10, 241, 440, 241);
		mainP.noStroke();
		mainP.smooth();

		// Sensor ID graying
		if (protocolDdl.getCaptionLabel().getText().equals("Multiplex")) {
			sensorIDTextlabel.setColorValueLabel(MainP.grayedColor);
			sensorIDDdl.hide();
			mainP.fill(MainP.grayedColor);
			mainP.rect(355, 170, 50, 20);
		} else {
			sensorIDTextlabel.setColorValueLabel(0);
			sensorIDDdl.show();
		}

		// Voltage graying
		if (voltRefChoiceDdl.getCaptionLabel().getText().equals("Internal (1.1V)")) {
			arduinoVccNBox.lock()
			              .setColorBackground(MainP.grayedColor)
			              .setColorValueLabel(MainP.grayedColor)
			              .setColorCaptionLabel(MainP.grayedColor);
		} else {
			arduinoVccNBox.unlock()
			              .setColorBackground(MainP.darkBackGray)
			              .setColorValueLabel(MainP.white)
			              .setColorCaptionLabel(mainP.color(0));
		}

		if (saveEpromTgl.getValue() == 0.0) {
			resetButtonPinTextLabel.setColorValueLabel(MainP.grayedColor);
			resetBtnPinDdl.hide();
			mainP.fill(MainP.grayedColor);
			mainP.rect(355, 258, 30, 20);
		} else {
			resetButtonPinTextLabel.setColorValueLabel(mainP.color(0));
			resetBtnPinDdl.show();
			mainP.fill(MainP.lightOrange); // rectangle border filled
			mainP.rect(10, 258, 152, 20);
			mainP.noFill();
		}
		mainP.stroke(MainP.darkBackGray); // rectangle border
		mainP.noFill();
		mainP.rect(10, 258, 152, 20);
		mainP.noStroke();

		if (!MainP.tempActive) {
			temperatureTgl.setColorCaptionLabel(MainP.grayedColor).setColorBackground(MainP.grayedColor);
		}

		// "SENSORS" graphic part
		mainP.fill(255, 128, 0);
		mainP.rect(mainP.width / 2 - 35, 295, 69, 18);
		mainP.rect(0, 310, mainP.width, 3);
		mainP.noFill();

		// Vario 2 enabled if vario 1
		if (varioTgl.getValue() == 1.0) {
			vario2Tgl.unlock().setColorCaptionLabel(0).setColorBackground(MainP.darkBackGray);
		} else {
			vario2Tgl.lock().setColorCaptionLabel(MainP.grayedColor).setColorBackground(MainP.grayedColor);
		}

		// Sensors table
		if (varioTgl.getValue() == 1.0) {
			mainP.fill(MainP.lightOrange);
			mainP.rect(45, 331, 120, 25);
			mainP.noFill();
		}
		if (vario2Tgl.getValue() == 1.0) {
			mainP.fill(MainP.lightOrange);
			mainP.rect(165, 331, 120, 25);
			mainP.noFill();
		}
		if (airSpeedTgl.getValue() == 1.0) {
			mainP.fill(MainP.lightOrange);
			mainP.rect(285, 331, 120, 25);
			mainP.noFill();
		}
		if (voltageTgl.getValue() == 1.0) {
			mainP.fill(MainP.lightOrange);
			mainP.rect(45, 356, 120, 25);
			mainP.noFill();
		}
		if (currentTgl.getValue() == 1.0) {
			mainP.fill(MainP.lightOrange);
			mainP.rect(165, 356, 120, 25);
			mainP.noFill();
		}
		if (rpmTgl.getValue() == 1.0) {
			mainP.fill(MainP.lightOrange);
			mainP.rect(285, 356, 120, 25);
			mainP.noFill();
		}

		mainP.stroke(MainP.darkBackGray);
		mainP.rect(45, 331, 120, 25);
		mainP.rect(165, 331, 120, 25);
		mainP.rect(285, 331, 120, 25);
		mainP.rect(45, 356, 120, 25);
		mainP.rect(165, 356, 120, 25);
		mainP.rect(285, 356, 120, 25);
		mainP.noStroke();

		// ----------------- Texfield and Numberbox mouse-over -----------------
		if (cp5.isMouseOver(oxsDir)) {
			oxsDir.setColorForeground(MainP.blueAct);
		} else {
			oxsDir.setColorForeground(MainP.tabGray);
		}

		if (cp5.isMouseOver(arduinoVccNBox)) {
			arduinoVccNBox.setColorForeground(MainP.blueAct);
		} else {
			arduinoVccNBox.setColorForeground(MainP.grayedColor);
		}

		// ----------------- Dropdownlist: mouse pressed elsewhere closes list -----------------
		if (!cp5.isMouseOver(protocolDdl)) {
			if (mainP.mousePressed == true) {
				protocolDdl.close();
			}
		}

		if (!cp5.isMouseOver(serialPinDdl)) {
			if (mainP.mousePressed == true) {
				serialPinDdl.close();
			}
		}

		if (!cp5.isMouseOver(sensorIDDdl)) {
			if (mainP.mousePressed == true) {
				sensorIDDdl.close();
			}
		}

		if (!cp5.isMouseOver(voltRefChoiceDdl)) {
			if (mainP.mousePressed == true) {
				voltRefChoiceDdl.close();
			}
		}

		if (!cp5.isMouseOver(resetBtnPinDdl)) {
			if (mainP.mousePressed == true) {
				resetBtnPinDdl.close();
			}
		}
	}

	public static String writeRefVolt() {
		StringBuilder conf = new StringBuilder();
		if (voltRefChoiceDdl.getCaptionLabel().getText().equals("  Arduino VCC")) {
			conf.append("#define REFERENCE_VOLTAGE " + (int) (arduinoVccNBox.getValue() * 100) + "0\n");
		} else {
			conf.append("#define USE_INTERNAL_REFERENCE\n");
		}
		return conf.toString();
	}

	public static void customizeToggleSensor(Toggle tglS) {
		tglS.setColorForeground(MainP.blueAct)
		    .setColorBackground(MainP.darkBackGray)
		    .setColorCaptionLabel(0)
		    .setSize(15, 15)
		    .setTab("default");
		// reposition the Labels
		tglS.getCaptionLabel().align(ControlP5.LEFT_OUTSIDE, ControlP5.CENTER).toUpperCase(false);
		controllers.add(tglS);
	}

	public static void customizeDdl(DropdownList ddlS) {
		ddlS.setColorForeground(MainP.blueAct)
		    .setBackgroundColor(190) // can't use standard color
		    .setColorActive(MainP.orangeAct)
		    .setItemHeight(20)
		    .setBarHeight(20)
		    .setTab("default");
		ddlS.getCaptionLabel().getStyle().marginTop = 2;
		ddlS.toUpperCase(false);
		controllers.add(ddlS);
	}

	public static Tab getGenTab() {
		return genTab;
	}

	public static Textfield getOxsDir() {
		return oxsDir;
	}

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

	public static List<Object> getControllers() {
		return controllers;
	}
}
