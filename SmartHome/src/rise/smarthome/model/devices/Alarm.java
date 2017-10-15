package rise.smarthome.model.devices;


public class Alarm extends Actuator{
	public Alarm(int pin, boolean isAnalog) {
		super(pin, isAnalog,"Alarm");
	}
        @Override
	public String toString() {
		return "Alarm [state=" + getState() + ", pin=" + getPin()+ "]";
	}
}
