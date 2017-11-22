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
public class LockDoors {% if data.feature.extends %}extends {{data.feature.extends}} implements AdaptableFeature {% else %} extends FeatureBase {% endif %} {

	private ArrayList<{{data.feature.actuator.name}}> {{data.feature.actuator.name}}s;

	private static LockDoors lockDoors = null;
	
	protected LockDoors(){}
	
	public static LockDoors getInstance(ArrayList<{{data.feature.actuator.name}}> {{data.feature.actuator.name|lower}}s) {
		if(lockDoors == null){
			lockDoors = new LockDoors();
			lockDoors.setName("{{data.feature.name|splitName}}");
            lockDoors.set{{data.feature.actuator.name}}s({{data.feature.actuator.name|lower}}s);
		}
		return lockDoors;
	}
	
	public static void distroy() {
		lockDoors = null;
	}

	@Override
	public void proceedActions(String[] args) {
		// [0] - 0 Lock all doors; 1 Unlock all doors
		for ({{data.feature.actuator.name}} actuator : {{data.feature.actuator.name}}s) {
			if(args[0].equals("0"))
				actuator.deactivate();
			else if(args[0].equals("1"))
				actuator.activate();
		}
	}
        public ArrayList<{{data.feature.actuator.name}}> get{{data.feature.actuator.name}}s() {
		return {{data.feature.actuator.name}}s;
	}


	public void set{{data.feature.actuator.name}}s(ArrayList<{{data.feature.actuator.name}}> {{data.feature.actuator.name}}s) {
		this.{{data.feature.actuator.name}}s = {{data.feature.actuator.name}}s;
	}

}
