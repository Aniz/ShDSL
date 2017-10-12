package rise.smarthome.model.devices;


public class Led extends Actuator{
	public Led(int pin, boolean isAnalog) {
		super(pin, isAnalog,"Led");
	}
	@Override
	public String toString() {
		return "Led [state=" + getState() + ", pin=" + getPin()+ "]";
	}
}
