package rise.smarthome.model.devices;

import rise.smarthome.util.ListUtils;

public class LightSensor extends Sensor {

	public LightSensor(int pin, boolean isAnalog) {
		super(pin, isAnalog,"Light Sensor");
	}

	@Override
	protected int[] activationValues() {
		return ListUtils.createArrayRange(0, 100);
	}
}
