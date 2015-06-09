package gui;

import oxsc.MainP;
import processing.core.PApplet;
import controlP5.ControlP5;
import controlP5.Controller;
import controlP5.DropdownList;
import controlP5.Textlabel;

public class TabPPM {
	
	@SuppressWarnings("unused")
	private final ControlP5 cp5 ;
	@SuppressWarnings("unused")
	private final PApplet p ; // TODO check if needed
	
	static Textlabel ppmPinL;
	static DropdownList ppmPin;

	public TabPPM(PApplet p, ControlP5 cp5) {
		
		this.cp5 = cp5;
		this.p = p;

		// RC Remote PPM pin and settings
		cp5.addToggle("ppm")
		.setCaptionLabel("PPM")
		.setPosition(53, 117)
		.hide()
		;
		MainP.customizeToggle(cp5.getController("ppm")) ;

		ppmPinL = cp5.addTextlabel("ppmPinL")
				.setText("Pin       ")
				.setPosition(90, 117)
				.setColorValueLabel(0)
				.hide()
				;
		cp5.getProperties().remove(cp5.getController("ppmPinL")) ;
		cp5.getTooltip().register("ppmPinL", "- Default: 2 -");

		ppmPin = cp5.addDropdownList("ppmPin")
				.setPosition(118, 136)
				.setSize(30, 75)
				.setColorForeground(MainP.orangeAct)
				.setColorActive(MainP.blueAct)
				.setBackgroundColor(MainP.backDdlGray)
				.setItemHeight(20)
				.setBarHeight(20)
				.hide()
				;
		ppmPin.getCaptionLabel().set(" ");
		ppmPin.getCaptionLabel().getStyle().marginTop = 2 ;
		ppmPin.addItem(" 2", 2);
		ppmPin.addItem(" 3", 3);
		ppmPin.setValue(2);
		ppmPin.toUpperCase(false) ;
		cp5.getProperties().remove(cp5.getGroup("ppmPin"), "ListBoxItems") ;

		// PPM range setting
		cp5.addTextlabel("ppmRngL")
		.setText("PPM range (µs)                                                                          ")
		.setPosition(175, 117)
		.setColorValueLabel(0)
		.hide()
		;
		cp5.getProperties().remove(cp5.getController("ppmRngL")) ;
		cp5.getTooltip().register("ppmRngL", "RC control range - Default: 988:2012 -");

		cp5.addNumberbox("ppmRngMin")
		.setPosition(306, 115)
		.setSize(40, 20)
		.setColorActive(MainP.blueAct)
		.setRange(888, 1088)
		.setMultiplier((float) 0.5)                     // set the sensitifity of the numberbox
		.setDirection(Controller.HORIZONTAL)    // change the control direction to left/right
		.setValue(988)
		.setCaptionLabel("Min.")
		.setColorCaptionLabel(0)
		.hide()
		;
		cp5.getController("ppmRngMin").getCaptionLabel().align(ControlP5.LEFT_OUTSIDE, ControlP5.CENTER).setPaddingX(5) ;
		cp5.getController("ppmRngMin").getCaptionLabel().toUpperCase(false) ;

		cp5.addNumberbox("ppmRngMax")
		.setPosition(366, 115)
		.setSize(40, 20)
		.setColorActive(MainP.blueAct)
		.setRange(1912, 2112)
		.setMultiplier((float) 0.5)                     // set the sensitifity of the numberbox
		.setDirection(Controller.HORIZONTAL)    // change the control direction to left/right
		.setValue(2012)
		.setCaptionLabel("Max.")
		.setColorCaptionLabel(0)
		.hide()
		;
		cp5.getController("ppmRngMax").getCaptionLabel().align(ControlP5.RIGHT_OUTSIDE, ControlP5.CENTER).setPaddingX(5) ;
		cp5.getController("ppmRngMax").getCaptionLabel().toUpperCase(false) ;    
	}

	public static DropdownList getPpmPin() {
		return ppmPin;
	}

	public void drawPPMzone(MainP mainP) {
		// PPM zone
		mainP.fill(MainP.darkBackGray) ;
		mainP.rect(mainP.width / 2 - 14, 102, 30, 13) ;
	
		mainP.stroke(MainP.blueAct) ;
		mainP.strokeWeight(3) ;
		mainP.noFill() ;
		//rect(4, 106, 442, 36) ;
		mainP.strokeWeight(1) ;
		mainP.noStroke() ;
	
		mainP.fill(MainP.blueAct) ;
		mainP.rect(mainP.width / 2 - 15, 101, 30, 13) ;
		mainP.fill(255) ;
		mainP.textFont(mainP.fontLabel) ;
		mainP.text("PPM", 212, 112) ;
	
		if ( mainP.cp5.getController("ppm").getValue() == 0 ) {         // RC remote
			mainP.cp5.getGroup("ppmPin").hide() ;
			mainP.fill(MainP.grayedColor) ;
			mainP.rect(118, 115, 30, 20) ;
			mainP.cp5.getController("ppmPinL").setColorValueLabel(MainP.grayedColor) ;
	
			mainP.cp5.getController("ppmRngL").setColorValueLabel(MainP.grayedColor) ;
			mainP.cp5.getController("ppmRngMin").lock() ;
			mainP.cp5.getController("ppmRngMin").setColorBackground(MainP.grayedColor) ;
			mainP.cp5.getController("ppmRngMin").setColorValueLabel(MainP.grayedColor) ;
			mainP.cp5.getController("ppmRngMin").setColorCaptionLabel(MainP.grayedColor) ;
	
			mainP.cp5.getController("ppmRngMax").lock() ;
			mainP.cp5.getController("ppmRngMax").setColorBackground(MainP.grayedColor) ;
			mainP.cp5.getController("ppmRngMax").setColorValueLabel(MainP.grayedColor) ;
			mainP.cp5.getController("ppmRngMax").setColorCaptionLabel(MainP.grayedColor) ;
		} else {
			mainP.fill(MainP.lightBlue) ;                                    // toggle border filled
			mainP.rect(12, 114, 58, 20) ;
			mainP.cp5.getGroup("ppmPin").show() ;
			mainP.cp5.getController("ppmPinL").setColorValueLabel(mainP.color(0)) ;
	
			mainP.cp5.getController("ppmRngL").setColorValueLabel(mainP.color(0)) ;
			mainP.cp5.getController("ppmRngMin").unlock() ;
			mainP.cp5.getController("ppmRngMin").setColorBackground(MainP.darkBackGray) ;
			mainP.cp5.getController("ppmRngMin").setColorValueLabel(mainP.color(255)) ;
			mainP.cp5.getController("ppmRngMin").setColorCaptionLabel(mainP.color(0)) ;
	
			mainP.cp5.getController("ppmRngMax").unlock() ;
			mainP.cp5.getController("ppmRngMax").setColorBackground(MainP.darkBackGray) ;
			mainP.cp5.getController("ppmRngMax").setColorValueLabel(mainP.color(255)) ;
			mainP.cp5.getController("ppmRngMax").setColorCaptionLabel(mainP.color(0)) ;
		}
	
		mainP.stroke(MainP.darkBackGray) ;                               // toggle border
		mainP.noFill() ;
		mainP.rect(12, 114, 58, 20) ;
		mainP.noStroke() ;
	
	}
}
