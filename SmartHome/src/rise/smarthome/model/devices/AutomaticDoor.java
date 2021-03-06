package rise.smarthome.model.devices;

public class AutomaticDoor extends Actuator {

	public AutomaticDoor(int pin, boolean isAnalog) {
		super(pin, isAnalog,"Automatic Door");
	}
        @Override
	public String toString() {
		return "Automatic Door [state=" + getState() + ", pin=" + getPin()+ "]";
	}

}
