package {{systemName|lower}}.smarthome.model.home;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import {{systemName|lower}}.smarthome.featureModeling.AlternativeFeature;
import {{systemName|lower}}.smarthome.featureModeling.FeatureBase;
import {{systemName|lower}}.smarthome.gui.AlternativeFeatureSelectionDialog;
import {{systemName|lower}}.smarthome.gui.Main;
import {{systemName|lower}}.smarthome.model.devices.Hardware;

{% for k,feature in data.items() %}
import {{systemName|lower}}.smarthome.features.{{feature["feature"].name}};
{% endfor %}

{% for k,device in extraData.items() %}
import {{systemName|lower}}.smarthome.model.devices.{{device["device"].name}};
{% endfor %}

public class HouseFacade {
	private static ArrayList<FeatureBase> avaliableFeatures;
	private static ArrayList<FeatureBase> features;
	private static ArrayList<Hardware> hardwareList;
	private static AutomatedFeaturesRunnable automatedFeaturesRunnable;

	public HouseFacade(){
		features = new ArrayList<FeatureBase>();
		hardwareList = new ArrayList<Hardware>();
		loadMandatoryFeatures();
		loadAvaliableFeatures();
		loadOptionalFeatures();
		automatedFeaturesRunnable = new AutomatedFeaturesRunnable(features);
		(new Thread(automatedFeaturesRunnable)).start();
	}

	public void addAvaliableFeature(FeatureBase featureBase){
		avaliableFeatures.add(featureBase);
	}
	private void loadAvaliableFeatures() {
		avaliableFeatures = new ArrayList<FeatureBase>();
		{% for key,feature in data.items() %}
		{% if feature["feature"].sensor %}
			avaliableFeatures.add({{feature["feature"].name}}.getInstance());
		{% endif %}
		{% endfor %}		               
	}

	public FeatureBase getFeatureByType(Class<? extends FeatureBase> clazz){
		for (FeatureBase feature : features) {
			if(clazz.isInstance(feature)){
				return feature;
			}
		}
		return null;
	}

	public ArrayList<Hardware> getAllHardwareByType(Class<? extends Hardware> clazz){
		ArrayList<Hardware> hardwareList = new ArrayList<Hardware>();
		for (Hardware hardware : HouseFacade.hardwareList) {
			if(clazz.isInstance(hardware)){
				hardwareList.add(hardware);
			}
		}
		return hardwareList;
	}


	private void loadMandatoryFeatures() {
		UserIlumination userIlumination = UserIlumination.getInstance(new ArrayList<{{data.UserIlumination.feature.actuator.name}}>());
		addFeature(userIlumination);
		PresenceIlusion presenceIlusion = PresenceIlusion.getInstance(userIlumination);
		addFeature(presenceIlusion);
		PanicMode panicMode = PanicMode.getInstance(userIlumination);
		addFeature(panicMode);
	}
	
	private void loadOptionalFeatures() {
		{% for key,feature in data.items() %}
		{% if feature["feature"].type == "Optional" %}
		{{feature["feature"].name}} {{feature["feature"].name|lower}} = {{feature["feature"].name}}.getInstance(new ArrayList<{{feature["feature"].actuator.name}}>());
		addFeature({{feature["feature"].name|lower}});
		{% endif %}
		{% endfor %}	
	}
	
