package gui;

import java.util.ArrayList;
import java.util.List;

import controlP5.ControlP5;
import controlP5.Controller;
import controlP5.DropdownList;
import controlP5.Numberbox;
import controlP5.Toggle;
import oxsc.MainP;

public class TabSequencer {

	private static final int PIN_NUMBER = 6;
	private static final int PPM_SEQUENCE_NUMBER = 9;
	private static final int SEQUENCE_STEP_MAX_NUMBER = 10;

	private static Toggle[] pinsTgl = new Toggle[PIN_NUMBER];
	private static Numberbox minVolt6Nbox;
	private static Numberbox minCellNbox;
	private static Toggle[] ppmSequTgl = new Toggle[PPM_SEQUENCE_NUMBER];
	private static Toggle sequLowTgl;
	private static DropdownList sequChoiceDdl;
//	private static Button addStepBtn;
//	private static Button removeStepBtn;

	private static List<Object> controllers = new ArrayList<>();

	public TabSequencer(ControlP5 cp5) {
		cp5.getTab("sequencer")
		   .setHeight(20)
		   .setColorLabel(MainP.white)
		   .setColorForeground(MainP.tabGray)
		   .setColorBackground(MainP.darkBackGray)
		   .setColorActive(MainP.orangeAct)
		   .setLabel("Sequencer")
		   .setId(8)
		// .hide();
		;
		cp5.getTab("sequencer").getCaptionLabel().toUpperCase(false);

		// -------- SETUP --------
		// Pins selection
		cp5.addTextlabel("selPins")
		   .setText("Pins selection")
		   .setPosition(5, 126)
		   .setColorValueLabel(0)
		   .setTab("sequencer");

		for (int i = 0; i < PIN_NUMBER; i++) {
			pinsTgl[i] = cp5.addToggle("pinTgl" + (8 + i))
					        .setPosition(92 + i * (15 + 3), 128)
					        .setCaptionLabel("" + (8 + i));
			pinsTgl[i].getCaptionLabel().setPaddingY(0);
			customizeToggle(pinsTgl[i]);
		}

		// Low voltage thresholds
		minVolt6Nbox = cp5.addNumberbox("minVolt6")
				          .setPosition(287, 113)
				          // .setValue(5)
				          .setCaptionLabel("min. volt6 (v)");
		cp5.getTooltip().register(minVolt6Nbox, "minimum volt6 voltage threshold");
		customizeNboxThreshold(minVolt6Nbox);

		minCellNbox = cp5.addNumberbox("minCell")
				         .setPosition(403, 113)
				         // .setValue(5)
				         .setCaptionLabel("min. cell (v)");
		cp5.getTooltip().register(minCellNbox, "minimum cell voltage threshold");
		customizeNboxThreshold(minCellNbox);

		// Active sequence choice
		cp5.addTextlabel("sequChoice")
		   .setText("     Active Sequence(s)")
		   .setPosition(10, 166)
		   .setSize(100, 50)
		   .setColorValueLabel(0)
		   .setMultiline(true)
		   .setTab("sequencer");

		for (int i = 0; i < PPM_SEQUENCE_NUMBER; i++) {
			ppmSequTgl[i] = cp5.addToggle("ppmSequTgl" + (1 + i))
					           .setPosition(99 + i * (15 + 17), 180)
					           .setCaptionLabel("" + (-100 + i * 25));
			ppmSequTgl[i].getCaptionLabel().setPaddingY(1);
			customizeToggle(ppmSequTgl[i]);
		}
		sequLowTgl = cp5.addToggle("ppmSequLow")
				        .setPosition(397, 180)
				        .setCaptionLabel("low");
		sequLowTgl.getCaptionLabel().setPaddingY(1);
		customizeToggle(sequLowTgl);

		// -------- EDITION --------
		// Sequence choice
		cp5.addTextlabel("sequenceChoiceLabel")
		   .setText("Sequ.")
		   .setPosition(85, 217)
		   .setColorValueLabel(0)
		   .setTab("sequencer");
		cp5.getTooltip().register("sequenceChoiceLabel", "Choose the sequence to edit");

		sequChoiceDdl = cp5.addDropdownList("sequChoiceDdl")
				           .setPosition(81, 256)
				           .setSize(47, 180)
				           .setColorForeground(MainP.blueAct)
				           .setBackgroundColor(190) // can't use standard color
				           .setColorActive(MainP.orangeAct)
				           .setItemHeight(20)
				           .setBarHeight(20)
				           .setTab("sequencer")
				           .toUpperCase(false);
		sequChoiceDdl.getCaptionLabel().getStyle().marginTop = 0;
		sequChoiceDdl.getCaptionLabel().align(ControlP5.LEFT, ControlP5.CENTER).setPaddingX(8);
		controllers.add(sequChoiceDdl);

		// Add/Remove step
		cp5.addTextlabel("addRemoveStepLabel")
		   .setText("Step")
		   .setPosition(138, 217)
		   .setColorValueLabel(0)
		   .setTab("sequencer");
		cp5.getTooltip().register("addRemoveStepLabel", "Add/Remove a step from the sequence selected");

		cp5.addButton("addStepBtn")
	       .setColorForeground(MainP.blueAct)
	       .setCaptionLabel("+")
	       .setPosition(135, 235)
	       .setSize(20, 20)
	       .setTab("sequencer");

		cp5.addButton("removeStepBtn")
	       .setColorForeground(MainP.blueAct)
	       .setCaptionLabel("-")
	       .setPosition(156, 235)
	       .setSize(20, 20)
	       .setTab("sequencer");

		// oop Preview
//		cp5.addButton("returnBtn")
//		   .setColorForeground(MainP.blueAct)
//		   .setCaptionLabel("<--")
//		   .setPosition(205, 235)
//		   .setSize(20, 20)
//		   .setTab("sequencer");

		// oop steps
	}

