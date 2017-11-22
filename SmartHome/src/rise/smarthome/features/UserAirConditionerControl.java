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
public class UserAirConditionerControl {% if data.feature.extends %}extends {{data.feature.extends}} implements AdaptableFeature {% else %} extends FeatureBase {% endif %} {

   
	private ArrayList<{{data.feature.actuator.name}}> {{data.feature.actuator.name|lower}}s;
	
	private static UserAirConditionerControl userAirConditionerControl = null;
	
	protected UserAirConditionerControl(){}
	
	public static UserAirConditionerControl getInstance(ArrayList<{{data.feature.actuator.name}}> {{data.feature.actuator.name|lower}}s){
		if(userAirConditionerControl == null){
			userAirConditionerControl = new UserAirConditionerControl();
			userAirConditionerControl.setName("User Air Conditioner Control");
            userAirConditionerControl.set{{data.feature.actuator.name}}s({{data.feature.actuator.name|lower}}s);
		}
		return userAirConditionerControl;
	}
	
	public static void distroy() {
		userAirConditionerControl = null;
	}


	@Override
	public void proceedActions(String[] args) {
		// [0] Led pin
		// [1] action: 1 on; 0 off;
		for ({{data.feature.actuator.name}} actuator : {{data.feature.actuator.name|lower}}s) {
			if(actuator.getPin()==Integer.parseInt(args[0])){
				if(Integer.parseInt(args[1]) == 1){
					actuator.activate();
				}else if (Integer.parseInt(args[1]) == 0 ){
					actuator.deactivate();
				}
			}
		}
	}

	public ArrayList<{{data.feature.actuator.name}}> get{{data.feature.actuator.name}}s() {
		return {{data.feature.actuator.name|lower}}s;
	}

	public void set{{data.feature.actuator.name}}s(ArrayList<{{data.feature.actuator.name}}> {{data.feature.actuator.name|lower}}s) {
		this.{{data.feature.actuator.name|lower}}s = {{data.feature.actuator.name|lower}}s;
	}

}
