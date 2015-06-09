package gui;

import oxsc.MainP;
import controlP5.ControlP5;
import controlP5.Controller;
import controlP5.DropdownList;

//import processing.core.PApplet;

public class TabVoltage {

	private final ControlP5 cp5;
	
	private static final int voltNbr = 6;
	private static DropdownList ddlNbrCells;

	// private final PApplet p ; // TODO check if needed

	public TabVoltage(ControlP5 cp5) {

		this.cp5 = cp5;
		// this.p = p;

		cp5.getTab("voltage")
		   .setHeight(20)
		   .setColorForeground(MainP.tabGray)
		   .setColorBackground(MainP.darkBackGray)
		   .setColorActive(MainP.blueAct)
		   .setLabel("Voltage...")
		   .setId(3)
		   .hide();		
		cp5.getTab("voltage").getCaptionLabel().toUpperCase(false);

		// Voltage 1-6 toggle
		cp5.addTextlabel("voltages")
		   .setText("Voltage number")
		   .setPosition(10, 145)
		   .setColorValueLabel(0)
		   .setTab("voltage");
		cp5.getProperties().remove(cp5.getController("voltages"));

		for (int i = 1; i <= voltNbr; i++) {
			cp5.addToggle("volt" + i)
			   .setCaptionLabel("" + i)
			   .setPosition(128 + 55 * (i - 1), 147)
			   .setTab("voltage");
			customizeToggleVolt(cp5.getController("volt" + i));
		}

		// Voltage 1-6 pin
		cp5.addTextlabel("voltPin")
		   .setText("Pin number")
		   .setPosition(10, 173)
	   	   .setColorValueLabel(0)
	   	   .setTab("voltage");
		cp5.getProperties().remove(cp5.getController("voltPin"));

		for (int i = 1; i <= voltNbr; i++) {
			cp5.addDropdownList("ddlVolt" + i)
			   .setPosition(113 + 55 * (i - 1), 192)
			   .setTab("voltage");
			customizeDdlVpin(cp5.get(DropdownList.class, "ddlVolt" + i));
			cp5.get(DropdownList.class, "ddlVolt" + i).setValue(-1);
			cp5.getProperties().remove(cp5.getGroup("ddlVolt" + i), "ListBoxItems");
		}

		// Voltage 1-6 divider factor
		cp5.addTextlabel("voltDivider")
		   .setText("Divider factor                                                                                           ")
		   .setPosition(10, 204)
		   .setColorValueLabel(0)
		   .setTab("voltage");
		cp5.getProperties().remove(cp5.getController("voltDivider"));
		cp5.getTooltip().register("voltDivider", "- Default: 1 -");

		for (int i = 1; i <= voltNbr; i++) {
			cp5.addNumberbox("dividerVolt" + i)
			   .setPosition(113 + 55 * (i - 1), 202)
			   .setSize(45, 20)
			   .setColorActive(MainP.blueAct)
			   .setDecimalPrecision(2)
			   .setRange((float) 0.01, (float) 99.99)
			   .setMultiplier((float) 0.01) // set the sensitivity of the numberbox
			   .setDirection(Controller.HORIZONTAL) // change the control direction to left/right
			   .setValue(1)
			   .setCaptionLabel("")
			   .setTab("voltage");
		}

		// Voltage 1-6 Offset
		cp5.addTextlabel("voltOffset")
		   .setText("Offset (mV)                                                                                              ")
		   .setPosition(10, 233)
		   .setColorValueLabel(0)
		   .setTab("voltage");
		cp5.getProperties().remove(cp5.getController("voltOffset"));
		cp5.getTooltip().register("voltOffset", "- Default: 0 -");

		for (int i = 1; i <= voltNbr; i++) {
			cp5.addNumberbox("offsetVolt" + i)
			   .setPosition(113 + 55 * (i - 1), 231)
			   .setSize(45, 20)
			   .setColorActive(MainP.blueAct)
			   .setDecimalPrecision(0)
			   .setRange(-5000, 5000)
			   .setMultiplier(1) // set the sensitivity of  the numberbox
			   .setDirection(Controller.HORIZONTAL) // change the control direction to left/right
			   .setValue(0)
			   .setCaptionLabel("")
			   .setTab("voltage");
		}

		// Cells monitoring -> Number of Cells
		cp5.addToggle("cells").setPosition(148, 296)
		   .setCaptionLabel("Battery cells monitoring")
		   .setColorForeground(MainP.orangeAct)
		   .setColorBackground(MainP.darkBackGray)
		   .setColorActive(MainP.blueAct)
		   .setColorCaptionLabel(0)
		   .setSize(15, 15).setTab("voltage");
		cp5.getController("cells").getCaptionLabel()
				 				  .align(ControlP5.LEFT_OUTSIDE, ControlP5.CENTER)
				 				  .setPaddingX(10);
		cp5.getController("cells").getCaptionLabel().toUpperCase(false);

		cp5.addTextlabel("NbrCells")
		   .setText("Number of Cells")
		   .setPosition(190, 295)
		   .setColorValueLabel(0)
		   .setTab("voltage");
		cp5.getProperties().remove(cp5.getController("NbrCells"));

		ddlNbrCells = cp5.addDropdownList("ddlNbrCells")
						 .setColorForeground(MainP.orangeAct)
						 .setColorBackground(MainP.darkBackGray)
						 .setColorActive(MainP.blueAct)
						 .setPosition(288, 314)
						 .setSize(25, 140)
						 .setItemHeight(20)
						 .setBarHeight(20)
						 .setTab("voltage")
						 ;
		/*
		 * for ( int i = 1; i <= 6; i++ ) { ddlNbrCells.addItem("" + i, i) ; }
		 */
		ddlNbrCells.getCaptionLabel().getStyle().marginTop = 2;
		ddlNbrCells.getCaptionLabel().set("");
		ddlNbrCells.toUpperCase(false);
		cp5.getProperties().remove(cp5.getGroup("ddlNbrCells"), "ListBoxItems");
		
		// dropdownlist overlap
		ddlNbrCells.bringToFront() ;
		for ( int i = 1; i <= voltNbr; i++ ) {
			cp5.getGroup("ddlVolt" + i).bringToFront() ;
		}

	}

