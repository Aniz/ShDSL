// Autogenerated by SHomeDSL
package rise.smarthome.model.devices;

public class LightSensor extends Sensor {

	public LightSensor(int pin, boolean isAnalog) {
		super(pin, isAnalog,"Light Sensor");
 	}
        @Override
	public String toString() {
		return ""Light Sensor" [state=" + getState() + ", pin=" + getPin()+ "]";
	}
}