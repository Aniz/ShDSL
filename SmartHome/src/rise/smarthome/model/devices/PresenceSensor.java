package rise.smarthome.model.devices;


public class PresenceSensor extends Sensor {
	
	public PresenceSensor(int pin, boolean isAnalog) {
		super(pin, isAnalog,"Presence Sensor");
	}

	@Override
	protected int[] activationValues() {
		int[] activated = {0};
		return activated;
	}
	
}
