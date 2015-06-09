package oxsc;

import processing.core.PApplet;
import controlP5.ControlP5;
import controlP5.DropdownList;

public class Vario extends Sensor {
	


	//private int parameters ;

	  public void addOXSdata() {
	    String name = this.getName() ;

	    if ( name == "vario" ) {
	      new OXSdata("ALTIMETER", "Altitude", name) ;
	      new OXSdata("VERTICAL_SPEED", "Vertical Speed", name) ;
	      new OXSdata("ALT_OVER_10_SEC", "Alt. over 10 seconds", name) ;
	      new OXSdata("SENSITIVITY", "Vario sensitivity", name) ;

	      cp5.get(DropdownList.class, "vSpeed1").addItem("       Vario 1", 0) ;
	      cp5.get(DropdownList.class, "vSpeed2").addItem("       Vario 1", 0) ;
	      cp5.get(DropdownList.class, "vSpeed1").setValue(0) ;

	      if ( MainP.airSpeed != null ) {  // TODO better
	        new OXSdata("PRANDTL_DTE", "Prandtl dTE", "varAspeed") ;
	        new OXSdata("PRANDTL_COMPENSATION", "Prandtl Compensation", "varAspeed") ;
	        
	        cp5.get(DropdownList.class, "vSpeed1").addItem(" V1 + A.Speed", 2) ;
	        cp5.get(DropdownList.class, "vSpeed2").addItem(" V1 + A.Speed", 2) ;
	      }
	    } else {
	      new OXSdata("ALTIMETER_" + name.substring(5), "Altitude " + name.substring(5), name) ;
	      new OXSdata("VERTICAL_SPEED_" + name.substring(5), "Vertical Speed " + name.substring(5), name) ;
	      new OXSdata("ALT_OVER_10_SEC_" + name.substring(5), "Alt. over 10 seconds " + name.substring(5), name) ;

	      cp5.get(DropdownList.class, "vSpeed1").addItem("       Vario 2", 1) ;
	      cp5.get(DropdownList.class, "vSpeed2").addItem("       Vario 2", 1) ;
	      cp5.get(DropdownList.class, "vSpeed2").setValue(1) ;

	    }
	  }
	  
	  public void removeSensor() {
	    
	    if ( this.getName() == "vario" ) {
	      cp5.get(DropdownList.class, "vSpeed1").removeItem("       Vario 1") ;
	      cp5.get(DropdownList.class, "vSpeed2").removeItem("       Vario 1") ;
	      
	      //if ( airSpeed == null ) {  // TODO better
	        cp5.get(DropdownList.class, "vSpeed1").removeItem(" V1 + A.Speed") ;
	        cp5.get(DropdownList.class, "vSpeed2").removeItem(" V1 + A.Speed") ;
	      //}
	        MainP.tabData.resetSentDataFields( "varAspeed" ) ;
	      OXSdata.removeFromList( "varAspeed" ) ;
	      PApplet.println("remove varAspeed") ;
	    } else {
	      cp5.get(DropdownList.class, "vSpeed1").removeItem("       Vario 2") ;
	      cp5.get(DropdownList.class, "vSpeed2").removeItem("       Vario 2") ;
	    }
	    
	    MainP.tabData.resetSentDataFields(this.getName()) ;
	    OXSdata.removeFromList( this ) ;
	    PApplet.println("remove " + this.getName() ) ;
	    updateUIoXSdataList() ;
	    
	  }
	  
	  public Vario(PApplet p, ControlP5 cp5, String name) { super(p, cp5, name) ; }

}
