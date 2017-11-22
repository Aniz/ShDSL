package rise.smarthome.features;

import java.util.ArrayList;

import rise.smarthome.featureModeling.FeatureBase;
import rise.smarthome.featureModeling.OptionalFeature;
import rise.smarthome.model.devices.AirConditioner;

@OptionalFeature
public class UserAirConditionerControl  extends FeatureBase  {

   
	private ArrayList<AirConditioner> airconditioners;
	
	private static UserAirConditionerControl userAirConditionerControl = null;
	
	protected UserAirConditionerControl(){}
	
	public static UserAirConditionerControl getInstance(ArrayList<AirConditioner> airconditioners){
		if(userAirConditionerControl == null){
			userAirConditionerControl = new UserAirConditionerControl();
			userAirConditionerControl.setName("User Air Conditioner Control");
            userAirConditionerControl.setAirConditioners(airconditioners);
		}
		return userAirConditionerControl;
	}
	
	public static void distroy() {
		userAirConditionerControl = null;
	}


	@Override
	public void proceedActions(String[] args) {
		// [0] Led pin
		// [1] action: 1 on; 0 off;
		for (AirConditioner actuator : airconditioners) {
			if(actuator.getPin()==Integer.parseInt(args[0])){
				if(Integer.parseInt(args[1]) == 1){
					actuator.activate();
				}else if (Integer.parseInt(args[1]) == 0 ){
					actuator.deactivate();
				}
			}
		}
	}

	public ArrayList<AirConditioner> getAirConditioners() {
		return airconditioners;
	}

	public void setAirConditioners(ArrayList<AirConditioner> airconditioners) {
		this.airconditioners = airconditioners;
	}

}