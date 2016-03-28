package gui;

import controlP5.Button;
import controlP5.ControlP5;
import controlP5.Group;
import controlP5.Textarea;
import oxsc.MBoxFunctionButton;
import oxsc.MainP;

public class MessageBox {

	private static final int M_BOX_WIDTH = 400;
	private static final int M_BOX_HEIGHT = 320;
	private static Group messageBox;
	private static Textarea textArea;
	private static Button okBtn;
	private static Button cancelBtn;
	private static Button funcBtn;

	private static MBoxFunctionButton source;

	public MessageBox(ControlP5 cp5, MainP mainP) {

		// create a group to store the messageBox elements
		messageBox = cp5.addGroup("messageBox", mainP.width / 2 - M_BOX_WIDTH / 2, 76, M_BOX_WIDTH)
				   .setBackgroundHeight(M_BOX_HEIGHT)
				   .setBackgroundColor(mainP.color(240))
				   .setTab("global")
				   .hideBar()
				   .hide()
				   ;
	
		// add a Textarea to the messageBox.
		textArea = cp5.addTextarea("messageBoxTextArea")
		              .setPosition(5,5)
		              .setSize(M_BOX_WIDTH - 10, M_BOX_HEIGHT - 48)
		              .setLineHeight(14)
		              .setColor(MainP.white)
		              .setColorActive(MainP.orangeAct)
		              .setColorBackground(mainP.color(120))
		              .setColorForeground(MainP.blueAct)
		              .setScrollBackground(mainP.color(80))
		              .moveTo(messageBox)
		              ;
	
		// OK button to the messageBox.
		okBtn = cp5.addButton(mainP, "btnOK", "buttonOK", 0, M_BOX_WIDTH / 2 - 80 - 5, M_BOX_HEIGHT - 37, 80, 30)
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
		cancelBtn = cp5.addButton(mainP, "btnCancel", "buttonCancel", 0, M_BOX_WIDTH / 2 + 5, M_BOX_HEIGHT - 37, 80, 30)
		               .setColorForeground(MainP.blueAct)
		               .setColorBackground(MainP.lightBackGray)
		               .setColorActive(MainP.orangeAct)
		               .moveTo(messageBox)
		               ;
		cancelBtn.getCaptionLabel().setFont(MainP.font20) ;
		cancelBtn.getCaptionLabel().toUpperCase(false) ;
		cancelBtn.getCaptionLabel().align(ControlP5.CENTER, ControlP5.CENTER).setPaddingX(10);

		// function button to the messageBox.
		funcBtn = cp5.addButton("funcButton")
				.setColorForeground(MainP.blueAct)
				.setColorBackground(MainP.lightBackGray)
				.setColorActive(MainP.orangeAct)
				.moveTo(messageBox)
				.hide()
				;
		funcBtn.getCaptionLabel().setFont(MainP.font20) ;
		funcBtn.getCaptionLabel().toUpperCase(false) ;
		funcBtn.getCaptionLabel().align(ControlP5.CENTER, ControlP5.CENTER).setPaddingX(10);

	}

	public static void mbOkCancel() {
		okBtn.show();

		cancelBtn.setPosition(M_BOX_WIDTH / 2 + 5, M_BOX_HEIGHT - 37)
                 .setCaptionLabel("Cancel");
	}

	public static void mbClose() {
		cancelBtn.setPosition(M_BOX_WIDTH / 2 - 40 , M_BOX_HEIGHT - 37)
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
	
	public static void infosWithBtn(StringBuilder message, String btnLabel, MBoxFunctionButton source) {
		MessageBox.source = source;
		int btnSize = btnLabel.length()*11;
		textArea.setText(message.toString());
		
		mbClose();
		funcBtn.setPosition((M_BOX_WIDTH - btnSize) / 2, 150)
		       .setSize(btnSize, 35)
		       .setColorBackground(MainP.warnColor)
		       .setCaptionLabel(btnLabel)
		       .show()
		       ;
		
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
		funcBtn.hide();
	}

	public static void funcBtnAction() {
		source.action();
	}

}
