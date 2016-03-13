package gui;

import controlP5.Button;
import controlP5.ControlP5;
import controlP5.Group;
import controlP5.Textarea;
import oxsc.MainP;
import oxsc.Validation;

public class MessageBox {

	private static Group messageBox;
	private static Textarea textArea;
	private static Button okBtn;
	private static Button cancelBtn;
	private static int mBoxWidth = 400;
	private static int mBoxHeight = 320;
	private static boolean mBabout = false;

	public MessageBox(ControlP5 cp5, MainP mainP) {

		// create a group to store the messageBox elements
		messageBox = cp5.addGroup("messageBox", mainP.width / 2 - mBoxWidth / 2, 76, mBoxWidth)
				   .setBackgroundHeight(mBoxHeight)
				   .setBackgroundColor(mainP.color(240))
				   .setTab("global")
				   .hideBar()
				   .hide()
				   ;
	
		// add a Textarea to the messageBox.
		textArea = cp5.addTextarea("messageBoxTextArea")
		              .setPosition(5,5)
		              .setSize(mBoxWidth - 10, mBoxHeight - 48)
		              .setLineHeight(14)
		              .setColor(MainP.white)
		              .setColorActive(MainP.orangeAct)
		              .setColorBackground(mainP.color(120))
		              .setColorForeground(MainP.blueAct)
		              .setScrollBackground(mainP.color(80))
		              .moveTo(messageBox)
		              ;
	
		// OK button to the messageBox.
		okBtn = cp5.addButton(mainP, "btnOK", "buttonOK", 0, mainP.width / 2 - 60, 218, 80, 30)
		                 .setColorBackground(MainP.lightBackGray)
		                 .moveTo(messageBox)
		                 ;
		okBtn.getCaptionLabel().setFont(MainP.font20);
		okBtn.getCaptionLabel().toUpperCase(false);
		okBtn.getCaptionLabel().align(ControlP5.CENTER, ControlP5.CENTER).setPaddingX(10);
	
		// Cancel button to the messageBox.
		cancelBtn = cp5.addButton(mainP, "btnCancel", "buttonCancel", 0, mBoxWidth / 2 + 5, mBoxHeight - 37, 80, 30)
		                  .setCaptionLabel("Cancel")
		                  .setColorForeground(MainP.blueAct)
		                  .setColorBackground(MainP.lightBackGray)
		                  .setColorActive(MainP.orangeAct)
		                  .moveTo(messageBox)
		                  .hide()
		                  ;
		cancelBtn.getCaptionLabel().setFont(MainP.font20) ;
		cancelBtn.getCaptionLabel().toUpperCase(false) ;
		cancelBtn.getCaptionLabel().align(ControlP5.CENTER, ControlP5.CENTER).setPaddingX(10) ;

	}

	public static void mbOkCancel() {
		okBtn.setPosition(mBoxWidth / 2 - 80 - 5, mBoxHeight - 37)
		           .setSize(80, 30)
		           .setCaptionLabel("OK");

		cancelBtn.show();
	}

	public static void mbClose() {
		okBtn.setPosition(mBoxWidth / 2 - 40 , mBoxHeight - 37)
		           .setSize(80, 30)
		           .setCaptionLabel("CLOSE");

		cancelBtn.hide();
	}
	
	public static void infos(StringBuilder message) {
		
		textArea.setText(message.toString());
		
		mbClose();
		
		okBtn.setColorForeground(MainP.orangeAct);
		okBtn.setColorActive(MainP.blueAct);
		messageBox.setBackgroundColor(MainP.blueAct);
		messageBox.show();
	}
	
	public static void warning(StringBuilder message) {
		
		textArea.setText(message.toString());
		
		mbOkCancel();
		
		okBtn.setColorForeground(MainP.blueAct);
		okBtn.setColorActive(MainP.orangeAct);
		messageBox.setBackgroundColor(MainP.warnColor);
		messageBox.show();
	}

	public static void error(StringBuilder message) {
		
		textArea.setText(message.toString());
		
		mbClose();
		
		okBtn.setColorForeground(MainP.blueAct);
		okBtn.setColorActive(MainP.orangeAct);
		messageBox.setBackgroundColor(MainP.errorColor);
		messageBox.show();
	}
	
	public static void good(StringBuilder message) {
		
		textArea.setText(message.toString());
		
		mbOkCancel();
		
		okBtn.setColorForeground(MainP.blueAct);
		okBtn.setColorActive(MainP.orangeAct);
		messageBox.setBackgroundColor(MainP.okColor);
		messageBox.show();
	}
	
	public static void about() {
		
		mBabout = true;
	
		StringBuilder message = new StringBuilder();
	
		message.append("                            OXS Configurator " + Validation.getOxsCversion() + " for OXS " + Validation.getOxsVersion() + "\n");
		message.append("                                                       ---\n");
		message.append("                         -- OpenXsensor configuration file GUI --\n");
		message.append("\n");
		message.append("Contributors:\n");
		message.append("\n");
		message.append("- Rainer Schloßhan\n");
		message.append("- Bertrand Songis\n");
		message.append("- André Bernet\n");
		message.append("- Michael Blandford\n");
		message.append("- Michel Strens\n");
		message.append("- David Laburthe\n");

		infos(message);
	}

	public static Group getGroup() {
		return messageBox;
	}

	public static boolean ismBabout() {
		return mBabout;
	}

	public static void setmBabout(boolean mBabout) {
		MessageBox.mBabout = mBabout;
	}
	
}
