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
			for (Led actuador : leds) {
				if(actuador.getPin()==Integer.parseInt(args[0])){
					if(Integer.parseInt(args[1]) == 1){
						actuador.activate();
					}else if (Integer.parseInt(args[1]) == 0 ){
						actuador.deactivate();
					}else if (Integer.parseInt(args[1]) == -1 ){
						if(actuador.getState()==0) actuador.activate(); else actuador.deactivate();
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