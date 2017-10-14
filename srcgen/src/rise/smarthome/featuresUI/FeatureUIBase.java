package rise.smarthome.featuresUI;

import javax.swing.JPanel;

import rise.smarthome.featureModeling.FeatureBase;

public abstract class FeatureUIBase extends JPanel {

	private static final long serialVersionUID = 5221285834937378979L;
	private Class<? extends FeatureBase> forClass;
	
	public FeatureUIBase() {
		setLayout(null);
	}
	
	protected void setForClass(Class<? extends FeatureBase> clazz){
		this.forClass = clazz;
	}
	
	public boolean isForClass(Class<? extends FeatureBase> clazz){
		if(forClass.equals(clazz))
			return true;
		return false;
	}
}