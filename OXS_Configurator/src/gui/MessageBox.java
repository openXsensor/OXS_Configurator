package gui;

import controlP5.Button;
import controlP5.ControlP5;
import controlP5.Group;
import controlP5.Textarea;
import oxsc.MainP;

public class MessageBox {

	private static Group messageBox;
	private static Textarea textArea;
	private static Button okBtn;
	private static Button cancelBtn;
	private static int mBoxWidth = 400;
	private static int mBoxHeight = 320;

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
		okBtn = cp5.addButton(mainP, "btnOK", "buttonOK", 0, mBoxWidth / 2 - 80 - 5, mBoxHeight - 37, 80, 30)
				   .setCaptionLabel("Ok")
				   .setColorForeground(MainP.blueAct)
	               .setColorBackground(MainP.lightBackGray)
	               .setColorActive(MainP.orangeAct)
		           .moveTo(messageBox)
		           .hide()
		           ;
		okBtn.getCaptionLabel().setFont(MainP.font20);
		okBtn.getCaptionLabel().toUpperCase(false);
		okBtn.getCaptionLabel().align(ControlP5.CENTER, ControlP5.CENTER).setPaddingX(10);
	
		// Cancel / Close button to the messageBox.
		cancelBtn = cp5.addButton(mainP, "btnCancel", "buttonCancel", 0, mBoxWidth / 2 + 5, mBoxHeight - 37, 80, 30)
		               .setColorForeground(MainP.blueAct)
		               .setColorBackground(MainP.lightBackGray)
		               .setColorActive(MainP.orangeAct)
		               .moveTo(messageBox)
		               ;
		cancelBtn.getCaptionLabel().setFont(MainP.font20) ;
		cancelBtn.getCaptionLabel().toUpperCase(false) ;
		cancelBtn.getCaptionLabel().align(ControlP5.CENTER, ControlP5.CENTER).setPaddingX(10) ;

	}

	public static void mbOkCancel() {
		okBtn.show();

		cancelBtn.setPosition(mBoxWidth / 2 + 5, mBoxHeight - 37)
                 .setCaptionLabel("Cancel");
	}

	public static void mbClose() {
		cancelBtn.setPosition(mBoxWidth / 2 - 40 , mBoxHeight - 37)
		         .setCaptionLabel("Close");

		okBtn.hide();
	}
	
	public static void infos(StringBuilder message) {
		
		textArea.setText(message.toString());
		
		mbClose();
		
		cancelBtn.setColorForeground(MainP.orangeAct);
		cancelBtn.setColorActive(MainP.blueAct);
		messageBox.setBackgroundColor(MainP.blueAct);
		messageBox.show();
	}
	
	public static void warning(StringBuilder message) {
		
		textArea.setText(message.toString());
		
		mbOkCancel();
		
		cancelBtn.setColorForeground(MainP.blueAct);
		cancelBtn.setColorActive(MainP.orangeAct);
		messageBox.setBackgroundColor(MainP.warnColor);
		messageBox.show();
	}

	public static void error(StringBuilder message) {
		
		textArea.setText(message.toString());
		
		mbClose();
		
		cancelBtn.setColorForeground(MainP.blueAct);
		cancelBtn.setColorActive(MainP.orangeAct);
		messageBox.setBackgroundColor(MainP.errorColor);
		messageBox.show();
	}
	
	public static void good(StringBuilder message) {
		
		textArea.setText(message.toString());
		
		mbOkCancel();
		
		cancelBtn.setColorForeground(MainP.blueAct);
		cancelBtn.setColorActive(MainP.orangeAct);
		messageBox.setBackgroundColor(MainP.okColor);
		messageBox.show();
	}

	public static void close() {
		messageBox.hide();
	}

}
