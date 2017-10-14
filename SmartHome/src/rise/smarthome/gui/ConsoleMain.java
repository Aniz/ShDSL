package {{systemName|lower}}.smarthome.gui;

import java.util.ArrayList;

import {{systemName|lower}}.smarthome.features.AutomatedIluminationByPresence;
import {{systemName|lower}}.smarthome.model.devices.Led;
import {{systemName|lower}}.smarthome.model.devices.PresenceSensor;

public class ConsoleMain {

	public static void main(String[] args) throws InterruptedException {
		Led led = new Led(13, false);
		AutomatedIluminationByPresence au = AutomatedIluminationByPresence.getInstance();
		PresenceSensor p = new PresenceSensor(7, false);
		au.setPresenceSensor(p);
		au.setLeds(new ArrayList<Led>());
		au.getLeds().add(led);
		au.getLedsToAutomate().add(led);
		au.setActive(true);
		while(true){
			au.proceedActions();
		}
	}
}
