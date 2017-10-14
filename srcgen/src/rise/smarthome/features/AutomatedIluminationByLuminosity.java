package rise.smarthome.features;

import java.util.ArrayList;

import rise.smarthome.featureModeling.FeatureBase;
import rise.smarthome.featureModeling.AlternativeFeature;
import rise.smarthome.model.devices.;
import rise.smarthome.model.devices.LightSensor;

@AlternativeFeature(alternatives={
	AutomatedIluminationByPresence.class})
public class AutomatedIluminationByLuminosity  extends FeatureBase {
    
    private ArrayList<Led> automatediluminationbyluminosityToAutomate;
    private LightSensor lightsensor;
    private static AutomatedIluminationByLuminosity automatediluminationbyluminosity = null;
	
	private AutomatedIluminationByLuminosity(){}
	
	public static AutomatedIluminationByLuminosity getInstance() {
		if(automatediluminationbyluminosity == null){
			automatediluminationbyluminosity = new AutomatedIluminationByLuminosity();
			automatediluminationbyluminosity.setName("Automated Ilumination By Luminosity");
            automatediluminationbyluminosity.setAutomatedIluminationByLuminosity(new ArrayList<Led>());
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
				for (Led actuador : automatediluminationbyluminosityToAutomate) {
					lightsensor.act(actuador);
				}
			}
		}
	}

	
    public ArrayList<Led> getAutomatedIluminationByLuminosity() {
		return automatediluminationbyluminosityToAutomate;
	}

	public void setAutomatedIluminationByLuminosity(ArrayList<Led> automatediluminationbyluminosityToAutomate) {
		this.automatediluminationbyluminosityToAutomate = automatediluminationbyluminosityToAutomate;
	}
	
	public LightSensor getLightSensor() {
		return lightsensor;
	}

	public void setLightSensor(LightSensor lightsensor) {
		this.lightsensor = lightsensor;
	}

}