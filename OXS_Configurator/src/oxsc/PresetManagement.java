package oxsc;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import gui.TabGeneralSettings;

public class PresetManagement {  // TODO
	
	private static List<List<Object>> uiUnits = new ArrayList<>();
	@SuppressWarnings("unused")
	private File presetDir;
	//private final ControlP5 cp5 ;
	int sdrg;

	public PresetManagement() {
		uiUnits.add(TabGeneralSettings.getControllers());
		
	}
	
	public static List<List<Object>> getUiUnits() {
		return uiUnits;
	}

	public void presetLoad(File selection) {
		// TODO preset load
	}

}