	public static int getVoltnbr() {
		return voltNbr;
	}

	public static DropdownList getDdlNbrCells() {
		return ddlNbrCells;
	}

	public void populateNbrCells() {
		ddlNbrCells.clear();
		for (int i = 1; i <= voltNbr; i++) {
			if (MainP.aVolt[i] != null) {
				ddlNbrCells.addItem("" + i, i);
			} else {
				if (cp5.getGroup("ddlNbrCells").getValue() >= i) { // change NbrCells if not possible
					if (i == 1) {
						ddlNbrCells.addItem("-", 0);
					} // set NbrCells to "-" if volt1 == null
					cp5.getGroup("ddlNbrCells").setValue(i - 1);
				}
				return;
			}
		}
	}

	public static void customizeDdlVpin(DropdownList ddlV) {
		ddlV.setSize(45, 200) ;
		ddlV.setColorForeground(MainP.orangeAct) ;
		ddlV.setColorBackground(MainP.darkBackGray) ;
		ddlV.setColorActive(MainP.blueAct) ;
		ddlV.setItemHeight(20) ;
		ddlV.setBarHeight(20) ;
		ddlV.getCaptionLabel().set(" ") ;
		ddlV.getCaptionLabel().setPaddingX(12) ;
		ddlV.getCaptionLabel().getStyle().marginTop = 2 ;
	
		ddlV.addItem(" --", -1) ;
		ddlV.addItems(MainP.analogPins) ;
		//ddlv.setValue(-1) ;
	
		ddlV.toUpperCase(false) ;
	}

	public static void customizeToggleVolt(Controller<?> tglV) {
		tglV.setColorForeground(MainP.orangeAct) ;
		tglV.setColorBackground(MainP.darkBackGray) ;
		tglV.setColorActive(MainP.blueAct) ;
		tglV.setColorCaptionLabel(0) ;
		tglV.setSize(15, 15) ;
	
		// reposition the Labels
		tglV.getCaptionLabel().align(ControlP5.CENTER, ControlP5.TOP_OUTSIDE).setPaddingX(10) ;	
		tglV.getCaptionLabel().toUpperCase(false) ;
	}

}
