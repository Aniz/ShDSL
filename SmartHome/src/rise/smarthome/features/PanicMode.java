package {{systemName|lower}}.smarthome.features;

{% if data.feature.extends %}
import {{systemName|lower}}.smarthome.featureModeling.AdaptableFeature;
{% else %}
import {{systemName|lower}}.smarthome.featureModeling.FeatureBase;
{% endif %}
{% if data.feature.type %}
import {{systemName|lower}}.smarthome.featureModeling.{{data.feature.type}}Feature;
{% endif %}
import {{systemName|lower}}.smarthome.model.devices.{{data.feature.actuador.name}};

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
public class PanicMode {% if data.feature.extends %}extends {{data.feature.extends}} implements AdaptableFeature {% else %} extends FeatureBase {% endif %} {


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
