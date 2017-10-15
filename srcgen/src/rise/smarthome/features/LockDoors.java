package rise.smarthome.features;

import java.util.ArrayList;

import rise.smarthome.featureModeling.FeatureBase;
import rise.smarthome.featureModeling.OptionalFeature;
import rise.smarthome.model.devices.AutomaticDoor;

@OptionalFeature
public class LockDoors  extends FeatureBase  {

	private ArrayList<AutomaticDoor> AutomaticDoors;

	private static LockDoors lockDoors = null;
	
	protected LockDoors(){}
	
	public static LockDoors getInstance(ArrayList<AutomaticDoor> automaticdoors) {
		if(lockDoors == null){
			lockDoors = new LockDoors();
			lockDoors.setName("Lock Doors");
            lockDoors.setAutomaticDoors(automaticdoors);
		}
		return lockDoors;
	}
	
	public static void distroy() {
		lockDoors = null;
	}

	@Override
	public void proceedActions(String[] args) {
		// [0] - 0 Lock all doors; 1 Unlock all doors
		for (AutomaticDoor actuador : AutomaticDoors) {
			if(args[0].equals("0"))
				actuador.deactivate();
			else if(args[0].equals("1"))
				actuador.activate();
		}
	}
        public ArrayList<AutomaticDoor> getAutomaticDoors() {
		return AutomaticDoors;
	}


	public void setAutomaticDoors(ArrayList<AutomaticDoor> AutomaticDoors) {
		this.AutomaticDoors = AutomaticDoors;
	}

}