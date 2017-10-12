package rise.smarthome.model.devices;

import rise.smarthome.util.ListUtils;


public class TemperatureSensor extends Sensor {

	public TemperatureSensor(int pin, boolean isAnalog) {
		super(pin, isAnalog,"Temperature Sensor");
	}

	@Override
	protected int[] activationValues() {
		return  ListUtils.createArrayRange(0, 100);
	}

}
