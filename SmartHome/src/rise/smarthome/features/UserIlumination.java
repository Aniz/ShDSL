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
public class UserIlumination {% if data.feature.extends %}extends {{data.feature.extends}} implements AdaptableFeature {% else %} extends FeatureBase {% endif %}{

	private ArrayList<{{data.feature.actuator.name}}> {{data.feature.actuator.name|lower}}s;
	private static UserIlumination userIlumination = null;

	protected UserIlumination(){}

	public static UserIlumination getInstance(ArrayList<{{data.feature.actuator.name}}> {{data.feature.actuator.name|lower}}s) {
		if(userIlumination == null){
			userIlumination = new UserIlumination();
			userIlumination.setName("User Illumination");
			userIlumination.set{{data.feature.actuator.name}}s({{data.feature.actuator.name|lower}}s);

		}
		return userIlumination;
	}

	public static void distroy() {
		userIlumination = null;
	}

	@Override
	public synchronized void proceedActions(String[] args) {
		// [0] Led pin
		// [1] action: 1 on; 0 off; -1 switch state
		if({{data.feature.actuator.name|lower}}s!=null){
			for ({{data.feature.actuator.name}} actuator : {{data.feature.actuator.name|lower}}s) {
				if(actuator.getPin()==Integer.parseInt(args[0])){
					if(Integer.parseInt(args[1]) == 1){
						actuator.activate();
					}else if (Integer.parseInt(args[1]) == 0 ){
						actuator.deactivate();
					}else if (Integer.parseInt(args[1]) == -1 ){
						if(actuator.getState()==0) actuator.activate(); else actuator.deactivate();
					}
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
