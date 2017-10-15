package {{systemName|lower}}.smarthome.features;

import java.util.ArrayList;

{% if data.feature.extend %}
import {{systemName|lower}}.smarthome.featureModeling.AdaptableFeature;
{% else %}
import {{systemName|lower}}.smarthome.featureModeling.FeatureBase;
{% endif %}
{% if data.feature.type %}
import {{systemName|lower}}.smarthome.featureModeling.{{data.feature.type}}Feature;
{% endif %}
import {{systemName|lower}}.smarthome.model.devices.{{data.feature.actuador.name}};
import {{systemName|lower}}.smarthome.model.devices.{{data.feature.sensor.name}};

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
public class {{data.feature.name}} {% if data.feature.extend %}extends {{data.feature.extend.name}} implements AdaptableFeature {% else %} extends FeatureBase {% endif %}{
    private ArrayList<{{data.feature.actuador.name}}> {{data.feature.name|lower}}ToAutomate;
    private {{data.feature.sensor.name}} {{data.feature.sensor.name|lower}};
    private static {{data.feature.name}} {{data.feature.name|lower}} = null;
	
	private {{data.feature.name}}(){}
	
	public static {{data.feature.name}} getInstance() {
		if({{data.feature.name|lower}} == null){
			{{data.feature.name|lower}} = new {{data.feature.name}}();
			{{data.feature.name|lower}}.setName("{{data.feature.name|splitName}}");
            {{data.feature.name|lower}}.set{{data.feature.name}}(new ArrayList<{{data.feature.actuador.name}}>());
			{{data.feature.name|lower}}.setActive(false);
		}
		return {{data.feature.name|lower}};
	}
	
	public static void distroy() {
		{{data.feature.name|lower}} = null;
	}

	@Override
	public void proceedActions() {
        if(isActive()){
			if({{data.feature.name|lower}}ToAutomate!= null && {{data.feature.sensor.name|lower}}!=null){
				for ({{data.feature.actuador.name}} actuador : {{data.feature.name|lower}}ToAutomate) {
					{{data.feature.sensor.name|lower}}.act(actuador);
				}
			}
		}
	}

	
    public ArrayList<{{data.feature.actuador.name}}> get{{data.feature.actuador.name}}ToAutomate() {
		return {{data.feature.name|lower}}ToAutomate;
	}

	public void set{{data.feature.actuador.name}}ToAutomate(ArrayList<{{data.feature.actuador.name}}> {{data.feature.name|lower}}ToAutomate) {
		this.{{data.feature.name|lower}}ToAutomate = {{data.feature.name|lower}}ToAutomate;
	}
	
	public {{data.feature.sensor.name}} get{{data.feature.sensor.name}}() {
		return {{data.feature.sensor.name|lower}};
	}

	public void set{{data.feature.sensor.name}}({{data.feature.sensor.name}} {{data.feature.sensor.name|lower}}) {
		this.{{data.feature.sensor.name|lower}} = {{data.feature.sensor.name|lower}};
	}

}
