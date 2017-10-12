package rise.smarthome.features;

import java.util.ArrayList;

import rise.smarthome.featureModeling.AdaptableFeature;
import rise.smarthome.model.devices.Led;
import rise.smarthome.model.devices.PresenceSensor;

public class AutomatedIluminationByPresence  extends UserIlumination implements AdaptableFeature{
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
