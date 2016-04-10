package oxsc;

import java.util.ArrayList;
import java.util.List;

import controlP5.ControlP5;
import gui.TabSequencer;

public class Sequence {

	private static final boolean DEBUG = true;
	private static List<Sequence> sequenceList = new ArrayList<>();
	private static final String[] SEQUENCE_NAMES = new String[] { "-100", "-75", "-50", "-25", "0", "25", "50", "75",
			"100", "low" };
	private static final int PIN_NUMBER = 6;

	private String name;
	private List<SequenceStep> stepList = new ArrayList<>();

	private Sequence(String name) {
		this.name = name;
		sequenceList.add(this);
		if (DEBUG) {
			System.out.println("New sequence: " + this.name + " created !");
		}
	}

	public static void createSequences(ControlP5 cp5) {
		for (String name : SEQUENCE_NAMES) {
			new Sequence(name);
		}
		TabSequencer.populateSequChoiceDdl(SEQUENCE_NAMES);
	}

	public void addStep() {
		if (stepList.size() < TabSequencer.getSequenceStepMaxNumber()) {
			stepList.add(new SequenceStep());
		}
	}

	public void removeStep() {
		stepList.remove(stepList.size() - 1);
	}
	// TODO 1 create steps

	public static void drawPreview(MainP mainP, ControlP5 cp5) {
		mainP.textFont(MainP.fontLabel, 12);
		mainP.textAlign(MainP.CENTER);

		// preview return button
		if (mainP.mouseX >= 205 && mainP.mouseX <= 226 && mainP.mouseY >= 234 && mainP.mouseY <= 255) {
			mainP.fill(MainP.blueAct);
			if (mainP.mousePressed)
				mainP.fill(MainP.orangeAct);
		} else {
			mainP.fill(MainP.darkBackGray);
		}
		mainP.rect(205, 234, 21, 21);
		mainP.fill(MainP.white);
		mainP.rect(211, 238, 2, 13);
		mainP.beginShape();
		mainP.vertex(213, 245);
		mainP.vertex(220, 238);
		mainP.vertex(220, 251);
		mainP.endShape(MainP.CLOSE);

		// preview num sequence
		mainP.stroke(MainP.darkBackGray);
		mainP.fill(MainP.tabGray);
		mainP.rect(229, 234, 30, 20);
		mainP.fill(0);
		mainP.text("sX", 229, 237, 30, 20); // TODO 1 replace with field

		// preview sequence timer
		mainP.fill(MainP.darkBackGray);
		mainP.rect(260, 234, 35, 20);
		mainP.fill(MainP.white);
		mainP.text("99.9s", 260, 237, 35, 20); // TODO 1 replace with field

		// preview pin border
		mainP.fill(MainP.tabGray);
		for (int i = 0; i < PIN_NUMBER; i++) {
			mainP.rect(295 + i * 20, 234, 20, 20);
		}
		mainP.noStroke();

		// preview pin rectangle
		mainP.fill(MainP.grayedColor);
		for (int i = 0; i < PIN_NUMBER; i++) {
			mainP.rect(298 + i * 20, 237, 15, 15);
		}

		// preview pin text
		mainP.fill(MainP.lightBackGray);
		for (int i = 0; i < PIN_NUMBER; i++) {
			mainP.text("" + (8 + i), 298 + i * 20, 237, 15, 15);
		}
		mainP.textAlign(MainP.LEFT, MainP.BASELINE);
	}

}
