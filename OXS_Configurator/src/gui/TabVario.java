package gui;

import oxsc.MainP;
import processing.core.PApplet;
import controlP5.ControlP5;
import controlP5.Controller;
import controlP5.DropdownList;
import controlP5.Numberbox;
import controlP5.Range;
import controlP5.Slider;
import controlP5.Toggle;

public class TabVario {
	
	private static ControlP5 cp5 ;
	@SuppressWarnings("unused")
	private final PApplet p ;  // TODO check if needed

	private static Range ppmRngSensMinMaxRng;
	private static Range ppmSensMinMaxRng;
	private static DropdownList vSpeed1Ddl;
	private static DropdownList vSpeed2Ddl;
	private static Numberbox ppmVspeedSwMinNbox;
	private static Numberbox ppmVspeedSwMaxNBox;
	private static Range sensMinMaxRng;
	private static Numberbox vSpeedMinNBox;
	private static Numberbox vSpeedMaxNBox;
	private static Slider varioHysteresisSld;
	
	private static Toggle analogClimbTgl;	
	private static DropdownList climbPin;
	private static Range outClimbRateMinMaxRng;
	
	public TabVario(PApplet p, ControlP5 cp5) {

		TabVario.cp5 = cp5;
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
	  
	    ppmRngSensMinMaxRng = cp5.addRange("ppmRngSensMinMax")
	                             .setPosition(186, 151)
	                             .setSize(220, 20)
	                             .setCaptionLabel("Max.")
	                             .setHandleSize(15)
	                             .setRange(0, 100)
	                             .setRangeValues(10, 40)
	                             ;
	    MainP.customizeRange(ppmRngSensMinMaxRng) ;
	    cp5.getTooltip().register("ppmRngSensMinMax", "- Default: 10:40 -") ;
	  
	    // PPM sensitivity range
	    cp5.addTextlabel("ppmSensRngL")
	       .setText("PPM sensitivity                   Min.")
	       .setPosition(10, 180)
	       .setColorValueLabel(0)
	       .setTab("vario")
	       ;
	    cp5.getProperties().remove(cp5.getController("ppmSensRngL")) ;
	  
	    ppmSensMinMaxRng = cp5.addRange("ppmSensMinMax")
	                          .setPosition(186, 178)
	                          .setSize(220, 20)
	                          .setCaptionLabel("Max.")
	                          .setHandleSize(15)
	                          .setRange(20, 150)
	                          .setRangeValues(20, (float) 100.5)
	                          ;
	    MainP.customizeRange(ppmSensMinMaxRng) ;
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
	  
	    vSpeed1Ddl = cp5.addDropdownList("vSpeed1")
	                    .setPosition(186, 234)
	                    .setSize(100, 75)
	                    .setColorForeground(MainP.orangeAct)
	                    .setColorActive(MainP.blueAct)
	                    .setBackgroundColor(MainP.backDdlGray)
	                    .setItemHeight(20)
	                    .setBarHeight(20)
	                    .setTab("vario")
	                    ;
	    vSpeed1Ddl.getCaptionLabel().getStyle().marginTop = 2 ;
	    //cp5.get(DropdownList.class, "vSpeed1").addItem("       Vario 1", 0) ;
	    //cp5.get(DropdownList.class, "vSpeed1").addItem("       Vario 2", 1) ;
	    //cp5.get(DropdownList.class, "vSpeed1").addItem(" V1 + A.Speed", 2) ;
	    //cp5.get(DropdownList.class, "vSpeed1").setValue(0) ;
	    vSpeed1Ddl.toUpperCase(false) ;
	    cp5.getProperties().remove(vSpeed1Ddl, "ListBoxItems") ;
	  
	    cp5.addTextlabel("vStSw2L")
	       .setText("2")
	       .setPosition(291, 215)
	       .setColorValueLabel(0)
	       .setTab("vario")
	       ;
	    cp5.getProperties().remove(cp5.getController("vStSw2L")) ;
	  
	    vSpeed2Ddl = cp5.addDropdownList("vSpeed2")
	                    .setPosition(306, 234)
	                    .setSize(100, 75)
	                    .setColorForeground(MainP.orangeAct)
	                    .setColorActive(MainP.blueAct)
	                    .setBackgroundColor(MainP.backDdlGray)
	                    .setItemHeight(20)
	                    .setBarHeight(20)
	                    .setTab("vario")
	                    ;
	    vSpeed2Ddl.getCaptionLabel().getStyle().marginTop = 2 ;
	    //cp5.get(DropdownList.class, "vSpeed2").addItem("       Vario 1", 0) ;
	    //cp5.get(DropdownList.class, "vSpeed2").addItem("       Vario 2", 1) ;
	    //cp5.get(DropdownList.class, "vSpeed2").addItem(" V1 + A.Speed", 2) ;
	    //cp5.get(DropdownList.class, "vSpeed2").setValue(1) ;
	    vSpeed2Ddl.toUpperCase(false) ;
	    cp5.getProperties().remove(vSpeed2Ddl, "ListBoxItems") ;
	  
	    // PPM Vertical speed switching values
	    cp5.addTextlabel("ppmVspeedSw")
	       .setText("PPM V.Speed switching (absolute values)                                                     ")
	       .setPosition(10, 242)
	       .setColorValueLabel(0)
	       .setTab("vario")
	       ;
	     cp5.getProperties().remove(cp5.getController("ppmVspeedSw")) ;
	     cp5.getTooltip().register("ppmVspeedSw", "- Default: 10:90 -") ;
	  
	     ppmVspeedSwMinNbox = cp5.addNumberbox("ppmVspeedSwMin")
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
	     ppmVspeedSwMinNbox.getCaptionLabel().align(ControlP5.LEFT_OUTSIDE, ControlP5.CENTER).setPaddingX(5) ;
	     ppmVspeedSwMinNbox.getCaptionLabel().toUpperCase(false) ;
	  
	     ppmVspeedSwMaxNBox = cp5.addNumberbox("ppmVspeedSwMax")
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
	     ppmVspeedSwMaxNBox.getCaptionLabel().align(ControlP5.RIGHT_OUTSIDE, ControlP5.CENTER).setPaddingX(5) ;
	     ppmVspeedSwMaxNBox.getCaptionLabel().toUpperCase(false) ;
	  
	    // Vario sensitivity range
	    cp5.addTextlabel("sensitivityRange")
	       .setText("Sensitivity                           Min.")
	       .setPosition(10, 279)
	       .setColorValueLabel(0)
	       .setTab("vario")
	       ;
	    cp5.getProperties().remove(cp5.getController("sensitivityRange")) ;
	    cp5.getTooltip().register("sensitivityRange", "Sensitivity based on vertical speed - Default: 50:50 -") ;
	  
	    sensMinMaxRng = cp5.addRange("sensMinMax")
	                       .setPosition(186, 277)
	                       .setCaptionLabel("Max.")
	                       .setSize(220, 20)
	                       .setHandleSize(15)
	                       .setRange(20, 150)
	                       .setRangeValues( (float) 50.9, (float) 50.9)
	                       ;
	    MainP.customizeRange(sensMinMaxRng) ;
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
	  
	     vSpeedMinNBox = cp5.addNumberbox("vSpeedMin")
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
	     vSpeedMinNBox.getCaptionLabel().align(ControlP5.LEFT_OUTSIDE, ControlP5.CENTER).setPaddingX(5) ;
	     vSpeedMinNBox.getCaptionLabel().toUpperCase(false) ;
	  
	     vSpeedMaxNBox = cp5.addNumberbox("vSpeedMax")
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
	     vSpeedMaxNBox.getCaptionLabel().align(ControlP5.RIGHT_OUTSIDE, ControlP5.CENTER).setPaddingX(5) ;
	     vSpeedMaxNBox.getCaptionLabel().toUpperCase(false) ;
	  
		vSpeedMinNBox.setBroadcast(true);
		vSpeedMaxNBox.setBroadcast(true);
	  
	    // Vario hysteresis
		varioHysteresisSld = cp5.addSlider("varioHysteresis")
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
		varioHysteresisSld.getCaptionLabel().align(ControlP5.LEFT_OUTSIDE, ControlP5.CENTER).setPaddingX(78) ;
		varioHysteresisSld.getValueLabel().align(ControlP5.RIGHT_OUTSIDE, ControlP5.CENTER).setPaddingX(10) ;
		varioHysteresisSld.getCaptionLabel().toUpperCase(false) ;
	    cp5.getTooltip().register("varioHysteresis", "Minimum measurements difference - Default: 5 -") ;
	  
	  
	    // Analog climb rate  pin and settings
	    analogClimbTgl = cp5.addToggle("analogClimb")
	                        .setCaptionLabel("Analog climb rate")
	                        .setPosition(117, 375)
	                        .setTab("vario")
	                        ;
	    MainP.customizeToggle(analogClimbTgl) ;
	  
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
	    cp5.getProperties().remove(climbPin, "ListBoxItems") ;
	  
	    // Output climb rate range
	    cp5.addTextlabel("outClimbRateRngL")
	       .setText("Range (m/s)  Min.")
	       .setPosition(201, 374)
	       .setColorValueLabel(0)
	       .setTab("vario")
	       ;
	    cp5.getProperties().remove(cp5.getController("outClimbRateRngL")) ;
	  
	    outClimbRateMinMaxRng = cp5.addRange("outClimbRateMinMax")
	                               .setPosition(306, 372)
	                               .setSize(100, 20)
	                               .setCaptionLabel("Max.")
	                               .setHandleSize(15)
	                               .setRange(-20, 20)
	                               .setRangeValues(-3, (float) 3.9)
	                               ;
	    MainP.customizeRange(outClimbRateMinMaxRng) ;
	    cp5.getTooltip().register("outClimbRateMinMax", "Analog climb rate range - Default: -3:3 -") ;
	    
	    // dropdownlist overlap
	    climbPin.bringToFront() ;
	    vSpeed1Ddl.bringToFront() ;
	    vSpeed2Ddl.bringToFront() ;
	  }

