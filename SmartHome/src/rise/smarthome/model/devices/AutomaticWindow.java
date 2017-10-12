package rise.smarthome.model.devices;

public class AutomaticWindow extends Actuator {

	public AutomaticWindow(int pin, boolean isAnalog) {
		super(pin, isAnalog,"Automatic Window");
 	}
        	@Override
	public String toString() {
		return "Window [state=" + getState() + ", pin=" + getPin()+ "]";
	}
}


