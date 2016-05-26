package oxsc;

import java.util.ArrayList;
import java.util.List;

import controlP5.ControlP5;
import controlP5.Controller;
import controlP5.Numberbox;
import controlP5.Toggle;
import gui.TabSequencer;

public class SequenceStep {

	private static final int X = 52;
	private static final int Y = 276;
	private static final int X_GAP = 181;
	private static final int Y_GAP = 33;

	private int id;
	private Toggle[] pinsTgl = new Toggle[TabSequencer.getPinNumber()];
	private Numberbox stepDuration;
	private int xTranslate;
	private int yTranslate;
	private int yTranslate2;
	private List<Object> controllers = new ArrayList<>();

	public SequenceStep(int id, String seqName) {
		this.id = id;
		System.out.println("step id: " + id);
		xTranslate = X_GAP * (1 - (id % 2));
		yTranslate = Y_GAP * ((id + 1) / 2 - 1);
		yTranslate2 = Y_GAP * ((id + 1) / 2 - 2);

		stepDuration = MainP.cp5.addNumberbox(seqName + "stepDuration" + id)
				                .setPosition(X + 21 + xTranslate, Y + yTranslate)
				                .setSize(37, 21)
				                .setRange(0, 99.9f)
				                .setMultiplier(0.1f)
				                .setDecimalPrecision(1)
				                .setDirection(Controller.HORIZONTAL)
				                .setCaptionLabel("")
				                .setTab("sequencer");
		controllers.add(stepDuration);

		for (int i = 0; i < pinsTgl.length; i++) {
			pinsTgl[i] = MainP.cp5.addToggle(seqName + "Tgl" + id + i)
					              .setPosition(X + 21 + 38 + (3 + 15) * i + xTranslate, Y + 3 + yTranslate)
					              .setCaptionLabel("" + (8 + i));
			customizeToggle((Toggle) MainP.cp5.getController(seqName + "Tgl" + id + i));
		}
		TabSequencer.getSequChoiceDdl().bringToFront();
	}

	public void removeControllers() {
		for (Toggle tgl : pinsTgl) {
			tgl.remove();
			System.out.println("Removing toggle: " + tgl.getName());
		}
		stepDuration.remove();
	}

	public void drawStep(MainP mainP) {
		// Step ID Border
		mainP.noFill();
		mainP.stroke(MainP.darkBackGray);
		mainP.rect(X + xTranslate, Y + yTranslate, 20, 20);

		// ID Text
		mainP.fill(0);
		mainP.textAlign(MainP.CENTER);
		mainP.text("" + id, X + xTranslate, Y + 3 + yTranslate, 20, 20);
		mainP.noFill();
		mainP.textAlign(MainP.LEFT, MainP.BASELINE);

		// Step pins border & fill
		for (int i = 0; i < pinsTgl.length; i++) {
			mainP.noFill();
			// graying pins toggle if setup pins not available
			if (TabSequencer.getPinsTgl()[i].getState()) {
				pinsTgl[i].unlock().setColorBackground(MainP.darkBackGray);
				if (pinsTgl[i].getState()) {
					pinsTgl[i].setColorCaptionLabel(0);
					mainP.fill(MainP.lightOrange);
				} else {
					pinsTgl[i].setColorCaptionLabel(MainP.tabGray);
				}
			} else {
				if (pinsTgl[i].getState())
					pinsTgl[i].setState(false);
				pinsTgl[i].lock().setColorCaptionLabel(MainP.grayedColor).setColorBackground(MainP.grayedColor);
			}
			mainP.rect(X + 21 + 36 + (3 + 15) * i + xTranslate, Y + yTranslate, 18, 20);
		}
		mainP.noStroke();
		mainP.noFill();

		for (int i = 0; i < pinsTgl.length; i++) {
			// ------------- Side arrow -------------
			if ((id % 2) == 0) {
				mainP.fill(MainP.darkBackGray);
				mainP.rect(X + 166, Y + 10 + yTranslate, 7, 2);
				mainP.triangle(X + 166 + 7, Y + 8 + yTranslate, X + 166 + 7 + 8, Y + 8 + 3 + yTranslate, X + 166 + 7,
						Y + 14 + yTranslate);
				mainP.noFill();
			} else if ((id % 2) != 0 && id != 1) {
				// ------------- Return arrow -------------
				mainP.fill(MainP.darkBackGray);
				mainP.rect(X + 166 + X_GAP, Y + 10 + yTranslate2, 9, 2);
				mainP.rect(X + 166 + 7 + X_GAP, Y + 12 + yTranslate2, 2, 2);
				mainP.triangle(X + 166 + 5 + X_GAP, Y + 14 + yTranslate2, X + 166 + 11 + X_GAP, Y + 14 + yTranslate2,
						X + 166 + 8 + X_GAP, Y + 14 + 8 + yTranslate2);
				mainP.rect(X + 166 + 7 + X_GAP, Y + 23 + yTranslate2, 2, 5);
				mainP.rect(X - 13, Y + 26 + yTranslate2, 367, 2);
				mainP.rect(X - 13, Y + 28 + yTranslate2, 2, 17);
				mainP.rect(X - 11, Y + 43 + yTranslate2, 3, 2);
				mainP.triangle(X - 8, Y + 41 + yTranslate2, X - 8 + 8, Y + 41 + 3 + yTranslate2, X - 8,
						Y + 47 + yTranslate2);
				mainP.noFill();
			}
		}

		// ------------- Numberbox mouse-over -------------
		if (MainP.cp5.isMouseOver(stepDuration)) {
			stepDuration.setColorForeground(MainP.blueAct);
		} else {
			stepDuration.setColorForeground(MainP.grayedColor);
		}
	}

	public void drawStepBorderPreview(MainP mainP) {
		mainP.stroke(MainP.orangeAct);
		mainP.rect(X - 3 + xTranslate, Y - 3 + yTranslate, 171, 26);
		mainP.noStroke();
	}

	private void customizeToggle(Toggle pTgl) {
		pTgl.setColorForeground(MainP.blueAct)
		    .setColorBackground(MainP.grayedColor)
			.setColorCaptionLabel(MainP.grayedColor)
			.setSize(15, 15)
			.setTab("sequencer");
		// reposition the Labels
		pTgl.getCaptionLabel().align(ControlP5.CENTER, ControlP5.CENTER).toUpperCase(false);
		controllers.add(pTgl);
	}

	public void hideControllers() {
		for (Toggle tgl : pinsTgl) {
			tgl.hide();
		}
		stepDuration.hide();
	}

	public void showControllers() {
		for (Toggle tgl : pinsTgl) {
			tgl.show();
		}
		stepDuration.show();
	}

	public int getId() {
		return id;
	}

	public long getDuration() {
		return (long) (stepDuration.getValue() * 1000);
	}

	public boolean getTglState(int i) {
		return pinsTgl[i].getState();
	}

	public List<Object> getControllers() {
		return controllers;
	}

	public void setValues(float nBox, boolean pinTgl1, boolean pinTgl2, boolean pinTgl3, boolean pinTgl4, boolean pinTgl5, boolean pinTgl6) {
		stepDuration.setValue(nBox);
		pinsTgl[0].setState(pinTgl1);
		pinsTgl[1].setState(pinTgl2);
		pinsTgl[2].setState(pinTgl3);
		pinsTgl[3].setState(pinTgl4);
		pinsTgl[4].setState(pinTgl5);
		pinsTgl[5].setState(pinTgl6);
	}

}
