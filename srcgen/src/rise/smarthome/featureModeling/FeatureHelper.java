package rise.smarthome.featureModeling;

import java.util.ArrayList;
import java.util.List;

import rise.smarthome.features.UserAirConditionerControl;
import rise.smarthome.features.AutomatedAirConditionerControl;
import rise.smarthome.features.PresenceIlusion;
import rise.smarthome.features.AutomatedIluminationByPresence;
import rise.smarthome.features.UserIlumination;
import rise.smarthome.features.AutomatedIluminationByLuminosity;
import rise.smarthome.features.UserWindowControl;
import rise.smarthome.features.AutomatedWindowControl;
import rise.smarthome.features.PanicMode;
import rise.smarthome.features.AlarmAgainstRobbery;
import rise.smarthome.features.LockDoors;

//#if defined(alarm_against_robery)
import rise.smarthome.features.AlarmAgainstRobbery;
//#endif
//#if defined(automated_air_conditioner_control)
import rise.smarthome.features.AutomatedAirConditionerControl;
//#endif
//#if defined(automated_ilumination_by_luminosity)
import rise.smarthome.features.AutomatedIluminationByLuminosity;
//#endif
//#if defined(automated_ilumination_by_presence)
import rise.smarthome.features.AutomatedIluminationByPresence;
//#endif
//#if defined(automated_window_control)
import rise.smarthome.features.AutomatedWindowControl;
//#endif
//#if defined(lock_doors)
import rise.smarthome.features.LockDoors;
//#endif

//#if defined(panic_mode)
import rise.smarthome.features.PanicMode;
//#endif
//#if defined(presence_ilusion)
import rise.smarthome.features.PresenceIlusion;
//#endif

//#if defined(user_air_conditioner_control)
import rise.smarthome.features.UserAirConditionerControl;
//#endif
//#if defined(user_ilumination)
import rise.smarthome.features.UserIlumination;
//#endif
//#if defined(user_window_control)
import rise.smarthome.features.UserWindowControl;
//#endif


public class FeatureHelper {

	public static List<Class<? extends FeatureBase>> getAllAvaliableFeatures(){

		ArrayList<Class<? extends FeatureBase>> featureList = new ArrayList<Class<? extends FeatureBase>>();

			featureList.add(UserAirConditionerControl.class);
			featureList.add(AutomatedAirConditionerControl.class);
			featureList.add(PresenceIlusion.class);
			featureList.add(AutomatedIluminationByPresence.class);
			featureList.add(UserIlumination.class);
			featureList.add(AutomatedIluminationByLuminosity.class);
			featureList.add(UserWindowControl.class);
			featureList.add(AutomatedWindowControl.class);
			featureList.add(PanicMode.class);
			featureList.add(AlarmAgainstRobbery.class);
			featureList.add(LockDoors.class);

		//#if defined(alarm_against_robery)
		featureList.add(AlarmAgainstRobbery.class);
		//#endif

		//#if defined(automated_air_conditioner_control)
		featureList.add(AutomatedAirConditionerControl.class);
		//#endif

		//#if defined(automated_ilumination_by_luminosity)
		featureList.add(AutomatedIluminationByLuminosity.class);
		//#endif

		//#if defined(automated_ilumination_by_presence)
		featureList.add(AutomatedIluminationByPresence.class);
		//#endif

		//#if defined(automated_window_control)
		featureList.add(AutomatedWindowControl.class);
		//#endif

		//#if defined(lock_doors)
		featureList.add(LockDoors.class);
		//#endif

		//#if defined(panic_mode)
		featureList.add(PanicMode.class);
		//#endif

		//#if defined(presence_ilusion)
		featureList.add(PresenceIlusion.class);
		//#endif

		//#if defined(user_air_conditioner_control)
		featureList.add(UserAirConditionerControl.class);
		//#endif

		//#if defined(user_ilumination)
		featureList.add(UserIlumination.class);
		//#endif

		//#if defined(user_window_control)
		featureList.add(UserWindowControl.class);
		//#endif

		return featureList;
	}
}