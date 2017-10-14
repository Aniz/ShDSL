package rise.smarthome.features;

import rise.smarthome.featureModeling.FeatureBase;
import rise.smarthome.featureModeling.MandatoryFeature;
import rise.smarthome.model.devices.Led;

@MandatoryFeature
public class PanicMode  extends FeatureBase  {


	private PanicModeThread panicModeThread;

	private static PanicMode panicMode = null;
	
	protected PanicMode(){}
	
	public static PanicMode getInstance( ) {
		if(panicMode == null){
			panicMode = new PanicMode();
			panicMode.setName("Panic Mode");
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
		private  ;
		private boolean shouldInterrupt = false;

		public PanicModeThread ( ){
			this. = ;
		}

		@Override
		public void run() {
			while(!shouldInterrupt){
				for (Led led : .getLeds()) {
					String instructionsArray[] = {String.valueOf(led.getPin()),"0"};
					.proceedActions(instructionsArray);
				}
				try {
					sleep(500);
				} catch (InterruptedException e) {e.printStackTrace();}
				for (Led led : .getLeds()) {
					String instructionsArray[] = {String.valueOf(led.getPin()),"1"};
					.proceedActions(instructionsArray);
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