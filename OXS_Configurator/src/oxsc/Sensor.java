package oxsc;

import processing.core.PApplet;
import controlP5.ControlP5;

public abstract class Sensor implements OXSdataController {

	protected final ControlP5 cp5 ;
	protected final PApplet p ;

	private String name = "" ;

	public String getName() { return name ; }
	
	protected Sensor(PApplet p, ControlP5 cp5, String name) {

		this.cp5 = cp5;
		this.p = p;	

		this.name = name ;
		addOXSdata() ;
		updateUIoXSdataList() ;
		PApplet.println("Creation d'un objet " + this.getName() ) ;
		PApplet.println() ;
	}

	public void updateUIoXSdataList() {
		MainP.tabData.populateSentDataFields() ;
	}

	public abstract void addOXSdata() ;

	public abstract void removeSensor() ;

}
