package rise.smarthome.features;

import java.util.ArrayList;
import rise.smarthome.featureModeling.AdaptableFeature;
import rise.smarthome.featureModeling.AlternativeFeature;
import rise.smarthome.model.devices.AirConditioner;
import rise.smarthome.model.devices.TemperatureSensor;

@AlternativeFeature(alternatives={AutomatedWindowControl.class})
public class AutomatedAirConditionerControl extends UserAirConditionerControl implements AdaptableFeature{
    
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
