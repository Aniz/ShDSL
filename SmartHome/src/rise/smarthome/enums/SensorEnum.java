package {{systemName|lower}}.smarthome.enums;

public enum SensorEnum {
	{% for device in data.device %}
	{% if device.typeDevice == 'Sensor' %}
		{{device.entity|splitName}}
	{% endif %}
	{% endfor %}
	LIGHT_SENSOR("Light Sensor"),
	PRESENCE_SENSOR("Presence Sensor"),
	TEMPERATURE_SENSOR("Temperature Sensor");
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
