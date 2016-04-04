package gui;

import oxsc.MainP;
import oxsc.OXSdata;
import oxsc.Protocol;
import java.util.ArrayList;
import java.util.List;

import controlP5.ControlP5;
import controlP5.Controller;
import controlP5.DropdownList;
import controlP5.Numberbox;

public class TabData {

	//private static ControlP5 cp5;

	private static final int FIELD_NBR = 12;
	private static final boolean DEBUG = false;
	private static DropdownList[] sentDataField = new DropdownList[FIELD_NBR + 1];
	private static DropdownList[] targetDataField = new DropdownList[FIELD_NBR + 1];
	private static Numberbox[] dataMultiplierNBox = new Numberbox[FIELD_NBR + 1];
	private static Numberbox[] dataDividerNBox = new Numberbox[FIELD_NBR + 1];
	private static Numberbox[] dataOffsetNBox = new Numberbox[FIELD_NBR + 1];

	@SuppressWarnings("unused")
	private DropdownList oXsDataField; // TODO later
	@SuppressWarnings("unused")
	private String[] dataDestFieldDisplayList = new String[FIELD_NBR + 1]; // TODO later
	
	private static List<Object> controllers = new ArrayList<>();
	
	public TabData(ControlP5 cp5) {
		
		//TabData.cp5 = cp5;

		cp5.getTab("data").setHeight(20)
						  .setColorForeground(MainP.tabGray)
				          .setColorBackground(0xFF285A28) // color(40, 90, 40)
				          .setColorActive(0xFF3CBE3C) // color(60, 190, 60)
				          .setLabel("DATA sent")
				          .setId(7)
				          .hide()
				          ;
		cp5.getTab("data").getCaptionLabel().toUpperCase(false);

		// Text Labels
		cp5.addTextlabel("sentData").setText("OXS measurement")
				.setPosition(22, 104).setColorValueLabel(0).setTab("data");

		cp5.addTextlabel("DataFS").setText("Telemetry data field")
				.setPosition(162, 104).setColorValueLabel(0).setTab("data");

		cp5.addTextlabel("multiplierL").setText("Multiplier")
				.setPosition(291, 104).setColorValueLabel(0).setTab("data");

		cp5.addTextlabel("dividerL").setText("Divider").setPosition(347, 104)
				.setColorValueLabel(0).setTab("data");

		cp5.addTextlabel("offsetL").setText("Offset").setPosition(400, 104)
				.setColorValueLabel(0).setTab("data");

		for (int i = 1; i <= FIELD_NBR; i++) {
			// Transmitted DATA field
			sentDataField[i] = cp5.addDropdownList("sentDataField" + i)
					              .setColorForeground(MainP.orangeAct)
					              .setBackgroundColor(190) // can't use standard color
					              .setColorActive(MainP.blueAct)
					              .setPosition(10, 146 - 25 + i * 25)
					              .setSize(135, 376 - 25 * i).setItemHeight(20)
					              .setBarHeight(20).setTab("data");
			sentDataField[i].getCaptionLabel().getStyle().marginTop = 2;
			sentDataField[i].getCaptionLabel().set("----------");
			sentDataField[i].toUpperCase(false);
			controllers.add(sentDataField[i]);

			// Telemetry target DATA fields  old ->SMART PORT DATA field
			targetDataField[i] = cp5.addDropdownList("targetDataField" + i)
					                .setColorForeground(MainP.orangeAct)
					                .setBackgroundColor(190) // can't use standard color
					                .setColorActive(MainP.blueAct)
					                .setPosition(150, 146 - 25 + i * 25)
					                .setSize(135, 376 - 25 * i)
					                .setItemHeight(20)
					                .setBarHeight(20)
					                .setTab("data");
			targetDataField[i].addItem("----------", 0);
			targetDataField[i].setValue(0);
			targetDataField[i].getCaptionLabel().getStyle().marginTop = 2;
			targetDataField[i].toUpperCase(false);
			controllers.add(targetDataField[i]);
			
			// Data sent multiplier
			dataMultiplierNBox[i] = cp5.addNumberbox("dataMultiplierNBox" + i)
					                   .setColorActive(MainP.blueAct)
					                   .setPosition(300, 125 - 25 + i * 25)
					                   .setSize(40, 20)
					                   // set the sensitivity of the numberbox
					                   .setRange(-1000, 1000)
					                   .setMultiplier(0.5f)
					                   // change the control direction to left/right
					                   .setDecimalPrecision(0)
					                   .setDirection(Controller.HORIZONTAL)
					                   .setValue(1).setCaptionLabel("")
					                   .setTab("data");
			cp5.getTooltip().register(dataMultiplierNBox[i], "- Default: 1 -");
			controllers.add(dataMultiplierNBox[i]);

			// Data sent divider
			dataDividerNBox[i] = cp5.addNumberbox("dataDividerNBox" + i)
					                .setColorActive(MainP.blueAct)
					                .setPosition(350, 125 - 25 + i * 25)
					                .setSize(40, 20)
					                .setRange(1, 1000)
					                .setMultiplier(0.5f)
					                .setDecimalPrecision(0)
					                .setDirection(Controller.HORIZONTAL)
					                .setValue(1)
					                .setCaptionLabel("")
					                .setTab("data");
			cp5.getTooltip().register(dataDividerNBox[i], "- Default: 1 -");
			controllers.add(dataDividerNBox[i]);

			// Data sent offset
			dataOffsetNBox[i] = cp5.addNumberbox("dataOffsetNBox" + i)
					               .setColorActive(MainP.blueAct)
					               .setPosition(400, 125 - 25 + i * 25)
					               .setSize(40, 20)
					               .setRange(-999, 999)
					               .setMultiplier(0.5f) 
					               .setDecimalPrecision(0)
					               .setDirection(Controller.HORIZONTAL) 
					               .setValue(0).setCaptionLabel("")
					               .setTab("data");
			cp5.getTooltip().register(dataOffsetNBox[i], "- Default: 0 -");
			controllers.add(dataOffsetNBox[i]);
		}
		
		// dropdownlist overlap
		for ( int i = FIELD_NBR; i >= 1; i-- ) {
			sentDataField[i].bringToFront() ;
			//cp5.getGroup("hubDataField" + i).bringToFront() ;
			targetDataField[i].bringToFront() ;
		}
	}

