package {{systemName|lower}}.smarthome.features;

import java.util.ConcurrentModificationException;
import java.util.Random;

{% if data.feature.extend %}
import {{systemName|lower}}.smarthome.featureModeling.AdaptableFeature;
{% else %}
import {{systemName|lower}}.smarthome.featureModeling.FeatureBase;
{% endif %}
{% if data.feature.type %}
import {{systemName|lower}}.smarthome.featureModeling.{{data.feature.type}}Feature;
{% endif %}
import {{systemName|lower}}.smarthome.model.devices.{{data.feature.actuator.name}};

{% if data.feature.type == "Mandatory" %}
@MandatoryFeature
{% endif %}
{% if data.feature.type == "Optional" %}
@OptionalFeature
{% endif %}
{% if data.feature.type == "Alternative" %}
@AlternativeFeature(alternatives={
{% for altenative in data.feature.alternatives %}
	{{altenative.name}}.class{% if not loop.last %},{% endif %}
{% endfor %}
})
{% endif %}
public class PresenceIlusion extends FeatureBase{

	private PresenceIlusionThread presenceIlusionThread;

	private static PresenceIlusion presenceIlusion = null;

	protected PresenceIlusion(){}

	public static PresenceIlusion getInstance(UserIlumination userIlumination) {
		if(presenceIlusion == null){
			presenceIlusion = new PresenceIlusion();
			presenceIlusion.setActive(true);
			presenceIlusion.setName("Presence Ilusion");
			presenceIlusion.addRequiredFeature(userIlumination);
		}
		return presenceIlusion;
	}

	public static void distroy() {
		presenceIlusion = null;
	}

	@Override
	public void proceedActions(String[] args) {
		// if args == null
		//    Proceed feature until deactivate command
		// else
		// [0] - 1 to activate; 0 to deactivate
		// [1] - time to automaticaly deactivate the feature (-1 to only deactivate when command)
		// [2] - interval time to change the state of a sorted led
		UserIlumination userIlumination = (UserIlumination) getRequiredFeatures().get(0);
		if(userIlumination.getLeds() != null){
			if(isActive()){
				if(args==null){
					presenceIlusionThread = new PresenceIlusionThread((UserIlumination) getRequiredFeatures().get(0));
					presenceIlusionThread.start();
				}else{
					if(args[0].equals("1")){
						presenceIlusionThread = new PresenceIlusionThread(Integer.parseInt(args[1]), 
								Integer.parseInt(args[2]), (UserIlumination) getRequiredFeatures().get(0));
					}else if (args[0].equals("0")){
						if(presenceIlusionThread!=null && presenceIlusionThread.isAlive()){
							presenceIlusionThread.finhishAction();
						}
					}
				}
			}
		}
	}

	private class PresenceIlusionThread extends Thread{
		private UserIlumination userIlumination;
		private int intervalTime;
		private int timeToStop;
		private boolean shouldInterrupt = false;

		public PresenceIlusionThread(int timeToStop, int intervalTime, 
				UserIlumination userIlumination) {
			this.timeToStop = timeToStop;
			this.intervalTime = intervalTime;
			this.userIlumination = userIlumination;
		}

		public PresenceIlusionThread(UserIlumination userIlumination){
			this(-1, 5000, userIlumination);
		}

		@Override
		public synchronized void run(){
			while(!shouldInterrupt && timeToStop!=0){
				try{
					if(userIlumination.getLeds().size()!=0){
						synchronized (userIlumination) {
							String instructionsArray[] = {String.valueOf(userIlumination.getLeds().get(new Random().nextInt(userIlumination.getLeds().size())).getPin()),"-1"};
							userIlumination.proceedActions(instructionsArray);
						}
					}
					try {
						sleep(intervalTime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					timeToStop--;
				}catch(ConcurrentModificationException ex){}
			}
			for (Led led : userIlumination.getLeds()) {
				String[] args = {String.valueOf(led.getPin()),"0"};
				userIlumination.proceedActions(args);
			}
		}

		protected void finhishAction(){
			shouldInterrupt = true;
		}

	}

}
