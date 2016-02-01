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
import gui.TabGeneralSettings;
import gui.TabPPM;
import gui.TabVario;
import gui.TabVoltage;
import gui.TabAirSpeed;
import gui.TabCurrent;
import gui.TabData;

public class PresetManagement {
	
	private static final String SPLIT_CHAR = " <> ";
	
	private static ControlP5 cp5;

	@SuppressWarnings("unused")
	private static File presetDir;
	
	private static List<List<Object>> uiUnits = new ArrayList<>();

	public PresetManagement(ControlP5 cp5) {
		PresetManagement.cp5 = cp5;
		addControllersList();		
	}
	
	public static void addControllersList(){
		uiUnits.add(TabGeneralSettings.getControllers());
		uiUnits.add(TabPPM.getControllers());
		uiUnits.add(TabVario.getControllers());
		uiUnits.add(TabAirSpeed.getControllers());
		uiUnits.add(TabVoltage.getControllers());
		uiUnits.add(TabCurrent.getControllers());

		uiUnits.add(TabData.getControllers());
	}

	public static List<List<Object>> getUiUnits() {
		return uiUnits;
	}

	public static void presetLoad(File selection) throws FileNotFoundException, IOException {
		//presetDir = new File(mainP.sketchPath("src/Preset/..."));
		try (BufferedReader buff = new BufferedReader(new FileReader(selection))) {
			String line;
			while ((line = buff.readLine()) != null) {
				if (line.length() > 0 && line.charAt(0) != '@') {
					String[] temp = line.split(SPLIT_CHAR);
					System.out.println("Loading " + temp[0] + " settings...");
					if (cp5.getGroup(temp[0]) instanceof controlP5.DropdownList) {
						controlP5.DropdownList dropDownList = (controlP5.DropdownList) cp5.getGroup(temp[0]);
						for (String[] stringArray : dropDownList.getListBoxItems()) {
							if (stringArray[1].equals(temp[1])) {
								dropDownList.setValue(Float.parseFloat(stringArray[2]));
							}
						}
						//dropDownList.setCaptionLabel(temp[1]);
					} else if (cp5.getController(temp[0]) instanceof controlP5.Toggle) {
						controlP5.Toggle toggle = (controlP5.Toggle) cp5.getController(temp[0]);
						toggle.setState(Boolean.parseBoolean(temp[1]));
					} else if (cp5.getController(temp[0]) instanceof controlP5.Numberbox) {
						controlP5.Numberbox numberbox = (controlP5.Numberbox) cp5.getController(temp[0]);
						numberbox.setValue(Float.parseFloat(temp[1]));
					} else if (cp5.getController(temp[0]) instanceof controlP5.Range) {
						controlP5.Range range = (controlP5.Range) cp5.getController(temp[0]);
						range.setLowValue(Float.parseFloat(temp[1]));
						range.setHighValue(Float.parseFloat(temp[2]));
					} else if (cp5.getController(temp[0]) instanceof controlP5.Slider) {
						controlP5.Slider slider = (controlP5.Slider) cp5.getController(temp[0]);
						slider.setValue(Float.parseFloat(temp[1]));
					} else if (cp5.getController(temp[0]) instanceof controlP5.Textfield && temp.length > 1) {
						controlP5.Textfield textField = (controlP5.Textfield) cp5.getController(temp[0]);
						textField.setText(temp[1]);
					}  
				}
			}
			TabGeneralSettings.getGenTab().bringToFront();
		}
	}

	public static void presetSave(File selection) throws FileNotFoundException {
		// System.out.println("User selected " + selection.getAbsolutePath());
		try (PrintWriter output = new PrintWriter(selection)){
			output.println("@ OXS Configurator " + Validation.getOxsCversion() + " preset file created the " + MainP.date);
			uiUnits.stream().forEach(uiU -> {
				output.println();
				uiU.stream().forEach(c -> {
					if (c instanceof controlP5.DropdownList) {
						controlP5.DropdownList dropDownList = (controlP5.DropdownList) c;
						output.println(dropDownList.getName() + SPLIT_CHAR + dropDownList.getCaptionLabel().getText()
								+ SPLIT_CHAR + (int)dropDownList.getValue());
					} else if (c instanceof controlP5.Toggle) {
						controlP5.Toggle toggle = (controlP5.Toggle) c;
						output.println(toggle.getName() + SPLIT_CHAR + toggle.getState());
					} else if (c instanceof controlP5.Numberbox) {
						controlP5.Numberbox numberBox = (controlP5.Numberbox) c;
						output.println(numberBox.getName() + SPLIT_CHAR + Math.floor(numberBox.getValue()*100.0)/100);
					} else if (c instanceof controlP5.Range) {
						controlP5.Range range = (controlP5.Range) c;
						output.println(range.getName() + SPLIT_CHAR + range.getLowValue() + SPLIT_CHAR + range.getHighValue());
					} else if (c instanceof controlP5.Slider) {
						controlP5.Slider slider = (controlP5.Slider) c;
						output.println(slider.getName() + SPLIT_CHAR + (int)slider.getValue());
					} else if (c instanceof controlP5.Textfield) {
						controlP5.Textfield textField = (controlP5.Textfield) c;
						output.println(textField.getName() + SPLIT_CHAR + textField.getText());
					}
				});
			});
		}
	}

}
