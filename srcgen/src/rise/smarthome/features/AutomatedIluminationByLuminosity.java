package rise.smarthome.features;

import java.util.ArrayList;

import rise.smarthome.featureModeling.AdaptableFeature;
import rise.smarthome.featureModeling.ORFeature;
import rise.smarthome.model.devices.Led;
import rise.smarthome.model.devices.LightSensor;

public class AutomatedIluminationByLuminosity extends UserIlumination implements AdaptableFeature {
    private ArrayList<Led> automatediluminationbyluminosityToAutomate;
    private LightSensor lightsensor;
    private static AutomatedIluminationByLuminosity automatediluminationbyluminosity = null;
	
	private AutomatedIluminationByLuminosity(){}
	
	public static AutomatedIluminationByLuminosity getInstance() {
		if(automatediluminationbyluminosity == null){
			automatediluminationbyluminosity = new AutomatedIluminationByLuminosity();
			automatediluminationbyluminosity.setName("Automated Ilumination By Luminosity");
            automatediluminationbyluminosity.setLedsToAutomate(new ArrayList<Led>());
			automatediluminationbyluminosity.setActive(false);
		}
		return automatediluminationbyluminosity;
	}
	
	public static void distroy() {
		automatediluminationbyluminosity = null;
	}

	@Override
	public void proceedActions() {
        if(isActive()){
			if(automatediluminationbyluminosityToAutomate!= null && lightsensor!=null){
				for (Led actuator : automatediluminationbyluminosityToAutomate) {
					lightsensor.act(actuator);
				}
			}
		}
	}

	
    public ArrayList<Led> getLedsToAutomate() {
		return automatediluminationbyluminosityToAutomate;
	}

	public void setLedsToAutomate(ArrayList<Led> automatediluminationbyluminosityToAutomate) {
		this.automatediluminationbyluminosityToAutomate = automatediluminationbyluminosityToAutomate;
	}
	
	public LightSensor getLightSensor() {
		return lightsensor;
	}

	public void setLightSensor(LightSensor lightsensor) {
		this.lightsensor = lightsensor;
	}

}