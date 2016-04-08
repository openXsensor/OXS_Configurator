package gui;

import java.util.ArrayList;
import java.util.List;

import controlP5.ControlP5;
import oxsc.MainP;

public class TabSequencer {

	private static List<Object> controllers = new ArrayList<>();

	public TabSequencer(ControlP5 cp5) {
		cp5.getTab("sequencer").setHeight(20).setColorLabel(MainP.white)
				.setColorForeground(MainP.tabGray)
				.setColorBackground(MainP.darkBackGray)
				.setColorActive(MainP.orangeAct).setLabel("Sequencer").setId(8)
		// .hide()
		;
		cp5.getTab("sequencer").getCaptionLabel().toUpperCase(false);

		// -------- SETUP --------
		// Pins selection

		// Low voltage threshold

		// Active sequence choice

		// -------- EDITION --------
		// Sequence choice
		
		// Add/Remove step
		
		// Preview
		
		// OO steps
	}
}
