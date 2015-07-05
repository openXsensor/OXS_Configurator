package gui;

import oxsc.MainP;
import controlP5.ControlP5;
import controlP5.Controller;
import controlP5.DropdownList;
import controlP5.Numberbox;

public class TabCurrent {
	
	//private final ControlP5 cp5 ;
	
	private static DropdownList currentPinDdl;
	private static Numberbox currentOutSensNBox;
	private static Numberbox currentOutOffsetNBox;
	private static Numberbox currentOutOffsetMaNBox;
	private static Numberbox currentDivNBox;

	public TabCurrent(ControlP5 cp5) {
		
		//this.cp5 = cp5;
		
	    cp5.getTab("current")
	       .setHeight(20)
	       .setColorForeground(MainP.tabGray)
	       .setColorBackground(MainP.darkBackGray)
	       .setColorActive(MainP.blueAct)
	       .setLabel("Current")
	       .setId(4)
	       .hide()
	       ;
	    cp5.getTab("current").getCaptionLabel().toUpperCase(false) ;
	  
	    // Current pin
	    cp5.addTextlabel("currentPinL")
	       .setText("Current sensor Pin number")
	       .setPosition(9, 127)
	       .setColorValueLabel(0)
	       .setTab("current")
	       ;
	    cp5.getProperties().remove(cp5.getController("currentPinL")) ;
	  
	    currentPinDdl = cp5.addDropdownList("currentPin")
	                       .setColorForeground(MainP.orangeAct)
	                       .setColorBackground(MainP.darkBackGray)
	                       .setColorActive(MainP.blueAct)
	                       .setPosition(165, 146)
	                       .setSize(45, 200)
	                       .setItemHeight(20)
	                       .setBarHeight(20)
	                       .setTab("current")
	                       ;
	    currentPinDdl.addItem(" --", -1) ;
	    currentPinDdl.addItems(MainP.analogPins) ;
	    currentPinDdl.getCaptionLabel().setPaddingX(12) ;
	    currentPinDdl.getCaptionLabel().getStyle().marginTop = 2 ;
	    currentPinDdl.setValue(-1) ;
	    currentPinDdl.toUpperCase(false) ;
	    cp5.getProperties().remove(currentPinDdl, "ListBoxItems") ;
	  
	    // Current sensor output sensitivity
	    currentOutSensNBox = cp5.addNumberbox("currentOutSensNb")
	                            .setColorActive(MainP.blueAct)
	                            .setPosition(165, 160)
	                            .setSize(45, 20)
	                            .setRange(0, 999)
	                            // set the sensitifity of the numberbox
	                            .setMultiplier(1)
	                            .setDecimalPrecision(0)
	                            // change the control direction to left/right
	                            .setDirection(Controller.HORIZONTAL)
	                            .setCaptionLabel("Output sensitivity (mV/A)    ")
	                            .setColorCaptionLabel(0)
	                            .setTab("current")
	                            ;
	    currentOutSensNBox.getCaptionLabel().align(ControlP5.LEFT_OUTSIDE, ControlP5.CENTER).setPaddingX(7) ;
	    currentOutSensNBox.getCaptionLabel().toUpperCase(false) ;
	  
	    // Current sensor offset
	    currentOutOffsetNBox = cp5.addNumberbox("currentOutOffsetNb")
	                              .setBroadcast(false)
	                              .setColorActive(MainP.blueAct)
	                              .setPosition(165, 195)
	                              .setSize(45, 20)
	                              .setRange(-5000, 5000)
	                              .setMultiplier(1)
	                              .setDecimalPrecision(0)
	                              .setDirection(Controller.HORIZONTAL)
	                              .setCaptionLabel("Output offset                  mV")
	                              .setColorCaptionLabel(0)
	                              .setTab("current")
	                              ;
	    currentOutOffsetNBox.getCaptionLabel().align(ControlP5.LEFT_OUTSIDE, ControlP5.CENTER).setPaddingX(5) ;
	    currentOutOffsetNBox.getCaptionLabel().toUpperCase(false) ;
	    //cp5.getTooltip().register("currentOutOffsetNb", "...") ;
	  
	    currentOutOffsetMaNBox = cp5.addNumberbox("currentOutOffsetMA")
	                                .setBroadcast(false)
	                                .setColorActive(MainP.blueAct)
	                                .setPosition(220, 195)
	                                .setSize(50, 20)
	                                .setRange(-99999, 99999)
	                                .setMultiplier(1)                       // set the sensitifity of the numberbox
	                                .setDecimalPrecision(0)
	                                .setDirection(Controller.HORIZONTAL)    // change the control direction to left/right
	                                .setCaptionLabel("mA")
	                                .setColorCaptionLabel(0)
	                                .setTab("current")
	                                ;
	    currentOutOffsetMaNBox.getCaptionLabel().align(ControlP5.RIGHT_OUTSIDE, ControlP5.CENTER).setPaddingX(5) ;
	    currentOutOffsetMaNBox.getCaptionLabel().toUpperCase(false) ;
	    cp5.getProperties().remove(currentOutOffsetMaNBox) ;
	    //cp5.getTooltip().register("currentOutOffsetMA", "...") ;
	  
	    currentOutOffsetNBox.setBroadcast(true) ;
	    currentOutOffsetMaNBox.setBroadcast(true) ;
	  
	    // Current sensor divider factor
	    currentDivNBox = cp5.addNumberbox("currentDivNb")
	                        .setColorActive(MainP.blueAct)
	                        .setPosition(165, 230)
	                        .setSize(45, 20)
	                        .setRange((float)0.01, (float) 99.99)
	                        .setMultiplier((float) 0.01)                    // set the sensitifity of the numberbox
	                        .setDecimalPrecision(2)
	                        .setDirection(Controller.HORIZONTAL)    // change the control direction to left/right
	                        .setValue(1)
	                        .setCaptionLabel("Divider factor                      ")
	                        .setColorCaptionLabel(0)
	                        .setTab("current")
	                        ;
	    currentDivNBox.getCaptionLabel().align(ControlP5.LEFT_OUTSIDE, ControlP5.CENTER).setPaddingX(9) ;
	    currentDivNBox.getCaptionLabel().toUpperCase(false) ;
	    cp5.getTooltip().register("currentDivNb", "- Default: 1 -") ;
	  
	    /*
	    // Current sensor Vcc
	    cp5.addNumberbox("currentVccNb")
	       .setColorActive(blueAct)
	       .setPosition(330, 118)
	       .setSize(30, 18)
	       .setRange(0, 9.9)
	       .setMultiplier(0.05)                    // set the sensitifity of the numberbox
	       .setDecimalPrecision(1)
	       .setDirection(Controller.HORIZONTAL)    // change the control direction to left/right
	       //.setValue(5)
	       .setLabel("Current sensor Vcc             volts")
	       .setColorLabel(color(0))
	       .setTab("current")
	       ;
	    cp5.getController("currentVccNb").getCaptionLabel().align(ControlP5.LEFT_OUTSIDE, ControlP5.CENTER).setPaddingX(-59) ;
	    cp5.getController("currentVccNb").captionLabel().toUpperCase(false) ;
	    cp5.getTooltip().register("currentVccNb", "Current sensor alimentation source value") ;
	    */
	    /*
	    // Current sensor direction
	    cp5.addTextlabel("currentDirL")
	       .setText("Unidirectional")
	       .setPosition(10, 157)
	       .setColorValue(#000000)
	       .setTab("current")
	       ;
	    cp5.getProperties().remove(cp5.getController("currentDirL")) ;
	  
	    cp5.addToggle("currentDir")
	       .setColorForeground(orangeAct)
	       .setColorBackground(OXSConfigurator._Gray)
	       .setColorActive(blueAct)
	       .setColorLabel(#000000)
	       .setPosition(98, 155)
	       .setSize(50, 20)
	       .setLabel("Bidirectional")
	       .setMode(ControlP5.SWITCH)
	       .setTab("current")
	       ;
	    cp5.getController("currentDir").getCaptionLabel().align(ControlP5.RIGHT_OUTSIDE, ControlP5.CENTER).setPaddingX(10) ;
	    cp5.getController("currentDir").captionLabel().toUpperCase(false) ;
	    */
	    
	    // dropdownlist overlap
	    currentPinDdl.bringToFront() ;
	  }

	public static DropdownList getCurrentPinDdl() {
		return currentPinDdl;
	}

	public static Numberbox getCurrentOutSensNBox() {
		return currentOutSensNBox;
	}

	public static Numberbox getCurrentOutOffsetNBox() {
		return currentOutOffsetNBox;
	}

	public static Numberbox getCurrentOutOffsetMaNBox() {
		return currentOutOffsetMaNBox;
	}

	public static Numberbox getCurrentDivNBox() {
		return currentDivNBox;
	}

}
