package gui;

import oxsc.MainP;
import processing.core.PApplet;
import controlP5.ControlP5;
import controlP5.Controller;
import controlP5.DropdownList;

public class TabVario {
	
	@SuppressWarnings("unused")
	private final ControlP5 cp5 ;
	@SuppressWarnings("unused")
	private final PApplet p ;  // TODO check if needed
	
	
	private static DropdownList climbPin;
	
	public TabVario(PApplet p, ControlP5 cp5) {

		this.cp5 = cp5;
		this.p = p;

		// ---------------------------- Tab 1 : Vario settings ------------------------------
		cp5.getTab("vario")
		   .activateEvent(true)
		   .setHeight(20)
		   .setColorForeground(MainP.tabGray)
		   .setColorBackground(MainP.darkBackGray)
		   .setColorActive(MainP.blueAct)
		   .setLabel("Vario")
		   .setId(1)
		   .hide()
		   ;
	    cp5.getTab("vario").getCaptionLabel().toUpperCase(false) ;
	  
	    // PPM sensitivity at PPM range
	    cp5.addTextlabel("ppmRngSensL")
	       .setText("PPM range for sensitivity   Min.")
	       .setPosition(10, 153)
	       .setColorValueLabel(0)
	       .setTab("vario")
	       ;
	    cp5.getProperties().remove(cp5.getController("ppmRngSensL")) ;
	  
	    cp5.addRange("ppmRngSensMinMax")
	       .setPosition(186, 151)
	       .setSize(220, 20)
	       .setCaptionLabel("Max.")
	       .setHandleSize(15)
	       .setRange(0, 100)
	       .setRangeValues(10, 40)
	       ;
	    MainP.customizeRange(cp5.getController("ppmRngSensMinMax")) ;
	    cp5.getTooltip().register("ppmRngSensMinMax", "- Default: 10:40 -") ;
	  
	    // PPM sensitivity range
	    cp5.addTextlabel("ppmSensRngL")
	       .setText("PPM sensitivity                   Min.")
	       .setPosition(10, 180)
	       .setColorValueLabel(0)
	       .setTab("vario")
	       ;
	    cp5.getProperties().remove(cp5.getController("ppmSensRngL")) ;
	  
	    cp5.addRange("ppmSensMinMax")
	       .setPosition(186, 178)
	       .setSize(220, 20)
	       .setCaptionLabel("Max.")
	       .setHandleSize(15)
	       .setRange(20, 150)
	       .setRangeValues(20, (float) 100.5)
	       ;
	    MainP.customizeRange(cp5.getController("ppmSensMinMax")) ;
	    cp5.getTooltip().register("ppmSensMinMax", "RC control sensitivity range - Default: 20:100 -") ;
	  
	    // V.Speed type switching
	    cp5.addTextlabel("vSpeedTypeSw")
	       .setText("V.Speed type                                                                                                  ")
	       .setPosition(10, 215)
	       .setColorValueLabel(0)
	       .setTab("vario")
	       ;
	    cp5.getProperties().remove(cp5.getController("vSpeedTypeSw")) ;
	    cp5.getTooltip().register("vSpeedTypeSw", "Choose the 2 different V.Speed you want to switch") ;
	  
	    cp5.addTextlabel("vStSwitching")
	       .setText("switching")
	       .setPosition(84, 215)
	       .setColorValueLabel(0)
	       .setTab("vario")
	       ;
	    cp5.getProperties().remove(cp5.getController("vStSwitching")) ;
	  
	    cp5.addTextlabel("vStSw1L")
	       .setText("1")
	       .setPosition(171, 215)
	       .setColorValueLabel(0)
	       .setTab("vario")
	       ;
	    cp5.getProperties().remove(cp5.getController("vStSw1L")) ;
	  
	    cp5.addDropdownList("vSpeed1")
	       .setPosition(186, 234)
	       .setSize(100, 75)
	       .setColorForeground(MainP.orangeAct)
	       .setColorActive(MainP.blueAct)
	       .setBackgroundColor(MainP.backDdlGray)
	       .setItemHeight(20)
	       .setBarHeight(20)
	       .setTab("vario")
	       ;
	    cp5.getGroup("vSpeed1").getCaptionLabel().getStyle().marginTop = 2 ;
	    //cp5.get(DropdownList.class, "vSpeed1").addItem("       Vario 1", 0) ;
	    //cp5.get(DropdownList.class, "vSpeed1").addItem("       Vario 2", 1) ;
	    //cp5.get(DropdownList.class, "vSpeed1").addItem(" V1 + A.Speed", 2) ;
	    //cp5.get(DropdownList.class, "vSpeed1").setValue(0) ;
	    cp5.get(DropdownList.class, "vSpeed1").toUpperCase(false) ;
	    cp5.getProperties().remove(cp5.getGroup("vSpeed1"), "ListBoxItems") ;
	  
	    cp5.addTextlabel("vStSw2L")
	       .setText("2")
	       .setPosition(291, 215)
	       .setColorValueLabel(0)
	       .setTab("vario")
	       ;
	    cp5.getProperties().remove(cp5.getController("vStSw2L")) ;
	  
	    cp5.addDropdownList("vSpeed2")
	       .setPosition(306, 234)
	       .setSize(100, 75)
	       .setColorForeground(MainP.orangeAct)
	       .setColorActive(MainP.blueAct)
	       .setBackgroundColor(MainP.backDdlGray)
	       .setItemHeight(20)
	       .setBarHeight(20)
	       .setTab("vario")
	       ;
	    cp5.getGroup("vSpeed2").getCaptionLabel().getStyle().marginTop = 2 ;
	    //cp5.get(DropdownList.class, "vSpeed2").addItem("       Vario 1", 0) ;
	    //cp5.get(DropdownList.class, "vSpeed2").addItem("       Vario 2", 1) ;
	    //cp5.get(DropdownList.class, "vSpeed2").addItem(" V1 + A.Speed", 2) ;
	    //cp5.get(DropdownList.class, "vSpeed2").setValue(1) ;
	    cp5.get(DropdownList.class, "vSpeed2").toUpperCase(false) ;
	    cp5.getProperties().remove(cp5.getGroup("vSpeed2"), "ListBoxItems") ;
	  
	    // PPM Vertical speed switching values
	    cp5.addTextlabel("ppmVspeedSw")
	       .setText("PPM V.Speed switching (absolute values)                                                     ")
	       .setPosition(10, 242)
	       .setColorValueLabel(0)
	       .setTab("vario")
	       ;
	     cp5.getProperties().remove(cp5.getController("ppmVspeedSw")) ;
	     cp5.getTooltip().register("ppmVspeedSw", "- Default: 10:90 -") ;
	  
	     cp5.addNumberbox("ppmVspeedSwMin")
	        .setPosition(306, 240)
	        .setSize(40, 20)
	        .setColorActive(MainP.blueAct)
	        .setRange(0, 100)
	        .setMultiplier((float) 0.5)                     // set the sensitifity of the numberbox
	        .setDirection(Controller.HORIZONTAL)    // change the control direction to left/right
	        .setValue(10)
	        .setCaptionLabel("Min.")
	        .setColorCaptionLabel(0)
	        .setTab("vario")
	        ;
	    cp5.getController("ppmVspeedSwMin").getCaptionLabel().align(ControlP5.LEFT_OUTSIDE, ControlP5.CENTER).setPaddingX(5) ;
	    cp5.getController("ppmVspeedSwMin").getCaptionLabel().toUpperCase(false) ;
	  
	    cp5.addNumberbox("ppmVspeedSwMax")
	       .setPosition(366, 240)
	       .setSize(40, 20)
	       .setColorActive(MainP.blueAct)
	       .setRange(0, 100)
	       .setMultiplier((float) 0.5)                     // set the sensitifity of the numberbox
	       .setDirection(Controller.HORIZONTAL)    // change the control direction to left/right
	       .setValue(90)
	       .setCaptionLabel("Max.")
	       .setColorCaptionLabel(0)
	       .setTab("vario")
	       ;
	    cp5.getController("ppmVspeedSwMax").getCaptionLabel().align(ControlP5.RIGHT_OUTSIDE, ControlP5.CENTER).setPaddingX(5) ;
	    cp5.getController("ppmVspeedSwMax").getCaptionLabel().toUpperCase(false) ;
	  
	    // Vario sensitivity range
	    cp5.addTextlabel("sensitivityRange")
	       .setText("Sensitivity                           Min.")
	       .setPosition(10, 279)
	       .setColorValueLabel(0)
	       .setTab("vario")
	       ;
	    cp5.getProperties().remove(cp5.getController("sensitivityRange")) ;
	    cp5.getTooltip().register("sensitivityRange", "Sensitivity based on vertical speed - Default: 50:50 -") ;
	  
	    cp5.addRange("sensMinMax")
	       .setPosition(186, 277)
	       .setCaptionLabel("Max.")
	       .setSize(220, 20)
	       .setHandleSize(15)
	       .setRange(20, 150)
	       .setRangeValues( (float) 50.9, (float) 50.9)
	       ;
	    MainP.customizeRange(cp5.getController("sensMinMax")) ;
	    cp5.getTooltip().register("sensMinMax", "Sensitivity based on vertical speed - Default: 50:50 -") ;
	  
	    // Vario Vertical speed switching sensitivity range
	    cp5.addTextlabel("vSpeedSensitivityRng")
	       .setText("Sensitivity interpolated switching (cm/s)                                                           ")
	       .setPosition(10, 306)
	       .setColorValueLabel(0)
	       .setTab("vario")
	       ;
	     cp5.getProperties().remove(cp5.getController("vSpeedSensitivityRng")) ;
	     cp5.getTooltip().register("vSpeedSensitivityRng", "Vertical speed threshold sensitivity - Default: 20:100 -") ;
	  
	     cp5.addNumberbox("vSpeedMin")
	        .setPosition(306, 304)
	        .setSize(40, 20)
	        .setColorActive(MainP.blueAct)
	        .setBroadcast(false)
	        .setMultiplier((float) 0.5)                     // set the sensitifity of the numberbox
	        .setDirection(Controller.HORIZONTAL)    // change the control direction to left/right
	        .setValue(20)
	        .setCaptionLabel("Min.")
	        .setColorCaptionLabel(0)
	        .setTab("vario")
	        ;
	    cp5.getController("vSpeedMin").getCaptionLabel().align(ControlP5.LEFT_OUTSIDE, ControlP5.CENTER).setPaddingX(5) ;
	    cp5.getController("vSpeedMin").getCaptionLabel().toUpperCase(false) ;
	  
	    cp5.addNumberbox("vSpeedMax")
	       .setPosition(366, 304)
	       .setSize(40, 20)
	       .setColorActive(MainP.blueAct)
	       .setBroadcast(false)
	       .setMultiplier((float) 0.5)                     // set the sensitifity of the numberbox
	       .setDirection(Controller.HORIZONTAL)    // change the control direction to left/right
	       .setValue(100)
	       .setCaptionLabel("Max.")
	       .setColorCaptionLabel(0)
	       .setTab("vario")
	       ;
	    cp5.getController("vSpeedMax").getCaptionLabel().align(ControlP5.RIGHT_OUTSIDE, ControlP5.CENTER).setPaddingX(5) ;
	    cp5.getController("vSpeedMax").getCaptionLabel().toUpperCase(false) ;
	  
	    cp5.getController("vSpeedMin").setBroadcast(true) ;
	    cp5.getController("vSpeedMax").setBroadcast(true) ;
	  
	    // Vario hysteresis
	    cp5.addSlider("varioHysteresis")
	       .setPosition(186, 340)
	       .setSize(220, 15)
	       .setCaptionLabel("Hysteresis (cm/s)")
	       .setColorForeground(MainP.blueAct)
	       .setColorCaptionLabel(0)
	       .setColorValueLabel(0)
	       .setRange(0, 100)
	       .setValue(5)
	       .setTab("vario")
	       ;
	    // reposition the Labels for controller 'slider'
	    cp5.getController("varioHysteresis").getCaptionLabel().align(ControlP5.LEFT_OUTSIDE, ControlP5.CENTER).setPaddingX(78) ;
	    cp5.getController("varioHysteresis").getValueLabel().align(ControlP5.RIGHT_OUTSIDE, ControlP5.CENTER).setPaddingX(10) ;
	    cp5.getController("varioHysteresis").getCaptionLabel().toUpperCase(false) ;
	    cp5.getTooltip().register("varioHysteresis", "Minimum measurements difference - Default: 5 -") ;
	  
	  
	    // Analog climb rate  pin and settings
	    cp5.addToggle("analogClimb")
	       .setCaptionLabel("Analog climb rate")
	       .setPosition(117, 375)
	       .setTab("vario")
	       ;
	    MainP.customizeToggle(cp5.getController("analogClimb")) ;
	  
	    cp5.addTextlabel("climbPinL")
	       .setText("Pin            ")
	       .setPosition(139, 374)
	       .setColorValueLabel(0)
	       .setTab("vario")
	       ;
	    cp5.getProperties().remove(cp5.getController("climbPinL")) ;
	    cp5.getTooltip().register("climbPinL", "- Default: 3 -") ;
	  
	    climbPin = cp5.addDropdownList("climbPin")
	                  .setPosition(165, 393)
	                  .setSize(30, 75)
	                  .setColorForeground(MainP.orangeAct)
	                  .setColorActive(MainP.blueAct)
	                  .setBackgroundColor(MainP.backDdlGray)
	                  .setItemHeight(20)
	                  .setBarHeight(20)
	                  .setTab("vario")
	                  ;
	    climbPin.getCaptionLabel().getStyle().marginTop = 2 ;
	    climbPin.addItem(" 3", 3) ;
	    climbPin.addItem("11", 11) ;
	    climbPin.setValue(3) ;
	    climbPin.toUpperCase(false) ;
	    cp5.getProperties().remove(cp5.getGroup("climbPin"), "ListBoxItems") ;
	  
	    // Output climb rate range
	    cp5.addTextlabel("outClimbRateRngL")
	       .setText("Range (m/s)  Min.")
	       .setPosition(201, 374)
	       .setColorValueLabel(0)
	       .setTab("vario")
	       ;
	    cp5.getProperties().remove(cp5.getController("outClimbRateRngL")) ;
	  
	    cp5.addRange("outClimbRateMinMax")
	       .setPosition(306, 372)
	       .setSize(100, 20)
	       .setCaptionLabel("Max.")
	       .setHandleSize(15)
	       .setRange(-20, 20)
	       .setRangeValues(-3, (float) 3.9)
	       ;
	    MainP.customizeRange(cp5.getController("outClimbRateMinMax")) ;
	    cp5.getTooltip().register("outClimbRateMinMax", "Analog climb rate range - Default: -3:3 -") ;
	    
	    // dropdownlist overlap
	    climbPin.bringToFront() ;
	    cp5.getGroup("vSpeed1").bringToFront() ;
	    cp5.getGroup("vSpeed2").bringToFront() ;
	  }

	public static DropdownList getClimbPin() {
		return climbPin;
	}
	  
	  /*public void populateVspeedFields() {  // TODO
	    cp5.get(DropdownList.class, "vSpeed1").clear() ;
	    cp5.get(DropdownList.class, "vSpeed2").clear() ;
	    for ( int i = 1; i <= dataSentFieldNbr; i++ ) {
	      TabData.getSentDataField(i).clear() ;
	      TabData.getSentDataField(i).addItem("----------", 0) ;
	      for ( int j = 0 ; j < OXSdata.getList().size() ; j++ )
	        TabData.getSentDataField(i).addItem(OXSdata.getList().get(j).getDisplayName(), j+1) ;
	    }
	  }*/

}
