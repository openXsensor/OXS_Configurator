package gui;

import oxsc.MainP;
import processing.core.PApplet;
import controlP5.ControlP5;
import controlP5.Controller;
import controlP5.Numberbox;
import controlP5.Range;

public class TabAirSpeed {
	
	//@SuppressWarnings("unused")
	//private final ControlP5 cp5 ;
	@SuppressWarnings("unused")
	private final PApplet p ; // TODO check if needed
	private static Numberbox aSpeedResetNBox;
	private static Range ppmRngCompMinMaxRng;
	private static Range ppmCompMinMaxRng;
	
	public TabAirSpeed(PApplet p, ControlP5 cp5) {
		
		//this.cp5 = cp5;
		this.p = p;
	    
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
	    aSpeedResetNBox = cp5.addNumberbox("aSpeedReset")
	                         .setPosition(206, 154)
	                         .setSize(40, 20)
	                         .setColorActive(MainP.blueAct)
	                         .setRange(0, 100)
	                         .setMultiplier((float) 0.5)                     // set the sensitifity of the numberbox
	                         .setDirection(Controller.HORIZONTAL)    // change the control direction to left/right
	                         .setValue(100)
	                         .setCaptionLabel("PPM Air Speed reset                       ")
	                         .setColorCaptionLabel(0)
	                         .setTab("airSpeed")
	                         ;
	    aSpeedResetNBox.getCaptionLabel().align(ControlP5.LEFT_OUTSIDE, ControlP5.CENTER).setPaddingX(5) ;
	    aSpeedResetNBox.getCaptionLabel().toUpperCase(false) ;
	  
	    // PPM compensation at PPM range
	    cp5.addTextlabel("ppmRngCompL")
	       .setText("PPM range for compensation   Min.")
	       .setPosition(10, 197)
	       .setColorValueLabel(0)
	       .setTab("airSpeed")
	       ;
	    cp5.getProperties().remove(cp5.getController("ppmRngCompL")) ;
	  
	    ppmRngCompMinMaxRng = cp5.addRange("ppmRngCompMinMax")
	                             .setPosition(206, 195)
	                             .setSize(200, 20)
	                             .setCaptionLabel("Max.")
	                             .setHandleSize(15)
	                             .setRange(0, 100)
	                             .setRangeValues(60, 90)
	                             ;
	    MainP.customizeRange(ppmRngCompMinMaxRng) ;
	    cp5.getTooltip().register("ppmRngCompMinMax", "- Default: 10:40 -") ;
	    ppmRngCompMinMaxRng.setTab("airSpeed") ;
	  
	    // PPM compensation
	    cp5.addTextlabel("ppmCompL")
	       .setText("PPM compensation                  Min.")
	       .setPosition(10, 225)
	       .setColorValueLabel(0)
	       .setTab("airSpeed")
	       ;
	    cp5.getProperties().remove(cp5.getController("ppmCompL")) ;
	  
	    ppmCompMinMaxRng = cp5.addRange("ppmCompMinMax")
	                          .setPosition(206, 223)
	                          .setSize(200, 20)
	                          .setCaptionLabel("Max.")
	                          .setHandleSize(15)
	                          .setRange(0, 150)
	                          .setRangeValues((float) 80.5, 120)
	                          ;
	    MainP.customizeRange(ppmCompMinMaxRng) ;
	    cp5.getTooltip().register("ppmCompMinMax", "- Default: 10:40 -") ;
	    ppmCompMinMaxRng.setTab("airSpeed") ;
	    
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

}
