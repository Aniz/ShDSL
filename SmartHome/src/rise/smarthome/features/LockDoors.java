package rise.smarthome.features;

import java.util.ArrayList;

import rise.smarthome.featureModeling.FeatureBase;
import rise.smarthome.featureModeling.OptionalFeature;
import rise.smarthome.model.devices.AutomaticDoor;

@OptionalFeature
public class LockDoors extends FeatureBase {

	private ArrayList<AutomaticDoor> automaticDoors;

	private static LockDoors lockDoors = null;
	
	protected LockDoors(){}
	
	public static LockDoors getInstance(ArrayList<AutomaticDoor> automaticDoors) {
		if(lockDoors == null){
			lockDoors = new LockDoors();
			lockDoors.setName("Lock Doors");
                        lockDoors.setautomaticDoors(automaticDoors);
		}
		return lockDoors;
	}
	
	public static void distroy() {
		lockDoors = null;
	}

	@Override
	public void proceedActions(String[] args) {
		// [0] - 0 Lock all doors; 1 Unlock all doors
		for (AutomaticDoor automaticDoor : automaticDoors) {
			if(args[0].equals("0"))
				automaticDoor.deactivate();
			else if(args[0].equals("1"))
				automaticDoor.activate();
		}
	}
        public ArrayList<AutomaticDoor> getautomaticDoors() {
		return automaticDoors;
	}


	public void setautomaticDoors(ArrayList<AutomaticDoor> automaticDoors) {
		this.automaticDoors = automaticDoors;
	}

}
