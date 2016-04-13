package gui;

import oxsc.MainP;

import java.util.ArrayList;
import java.util.List;

import controlP5.ControlP5;
import controlP5.Controller;
import controlP5.Numberbox;
import controlP5.Range;

public class TabAirSpeed {
	
	private static ControlP5 cp5;

	private static Numberbox aSpeedResetNBox;
	private static Range ppmRngCompMinMaxRng;
	private static Range ppmCompMinMaxRng;

	private static List<Object> controllers = new ArrayList<>();

	public TabAirSpeed(ControlP5 cp5) {

		TabAirSpeed.cp5 = cp5;

	    cp5.getTab("airSpeed")
	       .activateEvent(true)
	       .setHeight(20)
	       .setColorForeground(MainP.tabGray)
	       .setColorBackground(MainP.darkBackGray)
	       .setColorActive(MainP.blueAct)
	       .setLabel("Air Speed")
	       .setId(2)
	       .hide()
	       ;
	    cp5.getTab("airSpeed").getCaptionLabel().toUpperCase(false) ;

	    // PPM Air Speed reset
	    aSpeedResetNBox = cp5.addNumberbox("aSpeedResetNBox")
	                         .setPosition(206, 154)
	                         .setSize(40, 20)
	                         .setColorActive(MainP.blueAct)
	                         .setRange(0, 100)
	                         .setMultiplier(0.5f)               // set the sensitifity of the numberbox
	                         .setDirection(Controller.HORIZONTAL)      // change the control direction to left/right
	                         .setValue(100)
	                         .setCaptionLabel("PPM Air Speed reset                       ")
	                         .setColorCaptionLabel(0)
	                         .setTab("airSpeed")
	                         ;
	    aSpeedResetNBox.getCaptionLabel().align(ControlP5.LEFT_OUTSIDE, ControlP5.CENTER).setPaddingX(5) ;
	    aSpeedResetNBox.getCaptionLabel().toUpperCase(false) ;
	    controllers.add(aSpeedResetNBox);

	    // PPM compensation at PPM range
	    cp5.addTextlabel("ppmRngCompL")
	       .setText("PPM range for compensation   Min.")
	       .setPosition(10, 197)
	       .setColorValueLabel(0)
	       .setTab("airSpeed")
	       ;

	    ppmRngCompMinMaxRng = cp5.addRange("ppmRngCompMinMaxRng")
	                             .setPosition(206, 195)
	                             .setSize(200, 20)
	                             .setCaptionLabel("Max.")
	                             .setHandleSize(15)
	                             .setRange(0, 100)
	                             .setRangeValues(60, 90)
	                             ;
	    MainP.customizeRange(ppmRngCompMinMaxRng) ;
	    cp5.getTooltip().register(ppmRngCompMinMaxRng, "- Default: 10:40 -") ;
	    ppmRngCompMinMaxRng.setTab("airSpeed") ;
	    controllers.add(ppmRngCompMinMaxRng);

	    // PPM compensation
	    cp5.addTextlabel("ppmCompL")
	       .setText("PPM compensation                  Min.")
	       .setPosition(10, 225)
	       .setColorValueLabel(0)
	       .setTab("airSpeed")
	       ;

	    ppmCompMinMaxRng = cp5.addRange("ppmCompMinMaxRng")
	                          .setPosition(206, 223)
	                          .setSize(200, 20)
	                          .setCaptionLabel("Max.")
	                          .setHandleSize(15)
	                          .setRange(0, 150)
	                          .setRangeValues((float) 80.5, 120)
	                          ;
	    MainP.customizeRange(ppmCompMinMaxRng) ;
	    cp5.getTooltip().register(ppmCompMinMaxRng, "- Default: 10:40 -") ;
	    ppmCompMinMaxRng.setTab("airSpeed") ;
	    controllers.add(ppmCompMinMaxRng);

	  }

	public static Numberbox getaSpeedResetNBox() {
		return aSpeedResetNBox;
	}

	public static Range getPpmRngCompMinMaxRng() {
		return ppmRngCompMinMaxRng;
	}

	public static Range getPpmCompMinMaxRng() {
		return ppmCompMinMaxRng;
	}

	public static List<Object> getControllers() {
		return controllers;
	}

	public static void draw(MainP mainP) {
		mainP.stroke(MainP.blueAct) ;     // blue border
		mainP.strokeWeight(3) ;
		mainP.noFill() ;
		mainP.rect(4, 106, 442, 148) ;
		mainP.line(4, 142, 446, 142) ;
		mainP.strokeWeight(1) ;
		mainP.noStroke() ;

		TabPPM.drawPPMzone(mainP, cp5);

		// separation lines
		mainP.stroke(MainP.darkBackGray) ;
		mainP.line(10, 184, 440, 184) ;
		mainP.noStroke() ;

		if ( TabPPM.getPpmTgl().getValue() == 0.0 ) {
			aSpeedResetNBox.lock()
			               .setColorBackground(MainP.grayedColor)
			               .setColorForeground(MainP.grayedColor) 
			               .setColorValueLabel(MainP.grayedColor)
			               .setColorCaptionLabel(MainP.grayedColor);

			cp5.getController("ppmRngCompL").setColorValueLabel(MainP.grayedColor) ;
			ppmRngCompMinMaxRng.lock()
			                   .setColorForeground(MainP.grayedColor)
			                   .setColorBackground(MainP.grayedColor)
			                   .setColorValueLabel(MainP.grayedColor)
			                   .setColorCaptionLabel(MainP.grayedColor);

			cp5.getController("ppmCompL").setColorValueLabel(MainP.grayedColor) ;
			ppmCompMinMaxRng.lock()
			                .setColorForeground(MainP.grayedColor)
			                .setColorBackground(MainP.grayedColor)
			                .setColorValueLabel(MainP.grayedColor)
			                .setColorCaptionLabel(MainP.grayedColor);

		} else {
			aSpeedResetNBox.unlock()
			               .setColorBackground(MainP.darkBackGray)
			               .setColorValueLabel(MainP.white)
			               .setColorCaptionLabel(mainP.color(0));

			cp5.getController("ppmRngCompL").setColorValueLabel(mainP.color(0)) ;
			ppmRngCompMinMaxRng.unlock()
			                   .setColorForeground(MainP.blueAct)
			                   .setColorBackground(MainP.darkBackGray)
			                   .setColorValueLabel(MainP.white)
			                   .setColorCaptionLabel(mainP.color(0));

			cp5.getController("ppmCompL").setColorValueLabel(mainP.color(0)) ;
			ppmCompMinMaxRng.unlock()
			                .setColorForeground(MainP.blueAct)
			                .setColorBackground(MainP.darkBackGray)
			                .setColorValueLabel(MainP.white)
			                .setColorCaptionLabel(mainP.color(0));
		}

		// ----------------- Texfield and Numberbox mouse-over -----------------
		if (cp5.isMouseOver(aSpeedResetNBox)) {
			aSpeedResetNBox.setColorForeground(MainP.orangeAct);
		} else {
			aSpeedResetNBox.setColorForeground(MainP.grayedColor);
		}
	}

}
