package rise.smarthome.features;

import java.util.ArrayList;

import rise.smarthome.featureModeling.AdaptableFeature;
import rise.smarthome.model.devices.Led;
import rise.smarthome.model.devices.LightSensor;

public class AutomatedIluminationByLuminosity extends UserIlumination implements AdaptableFeature {

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
