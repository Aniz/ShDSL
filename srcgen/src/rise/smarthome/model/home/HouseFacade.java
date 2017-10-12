package rise.smarthome.model.home;

import java.lang.reflect.Modifier;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import rise.smarthome.featureModeling.AlternativeFeature;
import rise.smarthome.featureModeling.FeatureBase;
import rise.smarthome.gui.AlternativeFeatureSelectionDialog;
import rise.smarthome.gui.Main;

import rise.smarthome.features.AlarmAgainstRobbery;
import rise.smarthome.features.AutomatedAirConditionerControl;
import rise.smarthome.features.AutomatedIluminationByLuminosity;
import rise.smarthome.features.AutomatedIluminationByPresence;
import rise.smarthome.features.AutomatedWindowControl;
import rise.smarthome.features.LockDoors;
import rise.smarthome.features.PanicMode;
import rise.smarthome.features.PresenceIlusion;
import rise.smarthome.features.UserAirConditionerControl;
import rise.smarthome.features.UserIlumination;
import rise.smarthome.features.UserWindowControl;

import rise.smarthome.model.devices.AirConditioner;
import rise.smarthome.model.devices.Alarm;
import rise.smarthome.model.devices.AutomaticDoor;
import rise.smarthome.model.devices.AutomaticWindow;
import rise.smarthome.model.devices.Hardware;
import rise.smarthome.model.devices.Led;
import rise.smarthome.model.devices.LightSensor;
import rise.smarthome.model.devices.PresenceSensor;
import rise.smarthome.model.devices.TemperatureSensor;

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
		avaliableFeatures.add(AutomatedIluminationByLuminosity.getInstance());
		avaliableFeatures.add(AutomatedIluminationByPresence.getInstance());
        avaliableFeatures.add(AutomatedWindowControl.getInstance());
		avaliableFeatures.add(AutomatedAirConditionerControl.getInstance());
		               
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
		UserIlumination userIlumination = UserIlumination.getInstance(new ArrayList<Led>());
		addFeature(userIlumination);
		PresenceIlusion presenceIlusion = PresenceIlusion.getInstance(userIlumination);
		addFeature(presenceIlusion);
		PanicMode panicMode = PanicMode.getInstance(userIlumination);
		addFeature(panicMode);
        
	}
	
	private void loadOptionalFeatures() {
		LockDoors lockDoors = LockDoors.getInstance(new ArrayList<AutomaticDoor>());
		addFeature(lockDoors);
		AlarmAgainstRobbery alarmAgainstRobbery = AlarmAgainstRobbery.getInstance(new ArrayList<Alarm>());
        addFeature(alarmAgainstRobbery);
		UserAirConditionerControl userAirConditionerControl = UserAirConditionerControl.getInstance(new ArrayList<AirConditioner>());
		addFeature(userAirConditionerControl);
		UserWindowControl userWindowControl = UserWindowControl.getInstance(new ArrayList<AutomaticWindow>());
		addFeature(userWindowControl);
		
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
                    //
			if(anyHardware instanceof Led){
				if(featureBase instanceof UserIlumination){
					UserIlumination userIlumination = (UserIlumination) featureBase;
					userIlumination.getLeds().remove(anyHardware);
				}
				if(featureBase instanceof AutomatedIluminationByLuminosity){
					AutomatedIluminationByLuminosity au = (AutomatedIluminationByLuminosity) featureBase;
					au.getLedsToAutomate().remove(anyHardware);
				}
				if(featureBase instanceof AutomatedIluminationByPresence){
					AutomatedIluminationByPresence ap = (AutomatedIluminationByPresence) featureBase;
					ap.getLedsToAutomate().remove(anyHardware);
				}
			}else if(anyHardware instanceof LightSensor){
				if(featureBase instanceof AutomatedIluminationByLuminosity){
					AutomatedIluminationByLuminosity au = (AutomatedIluminationByLuminosity) featureBase;
					au.setLightSensor(null);
				}
			}else if(anyHardware instanceof TemperatureSensor){
				if(featureBase instanceof AutomatedWindowControl){
					AutomatedWindowControl automatedWindowControlau = (AutomatedWindowControl) featureBase;
					automatedWindowControlau.setTemperatureSensor(null);
				}
                        
                        }else if(anyHardware instanceof TemperatureSensor){
				if(featureBase instanceof AutomatedAirConditionerControl){
					AutomatedAirConditionerControl automatedAirConditionerControlau = (AutomatedAirConditionerControl) featureBase;
					automatedAirConditionerControlau.setTemperatureSensor(null);
				}
                        
                        }else if(anyHardware instanceof PresenceSensor){
				if(featureBase instanceof AutomatedIluminationByPresence){
					AutomatedIluminationByPresence ap = (AutomatedIluminationByPresence) featureBase;
					ap.setPresenceSensor(null);
				}
			} else if(anyHardware instanceof AutomaticWindow){
                            if(featureBase instanceof UserWindowControl){
                                UserWindowControl userWindowControl = (UserWindowControl) featureBase;
                                userWindowControl.getAutomaticWindows().remove(anyHardware);
                                
                            }
                            if(featureBase instanceof AutomatedWindowControl){
                                AutomatedWindowControl automatedWindowControl = (AutomatedWindowControl) featureBase;
                                automatedWindowControl.getAutomaticWindowsToAutomate().remove(anyHardware);
                            }
                            }else if(anyHardware instanceof AirConditioner){
                                 if(featureBase instanceof AutomatedAirConditionerControl){
                                AutomatedAirConditionerControl automatedAirConditionerControl = (AutomatedAirConditionerControl) featureBase;
                                automatedAirConditionerControl.getAirConditionersToAutomate().remove(anyHardware);
                                 }
                                 if(featureBase instanceof UserAirConditionerControl){
                                UserAirConditionerControl userAirConditionerControl = (UserAirConditionerControl) featureBase;
                                userAirConditionerControl.getAirConditioners().remove(anyHardware);  
                                 }
                            }else if(anyHardware instanceof Alarm){
                                if(featureBase instanceof AlarmAgainstRobbery){
                                    AlarmAgainstRobbery alarmAgainstRobbery = (AlarmAgainstRobbery) featureBase;
                                alarmAgainstRobbery.getAlarms().remove(anyHardware); 
                                    
                                }
                              
                            }else if(anyHardware instanceof AutomaticDoor){
                                if(featureBase instanceof LockDoors){
                                    LockDoors lockDoors = (LockDoors) featureBase;
                                lockDoors.getautomaticDoors().remove(anyHardware);
                                
                            }
		}
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
		if(feature instanceof UserIlumination){
			UserIlumination userIlumination = UserIlumination.getInstance(((UserIlumination)feature).getLeds());
			exchangeRequiredFeature(feature, userIlumination);
			features.remove(feature);
			addFeature(userIlumination);
		}else if(feature instanceof UserWindowControl){
                     UserWindowControl userWindowControl = UserWindowControl.getInstance(((UserWindowControl)feature).getAutomaticWindows());
			exchangeRequiredFeature(feature, userWindowControl);
			features.remove(feature);
			addFeature(userWindowControl);
                }else if(feature instanceof UserAirConditionerControl){
                    UserAirConditionerControl userAirConditionerControl = UserAirConditionerControl.getInstance(((UserAirConditionerControl)feature).getAirConditioners());
			exchangeRequiredFeature(feature, userAirConditionerControl);
			features.remove(feature);
			addFeature(userAirConditionerControl);
                }
                
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
		if(featureBase instanceof AutomatedIluminationByPresence){
			AutomatedIluminationByPresence automatedIluminationByPresence = (AutomatedIluminationByPresence) featureBase;
			((AutomatedIluminationByLuminosity) newFeature).setLeds(automatedIluminationByPresence.getLeds());
		}
		if(featureBase instanceof AutomatedIluminationByLuminosity){
			AutomatedIluminationByLuminosity automatedIluminationByLuminosity = (AutomatedIluminationByLuminosity) featureBase;
			((AutomatedIluminationByPresence) newFeature).setLeds(automatedIluminationByLuminosity.getLeds());
		}
        if(featureBase instanceof AutomatedWindowControl){
			AutomatedWindowControl automatedWindowControl = (AutomatedWindowControl) featureBase;
			((AutomatedWindowControl) newFeature).setTemperatureSensor(automatedWindowControl.getTemperatureSensor());
		}
		if(featureBase instanceof AutomatedAirConditionerControl){
			AutomatedAirConditionerControl automatedAirConditionerControl = (AutomatedAirConditionerControl) featureBase;
			((AutomatedAirConditionerControl) newFeature).setTemperatureSensor(automatedAirConditionerControl.getTemperatureSensor());
		}

	}
	
	private void keepFeatureState(FeatureBase oldFeature, FeatureBase newFeature) {
		if(oldFeature instanceof UserIlumination){
			UserIlumination userIlumination = (UserIlumination) oldFeature;
			if(newFeature instanceof AutomatedIluminationByLuminosity){
				((AutomatedIluminationByLuminosity) newFeature).setLeds(userIlumination.getLeds());
			}else if (newFeature instanceof AutomatedIluminationByPresence){
				((AutomatedIluminationByPresence) newFeature).setLeds(userIlumination.getLeds());
			}
		}

		if(oldFeature instanceof UserWindowControl){
			UserWindowControl userWindowControl = (UserWindowControl) oldFeature;
			((AutomatedWindowControl) newFeature).setAutomaticWindowsToAutomate((userWindowControl.getAutomaticWindows()));
		}

		if(oldFeature instanceof UserAirConditionerControl){
			UserAirConditionerControl userAirConditionerControl = (UserAirConditionerControl) oldFeature;
			((UserAirConditionerControl) newFeature).setAirConditioners((userAirConditionerControl.getAirConditioners()));
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