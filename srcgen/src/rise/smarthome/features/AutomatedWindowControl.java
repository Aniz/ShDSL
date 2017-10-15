package rise.smarthome.features;

import java.util.ArrayList;

import rise.smarthome.featureModeling.AdaptableFeature;
import rise.smarthome.featureModeling.AlternativeFeature;
import rise.smarthome.model.devices.AutomaticWindow;
import rise.smarthome.model.devices.TemperatureSensor;

@AlternativeFeature(alternatives={
	UserAirConditionerControl.class})
public class AutomatedWindowControl extends UserWindowControl implements AdaptableFeature {
    private ArrayList<AutomaticWindow> automatedwindowcontrolToAutomate;
    private TemperatureSensor temperaturesensor;
    private static AutomatedWindowControl automatedwindowcontrol = null;
	
	private AutomatedWindowControl(){}
	
	public static AutomatedWindowControl getInstance() {
		if(automatedwindowcontrol == null){
			automatedwindowcontrol = new AutomatedWindowControl();
			automatedwindowcontrol.setName("Automated Window Control");
            automatedwindowcontrol.setAutomaticWindowsToAutomate(new ArrayList<AutomaticWindow>());
			automatedwindowcontrol.setActive(false);
		}
		return automatedwindowcontrol;
	}
	
	public static void distroy() {
		automatedwindowcontrol = null;
	}

	@Override
	public void proceedActions() {
        if(isActive()){
			if(automatedwindowcontrolToAutomate!= null && temperaturesensor!=null){
				for (AutomaticWindow actuador : automatedwindowcontrolToAutomate) {
					temperaturesensor.act(actuador);
				}
			}
		}
	}

	
    public ArrayList<AutomaticWindow> getAutomaticWindowsToAutomate() {
		return automatedwindowcontrolToAutomate;
	}

	public void setAutomaticWindowsToAutomate(ArrayList<AutomaticWindow> automatedwindowcontrolToAutomate) {
		this.automatedwindowcontrolToAutomate = automatedwindowcontrolToAutomate;
	}
	
	public TemperatureSensor getTemperatureSensor() {
		return temperaturesensor;
	}

	public void setTemperatureSensor(TemperatureSensor temperaturesensor) {
		this.temperaturesensor = temperaturesensor;
	}

}