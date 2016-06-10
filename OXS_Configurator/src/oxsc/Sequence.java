package oxsc;

import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

import controlP5.ControlP5;
import gui.TabSequencer;

public class Sequence {

	private static final boolean DEBUG = true;
	private static final String[] SEQUENCE_NAMES = new String[] { "-100", "-75", "-50", "-25", "0", "25", "50", "75",
			"100", "low" };
	private static final int PIN_NUMBER = 6;

	private static List<Sequence> sequenceList = new ArrayList<>();
	private static int stepId = 1;
	private static double stepTime;
	private static long timer = System.currentTimeMillis();
	private static DecimalFormatSymbols fs = new DecimalFormatSymbols();
	private static DecimalFormat df;

	static {
		fs.setDecimalSeparator('.');
		df = new DecimalFormat("#0.0", fs);
	}

	private String name;
	private boolean active;
	private List<SequenceStep> stepList = new ArrayList<>();

	private Sequence(String name) {
		this.name = name;
		active = false;
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

	public static Sequence getSelectedSequ(String name) {
		sequenceList.stream().filter(s -> !s.name.equals(name))
				.forEach(s -> s.stepList.stream().forEach(st -> st.hideControllers()));
		sequenceList.stream().filter(s -> s.name.equals(name))
				.forEach(s -> s.stepList.stream().forEach(st -> st.showControllers()));
		return sequenceList.stream().filter(s -> s.name.equals(name)).findFirst().get();
	}

	public void addStep() {
		if (stepId == 0)
			resetStepId();
		if (stepList.size() < TabSequencer.getSequenceStepMaxNumber()) {
			stepList.add(new SequenceStep(stepList.size() + 1, this.name));
			if (DEBUG) {
				System.out.println("Adding step n°" + stepList.size() + " in sequence " + this.name);
			}
		} else {
			if (DEBUG) {
				System.out.println("Can't add more step in sequence " + this.name);
			}
		}
	}

	public void removeStep() {
		if (stepList.size() > 0) {
			resetStepId();
			stepList.get(stepList.size() - 1).removeControllers();
			stepList.remove(stepList.size() - 1);
			if (DEBUG) {
				System.out.println("Removing step n°" + (stepList.size() + 1) + " in sequence " + this.name);
			}
		} else {
			if (DEBUG) {
				System.out.println("No more step to remove in sequence " + this.name);
			}
		}
	}

	public static void drawPreview(MainP mainP) {
		mainP.textFont(MainP.fontLabel, 12);
		mainP.textAlign(MainP.CENTER);

		// preview rewind button
		if (mainP.mouseX >= 205 && mainP.mouseX <= 226 && mainP.mouseY >= 234 && mainP.mouseY <= 255) {
			mainP.fill(MainP.blueAct);
			if (mainP.mousePressed) {
				mainP.fill(MainP.orangeAct);
				resetStepId();
			}
		} else {
			mainP.fill(MainP.darkBackGray);
		}
		mainP.rect(205, 234, 21, 21);
		mainP.fill(MainP.tabGray);
		mainP.rect(211, 238, 2, 13);
		mainP.triangle(213, 245, 220, 238, 220, 251);

		// preview num sequence
		mainP.stroke(MainP.darkBackGray);
		mainP.fill(MainP.tabGray);
		mainP.rect(229, 234, 30, 20);
		mainP.fill(0);
		mainP.text("s" + stepId, 229, 237, 30, 20);

		// preview sequence timer
		mainP.fill(MainP.darkBackGray);
		mainP.rect(260, 234, 35, 20);
		mainP.fill(MainP.white);
		mainP.text(df.format(stepTime) + "s", 260, 237, 35, 20);

		// preview pin border
		mainP.fill(MainP.tabGray);
		for (int i = 0; i < PIN_NUMBER; i++) {
			if (MainP.sequence.stepList.size() > 0)
				if (MainP.sequence.stepList.get(stepId - 1).getTglState(i))
					mainP.fill(MainP.lightOrange);
			mainP.rect(295 + i * 20, 234, 20, 20);
			mainP.fill(MainP.tabGray);
		}
		mainP.noStroke();

		// preview pin rectangle
		mainP.fill(MainP.grayedColor);
		for (int i = 0; i < PIN_NUMBER; i++) {
			if (MainP.sequence.stepList.size() > 0)
				if (MainP.sequence.stepList.get(stepId - 1).getTglState(i))
					mainP.fill(MainP.orangeAct);
			mainP.rect(298 + i * 20, 237, 15, 15);
			mainP.fill(MainP.grayedColor);
		}

		// preview pin text
		mainP.fill(MainP.lightBackGray);
		for (int i = 0; i < PIN_NUMBER; i++) {
			if (MainP.sequence.stepList.size() > 0)
				if (MainP.sequence.stepList.get(stepId - 1).getTglState(i))
					mainP.fill(0);
			mainP.text("" + (8 + i), 298 + i * 20, 237, 15, 15);
			mainP.fill(MainP.lightBackGray);
		}
		mainP.textAlign(MainP.LEFT, MainP.BASELINE);

		// number of step in current sequence
//		mainP.fill(0);
//		mainP.text(MainP.sequence.getStepNbr(), 20, 250);
//		mainP.noFill();

		if (MainP.sequence.stepList.size() > 0) {
			// Draw steps
			for (SequenceStep step : MainP.sequence.stepList) {
				step.drawStep(mainP);
				if (step.getId() == stepId) {
					step.drawStepBorderPreview(mainP);
				}
			}
		}
		MainP.sequence.playPreview(mainP);
	}

	public void playPreview(MainP mainP) {
		if (stepList.size() > 0) {
			if (System.currentTimeMillis() - timer > stepList.get(stepId - 1).getDuration()) {
				timer = System.currentTimeMillis();
				if (stepList.get(stepId - 1).getDuration() > 0) {
					stepId++;
				}
				if (stepId > stepList.size())
					resetStepId();
			}
			stepTime = (double) (System.currentTimeMillis() - timer) / 1000;
		} else {
			stepTime = 0;
			stepId = 0;
		}
	}

	public static List<Sequence> getList() {
		return sequenceList;
	}

	public List<SequenceStep> getStepList() {
		return stepList;
	}

	public String getName() {
		return name;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public static void resetStepId() {
		stepId = 1;
	}

	public static void saveSequencesPreset(PrintWriter output) {
		for (Sequence seq : sequenceList) {
			output.println("");
			for (SequenceStep step : seq.stepList) {
				step.getControllers().stream().forEach(c -> {
					if (c instanceof controlP5.Numberbox) {
						controlP5.Numberbox numberBox = (controlP5.Numberbox) c;
						output.print(seq.getName() + "stepID" + step.getId() + PresetManagement.getSplitChar()
								+ numberBox.getValue());
					} else if (c instanceof controlP5.Toggle) {
						controlP5.Toggle toggle = (controlP5.Toggle) c;
						output.print(PresetManagement.getSplitChar() + (int)toggle.getValue());
					}
				});
				output.println("");
			}
		}
	}

	public static boolean loadSequencesPreset(String[] temp) {
		for (Sequence seq : sequenceList) {
			boolean[] tglStates = new boolean[PIN_NUMBER];
			if (temp[0].startsWith(seq.getName())
					&& temp[0].contains("stepID")) {
				int stepId = Integer.parseInt(
						temp[0].substring((seq.getName() + "stepID").length()));
				if (DEBUG) {
					System.out.println("loadSequencesPreset setId:" + stepId);
				}
				if (MainP.cp5
						.get(seq.getName() + "stepDuration" + stepId) == null) {
					seq.addStep();
				}
				for (int i = 0; i < tglStates.length; i++) {
					tglStates[i] = (temp[i + 2].equals("1") || temp[i + 2].equals("true"));
				}
				seq.stepList.get(stepId - 1).setValues(
						Float.parseFloat(temp[1]), tglStates[0], tglStates[1],
						tglStates[2], tglStates[3], tglStates[4], tglStates[5]);
				return true;
			} else {
				// TODO 1 preset: unknown/invalid sequence step error
			}
		}
		System.out.println("Invalid sequence step ! :" + temp[0]);
		return false;// TODO 1 preset: unknown controller error
	}

	public static void clearAllStepList() {
		for (Sequence seq : sequenceList) {
			for (int i = seq.stepList.size(); i > 0; i--) {
				seq.removeStep();
			}
		}
	}

	public static void writeConf(PrintWriter output) {
		// sequence pins setup
		output.print("#define SEQUENCE_OUTPUTS 0b");
		for (int i = TabSequencer.getPinNumber() - 1; i >= 0; i--) {
			output.print((int) TabSequencer.getPinsTgl()[i].getValue());
		}
		output.println();
		// sequence step time unit = 10 * SEQUENCE_UNIT -> ms
		output.println("#define SEQUENCE_UNIT " + SequenceStep.getTimeUnit());
		output.println();
		// sequence settings
		for (Sequence seq : sequenceList) {
			if (seq.isActive() && seq.stepList.size() > 0) {
				String seqName;
				try {
					seqName = Integer.parseInt(seq.name) < 0
							? "m" + seq.name.substring(1) : seq.name;
				} catch (NumberFormatException e) {
					seqName = seq.name.toUpperCase();
				}
				output.print("#define SEQUENCE_" + seqName + "   ");
				for (int i = 0; i < seq.stepList.size(); i++) {
					output.print(seq.stepList.get(i).getDuration() / SequenceStep.getTimeUnit() / 10 + " , 0b");
					for (int j = TabSequencer.getPinNumber() - 1; j >= 0; j--) {
						output.print(seq.stepList.get(i).getTglState(j) ? 1 : 0);
					}
					if (i < seq.stepList.size() - 1) {
						output.print(" , ");
					} 
				}
				output.println();
			}
		}
		// sequence "low" voltage thresholds
		if (TabSequencer.getMinVolt6NboxValue() > 0 && TabSequencer.getSequLowTgl().getState()) {
			output.println();
			output.println("#define SEQUENCE_MIN_VOLT_6 " + TabSequencer.getMinVolt6NboxValue());
		}
		if (TabSequencer.getMinCellNboxValue() > 0 && TabSequencer.getSequLowTgl().getState()) {
			output.println();
			output.println("#define SEQUENCE_MIN_CELL " + TabSequencer.getMinCellNboxValue());
		}
	}
}
