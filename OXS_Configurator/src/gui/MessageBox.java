package gui;

import controlP5.Button;
import controlP5.ControlP5;
import controlP5.Group;
import controlP5.Textarea;
import oxsc.MainP;
import oxsc.Validation;
import processing.core.PApplet;
import processing.data.StringList;

public class MessageBox {
	
//	private static ControlP5 cp5;
//	private static MainP mainP;
	
	private static Group group;
	private static Textarea textarea;
	private static Button buttonOKBtn;
	private static Button buttonCancel;
	private static StringList messageList = new StringList();
	private static int mBoxWidth = 400;
	private static int mBoxHeight = 320;

	public MessageBox(ControlP5 cp5, MainP mainP) {
		//MessageBox.cp5 = cp5;
		//MessageBox.mainP = mainP;

		// create a group to store the messageBox elements
		group = cp5.addGroup("messageBox", mainP.width / 2 - mBoxWidth / 2, 76, mBoxWidth)
				   .setBackgroundHeight(mBoxHeight)
				   .setBackgroundColor(mainP.color(240))
				   .setTab("global")
				   .hideBar()
				   .hide()
				   ;
	
		// add a Textarea to the messageBox.
		textarea = cp5.addTextarea("messageBoxLabel")
		              .setPosition(5,5)
		              .setSize(mBoxWidth - 10, mBoxHeight - 48)
		              .setLineHeight(14)
		              .setColor(MainP.white)
		              .setColorActive(MainP.orangeAct)
		              //.setBorderColor(color(0))
		              .setColorBackground(mainP.color(120))
		              .setColorForeground(MainP.blueAct)
		              .setScrollBackground(mainP.color(80))
		              //.setTab("global")
		              ;
		textarea.moveTo(group) ;
	
		// OK button to the messageBox.
		buttonOKBtn = cp5.addButton(mainP, "btnOK", "buttonOK", 0, mainP.width / 2 - 60, 218, 80, 30)
		                 .moveTo(group)
		                 .setColorForeground(mainP.color(MainP.blueAct))
		                 .setColorBackground(mainP.color(100))
		                 .setColorActive(mainP.color(MainP.orangeAct));
		buttonOKBtn.getCaptionLabel().setFont(MainP.font20);
		buttonOKBtn.getCaptionLabel().toUpperCase(false);
		buttonOKBtn.getCaptionLabel().align(ControlP5.CENTER, ControlP5.CENTER).setPaddingX(10);
	
		// Cancel button to the messageBox.
		buttonCancel = cp5.addButton(mainP, "btnCancel", "buttonCancel", 0, mBoxWidth / 2 + 5, mBoxHeight - 37, 80, 30)
		                  .moveTo(group)
		                  .setCaptionLabel("Cancel")
		                  .setColorForeground(MainP.blueAct)
		                  .setColorBackground(mainP.color(100))
		                  .setColorActive(MainP.orangeAct)
		                  .hide()
		                  ;
		buttonCancel.getCaptionLabel().setFont(MainP.font20) ;
		buttonCancel.getCaptionLabel().toUpperCase(false) ;
		buttonCancel.getCaptionLabel().align(ControlP5.CENTER, ControlP5.CENTER).setPaddingX(10) ;

	}

	public static void mbOkCancel() {
		buttonOKBtn.setPosition(mBoxWidth / 2 - 80 - 5, mBoxHeight - 37)
		           .setSize(80, 30)
		           .setCaptionLabel("OK");

		buttonCancel.show();
	}

	public static void mbClose() {
		buttonOKBtn.setPosition(mBoxWidth / 2 - 40 , mBoxHeight - 37)
		           .setSize(80, 30)
		           .setCaptionLabel("CLOSE");

		buttonCancel.hide();
	}

	public static void about() {
	
		mbClose() ;
	
		messageList.clear() ;
	
		messageList.append( "                            OXS Configurator " + Validation.getOxsCversion() + " for OXS " + Validation.getOxsVersion() ) ;
		messageList.append( "                                                       ---" ) ;
		messageList.append( "                         -- OpenXsensor configuration file GUI --" ) ;
		messageList.append( "\n" ) ;
		messageList.append( "Contributors:" ) ;
		messageList.append( "" ) ;
		messageList.append( "- Rainer Schloßhan" ) ;
		messageList.append( "- Bertrand Songis" ) ;
		messageList.append( "- André Bernet" ) ;
		messageList.append( "- Michael Blandford" ) ;
		messageList.append( "- Michel Strens" ) ;
		messageList.append( "- David Laburthe" ) ;
		messageList.append( "" ) ;
		messageList.append( "" ) ;
	
		String[] messageListArray = MessageBox.getMessageList().array() ;
	
		String joinedMessageList = PApplet.join(messageListArray, "\n") ;
	
		textarea.setText(joinedMessageList) ;
	
		buttonOKBtn.setColorForeground(MainP.orangeAct) ;
		buttonOKBtn.setColorBackground(MainP.darkBackGray) ;
		buttonOKBtn.setColorActive(MainP.blueAct) ;
		group.setBackgroundColor(MainP.blueAct) ;
		group.show() ;
	}

	public static Group getGroup() {
		return group;
	}

	public static Textarea getTextarea() {
		return textarea;
	}

	public static Button getButtonOKBtn() {
		return buttonOKBtn;
	}

	public static StringList getMessageList() {
		return messageList;
	}
	
}
