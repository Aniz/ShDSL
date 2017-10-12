
package rise.smarthome.features;

import java.util.ArrayList;
import rise.smarthome.featureModeling.AdaptableFeature;
import rise.smarthome.featureModeling.AlternativeFeature;
import rise.smarthome.model.devices.AutomaticWindow;
import rise.smarthome.model.devices.TemperatureSensor;

@AlternativeFeature(alternatives={AutomatedAirConditionerControl.class})
public class AutomatedWindowControl extends UserAirConditionerControl implements AdaptableFeature{
    
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
