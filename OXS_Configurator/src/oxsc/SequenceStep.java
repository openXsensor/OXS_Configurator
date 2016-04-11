package oxsc;

import java.time.Duration;

import controlP5.ControlP5;
import controlP5.Toggle;
import gui.TabSequencer;

public class SequenceStep {

	private int id;
	private Duration duration;
	private int x = 10;
	private int y = 300;
	private Toggle[] pinsTgl = new Toggle[TabSequencer.getPinNumber()];
	private Toggle testTgl;
	
	
	public SequenceStep(int id, String seqName) {
		this.id = id;
		System.out.println("step id: " + id);
		testTgl = MainP.cp5.addToggle(seqName + "Tgl" + id)
        .setPosition(x + 8 + 30 * id, y + 3)
        .setCaptionLabel("" + (8 + id));
		customizeToggle((Toggle)MainP.cp5.getController(seqName + "Tgl" + id));

	}

	public void removeTgl() {
		testTgl.remove();
		System.out.println("Removing toggle: " + testTgl.getName());
	}

	public void drawStep(MainP mainP) {
		mainP.stroke(MainP.darkBackGray);
		mainP.fill(MainP.tabGray);
		mainP.rect(x + 30 * id, y, 30, 20);
		mainP.noStroke();
		mainP.fill(0);
		mainP.text(id, x + 30 * id, y);
		mainP.noFill();

	}

	private void customizeToggle(Toggle pTgl) {
		pTgl.setColorForeground(MainP.blueAct)
		    .setColorBackground(MainP.darkBackGray)
		    .setColorCaptionLabel(0)
		    .setSize(15, 15)
		    .setTab("sequencer");
		// reposition the Labels
		pTgl.getCaptionLabel().align(ControlP5.CENTER, ControlP5.CENTER).toUpperCase(false);
	}

	public void hideTgl() {
		testTgl.hide();
	}

	public void showTgl() {
		testTgl.show();
	}
}
