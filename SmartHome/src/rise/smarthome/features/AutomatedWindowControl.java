
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
public class AutomatedWindowControl {% if data.feature.extends %}extends {{data.feature.extends}} implements AdaptableFeature {% endif %}{

    private ArrayList<AutomaticWindow> automaticWindowsToAutomate;
    private TemperatureSensor temperatureSensor;
    private static AutomatedWindowControl automatedWindowControl = null;
	
	private AutomatedWindowControl(){}
	
	public static AutomatedWindowControl getInstance() {
		if(automatedWindowControl == null){
			automatedWindowControl = new AutomatedWindowControl();
			automatedWindowControl.setName("Automated Window Control");
                        automatedWindowControl.setAutomaticWindowsToAutomate(new ArrayList<AutomaticWindow>());
			automatedWindowControl.setActive(false);
		}
		return automatedWindowControl;
	}
	
	public static void distroy() {
		automatedWindowControl = null;
	}
	@Override
	public void proceedActions() {
		
                        	if(isActive()){
			if(automaticWindowsToAutomate!= null && temperatureSensor!=null){
				for (AutomaticWindow automaticWindow : automaticWindowsToAutomate) {
					temperatureSensor.act(automaticWindow);
				}
			}
		}
	}

	
        public ArrayList<AutomaticWindow> getAutomaticWindowsToAutomate() {
		return automaticWindowsToAutomate;
	}

	public void setAutomaticWindowsToAutomate(ArrayList<AutomaticWindow> automaticWindowsToAutomate) {
		this.automaticWindowsToAutomate = automaticWindowsToAutomate;
	}
             	public TemperatureSensor getTemperatureSensor() {
		return temperatureSensor;
	}

	public void setTemperatureSensor(TemperatureSensor temperatureSensor) {
		this.temperatureSensor = temperatureSensor;
	}

}
