package oxsc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import controlP5.ControlP5;
import gui.TabData;
import gui.TabGeneralSettings;

public class PresetManagement {
	
	private static ControlP5 cp5;
	private static List<List<Object>> uiUnits = new ArrayList<>();
	@SuppressWarnings("unused")
	private File presetDir;

	public PresetManagement(ControlP5 cp5) {
		PresetManagement.cp5 = cp5;
		addControllersList();		
	}
	
	public static void addControllersList(){
		uiUnits.add(TabGeneralSettings.getControllers());

		uiUnits.add(TabData.getControllers());
	}
	
	public static List<List<Object>> getUiUnits() {
		return uiUnits;
	}

	public static void presetLoad(File selection) throws FileNotFoundException, IOException {
		try (BufferedReader buff = new BufferedReader(new FileReader(selection))) {
			String line;
			while ((line = buff.readLine()) != null) {
				if (line.charAt(0) != '#') {
					String[] temp = line.split(" - ");
					System.out.println("Loading " + temp[0] + " settings...");
					if (cp5.getController(temp[0]) instanceof controlP5.Textfield && temp.length > 1) {
						controlP5.Textfield textField = (controlP5.Textfield) cp5.getController(temp[0]);
						textField.setText(temp[1]);
					} else if (cp5.getGroup(temp[0]) instanceof controlP5.DropdownList) {
						controlP5.DropdownList dropDownList = (controlP5.DropdownList) cp5.getGroup(temp[0]);
						dropDownList.setValue(Float.parseFloat(temp[1]));
					} else if (temp.length > 1) {
						cp5.getController(temp[0]).setValue(Float.parseFloat(temp[1]));
					}
				}
			}
		}
	}

	public static void presetSave(File selection) throws FileNotFoundException {
		// println("User selected " + selection.getAbsolutePath());
		try (PrintWriter output = new PrintWriter(selection)){
			output.println("# OXS Configurator " + Validation.getOxsCversion() + " preset file created the " + MainP.date);
			PresetManagement.getUiUnits().stream().forEach(uiU -> uiU.stream().forEach(c -> {
				if (c instanceof controlP5.Textfield) {
					controlP5.Textfield textField = (controlP5.Textfield) c;
					output.println(textField.getName() + " - " + textField.getText());
				}
				if (c instanceof controlP5.DropdownList) {
					controlP5.DropdownList dropDownList = (controlP5.DropdownList) c;
					output.println(dropDownList.getName() + " - " + dropDownList.getValue());
				}
				if (c instanceof controlP5.Numberbox) {
					controlP5.Numberbox numberBox = (controlP5.Numberbox) c;
					output.println(numberBox.getName() + " - " + numberBox.getValue());
				}
				if (c instanceof controlP5.Toggle) {
					controlP5.Toggle toggle = (controlP5.Toggle) c;
					output.println(toggle.getName() + " - " + toggle.getValue());
				}
			}));
		}
	}

}
