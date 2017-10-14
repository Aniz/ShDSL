package rise.smarthome.features;

import java.util.ConcurrentModificationException;
import java.util.Random;

import rise.smarthome.featureModeling.FeatureBase;
import rise.smarthome.featureModeling.MandatoryFeature;
import rise.smarthome.model.devices.Led;

@MandatoryFeature
public class PresenceIlusion  extends FeatureBase  {

	private PresenceIlusionThread presenceIlusionThread;

	private static PresenceIlusion presenceIlusion = null;

	protected PresenceIlusion(){}

	public static PresenceIlusion getInstance( ) {
		if(presenceIlusion == null){
			presenceIlusion = new PresenceIlusion();
			presenceIlusion.setActive(true);
			presenceIlusion.setName("Presence Ilusion");
			presenceIlusion.addRequiredFeature();
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
		  = () getRequiredFeatures().get(0);
		if(.getLeds() != null){
			if(isActive()){
				if(args==null){
					presenceIlusionThread = new PresenceIlusionThread(() getRequiredFeatures().get(0));
					presenceIlusionThread.start();
				}else{
					if(args[0].equals("1")){
						presenceIlusionThread = new PresenceIlusionThread(Integer.parseInt(args[1]), 
								Integer.parseInt(args[2]), () getRequiredFeatures().get(0));
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
		private  ;
		private int intervalTime;
		private int timeToStop;
		private boolean shouldInterrupt = false;

		public PresenceIlusionThread(int timeToStop, int intervalTime, 
				 ) {
			this.timeToStop = timeToStop;
			this.intervalTime = intervalTime;
			this. = ;
		}

		public PresenceIlusionThread( ){
			this(-1, 5000, );
		}

		@Override
		public synchronized void run(){
			while(!shouldInterrupt && timeToStop!=0){
				try{
					if(.getLeds().size()!=0){
						synchronized () {
							String instructionsArray[] = {String.valueOf(.getLeds().get(new Random().nextInt(.getLeds().size())).getPin()),"-1"};
							.proceedActions(instructionsArray);
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
			for (Led led : .getLeds()) {
				String[] args = {String.valueOf(led.getPin()),"0"};
				.proceedActions(args);
			}
		}

		protected void finhishAction(){
			shouldInterrupt = true;
		}

	}

}