package rise.smarthome.enums;

public enum SensorEnum {
		TEMPERATURE_SENSOR("bu"),
		LIGHT_SENSOR("Light Sensor"),
		PRESENCE_SENSOR("Presence Sensor");
	
	private String literalName;
	
	SensorEnum (String literalName){
		this.literalName = literalName;
	}
	
	public String toString(){
		return literalName;
	}

	public void setLiteralName(String literalName) {
		this.literalName = literalName;
	}
}