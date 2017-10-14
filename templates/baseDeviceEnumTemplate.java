package {{systemName}}.smarthome.enums;

public enum {{extraData}}Enum {
	{% for key,value in data.items() %}
		{% if loop.last %}
		{{value.device.name|splitName|replace(" ","_")|upper}}({% if value.device.alias%}"{{value.device.alias}}"{% else %}"{{value.device.name|splitName}}"{% endif %});
		{% else %}
		{{value.device.name|splitName|replace(" ","_")|upper}}({% if value.device.alias%}"{{value.device.alias}}"{% else %}"{{value.device.name|splitName}}"{% endif %}),
		{% endif %}
	{% endfor %}
	
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
