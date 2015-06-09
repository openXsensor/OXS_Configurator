package oxsc;

import java.util.ArrayList;

public class AnalogPin {

	private String name;

	private static ArrayList<AnalogPin> analogPinList = new ArrayList<AnalogPin>();

	public static void addToList(AnalogPin newPin) {
		analogPinList.add(newPin);
	}

	public static void removeFromList(String pinName) {
		for (int i = analogPinList.size() - 1; i >= 0; i--) {
			// println( "sensorType for n°: " + i ) ;
			if (analogPinList.get(i).name == pinName)
				analogPinList.remove(analogPinList.get(i));
		}
	}

	public AnalogPin(String name) {
		for (int i = 0; i <= 7; i++) {
			analogPinList.add(new AnalogPin(name));
		}
	}

	public static ArrayList<AnalogPin> getAnalogPinList() {
		return analogPinList;
	}

}