	public static Range getPpmRngSensMinMaxRng() {
		return ppmRngSensMinMaxRng;
	}

	public static DropdownList getvSpeed1Ddl() {
		return vSpeed1Ddl;
	}
	
	public static DropdownList getvSpeed2Ddl() {
		return vSpeed2Ddl;
	}
	
	public static Numberbox getPpmVspeedSwMinNbox() {
		return ppmVspeedSwMinNbox;
	}

	public static Numberbox getPpmVspeedSwMaxNBox() {
		return ppmVspeedSwMaxNBox;
	}

	public static Range getSensMinMaxRng() {
		return sensMinMaxRng;
	}

	public static Numberbox getvSpeedMinNBox() {
		return vSpeedMinNBox;
	}

	public static Numberbox getvSpeedMaxNBox() {
		return vSpeedMaxNBox;
	}

	public static Slider getVarioHysteresisSld() {
		return varioHysteresisSld;
	}

	public static DropdownList getClimbPin() {
		return climbPin;
	}

	public static Range getOutClimbRateMinMaxRng() {
		return outClimbRateMinMaxRng;
	}

	public static Toggle getAnalogClimbTgl() {
		return analogClimbTgl;
	}

	public static void draw(MainP mainP) {
		mainP.stroke(MainP.blueAct) ;     // blue border
		mainP.strokeWeight(3) ;
		mainP.noFill() ;
		mainP.rect(4, 106, 442, 162) ;
		mainP.line(4, 142, 446, 142) ;
		mainP.strokeWeight(1) ;
		mainP.noStroke() ;
	
		TabPPM.drawPPMzone(mainP) ;
	
		// separation lines
		mainP.stroke(MainP.darkBackGray) ;
		mainP.line(10, 205, 440, 205) ;
		mainP.line(10, 331, 440, 331) ;
		mainP.line(10, 363, 440, 363) ;
		mainP.noStroke() ;
	
		if ( cp5.getController("ppm").getValue() == 0 ) {
			cp5.getController("ppmRngSensL").setColorValueLabel(MainP.grayedColor) ;
			cp5.getController("ppmRngSensMinMax").lock() ;
			cp5.getController("ppmRngSensMinMax").setColorForeground(MainP.grayedColor) ;
			cp5.getController("ppmRngSensMinMax").setColorBackground(MainP.grayedColor) ;
			cp5.getController("ppmRngSensMinMax").setColorValueLabel(MainP.grayedColor) ;
			cp5.getController("ppmRngSensMinMax").setColorCaptionLabel(MainP.grayedColor) ;
	
			cp5.getController("ppmSensRngL").setColorValueLabel(MainP.grayedColor) ;
			cp5.getController("ppmSensMinMax").lock() ;
			cp5.getController("ppmSensMinMax").setColorForeground(MainP.grayedColor) ;
			cp5.getController("ppmSensMinMax").setColorBackground(MainP.grayedColor) ;
			cp5.getController("ppmSensMinMax").setColorValueLabel(MainP.grayedColor) ;
			cp5.getController("ppmSensMinMax").setColorCaptionLabel(MainP.grayedColor) ;
		} else {
			cp5.getController("ppmRngSensL").setColorValueLabel(mainP.color(0)) ;
			cp5.getController("ppmRngSensMinMax").unlock() ;
			cp5.getController("ppmRngSensMinMax").setColorForeground(MainP.blueAct) ;
			cp5.getController("ppmRngSensMinMax").setColorBackground(MainP.darkBackGray) ;
			cp5.getController("ppmRngSensMinMax").setColorValueLabel(MainP.white) ;
			cp5.getController("ppmRngSensMinMax").setColorCaptionLabel(mainP.color(0)) ;
	
			cp5.getController("ppmSensRngL").setColorValueLabel(mainP.color(0)) ;
			cp5.getController("ppmSensMinMax").unlock() ;
			cp5.getController("ppmSensMinMax").setColorForeground(MainP.blueAct) ;
			cp5.getController("ppmSensMinMax").setColorBackground(MainP.darkBackGray) ;
			cp5.getController("ppmSensMinMax").setColorValueLabel(MainP.white) ;
			cp5.getController("ppmSensMinMax").setColorCaptionLabel(mainP.color(0)) ;
		}
	
		if ( cp5.getController("ppm").getValue() == 0 || ( mainP.cp5.getController("vario2").getValue() == 0 && mainP.cp5.getController("airSpeed").getValue() == 0 ) ) {
			cp5.getController("vStSw2L").setColorValueLabel(MainP.grayedColor) ;
			cp5.getController("vStSwitching").setColorValueLabel(MainP.grayedColor) ;
			cp5.getGroup("vSpeed2").hide() ;
			mainP.fill(MainP.grayedColor) ;
			mainP.rect(306, 213, 100, 20) ;
	
			cp5.getController("ppmVspeedSw").setColorValueLabel(MainP.grayedColor) ;
			cp5.getController("ppmVspeedSwMin").lock() ;
			cp5.getController("ppmVspeedSwMin").setColorBackground(MainP.grayedColor) ;
			cp5.getController("ppmVspeedSwMin").setColorForeground(MainP.grayedColor) ;
			cp5.getController("ppmVspeedSwMin").setColorValueLabel(MainP.grayedColor) ;
			cp5.getController("ppmVspeedSwMin").setColorCaptionLabel(MainP.grayedColor) ;
			cp5.getController("ppmVspeedSwMax").lock() ;
			cp5.getController("ppmVspeedSwMax").setColorBackground(MainP.grayedColor) ;
			cp5.getController("ppmVspeedSwMax").setColorForeground(MainP.grayedColor) ;
			cp5.getController("ppmVspeedSwMax").setColorValueLabel(MainP.grayedColor) ;
			cp5.getController("ppmVspeedSwMax").setColorCaptionLabel(MainP.grayedColor) ;
		} else {
			cp5.getController("vStSw2L").setColorValueLabel(mainP.color(0)) ;
			cp5.getController("vStSwitching").setColorValueLabel(mainP.color(0)) ;
			cp5.getGroup("vSpeed2").show() ;
	
			cp5.getController("ppmVspeedSw").setColorValueLabel(mainP.color(0)) ;
			cp5.getController("ppmVspeedSwMin").unlock() ;
			cp5.getController("ppmVspeedSwMin").setColorBackground(MainP.darkBackGray) ;
			cp5.getController("ppmVspeedSwMin").setColorValueLabel(MainP.white) ;
			cp5.getController("ppmVspeedSwMin").setColorCaptionLabel(mainP.color(0)) ;
			cp5.getController("ppmVspeedSwMax").unlock() ;
			cp5.getController("ppmVspeedSwMax").setColorBackground(MainP.darkBackGray) ;
			cp5.getController("ppmVspeedSwMax").setColorValueLabel(MainP.white) ;
			cp5.getController("ppmVspeedSwMax").setColorCaptionLabel(mainP.color(0)) ;
		}
	
		if ( cp5.getController("analogClimb").getValue() == 0 ) {                    // Analog climb rate
			cp5.getController("climbPinL").setColorValueLabel(MainP.grayedColor) ;
			cp5.getGroup("climbPin").hide() ;
			mainP.fill(MainP.grayedColor) ;
			mainP.rect(165, 372, 30, 20) ;
	
			cp5.getController("outClimbRateRngL").setColorValueLabel(MainP.grayedColor) ;
			cp5.getController("outClimbRateMinMax").lock() ;
			cp5.getController("outClimbRateMinMax").setColorForeground(MainP.grayedColor) ;
			cp5.getController("outClimbRateMinMax").setColorBackground(MainP.grayedColor) ;
			cp5.getController("outClimbRateMinMax").setColorValueLabel(MainP.grayedColor) ;
			cp5.getController("outClimbRateMinMax").setColorCaptionLabel(MainP.grayedColor) ;
		} else {
			mainP.fill(MainP.lightBlue) ;                                    // toggle border filled
			mainP.rect(10, 372, 124, 20) ;
			cp5.getController("climbPinL").setColorValueLabel(mainP.color(0)) ;
			cp5.getGroup("climbPin").show() ;
	
			cp5.getController("outClimbRateRngL").setColorValueLabel(mainP.color(0)) ;
			cp5.getController("outClimbRateMinMax").unlock() ;
			cp5.getController("outClimbRateMinMax").setColorForeground(MainP.blueAct) ;
			cp5.getController("outClimbRateMinMax").setColorBackground(MainP.darkBackGray) ;
			cp5.getController("outClimbRateMinMax").setColorValueLabel(MainP.white) ;
			cp5.getController("outClimbRateMinMax").setColorCaptionLabel(mainP.color(0)) ;
		}
	
		mainP.stroke(MainP.darkBackGray) ;                               // toggle border
		mainP.noFill() ;
		mainP.rect(10, 372, 124, 20) ;
		mainP.noStroke() ;
	
		if ( cp5.getGroup("climbPin").isOpen() ) {    // climb pin dropdown free
			cp5.getController("saveButton").hide() ;
		} else {
			cp5.getController("saveButton").show() ;
		}
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
