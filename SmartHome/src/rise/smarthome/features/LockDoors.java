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
import {{systemName|lower}}.smarthome.model.devices.{{data.feature.actuador.name}};

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

	private ArrayList<{{data.feature.actuador.name}}> {{data.feature.actuador.name}}s;

	private static LockDoors lockDoors = null;
	
	protected LockDoors(){}
	
	public static LockDoors getInstance(ArrayList<{{data.feature.actuador.name}}> {{data.feature.actuador.name|lower}}s) {
		if(lockDoors == null){
			lockDoors = new LockDoors();
			lockDoors.setName("{{data.feature.name|splitName}}");
            lockDoors.set{{data.feature.actuador.name}}s({{data.feature.actuador.name}}s);
		}
		return lockDoors;
	}
	
	public static void distroy() {
		lockDoors = null;
	}

	@Override
	public void proceedActions(String[] args) {
		// [0] - 0 Lock all doors; 1 Unlock all doors
		for ({{data.feature.actuador.name}} actuador : {{data.feature.actuador.name}}s) {
			if(args[0].equals("0"))
				actuador.deactivate();
			else if(args[0].equals("1"))
				actuador.activate();
		}
	}
        public ArrayList<{{data.feature.actuador.name}}> get{{data.feature.actuador.name}}s() {
		return {{data.feature.actuador.name}}s;
	}


	public void set{{data.feature.actuador.name}}s(ArrayList<{{data.feature.actuador.name}}> {{data.feature.actuador.name}}s) {
		this.{{data.feature.actuador.name}}s = {{data.feature.actuador.name}}s;
	}

}
