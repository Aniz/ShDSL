package rise.smarthome.features;

import java.util.ArrayList;

import rise.smarthome.featureModeling.AdaptableFeature;
import rise.smarthome.featureModeling.AlternativeFeature;
import rise.smarthome.model.devices.Led;
import rise.smarthome.model.devices.PresenceSensor;

@AlternativeFeature(alternatives={
	AutomatedIluminationByLuminosity.class})
public class AutomatedIluminationByPresence extends UserIlumination implements AdaptableFeature {
    private ArrayList<Led> automatediluminationbypresenceToAutomate;
    private PresenceSensor presencesensor;
    private static AutomatedIluminationByPresence automatediluminationbypresence = null;
	
	private AutomatedIluminationByPresence(){}
	
	public static AutomatedIluminationByPresence getInstance() {
		if(automatediluminationbypresence == null){
			automatediluminationbypresence = new AutomatedIluminationByPresence();
			automatediluminationbypresence.setName("Automated Ilumination By Presence");
            automatediluminationbypresence.setAutomatedIluminationByPresence(new ArrayList<Led>());
			automatediluminationbypresence.setActive(false);
		}
		return automatediluminationbypresence;
	}
	
	public static void distroy() {
		automatediluminationbypresence = null;
	}

	@Override
	public void proceedActions() {
        if(isActive()){
			if(automatediluminationbypresenceToAutomate!= null && presencesensor!=null){
				for (Led actuador : automatediluminationbypresenceToAutomate) {
					presencesensor.act(actuador);
				}
			}
		}
	}

	
    public ArrayList<Led> getAutomatedIluminationByPresence() {
		return automatediluminationbypresenceToAutomate;
	}

	public void setAutomatedIluminationByPresence(ArrayList<Led> automatediluminationbypresenceToAutomate) {
		this.automatediluminationbypresenceToAutomate = automatediluminationbypresenceToAutomate;
	}
	
	public PresenceSensor getPresenceSensor() {
		return presencesensor;
	}

	public void setPresenceSensor(PresenceSensor presencesensor) {
		this.presencesensor = presencesensor;
	}

}