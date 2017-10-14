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
import {{systemName|lower}}.smarthome.model.devices.LightSensor;

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
public class AutomatedIluminationByLuminosity {% if data.feature.extends %}extends {{data.feature.extends}} implements AdaptableFeature {% else %} extends FeatureBase {% endif %}{

	private ArrayList<Led> ledsToAutomate;
	private LightSensor lightSensor;
	private static AutomatedIluminationByLuminosity automatedIluminationByLuminosity = null;

	private  AutomatedIluminationByLuminosity() {}

	public static AutomatedIluminationByLuminosity getInstance() {
		if(automatedIluminationByLuminosity == null){
			automatedIluminationByLuminosity = new AutomatedIluminationByLuminosity();
			automatedIluminationByLuminosity.setName("Automated Ilumination By Luminosity");
			automatedIluminationByLuminosity.setLedsToAutomate(new ArrayList<Led>());
			automatedIluminationByLuminosity.setActive(false);
		}
		return automatedIluminationByLuminosity;
	}

	public static void distroy() {
		automatedIluminationByLuminosity = null;
	}

	@Override
	public void proceedActions(){
		if(isActive()){
			if(ledsToAutomate!= null && lightSensor!=null){
				for (Led led : ledsToAutomate) {
					lightSensor.act(led);
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

	public LightSensor getLightSensor() {
		return lightSensor;
	}

	public void setLightSensor(LightSensor lightSensor) {
		this.lightSensor = lightSensor;
	}

}
