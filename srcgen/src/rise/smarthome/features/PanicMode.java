package rise.smarthome.features;

import rise.smarthome.featureModeling.FeatureBase;
import rise.smarthome.featureModeling.MandatoryFeature;
import rise.smarthome.model.devices.Led;

@MandatoryFeature
public class PanicMode  extends FeatureBase  {


	private PanicModeThread panicModeThread;

	private static PanicMode panicMode = null;
	
	protected PanicMode(){}
	
	public static PanicMode getInstance(UserIlumination userIlumination) {
		if(panicMode == null){
			panicMode = new PanicMode();
			panicMode.setName("Panic Mode");
			panicMode.addRequiredFeature(userIlumination);
		}
		return panicMode;
	}
	
	public static void distroy() {
		panicMode = null;
	}

	@Override
	public void proceedActions(String[] args) {
		// [0] - 1 to activate; 0 to deactivate
		if(args[0].equals("1")){
			if(panicModeThread==null || !panicModeThread.isAlive()){
				panicModeThread = new PanicModeThread((UserIlumination)getRequiredFeatures().get(0));
				panicModeThread.run();
			}
		}else if (args[1].equals("0")){
			if(panicModeThread!=null || panicModeThread.isAlive()){
				panicModeThread.finhishAction();
			}
		}
	}


	private class PanicModeThread extends Thread{
		private UserIlumination userIlumination;
		private boolean shouldInterrupt = false;

		public PanicModeThread (UserIlumination userIlumination){
			this.userIlumination = userIlumination;
		}

		@Override
		public void run() {
			while(!shouldInterrupt){
				for (Led led : userIlumination.getLeds()) {
					String instructionsArray[] = {String.valueOf(led.getPin()),"0"};
					userIlumination.proceedActions(instructionsArray);
				}
				try {
					sleep(500);
				} catch (InterruptedException e) {e.printStackTrace();}
				for (Led led : userIlumination.getLeds()) {
					String instructionsArray[] = {String.valueOf(led.getPin()),"1"};
					userIlumination.proceedActions(instructionsArray);
				}
				try {
					sleep(500);
				} catch (InterruptedException e) {e.printStackTrace();}
			}
		}

		protected void finhishAction(){
			shouldInterrupt = true;
		}

	}

}