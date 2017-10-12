package {{systemName|lower}}.smarthome.model.devices;

import {{systemName|lower}}.smarthome.arduino.ArduinoControl;
import {{systemName|lower}}.smarthome.util.ListUtils;


public abstract class Sensor extends Hardware{
	
	public Sensor(int pin, boolean isAnalog, String name) {
		super(pin, isAnalog, name);
	}

	protected abstract int[] activationValues();
	
	public final void act(Actuator actuator) {
		int pinValue = getPinValue();
		if(ListUtils.isInRange(activationValues(), pinValue)){
			actuator.activate();
			return;
		}
		if(!ListUtils.isInRange(activationValues(), pinValue)){
			actuator.deactivate();
			return;
		}
	}

	public int getPinValue(){
		return ArduinoControl.getInstance().getPinValue(getPin(), isAnalog());
	}
	
	@Override
	public String toString() {
		return getName()+" [pin=" + getPin()+ "]";
	}

}
