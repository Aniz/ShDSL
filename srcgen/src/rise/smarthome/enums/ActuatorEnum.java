package rise.smarthome.enums;

public enum ActuatorEnum {
		LED("Led"),
		AIR_CONDITIONER("Air Conditioner"),
		ALARM("Alarm"),
		AUTOMATIC_DOOR("Automatic Door"),
		AUTOMATIC_WINDOW("Automatic Window");
	
	private String literalName;
	
	ActuatorEnum (String literalName){
		this.literalName = literalName;
	}
	
	public String toString(){
		return literalName;
	}

	public void setLiteralName(String literalName) {
		this.literalName = literalName;
	}
}