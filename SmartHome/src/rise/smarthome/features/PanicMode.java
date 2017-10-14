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
	
	public static PanicMode getInstance({{data.feature.extends}} {{data.feature.extends|lower}}) {
		if(panicMode == null){
			panicMode = new PanicMode();
			panicMode.setName("Panic Mode");
			{% if data.feature.extends %}
			panicMode.addRequiredFeature({{data.feature.extends|lower}});
			{% endif %}
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
		private {{data.feature.extends}} {{data.feature.extends|lower}};
		private boolean shouldInterrupt = false;

		public PanicModeThread ({{data.feature.extends}} {{data.feature.extends|lower}}){
			this.{{data.feature.extends|lower}} = {{data.feature.extends|lower}};
		}

		@Override
		public void run() {
			while(!shouldInterrupt){
				for ({{data.feature.actuador.name}} {{data.feature.actuador.name|lower}} : {{data.feature.extends|lower}}.get{{data.feature.actuador.name}}s()) {
					String instructionsArray[] = {String.valueOf({{data.feature.actuador.name|lower}}.getPin()),"0"};
					{{data.feature.extends|lower}}.proceedActions(instructionsArray);
				}
				try {
					sleep(500);
				} catch (InterruptedException e) {e.printStackTrace();}
				for ({{data.feature.actuador.name}} {{data.feature.actuador.name|lower}} : {{data.feature.extends|lower}}.get{{data.feature.actuador.name}}s()) {
					String instructionsArray[] = {String.valueOf({{data.feature.actuador.name|lower}}.getPin()),"1"};
					{{data.feature.extends|lower}}.proceedActions(instructionsArray);
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