	public static int getFieldNbr() {
		return FIELD_NBR;
	}
	
	public static DropdownList getSentDataField(int i) {
		return sentDataField[i];
	}
	
	public static DropdownList getTargetDataField(int i) {
		return targetDataField[i];
	}
	
	public static void setTargetDataFieldItem(int i, String displayName) {
		for (int j = 1; j < Protocol.getDataList().length; j++ ) {
			// TODO second remove ??
		}
	}

	public static Numberbox[] getDataMultiplierNBox() {
		return dataMultiplierNBox;
	}

	public static Numberbox[] getDataDividerNBox() {
		return dataDividerNBox;
	}

	public static Numberbox[] getDataOffsetNBox() {
		return dataOffsetNBox;
	}
	
	public static List<Object> getControllers() {
		return controllers;
	}

	public static void populateSentDataFields() {
		for (int i = 1; i <= FIELD_NBR; i++) {
			sentDataField[i].clear();
			for (int j = 0; j < OXSdata.getList().size(); j++)
				sentDataField[i]
						.addItem(OXSdata.getItem(j).getDisplayName(), j);
		}
	}

	public static void resetSentDataFields() { // TODO every time OXSdataList changes
		for (int i = 1; i <= FIELD_NBR; i++) {
			String ddlFieldDisplay = TabData.getSentDataField(i).getCaptionLabel().getText();// oXSdataFieldDisplay[i];
			if (DEBUG) {
				System.out.println("Data field n°" + i);
			}
			for (int j = 0; j < OXSdata.getList().size(); j++) {
				if (DEBUG) {
					System.out.println("OXSdata id " + j);
				}
				if (OXSdata.getItem(j).getDisplayName().contains(ddlFieldDisplay)) {
					break;
				} else if (j == OXSdata.getList().size() - 1) {
					sentDataField[i].setValue(0);
					if (DEBUG) {
						System.out.println("reset OXSdata " + OXSdata.getItem(j).getDisplayName() + " id " + j + ": "
								+ ddlFieldDisplay);
					}
				}
			}
		}
	}

	public static void populateTargetDataFields() {
		for (int i = 1; i <= FIELD_NBR; i++) {
			targetDataField[i].clear();
			for (int j = 0; j < Protocol.getDataList().length; j++)
				targetDataField[i].addItem(Protocol.getDataList()[j][1], j); 
		}
	}

	public static void resetTargetDataFields() {
		for (int i = 1; i <= FIELD_NBR; i++) {
			/*
			 * String ddlFieldDisplay = TabData.getTargetDataField(i)
			 * .getCaptionLabel().getText(); System.out.println(
			 * "Target Data field n°" + i); for (int j = 0; j <
			 * OXSdata.getList().size(); j++) { System.out.println("OXSdata id "
			 * + j); if (OXSdata.getItem(j).getDisplayName()
			 * .contains(ddlFieldDisplay)) { break; } else if (j ==
			 * OXSdata.getList().size() - 1) { sentDataField[i].setValue(0);
			 * System.out.println("reset OXSdata " +
			 * OXSdata.getItem(j).getDisplayName() + " id " + j + ": " +
			 * ddlFieldDisplay); } }
			 */
			targetDataField[i].setValue(0);
		}
	}
	
	public static void draw(MainP mainP) {
		mainP.fill(10);
		mainP.rect(298, 454, 124, 34);
		// Load and Save preset buttons hide
		for (int i = 1; i <= FIELD_NBR; i++) {
			if (sentDataField[i].isOpen() || targetDataField[i].isOpen()) {
				FileManagement.getLoadPresetBtn().hide();
				FileManagement.getSavePresetBtn().hide();
				break;
			} else {
				FileManagement.getLoadPresetBtn().show();
				FileManagement.getSavePresetBtn().show();
			}
		}
	}

}
