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
import {{systemName|lower}}.smarthome.model.devices.PresenceSensor;

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
public class AutomatedIluminationByPresence  {% if data.feature.extends %}extends {{data.feature.extends}} implements AdaptableFeature {% else %} extends FeatureBase {% endif %}{
	private ArrayList<Led> ledsToAutomate;
	private PresenceSensor presenceSensor;
	private static AutomatedIluminationByPresence automatedIluminationByPresence = null;

	private AutomatedIluminationByPresence(){}
	
	public static AutomatedIluminationByPresence getInstance() {
		if(automatedIluminationByPresence == null){
			automatedIluminationByPresence = new AutomatedIluminationByPresence();
			automatedIluminationByPresence.setName("Automated Ilumination By Presence");
			automatedIluminationByPresence.setLedsToAutomate(new ArrayList<Led>());
			automatedIluminationByPresence.setActive(false);
		}
		return automatedIluminationByPresence;
	}


	public static void distroy() {
		automatedIluminationByPresence = null;
	}

	@Override
	public void proceedActions(){
		if(isActive()){
			if(ledsToAutomate!= null && presenceSensor!=null){
				for (Led led : ledsToAutomate) {
					presenceSensor.act(led);
				}
			}
		}
	}

	public ArrayList<Led> getLedsToAutomate() {
		return ledsToAutomate;
	}

	public void setLedsToAutomate(ArrayList<Led> ledsToAutomate) {
		this.ledsToAutomate = ledsToAutomate;
	}

	public PresenceSensor getPresenceSensor() {
		return presenceSensor;
	}

	public void setPresenceSensor(PresenceSensor presenceSensor) {
		this.presenceSensor = presenceSensor;
	}
}
