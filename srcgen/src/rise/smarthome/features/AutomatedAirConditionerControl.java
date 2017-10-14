package rise.smarthome.features;

import java.util.ArrayList;

import rise.smarthome.featureModeling.FeatureBase;
import rise.smarthome.featureModeling.AlternativeFeature;
import rise.smarthome.model.devices.;
import rise.smarthome.model.devices.LightSensor;

@AlternativeFeature(alternatives={
	UserAirConditionerControl.class})
public class AutomatedAirConditionerControl  extends FeatureBase {
    
    private ArrayList<AirConditioner> automatedairconditionercontrolToAutomate;
    private TemperatureSensor temperaturesensor;
    private static AutomatedAirConditionerControl automatedairconditionercontrol = null;
	
	private AutomatedAirConditionerControl(){}
	
	public static AutomatedAirConditionerControl getInstance() {
		if(automatedairconditionercontrol == null){
			automatedairconditionercontrol = new AutomatedAirConditionerControl();
			automatedairconditionercontrol.setName("Automated Air Conditioner Control");
            automatedairconditionercontrol.setAutomatedAirConditionerControl(new ArrayList<AirConditioner>());
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
				for (AirConditioner actuador : automatedairconditionercontrolToAutomate) {
					temperaturesensor.act(actuador);
				}
			}
		}
	}

	
    public ArrayList<AirConditioner> getAutomatedAirConditionerControl() {
		return automatedairconditionercontrolToAutomate;
	}

	public void setAutomatedAirConditionerControl(ArrayList<AirConditioner> automatedairconditionercontrolToAutomate) {
		this.automatedairconditionercontrolToAutomate = automatedairconditionercontrolToAutomate;
	}
	
	public TemperatureSensor getTemperatureSensor() {
		return temperaturesensor;
	}

	public void setTemperatureSensor(TemperatureSensor temperaturesensor) {
		this.temperaturesensor = temperaturesensor;
	}

}