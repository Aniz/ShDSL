package {{systemName|lower}}.smarthome.features;

import java.util.ArrayList;

//import {{systemName|lower}}.smarthome.featureModeling.MandatoryFeature;
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
public class AlarmAgainstRobbery {% if data.feature.extends %}extends {{data.feature.extends}} implements AdaptableFeature {% else %} extends FeatureBase {% endif %} {

	private ArrayList<{{data.feature.actuador.name}}> {{data.feature.actuador.name|lower}}s;
	private AlarmAgainstRoberryThread alarmAgainstRobberyThread;
	private static AlarmAgainstRobbery alarmAgainstRobbery = null;
	
	private AlarmAgainstRobbery() {}
	
	public static AlarmAgainstRobbery getInstance(ArrayList<Alarm> {{data.feature.actuador.name|lower}}s) {
		if(alarmAgainstRobbery == null){
			alarmAgainstRobbery = new AlarmAgainstRobbery();
			alarmAgainstRobbery.setName("{% if data.feature.actuador.alias %}{{data.feature.actuador.alias}}{% else %}{{data.feature.actuador.name|splitName}}{% endif %}");
            alarmAgainstRobbery.set{{data.feature.actuador.name}}s({{data.feature.actuador.name|lower}}s);
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
				alarmAgainstRobberyThread = new AlarmAgainstRoberryThread({{data.feature.actuador.name|lower}}s);
				alarmAgainstRobberyThread.run();
			}
		}else if (args[0].equals("0")){
			if(alarmAgainstRobberyThread!=null || alarmAgainstRobberyThread.isAlive()){
				alarmAgainstRobberyThread.finhishAction();
			}
		}
	}
	
	private class AlarmAgainstRoberryThread extends Thread{
		private ArrayList<{{data.feature.actuador.name}}> {{data.feature.actuador.name|lower}}s;
		private boolean shouldInterrupt = false;
		
		public AlarmAgainstRoberryThread(ArrayList<{{data.feature.actuador.name}}> {{data.feature.actuador.name|lower}}s){
			this.{{data.feature.actuador.name|lower}}s = {{data.feature.actuador.name|lower}}s;
		}
		
		@Override
		public void run() {
			while(!shouldInterrupt){
				for ({{data.feature.actuador.name}} actuador : {{data.feature.actuador.name|lower}}s) {
					actuador.activate();
				}
			}
		}
		
		protected void finhishAction(){
			shouldInterrupt = true;
		}
    }
    
    public ArrayList<{{data.feature.actuador.name}}> get{{data.feature.actuador.name}}s() {
		return {{data.feature.actuador.name|lower}}s;
	}

	public void set{{data.feature.actuador.name}}s(ArrayList<{{data.feature.actuador.name}}> {{data.feature.actuador.name|lower}}s) {
		this.{{data.feature.actuador.name|lower}}s = {{data.feature.actuador.name|lower}}s;
	}
}
        

