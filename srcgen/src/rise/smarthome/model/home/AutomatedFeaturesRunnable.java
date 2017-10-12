package rise.smarthome.model.home;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import rise.smarthome.featureModeling.AdaptableFeature;
import rise.smarthome.featureModeling.FeatureBase;

public class AutomatedFeaturesRunnable implements Runnable {

	private ArrayList<FeatureBase> featureList;

	public AutomatedFeaturesRunnable(ArrayList<FeatureBase> featureList) {
		this.featureList = featureList;
	}

	@Override
	public void run() {
		while(true){
			synchronized (featureList) {
				try{
					for (FeatureBase featureBase : featureList) {
						if(featureBase instanceof AdaptableFeature){
							AdaptableFeature autonomicFeature = (AdaptableFeature) featureBase;
							autonomicFeature.proceedActions();
						}
					}
				}catch(ConcurrentModificationException ex){}
			}
		}
	}

	public synchronized ArrayList<FeatureBase> getFeatureList() {
		return featureList;
	}

	public synchronized void setFeatureList(ArrayList<FeatureBase> featureList) {
		this.featureList = featureList;
	}

}