	public void removeHardware(int pin, boolean isAnalog){
		Hardware anyHardware = findHardware(pin,isAnalog);
		if(hardwareList.contains(anyHardware)){
			removeHardwareFromFeatures(anyHardware);
			hardwareList.remove(anyHardware);
			JOptionPane.showMessageDialog(null, "Hardware removed successfully!","RiSE SmartHome - INFO",JOptionPane.INFORMATION_MESSAGE);
		}else{
			JOptionPane.showMessageDialog(null, "No have any hardware installed in this pin","RiSE SmartHome - INFO",JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private Hardware findHardware(int pin, boolean isAnalog) {
		for (Hardware hardware : hardwareList) {
			if(hardware.getPin() == pin && hardware.isAnalog() == isAnalog)
				return hardware;
		}
		return null;
	}

	private void removeHardwareFromFeatures(Hardware anyHardware) {
		for (FeatureBase featureBase : features) {
            
              {% for keyD,device in extraData.items() %}
           if(anyHardware instanceof {{keyD}}){
			{% for keyF,feature in data.items() %}
				{% if device["device"].typeDevice == "Actuator" %}
           		{% if feature["feature"].actuator.name == keyD and feature["feature"].type != "Mandatory" %}
           		if(featureBase instanceof {{feature["feature"].name}}){
					{{feature["feature"].name}} {{feature["feature"].name|lower}} = ({{feature["feature"].name}}) featureBase;
					{{feature["feature"].name|lower}}.get{{feature["feature"].actuator.name}}s().remove(anyHardware);
				}
				{% endif %}
           		{% endif %}
           		{% if device["device"].typeDevice == "Sensor" %}
           		{% if feature["feature"].sensor.name == keyD %}
           		if(featureBase instanceof {{feature["feature"].name}}){
				{{feature["feature"].name}} {{feature["feature"].name|lower}} = ({{feature["feature"].name}}) featureBase;
				{{feature["feature"].name|lower}}.set{{feature["feature"].sensor.name}}(null);
		   		}
		   		{% endif %}
		   		{% endif %}
           	{% endfor %}
			}
           {% endfor %}
     	}         
	}
        
	public void addHardware(Hardware newHardware){
		if (hardwareList.contains(newHardware)){
			JOptionPane.showMessageDialog(null, "Already exists another hardware installed in this pin.\n "
					+ "The action cannot be completed","RiSE SmartHome - INFO",JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		hardwareList.add(newHardware);
		JOptionPane.showMessageDialog(null, "Hardware successfully added!","RiSE SmartHome - INFO",JOptionPane.INFORMATION_MESSAGE);

	}	

	private boolean evaluateForFeatureDependency(FeatureBase feature) {
		// if the feature is child of another feature this always can be deleted
		if(!feature.getClass().getSuperclass().equals(FeatureBase.class))
			return true;
		for (FeatureBase featureBase : features) {
			for (FeatureBase requiredFeature : featureBase.getRequiredFeatures()) {
				if(feature == requiredFeature)
					return false;
			}
		}
		return true;
	}


	public void addFeature(FeatureBase newFeature){
		newFeature = evaluateForAlternativeFeature(newFeature);
		if(newFeature!=null){
			if(evaluateForFeatureRepetition(newFeature) && evaluationForFeatureHierarchy(newFeature)){
				features.add(newFeature);
			}
		}
	}

	private boolean evaluateForFeatureRepetition(FeatureBase newFeature) {
		for (FeatureBase featureBase : features) {
			if(featureBase.equals(newFeature))
				return false;
		}
		return true;
	}

	public void removeFeature(FeatureBase feature){
		if(evaluateForFeatureDependency(feature)){
			resolveRemotionFeatureHierarchy(feature);
		}else{
			JOptionPane.showMessageDialog(null, "The selected feature cannot be removed. Some other features require them");
		}
	}
	private void resolveRemotionFeatureHierarchy(FeatureBase feature) {
		Class<? extends FeatureBase> featureClass = feature.getClass();
		if(featureClass.getSuperclass().equals(FeatureBase.class))
		return;
		// Alternative Features have abstract superclass, so always can be deleted
		if(Modifier.isAbstract(featureClass.getSuperclass().getModifiers())){
			features.remove(feature);
		}
		// search for a feature brother
		for (FeatureBase featureBase : features) {
			// A feature brother will be different and has the same super class of the feature to be removed
			if(!featureBase.getClass().equals(featureClass) && featureBase.getClass().getSuperclass().equals(featureClass.getSuperclass())){
				// If the feature to remove has a brother, we need to exchange all features that require them to your brother
				exchangeRequiredFeature(feature, featureBase);
				features.remove(feature);
				return;
			}
		}
		recreateFatherFeature(feature);
	}
	private void recreateFatherFeature(FeatureBase feature) {
		{% set extendsArray = data|append %}
		{% for kF,value in data.items() %}
		{% if value.feature.extend %}
		{% if value.feature.extend.name in extendsArray %}
		if(feature instanceof {{value.feature.extend.name}}){
			{{value.feature.extend.name}} {{value.feature.extend.name|lower}} = {{value.feature.extend.name}}.getInstance((({{value.feature.extend.name}})feature).get{{value.feature.actuator.name}}s());
			exchangeRequiredFeature(feature, {{value.feature.extend.name|lower}});
			features.remove(feature);
			addFeature({{value.feature.extend.name|lower}});
		}
		{% set extendsArray = extendsArray|remove(value.feature.extend.name) %}
		{% endif %}
		
		{% endif %}
		{% endfor %}        
	}

	private  boolean evaluationForFeatureHierarchy(FeatureBase newFeature){
		for (FeatureBase featureBase : features) {
			// If exist any father feature of the new feature added
			if(newFeature.getClass().getSuperclass().equals(featureBase.getClass())){
				keepFeatureState(featureBase,newFeature);
				exchangeRequiredFeature(featureBase,newFeature);
				features.remove(featureBase);
				return true;
			}
			//If the new feature is brother of a feature previously added. 
			if(featureBase.getClass().getSuperclass().equals(newFeature.getClass().getSuperclass())){
				exchangeBrotherFeaturesData(featureBase,newFeature);
				return true;
			}
			// If the new feature is super class of an already added feature so, do nothing
			if(featureBase.getClass().getSuperclass().equals(newFeature.getClass())){
				return false;
			}
		}
		return true;
	}

	private void exchangeBrotherFeaturesData(FeatureBase featureBase,FeatureBase newFeature) {
		{% for kF,value in data.items() %}
		{% if value.feature.type == "OR" %}
		if(featureBase instanceof {{value.feature.name}}){
			{{value.feature.name}} {{value.feature.name|lower}} = ({{value.feature.name}}) featureBase;
			(({{value.feature.brother.name}}) newFeature).set{{value.feature.actuator.name}}s({{value.feature.name|lower}}.get{{value.feature.actuator.name}}s());
		}	
		{% endif %}
		{% endfor %}

	}
	
	private void keepFeatureState(FeatureBase oldFeature, FeatureBase newFeature) {
		if(oldFeature instanceof UserIlumination){
			UserIlumination userIlumination = (UserIlumination) oldFeature;
			if(newFeature instanceof AutomatedIluminationByLuminosity){
				((AutomatedIluminationByLuminosity) newFeature).set{{data.AutomatedIluminationByLuminosity.feature.actuator.name}}s(userIlumination.get{{data.AutomatedIluminationByLuminosity.feature.actuator.name}}s());
			}else if (newFeature instanceof AutomatedIluminationByPresence){
				((AutomatedIluminationByPresence) newFeature).set{{data.AutomatedIluminationByPresence.feature.actuator.name}}s(userIlumination.get{{data.AutomatedIluminationByPresence.feature.actuator.name}}s());
			}
		}

		if(oldFeature instanceof UserWindowControl){
			UserWindowControl userWindowControl = (UserWindowControl) oldFeature;
			((AutomatedWindowControl) newFeature).setAutomaticWindowsToAutomate((userWindowControl.get{{data.UserWindowControl.feature.actuator.name}}s()));
		}

		if(oldFeature instanceof UserAirConditionerControl){
			UserAirConditionerControl userAirConditionerControl = (UserAirConditionerControl) oldFeature;
			((UserAirConditionerControl) newFeature).setAirConditioners((userAirConditionerControl.get{{data.UserAirConditionerControl.feature.actuator.name}}s()));
		}
	}

	private  void exchangeRequiredFeature(FeatureBase oldFeature, FeatureBase newFeature){
		for (FeatureBase featureBase : features) {
			if(featureBase.getRequiredFeatures() !=null && !featureBase.getRequiredFeatures().isEmpty()){
				for (int i = 0; i < featureBase.getRequiredFeatures().size(); i++) {
					if(featureBase.getRequiredFeatures().get(i) == oldFeature){
						featureBase.getRequiredFeatures().set(i, newFeature);
					}
				}
			}
		}
	}
	
	private FeatureBase evaluateForAlternativeFeature(FeatureBase newFeature){
		FeatureBase selectedFeature = null;
		if(newFeature.getClass().getAnnotationsByType(AlternativeFeature.class).length > 0){
			AlternativeFeature alternativeFeature = newFeature.getClass().getAnnotationsByType(AlternativeFeature.class)[0];
			for (Class<? extends FeatureBase> featureClass : alternativeFeature.alternatives()) {
				for (FeatureBase featureBase : features) {
					if(featureBase.getClass().equals(featureClass)){
						AlternativeFeatureSelectionDialog dialog = new AlternativeFeatureSelectionDialog(featureBase, newFeature);
						dialog.setVisible(true);
						selectedFeature = dialog.getSelectedFeature();
						if(selectedFeature.equals(newFeature)){
							features.remove(featureBase);
							Main.removeFeatureTab(featureBase.getClass());
							return selectedFeature;
						}else{
							return null;
						}
					}
				}
			}
			selectedFeature = newFeature;
		}else{
			selectedFeature = newFeature;
		}
		return selectedFeature;
	}

	public ArrayList<FeatureBase> getFeatures() {
		return features;
	}

	public void setFeatures(ArrayList<FeatureBase> features) {
		HouseFacade.features = features;
	}

	public ArrayList<Hardware> getHardwareList() {
		return hardwareList;
	}

	public void setHardwareList(ArrayList<Hardware> hardwareList) {
		HouseFacade.hardwareList = hardwareList;
	}

	public ArrayList<FeatureBase> getAvaliableFeatures() {
		ArrayList<FeatureBase> avalFeature = new ArrayList<FeatureBase>();
		avalFeature.addAll(avaliableFeatures);
		avalFeature.removeAll(features);
		return avalFeature;
	}
}