	public static void draw(MainP mainP, ControlP5 cp5) {
	
		// ------------- Setup title -------------
		mainP.fill(MainP.orangeAct);
		mainP.rect(0, 100, mainP.width, 2);
		mainP.stroke(MainP.orangeAct);
		mainP.fill(MainP.lightOrange);
		mainP.rect(0, 100, 55, 20);
		mainP.noStroke();
		mainP.noFill();
	
		mainP.fill(0);
		mainP.textFont(MainP.font16);
		mainP.text("Setup", 7, 115);
		mainP.noFill();
	
		// ------------- Pins selection borders & fill -------------
		mainP.stroke(MainP.darkBackGray);
		for (int i = 0; i < PIN_NUMBER; i++) {
			mainP.rect(90 + i * 18, 115, 18, 31);
			if (pinsTgl[i].getState()) {
				mainP.fill(MainP.lightOrange);
				mainP.rect(90 + i * 18, 115, 18, 31);
			}
			mainP.noFill();
		}
		mainP.noStroke();
	
		// ------------- Volts threshold decorations -------------
		mainP.fill(MainP.blueAct);
		mainP.rect(213, 132, 2, 6);
		mainP.rect(213, 136, 231, 2);
		mainP.rect(442, 132, 2, 6);
		mainP.rect(403, 136, 2, 17);
		mainP.beginShape();
		mainP.vertex(399, 153);
		mainP.vertex(409, 153);
		mainP.vertex(404, 166);
		mainP.endShape(MainP.CLOSE);
		mainP.noFill();
	
		// ------------- PPM decorations -------------
		mainP.fill(MainP.orangeAct);
		mainP.rect(87, 159, 2, 6);
		mainP.rect(87, 159, 295, 2);
		mainP.rect(380, 159, 2, 6);
		mainP.textFont(MainP.fontLabel);
		mainP.text("PPM", 218, 157);
		mainP.noFill();
	
		// ------------- Active sequences borders & fill -------------
		mainP.stroke(MainP.darkBackGray);
		for (int i = 0; i < PPM_SEQUENCE_NUMBER; i++) {
			mainP.rect(90 + i * 32, 166, 32, 34);
			if (ppmSequTgl[i].getState()) {
				mainP.fill(MainP.lightOrange);
				mainP.rect(90 + i * 32, 166, 32, 34);
			}
			mainP.noFill();
		}
		mainP.rect(388, 166, 32, 34);
		if (sequLowTgl.getState()) {
			mainP.fill(MainP.lightOrange);
			mainP.rect(388, 166, 32, 34);
		}
		mainP.noFill();
		mainP.noStroke();
		// graying low voltage sequence toggle if no low voltage threshold sets
		if (minVolt6Nbox.getValue() > 0.0 || minCellNbox.getValue() > 0.0) {
			sequLowTgl.unlock().setColorCaptionLabel(0).setColorBackground(MainP.darkBackGray);
		} else {
			if (sequLowTgl.getState())
				sequLowTgl.setState(false);
			sequLowTgl.lock().setColorCaptionLabel(MainP.grayedColor).setColorBackground(MainP.grayedColor);
		}
	
		// ------------- Edition title -------------
		mainP.fill(MainP.blueAct);
		mainP.rect(0, 209, mainP.width, 2);
		mainP.stroke(MainP.blueAct);
		mainP.fill(MainP.lightBlue);
		mainP.rect(0, 209, 60, 20);
		mainP.noStroke();
		mainP.noFill();
	
		mainP.fill(0);
		mainP.textFont(MainP.font16);
		mainP.text("Edition", 6, 225);
		mainP.noFill();
	
		// --------- Sequence ddl and Add/Rem steps decorations ---------
		mainP.stroke(MainP.blueAct);
		mainP.rect(77, 219, 54, 39);
		mainP.rect(131, 219, 48, 39);
		mainP.noStroke();
	
		// ------------- Preview decorations -------------
		mainP.fill(MainP.lightBlue);
		mainP.stroke(MainP.blueAct);
		mainP.rect(201, 219, 218, 39);
		mainP.noStroke();
		mainP.fill(0);
		mainP.textFont(MainP.fontLabel, 12);
		mainP.text("Preview", 292, 231);
		mainP.noFill();
	
		// ------------- Numberbox mouse-over -------------
		if (cp5.isMouseOver(minVolt6Nbox)) {
			minVolt6Nbox.setColorForeground(MainP.blueAct);
		} else {
			minVolt6Nbox.setColorForeground(MainP.grayedColor);
		}
	
		if (cp5.isMouseOver(minCellNbox)) {
			minCellNbox.setColorForeground(MainP.blueAct);
		} else {
			minCellNbox.setColorForeground(MainP.grayedColor);
		}
	
		// --- Dropdownlist: mouse pressed elsewhere closes list ---
		if (!cp5.isMouseOver(sequChoiceDdl)) {
			if (mainP.mousePressed == true) {
				sequChoiceDdl.close();
			}
		}
	
	}

