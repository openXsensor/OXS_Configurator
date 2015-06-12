package oxsc;

import gui.TabData;
import processing.core.PApplet;
import controlP5.ControlP5;

public class Rpm extends Sensor {
	
	  //private int parameters ;

	  public void addOXSdata() {
	    new OXSdata("RPM", "RPM", this.getName()) ;
	  }

	  public void removeSensor() {
	    TabData.resetSentDataFields(this.getName()) ;
	    OXSdata.removeFromList( this ) ;
	    updateUIoXSdataList() ;
	    Sensor.getSensorList().remove(this);
	  }

	  public Rpm(PApplet p, ControlP5 cp5, String name) { super(p, cp5, name) ; }
	}