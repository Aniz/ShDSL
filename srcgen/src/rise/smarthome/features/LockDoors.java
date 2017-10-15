package rise.smarthome.features;

import java.util.ArrayList;

import rise.smarthome.featureModeling.FeatureBase;
import rise.smarthome.featureModeling.OptionalFeature;
import rise.smarthome.model.devices.AutomaticDoor;

@OptionalFeature
public class LockDoors  extends FeatureBase  {

	private ArrayList<AutomaticDoor> <textx:Device object at 0x10c59dd68>s;

	private static LockDoors lockDoors = null;
	
	protected LockDoors(){}
	
	public static LockDoors getInstance(ArrayList<AutomaticDoor> <textx:Device object at 0x10c59dd68>s) {
		if(lockDoors == null){
			lockDoors = new LockDoors();
			lockDoors.setName("Lock Doors");
            lockDoors.set<textx:Device object at 0x10c59dd68>s(<textx:Device object at 0x10c59dd68>s);
		}
		return lockDoors;
	}
	
	public static void distroy() {
		lockDoors = null;
	}

	@Override
	public void proceedActions(String[] args) {
		// [0] - 0 Lock all doors; 1 Unlock all doors
		for (AutomaticDoor actuador : <textx:Device object at 0x10c59dd68>s) {
			if(args[0].equals("0"))
				actuador.deactivate();
			else if(args[0].equals("1"))
				actuador.activate();
		}
	}
        public ArrayList<AutomaticDoor> get<textx:Device object at 0x10c59dd68>s() {
		return <textx:Device object at 0x10c59dd68>s;
	}


	public void set<textx:Device object at 0x10c59dd68>s(ArrayList<AutomaticDoor> <textx:Device object at 0x10c59dd68>s) {
		this.<textx:Device object at 0x10c59dd68>s = <textx:Device object at 0x10c59dd68>s;
	}

}