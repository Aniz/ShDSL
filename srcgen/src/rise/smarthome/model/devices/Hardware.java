package rise.smarthome.model.devices;

public abstract class Hardware {

	private int pin;
	private String name;
	private boolean isAnalog;
	
	public Hardware(int pin, boolean isAnalog, String name) {
		this.pin = pin;
		this.isAnalog = isAnalog;
		this.name = name;
	}

	public int getPin() {
		return pin;
	}
	public void setPin(int pin) {
		this.pin = pin;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isAnalog() {
		return isAnalog;
	}
	public void setAnalog(boolean isAnalog) {
		this.isAnalog = isAnalog;
	}

	@Override
	public String toString() {
		return name;
	}	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (isAnalog ? 1231 : 1237);
		result = prime * result + pin;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Hardware)) {
			return false;
		}
		Hardware other = (Hardware) obj;
		if (isAnalog != other.isAnalog()) {
			return false;
		}
		if (pin != other.getPin()) {
			return false;
		}
		return true;
	}

}