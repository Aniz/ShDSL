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
public class AlarmAgainstRobbery {% if data.feature.extends %}extends {{data.feature.extends}} implements AdaptableFeature {% else %} extends FeatureBase {% endif %} {

	private ArrayList<{{data.feature.actuator.name}}> {{data.feature.actuator.name|lower}}s;
	private AlarmAgainstRoberryThread alarmAgainstRobberyThread;
	private static AlarmAgainstRobbery alarmAgainstRobbery = null;
	
	private AlarmAgainstRobbery() {}
	
	public static AlarmAgainstRobbery getInstance(ArrayList<Alarm> {{data.feature.actuator.name|lower}}s) {
		if(alarmAgainstRobbery == null){
			alarmAgainstRobbery = new AlarmAgainstRobbery();
			alarmAgainstRobbery.setName("{% if data.feature.actuator.alias %}{{data.feature.actuator.alias}}{% else %}{{data.feature.actuator.name|splitName}}{% endif %}");
            alarmAgainstRobbery.set{{data.feature.actuator.name}}s({{data.feature.actuator.name|lower}}s);
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
				alarmAgainstRobberyThread = new AlarmAgainstRoberryThread({{data.feature.actuator.name|lower}}s);
				alarmAgainstRobberyThread.run();
			}
		}else if (args[0].equals("0")){
			if(alarmAgainstRobberyThread!=null || alarmAgainstRobberyThread.isAlive()){
				alarmAgainstRobberyThread.finhishAction();
			}
		}
	}
	
	private class AlarmAgainstRoberryThread extends Thread{
		private ArrayList<{{data.feature.actuator.name}}> {{data.feature.actuator.name|lower}}s;
		private boolean shouldInterrupt = false;
		
		public AlarmAgainstRoberryThread(ArrayList<{{data.feature.actuator.name}}> {{data.feature.actuator.name|lower}}s){
			this.{{data.feature.actuator.name|lower}}s = {{data.feature.actuator.name|lower}}s;
		}
		
		@Override
		public void run() {
			while(!shouldInterrupt){
				for ({{data.feature.actuator.name}} actuator : {{data.feature.actuator.name|lower}}s) {
					actuator.activate();
				}
			}
		}
		
		protected void finhishAction(){
			shouldInterrupt = true;
		}
    }
    
    public ArrayList<{{data.feature.actuator.name}}> get{{data.feature.actuator.name}}s() {
		return {{data.feature.actuator.name|lower}}s;
	}

	public void set{{data.feature.actuator.name}}s(ArrayList<{{data.feature.actuator.name}}> {{data.feature.actuator.name|lower}}s) {
		this.{{data.feature.actuator.name|lower}}s = {{data.feature.actuator.name|lower}}s;
	}
}
        

