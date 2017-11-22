package rise.smarthome.features;

import java.util.ArrayList;

import rise.smarthome.featureModeling.AdaptableFeature;
import rise.smarthome.featureModeling.AlternativeFeature;
import rise.smarthome.model.devices.AirConditioner;
import rise.smarthome.model.devices.TemperatureSensor;

@AlternativeFeature(alternatives={
	UserAirConditionerControl.class})
public class AutomatedAirConditionerControl extends UserAirConditionerControl implements AdaptableFeature {
    private ArrayList<AirConditioner> automatedairconditionercontrolToAutomate;
    private TemperatureSensor temperaturesensor;
    private static AutomatedAirConditionerControl automatedairconditionercontrol = null;
	
	private AutomatedAirConditionerControl(){}
	
	public static AutomatedAirConditionerControl getInstance() {
		if(automatedairconditionercontrol == null){
			automatedairconditionercontrol = new AutomatedAirConditionerControl();
			automatedairconditionercontrol.setName("Automated Air Conditioner Control");
            automatedairconditionercontrol.setAirConditionersToAutomate(new ArrayList<AirConditioner>());
			automatedairconditionercontrol.setActive(false);
		}
		return automatedairconditionercontrol;
	}
	
	public static void distroy() {
		automatedairconditionercontrol = null;
	}

	@Override
	public void proceedActions() {
        if(isActive()){
			if(automatedairconditionercontrolToAutomate!= null && temperaturesensor!=null){
				for (AirConditioner actuator : automatedairconditionercontrolToAutomate) {
					temperaturesensor.act(actuator);
				}
			}
		}
	}

	
    public ArrayList<AirConditioner> getAirConditionersToAutomate() {
		return automatedairconditionercontrolToAutomate;
	}

	public void setAirConditionersToAutomate(ArrayList<AirConditioner> automatedairconditionercontrolToAutomate) {
		this.automatedairconditionercontrolToAutomate = automatedairconditionercontrolToAutomate;
	}
	
	public TemperatureSensor getTemperatureSensor() {
		return temperaturesensor;
	}

	public void setTemperatureSensor(TemperatureSensor temperaturesensor) {
		this.temperaturesensor = temperaturesensor;
	}

}