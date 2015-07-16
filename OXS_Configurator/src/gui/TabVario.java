package gui;

import oxsc.MainP;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;

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
	private static Numberbox ppmVspeedSwMinNBox;
	private static Numberbox ppmVspeedSwMaxNBox;
	private static Range sensMinMaxRng;
	private static Numberbox vSpeedMinNBox;
	private static Numberbox vSpeedMaxNBox;
	private static Slider varioHysteresisSld;
	private static Toggle analogClimbTgl;
	private static DropdownList climbPinDdl;
	private static Range outClimbRateMinMaxRng;
	
	private static List<Object> controllers = new ArrayList<>();
	
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
	  
	    ppmRngSensMinMaxRng = cp5.addRange("ppmRngSensMinMaxRng")
	                             .setPosition(186, 151)
	                             .setSize(220, 20)
	                             .setCaptionLabel("Max.")
	                             .setHandleSize(15)
	                             .setRange(0, 100)
	                             .setRangeValues(10, 40)
	                             ;
	    MainP.customizeRange(ppmRngSensMinMaxRng) ;
	    cp5.getTooltip().register(ppmRngSensMinMaxRng, "- Default: 10:40 -") ;
	    controllers.add(ppmRngSensMinMaxRng);
	  
	    // PPM sensitivity range
	    cp5.addTextlabel("ppmSensRngL")
	       .setText("PPM sensitivity                   Min.")
	       .setPosition(10, 180)
	       .setColorValueLabel(0)
	       .setTab("vario")
	       ;
	  
	    ppmSensMinMaxRng = cp5.addRange("ppmSensMinMaxRng")
	                          .setPosition(186, 178)
	                          .setSize(220, 20)
	                          .setCaptionLabel("Max.")
	                          .setHandleSize(15)
	                          .setRange(20, 150)
	                          .setRangeValues(20, 100.5f)
	                          ;
	    MainP.customizeRange(ppmSensMinMaxRng) ;
	    cp5.getTooltip().register(ppmSensMinMaxRng, "RC control sensitivity range - Default: 20:100 -") ;
	    controllers.add(ppmSensMinMaxRng);
	  
	    // V.Speed type switching
	    cp5.addTextlabel("vSpeedTypeSw")
	       .setText("V.Speed type                                                                                                  ")
	       .setPosition(10, 215)
	       .setColorValueLabel(0)
	       .setTab("vario")
	       ;
	    cp5.getTooltip().register("vSpeedTypeSw", "Choose the 2 different V.Speed you want to switch") ;
	  
	    cp5.addTextlabel("vStSwitching")
	       .setText("switching")
	       .setPosition(84, 215)
	       .setColorValueLabel(0)
	       .setTab("vario")
	       ;
	  
	    cp5.addTextlabel("vStSw1L")
	       .setText("1")
	       .setPosition(171, 215)
	       .setColorValueLabel(0)
	       .setTab("vario")
	       ;
	  
	    vSpeed1Ddl = cp5.addDropdownList("vSpeed1Ddl")
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
	    controllers.add(vSpeed1Ddl);
	  
	    cp5.addTextlabel("vStSw2L")
	       .setText("2")
	       .setPosition(291, 215)
	       .setColorValueLabel(0)
	       .setTab("vario")
	       ;
	  
	    vSpeed2Ddl = cp5.addDropdownList("vSpeed2Ddl")
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
	    controllers.add(vSpeed2Ddl);
	  
	    // PPM Vertical speed switching values
	    cp5.addTextlabel("ppmVspeedSw")
	       .setText("PPM V.Speed switching (absolute values)                                                     ")
	       .setPosition(10, 242)
	       .setColorValueLabel(0)
	       .setTab("vario")
	       ;
	    cp5.getTooltip().register("ppmVspeedSw", "- Default: 10:90 -") ;
	  
	     ppmVspeedSwMinNBox = cp5.addNumberbox("ppmVspeedSwMinNBox")
	                             .setPosition(306, 240)
	                             .setSize(40, 20)
	                             .setColorActive(MainP.blueAct)
	                             .setRange(0, 100)
	                             .setMultiplier(0.5f)                     // set the sensitifity of the numberbox
	                             .setDirection(Controller.HORIZONTAL)    // change the control direction to left/right
	                             .setValue(10)
	                             .setCaptionLabel("Min.")
	                             .setColorCaptionLabel(0)
	                             .setTab("vario")
	                             ;
	     ppmVspeedSwMinNBox.getCaptionLabel().align(ControlP5.LEFT_OUTSIDE, ControlP5.CENTER).setPaddingX(5) ;
	     ppmVspeedSwMinNBox.getCaptionLabel().toUpperCase(false) ;
	     controllers.add(ppmVspeedSwMinNBox);
	  
	     ppmVspeedSwMaxNBox = cp5.addNumberbox("ppmVspeedSwMaxNBox")
	                             .setPosition(366, 240)
	                             .setSize(40, 20)
	                             .setColorActive(MainP.blueAct)
	                             .setRange(0, 100)
	                             .setMultiplier(0.5f)                     // set the sensitifity of the numberbox
	                             .setDirection(Controller.HORIZONTAL)    // change the control direction to left/right
	                             .setValue(90)
	                             .setCaptionLabel("Max.")
	                             .setColorCaptionLabel(0)
	                             .setTab("vario")
	                             ;
	     ppmVspeedSwMaxNBox.getCaptionLabel().align(ControlP5.RIGHT_OUTSIDE, ControlP5.CENTER).setPaddingX(5) ;
	     ppmVspeedSwMaxNBox.getCaptionLabel().toUpperCase(false) ;
	     controllers.add(ppmVspeedSwMaxNBox);
	  
	    // Vario sensitivity range
	    cp5.addTextlabel("sensitivityRange")
	       .setText("Sensitivity                           Min.")
	       .setPosition(10, 279)
	       .setColorValueLabel(0)
	       .setTab("vario")
	       ;
	    cp5.getTooltip().register("sensitivityRange", "Sensitivity based on vertical speed - Default: 50:50 -") ;
	  
	    sensMinMaxRng = cp5.addRange("sensMinMaxRng")
	                       .setPosition(186, 277)
	                       .setCaptionLabel("Max.")
	                       .setSize(220, 20)
	                       .setHandleSize(15)
	                       .setRange(20, 150)
	                       .setRangeValues(50.9f, 50.9f)
	                       ;
	    MainP.customizeRange(sensMinMaxRng) ;
	    cp5.getTooltip().register(sensMinMaxRng, "Sensitivity based on vertical speed - Default: 50:50 -") ;
	    controllers.add(sensMinMaxRng);
	  
	    // Vario Vertical speed switching sensitivity range
	    cp5.addTextlabel("vSpeedSensitivityRng")
	       .setText("Sensitivity interpolated switching (cm/s)                                                           ")
	       .setPosition(10, 306)
	       .setColorValueLabel(0)
	       .setTab("vario")
	       ;
	    cp5.getTooltip().register("vSpeedSensitivityRng", "Vertical speed threshold sensitivity - Default: 20:100 -") ;
	  
	     vSpeedMinNBox = cp5.addNumberbox("vSpeedMinNBox")
	                        .setPosition(306, 304)
	                        .setSize(40, 20)
	                        .setColorActive(MainP.blueAct)
	                        .setBroadcast(false)
	                        .setMultiplier(0.5f)                     // set the sensitifity of the numberbox
	                        .setDirection(Controller.HORIZONTAL)    // change the control direction to left/right
	                        .setValue(20)
	                        .setCaptionLabel("Min.")
	                        .setColorCaptionLabel(0)
	                        .setTab("vario")
	                        ;
	     vSpeedMinNBox.getCaptionLabel().align(ControlP5.LEFT_OUTSIDE, ControlP5.CENTER).setPaddingX(5) ;
	     vSpeedMinNBox.getCaptionLabel().toUpperCase(false) ;
	     controllers.add(vSpeedMinNBox);
	  
	     vSpeedMaxNBox = cp5.addNumberbox("vSpeedMaxNBox")
	                        .setPosition(366, 304)
	                        .setSize(40, 20)
	                        .setColorActive(MainP.blueAct)
	                        .setBroadcast(false)
	                        .setMultiplier(0.5f)                     // set the sensitifity of the numberbox
	                        .setDirection(Controller.HORIZONTAL)    // change the control direction to left/right
	                        .setValue(100)
	                        .setCaptionLabel("Max.")
	                        .setColorCaptionLabel(0)
	                        .setTab("vario")
	                        ;
	     vSpeedMaxNBox.getCaptionLabel().align(ControlP5.RIGHT_OUTSIDE, ControlP5.CENTER).setPaddingX(5) ;
	     vSpeedMaxNBox.getCaptionLabel().toUpperCase(false) ;
	     controllers.add(vSpeedMaxNBox);
	  
		vSpeedMinNBox.setBroadcast(true);
		vSpeedMaxNBox.setBroadcast(true);
	  
	    // Vario hysteresis
		varioHysteresisSld = cp5.addSlider("varioHysteresisSld")
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
	    cp5.getTooltip().register(varioHysteresisSld, "Minimum measurements difference - Default: 5 -") ;
	    controllers.add(varioHysteresisSld);
	  
	  
	    // Analog climb rate  pin and settings
	    analogClimbTgl = cp5.addToggle("analogClimbTgl")
	                        .setCaptionLabel("Analog climb rate")
	                        .setPosition(117, 375)
	                        .setTab("vario")
	                        ;
	    MainP.customizeToggle(analogClimbTgl) ;
	    controllers.add(analogClimbTgl);
	  
	    cp5.addTextlabel("climbPinL")
	       .setText("Pin            ")
	       .setPosition(139, 374)
	       .setColorValueLabel(0)
	       .setTab("vario")
	       ;
	    cp5.getTooltip().register("climbPinL", "- Default: 3 -") ;
	  
	    climbPinDdl = cp5.addDropdownList("climbPinDdl")
	                  .setPosition(165, 393)
	                  .setSize(30, 75)
	                  .setColorForeground(MainP.orangeAct)
	                  .setColorActive(MainP.blueAct)
	                  .setBackgroundColor(MainP.backDdlGray)
	                  .setItemHeight(20)
	                  .setBarHeight(20)
	                  .setTab("vario")
	                  ;
	    climbPinDdl.getCaptionLabel().getStyle().marginTop = 2 ;
	    climbPinDdl.addItem(" 3", 3) ;
	    climbPinDdl.addItem("11", 11) ;
	    climbPinDdl.setValue(3) ;
	    climbPinDdl.toUpperCase(false) ;
	    controllers.add(climbPinDdl);
	  
	    // Output climb rate range
	    cp5.addTextlabel("outClimbRateRngL")
	       .setText("Range (m/s)  Min.")
	       .setPosition(201, 374)
	       .setColorValueLabel(0)
	       .setTab("vario")
	       ;
	  
	    outClimbRateMinMaxRng = cp5.addRange("outClimbRateMinMaxRng")
	                               .setPosition(306, 372)
	                               .setSize(100, 20)
	                               .setCaptionLabel("Max.")
	                               .setHandleSize(15)
	                               .setRange(-20, 20)
	                               .setRangeValues(-3, 3.9f)
	                               ;
	    MainP.customizeRange(outClimbRateMinMaxRng) ;
	    cp5.getTooltip().register(outClimbRateMinMaxRng, "Analog climb rate range - Default: -3:3 -") ;
	    controllers.add(outClimbRateMinMaxRng);
	    
	    // dropdownlist overlap
	    climbPinDdl.bringToFront() ;
	    vSpeed1Ddl.bringToFront() ;
	    vSpeed2Ddl.bringToFront() ;
	  }

	public static Range getPpmRngSensMinMaxRng() {
		return ppmRngSensMinMaxRng;
	}

	public static Range getPpmSensMinMaxRng() {
		return ppmSensMinMaxRng;
	}

	public static DropdownList getvSpeed1Ddl() {
		return vSpeed1Ddl;
	}
	
	public static DropdownList getvSpeed2Ddl() {
		return vSpeed2Ddl;
	}
	
	public static Numberbox getPpmVspeedSwMinNBox() {
		return ppmVspeedSwMinNBox;
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

	public static DropdownList getClimbPinDdl() {
		return climbPinDdl;
	}

	public static Range getOutClimbRateMinMaxRng() {
		return outClimbRateMinMaxRng;
	}

	public static Toggle getAnalogClimbTgl() {
		return analogClimbTgl;
	}

	public static List<Object> getControllers() {
		return controllers;
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
	
		if ( TabPPM.getPpmTgl().getValue() == 0.0 ) {
			cp5.getController("ppmRngSensL").setColorValueLabel(MainP.grayedColor) ;
			ppmRngSensMinMaxRng.lock()
			                   .setColorForeground(MainP.grayedColor)
			                   .setColorBackground(MainP.grayedColor)
			                   .setColorValueLabel(MainP.grayedColor)
			                   .setColorCaptionLabel(MainP.grayedColor);
	
			cp5.getController("ppmSensRngL").setColorValueLabel(MainP.grayedColor) ;
			ppmSensMinMaxRng.lock()
			                .setColorForeground(MainP.grayedColor)
			                .setColorBackground(MainP.grayedColor)
			                .setColorValueLabel(MainP.grayedColor)
			                .setColorCaptionLabel(MainP.grayedColor);
		} else {
			cp5.getController("ppmRngSensL").setColorValueLabel(mainP.color(0)) ;
			ppmRngSensMinMaxRng.unlock()
			                   .setColorForeground(MainP.blueAct)
			                   .setColorBackground(MainP.darkBackGray)
			                   .setColorValueLabel(MainP.white)
			                   .setColorCaptionLabel(mainP.color(0));
	
			cp5.getController("ppmSensRngL").setColorValueLabel(mainP.color(0)) ;
			ppmSensMinMaxRng.unlock()
			                .setColorForeground(MainP.blueAct)
			                .setColorBackground(MainP.darkBackGray)
			                .setColorValueLabel(MainP.white)
			                .setColorCaptionLabel(mainP.color(0));
		}
	
		if ( TabPPM.getPpmTgl().getValue() == 0.0 
				|| ( TabGeneralSettings.getVario2Tgl().getValue() == 0.0 && TabGeneralSettings.getAirSpeedTgl().getValue() == 0.0 ) ) {
			cp5.getController("vStSw2L").setColorValueLabel(MainP.grayedColor) ;
			cp5.getController("vStSwitching").setColorValueLabel(MainP.grayedColor) ;
			vSpeed2Ddl.hide() ;
			mainP.fill(MainP.grayedColor) ;
			mainP.rect(306, 213, 100, 20) ;
	
			cp5.getController("ppmVspeedSw").setColorValueLabel(MainP.grayedColor) ;
			ppmVspeedSwMinNBox.lock()
			                  .setColorBackground(MainP.grayedColor)
			                  .setColorForeground(MainP.grayedColor)
			                  .setColorValueLabel(MainP.grayedColor)
			                  .setColorCaptionLabel(MainP.grayedColor);
			ppmVspeedSwMaxNBox.lock()
			                  .setColorBackground(MainP.grayedColor)
			                  .setColorForeground(MainP.grayedColor)
			                  .setColorValueLabel(MainP.grayedColor)
			                  .setColorCaptionLabel(MainP.grayedColor);
		} else {
			cp5.getController("vStSw2L").setColorValueLabel(mainP.color(0)) ;
			cp5.getController("vStSwitching").setColorValueLabel(mainP.color(0)) ;
			vSpeed2Ddl.show() ;
	
			cp5.getController("ppmVspeedSw").setColorValueLabel(mainP.color(0)) ;
			ppmVspeedSwMinNBox.unlock()
			                  .setColorBackground(MainP.darkBackGray) 
			                  .setColorValueLabel(MainP.white)
			                  .setColorCaptionLabel(mainP.color(0));
			ppmVspeedSwMaxNBox.unlock()
			                  .setColorBackground(MainP.darkBackGray)
			                  .setColorValueLabel(MainP.white)
			                  .setColorCaptionLabel(mainP.color(0));
		}
	
		if ( analogClimbTgl.getValue() == 0 ) {                    // Analog climb rate
			cp5.getController("climbPinL").setColorValueLabel(MainP.grayedColor) ;
			climbPinDdl.hide() ;
			mainP.fill(MainP.grayedColor) ;
			mainP.rect(165, 372, 30, 20) ;
	
			cp5.getController("outClimbRateRngL").setColorValueLabel(MainP.grayedColor) ;
			outClimbRateMinMaxRng.lock()
			                     .setColorForeground(MainP.grayedColor)
			                     .setColorBackground(MainP.grayedColor)
			                     .setColorValueLabel(MainP.grayedColor)
			                     .setColorCaptionLabel(MainP.grayedColor);
		} else {
			mainP.fill(MainP.lightBlue) ;                                    // toggle border filled
			mainP.rect(10, 372, 124, 20) ;
			cp5.getController("climbPinL").setColorValueLabel(mainP.color(0)) ;
			climbPinDdl.show() ;
	
			cp5.getController("outClimbRateRngL").setColorValueLabel(mainP.color(0)) ;
			outClimbRateMinMaxRng.unlock()
			                     .setColorForeground(MainP.blueAct)
			                     .setColorBackground(MainP.darkBackGray)
			                     .setColorValueLabel(MainP.white)
			                     .setColorCaptionLabel(mainP.color(0));
		}
	
		mainP.stroke(MainP.darkBackGray) ;                               // toggle border
		mainP.noFill() ;
		mainP.rect(10, 372, 124, 20) ;
		mainP.noStroke() ;
	
		if ( climbPinDdl.isOpen() ) {    // climb pin dropdown free
			FileManagement.getSavePresetBtn().hide() ;
		} else {
			FileManagement.getSavePresetBtn().show() ;
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
