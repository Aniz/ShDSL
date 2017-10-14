package rise.smarthome.features;

import java.util.ArrayList;

import rise.smarthome.featureModeling.FeatureBase;
import rise.smarthome.featureModeling.OptionalFeature;
import rise.smarthome.model.devices.AutomaticDoor;

@OptionalFeature
public class LockDoors  extends FeatureBase  {

	private ArrayList<AutomaticDoor> s;

	private static LockDoors lockDoors = null;
	
	protected LockDoors(){}
	
	public static LockDoors getInstance(ArrayList<AutomaticDoor> s) {
		if(lockDoors == null){
			lockDoors = new LockDoors();
			lockDoors.setName("Lock Doors");
            lockDoors.sets(s);
		}
		return lockDoors;
	}
	
	public static void distroy() {
		lockDoors = null;
	}

	@Override
	public void proceedActions(String[] args) {
		// [0] - 0 Lock all doors; 1 Unlock all doors
		for (AutomaticDoor actuator : s) {
			if(args[0].equals("0"))
				actuator.deactivate();
			else if(args[0].equals("1"))
				actuator.activate();
		}
	}
        public ArrayList<AutomaticDoor> gets() {
		return s;
	}


	public void sets(ArrayList<AutomaticDoor> s) {
		this.s = s;
	}

}