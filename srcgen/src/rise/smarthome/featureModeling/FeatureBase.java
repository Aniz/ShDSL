package rise.smarthome.featureModeling;

import java.util.ArrayList;

public abstract class FeatureBase {
	private String name;
	private boolean isActive=true;
	private ArrayList<FeatureBase> requiredFeatures;
	
	public abstract void proceedActions(String[] args);
	
	public void addRequiredFeature(FeatureBase feature){
		if(requiredFeatures == null) 
			requiredFeatures = new ArrayList<FeatureBase>();
		requiredFeatures.add(feature);
	}
	
	public ArrayList<FeatureBase> getRequiredFeatures() {
		return requiredFeatures;
	}
	public String getName() {
		return name;
	}
	protected void setName(String name) {
		this.name = name;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public String toString() {
		return  name;
	}
}