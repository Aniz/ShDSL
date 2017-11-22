package {{systemName|lower}}.smarthome.features;

import java.util.ArrayList;
{% if data.feature.extends %}
import {{systemName|lower}}.smarthome.featureModeling.AdaptableFeature;
{% else %}
import {{systemName|lower}}.smarthome.featureModeling.FeatureBase;
{% endif %}
{% if data.feature.type %}
import {{systemName|lower}}.smarthome.featureModeling.{{data.feature.type}}Feature;
{% endif %}
import {{systemName|lower}}.smarthome.model.devices.{{data.feature.actuator.name}};
import {{systemName|lower}}.smarthome.model.devices.TemperatureSensor;

{% if data.feature.type == "Mandatory" %}
@MandatoryFeature
{% endif %}
{% if data.feature.type == "Optional" %}
@OptionalFeature
{% endif %}
{% if data.feature.type == "Alternative" %}
@AlternativeFeature(alternatives={
{% for altenative in data.feature.alternatives %}
	{{altenative.name}}.class{% if not loop.last %},{% endif %}
{% endfor %}
})
{% endif %}
public class AutomatedAirConditionerControl {% if data.feature.extends %}extends {{data.feature.extends}} implements AdaptableFeature {% else %} extends FeatureBase {% endif %}{
    
    private ArrayList<AirConditioner> airConditionersToAutomate;
    private TemperatureSensor temperatureSensor;
    private static AutomatedAirConditionerControl automatedAirConditionerControl = null;
	
	private AutomatedAirConditionerControl(){}
	
	public static AutomatedAirConditionerControl getInstance() {
		if(automatedAirConditionerControl == null){
			automatedAirConditionerControl = new AutomatedAirConditionerControl();
			automatedAirConditionerControl.setName("Automated Air Conditioner Control");
            automatedAirConditionerControl.setAirConditionersToAutomate(new ArrayList<AirConditioner>());
			automatedAirConditionerControl.setActive(false);
		}
		return automatedAirConditionerControl;
	}
	
	public static void distroy() {
		automatedAirConditionerControl = null;
	}
	@Override
	public void proceedActions() {
		
                        	if(isActive()){
			if(airConditionersToAutomate!= null && temperatureSensor!=null){
				for (AirConditioner airConditioner : airConditionersToAutomate) {
					temperatureSensor.act(airConditioner);
				}
			}
		}
	}

	
        public ArrayList<AirConditioner> getAirConditionersToAutomate() {
		return airConditionersToAutomate;
	}

	public void setAirConditionersToAutomate(ArrayList<AirConditioner> airConditionersToAutomate) {
		this.airConditionersToAutomate = airConditionersToAutomate;
	}
             	public TemperatureSensor getTemperatureSensor() {
		return temperatureSensor;
	}

	public void setTemperatureSensor(TemperatureSensor temperatureSensor) {
		this.temperatureSensor = temperatureSensor;
	}

}
