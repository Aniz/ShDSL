package rise.smarthome.features;

import java.util.ArrayList;

import rise.smarthome.featureModeling.FeatureBase;
import rise.smarthome.featureModeling.MandatoryFeature;
import rise.smarthome.model.devices.Led;

@MandatoryFeature
public class UserIlumination  extends FeatureBase {

	private ArrayList<Led> leds;
	private static UserIlumination userIlumination = null;

	protected UserIlumination(){}

	public static UserIlumination getInstance(ArrayList<Led> leds) {
		if(userIlumination == null){
			userIlumination = new UserIlumination();
			userIlumination.setName("User Illumination");
			userIlumination.setLeds(leds);

		}
		return userIlumination;
	}

	public static void distroy() {
		userIlumination = null;
	}

	@Override
	public synchronized void proceedActions(String[] args) {
		// [0] Led pin
		// [1] action: 1 on; 0 off; -1 switch state
		if(leds!=null){
			for (Led actuator : leds) {
				if(actuator.getPin()==Integer.parseInt(args[0])){
					if(Integer.parseInt(args[1]) == 1){
						actuator.activate();
					}else if (Integer.parseInt(args[1]) == 0 ){
						actuator.deactivate();
					}else if (Integer.parseInt(args[1]) == -1 ){
						if(actuator.getState()==0) actuator.activate(); else actuator.deactivate();
					}
				}
			}
		}
	}


	public ArrayList<Led> getLeds() {
		return leds;
	}


	public void setLeds(ArrayList<Led> leds) {
		this.leds = leds;
	}
}