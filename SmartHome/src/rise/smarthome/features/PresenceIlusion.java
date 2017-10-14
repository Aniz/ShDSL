package {{systemName|lower}}.smarthome.features;

import java.util.ConcurrentModificationException;
import java.util.Random;

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
public class PresenceIlusion {% if data.feature.extends %}extends {{data.feature.extends}} implements AdaptableFeature {% else %} extends FeatureBase {% endif %} {

	private PresenceIlusionThread presenceIlusionThread;

	private static PresenceIlusion presenceIlusion = null;

	protected PresenceIlusion(){}

	public static PresenceIlusion getInstance({{data.feature.extends|lower}} {{data.feature.extends|lower}}) {
		if(presenceIlusion == null){
			presenceIlusion = new PresenceIlusion();
			presenceIlusion.setActive(true);
			presenceIlusion.setName("Presence Ilusion");
			presenceIlusion.addRequiredFeature({{data.feature.extends|lower}});
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
		{{data.feature.extends|lower}} {{data.feature.extends|lower}} = ({{data.feature.extends|lower}}) getRequiredFeatures().get(0);
		if({{data.feature.extends|lower}}.get{{data.feature.actuador.name}}s() != null){
			if(isActive()){
				if(args==null){
					presenceIlusionThread = new PresenceIlusionThread(({{data.feature.extends|lower}}) getRequiredFeatures().get(0));
					presenceIlusionThread.start();
				}else{
					if(args[0].equals("1")){
						presenceIlusionThread = new PresenceIlusionThread(Integer.parseInt(args[1]), 
								Integer.parseInt(args[2]), ({{data.feature.extends|lower}}) getRequiredFeatures().get(0));
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
		private {{data.feature.extends|lower}} {{data.feature.extends|lower}};
		private int intervalTime;
		private int timeToStop;
		private boolean shouldInterrupt = false;

		public PresenceIlusionThread(int timeToStop, int intervalTime, 
				{{data.feature.extends|lower}} {{data.feature.extends|lower}}) {
			this.timeToStop = timeToStop;
			this.intervalTime = intervalTime;
			this.{{data.feature.extends|lower}} = {{data.feature.extends|lower}};
		}

		public PresenceIlusionThread({{data.feature.extends|lower}} {{data.feature.extends|lower}}){
			this(-1, 5000, {{data.feature.extends|lower}});
		}

		@Override
		public synchronized void run(){
			while(!shouldInterrupt && timeToStop!=0){
				try{
					if({{data.feature.extends|lower}}.get{{data.feature.actuador.name}}s().size()!=0){
						synchronized ({{data.feature.extends|lower}}) {
							String instructionsArray[] = {String.valueOf({{data.feature.extends|lower}}.get{{data.feature.actuador.name}}s().get(new Random().nextInt({{data.feature.extends|lower}}.get{{data.feature.actuador.name}}s().size())).getPin()),"-1"};
							{{data.feature.extends|lower}}.proceedActions(instructionsArray);
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
			for (Led led : {{data.feature.extends|lower}}.get{{data.feature.actuador.name}}s()) {
				String[] args = {String.valueOf(led.getPin()),"0"};
				{{data.feature.extends|lower}}.proceedActions(args);
			}
		}

		protected void finhishAction(){
			shouldInterrupt = true;
		}

	}

}
