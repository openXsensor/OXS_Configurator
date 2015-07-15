package gui;

import oxsc.MainP;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;

import controlP5.ControlP5;
import controlP5.Controller;
import controlP5.DropdownList;
import controlP5.Numberbox;
import controlP5.Textlabel;
import controlP5.Toggle;

public class TabPPM {
	
	private static ControlP5 cp5 ;
	@SuppressWarnings("unused")
	private final PApplet p ; // TODO check if needed

	private static Toggle ppmTgl;
	private static Textlabel ppmPinL;
	private static DropdownList ppmPinDdl;
	private static Numberbox ppmRngMinNBox;
	private static Numberbox ppmRngMaxNBox;
	
	private static List<Object> controllers = new ArrayList<>(); // TODO first - TabPPM preset

	public TabPPM(PApplet p, ControlP5 cp5) {
		
		TabPPM.cp5 = cp5;
		this.p = p;

		// RC Remote PPM pin and settings
		ppmTgl = cp5.addToggle("ppm")
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
		cp5.getTooltip().register("ppmPinL", "- Default: 2 -");
		cp5.getProperties().remove(cp5.getController("ppmPinL")) ;

		ppmPinDdl = cp5.addDropdownList("ppmPin")
				       .setPosition(118, 136)
				       .setSize(30, 75)
				       .setColorForeground(MainP.orangeAct)
				       .setColorActive(MainP.blueAct)
				       .setBackgroundColor(MainP.backDdlGray)
				       .setItemHeight(20)
				       .setBarHeight(20)
				       .hide()
				       ;
		ppmPinDdl.getCaptionLabel().set(" ");
		ppmPinDdl.getCaptionLabel().getStyle().marginTop = 2 ;
		ppmPinDdl.addItem(" 2", 2);
		ppmPinDdl.addItem(" 3", 3);
		ppmPinDdl.setValue(2);
		ppmPinDdl.toUpperCase(false) ;
		cp5.getProperties().remove(cp5.getGroup("ppmPin"), "ListBoxItems") ;

		// PPM range setting
		cp5.addTextlabel("ppmRngL")
		   .setText("PPM range (µs)                                                                          ")
		   .setPosition(175, 117)
		   .setColorValueLabel(0)
		   .hide()
		   ;
		cp5.getTooltip().register("ppmRngL", "RC control range - Default: 988:2012 -");
		cp5.getProperties().remove(cp5.getController("ppmRngL")) ;

		ppmRngMinNBox = cp5.addNumberbox("ppmRngMin")
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
		ppmRngMinNBox.getCaptionLabel().align(ControlP5.LEFT_OUTSIDE, ControlP5.CENTER).setPaddingX(5) ;
		ppmRngMinNBox.getCaptionLabel().toUpperCase(false) ;

		ppmRngMaxNBox = cp5.addNumberbox("ppmRngMax")
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
		ppmRngMaxNBox.getCaptionLabel().align(ControlP5.RIGHT_OUTSIDE, ControlP5.CENTER).setPaddingX(5) ;
		ppmRngMaxNBox.getCaptionLabel().toUpperCase(false) ;    
	}

	public static Toggle getPpmTgl() {
		return ppmTgl;
	}

	public static DropdownList getPpmPinDdl() {
		return ppmPinDdl;
	}

	public static Numberbox getPpmRngMinNBox() {
		return ppmRngMinNBox;
	}

	public static Numberbox getPpmRngMaxNBox() {
		return ppmRngMaxNBox;
	}

	public static void drawPPMzone(MainP mainP) {
		// PPM zone
		mainP.fill(MainP.darkBackGray);
		mainP.rect(mainP.width / 2 - 14, 102, 30, 13);

		mainP.stroke(MainP.blueAct);
		mainP.strokeWeight(3);
		mainP.noFill();
		// rect(4, 106, 442, 36) ;
		mainP.strokeWeight(1);
		mainP.noStroke();

		mainP.fill(MainP.blueAct);
		mainP.rect(mainP.width / 2 - 15, 101, 30, 13);
		mainP.fill(255);
		mainP.textFont(MainP.fontLabel);
		mainP.text("PPM", 212, 112);
	
		if ( ppmTgl.getValue() == 0 ) {         // RC remote
			ppmPinDdl.hide() ;
			mainP.fill(MainP.grayedColor) ;
			mainP.rect(118, 115, 30, 20) ;
			ppmPinL.setColorValueLabel(MainP.grayedColor) ;
	
			cp5.getController("ppmRngL").setColorValueLabel(MainP.grayedColor) ;
			ppmRngMinNBox.lock()
			             .setColorBackground(MainP.grayedColor)
			             .setColorValueLabel(MainP.grayedColor)
			             .setColorCaptionLabel(MainP.grayedColor);
			ppmRngMaxNBox.lock()
			             .setColorBackground(MainP.grayedColor)
			             .setColorValueLabel(MainP.grayedColor)
			             .setColorCaptionLabel(MainP.grayedColor);
		} else {
			mainP.fill(MainP.lightBlue) ;                                    // toggle border filled
			mainP.rect(12, 114, 58, 20) ;
			ppmPinDdl.show() ;
			ppmPinL.setColorValueLabel(0) ;
	
			cp5.getController("ppmRngL").setColorValueLabel(mainP.color(0)) ;
			ppmRngMinNBox.unlock()
			             .setColorBackground(MainP.darkBackGray)
			             .setColorValueLabel(MainP.white)
			             .setColorCaptionLabel(mainP.color(0));
			ppmRngMaxNBox.unlock()
			             .setColorBackground(MainP.darkBackGray)
			             .setColorValueLabel(MainP.white)
			             .setColorCaptionLabel(mainP.color(0));
		}
	
		mainP.stroke(MainP.darkBackGray) ;                               // toggle border
		mainP.noFill() ;
		mainP.rect(12, 114, 58, 20) ;
		mainP.noStroke() ;
	}
}
