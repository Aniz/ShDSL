package rise.smarthome.model.devices;

public class AirConditioner extends Actuator {

	public AirConditioner(int pin, boolean isAnalog) {
		super(pin, isAnalog,"Air Conditioner");
 	}
        @Override
	public String toString() {
		return "AirConditioner [state=" + getState() + ", pin=" + getPin()+ "]";
	}
}
