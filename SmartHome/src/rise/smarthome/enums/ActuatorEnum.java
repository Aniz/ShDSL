package rise.smarthome.enums;

public enum {{extraData}}Enum {
	{% for device in data.device %}
	{% if device.typeDevice == extraData %}
		{{device.entity|splitName}}
	{% endif %}
	{% endfor %}

	AIR_CONDITIONER("Air Conditioner"),
	ALARM("Alarm"),
	AUTOMATIC_WINDOW("Automatic Window"),
	AUTOMATIC_DOOR("Automatic Door"),
	LED("Led");
	
	private String literalName;
	
	{{extraData}}Enum (String literalName){
		this.literalName = literalName;
	}
	
	public String toString(){
		return literalName;
	}

	public void setLiteralName(String literalName) {
		this.literalName = literalName;
	}
}
