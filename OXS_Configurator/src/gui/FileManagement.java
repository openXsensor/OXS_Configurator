package gui;

import controlP5.Button;
import controlP5.ControlP5;
import oxsc.MainP;

public class FileManagement {
	
	private static Button loadPresetBtn;
	private static Button savePresetBtn;
	private static Button writeConfBtn;

	public FileManagement(ControlP5 cp5) {
		
		// Load preset button
		loadPresetBtn = cp5.addButton("loadButton")
		                   .setColorForeground(MainP.blueAct)
		                   .setCaptionLabel("Load Preset")
		                   .setPosition(20, 419)
		                   .setSize(100, 25)
		                   .setTab("global")
		                   ;
		loadPresetBtn.getCaptionLabel().setFont(MainP.font16) ;
		loadPresetBtn.getCaptionLabel().toUpperCase(false) ;
		loadPresetBtn.getCaptionLabel().align(ControlP5.CENTER, ControlP5.CENTER) ;

		// Save preset button
		savePresetBtn = cp5.addButton("saveButton")
		                   .setColorForeground(MainP.orangeAct)
		                   .setColorActive(MainP.blueAct)
		                   .setCaptionLabel("Save Preset")
		                   .setPosition(140, 419)
		                   .setSize(100, 25)
		                   .setTab("global")
		                   ;
		savePresetBtn.getCaptionLabel().setFont(MainP.font16) ;
		savePresetBtn.getCaptionLabel().toUpperCase(false) ;
		savePresetBtn.getCaptionLabel().align(ControlP5.CENTER, ControlP5.CENTER) ;


		// Write button
		writeConfBtn = cp5.addButton("writeConfButton")
		                  .setColorForeground(MainP.orangeAct)
		                  .setColorActive(MainP.blueAct)
		                  .setCaptionLabel("Write Config")
		                  .setPosition(300, 416)
		                  .setSize(120, 30)
		                  .setTab("data")
		                  ;
		writeConfBtn.getCaptionLabel().setFont(MainP.font20) ;
		writeConfBtn.getCaptionLabel().toUpperCase(false) ;
		writeConfBtn.getCaptionLabel().align(ControlP5.CENTER, ControlP5.CENTER) ;
	}

	public static Button getLoadPresetBtn() {
		return loadPresetBtn;
	}

	public static Button getSavePresetBtn() {
		return savePresetBtn;
	}
	
	public static Button getWriteConfBtn() {
		return writeConfBtn;
	}
	

}
