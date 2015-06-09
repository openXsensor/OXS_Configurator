package oxsc;

import java.io.File;

import controlP5.ControlP5;

public class PresetManagement {
	
	private File presetDir;
	private final ControlP5 cp5 ;
	int sdrg;

	public PresetManagement(int sdrg, ControlP5 cp5) {
		this.cp5 = cp5;
		this.sdrg = sdrg;
	}
	
	public void presetLoad(File selection) { // TODO preset load
		if (selection == null) {
			//println("Window was closed or the user hit cancel.") ;
		} else {
			//println("User selected " + selection.getAbsolutePath()) ;
			//cp5.setBroadcast(false);
			cp5.loadProperties(selection.getAbsolutePath()) ;

			// Hack to keep slider labels alignement
			cp5.getController("varioHysteresis").getCaptionLabel().align(ControlP5.LEFT_OUTSIDE, ControlP5.CENTER).setPaddingX(10) ;
			cp5.getController("varioHysteresis").getValueLabel().align(ControlP5.RIGHT_OUTSIDE, ControlP5.CENTER).setPaddingX(10) ;
			//cp5.setBroadcast(true);
		}
	}

}
