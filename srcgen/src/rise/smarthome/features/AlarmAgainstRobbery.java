package rise.smarthome.features;

import java.util.ArrayList;

//import rise.smarthome.featureModeling.MandatoryFeature;
import rise.smarthome.featureModeling.FeatureBase;
import rise.smarthome.featureModeling.OptionalFeature;
import rise.smarthome.model.devices.Alarm;

@OptionalFeature
public class AlarmAgainstRobbery  extends FeatureBase  {

	private ArrayList<Alarm> alarms;
	private AlarmAgainstRoberryThread alarmAgainstRobberyThread;
	private static AlarmAgainstRobbery alarmAgainstRobbery = null;
	
	private AlarmAgainstRobbery() {}
	
	public static AlarmAgainstRobbery getInstance(ArrayList<Alarm> alarms) {
		if(alarmAgainstRobbery == null){
			alarmAgainstRobbery = new AlarmAgainstRobbery();
			alarmAgainstRobbery.setName("Alarm");
            alarmAgainstRobbery.setAlarms(alarms);
		}
		return alarmAgainstRobbery;
	}
	
	public static void distroy(){
		alarmAgainstRobbery = null;
	}

	@Override
	public void proceedActions(String[] args) {
		// [0] - 1 to activate; 0 to deactivate
		if(args[1].equals("1")){
			if(alarmAgainstRobberyThread==null || !alarmAgainstRobberyThread.isAlive()){
				alarmAgainstRobberyThread = new AlarmAgainstRoberryThread(alarms);
				alarmAgainstRobberyThread.run();
			}
		}else if (args[0].equals("0")){
			if(alarmAgainstRobberyThread!=null || alarmAgainstRobberyThread.isAlive()){
				alarmAgainstRobberyThread.finhishAction();
			}
		}
	}
	
	private class AlarmAgainstRoberryThread extends Thread{
		private ArrayList<Alarm> alarms;
		private boolean shouldInterrupt = false;
		
		public AlarmAgainstRoberryThread(ArrayList<Alarm> alarms){
			this.alarms = alarms;
		}
		
		@Override
		public void run() {
			while(!shouldInterrupt){
				for (Alarm actuator : alarms) {
					actuator.activate();
				}
			}
		}
		
		protected void finhishAction(){
			shouldInterrupt = true;
		}
    }
    
    public ArrayList<Alarm> getAlarms() {
		return alarms;
	}

	public void setAlarms(ArrayList<Alarm> alarms) {
		this.alarms = alarms;
	}
}
        
