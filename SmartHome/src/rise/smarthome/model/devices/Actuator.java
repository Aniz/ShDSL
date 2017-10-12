package {{systemName|lower}}.smarthome.model.devices;

import {{systemName|lower}}.smarthome.arduino.ArduinoControl;

public abstract class Actuator extends Hardware {
	private int state;
	
	public Actuator(int pin, boolean isAnalog, String name) {
		super(pin, isAnalog, name);
		state=0;
	}

	public void activate(){
		ArduinoControl.getInstance().activate(this);
		state=1;
	}
	
	public void deactivate(){
		ArduinoControl.getInstance().deactivate(this);
		state=0;
	}
	
	
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}

}