	public static void populateSequChoiceDdl(String[] itemList) {
		sequChoiceDdl.addItems(itemList);
		sequChoiceDdl.setValue(0);
	}

	public static int getSequenceStepMaxNumber() {
		return SEQUENCE_STEP_MAX_NUMBER;
	}

	private void customizeToggle(Toggle pTgl) {
		pTgl.setColorForeground(MainP.blueAct)
		    .setColorBackground(MainP.darkBackGray)
		    .setColorCaptionLabel(0)
		    .setSize(15, 15)
		    .setTab("sequencer");
		// reposition the Labels
		pTgl.getCaptionLabel().align(ControlP5.CENTER, ControlP5.TOP_OUTSIDE).toUpperCase(false);
		controllers.add(pTgl);
	}

	private void customizeNboxThreshold(Numberbox nBox) {
		nBox.setSize(37, 20)
		    .setRange(0, 99.9f)
		    .setMultiplier(0.1f) // set the sensitivity of the numberbox
		    .setDecimalPrecision(1)
		    .setDirection(Controller.HORIZONTAL)
		    .setTab("sequencer")
		    .setColorCaptionLabel(0)
		    .setColorActive(MainP.orangeAct);
		nBox.getCaptionLabel().align(ControlP5.LEFT_OUTSIDE, ControlP5.CENTER).setPaddingX(3).toUpperCase(false);
		controllers.add(nBox);

	}
}
