package oxsc;

import gui.TabData;
import processing.core.PApplet;
import controlP5.ControlP5;

public class Rpm extends Sensor {
	
	  //private int parameters ;

	  /**
	 * 
	 */
	private static final long serialVersionUID = 3858933033861114605L;

	public void addOXSdata() {
	    new OXSdata("RPM", "RPM", this.getName(), null) ;
	  }

	  public void removeSensor() {
	    OXSdata.removeFromList( this ) ;
	    TabData.resetSentDataFields() ;
	    //updateUIoXSdataList() ;
	    Sensor.getSensorList().remove(this);
	  }

	  public Rpm(PApplet p, ControlP5 cp5, String name) { super(p, cp5, name) ; }
	}