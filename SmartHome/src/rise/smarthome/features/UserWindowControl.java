package rise.smarthome.features;

import java.util.ArrayList;

import rise.smarthome.featureModeling.FeatureBase;
import rise.smarthome.featureModeling.OptionalFeature;
import rise.smarthome.model.devices.AutomaticWindow;

@OptionalFeature
public class UserWindowControl extends FeatureBase {

	private ArrayList<AutomaticWindow> automaticWindows;
	private static UserWindowControl userWindowControl = null;
	
	protected UserWindowControl(){}
	
	public static UserWindowControl getInstance(ArrayList<AutomaticWindow> automaticWindows) {
		if(userWindowControl == null){
			userWindowControl = new UserWindowControl();
			userWindowControl.setName("User Window Control");
            userWindowControl.setAutomaticWindows(automaticWindows);
		}
		return userWindowControl;
	}
	
	public static void distroy() {
		userWindowControl = null;
	}

	@Override
	public void proceedActions(String[] args) {
		// [0] Led pin
		// [1] action: 1 on; 0 off;
		for (AutomaticWindow automaticWindow : automaticWindows) {
			if(automaticWindow.getPin()==Integer.parseInt(args[0])){
				if(Integer.parseInt(args[1]) == 1){
					automaticWindow.activate();
				}else if (Integer.parseInt(args[1]) == 0 ){
					automaticWindow.deactivate();
				}
			}
		}
	}

	public ArrayList<AutomaticWindow> getAutomaticWindows() {
		return automaticWindows;
	}

	public void setAutomaticWindows(ArrayList<AutomaticWindow> automaticWindows) {
		this.automaticWindows = automaticWindows;
	}

}
